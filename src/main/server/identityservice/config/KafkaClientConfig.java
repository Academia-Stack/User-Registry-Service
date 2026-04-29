package identityservice.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.Map;

@Data
@Configuration
@ConfigurationProperties(prefix = "kafka")
public class KafkaClientConfig {
    private String host;
    private int port;
    private String bootstrapServers;

    private String username;
    private String password;

    private String topic;
    private String certDir;

    private Ssl ssl;
    private Map<String, String> properties;
    private Producer producer;

    @Data
    public static class Ssl {
        private String truststoreType;
        private String keystoreType;
        private String truststoreLocation;
        private String keystoreLocation;
        private String keyLocation;
    }

    @Data
    public static class Producer {
        private String keySerializer;
        private String valueSerializer;
    }
}