package com.kaka.SpringBootRocketMQ.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.kaka.SpringBootRocketMQ.vo.Order;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;

import javax.annotation.Resource;

@Resource
public interface OrderMapper extends BaseMapper<Order> {

   @Select({" SELECT COUNT(1) FROM `order` WHERE order_no = #{orderNo} "})
   Boolean isOrderIdExistsInDB(String orderNo);

   @Insert({"INSERT INTO `order`(`order_no`, `create_date`, `update_date`) VALUES (#{orderNo}, now(), now()"})
   void createOrderInDB(String orderNo);

   @Select({" SELECT `order_no`, `create_date`, `update_date` FROM `order` WHERE order_no = #{orderNo} "})
   Order queryOrderInfoByOrderNo(String orderNo);
}
