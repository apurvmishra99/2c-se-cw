package uk.ac.ed.bikerental;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class SystemTest1 {
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
        dateRange1 = new DateRange(LocalDate.of(2019, 1, 7), LocalDate.of(2019, 1, 10));
        dateRange2 = new DateRange(LocalDate.of(2019, 1, 5), LocalDate.of(2019, 1, 23));
        dateRange3 = new DateRange(LocalDate.of(2015, 1, 7), LocalDate.of(2018, 1, 10));

        datePast = LocalDate.of(2017, 1, 7);
        dateNow = LocalDate.now();
        dateFuture = LocalDate.of(2022, 5, 1);

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
        s1 = controller.addShop(loc1, "", new HashSet<Shop>(), new HashSet<Bike>(), new BigDecimal(0.4), new LinearDepreciationPolicy(), new TestPricingPolicy());
        controller.login(s1, "");
        b1 = controller.addBike(bikeType1);
        b2 = controller.addBike(bikeType2, datePast, "We love SE");
        b3 = controller.addBike(bikeType1);

        // Initialise Shop2 -- owns 2xType2 + 1xType4
        s2 = controller.addShop(loc2, "", new HashSet<Shop>(), new HashSet<Bike>(), new BigDecimal(0.1), new DoubleDecliningPolicy(), new DefaultPricingPolicy());
        controller.login(s2, "");
        b4 = controller.addBike(bikeType2);
        b5 = controller.addBike(bikeType4, dateNow, "We love SE");
        b6 = controller.addBike(bikeType2);

        // Initialise Shop3 -- owns 2xType1 + 1xType4
        s3 = controller.addShop(loc3, "", new HashSet<Shop>(), new HashSet<Bike>(), new BigDecimal(0.3));
        controller.login(s3, "");
        b7 = controller.addBike(bikeType1);
        b8 = controller.addBike(bikeType4);
        b9 = controller.addBike(bikeType1);


        // Initialise Shop4 -- owns 3xType1
        s4 = controller.addShop(loc4, "", new HashSet<Shop>(), new HashSet<Bike>(), new BigDecimal(0.2));
        controller.login(s4, "");
        b10 = controller.addBike(bikeType1);
        b11 = controller.addBike(bikeType1);
        b12 = controller.addBike(bikeType1);

        s1.addPartner(s2);
        s2.addPartner(s1);
        s4.addPartner(s5);
        s5.addPartner(s4);


        // // BIKES
        // b0 = new Bike(type, owner, manufactureDate, notes);
        // b1 = new Bike(new BigDecimal(900), new BigDecimal(20), BikeStatus.AVAILABLE, new BigDecimal(20), new BigDecimal(10), LocalDate.of(2012, 1, 1), "Mountain Bike", null);
        // b2 = new Bike(new BigDecimal(900), new BigDecimal(15), BikeStatus.AVAILABLE, new BigDecimal(20), new BigDecimal(10), LocalDate.of(2012, 1, 1), "Road Bike", null);
        
        // b3 = new Bike(new BigDecimal(150), new BigDecimal(40), BikeStatus.AVAILABLE, new BigDecimal(10), new BigDecimal(10), LocalDate.of(2017, 1, 1), "Hybrid Bike", null);
        // b4 = new Bike(new BigDecimal(120), new BigDecimal(15), BikeStatus.AVAILABLE, new BigDecimal(9), new BigDecimal(10), LocalDate.of(2015, 1, 1), "Road Bike", null);

        // b5 = new Bike(new BigDecimal(150), new BigDecimal(20), BikeStatus.RESERVED, new BigDecimal(11), new BigDecimal(12), LocalDate.of(2017, 1, 1), "Mountain Bike", res1);
        // b6 = new Bike(new BigDecimal(120), new BigDecimal(15), BikeStatus.RESERVED, new BigDecimal(9), new BigDecimal(10), LocalDate.of(2015, 1, 1), "Road Bike", res2);
        
        // b7 = new Bike(new BigDecimal(900), new BigDecimal(20), BikeStatus.RESERVED, new BigDecimal(20), new BigDecimal(10), LocalDate.of(2012, 1, 1), "Mountain Bike", res1);
        // b8 = new Bike(new BigDecimal(900), new BigDecimal(15), BikeStatus.RESERVED, new BigDecimal(20), new BigDecimal(10), LocalDate.of(2012, 1, 1), "Road Bike", res1);
        
        
//        BIKE RENTAL SHOPS
        
//        SHOP 1
        bikes1 = new HashSet();
        bikes1.add(b1);
        bikes1.add(b2);
        Location l1 = new Location("EH2 2PF", "Shop 1, 14 George St, Edinburgh");
        brs1 = new BikeRentalShop("Shop1", l1, "9999999999", "10:00 to 22:00", bikes1, null);
        
//        SHOP 2
        bikes2 = new HashSet();
        bikes2.add(b3);
        bikes2.add(b4);
        Location l2 = new Location("EH2 2PF", "Shop2 , 14 George St, Edinburgh");
        brs2 = new BikeRentalShop("Shop2", l2, "888888888", "11:00 to 22:00", bikes2, null);
        
//        SHOP3
        bikes3 = new HashSet();
        bikes3.add(b5);
        bikes3.add(b6);
        Location l3 = new Location("EH2 2PF", "Shop3 , 14 George St, Edinburgh");
        brs3 = new BikeRentalShop("Shop3", l3, "777777777", "12:00 to 22:00", bikes3, null);

//        SHOP4
        bikes4 = new HashSet();
        bikes4.add(b7);
        bikes4.add(b8);
        Location l4 = new Location("LW2 2PF", "Shop4 , 14 George St, Edinburgh");
        brs4 = new BikeRentalShop("Shop4", l4, "6666666666", "13:00 to 22:00", bikes4, null);
        
//        SHOP5
        bikes5 = new HashSet();
        bikes5.add(b7);
        bikes5.add(b8);
        Location l5 = new Location("EH2 2PF", "Shop 5, 14 George St, Edinburgh");
        brs5 = new BikeRentalShop("Shop5", l5, "55555555555", "14:00 to 22:00", bikes5, null);


        providers.add(brs1);
        providers.add(brs2);
        providers.add(brs3);
        providers.add(brs4);
        providers.add(brs5);
        // Put your test setup here
        
        bikesReq = new HashSet();
        
        bikesReq.add("Mountain Bike");
        bikesReq.add("Road Bike");
        //bikesReq.add("Hybrid Bike");
        
        l_customer = new Location("EH2 2PF", "QuaterMile, Edin");
       
        ms = new MainSystem(providers);
        
        
        // Get a returned list of quotes
    }
    
    // TODO: Write system tests covering the three main use cases

    @Test
    void findingAQuote()  // FINDING QUOTES =============================================
    {

        // list of matching quotes returned, should include
        // provider, bikes and price deposit amounts for quote
        
    	ms.setDepreciationMethod("DDBD");
    	actualQuotes = ms.createCustomer("Dan", "Wilks", l_customer, "999999999", bikesReq, dateRange3, "Delivery");
        
        int numQuotes = actualQuotes.size();
        
        assertEquals(numQuotes, 2);// CHECKING IF 2 QUOTES HAVE BEEN RETURNED
            
            
            java.util.Iterator<Quote> quote = actualQuotes.iterator();
            for(int i = 0; i < numQuotes; ++i)
            {
                Quote actualQ = quote.next();

                if(actualQ.getBrs().getName() == "Shop1")
                {
                    assertEquals(actualQ.getBikes().get(0).getType().getTypeName(), "Mountain Bike");
                    assertEquals(actualQ.getBikes().get(1).getType().getTypeName(), "Road Bike");
                    
                    assertEquals(actualQ.getTotalPrice(), new BigDecimal(35));
                    assertEquals(actualQ.getDeposit().setScale(2, RoundingMode.CEILING), new BigDecimal("184.32"));
                }
                else if(actualQ.getBrs().getName() == "Shop5")
                {
                    assertEquals(actualQ.getBikes().get(0).getType().getTypeName(), "Mountain Bike");
                    assertEquals(actualQ.getBikes().get(1).getType().getTypeName(), "Road Bike");

                    assertEquals(actualQ.getTotalPrice(), new BigDecimal(35));
                    assertEquals(actualQ.getDeposit().setScale(2, RoundingMode.CEILING), new BigDecimal("184.32"));                }
                else assert(false);// WRONG QUOTE
            }
    }

    @Test
    void bookingAQuote() {
        
        //actualQuotes = ms.createCustomer("Dan", "Wilks", l_customer, "999999999", bikesReq, dateRange3, "Delivery");
        
        // Quote selected by the customer
        Quote selectedQuote = new Quote(brs1, dateRange3, bikes1, new BigDecimal(35), new BigDecimal(20));

        
        booking = ms.makeBooking(selectedQuote);
        
        assertEquals(booking.getShop().getName(), "Shop1");
        
        assertEquals(booking.getDeposit(), 20.0);
        assertEquals(booking.getTotalPrice(), 35.0);
        
        assertEquals(booking.getBikes().get(0).getType().getTypeName(), "Mountain Bike");
        assertEquals(booking.getBikes().get(1).getType().getTypeName(), "Road Bike");
        
        //Checking if the status of the bikes has been changed or not
        assertEquals(booking.getBikes().get(0).isStatus(), BikeStatus.RESERVED);
        assertEquals(booking.getBikes().get(1).isStatus(), BikeStatus.RESERVED);
        
        assertEquals((booking.getOrderNumber() + 3), (noOfReservations + 1));
        // +3 as we already have 3 reservations from before to test our getQuotes() function
        
        DeliveryServiceFactory.setupMockDeliveryService();
        MockDeliveryService deliveryService = (MockDeliveryService) DeliveryServiceFactory.getDeliveryService();
        Collection<Deliverable> deliverables = deliveryService.getPickupsOn(booking.getDateRange().getStart());
        
        for(Deliverable deliverable: deliverables)
        {
            
        }
    }
    
    @Test
    public void returningBikes()
    {
        BikeRentalShop partnerBrs = brs1;
        ms.returnBikes(booking.getOrderNumber(), partnerBrs);
        
        if(booking.getShop().getName().equals(partnerBrs.getName()))
        {
            for(Bike bike: booking.getBikes())
                assertEquals(bike.isStatus(), BikeStatus.AVAILABLE);
        }
        else
        {
            for(Bike bike: booking.getBikes())
                assertEquals(bike.isStatus(), BikeStatus.PARTNER_TO_ORIGPROVIDER);
        }
    }
}