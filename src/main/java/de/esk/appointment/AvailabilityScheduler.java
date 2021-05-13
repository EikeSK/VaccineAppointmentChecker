package de.esk.appointment;

import de.esk.appointment.domain.CenterCheckResult;
import de.esk.appointment.outbound.AvailabilityChecker;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class AvailabilityScheduler {

    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
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
            availabilityChecker.check()
                    .ifPresent(this::printResult);
        }

        private void printResult(CenterCheckResult result) {
            final var timestamp = LocalDateTime.now().format(formatter);
            String message;
            final var availableAppointments = result.getAvailableAppointments();
            if (availableAppointments > 0) {
                message = String.format("%s:  %-35s: %10d results - %s",
                        timestamp,
                        result.getCenterName(),
                        availableAppointments,
                        result.getBookingUrl());
            } else {
                message = String.format("%s:  %-35s: %10d results",
                        timestamp,
                        result.getCenterName(),
                        availableAppointments);
            }
            System.out.println(message);
        }
    }

}
