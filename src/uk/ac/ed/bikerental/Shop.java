package uk.ac.ed.bikerental;

import static org.junit.Assert.assertTrue;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Collection;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

public class Shop {
    private UUID id;
    private Location address;
    private String hours;
    private Set<Shop> partners;
    private Set<Bike> bikes;
    private BigDecimal depositRate;
    private ValuationPolicy valuationPolicy;


    public Shop(Location address, String hours, Set<Shop> partners, Set<Bike> bikes, BigDecimal depositRate, ValuationPolicy valuationPolicy) {
        this.id = UUID.randomUUID();
        this.address = address;
        this.hours = hours;
        this.partners = partners;
        this.bikes = bikes;
        this.depositRate = depositRate;
        this.valuationPolicy = valuationPolicy;
    }

    // private int countBikesByType(BikeType bt) {
    //     int ret = 0;
    //     for (Bike b : bikes) {
    //         if (b.getType() == bt) {
    //             ret += 1;
    //         }
    //     }
    //     return ret;
    // }

    public boolean auth(String s) {
        return true;
    }

    public void addPartner(Shop s) {
        this.partners.add(s);
    }

    public BigDecimal generateDeposit(Collection<Bike> bikeList, LocalDate startDate) {
        BigDecimal ret = new BigDecimal(0);
        for (Bike b : bikeList) {
            ret.add(valuationPolicy.calculateValue(b,startDate));
        }
        return ret.multiply(this.depositRate);
    }

    // public boolean hasBikes(DateRange dates, Map<BikeType, Integer> bikes) {
    //     assertTrue(dates.isInFuture());
    //     for(BikeType bt : bikes.keySet()) {
    //         if (countBikesByType(bt) < bikes.get(bt)) {
    //             return false;
    //         }
    //     }
    //     return true;
    // }

    public Collection<Bike> getBikes(DateRange dates, Map<BikeType, Integer> bikes) {
        assertTrue(dates.isInFuture());
        Collection<Bike> ret = new HashSet<Bike>();
        for(Bike b : this.bikes) {
            if (b.isAvailable(dates)) {
                BikeType bt = b.getType();
                int needed = bikes.getOrDefault(bt, 0);
                if (needed > 0) {
                    ret.add(b);
                    bikes.put(bt, needed - 1);
                    bikes.remove(bt, 0);
                }
            }
        }
        if (bikes.isEmpty()) {
            return ret;
        } else {
            return null;
        }
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

    public BigDecimal getDepositRate() {
        return this.depositRate;
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