package cn.lovingliu.config;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Configuration;

/**
 * @Author：LovingLiu
 * @Description: 配置扫描路径
 * @Date：Created in 2020-02-07
 */
@Configuration
@MapperScan("cn.lovingliu.mapper")
public class MybatisConfig {
}
