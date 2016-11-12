package com.andymur.pg.domain;

import com.hazelcast.nio.ObjectDataInput;
import com.hazelcast.nio.ObjectDataOutput;
import com.hazelcast.nio.serialization.DataSerializable;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by muraand.
 */
public class AddressBookValue implements DataSerializable {

    private List<User> users;

    public AddressBookValue() {
        users = new ArrayList<>();
    }

    @Override
    public void writeData(ObjectDataOutput out) throws IOException {
        out.writeObject(users);
    }

    @Override
    public void readData(ObjectDataInput in) throws IOException {
        users = in.readObject();
    }

    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }


}
