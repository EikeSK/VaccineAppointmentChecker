package de.esk.appointment.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import de.esk.appointment.domain.VaccinceCenter;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;


public class VaccineCenterFactory {

    private ConfigFile configFile;

    public VaccineCenterFactory() throws IOException {
        final ObjectMapper om = new ObjectMapper(new YAMLFactory());
        final ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        final File file = new File(classLoader.getResource("vaccine-center-config.yml").getFile());
        configFile = om.readValue(file, ConfigFile.class);
    }

    public List<VaccinceCenter> createInstances() {
        return configFile.vaccineCenters;
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
