 package com.example.redpacket.dao;

 import com.example.redpacket.entity.UserRedPacket;
 import org.apache.ibatis.annotations.Mapper;

 /**
  * @author wlli
  * @date 2021/04/19
  */
 @Mapper
 public interface UserRedPacketMapper {
     /**
      * 插入抢红包信息
      *
      * @Param userRedPacket抢红包信息
      * @return 影响记录数
      */
     public int grapRedPacket(UserRedPacket userRedPacket);
 }
