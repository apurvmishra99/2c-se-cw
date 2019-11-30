package uk.ac.ed.bikerental;

import java.math.BigDecimal;
import java.time.LocalDate;

public class DefaultValuationPolicy implements ValuationPolicy {

    /**
     * @param bike
     * @param date
     * @return BigDecimal
     */
    public BigDecimal calculateValue(Bike bike, LocalDate date) {
        return bike.getType().getReplacementValue();
    }
}
