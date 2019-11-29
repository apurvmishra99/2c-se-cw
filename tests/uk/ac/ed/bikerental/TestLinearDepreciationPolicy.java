package uk.ac.ed.bikerental;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.*;
import java.math.BigDecimal;
import java.math.*;

import java.beans.Transient;
import java.time.LocalDate;
import java.time.Month;
class TestLinearDepreciationPolicy {
    private Bike bike1,bike2;
    private BikeType type1,type2;
    private LocalDate date1,date2,date3;

    @BeforeEach
    void setUp() throws Exception {
        // Setup resources before each test
        BigDecimal a = new BigDecimal(120);
        BigDecimal a1 = new BigDecimal(0.1);
        BigDecimal b = new BigDecimal(90);
        BigDecimal b1 = new BigDecimal(0.1);
        this.type1 = new BikeType("road",a,a1,new  BigDecimal(100));
        this.type2 = new BikeType("mountain",b,b1,new BigDecimal(100));
         this.date1 = LocalDate.of(2014,Month.MARCH,25);
        this.date2 = LocalDate.of(2010,Month.SEPTEMBER,15);
        this.date3 = LocalDate.of(2019,Month.NOVEMBER,23);
        this.bike1 = new Bike(date1,type1);
        this.bike2 = new Bike(date2,type2);
    }

    @Test
    void calculateValue1(){
        LinearDepreciationPolicy pol = new LinearDepreciationPolicy();
        // get a big decimal representing the deposit amount..i string trailing zeroes as they are not needed
        BigDecimal d = pol.calculateValue(bike1, this.date3).stripTrailingZeros();
        // create a math context object to use it for rounding bigdecimal to 1 digit..it is one digit as that is the precision of the depreciation rate i am using
        MathContext m = new MathContext(1); 
        d = d.round(m);
       // compare what i get with what i am supposed to get
        assertEquals(new BigDecimal(60.0).stripTrailingZeros().stripTrailingZeros(),d);
    }
    
    // second test is the same thing but testing it with a differet bike object
    @Test
       void calculateValue2(){
        LinearDepreciationPolicy pol1 = new LinearDepreciationPolicy();
        BigDecimal d = pol1.calculateValue(bike2, date3).stripTrailingZeros();
        MathContext m = new MathContext(1);
        d = d.round(m);
        assertEquals(new BigDecimal(9.0).stripTrailingZeros(),d);
      }
// }
}