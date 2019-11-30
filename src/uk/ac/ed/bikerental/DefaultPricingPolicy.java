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
     * @param bikes
     * @param duration
     * @return BigDecimal
     */
    public BigDecimal calculatePrice(Collection<Bike> bikes, DateRange duration) {
        BigDecimal tot = new BigDecimal(0);

        for (Bike b : bikes) {
            BigDecimal d = dailyPrices.get(b.getType());
            tot = tot.add(d);

        }
        BigDecimal range = new BigDecimal(duration.toDays());

        return tot.multiply(range);
    }
}
