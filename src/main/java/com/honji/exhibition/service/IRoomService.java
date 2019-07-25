package com.honji.exhibition.service;

import com.honji.exhibition.entity.Room;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author yao
 * @since 2019-07-24
 */
public interface IRoomService extends IService<Room> {

    void add(Room room);
}
