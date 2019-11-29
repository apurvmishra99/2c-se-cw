package uk.ac.ed.bikerental;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.*;
import java.math.BigDecimal;
import java.math.*;

import java.beans.Transient;
import java.time.LocalDate;
import java.time.Month;
class TestDoubleDecliningPolicy{
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
        this.type1 = new BikeType("road",a,a1,new BigDecimal(100)); // set default daily price to 100
        this.type2 = new BikeType("mountain",b,b1,new BigDecimal(100));
         this.date1 = LocalDate.of(2014,Month.MARCH,25);
        this.date2 = LocalDate.of(2010,Month.SEPTEMBER,15);
        this.date3 = LocalDate.of(2019,Month.NOVEMBER,23);
        this.bike1 = new Bike(date1,type1);
        this.bike2 = new Bike(date2,type2);
    }

    @Test
    void calculateValue1(){
        DoubleDecliningPolicy pol1 = new DoubleDecliningPolicy();
        BigDecimal d = pol1.calculateValue(bike2, date3).stripTrailingZeros();
        MathContext m = new MathContext(9);
        d = d.round(m);
        assertEquals(new BigDecimal(12.07959552).round(m),d);
    }

   @Test
   void calculateValue2(){
    DoubleDecliningPolicy pol1 = new DoubleDecliningPolicy();
    BigDecimal d = pol1.calculateValue(bike1, date3).stripTrailingZeros();
    MathContext m = new MathContext(5);
    d = d.round(m);
    assertEquals(new BigDecimal(39.3216).round(m),d);
   } 
}