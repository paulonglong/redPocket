package com.example.redpacket.controller;

import com.example.redpacket.entity.RedPacket;
import com.example.redpacket.service.RedPacketService;
import com.example.redpacket.service.UserRedPacketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.text.DateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Li Wenlong
 * @since 2021/4/19
 */
@Controller
@RequestMapping("/userRedPacket")
public class RedPacketController {
    @Autowired
    private UserRedPacketService UserRedPacketService;
    @Autowired
    private RedPacketService redPacketService;

    @GetMapping("/getRedPacket4jsp")
    public String getRedPacket4jsp(Model model) {
        model.addAttribute("name", "wlli");
        model.addAttribute("now", DateFormat.getDateTimeInstance().format(new Date()));
        RedPacket result = redPacketService.getRedPacket(2L);
        model.addAttribute("redpocket", result);
        return "index";
    }

    @GetMapping("/grapRedPacket4jsp")
    public String grapRedPacket4jsp() {
        return "grap";
    }

    @RequestMapping(value = "/grapRedPacket")
    @ResponseBody
    public Map<String, Object> grapRedPacket(Long redPacketId, Long userId) {
        // 抢红包
        int result = UserRedPacketService.grapRedPacket(redPacketId, userId);
        Map<String, Object> retMap = new HashMap<String, Object>();
        boolean flag = result > 0;
        retMap.put("success", flag);
        retMap.put("message", flag ? "抢红包成功" : "抢红包失败");
        return retMap;
    }

    @RequestMapping(value = "/grapRedPacketByRedis")
    @ResponseBody
    public Map<String, Object> grapRedPacketByRedis(Long redPacketId, Long userId) {
        // 抢红包
        Long result = UserRedPacketService.grapRedPacketByRedis(redPacketId, userId);
        Map<String, Object> retMap = new HashMap<String, Object>();
        boolean flag = result > 0;
        retMap.put("success", flag);
        retMap.put("message", flag ? "抢红包成功" : "抢红包失败");
        return retMap;
    }
}
