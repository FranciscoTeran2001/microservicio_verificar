package HealthAnalyzer.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.Instant;

@Entity
@Getter
@Setter
@Table(name = "medical_alerts")
public class MedicalAlert {
    @Id
    @Column(name = "alert_id", nullable = false, unique = true)
    private String alertId; // Ej: "ALT-001" (debes generarlo manualmente)

    @Column(nullable = false) // Añadir restricciones de BD
    private String type;      // "CriticalHeartRateAlert"

    @Column(name = "device_id", nullable = false) // Mapea a snake_case en BD
    private String deviceId;  // "D001"

    @Column(nullable = false, precision = 19, scale = 2) // Ajusta scale según precisión necesaria
    private BigDecimal value;

    @Column(nullable = false, precision = 19, scale = 2)
    private BigDecimal threshold;

    @Column(nullable = false)
    private Instant timestamp; // "2024-04-05T12:05:00Z"
}