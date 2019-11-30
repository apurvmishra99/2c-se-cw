package uk.ac.ed.bikerental;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

public class Controller {

    private CurrentAction action;
    private Shop loggedInShop;
    private Collection<Shop> shops;
    private Collection<BikeType> bikeTypes;
    private Map<UUID, Booking> bookings;

    /**
     * @return
     */
    public Controller() {
        this.action = CurrentAction.VIEW;
        this.loggedInShop = null;
        this.shops = new HashSet<Shop>();
        this.bikeTypes = new HashSet<BikeType>();
        this.bookings = new HashMap<UUID, Booking>();
    }

    /**
     * @param bikes
     * @param dates
     * @param location
     * @return Set<Quote>
     */
    public Set<Quote> getQuotes(Map<BikeType, Integer> bikes, DateRange dates, Location location) {
        Set<Quote> ret = new HashSet<Quote>();
        for (Shop s : this.shops) {
            if (!s.getAddress().isNearTo(location)) {
                continue;
            }
            Collection<Bike> bikeList = s.getBikes(dates, bikes);
            if (bikeList != null) {
                BigDecimal price = s.getPricingPolicy().calculatePrice(bikeList, dates).setScale(0,
                        BigDecimal.ROUND_HALF_UP);
                BigDecimal deposit = s.generateDeposit(bikeList, dates.getStart()).setScale(0,
                        BigDecimal.ROUND_HALF_UP);
                Quote q = new Quote(price, deposit, dates, location, s, bikeList);
                ret.add(q);
            }
        }
        return ret;
    }

    /**
     * @param  quote
     * @param  pickupMethod
     * @return Invoice
     * @throws Error if the quote is not available anymore.
     */
    public Invoice bookQuote(Quote quote, PickupMethod pickupMethod) {
        for (Bike b : quote.getBikeList()) {
            if (!b.isAvailable(quote.getDates())) {
                throw new Error("Quote expired.");
            }
        }
        Invoice invoice = quote.generateInvoice();
        invoice.setPickupMethod(pickupMethod);
        // Create a booking
        Booking b = new Booking(invoice);
        // Storing the booking id and the booking
        this.bookings.put(b.getId(), b);
        return invoice;
    }

    /**
     * @param b
     * @throws Error if User not logged in
     * @throws Error if Booking not found
     */
    public void returnBooking(UUID bookingID) {
        if (this.loggedInShop == null) {
            throw new Error("User not logged in");
        }
        if (!this.bookings.containsKey(bookingID)) {
            throw new Error("Booking not found.");
        }
        this.bookings.get(bookingID).returnBikes(this.loggedInShop);
    }

    /**
     * @param bikeType
     * @param dailyPrice
     * @throws Error if User not logged in
     */
    public void setDailyPrice(BikeType bikeType, BigDecimal dailyPrice) {
        if (this.loggedInShop == null) {
            throw new Error("User not logged in");
        }
        this.loggedInShop.getPricingPolicy().setDailyRentalPrice(bikeType, dailyPrice);
    }

    /**
     * @param shop
     * @param password
     */
    public void login(Shop shop, String password) {
        if (shop.auth(password)) {
            this.loggedInShop = shop;
        }
    }

    /**
     * @param bikeType
     * @return Bike
     */
    public Bike addBike(BikeType bikeType) {
        return this.addBike(bikeType, LocalDate.now(), "");
    }

    /**
     * @param bikeType
     * @param manifactureDate
     * @param notes
     * @return Bike
     * @throws Error if User not logged in
     */
    public Bike addBike(BikeType bikeType, LocalDate manifactureDate, String notes) {
        assert (manifactureDate.compareTo(LocalDate.now()) <= 0);
        if (this.loggedInShop == null) {
            throw new Error("User not logged in");
        }
        return this.loggedInShop.addBike(bikeType, manifactureDate, notes);
    }

    /**
     * @param s
     * @param replacementValue
     * @param depreciationRate
     * @return BikeType
     * @throws Error if BikeType is already registered
     */
    public BikeType addBikeType(String s, BigDecimal replacementValue, BigDecimal depreciationRate) {
        BikeType newBikeType = new BikeType(s, replacementValue, depreciationRate);
        if (!this.bikeTypes.add(newBikeType)) {
            throw new Error("BikeType already registered");
        }
        return newBikeType;
    }

    /**
     * @param address
     * @param hours
     * @param partners
     * @param bikes
     * @param depositRate
     * @return Shop
     */
    public Shop addShop(Location address, String hours, Set<Shop> partners, Set<Bike> bikes, BigDecimal depositRate) {
        Shop shop = new Shop(address, hours, partners, bikes, depositRate);
        this.shops.add(shop);
        return shop;
    }

    /**
     * @param address
     * @param hours
     * @param partners
     * @param bikes
     * @param depositRate
     * @param valuationPolicy
     * @param pricingPolicy
     * @return Shop
     */
    public Shop addShop(Location address, String hours, Set<Shop> partners, Set<Bike> bikes, BigDecimal depositRate,
            ValuationPolicy valuationPolicy, PricingPolicy pricingPolicy) {
        Shop shop = new Shop(address, hours, partners, bikes, depositRate, valuationPolicy, pricingPolicy);
        this.shops.add(shop);
        return shop;
    }

    /**
     * @return CurrentAction
     */
    public CurrentAction getAction() {
        return this.action;
    }

    /**
     * @return Shop
     */
    public Shop getLoggedInShop() {
        return this.loggedInShop;
    }

    /**
     * @return Collection<Shop>
     */
    public Collection<Shop> getShops() {
        return this.shops;
    }

    /**
     * @return String
     */
    @Override
    public String toString() {
        return "{" + " action='" + getAction() + "'" + ", loginID='" + getLoggedInShop() + "'" + ", shops='"
                + getShops() + "'" + "}";
    }
}
