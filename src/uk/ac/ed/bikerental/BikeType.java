package uk.ac.ed.bikerental;

import java.math.BigDecimal;
import java.util.Objects;

public class BikeType {
    private String name;
    private BigDecimal replacementValue;
    private BigDecimal depreciationRate;

    public BikeType(String s, BigDecimal replacementValue, BigDecimal depreciationRate) {
        this.name = s;
        this.replacementValue = replacementValue;
        this.depreciationRate = depreciationRate;
    }

    public String getName() {
        return this.name;
    }

    public BigDecimal getReplacementValue() {
        return this.replacementValue;
    }

    public BigDecimal getDepreciationRate() {
        return this.depreciationRate;
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
