package com.honji.exhibition.mapper;

import com.honji.exhibition.entity.Shop;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Select;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author yao
 * @since 2019-07-08
 */
public interface ShopMapper extends BaseMapper<Shop> {

    @Select("SELECT * FROM shop WHERE id = (SELECT shop_id FROM USER WHERE id = #{user_id})")
    Shop selectByUserId(Long userId);

}
