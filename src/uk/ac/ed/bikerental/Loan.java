package uk.ac.ed.bikerental;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Set;
import java.util.UUID;

public class Loan {

    private UUID id;
    private Shop store;
    private Set<Bike> bikes;
    private DateRange dates;
    private BigDecimal deposit;
    private LoanStatus status;
    private String returnConditions;
    private PickupMethod pickupMethod;


    public Loan(Shop store, Set<Bike> bikes, DateRange dates, BigDecimal deposit,
                LoanStatus status, PickupMethod pickupMethod) {
        this.id = UUID.randomUUID();
        this.store = store;
        this.bikes = bikes;
        this.dates = dates;
        this.deposit = deposit;
        this.status = status;;
        this.pickupMethod = pickupMethod;
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

    public LoanStatus getStatus() {
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