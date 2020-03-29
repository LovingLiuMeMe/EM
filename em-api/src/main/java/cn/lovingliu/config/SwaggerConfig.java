package cn.lovingliu.config;

import io.swagger.annotations.Api;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * @Author：LovingLiu
 * @Description: Swagger配置
 * @Date：Created in 2019-10-29
 * http://127.0.0.1:8000/swagger-ui.html 原网页
 * http://127.0.0.1:8000/doc.html bootstrap风格
 */
@Configuration
@EnableSwagger2
public class SwaggerConfig {
    @Bean
    public Docket createRestApi(){
        return new Docket(DocumentationType.SWAGGER_2)
            .apiInfo(apiInfo())
            .select()
            //为指定包下controller生成API文档
            .apis(RequestHandlerSelectors.basePackage("cn.lovingliu.controller"))
            //为有@Api注解的Controller生成API文档
            .apis(RequestHandlerSelectors.withClassAnnotation(Api.class))
            //为有@ApiOperation注解的方法生成API文档
            //.apis(RequestHandlerSelectors.withMethodAnnotation(ApiOperation.class))
            .paths(PathSelectors.any())
            .build();
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("体育器材管理系统")
                .contact(new Contact("lovingLiu","www.lovingliu.cn","lovingliu@126.com"))
                .description("基于 SpringBoot 版本")
                .version("1.0.0")
                .termsOfServiceUrl("http://www.lovingliu.cn")
                .build();
    }
}
