package uk.ac.ed.bikerental;

import static org.junit.Assert.assertTrue;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

public class Bike {

    private BikeType type;
    private UUID id;
    private Shop owner;
    private LocalDate manufactureDate;
    private Set<DateRange> bookingDates;
    private BikeStatus status;
    private String notes;

    public Bike(BikeType type, Shop owner) {
        this(type, owner, LocalDate.now(), "");
    }

    public Bike(BikeType type, Shop owner, LocalDate manufactureDate, String notes) {
        this.type = type;
        this.id = UUID.randomUUID();
        this.owner = owner;
        this.manufactureDate = manufactureDate;
        this.bookingDates = new HashSet<DateRange>();
        this.status = BikeStatus.AVAILABLE;
        this.notes = notes;
    }

    public void updateBikeStatus(BikeStatus s) {
        this.status = s;
    }

    public boolean book(DateRange dateRange) {
        assertTrue(dateRange.isInFuture());
        if (isAvailable(dateRange)) {
            bookingDates.add(dateRange);
            return true;
        }
        return false;
    }

    public boolean isAvailable(DateRange dateRange) {
        assertTrue(dateRange.isInFuture());
        for (DateRange d : bookingDates) {
            if (dateRange.overlaps(d)) {
                return false;
            }
        }
        return true;
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

    public Set<DateRange> getBookingDates() {
        return this.bookingDates;
    }

    public LocalDate getManufactureDate() {
        return this.manufactureDate;
    }

    public BikeStatus getStatus() {
        return this.status;
    }

    public String getNotes() {
        return this.notes;
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