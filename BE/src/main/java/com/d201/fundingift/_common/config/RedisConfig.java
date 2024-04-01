package com.d201.fundingift._common.config;

import com.d201.fundingift.consumeralarm.entity.ConsumerAlarm;
import com.d201.fundingift.friend.entity.Friend;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

@Configuration
public class RedisConfig {

    @Value("${redis.host}")
    private String host;

    @Value("${redis.port}")
    private int port;

    @Value("${redis.password}")
    private String password;

    @Bean
    public RedisConnectionFactory redisConnectionFactory() {
        RedisStandaloneConfiguration redisStandaloneConfiguration = new RedisStandaloneConfiguration();
        redisStandaloneConfiguration.setHostName(host);
        redisStandaloneConfiguration.setPort(port);
        redisStandaloneConfiguration.setPassword(password);
        return new LettuceConnectionFactory(redisStandaloneConfiguration);
    }

    @Bean
    public RedisTemplate<?, ?> redisTemplate() {
        RedisTemplate<byte[], byte[]> redisTemplate = new RedisTemplate<>();
        redisTemplate.setConnectionFactory(redisConnectionFactory());
        // 직렬화 설정 추가.
        redisTemplate.setKeySerializer(new StringRedisSerializer());
        redisTemplate.setValueSerializer(new StringRedisSerializer());
        return redisTemplate;
    }
    @Bean
    public RedisTemplate<String, Friend> friendRedisTemplate(RedisConnectionFactory connectionFactory) {
        RedisTemplate<String, Friend> template = new RedisTemplate<>();
        template.setConnectionFactory(connectionFactory);
        template.setKeySerializer(new StringRedisSerializer());
        template.setHashValueSerializer(new Jackson2JsonRedisSerializer<>(Friend.class));
        template.setValueSerializer(new Jackson2JsonRedisSerializer<>(Friend.class));
        return template;
    }

    @Bean
    public RedisTemplate<String, ConsumerAlarm> consumerAlarmRedisTemplate(RedisConnectionFactory connectionFactory) {
        RedisTemplate<String, ConsumerAlarm> template = new RedisTemplate<>();
        template.setConnectionFactory(connectionFactory);
        template.setKeySerializer(new StringRedisSerializer());
        // Jackson2JsonRedisSerializer를 사용하여 ConsumerAlarm 객체를 JSON으로 직렬화합니다.
        Jackson2JsonRedisSerializer<ConsumerAlarm> serializer = new Jackson2JsonRedisSerializer<>(ConsumerAlarm.class);
        template.setHashValueSerializer(serializer);
        template.setValueSerializer(serializer);
        return template;
    }

}
