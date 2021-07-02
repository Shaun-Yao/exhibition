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


    /**
     * 查找该用户下面还未分配房间的参与人
     * @param userId
     * @return
     */
    @Select("SELECT * FROM participant WHERE user_id = #{userId}" +
                " and id not in (SELECT participant_id FROM room_participant)")
    List<Participant> selectAvailable(Long userId);

    /**
     * 查找该用户下面已经分配房间的参与人
     * @param userId
     * @return
     */
    @Select({"SELECT pp.* FROM participant pp",
            " JOIN room_participant rp on pp.id = rp.participant_id",
            "  WHERE pp.user_id = #{userId}",
    })
    List<Participant> selectUnAvailable(Long userId);

    /**
     * 查找同一个大区，来返日期相同还未分配房间的参与人
     * @param userId
     * @return
     */
    @Select({"SELECT pp.* FROM participant pp",
            " JOIN user on pp.user_id = user.id",
            " JOIN shop on user.shop_id = shop.id",
            " JOIN `schedule` sc on user.id = sc.user_id",
            " WHERE shop.area = ",
            " (SELECT area FROM `user` JOIN shop on user.shop_id = shop.id WHERE user.id = #{userId})",
            " and DATE_FORMAT(sc.arrived_time,'%Y-%m-%d') = ",
            " (SELECT DATE_FORMAT(arrived_time,'%Y-%m-%d') FROM `schedule` WHERE user_id = #{userId})",
            " and DATE_FORMAT(sc.leaved_time,'%Y-%m-%d') = ",
            " (SELECT DATE_FORMAT(leaved_time,'%Y-%m-%d') FROM `schedule` WHERE user_id = #{userId})",
            " AND pp.id not in (SELECT participant_id FROM room_participant)"
    })
    List<Participant> selectByArea(Long userId);


    /**
     * 查找同一个大区，来返日期相同还未分配房间的参与人
     * @param userId
     * @return
     */
    @Select({"SELECT pp.* FROM participant pp",
            " JOIN user on pp.user_id = user.id",
            " JOIN shop on user.shop_id = shop.id",
            " WHERE sex = 3",
            " and area = ",
            " (SELECT area FROM `user` JOIN shop on user.shop_id = shop.id WHERE user.id = #{userId})",
            " AND pp.id not in (SELECT participant_id FROM room_participant)"
    })
    List<Participant> selectChildren(Long userId);

    @Select({"SELECT pp.* FROM participant pp",
            " JOIN room_participant rp on pp.id = rp.participant_id",
            "  WHERE rp.room_id = #{roomId}",
    })
    List<Participant> selectByRoom(Long roomId);
}
