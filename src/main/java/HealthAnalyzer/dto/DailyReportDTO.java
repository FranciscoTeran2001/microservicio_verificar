package HealthAnalyzer.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.Instant;
import java.util.Map;

@Getter
@Setter
public class DailyReportDTO {
    private String reportId;      // Ej: "REP-20240405"
    private Instant date;         // Fecha del reporte
    private Map<String, Double> averages;  // Ej: {"heart-rate": 72.5, "oxygen": 95.0}
    private Map<String, Double> maxValues; // Ej: {"heart-rate": 140.0}
    private Map<String, Double> minValues; // Ej: {"heart-rate": 60.0}
    private int totalAlerts;     // Número de alertas generadas
}