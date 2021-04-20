package com.example.redpacket.service;

import com.example.redpacket.entity.RedPacket;

/**
 * @author wlli
 * @date 2021/04/19
 */
public interface RedPacketService {
    /**
     * 获取红包
     *
     * @param id 编号
     * @return 红包信息
     */
    public RedPacket getRedPacket(Long id);

    /**
     * 扣减红包
     *
     * @param id 编号
     * @return 影响条数
     */
    public int decreaseRedPacket(Long id);
}
