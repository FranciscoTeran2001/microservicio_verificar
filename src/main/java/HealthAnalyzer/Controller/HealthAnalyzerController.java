package HealthAnalyzer.Controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/conjunta/2p/health-analyzer")
public class HealthAnalyzerController {

    // Endpoint opcional de salud (para monitoreo)
    @GetMapping("/health")
    public ResponseEntity<String> healthCheck() {
        return ResponseEntity.ok("HealthAnalyzer is running");
    }

}