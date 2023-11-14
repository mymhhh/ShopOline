package com.shop.shoponline.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.shop.shoponline.common.exception.ServerException;
import com.shop.shoponline.convert.AddressConvert;
import com.shop.shoponline.entity.UserShippingAddress;
import com.shop.shoponline.enums.AddressDefaultEnum;
import com.shop.shoponline.enums.AddressDelFlagEnum;
import com.shop.shoponline.mapper.UserShippingAddressMapper;
import com.shop.shoponline.service.UserShippingAddressService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.shop.shoponline.vo.AddressVO;
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
public class UserShippingAddressServiceImpl extends ServiceImpl<UserShippingAddressMapper, UserShippingAddress> implements UserShippingAddressService {
    //添加收货地址
    @Override
    public Integer saveShippingAddress(AddressVO addressVO) {
        UserShippingAddress convert = AddressConvert.INSTANCE.convert(addressVO);
        if(addressVO.getIsDefault()== AddressDefaultEnum.DEFAULT_ADDRESS.getValue()){
            List<UserShippingAddress> list =baseMapper.selectList(new
                    LambdaQueryWrapper<UserShippingAddress>().eq(UserShippingAddress::getIsDefault,
                    AddressDefaultEnum.DEFAULT_ADDRESS.getValue()));
            if(list.size()>0){
                throw new ServerException("已经存在默认地址，请勿重复操作");
            }
        }
        save(convert);
        return convert.getId();
    }

    //修改收货地址
    @Override
    public Integer editShippingAddress(AddressVO addressVO) {
        UserShippingAddress userShippingAddress = baseMapper.selectById(addressVO.getId());
        if(userShippingAddress==null){
            throw  new ServerException("地址不存在");
        }
        if(addressVO.getIsDefault()==AddressDefaultEnum.DEFAULT_ADDRESS.getValue()){
//            查询该用户是否已经存在默认地址
            UserShippingAddress address=baseMapper.selectOne(new
                    LambdaQueryWrapper<UserShippingAddress>().eq(UserShippingAddress::getUserId,
                    addressVO.getUserId()).eq(UserShippingAddress::getIsDefault,
                    AddressDefaultEnum.DEFAULT_ADDRESS.getValue()));
            if(address!=null){
                address.setIsDefault(AddressDefaultEnum.NOT_DEFAULT_ADDRESS.getValue());
                updateById(address);
            }
        }
        UserShippingAddress address = AddressConvert.INSTANCE.convert(addressVO);
        updateById(address);
        return address.getId();
    }
    //收货地址列表
    @Override
    public List<AddressVO> getAddressList(Integer userId) {
        List<UserShippingAddress> list = baseMapper.selectList(new
                LambdaQueryWrapper<UserShippingAddress>().eq(UserShippingAddress::getUserId, userId));
        List<AddressVO> addressList = AddressConvert.INSTANCE.convertToAddressVOList(list);
        return addressList;
    }
//获取收货地址详情
    @Override
    public AddressVO getAddressInfo(Integer id) {
        UserShippingAddress address = baseMapper.selectOne(new
                LambdaQueryWrapper<UserShippingAddress>().eq(UserShippingAddress::getId, id));
        return AddressConvert.INSTANCE.convertToAddressVO(address);
    }
//删除收货地址
    @Override
    public void removeShippingAddress(Integer id) {
        removeById(id);
    }
}
