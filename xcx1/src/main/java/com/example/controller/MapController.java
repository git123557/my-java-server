package com.example.controller;

import com.example.service.AmapService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/map")
public class MapController {

    @Autowired
    private AmapService amapService;

    /**
     * 添加位置点
     */
    @PostMapping("/addPoint")
    public String addPoint(@RequestParam String lng, @RequestParam String lat) {
        amapService.addTrackPoint(lng, lat);
        return "Point added";
    }

    /**
     * 获取当前轨迹
     */
    @GetMapping("/getTrack")
    public String getTrack() {
        return amapService.getCurrentTrack();
    }

    /**
     * 获取高德地图key
     */
    @GetMapping("/getKey")
    public String getKey() {
        return amapService.getApiKey();
    }
}