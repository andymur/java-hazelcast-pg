package com.andymur.pg.domain;

import com.google.common.base.MoreObjects;
import com.hazelcast.nio.ObjectDataInput;
import com.hazelcast.nio.ObjectDataOutput;
import com.hazelcast.nio.serialization.DataSerializable;

import java.io.IOException;

/**
 * Created by muraand.
 */
public class AddressBookKey implements DataSerializable {
    String country;
    String city;
    String street;

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("country", country)
                .add("city", city)
                .add("street", street)
                .toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        AddressBookKey that = (AddressBookKey) o;

        if (country != null ? !country.equals(that.country) : that.country != null) return false;
        if (city != null ? !city.equals(that.city) : that.city != null) return false;
        return street != null ? street.equals(that.street) : that.street == null;

    }

    @Override
    public int hashCode() {
        int result = country != null ? country.hashCode() : 0;
        result = 31 * result + (city != null ? city.hashCode() : 0);
        result = 31 * result + (street != null ? street.hashCode() : 0);
        return result;
    }

    @Override
    public void writeData(ObjectDataOutput out) throws IOException {
        out.writeUTF(country);
        out.writeUTF(city);
        out.writeUTF(street);
    }

    @Override
    public void readData(ObjectDataInput in) throws IOException {
        country = in.readUTF();
        city = in.readUTF();
        street = in.readUTF();
    }
}
