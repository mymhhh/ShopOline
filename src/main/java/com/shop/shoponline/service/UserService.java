package com.shop.shoponline.service;

import com.shop.shoponline.entity.User;
import com.baomidou.mybatisplus.extension.service.IService;
import com.shop.shoponline.query.UserLoginQuery;
import com.shop.shoponline.vo.LoginResultVO;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author maoyumeng
 * @since 2023-11-07
 */
public interface UserService extends IService<User> {

    User getUserInfo(Integer userId);
    LoginResultVO login(UserLoginQuery query);
}
