package identityservice.service;

import identityservice.config.TimeZoneConfig;
import org.springframework.cache.annotation.Cacheable;
import identityservice.entity.LogEntry;
import identityservice.repository.LogEntryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class LogService {
    @Autowired
    private LogEntryRepository logRepository;

    @Autowired
    private TimeZoneConfig timeZoneConfig;

    public LogEntry saveLog(LogEntry log) {
        var now = ZonedDateTime.now(ZoneId.of(timeZoneConfig.getDefaultTimeZone()));
        log.setCreatedDate(now.toLocalDate());
        log.setCreatedTime(now.toLocalTime());
        return logRepository.save(log);
    }

    @Cacheable(value = "log", key = "#logId" , unless = "#result.isEmpty()")
    public Optional<LogEntry> findLogById(int logId){
        return logRepository.findById(logId);
    }

    //@Cacheable(value = "log", key = "'list'")
    public List<LogEntry> getAllLogs(){
        return logRepository.findAll();
    }
}
