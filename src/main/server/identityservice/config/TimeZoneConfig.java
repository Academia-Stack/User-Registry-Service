package identityservice.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Data
@Configuration
@ConfigurationProperties(prefix = "timezones")
public class TimeZoneConfig {

    private List<String> zones;

    public String getDefaultTimeZone(){
        return this.zones.getFirst();
    }
}