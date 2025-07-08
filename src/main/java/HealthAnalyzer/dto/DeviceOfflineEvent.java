package HealthAnalyzer.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.Instant;

@Getter
@Setter
public class DeviceOfflineEvent {
    private String eventId;
    private String deviceId;
    private Instant lastActiveTime;
    private Instant timestamp;
}