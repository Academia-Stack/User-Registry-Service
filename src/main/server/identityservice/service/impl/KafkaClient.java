package identityservice.service;

import java.time.Instant;
import java.util.Properties;
import java.util.UUID;

import identityservice.config.KafkaClientConfig;
import identityservice.entity.LogEntry;
import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;

// interface for Producer
import org.apache.kafka.clients.producer.KafkaProducer;
// Actual implementation

import org.apache.kafka.clients.producer.ProducerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class KafkaClient implements IKafkaClient {
    @Autowired
    KafkaClientConfig config;

    KafkaProducer<String, LogEntry> producer;
    Properties props = new Properties();

    @PostConstruct  // init lifecycle method
    private void init() {
        props = new Properties();

        // Core
        props.setProperty("bootstrap.servers", config.getBootstrapServers());

        // SSL (critical)
        props.setProperty("security.protocol", "SSL");

        props.setProperty("ssl.truststore.type", config.getSsl().getTruststoreType());
        props.setProperty("ssl.keystore.type", config.getSsl().getKeystoreType());

        props.setProperty("ssl.truststore.location", config.getSsl().getTruststoreLocation());
        props.setProperty("ssl.keystore.location", config.getSsl().getKeystoreLocation());
        props.setProperty("ssl.key.location", config.getSsl().getKeyLocation());

        // Extra properties (schema registry, auth, etc.)
        if (config.getProperties() != null) {
            config.getProperties().forEach(props::setProperty);
        }

        // Serializers
        props.setProperty("key.serializer", config.getProducer().getKeySerializer());
        props.setProperty("value.serializer", config.getProducer().getValueSerializer());

        producer = new KafkaProducer<>(props);
    }

    public void sendLogEntry(LogEntry log) {
        log.setTimestamp(Instant.now());
        log.setLogId(UUID.randomUUID());  // generate a new id

        try {
            //System.out.println(mapper.writeValueAsString(log));  // debug

            ProducerRecord newEntry =
                    new ProducerRecord<>(config.getTopic(), log.getLogId().toString(), log);
            producer.send(newEntry, (metadata, exception) -> {
                if (exception != null)
                    System.out.println("Failed: " + exception.getMessage()
                            + " caused by " + exception.getCause());
                else System.out.println("Sent to partition "
                        + metadata.partition() + " offset " + metadata.offset());
            });
        }catch (Exception ex) {
            // TODO Auto-generated catch block
            System.out.println(ex.getMessage() + " caused by " + ex.getCause());
        }
    }

    @PreDestroy // lifecycle method for connection-pool cleanup
    private void cleanup() {
        producer.flush();
        producer.close();
    }
}