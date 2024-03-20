package com.skcc.orderv1.global.thread;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConstructorBinding;

@Getter
@ConstructorBinding
@ConfigurationProperties(prefix = "thread.configuration")
@RequiredArgsConstructor
public class ThreadProperty {

    private final int corePoolSize;
    private final int maximumPoolSize;
    private final long keepAliveTime;
}
