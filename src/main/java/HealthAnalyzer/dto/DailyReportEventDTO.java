package HealthAnalyzer.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.util.Map;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DailyReportEventDTO {
    private String eventId;
    private String eventType = "DailyReportGenerated"; // Tipo fijo
    private Instant reportDate;  // Fecha del reporte
    private Map<String, Double> averages;  // Promedios por tipo
    private Map<String, Double> maxValues; // Máximos
    private Map<String, Double> minValues; // Mínimos
    private int totalAlerts;    // Total de alertas
    private Instant timestamp;
}
