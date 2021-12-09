package br.hspm.isolamento.main.config;

import org.springframework.context.annotation.Configuration;

import java.util.Arrays;
import springfox.documentation.builders.RequestParameterBuilder;

import org.springframework.context.annotation.Bean;

import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

@Configuration
public class SpringFoxSwaggerConfigurations {

	@Bean
    public Docket api() { 
        return new Docket(DocumentationType.SWAGGER_2)  
          .select()                                  
          .apis(RequestHandlerSelectors.any())              
          .paths(PathSelectors.any())                          
          .build()
          .globalRequestParameters(Arrays.asList(new RequestParameterBuilder().name("Authorization").description("Bearer Token").required(false).in("header").build()))
          .apiInfo(apiInfo());
    }
			
	private ApiInfo apiInfo() {
	    return new ApiInfoBuilder()
	            .title("Sistema de Isolamento")
	            .description("Aplicação para o gerenciamento de pacientes com MDR(Multidrug-Resistant)")
	            .version("1.0.0")
	            .license("Apache License Version 2.0")
	            .licenseUrl("https://www.apache.org/licenses/LICENSE-2.0")
	            .contact(new Contact("Henrique Lustosa", "https://henriquelustosa.me/", "henriqlustosa@gmail.com"))
	            .build();
	}
}
