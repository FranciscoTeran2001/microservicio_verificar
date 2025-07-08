package HealthAnalyzer.config;

import HealthAnalyzer.service.AlertService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

@Configuration
@EnableScheduling
public class SchedulingConfig {

    @Autowired
    private AlertService reportService;

    // Reporte diario a medianoche
    @Scheduled(cron = "0 0 0 * * ?")
    public void generateDailyReport() {
        try {
            reportService.generateDailyReport();
        } catch (Exception e) {
            System.err.println("Error generando reporte diario: " + e.getMessage());
        }
    }

    // Verificación de dispositivos inactivos cada 6 horas
    @Scheduled(cron = "0 0 */6 * * ?")
    public void checkInactiveDevices() {
        try {
            reportService.checkInactiveDevices();
        } catch (Exception e) {
            System.err.println("Error verificando dispositivos: " + e.getMessage());
        }
    }
}
