package uk.ac.ed.bikerental;

import java.util.UUID;

public class Invoice extends Quote {
    private PickupMethod pickupMethod;
    private UUID orderNumber;

    
    /** 
     * @param q
     * @return 
     */
    public Invoice(Quote q) {
        super(q);
        this.orderNumber = UUID.randomUUID();
    }

    
    /** 
     * @param pickupMethod
     */
    public void setPickupMethod(PickupMethod pickupMethod) {
        this.pickupMethod = pickupMethod;
    }

    
    /** 
     * @return PickupMethod
     */
    public PickupMethod getPickupMethod() {
        return this.pickupMethod;
    }

    
    /** 
     * @return UUID
     */
    public UUID getUUID() {
        return this.orderNumber;
    }
}
