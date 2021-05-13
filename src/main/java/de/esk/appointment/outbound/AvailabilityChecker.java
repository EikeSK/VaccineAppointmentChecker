package de.esk.appointment.outbound;

import com.google.gson.Gson;
import de.esk.appointment.domain.CenterCheckResult;
import de.esk.appointment.domain.VaccinceCenter;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

import static java.util.Optional.ofNullable;

public class AvailabilityChecker {
    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    private final VaccinceCenter center;
    private final OkHttpClient httpClient;
    private final Gson gson;

    public AvailabilityChecker(VaccinceCenter center, OkHttpClient httpClient, Gson gson) {
        this.center = center;
        this.httpClient = httpClient;
        this.gson = gson;
    }

    public Optional<CenterCheckResult> check() {
        final var request = new Request.Builder()
                .url(createUrlFor(center))
                .get()
                .build();

        try (Response response = httpClient.newCall(request).execute()) {
            final var centerResponse = gson.fromJson(response.body().string(), CenterResponse.class);
            return ofNullable(centerResponse)
                    .map(resp -> createResult(resp, center));
        } catch (IOException ex) {
            System.out.println("Failed to request " + center.getName());
            return Optional.empty();
        }
    }

    private CenterCheckResult createResult(CenterResponse resp, VaccinceCenter center) {
        return new CenterCheckResult(resp.getTotal(), center.getBookingUrl(), center.getName());
    }

    private String createUrlFor(VaccinceCenter center) {
        final var currentDate = LocalDate.now().format(formatter);
        return String.format("%s&start_date=%s", center.getAvailabilityCheckUrl(), currentDate);
    }


    private static class CenterResponse {
        private int total;

        public CenterResponse() {
        }

        public CenterResponse(int availabilities) {
            this.total = availabilities;
        }

        public int getTotal() {
            return total;
        }
    }
}
