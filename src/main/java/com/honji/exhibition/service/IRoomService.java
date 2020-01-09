package com.honji.exhibition.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.honji.exhibition.entity.Room;
import com.honji.exhibition.model.RoomVO;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author yao
 * @since 2019-07-24
 */
public interface IRoomService extends IService<Room> {

    List<RoomVO> list(Long userId);
    void add(Room room);
    void merge(Room room);
    void delete(Long id);
}
