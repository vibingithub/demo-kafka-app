package com.ecsfin.demo.kafka.config;

import java.util.HashMap;
import java.util.Map;

import org.apache.kafka.clients.CommonClientConfigs;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.config.SaslConfigs;
import org.apache.kafka.common.security.auth.SecurityProtocol;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.kafka.KafkaProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.support.serializer.JsonDeserializer;
import org.springframework.retry.backoff.FixedBackOffPolicy;
import org.springframework.retry.policy.SimpleRetryPolicy;
import org.springframework.retry.support.RetryTemplate;

import com.ecsfin.demo.kafka.model.Student;
import com.ecsfin.demo.kafka.util.AppConstants;

@Configuration
public class KafkaConsumerConfig {

	@Autowired
	KafkaCommonProperties kafkaCommonProperties;
	
	private String KAFKA_BOOTSTRAP_SERVER;

	public KafkaConsumerConfig(@Value("${spring.kafka.bootstrap-servers}") String bootStrapServer) {
		KAFKA_BOOTSTRAP_SERVER = bootStrapServer;
	}

	@Bean
	ConsumerFactory<String, String> consumerFactory() {
		Map<String, Object> props = kafkaCommonProperties.getCommonProperties();

		props.put(ConsumerConfig.GROUP_ID_CONFIG, AppConstants.DEMO_COMMON_GROUP);
		props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
		props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
		
		
		return new DefaultKafkaConsumerFactory<>(props);
	}

	@Bean
	ConcurrentKafkaListenerContainerFactory<String, String> kafkaListenerContainerFactory() {
		ConcurrentKafkaListenerContainerFactory<String, String> factory = new ConcurrentKafkaListenerContainerFactory<>();
		factory.setConsumerFactory(consumerFactory());
		return factory;
	}

	@Bean
	ConsumerFactory<String, Student> userConsumerFactory() {
		Map<String, Object> props = kafkaCommonProperties.getCommonProperties();
		
		props.put(ConsumerConfig.GROUP_ID_CONFIG, AppConstants.DEMO_STUDENT_GROUP);
		return new DefaultKafkaConsumerFactory<>(props, new StringDeserializer(), getStudentDeserializer());
	}
	
	@Bean
	ConcurrentKafkaListenerContainerFactory<String, Student> userKafkaListenerContainerFactory() {
		ConcurrentKafkaListenerContainerFactory<String, Student> factory = new ConcurrentKafkaListenerContainerFactory<>();
		factory.setConsumerFactory(userConsumerFactory());
		return factory;
	}

	private JsonDeserializer<Student> getStudentDeserializer() {
		JsonDeserializer<Student> deserializer = new JsonDeserializer<Student>(Student.class);
		deserializer.setRemoveTypeHeaders(false);
		deserializer.addTrustedPackages("com.ecsfin.demo.kafka.model.*");
		deserializer.setUseTypeMapperForKey(true);
		return deserializer;
	}
}
