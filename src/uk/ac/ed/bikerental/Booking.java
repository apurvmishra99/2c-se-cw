package uk.ac.ed.bikerental;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Collection;
import java.util.UUID;

public class Booking implements Deliverable {

    private UUID id;
    private Shop store;
    private Collection<Bike> bikes;
    private DateRange dates;
    private BigDecimal deposit;
    private BookingStatus status;
    private String returnConditions;
    private PickupMethod pickupMethod;

    /**
     * @param invoice
     * @return
     */
    public Booking(Invoice invoice) {
        this.id = invoice.getUUID();
        this.store = invoice.getShop();
        this.bikes = invoice.getBikeList();
        this.dates = invoice.getDates();
        this.deposit = invoice.getDeposit();
        this.status = BookingStatus.BOOKED;
        this.pickupMethod = invoice.getPickupMethod();
        this.returnConditions = "";
    }

    /**
     * @param store
     * @param bikes
     * @param dates
     * @param deposit
     * @param pickupMethod
     * @return
     */
    public Booking(Shop store, Collection<Bike> bikes, DateRange dates, BigDecimal deposit, PickupMethod pickupMethod) {
        this.id = UUID.randomUUID();
        this.store = store;
        this.bikes = bikes;
        this.dates = dates;
        this.deposit = deposit;
        this.status = BookingStatus.BOOKED;
        this.pickupMethod = pickupMethod;
    }

    /**
     * @param bs
     */
    public void updateBikesStatus(BikeStatus bs) {
        for (Bike b : this.bikes) {
            b.updateBikeStatus(bs);
        }
    }

    /**
     * @param ls
     */
    public void updateBookingStatus(BookingStatus ls) {
        this.status = ls;
        BikeStatus bs = null;
        if (ls == BookingStatus.ONLOAN) {
            bs = BikeStatus.ONLOAN;
        } else if (ls == BookingStatus.RETURNED) {
            bs = BikeStatus.AVAILABLE;
        }
        this.updateBikesStatus(bs);
    }

    /**
     * @param shop
     * @throws Error if shops are not partnered
     */
    public void returnBikes(Shop shop) {
        if (shop != this.store) {
            if (!this.store.hasPartner(shop)) {
                throw new Error("Shops are not partner.");
            }
            DeliveryServiceFactory.getDeliveryService().scheduleDelivery(this, this.store.getAddress(),
                    shop.getAddress(), LocalDate.now());
        } else {
            updateBookingStatus(BookingStatus.RETURNED);
        }
    }

    public void onPickup() {
        this.updateBikesStatus(BikeStatus.DELIVERY);
    }

    public void onDropoff() {
        if (this.status == BookingStatus.ONLOAN) {
            this.updateBookingStatus(BookingStatus.RETURNED);
        } else {
            this.updateBookingStatus(BookingStatus.ONLOAN);
        }
    }

    /**
     * @return UUID
     */
    public UUID getId() {
        return this.id;
    }

    /**
     * @return Shop
     */
    public Shop getStore() {
        return this.store;
    }

    /**
     * @return Collection<Bike>
     */
    public Collection<Bike> getBikes() {
        return this.bikes;
    }

    /**
     * @return DateRange
     */
    public DateRange getDates() {
        return this.dates;
    }

    /**
     * @return BigDecimal
     */
    public BigDecimal getDeposit() {
        return this.deposit;
    }

    /**
     * @return BookingStatus
     */
    public BookingStatus getStatus() {
        return this.status;
    }

    /**
     * @return String
     */
    public String getReturnConditions() {
        return this.returnConditions;
    }

    /**
     * @return PickupMethod
     */
    public PickupMethod getPickupMethod() {
        return this.pickupMethod;
    }

    /**
     * @param s
     */
    public void setReturnConditions(String s) {
        this.returnConditions = s;
    }

    /**
     * @return String
     */
    @Override
    public String toString() {
        return "{" + " id='" + getId() + "'" + ", store='" + getStore() + "'" + ", bikes='" + getBikes() + "'"
                + ", dates='" + getDates() + "'" + ", deposit='" + getDeposit() + "'" + ", status='" + getStatus() + "'"
                + ", returnConditions='" + getReturnConditions() + "'" + ", pickupMethod='" + getPickupMethod() + "'"
                + "}";
    }
}