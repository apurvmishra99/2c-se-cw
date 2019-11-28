package uk.ac.ed.bikerental;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

public class Controller {

    private CurrentAction action;
    private UUID loginID;
    private Set<Shop> shops;

    public Controller() {
        this.action = CurrentAction.VIEW;
        this.loginID = null;
        this.shops = new HashSet<Shop>();
    }

    public CurrentAction getAction() {
        return this.action;
    }

    public UUID getLoginID() {
        return this.loginID;
    }

    public Set<Shop> getShops() {
        return this.shops;
    }

    public void login(UUID shopID) {
        this.loginID = shopID;
    }

    public void addShop(Shop s) {
        this.shops.add(s);
    }

    @Override
    public String toString() {
        return "{" +
            " action='" + getAction() + "'" +
            ", loginID='" + getLoginID() + "'" +
            ", shops='" + getShops() + "'" +
            "}";
    }
}
