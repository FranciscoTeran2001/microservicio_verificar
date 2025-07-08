package HealthAnalyzer.service;

import HealthAnalyzer.dto.*;
import HealthAnalyzer.entity.MedicalAlert;
import HealthAnalyzer.repository.MedicalAlertRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.DoubleSummaryStatistics;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Service
public class AlertService {
    @Autowired
    private MedicalAlertRepository alertRepository;


    public void processVitalSignEvent(NewVitalSignEventDTO event) {
        if (isCritical(event.getType(), event.getValue())) {
            MedicalAlert alert = createAlertFromEvent(event);
            MedicalAlert savedAlert = alertRepository.save(alert);
            sendAlertNotification(savedAlert);
        }
    }

    private MedicalAlert createAlertFromEvent(NewVitalSignEventDTO event) {
        MedicalAlert alert = new MedicalAlert();
        alert.setAlertId("ALT-" + Instant.now().toEpochMilli());
        alert.setType("Critical" + event.getType() + "Alert");
        alert.setDeviceId(event.getDeviceId());
        alert.setValue(event.getValue());
        alert.setThreshold(getThresholdForType(event.getType()));
        alert.setTimestamp(Instant.now());
        return alert;
    }

    private void sendAlertNotification(MedicalAlert alert) {
        MedicalAlertDTO alertDTO = new MedicalAlertDTO();
        alertDTO.setAlertId(alert.getAlertId());
        alertDTO.setType(alert.getType());
        alertDTO.setDeviceId(alert.getDeviceId());
        alertDTO.setValue(alert.getValue());
        alertDTO.setThreshold(alert.getThreshold());
        alertDTO.setTimestamp(alert.getTimestamp());

        // Uncomment when ready to use RabbitMQ
        // streamBridge.send("alerts-out-0", alertDTO);
    }

    private boolean isCritical(String type, BigDecimal value) {
        switch (type.toLowerCase()) {
            case "heart-rate":
                return value.compareTo(new BigDecimal("140")) > 0 ||
                        value.compareTo(new BigDecimal("40")) < 0;
            case "oxygen":
                return value.compareTo(new BigDecimal("90")) < 0;
            case "blood-pressure":
                return value.compareTo(new BigDecimal("180")) > 0;
            default:
                return false;
        }
    }

    private BigDecimal getThresholdForType(String type) {
        return switch (type.toLowerCase()) {
            case "heart-rate" -> new BigDecimal("140");
            case "oxygen" -> new BigDecimal("90");
            case "blood-pressure" -> new BigDecimal("180");
            default -> BigDecimal.ZERO;
        };
    }

    public void generateDailyReport() {
        Instant startTime = Instant.now().minus(24, ChronoUnit.HOURS);
        List<MedicalAlert> alerts = alertRepository.findByTimestampAfter(startTime);

        DailyReportDTO report = new DailyReportDTO();
        report.setDate(Instant.now());
        report.setTotalAlerts(alerts.size());

        // Calcular promedios, máximos y mínimos por tipo (ejemplo para heart-rate)
        DoubleSummaryStatistics stats = alerts.stream()
                .filter(a -> "CriticalHeartRateAlert".equals(a.getType()))
                .mapToDouble(a -> a.getValue().doubleValue())
                .summaryStatistics();

        report.setAverages(Map.of("heart-rate", stats.getAverage()));
        report.setMaxValues(Map.of("heart-rate", stats.getMax()));
        report.setMinValues(Map.of("heart-rate", stats.getMin()));

        //streamBridge.send("reports-out-0", report); // Enviar a RabbitMQ
    }

    public void checkInactiveDevices() {
        Instant thresholdTime = Instant.now().minus(24, ChronoUnit.HOURS); // Dispositivos sin datos en 24h

        // 1. Consultar dispositivos inactivos (requiere método en el repositorio)
        List<String> inactiveDevices = alertRepository.findDevicesOlderThan(thresholdTime);

        // 2. Para cada dispositivo inactivo, generar y enviar evento
        inactiveDevices.forEach(deviceId -> {
            DeviceOfflineEvent event = new DeviceOfflineEvent();
            event.setEventId("EVT-" + UUID.randomUUID());
            event.setDeviceId(deviceId);
            event.setLastActiveTime(thresholdTime);
            event.setTimestamp(Instant.now());

            // 3. Enviar evento a RabbitMQ (requiere StreamBridge inyectado)
            //streamBridge.send("device-offline-events-out-0", event);
        });
    }
}