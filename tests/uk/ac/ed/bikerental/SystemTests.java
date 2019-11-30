package uk.ac.ed.bikerental;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class SystemTests {
    Set<Shop> providers = new HashSet<>();
    // Customer cr1 = new Customer("ABCD", "XYZ");
    // Customer cr2 = new Customer("1234", "890");

    // BIKE RENTAL SHOPS
    Shop s1;
    Shop s2;
    Shop s3;
    Shop s4;
    Shop s5;

    // BIKES
    Bike b1;
    Bike b2;
    Bike b3;
    Bike b4;
    Bike b5;
    Bike b6;
    Bike b7;
    Bike b8;
    Bike b9;
    Bike b10;
    Bike b11;
    Bike b12;
    Bike b13;

    BikeType bikeType1;
    BikeType bikeType2;
    BikeType bikeType3;
    BikeType bikeType4;
    BikeType bikeType5;

    // COLLECTION OF BIKES
    HashSet<Bike> bikes1;
    HashSet<Bike> bikes2;
    HashSet<Bike> bikes3;
    HashSet<Bike> bikes4;
    HashSet<Bike> bikes5;

    // DATE RANGES DEFINITION
    DateRange dateRange1;
    DateRange dateRange2;
    DateRange dateRange3;

    LocalDate datePast;
    LocalDate dateNow;
    LocalDate dateFuture;

    Location customerLocation;
    Location loc1;
    Location loc2;
    Location loc3;
    Location loc4;
    Location loc5;

    // BOOKINGS
    Booking l1;
    Booking l2;
    Booking l3;

    // EXPECTED QUOTES
    Set<Quote> expectedQuotes;

    Controller controller;
    Location loc;
    HashMap<BikeType, Integer> bikesRequested;

    @BeforeEach
    void setUp() throws Exception {
        // Mock delivery
        DeliveryServiceFactory.setupMockDeliveryService();

        // Dates
        dateRange1 = new DateRange(LocalDate.of(2020, 1, 8), LocalDate.of(2020, 1, 9));
        dateRange2 = new DateRange(LocalDate.of(2020, 1, 7), LocalDate.of(2020, 2, 10));
        dateRange3 = new DateRange(LocalDate.of(2021, 1, 7), LocalDate.of(2021, 1, 10));
        

        datePast = LocalDate.of(2017, 1, 7);
        dateNow = LocalDate.now();
        dateFuture = LocalDate.of(2022, 5, 1);

        customerLocation = new Location("EH8 8KK", "100111 Informatics Forum");
        loc1 = new Location("EH7 4EG", "38 Haddington Place");
        loc2 = new Location("EH3 9LW", "15 Leven Terrace");
        loc3 = new Location("CA3 1LW", "Cagliariiii");
        loc4 = new Location("eh1 1jh", "Somewhere in Edi");
        loc5 = new Location("eh4 5th", "30 Collegiate, Edinburgh");

        controller = new Controller();

        // Add bike types
        bikeType1 = controller.addBikeType("Mountain", new BigDecimal(500), new BigDecimal(0.1));
        bikeType2 = controller.addBikeType("Commercial", new BigDecimal(150), new BigDecimal(0.1));
        bikeType3 = controller.addBikeType("Stunt", new BigDecimal(250), new BigDecimal(0.2));
        bikeType4 = controller.addBikeType("BT1", new BigDecimal(20), new BigDecimal(0.5));
        bikeType5 = controller.addBikeType("BTT", new BigDecimal(40), new BigDecimal(0.75));

        // Initialise Shop2 -- owns 2xType1 + 1xType2
        s1 = controller.addShop(loc1, "", new HashSet<Shop>(), new HashSet<Bike>(), new BigDecimal(0.4),
                new LinearDepreciationPolicy(), new MultidayPricingPolicy());
        controller.login(s1, "");
        b1 = controller.addBike(bikeType1);
        b2 = controller.addBike(bikeType2, datePast, "We love SE");
        b3 = controller.addBike(bikeType1);
        controller.setDailyPrice(bikeType1, new BigDecimal(10));
        controller.setDailyPrice(bikeType2, new BigDecimal(20));

        // Initialise Shop2 -- owns 2xType2 + 1xType4
        s2 = controller.addShop(loc2, "", new HashSet<Shop>(), new HashSet<Bike>(), new BigDecimal(0.1),
                new DoubleDecliningPolicy(), new DefaultPricingPolicy());
        controller.login(s2, "");
        b4 = controller.addBike(bikeType2);
        b5 = controller.addBike(bikeType4, dateNow, "We love SE");
        b6 = controller.addBike(bikeType2);
        controller.setDailyPrice(bikeType2, new BigDecimal(20));
        controller.setDailyPrice(bikeType4, new BigDecimal(30));

        // Initialise Shop3 -- owns 2xType1 + 1xType4
        s3 = controller.addShop(loc3, "", new HashSet<Shop>(), new HashSet<Bike>(), new BigDecimal(0.3));
        controller.login(s3, "");
        b7 = controller.addBike(bikeType1);
        b8 = controller.addBike(bikeType4);
        b9 = controller.addBike(bikeType1);
        controller.setDailyPrice(bikeType1, new BigDecimal(30));
        controller.setDailyPrice(bikeType4, new BigDecimal(40));

        // Initialise Shop4 -- owns 3xType1
        s4 = controller.addShop(loc4, "", new HashSet<Shop>(), new HashSet<Bike>(), new BigDecimal(0.2));
        controller.login(s4, "");
        b10 = controller.addBike(bikeType1);
        b11 = controller.addBike(bikeType1);
        b12 = controller.addBike(bikeType1);
        controller.setDailyPrice(bikeType1, new BigDecimal(40));

        s1.addPartner(s2);
        s2.addPartner(s1);
        s4.addPartner(s5);
        // s5.addPartner(s4);
    }

    @Test
    void findingAQuote() {

        // First user requires 2xType1
        Map<BikeType, Integer> requestedBikes = new HashMap<BikeType, Integer>();
        requestedBikes.put(bikeType1, 2);

        // Expected object:
        Collection<Bike> expectedBikes1 = new HashSet<Bike>();
        expectedBikes1.add(b1);
        expectedBikes1.add(b3);
        Quote q1 = new Quote(new BigDecimal(20), new BigDecimal(400), dateRange1, customerLocation, s1, expectedBikes1);
        
        Collection<Bike> expectedBikes2 = new HashSet<Bike>();
        expectedBikes2.add(b7);
        expectedBikes2.add(b9);
        Quote q2 = new Quote(new BigDecimal(80), new BigDecimal(200), dateRange1, customerLocation, s1, expectedBikes1);

        Collection<Quote> expectedQuotes = new HashSet<Quote>();
        expectedQuotes.add(q1);
        expectedQuotes.add(q2);
        
        // Call method Quote()
        Collection<Quote> actualQuotes = controller.getQuotes(requestedBikes, dateRange1, customerLocation);
        assertEquals(expectedQuotes, actualQuotes);
    }
    @Test
    void bookingAQuote() {

        
        Map<BikeType, Integer> requestedBikes = new HashMap<BikeType, Integer>();
        requestedBikes.put(bikeType1, 2);
        PickupMethod method = PickupMethod.DELIVERY;
        // Expected object:
        Collection<Bike> expectedBikes1 = new HashSet<Bike>();
        expectedBikes1.add(b1);
        expectedBikes1.add(b3);
    
        // Quote selected by the customer
        Quote selectedQuote = new Quote(new BigDecimal(20), new BigDecimal(400), dateRange1, customerLocation, s1, expectedBikes1);
        
        // Calling bookQuote()
        Invoice actualBooking = controller.bookQuote(selectedQuote, method);
        
        // Expected Invoice()
        Invoice expectedInvoice = new Invoice(selectedQuote);

        assertEquals(actualBooking, expectedInvoice);
    //     assertEquals(booking.getShop().getName(), "Shop1");

    //     assertEquals(booking.getDeposit(), 20.0);
    //     assertEquals(booking.getTotalPrice(), 35.0);

    //     assertEquals(booking.getBikes().get(0).getType().getTypeName(), "Mountain Bike");
    //     assertEquals(booking.getBikes().get(1).getType().getTypeName(), "Road Bike");

    //     // Checking if the status of the bikes has been changed or not
    //     assertEquals(booking.getBikes().get(0).isStatus(), BikeStatus.RESERVED);
    //     assertEquals(booking.getBikes().get(1).isStatus(), BikeStatus.RESERVED);

    //     assertEquals((booking.getOrderNumber() + 3), (noOfReservations + 1));
    //     // +3 as we already have 3 reservations from before to test our getQuotes()
    //     // function

    //     DeliveryServiceFactory.setupMockDeliveryService();
    //     MockDeliveryService deliveryService = (MockDeliveryService) DeliveryServiceFactory.getDeliveryService();
    //     Collection<Deliverable> deliverables = deliveryService.getPickupsOn(booking.getDateRange().getStart());

    //     for (Deliverable deliverable : deliverables) {

    //     }
    // }

    // @Test
    // public void returningBikes() {
    //     BikeRentalShop partnerBrs = brs1;
    //     ms.returnBikes(booking.getOrderNumber(), partnerBrs);

    //     if (booking.getShop().getName().equals(partnerBrs.getName())) {
    //         for (Bike bike : booking.getBikes())
    //             assertEquals(bike.isStatus(), BikeStatus.AVAILABLE);
    //     } else {
    //         for (Bike bike : booking.getBikes())
    //             assertEquals(bike.isStatus(), BikeStatus.PARTNER_TO_ORIGPROVIDER);
    //     }
    }
}
