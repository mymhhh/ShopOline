package com.shop.shoponline.mapper;

import com.shop.shoponline.entity.Goods;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.shop.shoponline.vo.UserOrderGoodsVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author maoyumeng
 * @since 2023-11-07
 */
public interface GoodsMapper extends BaseMapper<Goods> {
    List<UserOrderGoodsVO> getGoodsListByOrderId(@Param("id") Integer id);
}
