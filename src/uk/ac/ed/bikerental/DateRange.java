package uk.ac.ed.bikerental;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.Objects;
// import java.util.function.BooleanSupplier;

public class DateRange {

    /**
     * Start date
     */
    private LocalDate start;

    /**
     * End date
     */
    private LocalDate end;

    /**
     * DateRange constructor.
     *
     * @param start Start date
     * @param end   End date
     * @return DateRange object
     */
    public DateRange(LocalDate start, LocalDate end) {
        this.start = start;
        this.end = end;
    }

    /**
     * start date getter.
     *
     * @return start date
     */
    public LocalDate getStart() {
        return this.start;
    }

    /**
     * end date getter.
     *
     * @return end date
     */
    public LocalDate getEnd() {
        return this.end;
    }

    /**
     * Calculate years between start and end date.
     *
     * @return years
     */
    public long toYears() {
        return ChronoUnit.YEARS.between(this.getStart(), this.getEnd());
    }

    /**
     * Calculate days between start and end date.
     *
     * @return days
     */
    public long toDays() {
        return ChronoUnit.DAYS.between(this.getStart(), this.getEnd());
    }

    /**
     * Check if two DateRange(s) overlap.
     *
     * @param  other
     * @return True if they overlap, False otherwise
     */
    public Boolean overlaps(DateRange other) {
        // If starts after other, then should also end before other.
        // Similarly applied to opposite
        if (this.start.compareTo(other.getStart()) > 0) {
            return this.start.compareTo(other.getEnd()) < 0;
        } else if (this.start.compareTo(other.getStart()) < 0) {
            return this.end.compareTo(other.getStart()) > 0;
        }
        return true;
    }

    /**
     * HashCode used to compare objects.
     * @return hash
     */
    @Override
    public int hashCode() {
        // hashCode method allowing use in collections
        return Objects.hash(end, start);
    }

    /**
     * Override equals method.
     * 
     * @return True if the objects are equal, False otherwise
     */
    @Override
    public boolean equals(Object obj) {
        // equals method for testing equality in tests
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        DateRange other = (DateRange) obj;
        return Objects.equals(end, other.end) && Objects.equals(start, other.start);
    }

}
