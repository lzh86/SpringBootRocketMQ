package com.kaka.SpringBootRocketMQ.consumer;

import com.kaka.SpringBootRocketMQ.common.Constant;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.spring.annotation.ConsumeMode;
import org.apache.rocketmq.spring.annotation.MessageModel;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQReplyListener;
import org.springframework.stereotype.Component;


@Component
@Slf4j
//消息有序消费还是并发消费则由consumeMode属性定制
//消息过滤可以由里面的selectorType属性和selectorExpression来定制
@RocketMQMessageListener(topic = Constant.DEFAULT_TOP_1, consumerGroup = "springBootGroup",enableMsgTrace = true,messageModel = MessageModel.CLUSTERING,
        selectorExpression = "*", consumeMode = ConsumeMode.ORDERLY)
public class MessageReplyListener implements RocketMQReplyListener<String,String> {


    /**
     * @param message data received by the listener
     * @return data replying to producer
     */
    @Override
    public String onMessage(String message) {
        log.info("应答模式接收消息:{}",message);
        return "接收成功";
    }
}
