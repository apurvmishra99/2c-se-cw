package uk.ac.ed.bikerental;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Objects;
import java.util.UUID;

public class Quote {
    protected UUID id;
    protected BigDecimal price;
    protected BigDecimal deposit;
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

    public Quote(BigDecimal price, BigDecimal deposit, DateRange dates, Location loc, ArrayList<Bike> bikeList) {
        this.id = UUID.randomUUID();
        this.price = price;
        this.deposit = deposit;
        this.dates = dates;
        this.loc = loc;
        this.bikeList = bikeList;
    }

    public UUID getId() {
        return this.id;
    }

    public BigDecimal getPrice() {
        return this.price;
    }

    public BigDecimal getDeposit() {
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