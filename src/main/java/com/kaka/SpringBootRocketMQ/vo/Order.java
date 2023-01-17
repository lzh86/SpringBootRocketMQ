package com.kaka.SpringBootRocketMQ.vo;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * order
 *
 * @author
 */
@Data
public class Order implements Serializable {
    /**
     * 主键
     */
    private Integer id;

    /**
     * 订单号
     */
    private Integer orderNo;

    /**
     * 创建时间
     */
    private Date createDate;

    /**
     * 更新时间
     */
    private Date updateDate;

    private static final long serialVersionUID = 1L;
}