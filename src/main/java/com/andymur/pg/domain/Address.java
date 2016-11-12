package com.andymur.pg.domain;

import com.google.common.base.MoreObjects;
import com.hazelcast.nio.ObjectDataInput;
import com.hazelcast.nio.ObjectDataOutput;
import com.hazelcast.nio.serialization.DataSerializable;

import java.io.IOException;
import java.io.Serializable;

/**
 * Created by muraand.
 */
public class Address implements DataSerializable, Serializable {

    private String country;
    private String city;
    private String street;
    private String building;

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

    public String getBuilding() {
        return building;
    }

    public void setBuilding(String building) {
        this.building = building;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Address address = (Address) o;

        if (country != null ? !country.equals(address.country) : address.country != null) return false;
        if (city != null ? !city.equals(address.city) : address.city != null) return false;
        if (street != null ? !street.equals(address.street) : address.street != null) return false;
        return building != null ? building.equals(address.building) : address.building == null;

    }

    @Override
    public int hashCode() {
        int result = country != null ? country.hashCode() : 0;
        result = 31 * result + (city != null ? city.hashCode() : 0);
        result = 31 * result + (street != null ? street.hashCode() : 0);
        result = 31 * result + (building != null ? building.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("country", country)
                .add("city", city)
                .add("street", street)
                .add("building", building)
                .toString();
    }

    @Override
    public void writeData(ObjectDataOutput objectDataOutput) throws IOException {
        objectDataOutput.writeUTF(country);
        objectDataOutput.writeUTF(city);
        objectDataOutput.writeUTF(street);
        objectDataOutput.writeUTF(building);
    }

    @Override
    public void readData(ObjectDataInput objectDataInput) throws IOException {
        country = objectDataInput.readUTF();
        city = objectDataInput.readUTF();
        street = objectDataInput.readUTF();
        building = objectDataInput.readUTF();
    }
}
