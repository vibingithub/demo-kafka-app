package com.ecsfin.demo.kafka.service;

import com.ecsfin.demo.kafka.model.Student;

public interface KafkaProducerService {
	
	public void sendMessage(String msg);
	
	public void sendStudent(Student student);

}
