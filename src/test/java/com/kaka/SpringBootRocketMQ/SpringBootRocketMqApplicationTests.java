package com.kaka.SpringBootRocketMQ;

import com.kaka.SpringBootRocketMQ.service.OrderService;
import com.kaka.SpringBootRocketMQ.vo.Order;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class SpringBootRocketMqApplicationTests {

	@Autowired
	private OrderService orderService;

	@Test
	void contextLoads() {
		Order order = orderService.queryOrderInfoByOrderNo("1");
		System.out.println(order);
	}

}
