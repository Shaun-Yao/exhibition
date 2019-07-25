package com.honji.exhibition.mapper;

import com.honji.exhibition.entity.Participant;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author yao
 * @since 2019-07-07
 */
public interface ParticipantMapper extends BaseMapper<Participant> {

    @Select("SELECT *  FROM participant WHERE user_id IN (SELECT id FROM `user` " +
            "WHERE shop_id IN ( SELECT id FROM shop WHERE area IN ( SELECT area FROM shop WHERE id = ( SELECT shop_id FROM `user` WHERE id = #{userId} ) ) ))")
    List<Participant> selectByArea(Long userId);

    @Select("SELECT * FROM participant WHERE sex = 3")
    List<Participant> selectChildren();

}
