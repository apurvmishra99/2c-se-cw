package uk.ac.ed.bikerental;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.*;

class LocationTest {

    Location loc1, loc2, loc3, loc4;

    /**
     * @throws Exception
     */
    @BeforeEach
    void setUp() throws Exception {
        loc1 = new Location("EH7 4EG", "38 Haddington Place");
        loc2 = new Location("EH3 9LW", "15 Leven Terrace");
        loc3 = new Location("CA3 1LW", "Cagliariiii");
        loc4 = new Location("eh1 1jh", "Somewhere in Edi");
    }

    // Check loc1 is near loc2 (expect True as both 'EH')
    @Test
    void isNearTo_true() {
        boolean loc1loc2 = loc1.isNearTo(loc2);
        assertTrue(loc1loc2);
    }

    // Check loc1 is near loc2 (expect True as both 'EH') (expect case insensitive)
    @Test
    void isNearTo_symmetric() {
        boolean loc1loc2 = loc1.isNearTo(loc2);
        boolean loc2loc1 = loc2.isNearTo(loc1);
        assertEquals(loc1loc2, loc2loc1);
    }

    // Check loc1 is near loc2 (expect True as both 'EH') (expect case insensitive)
    @Test
    void isNearTo_caseInsensitive() {
        boolean loc1loc4 = loc1.isNearTo(loc4);
        assertTrue(loc1loc4);
    }

    // Test loc1 is near loc3 (expect False)
    @Test
    void isNearTo_false() {
        boolean loc1loc3 = loc1.isNearTo(loc3);
        assertFalse(loc1loc3);
    }
}
