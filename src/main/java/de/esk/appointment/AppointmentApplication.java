package de.esk.appointment;

import com.google.gson.Gson;
import de.esk.appointment.config.VaccineCenterFactory;
import de.esk.appointment.domain.VaccinceCenter;
import de.esk.appointment.outbound.AvailabilityChecker;
import okhttp3.OkHttpClient;

import java.time.Duration;
import java.util.List;
import java.util.stream.Collectors;

public class AppointmentApplication {

	public static void main(String[] args) {

		final Gson gson = new Gson();
		final OkHttpClient httpClient = new OkHttpClient.Builder()
				.connectTimeout(Duration.ofSeconds(10))
				.readTimeout(Duration.ofSeconds(10))
				.callTimeout(Duration.ofSeconds(15))
				.build();
		final VaccineCenterFactory vaccineCenterFactory = new VaccineCenterFactory();
		final var centers = vaccineCenterFactory.createInstances();

		if (centers.size() == 0) {
			System.out.println("No center configured, exiting...");
			return;
		}

		final var availabilityChecker = createAppointmentChecker(centers, gson, httpClient);
		final var scheduler = new AvailabilityScheduler(availabilityChecker);
		scheduler.start();

	}

	private static List<AvailabilityChecker> createAppointmentChecker(List<VaccinceCenter> centers,
																	  Gson gson,
																	  OkHttpClient httpClient) {
		return centers.stream()
				.map(center -> new AvailabilityChecker(center, httpClient, gson))
				.collect(Collectors.toList());
	}

}
