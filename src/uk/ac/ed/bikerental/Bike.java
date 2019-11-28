package uk.ac.ed.bikerental;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Hashtable;
import java.util.Objects;

public class Bike {
    private BikeType type;
    private String id;
    private Shop owner;
    private Hashtable<LocalDate, Boolean> availability;
    private BikeStatus status;
    private String notes;

    public Bike(BikeType type, String id, Shop owner, Hashtable<LocalDate,Boolean> availability, BikeStatus status, String notes) {
        this.type = type;
        this.id = id;
        this.owner = owner;
        this.availability = availability;
        this.status = status;
        this.notes = notes;
    }
    public void setType(BikeType type) {
        this.type = type;
    }

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Shop getOwner() {
        return this.owner;
    }

    public void setOwner(Shop owner) {
        this.owner = owner;
    }

    public Hashtable<LocalDate,Boolean> getAvailability() {
        return this.availability;
    }

    public void setAvailability(Hashtable<LocalDate,Boolean> availability) {
        this.availability = availability;
    }

    public BikeStatus getStatus() {
        return this.status;
    }

    public void setStatus(BikeStatus status) {
        this.status = status;
    }

    public String getNotes() {
        return this.notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public Bike type(BikeType type) {
        this.type = type;
        return this;
    }

    public Bike id(String id) {
        this.id = id;
        return this;
    }

    public Bike owner(Shop owner) {
        this.owner = owner;
        return this;
    }

    public Bike availability(Hashtable<LocalDate,Boolean> availability) {
        this.availability = availability;
        return this;
    }

    public Bike status(BikeStatus status) {
        this.status = status;
        return this;
    }

    public Bike notes(String notes) {
        this.notes = notes;
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this)
            return true;
        if (!(o instanceof Bike)) {
            return false;
        }
        Bike bike = (Bike) o;
        return Objects.equals(type, bike.type) && Objects.equals(id, bike.id) && Objects.equals(owner, bike.owner) && Objects.equals(availability, bike.availability) && Objects.equals(status, bike.status) && Objects.equals(notes, bike.notes);
    }

    @Override
    public int hashCode() {
        return Objects.hash(type, id, owner, availability, status, notes);
    }

    @Override
    public String toString() {
        return "{" +
            " type='" + getType() + "'" +
            ", id='" + getId() + "'" +
            ", owner='" + getOwner() + "'" +
            ", availability='" + getAvailability() + "'" +
            ", status='" + getStatus() + "'" +
            ", notes='" + getNotes() + "'" +
            "}";
    }

    public Bike(BikeType bt) {
        this.type = bt;
    }

    public BikeType getType() {
        return this.type;
    }
}