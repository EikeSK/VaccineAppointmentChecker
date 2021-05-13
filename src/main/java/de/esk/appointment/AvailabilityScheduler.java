package de.esk.appointment;

import de.esk.appointment.domain.CenterCheckResult;
import de.esk.appointment.outbound.AvailabilityChecker;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class AvailabilityScheduler {

    private final ScheduledThreadPoolExecutor executor;
    private final List<AvailabilityChecker> availabilityChecker;

    public AvailabilityScheduler(List<AvailabilityChecker> availabilityChecker) {
        this.availabilityChecker = availabilityChecker;
        this.executor = (ScheduledThreadPoolExecutor) Executors.newScheduledThreadPool(availabilityChecker.size());
    }

    public void start() {
        System.out.println("Starting scheduler for " + availabilityChecker.size() + " centers");

        availabilityChecker.stream()
                .map(CheckCommand::new)
                .forEach(command -> executor.scheduleWithFixedDelay(command, 1, 10, TimeUnit.SECONDS));
    }

    private static class CheckCommand implements Runnable {

        private final AvailabilityChecker availabilityChecker;

        public CheckCommand(AvailabilityChecker availabilityChecker) {
            this.availabilityChecker = availabilityChecker;
        }

        @Override
        public void run() {
            final var result = availabilityChecker.check();
            printResult(result);
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

}
