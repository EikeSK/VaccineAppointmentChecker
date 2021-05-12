package de.esk.appointment.domain;

public class VaccinceCenter {
    private final String centerName;
    private final String bookingUrl;
    private final String availabilityCheckUrl;

    public VaccinceCenter(final String centerName, final String bookingUrl, final String availabilityCheckUrl) {
        this.centerName = centerName;
        this.bookingUrl = bookingUrl;
        this.availabilityCheckUrl = availabilityCheckUrl;
    }

    public String getCenterName() {
        return centerName;
    }

    public String getBookingUrl() {
        return bookingUrl;
    }

    public String getAvailabilityCheckUrl() {
        return availabilityCheckUrl;
    }
}
