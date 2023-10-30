package com.example.senderservice.configurations;

import com.example.senderservice.dtos.MessageDTO;
import lombok.RequiredArgsConstructor;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.listener.ContainerProperties;
import org.springframework.kafka.support.serializer.JsonDeserializer;
import org.springframework.retry.support.RetryTemplate;

import java.time.LocalTime;
import java.util.HashMap;
import java.util.Map;

@Configuration
@RequiredArgsConstructor
public class KafkaConsumerConfig {

    @Value("${spring.kafka.consumer.bootstrap-servers}")
    private String BOOTSTRAP_SERVERS_CONFIG;

    @Value("${spring.kafka.consumer.group-id}")
    private String GROUP_ID_CONFIG;

    @Value("${spring.kafka.consumer.key-deserializer}")
    private String KEY_DESERIALIZER_CLASS_CONFIG;

    @Value("${spring.kafka.consumer.value-deserializer}")
    private String VALUE_DESERIALIZER_CLASS_CONFIG;

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, MessageDTO> kafkaListenerContainerFactory() {
        ConcurrentKafkaListenerContainerFactory<String, MessageDTO> factory = new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(consumerFactory());
        factory.setConcurrency(3);
        factory.getContainerProperties().setPollTimeout(3000);
        factory.getContainerProperties().setAckMode(ContainerProperties.AckMode.MANUAL_IMMEDIATE);
        factory.setRecordFilterStrategy(record -> {
            if(record.value().getFromTime().getMinute() == LocalTime.now().getMinute()) {
                return true;
            }else{
                return false;
            }
        });
        factory.setBatchListener(true);
        return factory;
    }

    @Bean
    public ConsumerFactory<String, MessageDTO> consumerFactory() {
        return new DefaultKafkaConsumerFactory<>(consumerConfigs(), new StringDeserializer(), new JsonDeserializer<>(MessageDTO.class));
    }

    @Bean
    public Map<String, Object> consumerConfigs() {
        Map<String, Object> props = new HashMap<>();
        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, BOOTSTRAP_SERVERS_CONFIG);
        props.put(ConsumerConfig.GROUP_ID_CONFIG, GROUP_ID_CONFIG);
        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
        return props;
    }
}
