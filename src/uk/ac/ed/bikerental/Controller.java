package uk.ac.ed.bikerental;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

public class Controller {

    private CurrentAction action;
    private UUID loginID;
    private Collection<Shop> shops;
    private Collection<BikeType> bikeTypes;

    public Controller() {
        this.action = CurrentAction.VIEW;
        this.loginID = null;
        this.shops = new HashSet<Shop>();
    }

    public Set<Quote> getQuotes(Map<BikeType, Integer> bikes, DateRange dates, Location location) {
        Set<Quote> ret = new HashSet<Quote>();
        for (Shop s : this.shops) {
            Collection<Bike> bl = s.getBikes(dates, bikes);
            if(bl != null) {
                BigDecimal p = (new TestPricingPolicy()).calculatePrice(bl, dates);
                s.generatePrice(bl);
                Quote q = new Quote(p, deposit, dates, location, bikes);
            }
        }
        return ret;
    }

    public CurrentAction getAction() {
        return this.action;
    }

    public UUID getLoginID() {
        return this.loginID;
    }

    public Collection<Shop> getShops() {
        return this.shops;
    }

    public void login(UUID shopID) {
        this.loginID = shopID;
    }

    public void addShop(Shop s) {
        this.shops.add(s);
    }

    @Override
    public String toString() {
        return "{" +
            " action='" + getAction() + "'" +
            ", loginID='" + getLoginID() + "'" +
            ", shops='" + getShops() + "'" +
            "}";
    }
}
