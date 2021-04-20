package com.example.redpacket.service;

import com.example.redpacket.dao.RedPacketMapper;
import com.example.redpacket.dao.UserRedPacketMapper;
import com.example.redpacket.entity.RedPacket;
import com.example.redpacket.entity.UserRedPacket;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import redis.clients.jedis.Jedis;

/**
 * @author wlli
 * @date 2021/04/19
 */
@Service
public class UserRedPacketServiceImpl implements UserRedPacketService {
    @Autowired
    private UserRedPacketMapper userRedPacketDao;
    @Autowired
    private RedPacketMapper redPacketDao;
    // 失败
    private static final int FAILED = 0;
    @Autowired
    private StringRedisTemplate redisTemplate = null;
    @Autowired
    private RedisRedPacketService redisRedPacketService = null;
    // Lua脚本
    String script = "local listKey = 'red_packet_list_'..KEYS[1] \n" + "local redPacket = 'red_packet_'..KEYS[1] \n"
            + "local stock = tonumber(redis.call('hget', redPacket, 'stock'))\n" + "if stock <= 0 then return 0 end \n"
            + "stock = stock -1 \n" + "redis . call ('hset', redPacket, 'stock', tostring (stock)) \n"
            + "redis.call('rpush', listKey, ARGV[1]) \n" + "if stock == 0 then return 2 end \n" + "return 1 \n";
    // 在缓存Lua脚本后，使用该变量保存Redis返回的32位的SHA1编码，使用它去执行缓存的 Lua脚本
    String sha1 = null;

    @Override
    @Transactional(isolation = Isolation.READ_COMMITTED, propagation = Propagation.REQUIRED)
    public int grapRedPacket(Long redPacketId, Long userId) {
        for (int i = 0; i < 3; i++) {
            // 获取红包信息
            RedPacket redPacket = redPacketDao.getRedPacket(redPacketId);
            // 当前小红包库存大于0
            if (redPacket.getStock() > 0) {
                int update = redPacketDao.decreaseRedPacketForVersion(redPacketId, redPacket.getVersion());
                // version被其他线程更改了，则重试
                if (0 == update) {
                    continue;
                }
                // 生成抢红包信息
                UserRedPacket userRedPacket = new UserRedPacket();
                userRedPacket.setRedPacketId(redPacketId);
                userRedPacket.setUserId(userId);
                userRedPacket.setAmount(redPacket.getUnitAmount());
                userRedPacket.setNote("抢红包 " + redPacketId);
                // 插入抢红包信息
                int result = userRedPacketDao.grapRedPacket(userRedPacket);
                return result;
            } else {
                return FAILED;
            }
        }
        // 失败返回
        return FAILED;
    }

    @Override
    public Long grapRedPacketByRedis(Long redPacketId, Long userId) {
        // 当前抢红包用户和日期信息
        String args = userId + "_" + System.currentTimeMillis();
        Long result = null;
        // 获取底层Redis操作对象
        Jedis jedis = (Jedis) redisTemplate.getConnectionFactory().getConnection().getNativeConnection();
        // 如果脚本没有加载过，那么进行加载，这样就会返回一个sha1编码
        if (sha1 == null) {
            sha1 = jedis.scriptLoad(script);
        }
        // 执行脚本，返回结果
        Object res = jedis.evalsha(sha1, 1, redPacketId.toString(), args);
        result = (Long) res;
        // 返回2时为最后一个红包，此时将抢红包信息通过异步保存到数据库中
        if (result == 2) {
            // 获取单个小红包金额
            String unitAmountStr = jedis.hget("red_pmcket_" + redPacketId, "unit_amount");
            // 触发保存数据库操作
            Double unitAmount = Double.parseDouble(unitAmountStr);
            System.err.println("thread_name    =    " + Thread.currentThread().getName());
            redisRedPacketService.saveUserRedPacketByRedis(redPacketId, unitAmount);
        }

        // 确保jedis顺利关闭
        if (jedis != null && jedis.isConnected()) {
            jedis.close();
        }
        return result;
    }
}
