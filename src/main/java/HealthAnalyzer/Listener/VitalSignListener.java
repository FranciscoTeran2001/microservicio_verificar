package HealthAnalyzer.Listener;

import HealthAnalyzer.dto.NewVitalSignEventDTO;
import HealthAnalyzer.service.AlertService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class VitalSignListener {

    @Autowired
    private AlertService alertService;

    @Autowired
    private ObjectMapper objectMapper;

    @RabbitListener(queues = "vital-signs.queue")
    public void handleVitalSignEvent(String message) {
        try {
            NewVitalSignEventDTO event = objectMapper.readValue(message, NewVitalSignEventDTO.class);
            alertService.processVitalSignEvent(event);
        } catch (Exception e) {
            e.printStackTrace();
            // Manejo de errores (ej: enviar a DLQ)
        }
    }
}