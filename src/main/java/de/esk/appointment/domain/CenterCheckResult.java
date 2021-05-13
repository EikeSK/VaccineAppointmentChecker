package de.esk.appointment.domain;

public class CenterCheckResult {
    private final String nextAvailableSlot;
    private final String bookingUrl;
    private final String centerName;

    public CenterCheckResult(String nextAvailableSlot, String bookingUrl, String centerName) {
        this.nextAvailableSlot = nextAvailableSlot;
        this.bookingUrl = bookingUrl;
        this.centerName = centerName;
    }

    public String getNextAvailableSlot() {
        return nextAvailableSlot;
    }

    public String getBookingUrl() {
        return bookingUrl;
    }

    public String getCenterName() {
        return centerName;
    }
}
