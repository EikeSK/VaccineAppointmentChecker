package de.esk.appointment;


import de.esk.appointment.config.VaccineCenterFactory;

import java.io.IOException;

public class AppointmentApplication {

	public static void main(String[] args) {

		VaccineCenterFactory vaccineCenterFactory = null;

		try {
			vaccineCenterFactory = new VaccineCenterFactory();
		} catch (IOException e) {
			System.out.println("Failed to load vaccine center configuration");
		}

		if (vaccineCenterFactory == null || vaccineCenterFactory.createInstances().size() == 0) {
			System.out.println("No center configured, exiting...");
			return;
		}
		final var centers = vaccineCenterFactory.createInstances();
		System.out.println("loaded " + centers.size() + " vaccine centers");

	}

}
