package com.kaka.SpringBootRocketMQ.service;


import com.kaka.SpringBootRocketMQ.vo.Order;
import org.apache.rocketmq.client.exception.MQClientException;

public interface OrderService {

    /**
     * 创建订单
     * @param orderNo           订单号
     * @return
     * @throws MQClientException
     */
    boolean createOrder(String orderNo) throws MQClientException;

    /**
     *
     * @param orderNo
     * @return
     */
    Order queryOrderInfoByOrderNo(String orderNo);
}
