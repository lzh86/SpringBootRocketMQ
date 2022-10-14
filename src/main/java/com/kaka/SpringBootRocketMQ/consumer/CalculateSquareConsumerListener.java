package com.kaka.SpringBootRocketMQ.consumer;

import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.spring.annotation.ConsumeMode;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.springframework.stereotype.Component;

@Component
@Slf4j
//消息有序消费还是并发消费则由consumeMode属性定制
//消息过滤可以由里面的selectorType属性和selectorExpression来定制
@RocketMQMessageListener(topic = "calSquareTopic", consumerGroup = "springBootGroup",
        selectorExpression = "*", consumeMode = ConsumeMode.ORDERLY)
public class CalculateSquareConsumerListener implements RocketMQListener<String> {
    @Override
    public void onMessage(String integer) {
        log.info("mq接收消息：{}", integer);
        //int i = integer * integer;
        //System.out.println(i);
    }
}
