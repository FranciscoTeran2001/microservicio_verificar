package HealthAnalyzer.repository;


import HealthAnalyzer.entity.MedicalAlert;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.Instant;
import java.util.List;

public interface MedicalAlertRepository extends JpaRepository<MedicalAlert, String> {

    // Buscar alertas por deviceId (para el endpoint GET /alerts/{deviceId})
    List<MedicalAlert> findByDeviceId(String deviceId);

    // Buscar alertas después de una fecha (para reportes diarios)
    List<MedicalAlert> findByTimestampAfter(Instant timestamp);

    @Query("SELECT DISTINCT a.deviceId FROM MedicalAlert a WHERE a.timestamp < :thresholdTime")
    List<String> findDevicesOlderThan(Instant thresholdTime);


}