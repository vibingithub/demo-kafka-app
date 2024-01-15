package com.ecsfin.demo.kafka.config;

import java.util.Map;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.CooperativeStickyAssignor;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.support.serializer.JsonDeserializer;

import com.ecsfin.demo.kafka.model.Student;
import com.ecsfin.demo.kafka.util.AppConstants;

@Configuration
public class KafkaConsumerConfig {

	private KafkaCommonProperties kafkaCommonProperties;
	
	public KafkaConsumerConfig(KafkaCommonProperties kafkaCommonProperties) {
		this.kafkaCommonProperties = kafkaCommonProperties;
	}

	@Bean
	ConsumerFactory<String, String> consumerFactory() {
		Map<String, Object> props = kafkaCommonProperties.getCommonProperties();

		props.put(ConsumerConfig.GROUP_ID_CONFIG, AppConstants.DEMO_COMMON_GROUP);
		props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
		props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
		props.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, AppConstants.DEFAULT_AUTO_OFFSET_APPROACH);
		
		//Consumer Re-balance Approach
		props.put(ConsumerConfig.PARTITION_ASSIGNMENT_STRATEGY_CONFIG, CooperativeStickyAssignor.class.getName());
		
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
		props.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, AppConstants.DEFAULT_AUTO_OFFSET_APPROACH);
		
		//Consumer Re-balance Approach
		props.put(ConsumerConfig.PARTITION_ASSIGNMENT_STRATEGY_CONFIG, CooperativeStickyAssignor.class.getName());
		
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
