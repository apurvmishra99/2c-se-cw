package uk.ac.ed.bikerental;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.*;
import java.math.BigDecimal;
import java.math.*;

import java.time.LocalDate;
import java.util.HashSet;

class TestLinearDepreciationPolicy {
    Bike bike1, bike2;
    BikeType type1, type2;
    LocalDate date1, date2, date3;
    Shop s1, s2, s3;
    Location l1, l2, l3;

    /**
     * @throws Exception
     */
    @BeforeEach
    void setUp() throws Exception {
        // Setup resources before each test
        // BigDecimal a = new BigDecimal(120);
        // BigDecimal a1 = new BigDecimal(0.1);
        // BigDecimal b = new BigDecimal(90);
        // BigDecimal b1 = new BigDecimal(0.1);
        l1 = new Location("EH7 4EG", "38 Haddington Place");
        l2 = new Location("EH3 9LW", "15 Leven Terrace");
        l3 = new Location("CA3 1LW", "Cagliari");
        this.type1 = new BikeType("Road", new BigDecimal(120), new BigDecimal(0.1));
        this.type2 = new BikeType("Mountain", new BigDecimal(90), new BigDecimal(0.3));
        this.date1 = LocalDate.of(2022, 3, 25);
        this.date2 = LocalDate.of(2021, 9, 15);
        this.date3 = LocalDate.of(2023, 11, 30);
        this.s1 = new Shop(l1, "", new HashSet<Shop>(), new HashSet<Bike>(), new BigDecimal(0.1),
                new LinearDepreciationPolicy(), new DefaultPricingPolicy());
        this.bike1 = new Bike(this.type1, this.s1);
        this.bike2 = new Bike(this.type2, this.s1);
    }

    @Test
    void calculateValue1() {
        LinearDepreciationPolicy pol = new LinearDepreciationPolicy();
        // Get a big decimal representing the deposit amount(strip trailing zeroes)
        BigDecimal d = pol.calculateValue(bike1, this.date3).stripTrailingZeros();
        // create a math context object to use it for rounding bigdecimal to 1 digit..it
        // is one digit as that is the precision of the depreciation rate i am using
        MathContext m = new MathContext(2);
        d = d.round(m);
        // compare what i get with what i am supposed to get
        assertEquals(new BigDecimal(72).stripTrailingZeros(), d);
    }

    // Testing with a different bike object
    @Test
    void calculateValue2() {
        LinearDepreciationPolicy pol1 = new LinearDepreciationPolicy();
        BigDecimal d = pol1.calculateValue(bike2, date2).stripTrailingZeros();
        MathContext m = new MathContext(2);
        d = d.round(m);
        assertEquals(new BigDecimal(63).stripTrailingZeros(), d);
    }
}