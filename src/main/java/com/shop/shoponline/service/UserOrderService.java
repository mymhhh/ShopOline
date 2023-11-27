package com.shop.shoponline.service;

import com.shop.shoponline.common.result.PageResult;
import com.shop.shoponline.entity.UserOrder;
import com.baomidou.mybatisplus.extension.service.IService;
import com.shop.shoponline.query.CancelGoodsQuery;
import com.shop.shoponline.query.OrderQuery;
import com.shop.shoponline.vo.OrderDetailVO;
import com.shop.shoponline.vo.OrderLogisticVO;
import com.shop.shoponline.vo.SubmitOrderVO;
import com.shop.shoponline.vo.UserOrderVO;
import com.shop.shoponline.query.OrderPreQuery;

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
    // 新增订单
    Integer addGoodsOrder(UserOrderVO userOrderVO);
     // 获取订单详情
    OrderDetailVO getOrderDetail(Integer id);
     // 填写订单-获取预付订单
    SubmitOrderVO getPreOrderDetail(Integer userId);
    // 填写订单-立即支付
    SubmitOrderVO getPreNowOrderDetail(OrderPreQuery orderPreQuery);
    // 填写订单-再次购买
    SubmitOrderVO getRepurchaseOrderDetail(Integer id);
    // 订单列表
    PageResult<OrderDetailVO> getOrderList(OrderQuery query);
    // 取消订单
    OrderDetailVO cancelOrder(CancelGoodsQuery query);
    // 删除订单
    void deleteOrder(List<Integer> ids, Integer userId);
    // 模拟发货
    void consignOrder(Integer id);
    // 订单支付
    void payOrder(Integer id);
    // 确认收货
    OrderDetailVO receiptOrder(Integer id);
    // 物流订单信息
    OrderLogisticVO getOrderLogistics(Integer id);
}
