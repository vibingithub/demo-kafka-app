package com.ecsfin.demo.kafka.config;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.kafka.clients.CommonClientConfigs;
import org.springframework.boot.autoconfigure.kafka.KafkaProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.CollectionUtils;

import lombok.Data;

@Configuration
@ConfigurationProperties(prefix = "spring.kafka")
@Data
public class KafkaCommonProperties {
	private List<String> bootstrapServers;
	private Map<String, String> properties = new HashMap<>();
	private KafkaProperties.Ssl ssl = new KafkaProperties.Ssl();
	private KafkaProperties.Security security = new KafkaProperties.Security();

	public Map<String, Object> getCommonProperties() {
		Map<String, Object> properties = new HashMap<>();
		if (this.bootstrapServers != null) {
			properties.put(CommonClientConfigs.BOOTSTRAP_SERVERS_CONFIG, this.bootstrapServers);
		}
		properties.putAll(this.ssl.buildProperties(null));
		properties.putAll(this.security.buildProperties());
		if (!CollectionUtils.isEmpty(this.properties)) {
			properties.putAll(this.properties);
		}
		return properties;
	}

}
