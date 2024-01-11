package com.ecsfin.demo.kafka.service;

import com.ecsfin.demo.kafka.model.Student;

public interface KafkaConsumerService {

	public void consume(String msg);
	
	public void consume(Student student);
}
