package com.shop.shoponline.service;

import com.shop.shoponline.entity.User;
import com.baomidou.mybatisplus.extension.service.IService;
import com.shop.shoponline.query.UserLoginQuery;
import com.shop.shoponline.vo.LoginResultVO;
import com.shop.shoponline.vo.UserVO;
import org.springframework.web.multipart.MultipartFile;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author maoyumeng
 * @since 2023-11-07
 */
public interface UserService extends IService<User> {

    //获取用户信息
    User getUserInfo(Integer userId);
    //一键登录（微信登陆）
    LoginResultVO login(UserLoginQuery query);
    //修改用户信息
    UserVO editUserInfo(UserVO userVO);
    //修改头像
    String editUserAvatar(Integer userID, MultipartFile file);

}
