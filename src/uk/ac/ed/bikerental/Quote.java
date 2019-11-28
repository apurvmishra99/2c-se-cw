package uk.ac.ed.bikerental;

import java.util.ArrayList;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Hashtable;
import java.util.Objects;

public class Quote {
    protected String id;
    protected double price;
    protected double deposit;
    protected DateRange dates;
    protected Location loc;
    protected ArrayList<Bike> bikeList;

    // Shallow copy constructor
    public Quote(Quote q) {
        this.id = q.id;
        this.price = q.price;
        this.deposit = q.deposit;
        this.dates = q.dates;
        this.loc = q.loc;
        this.bikeList = q.bikeList;
    }

    public Quote(String id, double price, double deposit, DateRange dates, Location loc, ArrayList<Bike> bikeList) {
        this.id = id;
        this.price = price;
        this.deposit = deposit;
        this.dates = dates;
        this.loc = loc;
        this.bikeList = bikeList;
    }

    public String getId() {
        return this.id;
    }

    public double getPrice() {
        return this.price;
    }

    public double getDeposit() {
        return this.deposit;
    }

    public DateRange getDates() {
        return this.dates;
    }

    public Location getLoc() {
        return this.loc;
    }

    public ArrayList<Bike> getBikeList() {
        return this.bikeList;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this)
            return true;
        if (!(o instanceof Quote)) {
            return false;
        }
        Quote quote = (Quote) o;
        return Objects.equals(id, quote.id) && price == quote.price && deposit == quote.deposit && Objects.equals(dates, quote.dates) && Objects.equals(loc, quote.loc) && Objects.equals(bikeList, quote.bikeList);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, price, deposit, dates, loc, bikeList);
    }

    @Override
    public String toString() {
        return "{" +
            " id='" + getId() + "'" +
            ", price='" + getPrice() + "'" +
            ", deposit='" + getDeposit() + "'" +
            ", dates='" + getDates() + "'" +
            ", loc='" + getLoc() + "'" +
            ", bikeList='" + getBikeList() + "'" +
            "}";
    }


}