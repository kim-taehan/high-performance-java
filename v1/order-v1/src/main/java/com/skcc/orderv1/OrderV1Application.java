package com.skcc.orderv1;

import com.skcc.orderv1.core.netty.NettyServer;
import com.skcc.orderv1.domain.service.OrderService;
import com.skcc.orderv1.global.thread.ExecutorServiceFactory;
import com.skcc.orderv1.global.thread.ThreadProperty;
import com.skcc.orderv1.global.kafka.MessageProducer;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Bean;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import static org.springframework.boot.Banner.Mode.OFF;

@SpringBootApplication
@ConfigurationPropertiesScan
public class OrderV1Application {


	public static void main(String[] args) {

		SpringApplication application = new SpringApplication(OrderV1Application.class);
		// 스프링부트 웰컴 메시지 관리
		application.setBannerMode(OFF);

		application.run(args);
	}

	/**
	 * netty server start (only web-application-type is none)
	 * @param nettyServer
	 * @return
	 */
	@Bean
	@ConditionalOnProperty(value = "spring.main.web-application-type", havingValue = "none")
	public ApplicationListener<ApplicationReadyEvent> readyEventApplicationListener(NettyServer nettyServer
	) {
		return applicationReadyEvent -> {
            nettyServer.start();
        };
	}


	/**
	 * multi thread setting (only web-application-type is none)
	 * @return
	 */
	@Bean
	@ConditionalOnProperty(value = "spring.main.web-application-type", havingValue = "none")
	public ExecutorService executorService(ThreadProperty thread) {
		return new ThreadPoolExecutor(
				thread.getCorePoolSize(), // 코어 쓰레드 수
				thread.getMaximumPoolSize(), // 최대 쓰레드 수
				thread.getKeepAliveTime(), // 최대 유휴 시간
				TimeUnit.SECONDS, // 최대 유휴 시간 단위
				new SynchronousQueue<>() // 작업 큐
		);
	}

	@Bean
	@ConditionalOnProperty(value = "spring.main.web-application-type", havingValue = "none")
	public ExecutorServiceFactory executorServiceFactory(ExecutorService executorService, OrderService orderService, MessageProducer messageProducer) {
		return new ExecutorServiceFactory(executorService, orderService, messageProducer);
	}

}
