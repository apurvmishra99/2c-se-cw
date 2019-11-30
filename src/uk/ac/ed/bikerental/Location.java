package uk.ac.ed.bikerental;

public class Location {

    /**
     * Location's postcode.
     */
    private String postcode;

    /**
     * Location's address.
     */
    private String address;

    /**
     * Location constructor.
     *
     * @param postcode Postcode (case insensitive)
     * @param address  Address
     * @return Location object.
     */
    public Location(String postcode, String address) {
        assert postcode.length() >= 6;
        this.postcode = postcode.toUpperCase();
        this.address = address;
    }

    /**
     * Location constructor.
     *
     * @param otherLocation Location object to check vicinity with
     * @return True if the locations are near each other.
     */
    public boolean isNearTo(Location otherLocation) {
        if (this.postcode.substring(0, 2).equals(otherLocation.postcode.substring(0, 2))) {
            return true;
        }
        return false;
    }

    /**
     * Postcode getter.
     *
     * @return Object's postcode.
     */
    public String getPostcode() {
        return postcode;
    }

    /**
     * Address getter.
     *
     * @return Object's address.
     */
    public String getAddress() {
        return address;
    }
}
