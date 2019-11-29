package uk.ac.ed.bikerental;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;


class DefaultPricingPolicy implements PricingPolicy {

    private Map<BikeType, BigDecimal> dailyPrices = new HashMap<BikeType, BigDecimal>();

    public void setDailyRentalPrice(BikeType bikeType, BigDecimal dailyPrice){
        // System.out.println("Adding price for BT: " + dailyPrice);
        dailyPrices.put(bikeType, dailyPrice);
    }

    public BigDecimal getDailyPrice(BikeType bikeType) {
        return dailyPrices.get(bikeType);
    }
    public BigDecimal calculatePrice(Collection<Bike> bikes, DateRange duration){
        BigDecimal tot = new BigDecimal(0);
        System.out.println(bikes);
        for(Bike b : bikes){
            BigDecimal d = dailyPrices.get(b.getType());
            tot = tot.add(d);

        }
        BigDecimal range = new BigDecimal (duration.toDays());
        System.out.println("Returning range " + range.stripTrailingZeros());
        return tot.multiply(range);
    }
}
