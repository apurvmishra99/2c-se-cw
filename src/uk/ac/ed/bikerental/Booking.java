package uk.ac.ed.bikerental;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Set;
import java.util.UUID;

public class Booking implements Deliverable {

    private UUID id;
    private Shop store;
    private Set<Bike> bikes;
    private DateRange dates;
    private BigDecimal deposit;
    private BookingStatus status;
    private String returnConditions;
    private PickupMethod pickupMethod;


    public Booking(Shop store, Set<Bike> bikes, DateRange dates,
                BigDecimal deposit, PickupMethod pickupMethod) {
        this.id = UUID.randomUUID();
        this.store = store;
        this.bikes = bikes;
        this.dates = dates;
        this.deposit = deposit;
        this.status = BookingStatus.BOOKED;
        this.pickupMethod = pickupMethod;
    }

    public void updateBikesStatus(BikeStatus bs) {
        for (Bike b : this.bikes) {
            b.updateBikeStatus(bs);
        }
    }

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

    public void returnBikes(Shop shop) {
        if (shop != this.store) {
            DeliveryServiceFactory
                    .getDeliveryService()
                    .scheduleDelivery(this, this.store.getAddress(), shop.getAddress(), LocalDate.now());
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

    public UUID getId() {
        return this.id;
    }

    public Shop getStore() {
        return this.store;
    }

    public Set<Bike> getBikes() {
        return this.bikes;
    }

    public DateRange getDates() {
        return this.dates;
    }

    public BigDecimal getDeposit() {
        return this.deposit;
    }

    public BookingStatus getStatus() {
        return this.status;
    }

    public String getReturnConditions() {
        return this.returnConditions;
    }

    public PickupMethod getPickupMethod() {
        return this.pickupMethod;
    }

    @Override
    public String toString() {
        return "{" +
            " id='" + getId() + "'" +
            ", store='" + getStore() + "'" +
            ", bikes='" + getBikes() + "'" +
            ", dates='" + getDates() + "'" +
            ", deposit='" + getDeposit() + "'" +
            ", status='" + getStatus() + "'" +
            ", returnConditions='" + getReturnConditions() + "'" +
            ", pickupMethod='" + getPickupMethod() + "'" +
            "}";
    }
}