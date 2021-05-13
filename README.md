# VaccineAppointmentChecker

This is a simple Java application that checks periodically for available vaccination appointments in
Berlin. It uses doctolib endpoints under the hood to check availability.

### Prerequisites
- Java 11+
- Maven

### Create executable JAR file
```sh
mvn clean package
```

### Change configured vaccination centers
Vaccination centers are configured in `vaccine-center-config.yml` and can be extended by adjusting
that file.

### Request Scheduler
Requests for each vaccination center are executed in parallel every 10 seconds.
If you want to change the default, you can adjust the scheduling in `AvailabilityScheduler`

e.g.

```java
availabilityChecker.stream()
   .map(CheckCommand::new)
   .forEach(command -> executor.scheduleWithFixedDelay(command, 1, 10, TimeUnit.SECONDS));
    }
```