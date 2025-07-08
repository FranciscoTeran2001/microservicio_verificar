package HealthAnalyzer.config;

import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class RabbitMQConfig {

    // Cola para recibir eventos NewVitalSignEvent (requerimiento obligatorio)
    @Bean
    public Queue vitalSignQueue() {
        return new Queue("new.vital.sign.queue", true); // durable=true
    }

    // Cola para enviar alertas médicas (requerimiento obligatorio)
    @Bean
    public Queue alertsQueue() {
        return new Queue("medical-alerts.queue", true);
    }
}
