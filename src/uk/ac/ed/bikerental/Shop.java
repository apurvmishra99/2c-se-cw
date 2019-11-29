package uk.ac.ed.bikerental;

import static org.junit.Assert.assertTrue;

import java.util.Map;
import java.util.Set;
import java.util.UUID;

public class Shop {
    private UUID id;
    private Location address;
    private String hours;
    private Set<Shop> partners;
    private Set<Bike> bikes;


    public Shop(Location address, String hours, Set<Shop> partners, Set<Bike> bikes) {
        this.id = UUID.randomUUID();
        this.address = address;
        this.hours = hours;
        this.partners = partners;
        this.bikes = bikes;
    }

    private int countBikesByType(BikeType bt) {
        int ret = 0;
        for (Bike b : bikes) {
            if (b.getType() == bt) {
                ret += 1;
            }
        }
        return ret;
    }

    public boolean auth(String s) {
        return true;
    }

    public void addPartner(Shop s) {
        this.partners.add(s);
    }

    public boolean hasBikes(DateRange dates, Map<BikeType, Integer> bikes) {
        assertTrue(dates.isInFuture());
        for(BikeType bt : bikes.keySet()) {
            if (countBikesByType(bt) < bikes.get(bt)) {
                return false;
            }
        }
        return true;
    }

    public UUID getId() {
        return this.id;
    }

    public Location getAddress() {
        return this.address;
    }

    public String getHours() {
        return this.hours;
    }

    public Set<Shop> getPartners() {
        return this.partners;
    }

    public Set<Bike> getBikes() {
        return this.bikes;
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