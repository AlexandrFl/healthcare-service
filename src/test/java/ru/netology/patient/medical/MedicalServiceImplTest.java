package ru.netology.patient.medical;


import org.junit.jupiter.api.*;
import org.junit.jupiter.api.Test;


import org.mockito.Mockito;
import ru.netology.patient.entity.BloodPressure;
import ru.netology.patient.entity.HealthInfo;
import ru.netology.patient.entity.PatientInfo;
import ru.netology.patient.repository.PatientInfoFileRepository;
import ru.netology.patient.repository.PatientInfoRepository;
import ru.netology.patient.service.alert.SendAlertService;
import ru.netology.patient.service.alert.SendAlertServiceImpl;
import ru.netology.patient.service.medical.MedicalService;
import ru.netology.patient.service.medical.MedicalServiceImpl;

import java.math.BigDecimal;
import java.time.LocalDate;

import static org.mockito.ArgumentMatchers.anyString;


public class MedicalServiceImplTest {
    @BeforeAll
    public static void start() {
        System.out.println(">>>Тестирование началось<<<");
    }

    @BeforeEach
    public void startEach() {
        System.out.println("*Тест запущен*");
    }

    @AfterEach
    public void finishEach() {
        System.out.println("Тест завершен успешно\n");
    }

    @AfterAll
    public static void finish() {
        System.out.println("<<<Тестирование завершено>>>");
    }

    @Test
    void checkBloodPressureTest() {
        String id = "1";

        PatientInfo patientInfo = new PatientInfo(id, "Вупсень", "Петров", LocalDate.of(1980, 11, 26),
                new HealthInfo(new BigDecimal("36.65"), new BloodPressure(120, 80)));

        PatientInfoRepository patientInfoRepository = Mockito.mock(PatientInfoFileRepository.class);
        Mockito.when(patientInfoRepository.getById(id)).
                thenReturn(patientInfo);

        SendAlertService alertService = new SendAlertServiceImpl();
        MedicalService medicalService = new MedicalServiceImpl(patientInfoRepository, alertService);

        BloodPressure currentPressure = new BloodPressure(0, 0);
        medicalService.checkBloodPressure(id, currentPressure);
    }

    @Test
    void checkTemperatureTest() {
        String id = "2";

        PatientInfo patientInfo = new PatientInfo(id, "Пупсень", "Петров", LocalDate.of(1980, 11, 26),
                new HealthInfo(new BigDecimal("36.65"), new BloodPressure(120, 80)));

        PatientInfoRepository patientInfoRepository = Mockito.mock(PatientInfoFileRepository.class);
        Mockito.when(patientInfoRepository.getById(id)).
                thenReturn(patientInfo);

        SendAlertService alertService = new SendAlertServiceImpl();
        MedicalService medicalService = new MedicalServiceImpl(patientInfoRepository, alertService);

        BigDecimal currentTemperature = new BigDecimal("35");
        medicalService.checkTemperature(id, currentTemperature);
    }

    @Test
    void sendTest() {
        String id = "2";

        PatientInfo patientInfo = new PatientInfo(id, "Пупсень", "Петров", LocalDate.of(1980, 11, 26),
                new HealthInfo(new BigDecimal("36.65"), new BloodPressure(120, 80)));

        PatientInfoRepository patientInfoRepository = Mockito.mock(PatientInfoFileRepository.class);
        Mockito.when(patientInfoRepository.getById(id)).
                thenReturn(patientInfo);

        SendAlertService alertService = Mockito.spy(SendAlertServiceImpl.class);

        MedicalService medicalService = new MedicalServiceImpl(patientInfoRepository, alertService);

        BigDecimal currentTemperature = new BigDecimal("36");
        medicalService.checkTemperature(id, currentTemperature);

        Mockito.verify(alertService, Mockito.times(0)).send(anyString());





    }
}
