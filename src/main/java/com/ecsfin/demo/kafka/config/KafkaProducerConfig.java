package com.ecsfin.demo.kafka.config;

import java.util.HashMap;
import java.util.Map;

import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.support.serializer.JsonSerializer;

import com.ecsfin.demo.kafka.model.Student;

@Configuration
public class KafkaProducerConfig {
	
	@Autowired
	KafkaCommonProperties kafkaCommonProperties;

	private String KAFKA_BOOTSTRAP_SERVER;

	public KafkaProducerConfig(@Value("${spring.kafka.bootstrap-servers}") String bootStrapServer) {
		KAFKA_BOOTSTRAP_SERVER = bootStrapServer;
	}
	
	@Bean
	ProducerFactory<String, String> producerFactory(){
		Map<String, Object> props = kafkaCommonProperties.getCommonProperties();
		props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
		props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class);

		return new DefaultKafkaProducerFactory<>(props);
	}
	
	@Bean
	KafkaTemplate<String, String> kafkaTemplate(){
		return new KafkaTemplate<String, String>(producerFactory());
	}

	@Bean
	ProducerFactory<String, Student> producerStudentFactory(){
		Map<String, Object> props = kafkaCommonProperties.getCommonProperties();
		props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
		props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class);
		
		return new DefaultKafkaProducerFactory<>(props);
	}
	
	
	@Bean
	KafkaTemplate<String, Student> kafkaStudentTemplate(){
		return new KafkaTemplate<String, Student>(producerStudentFactory());
	}
}
