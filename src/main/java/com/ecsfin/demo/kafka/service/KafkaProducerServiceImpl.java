package com.ecsfin.demo.kafka.service;

import java.util.concurrent.CompletableFuture;

import org.apache.kafka.clients.producer.RecordMetadata;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;

import com.ecsfin.demo.kafka.model.Student;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class KafkaProducerServiceImpl implements KafkaProducerService {

	private String COMMON_TOPIC_NAME;
	private String STUDENT_TOPIC_NAME;
	private KafkaTemplate<String, String> strTemplate;
	private KafkaTemplate<String, Student> studentTemplate;

	public KafkaProducerServiceImpl(@Value("${common.topic-name}") String commonTopicName,
									@Value("${student.topic-name}") String studentTopicName,
									KafkaTemplate<String, String> strTemplate,
									KafkaTemplate<String, Student> studentTemplate) {
		STUDENT_TOPIC_NAME = studentTopicName;
		COMMON_TOPIC_NAME = commonTopicName;
		this.strTemplate = strTemplate;
		this.studentTemplate = studentTemplate;
	}

	@Override
	public void sendMessage(String msg) {
		CompletableFuture<SendResult<String, String>> sendResp = strTemplate.send(COMMON_TOPIC_NAME, msg);
		
		sendResp.whenComplete((result,ex)->{
			if(ex==null) {
				//Handle success
				
				RecordMetadata recordMetadata = result.getRecordMetadata();
				log.info("Metadata: topic '{}', patition '{}', offset '{}', timestamp '{}'",
						recordMetadata.topic(),
						recordMetadata.partition(), 
						recordMetadata.offset(), 
						recordMetadata.timestamp());
			}else {
				//Handle failure
				log.error("Exception while sending {}",ex);
			}
		});
		
		/**
		 * thread wont be waiting to complete the submission of message in topic,
		 * 	once that completed whencomplete() block will be executed 
		 */
			
		log.info("Sending Completed");
	}

	@Override
	public void sendStudent(Student student) {
		studentTemplate.send(STUDENT_TOPIC_NAME, student);
	}

}
