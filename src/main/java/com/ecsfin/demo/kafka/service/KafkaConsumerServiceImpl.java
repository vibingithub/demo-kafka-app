package com.ecsfin.demo.kafka.service;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import com.ecsfin.demo.kafka.model.Student;
import com.ecsfin.demo.kafka.util.AppConstants;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class KafkaConsumerServiceImpl implements KafkaConsumerService {
	
	@Override
	@KafkaListener(topics = "${common.topic-name}",
					groupId = AppConstants.DEMO_COMMON_GROUP)
	public void consume(String msg) {
		log.info("Received msg: {}", msg);
	}

	@Override
	@KafkaListener(topics = "${student.topic-name}",
					groupId = AppConstants.DEMO_STUDENT_GROUP,
					containerFactory = "userKafkaListenerContainerFactory")
	public void consume(Student student) {
		log.info("Received student: {}", student);
	}

}
