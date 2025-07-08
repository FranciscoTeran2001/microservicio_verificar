package HealthAnalyzer.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class NewVitalSignEventDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    private String eventId;           // "EVT-001"
    private String eventType;        // "NewVitalSignEvent"
    private String deviceId;         // "D001"
    private String type;             // "heart-rate", "oxygen", etc.
    private BigDecimal value;        // 135.0 (usamos BigDecimal para precisión)
    private LocalDateTime timestamp; // Cuando se capturó el dato médico
    private LocalDateTime eventTimestamp; // Cuando se emitió el evento
    private String source;           // "PatientDataCollector"
    private String version;          // "1.0.0"
    private String correlationId;    // ID para trazar el flujo (opcional)
}