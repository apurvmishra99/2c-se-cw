package uk.ac.ed.bikerental;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Collection;
import java.util.Objects;
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

        if (this.pickupMethod == PickupMethod.DELIVERY) {
            DeliveryServiceFactory.getDeliveryService().scheduleDelivery(this, this.store.getAddress(),
                    invoice.getLocation(), this.dates.getStart());
        }
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
        switch (this.status) {
            // Impossible
            case BOOKED:
                break;
            // Delivered to customer
            case ONLOAN:
                this.updateBikesStatus(BikeStatus.ONLOAN);
                break;
            // Returned to shop
            case RETURNED:
                this.updateBikesStatus(BikeStatus.AVAILABLE);
                break;
        }
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
        // System.out.println("Being pickedup");
        this.updateBikesStatus(BikeStatus.DELIVERY);
    }

    public void onDropoff() {
        // System.out.println("Being dropped off");
        switch (this.status) {
        // Delivery to customer
        case BOOKED:
            this.updateBookingStatus(BookingStatus.ONLOAN);
            break;
        // Delivery from partner shop
        case ONLOAN:
            this.updateBookingStatus(BookingStatus.RETURNED);
            break;
        // Impossible
        case RETURNED:
            break;
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
     * @param o
     * @return boolean
     */
    @Override
    public boolean equals(Object o) {
        if (o == this)
            return true;
        if (!(o instanceof Booking)) {
            return false;
        }
        Booking booking = (Booking) o;
        return (this.store.equals(booking.store) && this.deposit.equals(booking.deposit)
                && this.status.equals(booking.status) && this.bikes.size() == booking.bikes.size()
                && this.pickupMethod.equals(booking.pickupMethod) && this.dates.equals(booking.dates));
    }

    /**
     * @return int
     */
    @Override
    public int hashCode() {
        return Objects.hash(store, bikes.size(), dates, deposit, status, pickupMethod);
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