package com.example.redpacket.dao;

import org.apache.ibatis.annotations.Mapper;

import com.example.redpacket.entity.RedPacket;

/**
 * @author wlli
 * @date 2021/04/19
 */
@Mapper
public interface RedPacketMapper {
    /**
     * 获取红包信息
     *
     * @param id 红包 id
     * @return 红包具体信息
     */
    public RedPacket getRedPacket(Long id);

    /**
     * 获取红包信息，使用悲观锁，将该id对应的记录锁住
     *
     * @param id
     *            红包 id
     * @return 红包具体信息
     */
    public RedPacket getRedPacketForUpdate(Long id);

    /**
     * 扣减抢红包数
     *
     * @param id -- 红包id
     * @return 更新记录条数
     */
    public int decreaseRedPacket(Long id);


    /**
     * 扣减抢红包数，通过业务相关的oldStock来实现乐观锁
     * 
     * @param id
     * @param oldStock
     * @return
     */
    public int decreaseRedPacketForOldStock(Long id, Integer oldStock);

    /**
     * 扣减抢红包数，通过业务无关的版本号来实现乐观锁，避免ABA问题
     * 
     * @param id
     * @return
     */
    public int decreaseRedPacketForVersion(Long id, Integer version);
}
