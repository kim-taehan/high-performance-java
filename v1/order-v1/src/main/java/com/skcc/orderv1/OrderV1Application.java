package com.skcc.orderv1;

import com.skcc.orderv1.core.netty.NettyServer;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class OrderV1Application {

	public static void main(String[] args) {
		SpringApplication.run(OrderV1Application.class, args);
	}

	@Bean
	@ConditionalOnProperty(value = "spring.main.web-application-type", havingValue = "none")
	public ApplicationListener<ApplicationReadyEvent> readyEventApplicationListener(NettyServer nettyServer
	) {
		return applicationReadyEvent -> {
            nettyServer.start();
        };
	}
}
