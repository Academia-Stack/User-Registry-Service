package identityservice.service;

import identityservice.entity.LogEntry;

public interface IKafkaClient {
    void sendLogEntry(LogEntry log);
}
