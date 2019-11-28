package uk.ac.ed.bikerental;

import java.util.ArrayList;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Hashtable;
import java.util.Objects;

public class Quote {
    private String id;
    private double price;
    private double deposit;
    private DateRange dates;
    private Location loc;
    private ArrayList<Bike> bikeList;


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

    public void setId(String id) {
        this.id = id;
    }

    public double getPrice() {
        return this.price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public double getDeposit() {
        return this.deposit;
    }

    public void setDeposit(double deposit) {
        this.deposit = deposit;
    }

    public DateRange getDates() {
        return this.dates;
    }

    public void setDates(DateRange dates) {
        this.dates = dates;
    }

    public Location getLoc() {
        return this.loc;
    }

    public void setLoc(Location loc) {
        this.loc = loc;
    }

    public ArrayList<Bike> getBikeList() {
        return this.bikeList;
    }

    public void setBikeList(ArrayList<Bike> bikeList) {
        this.bikeList = bikeList;
    }

    public Quote id(String id) {
        this.id = id;
        return this;
    }

    public Quote price(double price) {
        this.price = price;
        return this;
    }

    public Quote deposit(double deposit) {
        this.deposit = deposit;
        return this;
    }

    public Quote dates(DateRange dates) {
        this.dates = dates;
        return this;
    }

    public Quote loc(Location loc) {
        this.loc = loc;
        return this;
    }

    public Quote bikeList(ArrayList<Bike> bikeList) {
        this.bikeList = bikeList;
        return this;
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