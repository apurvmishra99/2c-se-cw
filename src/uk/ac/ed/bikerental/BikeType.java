package uk.ac.ed.bikerental;

import java.math.BigDecimal;
import java.util.Objects;

public class BikeType {
    private String name;
    private BigDecimal replacementValue;

    public BikeType(String s, BigDecimal replacementValue) {
        this.name = s;
        this.replacementValue = replacementValue;
    }

    public String getName() {
        return this.name;
    }

    public BigDecimal getReplacementValue() {
        return this.replacementValue;
    }

    /**
     * HashCode used to compare objects.
     * @return hash
     */
    @Override
    public int hashCode() {
        // hashCode method allowing use in collections
        return Objects.hash(this.name);
    }

    /**
     * Override equals method.
     * 
     * @return True if the names are equal, False otherwise
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
        BikeType other = (BikeType) obj;
        return Objects.equals(this.name, other.getName());
    }
}
