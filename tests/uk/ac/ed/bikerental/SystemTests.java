package uk.ac.ed.bikerental;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;
import java.util.function.Consumer;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;

public class SystemTests {
    // You can add attributes here
    Booking booking;
    BikeType bike_type1;
    BikeType bike_type2;
    BikeType bike_type3;
    Bike b1;
    Bike b2;
    Bike b3;
    HashSet<Bike> arr;
    HashSet<BikeType> arr1;
    BigDecimal bg;
    DateRange dateRange1;
    DateRange dateRange2;
    HashSet<Consumer> cons;
    HashSet<Shop> shops;
    Shop shop1;
    Shop shop2;
    Shop shop3;
    LocalDate manufactureDate1;
    LocalDate manufactureDate2;
    LocalDate manufactureDate3;
    UUID id1;
    UUID id2;
    UUID id3;
    Set<DateRange> bookingDates;
    BikeStatus status;
    String notes;

    @BeforeEach
    void setUp() throws Exception {
        // Setup mock delivery service before each tests
        // DeliveryServiceFactory.setupMockDeliveryService();

        // Put your test setup here
        bike_type1 = new BikeType("Mountain", new BigDecimal(500), new BigDecimal(0.1));
        bike_type2 = new BikeType("Commercial", new BigDecimal(150), new BigDecimal(0.1));
        bike_type3 = new BikeType("Stunt", new BigDecimal(250), new BigDecimal(0.2));
        
        b1 = new Bike(bike_type1, shop1);
        b2 = new Bike(bike_type2, shop1);
        b3 = new Bike(bike_type3, shop2)
        
        arr = new HashSet<>();
        arr.add(b1);
        arr.add(b2);
        arr1 = new HashSet<>();
        // arr1.add(bike_type1);arr1.add(bike_type2);
        arr1.add(bike_type3);
        provider_1 = new Provider("Dezaw Nam", new Location("EH10 37Y", "Somewhere"), "7978679389");
        provider_1.registerBikes(arr);
        arr.add(new Bike(new BikeType("Stunt"), 0));
        provider_2 = new Provider("Sap Boe", new Location("EH16 8HT", "Appleton Tower"), "7876092376");
        provider_2.registerBikes(arr);
        cons = new HashSet<>();
        cons.add(new Consumer("Wazeed", "Naeem", new Location("EH16 5AY", "Pollock Halls"), "79828793892"));
        cons.add(new Consumer("Saptanshu", "Bose", new Location("EH16 8HT", "Appleton Tower"), "79828983892"));
        pros = new HashSet<>();
        // qm = new QuoteManager(cons, pros);
        pros.add(provider_1);
        pros.add(provider_2);
        this.dateRange1 = new DateRange(LocalDate.of(2019, 1, 8), LocalDate.of(2019, 1, 10));
        this.dateRange2 = new DateRange(LocalDate.of(2019, 1, 7), LocalDate.of(2019, 1, 10));
        provider_1.setDailyRentalPrice(bike_type1, new BigDecimal(500.0));
        provider_1.setDailyRentalPrice(bike_type2, new BigDecimal(500.0));// provider_1.setDailyRentalPrice(bike_type3,
                                                                          // new BigDecimal(800.0));
        provider_2.setDailyRentalPrice(bike_type2, new BigDecimal(400.0));
        provider_2.setDailyRentalPrice(bike_type1, new BigDecimal(500.0));
        provider_2.setDailyRentalPrice(bike_type3, new BigDecimal(800.0));
        quote_manager = new QuoteManager(cons, pros);
    }

    // TODO: Write system tests covering the three main use cases
    /**
     * Test for the get quotes checks if the standard answer meets the answer
     * 
     * @throws Exception
     */
    @Test
    void testsGetQuote() throws Exception {
        HashSet<Quote> quotes = (HashSet<Quote>) quote_manager.makeThem(
                new Consumer("Wazeed", "Naeem", new Location("EH16 5AY", "Pollock Halls"), "79828793892"), dateRange1,
                arr1);
        HashSet<Quote> quote = new HashSet<>();
        HashSet<Bike> bbk = new HashSet<>();
        bbk.add(new Bike(bike_type3, 0));
        quote.add(new Quote(new Consumer("Wazeed", "Naeem", new Location("EH16 5AY", "Pollock Halls"), "79828793892"),
                new Provider("Sap Boe", new Location("EH16 8HT", "Appleton Tower"), "7876092376"), bbk,
                new BigDecimal(800), dateRange1));
        assertEquals(quote, quotes);
    }

    /**
     * Test for the Book Quote
     * 
     * @throws Exception
     */
    @Test
    void testBookQuote() throws Exception {
        HashSet<Quote> quotes = (HashSet<Quote>) quote_manager.makeThem(
                new Consumer("Wazeed", "Naeem", new Location("EH16 5AY", "Pollock Halls"), "79828793892"), dateRange1,
                arr1);
        HashSet<Bike> bbk = new HashSet<>();
        bbk.add(new Bike(bike_type3, 0));
        Quote q = new Quote(new Consumer("Wazeed", "Naeem", new Location("EH16 5AY", "Pollock Halls"), "79828793892"),
                new Provider("Sap Boe", new Location("EH16 8HT", "Appleton Tower"), "7876092376"), bbk,
                new BigDecimal(800), dateRange1);

        Order or = quote_manager.bookQuote(
                new Consumer("Wazeed", "Naeem", new Location("EH16 5AY", "Pollock Halls"), "79828793892"), q, quotes);
        Order rigth = new Order(q);
        rigth.setOrderId(new BigInteger("0"));
        assertEquals(or, rigth);
    }

    /**
     * the test for return bike use case
     * 
     * @throws Exception
     */
    @Test
    void testReturnBike() throws Exception {
        HashSet<Quote> quotes = (HashSet<Quote>) quote_manager.makeThem(
                new Consumer("Wazeed", "Naeem", new Location("EH16 5AY", "Pollock Halls"), "79828793892"), dateRange1,
                arr1);
        HashSet<Bike> bbk = new HashSet<>();
        bbk.add(new Bike(bike_type3, 0));
        Quote q = new Quote(new Consumer("Wazeed", "Naeem", new Location("EH16 5AY", "Pollock Halls"), "79828793892"),
                new Provider("Sap Boe", new Location("EH16 8HT", "Appleton Tower"), "7876092376"), bbk,
                new BigDecimal(800), dateRange1);

        Order or = quote_manager.bookQuote(
                new Consumer("Wazeed", "Naeem", new Location("EH16 5AY", "Pollock Halls"), "79828793892"), q, quotes);

        quote_manager.returnBike(
                new Consumer("Wazeed", "Naeem", new Location("EH16 5AY", "Pollock Halls"), "79828793892"), or);
        assertEquals(true, provider_2.getReturnedBike().contains(new Bike(bike_type3, 0)));
    }

    }
    // TODO: Write system tests covering the three main use cases

    @Test
    void getQuotes_test() {

    }
}
