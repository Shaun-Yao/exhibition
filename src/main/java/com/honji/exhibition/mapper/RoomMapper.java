package com.honji.exhibition.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.honji.exhibition.entity.Room;
import com.honji.exhibition.model.RoomVO;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author yao
 * @since 2019-07-24
 */
public interface RoomMapper extends BaseMapper<Room> {

    @Select({"<script>",
            "SELECT room.id, room.type, group_concat(participant.name) as userNames FROM room ",
            "LEFT JOIN room_participant rp ON room.id = rp.room_id ",
            "LEFT JOIN participant ON rp.participant_id = participant.id ",
            "WHERE room.user_id = #{userId} ",
            "GROUP BY room_id",
            "</script>"})
    List<RoomVO> selectListVO(@Param("userId") Long userId);


}
