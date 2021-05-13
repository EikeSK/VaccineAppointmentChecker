package de.esk.appointment.domain;

public class CenterCheckResult {
    private final int availableAppointments;
    private final String bookingUrl;
    private final String centerName;

    public CenterCheckResult(int availableAppointments, String bookingUrl, String centerName) {
        this.availableAppointments = availableAppointments;
        this.bookingUrl = bookingUrl;
        this.centerName = centerName;
    }

    public int getAvailableAppointments() {
        return availableAppointments;
    }

    public String getBookingUrl() {
        return bookingUrl;
    }

    public String getCenterName() {
        return centerName;
    }
}
