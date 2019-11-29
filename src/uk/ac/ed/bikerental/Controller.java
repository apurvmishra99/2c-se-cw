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
    }

    public Set<Quote> getQuotes(Map<BikeType, Integer> bikes, DateRange dates, Location location) {
        Set<Quote> ret = new HashSet<Quote>();
        for (Shop s : this.shops) {
            Collection<Bike> bikeList = s.getBikes(dates, bikes);
            if(bikeList != null) {
                BigDecimal p = (new TestPricingPolicy()).calculatePrice(bikeList, dates);
                BigDecimal d = s.generateDeposit(bikeList, dates.getStart());
                Quote q = new Quote(p, d, dates, location, s, bikeList);
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


    public void login(Shop shop, String password) {
        if (shop.auth(password)) {
            this.loggedInShop = shop;
        }
    }

    public void addBike(BikeType bikeType) {
        this.addBike(bikeType, LocalDate.now(), "");
    }

    public void addBike(BikeType bikeType, LocalDate manifactureDate, String notes) {
        if (this.loggedInShop == null) {
            throw new Error("User not logged in");
        }
        this.loggedInShop.addBike(bikeType, manifactureDate, notes);;
    }

    public void addBikeType(String s, BigDecimal replacementValue, BigDecimal depreciationRate) {
        BikeType newBikeType = new BikeType(s, replacementValue, depreciationRate);
        if (!this.bikeTypes.add(newBikeType)) {
            throw new Error("BikeType already present");
        }
    }

    public void addShop(Shop s) {
        this.shops.add(s);
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
