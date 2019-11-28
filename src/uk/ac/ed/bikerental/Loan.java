package uk.ac.ed.bikerental;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Objects;

public class Loan {

    private String id;
    private Shop store;
    private ArrayList<Bike> bikes;
    private DateRange dates;
    private BigDecimal deposit;
    private LoanStatus status;
    private String returnConditions;
    private PickupMethod pickupMethod;


    public Loan(String id, Shop store, ArrayList<Bike> bikes, DateRange dates, BigDecimal deposit, LoanStatus status, String returnConditions, PickupMethod pickupMethod) {
        this.id = id;
        this.store = store;
        this.bikes = bikes;
        this.dates = dates;
        this.deposit = deposit;
        this.status = status;
        this.returnConditions = returnConditions;
        this.pickupMethod = pickupMethod;
    }

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Shop getStore() {
        return this.store;
    }

    public void setStore(Shop store) {
        this.store = store;
    }

    public ArrayList<Bike> getBikes() {
        return this.bikes;
    }

    public void setBikes(ArrayList<Bike> bikes) {
        this.bikes = bikes;
    }

    public DateRange getDates() {
        return this.dates;
    }

    public void setDates(DateRange dates) {
        this.dates = dates;
    }

    public BigDecimal getDeposit() {
        return this.deposit;
    }

    public void setDeposit(BigDecimal deposit) {
        this.deposit = deposit;
    }

    public LoanStatus getStatus() {
        return this.status;
    }

    public void setStatus(LoanStatus status) {
        this.status = status;
    }

    public String getReturnConditions() {
        return this.returnConditions;
    }

    public void setReturnConditions(String returnConditions) {
        this.returnConditions = returnConditions;
    }

    public PickupMethod getPickupMethod() {
        return this.pickupMethod;
    }

    public void setPickupMethod(PickupMethod pickupMethod) {
        this.pickupMethod = pickupMethod;
    }

    public Loan id(String id) {
        this.id = id;
        return this;
    }

    public Loan store(Shop store) {
        this.store = store;
        return this;
    }

    public Loan bikes(ArrayList<Bike> bikes) {
        this.bikes = bikes;
        return this;
    }

    public Loan dates(DateRange dates) {
        this.dates = dates;
        return this;
    }

    public Loan deposit(BigDecimal deposit) {
        this.deposit = deposit;
        return this;
    }

    public Loan status(LoanStatus status) {
        this.status = status;
        return this;
    }

    public Loan returnConditions(String returnConditions) {
        this.returnConditions = returnConditions;
        return this;
    }

    public Loan pickupMethod(PickupMethod pickupMethod) {
        this.pickupMethod = pickupMethod;
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this)
            return true;
        if (!(o instanceof Loan)) {
            return false;
        }
        Loan loan = (Loan) o;
        return Objects.equals(id, loan.id) && Objects.equals(store, loan.store) && Objects.equals(bikes, loan.bikes) && Objects.equals(dates, loan.dates) && Objects.equals(deposit, loan.deposit) && Objects.equals(status, loan.status) && Objects.equals(returnConditions, loan.returnConditions) && Objects.equals(pickupMethod, loan.pickupMethod);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, store, bikes, dates, deposit, status, returnConditions, pickupMethod);
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