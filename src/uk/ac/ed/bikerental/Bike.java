package uk.ac.ed.bikerental;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

public class Bike {

    private BikeType type;
    private UUID id;
    private Shop owner;
    private Set<DateRange> bookingDates;
    private BikeStatus status;
    private String notes;

    public Bike(BikeType type, Shop owner) {
        this(type, owner, "");
    }

    public Bike(BikeType type, Shop owner, String notes) {
        this.type = type;
        this.id = UUID.randomUUID();
        this.owner = owner;
        this.bookingDates = new HashSet<DateRange>();
        this.status = BikeStatus.AVAIALBLE;
        this.notes = notes;
    }

    public BikeType getType() {
        return this.type;
    }

    public UUID getId() {
        return this.id;
    }

    public Shop getOwner() {
        return this.owner;
    }

    public Set<DateRange> getUnavailabile() {
        return this.bookingDates;
    }

    public BikeStatus getStatus() {
        return this.status;
    }

    public String getNotes() {
        return this.notes;
    }

    public void updateBikeStatus(BikeStatus s) {
        this.status = s;
    }

    public boolean book(DateRange dateRange) {
        if (isAvailable(dateRange)) {
            bookingDates.add(dateRange);
            return true;
        }
        return false;
    }

    public boolean isAvailable(DateRange dateRange) {
        for (DateRange d: bookingDates) {
            if (dateRange.overlaps(d)) {
                return false;
            }
        }
        return true;
    }

    @Override
    public String toString() {
        return "{" +
            " type='" + getType() + "'" +
            ", id='" + getId() + "'" +
            ", owner='" + getOwner() + "'" +
            ", status='" + getStatus() + "'" +
            ", notes='" + getNotes() + "'" +
            "}";
    }
}