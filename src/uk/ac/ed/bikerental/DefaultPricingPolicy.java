package uk.ac.ed.bikerental;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;


class DefaultPricingPolicy implements PricingPolicy {

    private Map<BikeType, BigDecimal> dailyPrices = new HashMap<BikeType, BigDecimal>();

    
    /** 
     * @param bikeType
     * @param dailyPrice
     */
    public void setDailyRentalPrice(BikeType bikeType, BigDecimal dailyPrice){
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
     * @param bikes
     * @param duration
     * @return BigDecimal
     */
    public BigDecimal calculatePrice(Collection<Bike> bikes, DateRange duration){
        BigDecimal tot = new BigDecimal(0);
        System.out.println(dailyPrices.entrySet());
        System.out.println(bikes);
        for(Bike b : bikes){
            BigDecimal d = dailyPrices.get(b.getType());
            tot = tot.add(d);
            System.out.println("Partial sum " + tot.stripTrailingZeros());
        }
        BigDecimal range = new BigDecimal (duration.toDays());
        System.out.println("Returning range " + range.stripTrailingZeros());
        return tot.multiply(range);
    }
}
