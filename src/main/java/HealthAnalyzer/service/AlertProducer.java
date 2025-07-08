package HealthAnalyzer.service;

import HealthAnalyzer.dto.DeviceOfflineEvent;
import HealthAnalyzer.dto.MedicalAlertDTO;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AlertProducer {

    @Autowired
    private AmqpTemplate amqpTemplate;

    @Autowired
    private ObjectMapper objectMapper;

    public void sendAlert(MedicalAlertDTO alert) {
        try {
            String json = objectMapper.writeValueAsString(alert);
            amqpTemplate.convertAndSend("medical-alerts.queue", json);
        } catch (Exception e) {
            e.printStackTrace();
            // Aquí podrías agregar el fallback a SQLite
        }
    }

    public void sendDeviceOfflineEvent(DeviceOfflineEvent event) {
        try {
            String json = objectMapper.writeValueAsString(event);
            amqpTemplate.convertAndSend("device-offline.queue", json);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}