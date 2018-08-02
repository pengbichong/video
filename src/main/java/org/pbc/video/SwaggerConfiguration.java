package org.pbc.video;

import org.springframework.context.annotation.Bean;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger.web.UiConfiguration;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * Created by LittleTry
 * Date: 2017-12-01
 * Time: 上午3:08
 * @author LittleTry
 */
@EnableSwagger2
public class SwaggerConfiguration {
    @Bean
    public Docket getApiInfo() {
        return new Docket(DocumentationType.SWAGGER_2)
                .groupName("outer api")
                .select()
                .apis(RequestHandlerSelectors.any())
                .paths(PathSelectors.any())
                .build()
                .apiInfo(outApiInfo());
    }
    private ApiInfo outApiInfo() {
        return new ApiInfo(
                // title 标题
                "video接口文档",
                // description 描述 标题下
                "接口文档",
                // version
                "1.0.0",
                // termsOfService
                "http://mylearn/*",
                // contact
                new Contact("pbc","","1528641447@qq.com"),
                // licence
                "Apache 2.0",
                // licence url
                "http://www.apache.org/licenses/LICENSE-2.0.html"
        );
    }
    @Bean
    public UiConfiguration getUiConfig() {
        return new UiConfiguration(
                // url,暂不用
                null,
                // docExpansion          => none | list
                "none",
                // apiSorter             => alpha
                "alpha",
                // defaultModelRendering => schema
                "schema",
                UiConfiguration.Constants.DEFAULT_SUBMIT_METHODS,
                // enableJsonEditor      => true | false
                false,
                // showRequestHeaders    => true | false
                true);
    }
}