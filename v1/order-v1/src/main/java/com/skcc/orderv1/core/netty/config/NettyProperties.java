/*
 * Copyright 2015 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.skcc.orderv1.core.netty.config;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConstructorBinding;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * Netty ConfigurationProperties
 *
 * @author Jibeom Jung akka. Manty
 */
@Getter
@ConstructorBinding
@ConfigurationProperties(prefix = "netty")
@RequiredArgsConstructor
public class NettyProperties {

    @NotNull
    @Size(min=1000, max=65535)
    private final int tcpPort;

    @NotNull
    @Min(1)
    private final int bossCount;

    @NotNull
    @Min(2)
    private final int workerCount;

    @NotNull
    private final boolean keepAlive;

    @NotNull
    private final int backlog;


    @Override
    public String toString() {
        return "NettyProperties{" +
                "tcpPort=" + tcpPort +
                ", bossCount=" + bossCount +
                ", workerCount=" + workerCount +
                ", keepAlive=" + keepAlive +
                ", backlog=" + backlog +
                '}';
    }
}
