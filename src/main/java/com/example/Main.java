package com.example;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

@SpringBootApplication
@RestController
public class Main {

    public static void main(String[] args) {
        SpringApplication.run(Main.class, args);
    }

    // 高德地图API密钥（从环境变量获取，更安全）
    private static final String AMAP_KEY = System.getenv("AMAP_API_KEY");

    // 示例接口：根据地址获取经纬度（地理编码）
    @GetMapping("/geocode")
    public String geocode(@RequestParam String address) {
        String url = "https://restapi.amap.com/v3/geocode/geo?" +
                "key=" + AMAP_KEY +
                "&address=" + address;
        
        RestTemplate restTemplate = new RestTemplate();
        return restTemplate.getForObject(url, String.class);
    }

    // 示例接口：获取POI搜索结果
    @GetMapping("/poi")
    public String searchPoi(@RequestParam String keywords, @RequestParam String city) {
        String url = "https://restapi.amap.com/v3/place/text?" +
                "key=" + AMAP_KEY +
                "&keywords=" + keywords +
                "&city=" + city +
                "&output=json";
        
        RestTemplate restTemplate = new RestTemplate();
        return restTemplate.getForObject(url, String.class);
    }

    // CORS配置：允许小程序前端跨域调用
    @Bean
    public CorsFilter corsFilter() {
        CorsConfiguration config = new CorsConfiguration();
        // 允许所有域名进行跨域调用（生产环境建议指定具体域名）
        config.addAllowedOriginPattern("*");
        // 允许任何请求头
        config.addAllowedHeader("*");
        // 允许任何方法（POST、GET等）
        config.addAllowedMethod("*");
        // 允许携带凭证（如cookie）
        config.setAllowCredentials(true);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        // 对所有接口都有效
        source.registerCorsConfiguration("/**", config);
        
        return new CorsFilter(source);
    }
}
