package com.shop.shoponline.service;

import com.shop.shoponline.entity.UserOrder;
import com.baomidou.mybatisplus.extension.service.IService;
import com.shop.shoponline.vo.OrderDetailVO;
import com.shop.shoponline.vo.UserOrderVO;

import com.shop.shoponline.entity.UserOrderGoods;
import com.shop.shoponline.vo.UserOrderVO;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author maoyumeng
 * @since 2023-11-07
 */
public interface UserOrderService extends IService<UserOrder> {
    //新增订单
    Integer addGoodsOrder(UserOrderVO userOrderVO);
     // 获取订单详情
    OrderDetailVO getOrderDetail(Integer id);
}
