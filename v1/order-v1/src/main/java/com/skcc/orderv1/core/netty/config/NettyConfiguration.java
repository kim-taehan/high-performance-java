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

import com.skcc.orderv1.core.netty.NettyServer;
import com.skcc.orderv1.core.netty.handler.SimpleChatChannelInitializer;
import com.skcc.orderv1.core.netty.handler.SimpleChatServerHandler;
import com.skcc.orderv1.global.ObjectMapperConverter;
import com.skcc.orderv1.global.thread.ExecutorServiceFactory;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelOption;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.net.InetSocketAddress;

/**
 * NettyConfiguration
 *
 * @author Jibeom Jung akka. Manty
 */
@Configuration
@RequiredArgsConstructor
@EnableConfigurationProperties(NettyProperties.class)
@ConditionalOnProperty(value = "spring.main.web-application-type", havingValue = "none")
public class NettyConfiguration {

    private final NettyProperties nettyProperties;
    @Bean
    public SimpleChatChannelInitializer simpleChatChannelInitializer(SimpleChatServerHandler simpleChatServerHandler) {
        return new SimpleChatChannelInitializer(simpleChatServerHandler);
    }
    @Bean
    public SimpleChatServerHandler simpleChatServerHandler(ObjectMapperConverter converter, ExecutorServiceFactory factory) {
        return new SimpleChatServerHandler(converter, factory);
    }

    @Bean(name = "serverBootstrap")
    public ServerBootstrap bootstrap(SimpleChatChannelInitializer simpleChatChannelInitializer) {
        ServerBootstrap b = new ServerBootstrap();
        b.group(bossGroup(), workerGroup())
                .channel(NioServerSocketChannel.class)
                .handler(new LoggingHandler(LogLevel.INFO))
                .childHandler(simpleChatChannelInitializer);
        b.option(ChannelOption.SO_BACKLOG, nettyProperties.getBacklog());
        return b;
    }

    @Bean(destroyMethod = "shutdownGracefully")
    public NioEventLoopGroup bossGroup() {
        return new NioEventLoopGroup(nettyProperties.getBossCount());
    }

    @Bean(destroyMethod = "shutdownGracefully")
    public NioEventLoopGroup workerGroup() {
        return new NioEventLoopGroup(nettyProperties.getWorkerCount());
    }

    @Bean
    public InetSocketAddress tcpSocketAddress() {
        return new InetSocketAddress(nettyProperties.getTcpPort());
    }

    @Bean
    public NettyServer nettyServer(ServerBootstrap bootstrap, InetSocketAddress tcpSocketAddress) {
        return new NettyServer(bootstrap, tcpSocketAddress);
    }

}
