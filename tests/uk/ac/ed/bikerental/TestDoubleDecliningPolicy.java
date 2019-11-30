package uk.ac.ed.bikerental;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.*;
import java.math.BigDecimal;
import java.math.*;

import java.time.LocalDate;
import java.util.HashSet;

class TestDoubleDecliningPolicy {
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
        l1 = new Location("EH7 4EG", "38 Haddington Place");
        l2 = new Location("EH3 9LW", "15 Leven Terrace");
        l3 = new Location("CA3 1LW", "Cagliari");
        this.type1 = new BikeType("Road", new BigDecimal(120), new BigDecimal(0.1));
        this.type2 = new BikeType("Mountain", new BigDecimal(90), new BigDecimal(0.3));
        this.date1 = LocalDate.of(2022, 3, 25);
        this.date2 = LocalDate.of(2021, 9, 15);
        this.date3 = LocalDate.of(2023, 11, 30);
        this.s1 = new Shop(l1, "", new HashSet<Shop>(), new HashSet<Bike>(), new BigDecimal(0.1),
                new DoubleDecliningPolicy(), new DefaultPricingPolicy());
        this.bike1 = new Bike(this.type1, this.s1);
        this.bike2 = new Bike(this.type2, this.s1);
    }

    @Test
    void calculateValue1() {
        DoubleDecliningPolicy pol1 = new DoubleDecliningPolicy();
        BigDecimal d = pol1.calculateValue(bike1, date3).stripTrailingZeros();
        MathContext m = new MathContext(2);
        d = d.round(m);
        assertEquals(new BigDecimal(49.152).round(m), d);
    }

    @Test
    void calculateValue2() {
        DoubleDecliningPolicy pol1 = new DoubleDecliningPolicy();
        BigDecimal d = pol1.calculateValue(bike2, date1).stripTrailingZeros();
        MathContext m = new MathContext(5);
        d = d.round(m);
        assertEquals(new BigDecimal(14.4).round(m), d);
    }
}