package com.ecsfin.demo.kafka.config;

import java.util.Map;

import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.support.serializer.JsonSerializer;

import com.ecsfin.demo.kafka.model.Student;

@Configuration
public class KafkaProducerConfig {
	
	private KafkaCommonProperties kafkaCommonProperties;
	
	public KafkaProducerConfig(KafkaCommonProperties kafkaCommonProperties) {
		this.kafkaCommonProperties = kafkaCommonProperties;
	}

	@Bean
	ProducerFactory<String, String> producerFactory(){
		Map<String, Object> props = kafkaCommonProperties.getCommonProperties();
		props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
		props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
		
		//High Throughput Producer
		props.put(ProducerConfig.COMPRESSION_TYPE_CONFIG, "snappy");
		props.put(ProducerConfig.LINGER_MS_CONFIG, "20");
		props.put(ProducerConfig.BATCH_SIZE_CONFIG, Integer.toString(32*1024));

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
		
		//High Throughput Producer
		props.put(ProducerConfig.COMPRESSION_TYPE_CONFIG, "snappy");
		props.put(ProducerConfig.LINGER_MS_CONFIG, "20");
		props.put(ProducerConfig.BATCH_SIZE_CONFIG, Integer.toString(32*1024));
		
		return new DefaultKafkaProducerFactory<>(props);
	}
	
	
	@Bean
	KafkaTemplate<String, Student> kafkaStudentTemplate(){
		return new KafkaTemplate<String, Student>(producerStudentFactory());
	}
}
