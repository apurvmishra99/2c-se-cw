package uk.ac.ed.bikerental;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class SystemTests {

    MockDeliveryService deliveryService;

    // Utils definitions
    LocalDate datePast;
    LocalDate dateNow;
    LocalDate dateFuture;

    DateRange dateRange1;
    DateRange dateRange2;
    DateRange dateRange3;
    DateRange dateRangePast;

    Location customerLocation;
    Location locEH1;
    Location locEH2;
    Location locCAG;
    Location locEH3;
    Location locEH4;

    // System controller
    Controller controller;

    // BikeTypes
    BikeType bikeType1;
    BikeType bikeType2;
    BikeType bikeType3;
    BikeType bikeType4;
    BikeType bikeType5;

    // Bikes
    Bike bike1;
    Bike bike2;
    Bike bike3;
    Bike bike4;
    Bike bike5;
    Bike bike6;
    Bike bike7;
    Bike bike8;
    Bike bike9;
    Bike bike10;
    Bike bike11;
    Bike bike12;
    Bike bike13;

    // Bike rental shops
    Shop shop1;
    Shop shop2;
    Shop shop3;
    Shop shop4;
    Shop shop5;

    // Set<Shop> providers = new HashSet<>();

    // // COLLECTION OF BIKES
    // HashSet<Bike> bikeshop1;
    // HashSet<Bike> bikeshop2;
    // HashSet<Bike> bikeshop3;
    // HashSet<Bike> bikeshop4;
    // HashSet<Bike> bikeshop5;

    // BOOKINGS
    Booking l1;
    Booking l2;
    Booking l3;

    // BOOKING IDS
    UUID id1;
    UUID id2;
    UUID id3;

    // EXPECTED QUOTES
    Set<Quote> expectedQuotes;

    Location loc;
    HashMap<BikeType, Integer> bikesRequested;

    /**
     * The state is reset before each test.
     */
    @BeforeEach
    void setUp() throws Exception {
        DeliveryServiceFactory.setupMockDeliveryService();
        deliveryService = (MockDeliveryService) DeliveryServiceFactory.getDeliveryService();
        // MockDeliveryService deliveryService = (MockDeliveryService) deliveryService;

        // Dates
        datePast = LocalDate.of(2017, 1, 7);
        dateNow = LocalDate.now();
        dateFuture = LocalDate.of(2022, 5, 1);

        // DateRanges
        dateRange1 = new DateRange(LocalDate.of(2020, 1, 8), LocalDate.of(2020, 1, 9));
        dateRange2 = new DateRange(LocalDate.of(2020, 1, 7), LocalDate.of(2020, 2, 10));
        dateRange3 = new DateRange(LocalDate.of(2021, 1, 7), LocalDate.of(2021, 1, 10));
        dateRangePast = new DateRange(datePast, dateFuture);

        // Locations
        customerLocation = new Location("EH8 8KK", "100111 Informatics Forum");
        locEH1 = new Location("EH7 4EG", "38 Haddington Place");
        locEH2 = new Location("EH3 9LW", "15 Leven Terrace");
        locCAG = new Location("CA3 1LW", "Cagliariiii");
        locEH3 = new Location("eh1 1jh", "Somewhere in Edi");
        locEH4 = new Location("eh4 5th", "30 Collegiate, Edinburgh");

        controller = new Controller();

        // Add bike types
        bikeType1 = controller.addBikeType("Mountain", new BigDecimal(500), new BigDecimal(0.1));
        bikeType2 = controller.addBikeType("Commercial", new BigDecimal(150), new BigDecimal(0.1));
        bikeType3 = controller.addBikeType("Stunt", new BigDecimal(250), new BigDecimal(0.2));
        bikeType4 = controller.addBikeType("BT1", new BigDecimal(20), new BigDecimal(0.5));
        bikeType5 = controller.addBikeType("BTT", new BigDecimal(40), new BigDecimal(0.75));

        // Initialise Shop2 -- owns 2xType1 + 1xType2
        shop1 = controller.addShop(locEH1, "", new HashSet<Shop>(), new HashSet<Bike>(), new BigDecimal(0.4),
                new LinearDepreciationPolicy(), new MultidayPricingPolicy());
        controller.login(shop1, "");
        bike1 = controller.addBike(bikeType1);
        bike2 = controller.addBike(bikeType2, datePast, "We love SE");
        bike3 = controller.addBike(bikeType1);
        // Setting the daily prices
        controller.setDailyPrice(bikeType1, new BigDecimal(10));
        controller.setDailyPrice(bikeType2, new BigDecimal(20));
        // Setting the discount rates
        controller.addDiscountRates(1, new BigDecimal(0));
        controller.addDiscountRates(3, new BigDecimal(0.05));
        controller.addDiscountRates(7, new BigDecimal(0.10));
        controller.addDiscountRates(14, new BigDecimal(0.15));

        // Initialise Shop2 -- owns 2xType2 + 1xType4
        shop2 = controller.addShop(locEH2, "", new HashSet<Shop>(), new HashSet<Bike>(), new BigDecimal(0.1),
                new DoubleDecliningPolicy(), new MultidayPricingPolicy());
        controller.login(shop2, "");
        bike4 = controller.addBike(bikeType2);
        bike5 = controller.addBike(bikeType4, dateNow, "We love SE");
        bike6 = controller.addBike(bikeType2);
        controller.setDailyPrice(bikeType2, new BigDecimal(20));
        controller.setDailyPrice(bikeType4, new BigDecimal(30));
        // Setting the discount rates
        controller.addDiscountRates(1, new BigDecimal(0));
        controller.addDiscountRates(3, new BigDecimal(0.05));
        controller.addDiscountRates(7, new BigDecimal(0.10));
        controller.addDiscountRates(14, new BigDecimal(0.15));

        // Initialise Shop3 -- owns 2xType1 + 1xType4
        shop3 = controller.addShop(locCAG, "", new HashSet<Shop>(), new HashSet<Bike>(), new BigDecimal(0.3));
        controller.login(shop3, "");
        bike7 = controller.addBike(bikeType1);
        bike8 = controller.addBike(bikeType4);
        bike9 = controller.addBike(bikeType1);
        controller.setDailyPrice(bikeType1, new BigDecimal(30));
        controller.setDailyPrice(bikeType4, new BigDecimal(40));
        // Setting the discount rates
        controller.addDiscountRates(1, new BigDecimal(0));
        controller.addDiscountRates(3, new BigDecimal(0.05));
        controller.addDiscountRates(7, new BigDecimal(0.10));
        controller.addDiscountRates(14, new BigDecimal(0.15));

        // Initialise Shop4 -- owns 3xType1
        shop4 = controller.addShop(locEH3, "", new HashSet<Shop>(), new HashSet<Bike>(), new BigDecimal(0.2));
        controller.login(shop4, "");
        bike10 = controller.addBike(bikeType1);
        bike11 = controller.addBike(bikeType1);
        bike12 = controller.addBike(bikeType1);
        controller.setDailyPrice(bikeType1, new BigDecimal(40));
        // Setting the discount rates
        controller.addDiscountRates(1, new BigDecimal(0));
        controller.addDiscountRates(3, new BigDecimal(0.05));
        controller.addDiscountRates(7, new BigDecimal(0.10));
        controller.addDiscountRates(14, new BigDecimal(0.15));
        
        shop1.addPartner(shop2);
        shop2.addPartner(shop1);
        shop4.addPartner(shop5);
        // s5.addPartner(s4);
    }

    /**
     * Tries to find quotes;
     * Normal action, no edge cases;
     * Covers location as a shop (3) has bikes available but not closeby
     * @asserts Quotes returned are as expected,
     */
    @Test
    void findingAQuote_normal() {
        // User requires 2xType1
        Map<BikeType, Integer> requestedBikes = new HashMap<BikeType, Integer>();
        requestedBikes.put(bikeType1, 2);

        // Expected object:
        Collection<Bike> expectedBikeshop1 = new HashSet<Bike>();
        expectedBikeshop1.add(bike1);
        expectedBikeshop1.add(bike3);
        Quote q1 = new Quote(new BigDecimal(20), new BigDecimal(400), dateRange1, customerLocation, shop1,
                expectedBikeshop1);

        Collection<Bike> expectedBikeshop2 = new HashSet<Bike>();
        expectedBikeshop2.add(bike7);
        expectedBikeshop2.add(bike9);
        Quote q2 = new Quote(new BigDecimal(80), new BigDecimal(200), dateRange1, customerLocation, shop1,
                expectedBikeshop1);

        Collection<Quote> expectedQuotes = new HashSet<Quote>();
        expectedQuotes.add(q1);
        expectedQuotes.add(q2);

        // Call method Quote()
        Collection<Quote> actualQuotes = controller.getQuotes(requestedBikes, dateRange1, customerLocation);
        assertEquals(expectedQuotes, actualQuotes);
    }

    /**
     * Tries to find quotes;
     * Normal action, no edge cases;
     * Covers location as a shop (3) has bikes available but not closeby
     * @asserts Quotes is empty
     */
    @Test
    void findingAQuote_noneAvailable() {
        // User requires 2xType1
        Map<BikeType, Integer> requestedBikes = new HashMap<BikeType, Integer>();
        requestedBikes.put(bikeType1, 5);

        // Expected object:
        Collection<Bike> expectedBikeshop1 = new HashSet<Bike>();
        expectedBikeshop1.add(bike1);
        expectedBikeshop1.add(bike3);
        Quote q1 = new Quote(new BigDecimal(20), new BigDecimal(400), dateRange1, customerLocation, shop1,
                expectedBikeshop1);

        Collection<Bike> expectedBikeshop2 = new HashSet<Bike>();
        expectedBikeshop2.add(bike7);
        expectedBikeshop2.add(bike9);
        Quote q2 = new Quote(new BigDecimal(80), new BigDecimal(200), dateRange1, customerLocation, shop1,
                expectedBikeshop1);

        Collection<Quote> expectedQuotes = new HashSet<Quote>();

        // Call method Quote()
        Collection<Quote> actualQuotes = controller.getQuotes(requestedBikes, dateRange1, customerLocation);
        assertEquals(expectedQuotes, actualQuotes);
    }

    /**
     * Tries to find quotes;
     * @asserts Error is thrown as expected,
     */
    @Test
    void findingAQuote_pastDate() {
        // User requires 2xType1
        Map<BikeType, Integer> requestedBikes = new HashMap<BikeType, Integer>();
        requestedBikes.put(bikeType1, 2);

        // Call method Quote() with a past DateRange
        // Trying to book it twice
        assertThrows(Error.class, () -> {
            controller.getQuotes(requestedBikes, dateRangePast, customerLocation);
        });
    }

    /**
     * Tries to book an available quote WITHOUT delivery;
     * Normal action, no edge cases;
     * @asserts Booking object is populated as expected,
     * @asserts Bikes are not available during those dates.
     */
    @Test
    void bookingAQuote_pickup() {

        Map<BikeType, Integer> requestedBikes = new HashMap<BikeType, Integer>();
        requestedBikes.put(bikeType1, 2);
        PickupMethod method = PickupMethod.PICKUP;
        // Expected object:
        Collection<Bike> expectedBikeshop1 = new HashSet<Bike>();
        expectedBikeshop1.add(bike1);
        expectedBikeshop1.add(bike3);

        // Quote selected by the customer
        Quote selectedQuote = new Quote(new BigDecimal(20), new BigDecimal(400), dateRange1, customerLocation, shop1,
                expectedBikeshop1);

        // Calling bookQuote()
        Invoice actualBooking = controller.bookQuote(selectedQuote, method);

        // Expected Invoice()
        Invoice expectedInvoice = new Invoice(selectedQuote);

        assertEquals(actualBooking, expectedInvoice);

        // Booking object to access bikes
        Booking l = new Booking(actualBooking);
        for (Bike b : l.getBikes()) {
            assertFalse(b.isAvailable(dateRange1));
        }
    }

    /**
     * Tries to book an available quote WITH Delivery;
     * Normal action, no edge cases;
     * @asserts Booking object is populated as expected,
     * @asserts Bikes are not available during those dates.
     */
    @Test
    void bookingAQuote_delivery() {

        Map<BikeType, Integer> requestedBikes = new HashMap<BikeType, Integer>();
        requestedBikes.put(bikeType1, 2);
        PickupMethod method = PickupMethod.DELIVERY;
        // Expected object:
        Collection<Bike> expectedBikeshop1 = new HashSet<Bike>();
        expectedBikeshop1.add(bike1);
        expectedBikeshop1.add(bike3);

        // Quote selected by the customer
        Quote selectedQuote = new Quote(new BigDecimal(20), new BigDecimal(400), dateRange1, customerLocation, shop1,
                expectedBikeshop1);

        // Calling bookQuote()
        Invoice actualBooking = controller.bookQuote(selectedQuote, method);

        // Expected Invoice()
        Invoice expectedInvoice = new Invoice(selectedQuote);

        assertEquals(actualBooking, expectedInvoice);

        // Booking object to access bikes
        Booking booking = new Booking(actualBooking);
        for (Bike b : booking.getBikes()) {
            assertFalse(b.isAvailable(dateRange1));
        }

        // Test delivery
        LocalDate deliveryDate = actualBooking.getDates().getStart();
        Collection<Deliverable> pickups;
        pickups = deliveryService.getPickupsOn(deliveryDate);
        assertTrue(Collections.frequency(pickups, booking) == 1);

        // DELIVERY status is same for delivery to customer or shop
        deliveryService.carryOutPickups(deliveryDate);
        for (Bike b : booking.getBikes()) {
            assertEquals(BikeStatus.DELIVERY, b.getStatus());
        }

        deliveryService.carryOutDropoffs();
        for (Bike b : booking.getBikes()) {
            assertEquals(BikeStatus.ONLOAN, b.getStatus());
        }
    }

    /**
     * Tries to book a quote twice;
     * Simulates two customers receiving the same quote;
     * @asserts Error is thrown as expected.
     */
    @Test
    void bookingAQuote_concurrent() {

        Map<BikeType, Integer> requestedBikes = new HashMap<BikeType, Integer>();
        requestedBikes.put(bikeType1, 2);
        PickupMethod method = PickupMethod.DELIVERY;
        // Expected object:
        Collection<Bike> expectedBikeshop1 = new HashSet<Bike>();
        expectedBikeshop1.add(bike1);
        expectedBikeshop1.add(bike3);

        // Quote selected by the customer
        Quote selectedQuote = new Quote(new BigDecimal(20), new BigDecimal(400), dateRange1, customerLocation, shop1,
                expectedBikeshop1);

        // Calling bookQuote() once
        controller.bookQuote(selectedQuote, method);

        // Trying to book it twice
        assertThrows(Error.class, () -> {
            controller.bookQuote(selectedQuote, method);
        });
    }

    /**
     * Tries to return bikes to the same provider;
     * Normal action, no edge cases;
     * @assert Bikes are correctly setup available
     */
    @Test
    public void returningBikes_sameShop() {
        controller.login(shop1, "");
        Map<BikeType, Integer> requestedBikes = new HashMap<BikeType, Integer>();
        requestedBikes.put(bikeType1, 2);
        PickupMethod method = PickupMethod.DELIVERY;

        // Bikes user rented:
        Collection<Bike> expectedBikeshop1 = new HashSet<Bike>();
        expectedBikeshop1.add(bike1);
        expectedBikeshop1.add(bike3);

        // Quote which was selected by the customer
        Quote selectedQuote = new Quote(new BigDecimal(20), new BigDecimal(400), dateRange1, customerLocation, shop1,
                expectedBikeshop1);

        // Invoice which was generated
        Invoice actualBooking = controller.bookQuote(selectedQuote, method);

        // Creating a booking object
        l1 = new Booking(actualBooking);
        id1 = l1.getId();

        // Collect the booking
        controller.collectBooking(id1);
        // Returning the booking
        controller.returnBooking(id1);

        for (Bike b : l1.getBikes()) {
            assertEquals(BikeStatus.AVAILABLE, b.getStatus());
        }
    }

    /**
     * Tries to return bikes to a partnered provider;
     * Normal action, no edge cases;
     * @assert Bikes are correctly setup available
     * @assert Delivery is as expected
     */
    @Test
    public void returningBikes_partneredShop() {
        controller.login(shop2, "");
        Map<BikeType, Integer> requestedBikes = new HashMap<BikeType, Integer>();
        requestedBikes.put(bikeType1, 2);
        PickupMethod method = PickupMethod.DELIVERY;

        // Bikes user rented:
        Collection<Bike> expectedBikeshop1 = new HashSet<Bike>();
        expectedBikeshop1.add(bike1);
        expectedBikeshop1.add(bike3);

        // Quote which was selected by the customer
        Quote selectedQuote = new Quote(new BigDecimal(20), new BigDecimal(400), dateRange1, customerLocation, shop1,
                expectedBikeshop1);

        // Invoice which was generated
        Invoice actualBooking = controller.bookQuote(selectedQuote, method);

        // Creating a booking object
        l1 = new Booking(actualBooking);
        id1 = l1.getId();

        // Collect the booking
        controller.collectBooking(id1);
        // Returning the booking
        controller.returnBooking(id1);

        // Test delivery
        // LocalDate deliveryDate = actualBooking.getDates().getStart();
        Collection<Deliverable> pickups;
        pickups = deliveryService.getPickupsOn(LocalDate.now());

        // DELIVERY status is same for delivery to customer or shop
        deliveryService.carryOutPickups(LocalDate.now());
        for (Bike b : l1.getBikes()) {
            assertEquals(BikeStatus.DELIVERY, b.getStatus());
        }

        deliveryService.carryOutDropoffs();
        for (Bike b : l1.getBikes()) {
            assertEquals(BikeStatus.AVAILABLE, b.getStatus());
        }
    }

    /**
     * Tries to return bikes;
     * User not logged in;
     * @assert Error is thrown
     */
    @Test
    public void returningBikes_unauthUser() {
        Map<BikeType, Integer> requestedBikes = new HashMap<BikeType, Integer>();
        requestedBikes.put(bikeType1, 2);
        PickupMethod method = PickupMethod.DELIVERY;

        // Bikes user rented:
        Collection<Bike> expectedBikeshop1 = new HashSet<Bike>();
        expectedBikeshop1.add(bike1);
        expectedBikeshop1.add(bike3);

        // Quote which was selected by the customer
        Quote selectedQuote = new Quote(new BigDecimal(20), new BigDecimal(400), dateRange1, customerLocation, shop1,
                expectedBikeshop1);


        // Invoice which was generated
        Invoice actualBooking = controller.bookQuote(selectedQuote, method);

        // Creating a booking object
        l1 = new Booking(actualBooking);
        id1 = l1.getId();

        // Collect the booking
        controller.collectBooking(id1);
        // Try to return the order
        assertThrows(Error.class, () -> {
            controller.returnBooking(id1);
        });
    }
}
