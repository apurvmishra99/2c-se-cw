package uk.ac.ed.bikerental;

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

    
    /** 
     * @param type
     * @param owner
     * @return 
     */
    public Bike(BikeType type, Shop owner) {
        this(type, owner, LocalDate.now(), "");
    }

    
    /** 
     * @param type
     * @param owner
     * @param manufactureDate
     * @param notes
     * @return 
     */
    public Bike(BikeType type, Shop owner, LocalDate manufactureDate, String notes) {
        this.type = type;
        this.id = UUID.randomUUID();
        this.owner = owner;
        this.manufactureDate = manufactureDate;
        this.bookingDates = new HashSet<DateRange>();
        this.status = BikeStatus.AVAILABLE;
        this.notes = notes;
    }

    
    /** 
     * @param s
     */
    public void updateBikeStatus(BikeStatus s) {
        this.status = s;
    }

    
    /** 
     * @param dateRange
     * @return boolean
     */
    public boolean book(DateRange dateRange) {
        assert (dateRange.isInFuture());
        if (isAvailable(dateRange)) {
            bookingDates.add(dateRange);
            return true;
        }
        return false;
    }

    
    /** 
     * @param dateRange
     * @return boolean
     */
    public boolean isAvailable(DateRange dateRange) {
        assert (dateRange.isInFuture());
        for (DateRange d : bookingDates) {
            if (dateRange.overlaps(d)) {
                return false;
            }
        }
        return true;
    }

    
    /** 
     * @return BikeType
     */
    public BikeType getType() {
        return this.type;
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
    public Shop getOwner() {
        return this.owner;
    }

    
    /** 
     * @return Set<DateRange>
     */
    public Set<DateRange> getBookingDates() {
        return this.bookingDates;
    }

    
    /** 
     * @return LocalDate
     */
    public LocalDate getManufactureDate() {
        return this.manufactureDate;
    }

    
    /** 
     * @return BikeStatus
     */
    public BikeStatus getStatus() {
        return this.status;
    }

    
    /** 
     * @return String
     */
    public String getNotes() {
        return this.notes;
    }

    
    /** 
     * @return String
     */
    @Override
    public String toString() {
        return "{" +
            " type='" + getType() + "'\n" +
            ", id='" + getId() + "'\n" +
            ", owner='" + getOwner() + "'\n" +
            ", status='" + getStatus() + "'\n" +
            ", notes='" + getNotes() + "'\n" +
            "}\n";
    }
}