package com.ecsfin.demo.kafka.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ecsfin.demo.kafka.model.Student;
import com.ecsfin.demo.kafka.service.KafkaProducerService;

@RestController
@RequestMapping("/api")
public class DemoController {
	
	private KafkaProducerService kafkaProducerService;
	
	DemoController(KafkaProducerService kafkaProducerService){
		this.kafkaProducerService = kafkaProducerService;
	}
	
	@PostMapping("/msg")
	public ResponseEntity<String> sendMessage(@RequestBody String msg){
		kafkaProducerService.sendMessage(msg);
		
		return new ResponseEntity<String>("Success", HttpStatus.ACCEPTED);
	}
	
	@PostMapping("/student")
	public ResponseEntity<String> sendStudent(@RequestBody Student student){
		kafkaProducerService.sendStudent(student);
		
		return new ResponseEntity<String>("Success", HttpStatus.ACCEPTED);
	}
}
