package com.honji.exhibition.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.honji.exhibition.entity.User;
import com.honji.exhibition.model.UserSessionVO;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

public interface UserMapper extends BaseMapper<User> {
//    @Insert("INSERT INTO exhibition_user(user_name) VALUES(#{userName})")
//    void insert(User user);

    @Select("SELECT `user`.id, shop.id as shopId, shop.code as shopCode, shop.type as shopType FROM `user` " +
            "LEFT JOIN shop ON `user`.shop_id = shop.id  " +
            "WHERE open_id = #{openId}")
    UserSessionVO selectForSession(@Param("openId")String openId);
}
