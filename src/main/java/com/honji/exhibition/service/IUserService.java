package com.honji.exhibition.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.honji.exhibition.entity.User;
import com.honji.exhibition.model.UserSessionVO;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author yao
 * @since 2019-03-01
 */
public interface IUserService extends IService<User> {

    void insert(User user);
    UserSessionVO getForSession(String openId);
}
