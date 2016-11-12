package com.andymur.pg.mbean;

import com.andymur.pg.domain.AddressBookKey;
import com.andymur.pg.domain.User;
import com.andymur.pg.domain.UserKey;
import com.andymur.pg.dumper.PartitionDumper;
import com.andymur.pg.dumper.UserMapDumper;
import com.andymur.pg.generator.UUIDGenerator;
import com.andymur.pg.generator.UserGenerator;
import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.core.IMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jmx.export.annotation.ManagedAttribute;
import org.springframework.jmx.export.annotation.ManagedOperation;
import org.springframework.jmx.export.annotation.ManagedResource;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by muraand.
 */
@Component
@ManagedResource(description = "Users Map MBean", objectName="App:name=usersMapMBean")
public class UserMapBean {

    @Autowired
    private
    UserGenerator userGenerator;

    @Autowired
    private
    UUIDGenerator userKeyGenerator;

    @Resource(name ="users")
    private IMap<UserKey, User> userMap;

    @Resource(name = "hz-example")
    private HazelcastInstance hzInstance;

    @Autowired
    private UserMapDumper mapDumper;

    @Autowired
    private PartitionDumper partitionDumper;

    private int desiredSize = 1_000_000;

    private String userMapFileName = "users.dmp";

    private String userMapPartitioningDumpFileName = "usersParts.dmp";

    @ManagedAttribute
    public int getDesiredSize() {
        return desiredSize;
    }

    @ManagedAttribute
    public void setDesiredSize(int desiredSize) {
        this.desiredSize = desiredSize;
    }

    @ManagedAttribute
    public String getUserMapFileName() {
        return userMapFileName;
    }

    @ManagedAttribute
    public void setUserMapFileName(String userMapFileName) {
        this.userMapFileName = userMapFileName;
    }

    @ManagedAttribute
    public String getUserMapPartitioningDumpFileName() {
        return userMapPartitioningDumpFileName;
    }

    @ManagedAttribute
    public void setUserMapPartitioningDumpFileName(String userMapPartitioningDumpFileName) {
        this.userMapPartitioningDumpFileName = userMapPartitioningDumpFileName;
    }

    @ManagedOperation(description = "Return size of users map")
    public int getUserMapSize() {
        return userMap.size();
    }

    @ManagedOperation(description = "Populate with initial data")
    public int populate() {
        for (int i = 0; i < desiredSize; i++) {
            userMap.set(generateUserKey(), generateUser());
        }
        return getUserMapSize();
    }

    @ManagedOperation(description = "Clear users map")
    public int clear() {
        userMap.clear();
        return getUserMapSize();
    }

    @ManagedOperation(description = "Dump userMap")
    public int dump() {
        mapDumper.dump(userMapFileName);
        return getUserMapSize();
    }

    @ManagedOperation(description = "Dump partitioning for userMap")
    public void dumpPartitioning() {
        Map<Integer, Integer> partitioningMap = new HashMap<>();

        for(UserKey userKey: userMap.keySet()) {
            int partId = hzInstance.getPartitionService().getPartition(userKey).getPartitionId();

            if (!partitioningMap.containsKey(partId)) {
                partitioningMap.put(partId, 1);
            } else {
                int oldPartId = partitioningMap.get(partId);
                partitioningMap.put(partId, oldPartId + 1);
            }
        }

        partitionDumper.setPartitionIdCountMap(partitioningMap);
        partitionDumper.dump(userMapPartitioningDumpFileName);
    }

    private UserKey generateUserKey() {
        UserKey userKey = new UserKey();
        userKey.setUuid(userKeyGenerator.generate());
        return userKey;
    }

    private User generateUser() {
        return userGenerator.generate();
    }

}
