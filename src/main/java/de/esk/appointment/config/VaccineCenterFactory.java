package de.esk.appointment.config;

import de.esk.appointment.domain.VaccinceCenter;

import java.util.Arrays;
import java.util.List;

// TODO: read yaml file
public class VaccineCenterFactory {
    public List<VaccinceCenter> createInstances() {
        return Arrays.asList(
                new VaccinceCenter(
                        "Messe Berlin/ Halle 21",
                        "https://www.doctolib.de/institut/berlin/ciz-berlin-berlin?pid=practice-158434",
                        ""
                )
        )
    }
}
