package uk.ac.ed.bikerental;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.UUID;

public class Quote {
    protected UUID id;
    protected BigDecimal price;
    protected BigDecimal deposit;
    protected DateRange dates;
    protected Location loc;
    protected Shop shop;
    protected Collection<Bike> bikeList;

    // Shallow copy constructor
    public Quote(Quote q) {
        this.id = q.id;
        this.price = q.price;
        this.deposit = q.deposit;
        this.dates = q.dates;
        this.loc = q.loc;
        this.shop = q.shop;
        this.bikeList = q.bikeList;
    }

    public Quote(BigDecimal price, BigDecimal deposit, DateRange dates,
            Location loc, Shop shop, Collection<Bike> bikeList) {
        this.id = UUID.randomUUID();
        this.price = price;
        this.deposit = deposit;
        this.dates = dates;
        this.loc = loc;
        this.shop = shop;
        this.bikeList = bikeList;
    }

    public Invoice generateInvoice() {
        return new Invoice(this);
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

    public Shop getShop() {
        return this.shop;
    }

    public Collection<Bike> getBikeList() {
        return this.bikeList;
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