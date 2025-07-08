package HealthAnalyzer.dto;


import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.Instant;

@Getter
@Setter
public class MedicalAlertDTO {
    private String alertId;
    private String type;
    private String deviceId;
    private BigDecimal value;      // BigDecimal aquí también
    private BigDecimal threshold; // BigDecimal aquí también
    private Instant timestamp;
}