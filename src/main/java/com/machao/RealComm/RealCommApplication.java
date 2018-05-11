package com.machao.RealComm;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;

@EnableWebSocket
@EnableWebSocketMessageBroker
@EnableScheduling
@SpringBootApplication
public class RealCommApplication extends SpringBootServletInitializer {

	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
		return application.sources(RealCommApplication.class);
	}

	public static void main(String[] args) {
		SpringApplication.run(RealCommApplication.class, args);
	}
}
