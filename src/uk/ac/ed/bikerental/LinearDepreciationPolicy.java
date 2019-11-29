package uk.ac.ed.bikerental;

import java.math.BigDecimal;
import java.time.LocalDate;

public class LinearDepreciationPolicy implements ValuationPolicy {
    public BigDecimal calculateValue(Bike bike, LocalDate date) {
    DateRange dates = new DateRange(bike.getManufactureDate(),date);
	BigDecimal age =new BigDecimal(dates.toYears());
	BikeType type = bike.getType();


	BigDecimal deprAmount = age.multiply(type.getDepreciationRate()); //each bike type has certain deprecation rate

	assert deprAmount.compareTo(BigDecimal.ONE) < 0; // if deposit amount would be <= 0 
	
	return (BigDecimal.ONE.subtract(deprAmount)).multiply(type.getValue());
    }
}
    
