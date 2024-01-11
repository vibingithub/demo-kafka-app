package com.ecsfin.demo.kafka.config;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

@Configuration
public class KafkaTopicConfig {
	
	/**
	 * Only needed if topic need to be created programmatically
	 */
	
	private String COMMON_TOPIC_NAME;
	private String STUDENT_TOPIC_NAME;

	public KafkaTopicConfig(@Value("${common.topic-name}") String commonTopicName, 
							@Value("${student.topic-name}") String studentTopicName) {
		STUDENT_TOPIC_NAME = studentTopicName;
		COMMON_TOPIC_NAME = commonTopicName;
	}
	
	
	@Bean
	NewTopic commonTopic() {
		return TopicBuilder.name(COMMON_TOPIC_NAME)
					.build();
	}
	
	@Bean
	NewTopic studentTopic() {
		return TopicBuilder.name(STUDENT_TOPIC_NAME)
				.build();
	}
	
	
}
