package com.skcc.stockv1;

import com.skcc.stockv1.domain.service.StockService;
import com.skcc.stockv1.global.kafka.MessageProducer;
import com.skcc.stockv1.global.thread.ExecutorServiceFactory;
import com.skcc.stockv1.global.thread.ThreadProperty;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.context.annotation.Bean;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

@SpringBootApplication
@ConfigurationPropertiesScan
public class StockV1Application {
	public static void main(String[] args) {
		SpringApplication.run(StockV1Application.class, args);
	}

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
	public ExecutorServiceFactory executorServiceFactory(ExecutorService executorService, StockService stockService, MessageProducer messageProducer) {
		return new ExecutorServiceFactory(executorService, stockService, messageProducer);
	}
}
