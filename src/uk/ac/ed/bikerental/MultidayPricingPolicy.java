package uk.ac.ed.bikerental;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;


class MultidayPricingPolicy implements PricingPolicy {

    private Map<BikeType, BigDecimal> dailyPrices = new HashMap<BikeType, BigDecimal>();

    public void setDailyRentalPrice(BikeType bikeType, BigDecimal dailyPrice){
        dailyPrices.put(bikeType, dailyPrice);
    }

    public BigDecimal getDailyPrice(BikeType bikeType) {
        return dailyPrices.get(bikeType);
    }

    public BigDecimal calculatePrice(Collection<Bike> bikes, DateRange duration){
        BigDecimal tot = new BigDecimal(0.0);
        for(Bike b : bikes){
            BigDecimal d = dailyPrices.get(b.getType());
            BigDecimal range = new BigDecimal ( duration.toDays());
            BigDecimal sum = d.multiply(range);
            tot = tot.add(sum);
        }
        BigDecimal discount = discount(duration);
        BigDecimal one = new BigDecimal(1);
        BigDecimal totdiscount = one.subtract(discount);
        tot= tot.multiply(totdiscount);
        return tot;
    }

    public BigDecimal discount(DateRange duration){
        long days = duration.toDays();
        if(days<3) return new BigDecimal(0.0);
        else if(days>2 && days < 7) return new BigDecimal((double)5/100);
        else if (days>6 && days < 14) return new BigDecimal(0.01);
        else return new BigDecimal(0.15);
    }
}
