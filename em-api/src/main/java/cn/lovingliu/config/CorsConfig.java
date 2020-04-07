package cn.lovingliu.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

/**
 * @Author：LovingLiu
 * @Description: 实现跨域访问
 * @Date：Created in 2019-11-03
 */
@Configuration
public class CorsConfig {
    private CorsConfiguration buildConfig() {
        CorsConfiguration corsConfiguration = new CorsConfiguration();
        corsConfiguration.addAllowedOrigin("*"); // 1允许任何域名使用
        /**
         * 在前端设置withCredentials = true 的情况下
         * 后端要设置Access-Control-Allow-Origin为你的源地址，例如http://localhost:8088(前端页面的地址)，不能是*，
         * 而且还要设置header('Access-Control-Allow-Credentials: true');
         */
        corsConfiguration.addAllowedOrigin("http://127.0.0.1:8000"); // 该配置可设置多次
        corsConfiguration.addAllowedOrigin("http://localhost:8000");
        corsConfiguration.setAllowCredentials(true);
        corsConfiguration.addAllowedHeader("*"); // 2允许任何头
        corsConfiguration.addAllowedMethod("*"); // 3允许任何方法（post、get等）
        return corsConfiguration;
    }

    @Bean
    public CorsFilter corsFilter() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", buildConfig());
        return new CorsFilter(source);
    }
}
