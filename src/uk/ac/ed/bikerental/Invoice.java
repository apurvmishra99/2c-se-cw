package uk.ac.ed.bikerental;

import java.util.UUID;

public class Invoice extends Quote {
    private PickupMethod pickupMethod;
    private UUID orderNumber;

    public Invoice(Quote q) {
        super(q);
        this.orderNumber = UUID.randomUUID();
    }

    public void setPickupMethod(PickupMethod pickupMethod) {
        this.pickupMethod = pickupMethod;
    }

    public PickupMethod getPickupMethod() {
        return this.pickupMethod;
    }

    public UUID getUUID() {
        return this.orderNumber;
    }
}
