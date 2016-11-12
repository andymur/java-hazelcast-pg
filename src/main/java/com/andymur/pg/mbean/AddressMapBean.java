package com.andymur.pg.mbean;

import com.andymur.pg.cache.AddressBookProcessor;
import com.andymur.pg.domain.Address;
import com.andymur.pg.domain.AddressBookKey;
import com.andymur.pg.domain.AddressBookValue;
import com.andymur.pg.domain.User;
import com.andymur.pg.domain.UserKey;
import com.andymur.pg.dumper.AddressBookDumper;
import com.andymur.pg.dumper.PartitionDumper;
import com.google.common.collect.Lists;
import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.core.IMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jmx.export.annotation.ManagedAttribute;
import org.springframework.jmx.export.annotation.ManagedOperation;
import org.springframework.jmx.export.annotation.ManagedResource;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by muraand.
 */
@Component
@ManagedResource(description = "Address Map MBean", objectName="App:name=addressMapMBean")
public class AddressMapBean {
    Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private AddressBookDumper dumper;

    @Autowired
    private PartitionDumper partitionDumper;

    @Resource(name ="addressBook")
    private IMap<AddressBookKey, AddressBookValue> addressBook;

    @Resource(name = "hz-example")
    private HazelcastInstance hzInstance;

    @Resource(name ="users")
    private IMap<UserKey, User> users;

    private String addressBookFileName = "addressBook.dmp";

    private String addressBookPartitioningDumpFileName = "addressBookParts.dmp";

    @ManagedAttribute(description = "Address Book Dump File Name")
    public String getAddressBookFileName() {
        return addressBookFileName;
    }

    @ManagedAttribute(description = "Address Book Dump File Name")
    public void setAddressBookFileName(String addressBookFileName) {
        this.addressBookFileName = addressBookFileName;
    }

    @ManagedOperation(description = "Address Book Partitioning Dump File Name")
    public String getAddressBookPartitioningDumpFileName() {
        return addressBookPartitioningDumpFileName;
    }

    @ManagedOperation(description = "Address Book Partitioning Dump File Name")
    public void setAddressBookPartitioningDumpFileName(String addressBookPartitioningDumpFileName) {
        this.addressBookPartitioningDumpFileName = addressBookPartitioningDumpFileName;
    }

    @ManagedOperation(description = "Return size of address book")
    public int getAddressBookSize() {
        return addressBook.size();
    }

    @ManagedOperation(description = "Clear users map")
    public int clear() {
        addressBook.clear();
        return getAddressBookSize();
    }

    @ManagedOperation(description = "Dump addressBook")
    public int dump() {
        dumper.dump(addressBookFileName);
        return getAddressBookSize();
    }

    @ManagedOperation(description = "Process users map into addresses map")
    public int process() {
        logger.debug("process.enter;");
        for (Map.Entry<UserKey, User> entry: users.entrySet()) {
            User user = entry.getValue();
            Address address = user.getAddress();
            AddressBookKey addressBookKey = new AddressBookKey();

            addressBookKey.setCountry(address.getCountry());
            addressBookKey.setCity(address.getCity());
            addressBookKey.setStreet(address.getStreet());

            AddressBookProcessor addressBookProcessor = new AddressBookProcessor(Lists.newArrayList(user));
            addressBookProcessor.setHazelcastInstance(hzInstance);
            addressBook.executeOnKey(addressBookKey, addressBookProcessor);
        }

        logger.debug("process.exit;");
        return getAddressBookSize();
    }

    @ManagedOperation(description = "Process users map into addresses map with internal caching")
    public int processWithCaching() {
        logger.debug("processWithCaching.enter;");
        Map<AddressBookKey, List<User>> localBook = new HashMap<>();

        for (Map.Entry<UserKey, User> entry: users.entrySet()) {
            List<User> usersToAdd;

            User user = entry.getValue();
            Address address = user.getAddress();
            AddressBookKey addressBookKey = new AddressBookKey();

            addressBookKey.setCountry(address.getCountry());
            addressBookKey.setCity(address.getCity());
            addressBookKey.setStreet(address.getStreet());

            if (!localBook.containsKey(addressBookKey)) {
                usersToAdd = new ArrayList<>();
                localBook.put(addressBookKey, usersToAdd);
            } else {
                usersToAdd = localBook.get(addressBookKey);
            }

            usersToAdd.add(user);
        }
        logger.debug("processWithCaching.enter; intermediate cache is ready;");
        localBook.forEach((k, v) ->{
            AddressBookProcessor addressBookProcessor = new AddressBookProcessor(localBook.get(k));
            addressBookProcessor.setHazelcastInstance(hzInstance);
            addressBook.executeOnKey(k, addressBookProcessor);
        });

        logger.debug("processWithCaching.exit;");
        return getAddressBookSize();
    }

    @ManagedOperation(description = "Dump partioning for addressBook")
    public void dumpPartitioning() {
        Map<Integer, Integer> partitioningMap = new HashMap<>();

        for(AddressBookKey addressBookKey: addressBook.keySet()) {
            int partId = hzInstance.getPartitionService().getPartition(addressBookKey).getPartitionId();

            if (!partitioningMap.containsKey(partId)) {
                partitioningMap.put(partId, 1);
            } else {
                int oldPartId = partitioningMap.get(partId);
                partitioningMap.put(partId, oldPartId + 1);
            }
        }

        partitionDumper.setPartitionIdCountMap(partitioningMap);
        partitionDumper.dump(addressBookPartitioningDumpFileName);
    }
}
