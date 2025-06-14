package com.example.service;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class AmapService {

    @Value("${amap.key}")
    private String apiKey;

    @Value("${amap.bizToken}")
    private String bizToken;

    @Value("${amap.bizSecret}")
    private String bizSecret;

    // 存储轨迹点
    private List<String> trackPoints = new ArrayList<>();

    /**
     * 获取高德地图API Key
     */
    public String getApiKey() {
        return this.apiKey;
    }

    /**
     * 添加轨迹点
     */
    public void addTrackPoint(String lng, String lat) {
        trackPoints.add(lng + "," + lat);
    }

    /**
     * 获取当前轨迹
     */
    public String getCurrentTrack() {
        return String.join(";", trackPoints);
    }

    /**
     * 调用高德地图API
     */
    public String callAmapApi(String url) throws Exception {
        HttpClient httpClient = HttpClients.createDefault();
        HttpGet request = new HttpGet(url);
        HttpResponse response = httpClient.execute(request);
        return EntityUtils.toString(response.getEntity());
    }
}