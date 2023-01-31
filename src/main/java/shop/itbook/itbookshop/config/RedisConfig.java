package shop.itbook.itbookshop.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.beans.factory.BeanClassLoaderAware;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import shop.itbook.itbookshop.productgroup.product.fileservice.init.Token;

/**
 * redis에 대한 설정 파일입니다.
 *
 * @author 이하늬
 * @since 1.0
 */
@Configuration
@ConfigurationProperties(prefix = "redis")
public class RedisConfig implements BeanClassLoaderAware {
    private int port;
    private String host;
    private String password;
    private int database;
    private ClassLoader classLoader;

    @Bean
    public RedisConnectionFactory redisConnectionFactory() {

        RedisStandaloneConfiguration configuration = new RedisStandaloneConfiguration();
        configuration.setHostName(this.host);
        configuration.setPort(this.port);
        configuration.setPassword(this.password);
        configuration.setDatabase(this.database);

        return new LettuceConnectionFactory(configuration);
    }

    @Bean
    public RedisTemplate<?, ?> redisTemplate() {
        RedisTemplate<byte[], byte[]> redisTemplate = new RedisTemplate<>();
        redisTemplate.setConnectionFactory(redisConnectionFactory());
        redisTemplate.setKeySerializer(new StringRedisSerializer());
        redisTemplate.setValueSerializer(new GenericJackson2JsonRedisSerializer(objectMapper()));
        redisTemplate.setHashKeySerializer(new StringRedisSerializer());
        redisTemplate.setHashValueSerializer(
            new GenericJackson2JsonRedisSerializer(objectMapper()));

        return redisTemplate;
    }

    public ObjectMapper objectMapper() {
        ObjectMapper mapper = new ObjectMapper();
        mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS); // timestamp 형식 안따르도록 설정
        mapper.registerModules(new JavaTimeModule(), new Jdk8Module());

        return mapper;
    }


    @Override
    public void setBeanClassLoader(ClassLoader classLoader) {
        this.classLoader = classLoader;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getDatabase() {
        return database;
    }

    public void setDatabase(int database) {
        this.database = database;
    }

    public ClassLoader getClassLoader() {
        return classLoader;
    }
}
