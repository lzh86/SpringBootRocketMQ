package com.kaka.SpringBootRocketMQ.controller;

import com.kaka.SpringBootRocketMQ.common.Constant;
import com.kaka.SpringBootRocketMQ.service.OrderService;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.client.exception.MQBrokerException;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.remoting.exception.RemotingException;
import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.nio.charset.StandardCharsets;

@Slf4j
@RequestMapping(value = "/index")
@Controller
public class HelloController {

    @Resource
    private RocketMQTemplate rocketMQTemplate;
    @Resource
    private OrderService orderService;

    @RequestMapping(value = "/syncSend", method = RequestMethod.GET)
    @ResponseBody
    public String syncSend() {
        Integer integer = 2;
        try {
            //发送一个同步消息，会返回值 ---发送到 calSquareTopic 主题
            SendResult sendResult = rocketMQTemplate.syncSend(Constant.DEFAULT_TOP, integer);
            log.info("同步消息发送返回结果:{}", sendResult);
        } catch (Exception e) {
            log.error("{}", e.getStackTrace());
        }
        return "SUCCESS";
    }

    @RequestMapping(value = "/send", method = RequestMethod.GET)
    @ResponseBody
    public String send(String str) {
        try {
            //Message message = new Message(Constant.DEFAULT_TOP, str, str.getBytes(StandardCharsets.UTF_8));
            Message message = new Message(Constant.DEFAULT_TOP_1, str, str.getBytes(StandardCharsets.UTF_8));
            SendResult send = rocketMQTemplate.getProducer().send(message);
            log.info("消息发送返回结果{}", send);
        } catch (MQClientException e) {
            log.error("{}", e.getStackTrace());
        } catch (RemotingException e) {
            log.error("{}", e.getStackTrace());
        } catch (MQBrokerException e) {
            log.error("{}", e.getStackTrace());
        } catch (InterruptedException e) {
            log.error("{}", e.getStackTrace());
        }catch (Exception e){
            log.error("{}",e.getStackTrace());
        }
        return "SUCCESS";
    }

    @RequestMapping(value = "/sendAndReceive", method = RequestMethod.GET)
    @ResponseBody
    public String sendAndReceive() {
        Object result = rocketMQTemplate.sendAndReceive("noExitTopic", "request String", String.class);
        log.info("同步消息发送返回结果:{}", result);
        return "SUCCESS";
    }

    @RequestMapping(value = "/createOrder", method = RequestMethod.GET)
    @ResponseBody
    public String createOrder(String orderNo) {
        try {
            orderService.createOrder(orderNo);
        } catch (MQClientException e) {
            log.error("创建订单MQ报错:{},{}", e.getMessage(), e);
        } catch (Exception e) {
            log.error("创建订单报错:{},{}", e.getMessage(), e);
        }
        return "SUCCESS";
    }

}
