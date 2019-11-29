package uk.ac.ed.bikerental;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Collection;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class Controller {

    private CurrentAction action;
    private Shop loggedInShop;
    private Collection<Shop> shops;
    private Collection<BikeType> bikeTypes;

    public Controller() {
        this.action = CurrentAction.VIEW;
        this.loggedInShop = null;
        this.shops = new HashSet<Shop>();
        this.bikeTypes = new HashSet<BikeType>();
    }

    public Set<Quote> getQuotes(Map<BikeType, Integer> bikes, DateRange dates, Location location) {
        Set<Quote> ret = new HashSet<Quote>();
        for (Shop s : this.shops) {
            if (!s.getAddress().isNearTo(location)) {
                continue;
            }
            Collection<Bike> bikeList = s.getBikes(dates, bikes);
            if(bikeList != null) {
                BigDecimal price = s.getPricingPolicy().calculatePrice(bikeList, dates);
                BigDecimal deposit = s.generateDeposit(bikeList, dates.getStart());
                Quote q = new Quote(price, deposit, dates, location, s, bikeList);
                ret.add(q);
            }
        }
        return ret;
    }

    public Invoice bookQuote(Quote quote, PickupMethod pickupMethod) {
        for (Bike b : quote.getBikeList()) {
            if (!b.isAvailable(quote.getDates())) {
                throw new Error("Quote expired.");
            }
        }
        Invoice invoice = quote.generateInvoice();
        invoice.setPickupMethod(pickupMethod);
        return invoice;
    }

    public void returnBooking(Booking b) {
        if (this.loggedInShop == null) {
            throw new Error("User not logged in");
        }
        b.returnBikes(this.loggedInShop);
    }

    public void setDailyPrice(BikeType bikeType, BigDecimal dailyPrice) {
        if (this.loggedInShop == null) {
            throw new Error("User not logged in");
        }
        this.loggedInShop.getPricingPolicy().setDailyRentalPrice(bikeType, dailyPrice);
    }

    public void login(Shop shop, String password) {
        if (shop.auth(password)) {
            this.loggedInShop = shop;
        }
    }

    public Bike addBike(BikeType bikeType) {
        return this.addBike(bikeType, LocalDate.now(), "");
    }

    public Bike addBike(BikeType bikeType, LocalDate manifactureDate, String notes) {
        assert (manifactureDate.compareTo(LocalDate.now()) <= 0);
        if (this.loggedInShop == null) {
            throw new Error("User not logged in");
        }
        return this.loggedInShop.addBike(bikeType, manifactureDate, notes);
    }

    public BikeType addBikeType(String s, BigDecimal replacementValue, BigDecimal depreciationRate) {
        BikeType newBikeType = new BikeType(s, replacementValue, depreciationRate);
        if (!this.bikeTypes.add(newBikeType)) {
            throw new Error("BikeType already registered");
        }
        return newBikeType;
    }

    public Shop addShop(Location address, String hours, Set<Shop> partners, Set<Bike> bikes, BigDecimal depositRate) {
        Shop shop = new Shop(address, hours, partners, bikes, depositRate);
        this.shops.add(shop);
        return shop;
    }

    public Shop addShop(Location address, String hours, Set<Shop> partners, Set<Bike> bikes, BigDecimal depositRate, ValuationPolicy valuationPolicy, PricingPolicy pricingPolicy) {
        Shop shop = new Shop(address, hours, partners, bikes, depositRate, valuationPolicy, pricingPolicy);
        this.shops.add(shop);
        return shop;
    }

    public CurrentAction getAction() {
        return this.action;
    }

    public Shop getLoggedInShop() {
        return this.loggedInShop;
    }

    public Collection<Shop> getShops() {
        return this.shops;
    }

    @Override
    public String toString() {
        return "{" +
            " action='" + getAction() + "'" +
            ", loginID='" + getLoggedInShop() + "'" +
            ", shops='" + getShops() + "'" +
            "}";
    }
}
