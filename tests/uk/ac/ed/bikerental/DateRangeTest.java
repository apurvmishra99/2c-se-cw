package uk.ac.ed.bikerental;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class DateRangeTest {
    private DateRange dateRange1, dateRange2, dateRange3;

    @BeforeEach
    void setUp() throws Exception {
        // Setup resources before each test
        this.dateRange1 = new DateRange(LocalDate.of(2019, 1, 7),
                LocalDate.of(2019, 1, 10));
        this.dateRange2 = new DateRange(LocalDate.of(2019, 1, 5),
                LocalDate.of(2019, 1, 23));
        this.dateRange3 = new DateRange(LocalDate.of(2015, 1, 7),
                LocalDate.of(2018, 1, 10));
    }

    // Test toYears()
    @Test
    void toYears_date1() {
        assertEquals(0, this.dateRange1.toYears());
    }

    // Test toYears()
    @Test
    void toYears_date3() {
        assertEquals(3, this.dateRange3.toYears());
    }

    // Test toDays()
    @Test
    void toDays_date1() {
        assertEquals(3, this.dateRange1.toDays());
    }

    // Test toDays()
    @Test
    void toDays_date3() {
        assertEquals(1099, this.dateRange3.toDays());
    }

    // Test overlaps() -- expect True
    @Test
    void overlaps_true() {
        boolean d1d2 = dateRange1.overlaps(dateRange2);
        assertTrue(d1d2);
    }

    // Test overlap() is symmetric
    @Test
    void overlaps_symmtric() {
        boolean d1d2 = dateRange1.overlaps(dateRange2);
        boolean d2d1 = dateRange2.overlaps(dateRange1);
        assertEquals(d1d2, d2d1);
    }

    // Test overlap() -- expect False
    @Test
    void overlaps_false() {
        boolean d1d3 = dateRange1.overlaps(dateRange3);
        assertFalse(d1d3);
    }
}
