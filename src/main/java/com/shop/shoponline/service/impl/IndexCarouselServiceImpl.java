package com.shop.shoponline.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.shop.shoponline.entity.IndexCarousel;
import com.shop.shoponline.mapper.IndexCarouselMapper;
import com.shop.shoponline.service.IndexCarouselService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

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
public class IndexCarouselServiceImpl extends ServiceImpl<IndexCarouselMapper, IndexCarousel> implements IndexCarouselService {

    @Override
    public List<IndexCarousel> getList(Integer distributionSite) {
        LambdaQueryWrapper<IndexCarousel> wrapper=new LambdaQueryWrapper<>();
        if (distributionSite!=null){
            wrapper.eq(IndexCarousel::getType,distributionSite);
        }
        wrapper.orderByDesc(IndexCarousel::getCreateTime);
        //查询广告列表
        List<IndexCarousel> list= baseMapper.selectList(wrapper);
        return list;
    }
}
