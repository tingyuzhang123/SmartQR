package nz.co.zufang.config;

import static com.google.common.collect.Lists.newArrayList;
import static springfox.documentation.schema.AlternateTypeRules.newRule;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.context.request.async.DeferredResult;

import com.fasterxml.classmate.TypeResolver;

import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.builders.ResponseMessageBuilder;
import springfox.documentation.schema.WildcardType;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.ResponseMessage;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;



@Configuration
public class SwaggerConfig {
    
    @Autowired
    private TypeResolver typeResolver;
	
    @Bean
    ApiInfo apiInfo() {
        ApiInfo apiInfo = new ApiInfo(
        "Recommended Engine",
        "Draft version of the Recommend Engine",
        "1.0.0",
        null,
        "XIAOLIANG LI",
        null,
        null );
        return apiInfo;
    }

    @Bean
    public Docket rentApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .select()
                .apis(RequestHandlerSelectors.basePackage("nz.co.zufang.controller"))
                .paths(PathSelectors.any())
                .build()
                .pathMapping("/")
//                .ignoredParameterTypes(MetaClass.class)
                .directModelSubstitute(LocalDate.class, String.class)
                .genericModelSubstitutes(ResponseEntity.class)
                .alternateTypeRules(
                        newRule(typeResolver.resolve(DeferredResult.class,
                                typeResolver.resolve(ResponseEntity.class, WildcardType.class)),
                                typeResolver.resolve(WildcardType.class)))
                .useDefaultResponseMessages(false)
                .globalResponseMessage(RequestMethod.GET, getBadRequestContent())
                .globalResponseMessage(RequestMethod.POST, getBadRequestContent())
                .globalResponseMessage(RequestMethod.DELETE, getBadRequestContent())
                .enableUrlTemplating(false)
                ;
    }

    private List<ResponseMessage> getBadRequestContent() {
        return newArrayList(new ResponseMessageBuilder()
                .code(500)
                .message("When the API encounters an unexpected exception")
                .build());
    }

}