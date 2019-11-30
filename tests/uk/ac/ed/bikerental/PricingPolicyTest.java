package uk.ac.ed.bikerental;

import static org.junit.jupiter.api.Assertions.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;

import org.junit.jupiter.api.*;

public class PricingPolicyTest {
    // You can add attributes here
    MultidayPricingPolicy tpp;
    Shop s1;
    BikeType bt1;
    Bike b1;
    BikeType bt2;
    Bike b2;
    ArrayList<Bike> arr;
    BigDecimal bg;
    DateRange dateRange1;
    DateRange dateRange2;

    
    /** 
     * @throws Exception
     */
    @BeforeEach
    void setUp() throws Exception {
        tpp = new MultidayPricingPolicy();
        bt1 = new BikeType("Mountain", new BigDecimal(75), new BigDecimal(85));
        b1 = new Bike(bt1, s1);
        bt2 = new BikeType("Commercial", new BigDecimal(85), new BigDecimal(85));
        b2 = new Bike(bt2, s1);
        this.dateRange1 = new DateRange(LocalDate.of(2019, 1, 8), LocalDate.of(2019, 1, 10));
        this.dateRange2 = new DateRange(LocalDate.of(2019, 1, 7), LocalDate.of(2019, 1, 10));
        tpp.setDailyRentalPrice(bt1, new BigDecimal(500.0));
        tpp.setDailyRentalPrice(bt2, new BigDecimal(400.0));
        arr = new ArrayList<>();
        arr.add(b1);
        arr.add(b2);
    }

    @Test
    void testsetRentalPrice() {
        tpp.setDailyRentalPrice(bt1, new BigDecimal(120.0));
        assertEquals(bt1.getReplacementValue(), new BigDecimal(75.0));
    }

    @Test
    void testCalculatePrice() {
        BigDecimal bgg = tpp.calculatePrice(arr, dateRange1);
        assertEquals(bgg, new BigDecimal(1800.0));
    }

    @Test
    void testDiscount() {
        assertEquals(new BigDecimal(0.00), tpp.discount(dateRange1));
    }

    @Test
    void testDiscountCal() {
        BigDecimal bgg = tpp.calculatePrice(arr, dateRange2);
        BigDecimal dis = new BigDecimal(0.05);
        BigDecimal one = new BigDecimal(1.0);
        BigDecimal rent1 = b1.getType().getReplacementValue();
        BigDecimal rent2 = b2.getType().getReplacementValue();
        BigDecimal range = new BigDecimal(dateRange2.toDays());
        BigDecimal sum1 = rent1.multiply(range);
        BigDecimal sum2 = rent2.multiply(range);
        BigDecimal sum = sum1.add(sum2);
        BigDecimal totdiscount = one.subtract(dis);
        BigDecimal total = sum.multiply(totdiscount);
        assertEquals(total, bgg);
    }
}
