package com.ecsfin.demo.kafka.service;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.listener.adapter.ConsumerRecordMetadata;
import org.springframework.stereotype.Service;

import com.ecsfin.demo.kafka.model.Student;
import com.ecsfin.demo.kafka.util.AppConstants;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class KafkaConsumerService {
	
	@KafkaListener(topics = "${common.topic-name}",
					groupId = AppConstants.DEMO_COMMON_GROUP)
	public void consume(String msg, ConsumerRecordMetadata consumerRecordMetadata) {
		log.info("Received msg: {}, from topic '{}', partition '{}', offset '{}' at '{}'", 
				msg, 
				consumerRecordMetadata.topic(),
				consumerRecordMetadata.partition(),
				consumerRecordMetadata.offset(),
				consumerRecordMetadata.timestamp());
	}

	@KafkaListener(topics = "${student.topic-name}",
					groupId = AppConstants.DEMO_STUDENT_GROUP,
					containerFactory = "userKafkaListenerContainerFactory")
	public void consume(Student student, ConsumerRecordMetadata consumerRecordMetadata) {
		log.info("Received student: {}, from topic '{}', partition '{}', offset '{}' at '{}'", 
				student, 
				consumerRecordMetadata.topic(),
				consumerRecordMetadata.partition(),
				consumerRecordMetadata.offset(),
				consumerRecordMetadata.timestamp());
	}

}
