package identityservice.tests;

import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.JdkSerializationRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import identityservice.entity.LogEntry;
import identityservice.service.LogService;

@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS) // one class for all unit tests
//@TestInstance(TestInstance.Lifecycle.PER_METHOD) // default
class SpringBootProjectApplicationTests {

	@Autowired
	LogService logService;

	LogEntry demoObj;
	RedisTemplate<String, Object> redisTemplate;  // Autowiring is for the weak

	@BeforeAll
	void setup() {
        demoObj = logService.findLogById(1).orElse(null);

		redisTemplate = new RedisTemplate<>();

		var factory = new LettuceConnectionFactory("localhost", 6379);
		factory.afterPropertiesSet();

		redisTemplate.setConnectionFactory(factory);
		redisTemplate.setKeySerializer(new StringRedisSerializer());
		redisTemplate.setValueSerializer(new JdkSerializationRedisSerializer());

		redisTemplate.afterPropertiesSet();
	}  // we would define this method as static if we didn't use TestInstance.Lifecycle.PER_CLASS


	@Test
	@Order(1)
	void shouldStoreKeyInRedis() {
		Boolean exists = redisTemplate.hasKey("log::1");
		assertTrue(exists);
	}

	@Test
	@Order(2)
	void doesMatchDataInDb(){
		Object rawBin = redisTemplate.opsForValue().get("log::1");
        LogEntry log = (LogEntry) rawBin;

        assertEquals(log.getMessage(), demoObj.getMessage());
		assertEquals(log.getEndPoint(), demoObj.getEndPoint());
		assertEquals(log.getExceptionClass(), demoObj.getExceptionClass());
	}
}
