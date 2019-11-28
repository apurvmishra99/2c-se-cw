package uk.ac.ed.bikerental;

import java.util.UUID;

public class Invoice extends Quote {
    private PickupMethod pickupMethod;
    private UUID orderNumber;

    public Invoice(Quote q, PickupMethod pickupMethod) {
        super(q);
        this.orderNumber = UUID.randomUUID();
    }

    public PickupMethod getPickupMethod() {
        return this.pickupMethod;
    }

    public void setPickupMethod(PickupMethod pickupMethod) {
        this.pickupMethod = pickupMethod;
    }

    public Invoice pickupMethod(PickupMethod pickupMethod) {
        this.pickupMethod = pickupMethod;
        return this;
    }
}
