package uk.ac.ed.bikerental;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Collection;
import java.util.HashMap;
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
    private PricingPolicy pricingPolicy;

    /**
     * @param address
     * @param hours
     * @param partners
     * @param bikes
     * @param depositRate
     * @return
     */
    public Shop(Location address, String hours, Set<Shop> partners, Set<Bike> bikes, BigDecimal depositRate) {
        this(address, hours, partners, bikes, depositRate, new DefaultValuationPolicy(), new DefaultPricingPolicy());
    }

    /**
     * @param address
     * @param hours
     * @param partners
     * @param bikes
     * @param depositRate
     * @param valuationPolicy
     * @param pricingPolicy
     * @return
     */
    public Shop(Location address, String hours, Set<Shop> partners, Set<Bike> bikes, BigDecimal depositRate,
            ValuationPolicy valuationPolicy, PricingPolicy pricingPolicy) {
        this.id = UUID.randomUUID();
        this.address = address;
        this.hours = hours;
        this.partners = partners;
        this.bikes = bikes;
        this.depositRate = depositRate;
        this.valuationPolicy = valuationPolicy;
        this.pricingPolicy = pricingPolicy;
    }

    /**
     * @param s
     * @return boolean
     */
    public boolean auth(String s) {
        return true;
    }

    /**
     * @param s
     */
    public void addPartner(Shop s) {
        this.partners.add(s);
    }

    /**
     * @param bikeType
     * @return Bike
     */
    public Bike addBike(BikeType bikeType) {
        return this.addBike(bikeType, LocalDate.now(), "");
    }

    /**
     * @param bikeType
     * @param manifactureDate
     * @param notes
     * @return Bike
     */
    public Bike addBike(BikeType bikeType, LocalDate manifactureDate, String notes) {
        Bike bike = new Bike(bikeType, this, manifactureDate, notes);
        this.bikes.add(bike);
        return bike;
    }

    /**
     * @param bikeList
     * @param startDate
     * @return BigDecimal
     */
    public BigDecimal generateDeposit(Collection<Bike> bikeList, LocalDate startDate) {
        BigDecimal ret = new BigDecimal(0);
        for (Bike b : bikeList) {
            BigDecimal calc = valuationPolicy.calculateValue(b, startDate);
            ret = ret.add(calc);
        }
        BigDecimal res = ret.multiply(this.depositRate);
        return res;

    }

    /**
     * @param dates
     * @param bikes
     * @return Collection<Bike>
     */
    public Collection<Bike> getBikes(DateRange dates, Map<BikeType, Integer> bikes) {
        assert (dates.isInFuture());
        Map<BikeType, Integer> copiedBikes = new HashMap<BikeType, Integer>();
        for (BikeType bt : bikes.keySet()) {
            copiedBikes.put(bt, new Integer(bikes.get(bt)));
        }
        Collection<Bike> ret = new HashSet<Bike>();
        for (Bike b : this.bikes) {
            if (b.isAvailable(dates)) {
                BikeType bt = b.getType();
                int needed = copiedBikes.getOrDefault(bt, 0);
                if (needed > 0) {
                    ret.add(b);
                    copiedBikes.put(bt, needed - 1);
                    copiedBikes.remove(bt, 0);
                }
            }
        }
        if (copiedBikes.isEmpty()) {
            return ret;
        } else {
            return null;
        }
    }

    /**
     * @return UUID
     */
    public UUID getId() {
        return this.id;
    }

    /**
     * @return Location
     */
    public Location getAddress() {
        return this.address;
    }

    /**
     * @return String
     */
    public String getHours() {
        return this.hours;
    }

    /**
     * @return BigDecimal
     */
    public BigDecimal getDepositRate() {
        return this.depositRate;
    }

    /**
     * @return Set<Shop>
     */
    public Set<Shop> getPartners() {
        return this.partners;
    }

    /**
     * @return Set<Bike>
     */
    public Set<Bike> getBikes() {
        return this.bikes;
    }

    /**
     * @return PricingPolicy
     */
    public PricingPolicy getPricingPolicy() {
        return this.pricingPolicy;
    }

    // @Override
    // public String toString() {
    // return "{" +
    // " id='" + getId() + "'" +
    // ", address='" + getAddress() + "'" +
    // ", hours='" + getHours() + "'" +
    // ", partners='" + getPartners() + "'" +
    // ", bikes='" + getBikes() + "'" +
    // "}";
    // }
}