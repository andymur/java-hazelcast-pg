package com.andymur.pg.cache;

import com.andymur.pg.domain.AddressBookKey;
import com.andymur.pg.domain.AddressBookValue;
import com.andymur.pg.domain.User;
import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.map.AbstractEntryProcessor;
import com.hazelcast.nio.ObjectDataInput;
import com.hazelcast.nio.ObjectDataOutput;
import com.hazelcast.nio.serialization.DataSerializable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * Created by andymur.
 */
public class AddressBookProcessor extends AbstractEntryProcessor<AddressBookKey, AddressBookValue> implements DataSerializable {

    private HazelcastInstance hazelcastInstance;

    private static final Logger logger = LoggerFactory.getLogger(AddressBookProcessor.class);

    private List<User> usersToAdd;

    public AddressBookProcessor() {
    }

    public AddressBookProcessor(List<User> userToAdd) {
        this.usersToAdd = userToAdd;
    }

    public void setHazelcastInstance(HazelcastInstance hazelcastInstance) {
        this.hazelcastInstance = hazelcastInstance;
    }

    @Override
    public Object process(Map.Entry<AddressBookKey, AddressBookValue> entry) {
        int partId = hazelcastInstance.getPartitionService().getPartition(entry.getKey()).getPartitionId();
        logger.debug("process.enter; partId = {}", partId);
        AddressBookValue value = entry.getValue();

        if (value == null) {
            value = new AddressBookValue();
        }

        value.getUsers().addAll(usersToAdd);

        entry.setValue(value);
        logger.debug("process.exit; partId = {}", partId);
        return null;
    }

    @Override
    public void writeData(ObjectDataOutput out) throws IOException {
        out.writeObject(usersToAdd);
    }

    @Override
    public void readData(ObjectDataInput in) throws IOException {
        usersToAdd = in.readObject();
    }
}
