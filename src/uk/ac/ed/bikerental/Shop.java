package uk.ac.ed.bikerental;

import java.util.ArrayList;
import java.util.Objects;

public class Shop {
    private String id;
    private Location address;
    private String hours;
    private ArrayList<Shop> partners;
    private ArrayList<Bike> bikes;


    public Shop(String id, Location address, String hours, ArrayList<Shop> partners, ArrayList<Bike> bikes) {
        this.id = id;
        this.address = address;
        this.hours = hours;
        this.partners = partners;
        this.bikes = bikes;
    }

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Location getAddress() {
        return this.address;
    }

    public void setAddress(Location address) {
        this.address = address;
    }

    public String getHours() {
        return this.hours;
    }

    public void setHours(String hours) {
        this.hours = hours;
    }

    public ArrayList<Shop> getPartners() {
        return this.partners;
    }

    public void setPartners(ArrayList<Shop> partners) {
        this.partners = partners;
    }

    public ArrayList<Bike> getBikes() {
        return this.bikes;
    }

    public void setBikes(ArrayList<Bike> bikes) {
        this.bikes = bikes;
    }

    public Shop id(String id) {
        this.id = id;
        return this;
    }

    public Shop address(Location address) {
        this.address = address;
        return this;
    }

    public Shop hours(String hours) {
        this.hours = hours;
        return this;
    }

    public Shop partners(ArrayList<Shop> partners) {
        this.partners = partners;
        return this;
    }

    public Shop bikes(ArrayList<Bike> bikes) {
        this.bikes = bikes;
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this)
            return true;
        if (!(o instanceof Shop)) {
            return false;
        }
        Shop shop = (Shop) o;
        return Objects.equals(id, shop.id) && Objects.equals(address, shop.address) && Objects.equals(hours, shop.hours) && Objects.equals(partners, shop.partners) && Objects.equals(bikes, shop.bikes);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, address, hours, partners, bikes);
    }

    @Override
    public String toString() {
        return "{" +
            " id='" + getId() + "'" +
            ", address='" + getAddress() + "'" +
            ", hours='" + getHours() + "'" +
            ", partners='" + getPartners() + "'" +
            ", bikes='" + getBikes() + "'" +
            "}";
    }
}