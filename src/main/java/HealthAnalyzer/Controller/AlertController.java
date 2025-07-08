package HealthAnalyzer.Controller;

import HealthAnalyzer.dto.NewVitalSignEventDTO;
import HealthAnalyzer.entity.MedicalAlert;
import HealthAnalyzer.repository.MedicalAlertRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/conjunta/2p/health-analyzer")
public class AlertController {

    @Autowired
    private MedicalAlertRepository alertRepository;

    // Endpoint 1: Obtener alertas por dispositivo (GET)
    @GetMapping("/alerts/{deviceId}")
    public ResponseEntity<List<MedicalAlert>> getAlertsByDevice(
            @PathVariable String deviceId) {
        List<MedicalAlert> alerts = alertRepository.findByDeviceId(deviceId);
        return ResponseEntity.ok(alerts);
    }

    // Endpoint 2: Obtener alertas críticas recientes (GET)
    @GetMapping("/critical-alerts")
    public ResponseEntity<List<MedicalAlert>> getCriticalAlerts() {
        List<MedicalAlert> alerts = alertRepository.findByTypeContaining("Critical");
        return ResponseEntity.ok(alerts);
    }

    // Endpoint 3: Simular evento manual (POST) - Para pruebas
    @PostMapping("/simulate-event")
    public ResponseEntity<String> simulateEvent(
            @RequestBody NewVitalSignEventDTO event) {
        // Lógica para procesar el evento manualmente
        return ResponseEntity.ok("Evento simulado procesado");
    }
}
