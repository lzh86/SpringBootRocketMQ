package com.kaka.SpringBootRocketMQ.service;

import com.kaka.SpringBootRocketMQ.common.Constant;
import com.kaka.SpringBootRocketMQ.mapper.OrderMapper;
import com.kaka.SpringBootRocketMQ.vo.Order;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.*;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.common.message.MessageExt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import java.nio.charset.StandardCharsets;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.*;

@Service
@Slf4j
public class OrderServiceImpl implements OrderService {

    @Autowired
    private OrderMapper orderMapper;

    private TransactionMQProducer producer;

    @PostConstruct
    public void init() throws MQClientException {
        TransactionListener transactionListener = createTransactionListener();
        producer = new TransactionMQProducer(Constant.DEFAULT_GROUP);
        producer.setExecutorService(Executors.newCachedThreadPool());
        producer.setTransactionListener(transactionListener);
        producer.start();
    }

    /**
     * 创建订单
     *
     * @param orderNo 订单号
     * @return
     * @throws MQClientException
     */
    public boolean createOrder(String orderNo) throws MQClientException {
        // 根据创建订单请求创建⼀条消息
        Message msg = new Message(Constant.DEFAULT_TOP, orderNo.getBytes(StandardCharsets.UTF_8));
        // 发送事务消息
        SendResult sendResult = producer.sendMessageInTransaction(msg, orderNo);
        // 返回：事务是否成功
        return sendResult.getSendStatus().equals(SendStatus.SEND_OK);
    }

    /**
     * @param orderNo
     * @return
     */
    @Override
    public Order queryOrderInfoByOrderNo(String orderNo) {
        return orderMapper.queryOrderInfoByOrderNo(orderNo);
    }


    private TransactionListener createTransactionListener() {
        return new TransactionListener() {
            @Override
            public LocalTransactionState executeLocalTransaction(Message msg, Object arg) {
                String request = (String) arg;
                try {
                    log.info("MQ执行本地事务创建订单请求:{}", request);
                    // 执⾏本地事务创建订单
                    orderMapper.createOrderInDB(request);
                    // 如果没抛异常说明执⾏成功，提交事务消息
                    return LocalTransactionState.COMMIT_MESSAGE;
                } catch (Throwable t) {
                    // 失败则直接回滚事务消息
                    return LocalTransactionState.ROLLBACK_MESSAGE;
                }
            }

            // 反查本地事务
            @Override
            public LocalTransactionState checkLocalTransaction(MessageExt msg) {
                // 从消息中获得订单ID
                String orderNo = msg.getUserProperty("orderNo");
                log.info("MQ反查本地事务订单号:{}", orderNo);
                // 去数据库中查询订单号是否存在，如果存在则提交事务；
                // 如果不存在，可能是本地事务失败了，也可能是本地事务还在执⾏，所以返回UNKNOW
                return orderMapper.isOrderIdExistsInDB(orderNo) ?
                        LocalTransactionState.COMMIT_MESSAGE : LocalTransactionState.UNKNOW;
            }
        };
    }

}
