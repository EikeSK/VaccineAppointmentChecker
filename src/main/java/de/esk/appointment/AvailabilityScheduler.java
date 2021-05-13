package de.esk.appointment;

import de.esk.appointment.domain.CenterCheckResult;
import de.esk.appointment.outbound.AvailabilityChecker;

import java.util.List;
import java.util.Optional;

public class AvailabilityScheduler {

    private final List<AvailabilityChecker> availabilityChecker;

    public AvailabilityScheduler(List<AvailabilityChecker> availabilityChecker) {
        this.availabilityChecker = availabilityChecker;
    }

    public void start() {
        System.out.println("Starting scheduler for " + availabilityChecker.size() + " centers");
        availabilityChecker.parallelStream()
                .map(AvailabilityChecker::check)
                .forEach(this::printResult);
    }

    private void printResult(Optional<CenterCheckResult> centerCheckResult) {
        centerCheckResult.ifPresent(result -> {
            String message;
            final var availableAppointments = result.getAvailableAppointments();
            if (availableAppointments > 0) {
                message = String.format("%-30s: %10d results - %s", result.getCenterName(), availableAppointments, result.getBookingUrl());
            } else {
                message = String.format("%-30s: %10d results", result.getCenterName(), availableAppointments);
            }
            System.out.println(message);
        });
    }

}
