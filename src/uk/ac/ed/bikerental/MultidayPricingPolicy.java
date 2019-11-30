package uk.ac.ed.bikerental;

import java.math.BigDecimal;
import java.util.Map.Entry;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

class MultidayPricingPolicy implements PricingPolicy {

    private Map<BikeType, BigDecimal> dailyPrices = new HashMap<BikeType, BigDecimal>();
    private TreeMap<Integer, BigDecimal> discountRates = new TreeMap<Integer, BigDecimal>();
    
    /**
     * @param bikeType
     * @param dailyPrice
     */
    public void setDailyRentalPrice(BikeType bikeType, BigDecimal dailyPrice) {
        dailyPrices.put(bikeType, dailyPrice);
    }

    /**
     * @param bikeType
     * @return BigDecimal
     */
    public BigDecimal getDailyPrice(BikeType bikeType) {
        return dailyPrices.get(bikeType);
    }
    
    /**
     * 
     * @param fromDay
     * @param discount
     */
    public void addDiscountRate(int fromDay, BigDecimal discount) {
        discountRates.put(fromDay, discount);
    }

    /**
     * @param bikes
     * @param duration
     * @return BigDecimal
     */
    public BigDecimal calculatePrice(Collection<Bike> bikes, DateRange duration) {
        BigDecimal totalPerDay = new BigDecimal(0.0);

        for (Bike bike : bikes) {
            BigDecimal dailyPrice = this.dailyPrices.get(bike.getType());
            totalPerDay = totalPerDay.add(dailyPrice);
        }

        BigDecimal numDays = new BigDecimal(duration.toDays());

        BigDecimal total = totalPerDay.multiply(numDays);

        BigDecimal discount = this.discount(duration);

        BigDecimal one = new BigDecimal(1);

        // total after discount = total * (1 - discount rate)
        BigDecimal totalAfterDiscount = total.multiply(one.subtract(discount));

        return totalAfterDiscount;
    }

    /**
     * @param duration
     * @return BigDecimal
     */
    public BigDecimal discount(DateRange duration) {
        int numDays = (int) duration.toDays();
        
        Entry<Integer, BigDecimal> treeEntry = discountRates.floorEntry(numDays);
        if (treeEntry != null && treeEntry.getValue() == null) {
            treeEntry = discountRates.lowerEntry(numDays);
        }
                
        return treeEntry == null ? new BigDecimal(0) : treeEntry.getValue();

    }
}