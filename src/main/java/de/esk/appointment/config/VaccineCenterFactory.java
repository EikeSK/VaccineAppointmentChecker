package de.esk.appointment.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import de.esk.appointment.domain.VaccinceCenter;

import java.io.File;
import java.io.IOException;
import java.util.List;

import static java.util.Collections.emptyList;
import static java.util.Optional.ofNullable;


public class VaccineCenterFactory {

    private ConfigFile configFile;

    public VaccineCenterFactory() {
        final ObjectMapper om = new ObjectMapper(new YAMLFactory());
        final ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        final File file = new File(classLoader.getResource("vaccine-center-config.yml").getFile());
        try {
            configFile = om.readValue(file, ConfigFile.class);
        } catch (IOException e) {
            System.out.println("Failed to load vaccine center configuration");
        }
    }

    public List<VaccinceCenter> createInstances() {
        return ofNullable(configFile)
                .map(ConfigFile::getVaccineCenters)
                .orElse(emptyList());
    }

    public static class ConfigFile {
        public List<VaccinceCenter> vaccineCenters;

        public ConfigFile(List<VaccinceCenter> vaccineCenters) {
            this.vaccineCenters = vaccineCenters;
        }

        public ConfigFile() {
        }

        public List<VaccinceCenter> getVaccineCenters() {
            return vaccineCenters;
        }

    }

}
