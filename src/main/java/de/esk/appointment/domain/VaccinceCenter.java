package de.esk.appointment.domain;

public class VaccinceCenter {
    private String name;
    private String bookingUrl;
    private String availabilityCheckUrl;

    // for Jackson
    public VaccinceCenter() {
    }

    public VaccinceCenter(final String name, final String bookingUrl, final String availabilityCheckUrl) {
        this.name = name;
        this.bookingUrl = bookingUrl;
        this.availabilityCheckUrl = availabilityCheckUrl;
    }

    public String getName() {
        return name;
    }

    public String getBookingUrl() {
        return bookingUrl;
    }

    public String getAvailabilityCheckUrl() {
        return availabilityCheckUrl;
    }
}
