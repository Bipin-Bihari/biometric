package com.platform.biometric.config;

import org.apache.kafka.clients.admin.NewTopic;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.support.serializer.JsonSerializer;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class KafkaProducerConfig {

    @Value(value = "${spring.kafka.producer.bootstrap-servers}")
    String bootStrapServers;

    @Bean
    public KafkaTemplate<String, Object> getKafkaTemplate() {

        return new KafkaTemplate<>(multiTypeProducerFactory());
    }

    @Bean
    public ProducerFactory<String, Object> multiTypeProducerFactory() {
        Map<String, Object> configProp = new HashMap<>();
        configProp.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        configProp.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class);
        configProp.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, bootStrapServers);
        return new DefaultKafkaProducerFactory<>(configProp);
    }

    @Bean
    public NewTopic topic() {
        //return new NewTopic("foo", 3, (short) 2);
        return TopicBuilder.name("Attendance-Topic").partitions(2).replicas(1).build();
    }
}
