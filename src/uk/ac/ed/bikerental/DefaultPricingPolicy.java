package uk.ac.ed.bikerental;

import java.math.BigDecimal;
import java.util.Collection;


class DefaultPricingPolicy implements PricingPolicy {

    // public TestPricingPolicy() {};

    public void setDailyRentalPrice(BikeType bikeType, BigDecimal dailyPrice){
        bikeType.setDailyPrice(dailyPrice);
    }

    public BigDecimal calculatePrice(Collection<Bike> bikes, DateRange duration){
        BigDecimal tot = new BigDecimal(0);
        for(Bike b : bikes){
            BigDecimal d = b.getType().getDailyPrice();
            BigDecimal range = new BigDecimal (duration.toDays());
            BigDecimal sum = d.multiply(range);
            tot = tot.add(sum);
        }
        return tot;
    }
}
