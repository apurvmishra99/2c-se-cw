package uk.ac.ed.bikerental;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.Objects;
import java.util.UUID;

public class Quote {
    protected UUID id;
    protected BigDecimal price;
    protected BigDecimal deposit;
    protected DateRange dates;
    protected Location loc;
    protected Shop shop;
    protected Collection<Bike> bikeList;

    
    /** 
     * @param q
     * @return 
     */
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

    
    /** 
     * @param price
     * @param deposit
     * @param dates
     * @param loc
     * @param shop
     * @param bikeList
     * @return 
     */
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

    
    /** 
     * @return Invoice
     */
    public Invoice generateInvoice() {
        return new Invoice(this);
    }


    
    /** 
     * @return 
     */
    public Quote() {
    }

    
    /** 
     * @param id
     * @param price
     * @param deposit
     * @param dates
     * @param loc
     * @param shop
     * @param bikeList
     * @return 
     */
    public Quote(UUID id, BigDecimal price, BigDecimal deposit, DateRange dates, Location loc, Shop shop, Collection<Bike> bikeList) {
        this.id = id;
        this.price = price;
        this.deposit = deposit;
        this.dates = dates;
        this.loc = loc;
        this.shop = shop;
        this.bikeList = bikeList;
    }

    
    /** 
     * @return UUID
     */
    public UUID getId() {
        return this.id;
    }

    
    /** 
     * @return BigDecimal
     */
    public BigDecimal getPrice() {
        return this.price;
    }

    
    /** 
     * @return BigDecimal
     */
    public BigDecimal getDeposit() {
        return this.deposit;
    }

    
    /** 
     * @return DateRange
     */
    public DateRange getDates() {
        return this.dates;
    }

    
    /** 
     * @return Location
     */
    public Location getLoc() {
        return this.loc;
    }

    
    /** 
     * @return Shop
     */
    public Shop getShop() {
        return this.shop;
    }

    
    /** 
     * @return Collection<Bike>
     */
    public Collection<Bike> getBikeList() {
        return this.bikeList;
    }

    
    /** 
     * @param o
     * @return boolean
     */
    @Override
    public boolean equals(Object o) {
        if (o == this)
            return true;
        if (!(o instanceof Quote)) {
            return false;
        }
        Quote quote = (Quote) o;
        return (this.dates.equals(quote.dates) && this.deposit.equals(quote.deposit) && this.price.equals(quote.price) && this.bikeList.size() == quote.bikeList.size()); 
    }

    
    /** 
     * @return int
     */
    @Override
    public int hashCode() {
        return Objects.hash(price, deposit, dates, loc);
    }

    
    /** 
     * @return String
     */
    @Override
    public String toString() {
        return "{" +
            " id='" + getId() + "'\n" +
            ", price='" + getPrice() + "'\n" +
            ", deposit='" + getDeposit() + "'\n" +
            ", dates='" + getDates() + "'\n" +
            ", loc='" + getLoc() + "'\n" +
            ", shop='" + getShop() + "'\n" +
            ", bikeList='" + getBikeList() + "'\n" +
            "}\n";
    }
}