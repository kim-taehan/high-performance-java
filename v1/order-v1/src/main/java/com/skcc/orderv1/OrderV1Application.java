package com.skcc.orderv1;

import com.skcc.orderv1.core.netty.NettyServer;
import com.skcc.orderv1.global.config.ThreadProperty;
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

@SpringBootApplication
@ConfigurationPropertiesScan
public class OrderV1Application {

	public static void main(String[] args) {
		SpringApplication.run(OrderV1Application.class, args);
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
}
