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
package com.skcc.orderv1.core.netty.handler;

import com.skcc.orderv1.core.netty.domain.ChannelRepository;
import com.skcc.orderv1.core.netty.domain.User;
import com.skcc.orderv1.domain.data.OrderCreateRequest;
import com.skcc.orderv1.domain.data.OrderCreateResponse;
import com.skcc.orderv1.domain.service.OrderService;
import com.skcc.orderv1.global.ObjectMapperConverter;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.concurrent.*;

/**
 * event handler to process receiving messages
 *
 * @author Jibeom Jung akka. Manty
 */
@Component
@Slf4j
@RequiredArgsConstructor
@ChannelHandler.Sharable
public class SimpleChatServerHandler extends ChannelInboundHandlerAdapter {

    private final ChannelRepository channelRepository;
    private final ObjectMapperConverter objectMapper;

    private final OrderService orderService;


    @Override
    public void channelActive(ChannelHandlerContext ctx) {
        Assert.notNull(this.channelRepository, "[Assertion failed] - ChannelRepository is required; it must not be null");

        ctx.fireChannelActive();
        log.info(ctx.channel().remoteAddress() + "");
        String remoteAddress = ctx.channel().remoteAddress().toString();

        ctx.writeAndFlush("Your remote address is " + remoteAddress + ".\r\n");

        log.info("Bound Channel Count is {}", this.channelRepository.size());

    }

    private final String CALL_LOG_TEXT = "CALL 상품주문 (호출방식:TCP)";
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) {
        String stringMessage = (String) msg;

        OrderCreateRequest request = objectMapper.stringToObject(stringMessage, OrderCreateRequest.class);
        log.info("{}={}", CALL_LOG_TEXT, request);
        if (request != null) {
            orderService.createOrderSocket(request);
            ctx.channel().writeAndFlush("ok" + "\n\r");
        }
        else{
            ctx.channel().writeAndFlush("error" + "\n\r");
        }

//        User.current(ctx.channel())
//                .tell(channelRepository.get(splitMessage[0]), splitMessage[0], splitMessage[1]);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        log.error(cause.getMessage(), cause);
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) {
        Assert.notNull(this.channelRepository, "[Assertion failed] - ChannelRepository is required; it must not be null");
        Assert.notNull(ctx, "[Assertion failed] - ChannelHandlerContext is required; it must not be null");

        User.current(ctx.channel()).logout(this.channelRepository, ctx.channel());
        log.info("Channel Count is " + this.channelRepository.size());
    }
}
