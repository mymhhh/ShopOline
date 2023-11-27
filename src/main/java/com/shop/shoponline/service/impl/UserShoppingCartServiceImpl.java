package com.shop.shoponline.service.impl;

import com.shop.shoponline.common.exception.ServerException;
import com.shop.shoponline.entity.Goods;
import com.shop.shoponline.entity.UserShoppingCart;
import com.shop.shoponline.mapper.GoodsMapper;
import com.shop.shoponline.mapper.UserShoppingCartMapper;
import com.shop.shoponline.query.CartQuery;
import com.shop.shoponline.query.EditCartQuery;
import com.shop.shoponline.service.UserShoppingCartService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.shop.shoponline.vo.CartGoodsVO;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import java.util.stream.Collectors;

import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author maoyumeng
 * @since 2023-11-07
 */
@Service
@AllArgsConstructor
public class UserShoppingCartServiceImpl extends ServiceImpl<UserShoppingCartMapper, UserShoppingCart> implements UserShoppingCartService {
    private final GoodsMapper goodsMapper;
    @Override
    public CartGoodsVO addShopCart(CartQuery query) {
        //查询商品是否存在，以及库存是否充分
        Goods goods=goodsMapper.selectById(query.getId());
        if (goods==null){
            throw new ServerException("商品信息不存在");
        }
        if (query.getCount() >goods.getInventory()){
            throw new ServerException("库存数量不足");
        }
        //插入购物车信息
        UserShoppingCart userShoppingCart=new UserShoppingCart();
        userShoppingCart.setUserId(query.getUserId());
        userShoppingCart.setGoodsId(goods.getId());
        userShoppingCart.setPrice(goods.getPrice());
        userShoppingCart.setCount(query.getCount());
        userShoppingCart.setAttrsText(query.getAttrsText());
        userShoppingCart.setSelected(false);
        baseMapper.insert(userShoppingCart);

        //返回用户添加购物车信息
        CartGoodsVO goodsVO=new CartGoodsVO();
        goodsVO.setId(userShoppingCart.getId());
        goodsVO.setName(goodsVO.getName());
        goodsVO.setAttrsText(query.getAttrsText());
        goodsVO.setPrice(userShoppingCart.getPrice());
        goodsVO.setStock(goods.getInventory());
        goodsVO.setCount(query.getCount());
        goodsVO.setPicture(goods.getCover());
        goodsVO.setDiscount(goods.getDiscount());
        return goodsVO;
    }

    @Override
    public List<CartGoodsVO> shopCartList(Integer userId) {
        List<CartGoodsVO> list=baseMapper.getCartGoodsInfo(userId);
        return list;
    }
    @Override
    public CartGoodsVO editCart(EditCartQuery query) {
        UserShoppingCart userShoppingCart = baseMapper.selectById(query.getId());
        if (userShoppingCart == null) {
            throw new ServerException("购物车信息不存在");
        }
        userShoppingCart.setCount(query.getCount());
        userShoppingCart.setSelected(query.getSelected());
        baseMapper.updateById(userShoppingCart);
        // 查询购物车信息
        Goods goods = goodsMapper.selectById(userShoppingCart.getGoodsId());
        if (query.getCount() > goods.getInventory()) {
            throw new ServerException("库存数量不足");
//            throw new ServerException(goods.getName() + "库存数量不足");
        }
        CartGoodsVO cartGoodsVO = new CartGoodsVO();
        cartGoodsVO.setId(userShoppingCart.getId());
        cartGoodsVO.setName(goods.getName());
        cartGoodsVO.setAttrsText(userShoppingCart.getAttrsText());
        cartGoodsVO.setPrice(userShoppingCart.getPrice());
        cartGoodsVO.setNowPrice(goods.getPrice());
        cartGoodsVO.setSelected(userShoppingCart.getSelected());
        cartGoodsVO.setStock(goods.getInventory());
        cartGoodsVO.setCount(query.getCount());
        cartGoodsVO.setPicture(goods.getCover());
        cartGoodsVO.setDiscount(goods.getDiscount());
        return cartGoodsVO;
    }

    @Override
    public void removeCartGoods(Integer userId, List<Integer> ids) {
        // 查询用户购物车列表
        List<UserShoppingCart> cartList = baseMapper.selectList(new LambdaQueryWrapper<UserShoppingCart>().eq(UserShoppingCart::getUserId, userId));
        if (cartList.size() == 0) {
            return;
        }
        // 与需要删除的购物车取交集
        List<UserShoppingCart> deleteCartList = cartList.stream().filter(item -> ids.contains(item.getId())).collect(Collectors.toList());
        // 删除购物车信息
        removeBatchByIds(deleteCartList);
    }

    @Override
    public void editCartSelected(Boolean selected, Integer userId) {
        // 查询用户购物车列表
        List<UserShoppingCart> cartList = baseMapper.selectList(new LambdaQueryWrapper<UserShoppingCart>().eq(UserShoppingCart::getUserId, userId));
        if (cartList.size() == 0) {
            return;
        }
        // 批量修改购物车选中状态
        cartList.stream().forEach(item -> item.setSelected(selected));
        // 数据库数据更新
        saveOrUpdateBatch(cartList);
    }
}
