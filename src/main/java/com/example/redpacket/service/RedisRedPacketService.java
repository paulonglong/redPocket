package com.example.redpacket.service;

/**
 * @author Li Wenlong
 * @since 2021/4/20
 */
public interface RedisRedPacketService {
    /**
     * 保存redis抢红包列表
     *
     * @param redPacketId ―抢红包编号
     * @param unitAmount  --红包金额
     */
    public void saveUserRedPacketByRedis(Long redPacketId, Double unitAmount);
}
