package com.example.TheFinalBookStore.configs;

import com.example.TheFinalBookStore.TheFinalBookStoreApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.CacheControl;
import org.springframework.web.servlet.config.annotation.DefaultServletHandlerConfigurer;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

public class ServletInitializer extends SpringBootServletInitializer {
//Helps with putting out static data-
	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
		return application.sources(TheFinalBookStoreApplication.class);
	}


	@Configuration
	//@EnableWebMvc //The WebConfig enables Spring MVC annotations with @EnableWebMvc and configures component scanning for the com.booksystem.one package. It sets up the Mustache engine and registers static resource handlers.
	@ComponentScan(basePackages = {"com.example.TheFinalBookStore"}) //Component based annotation
	public class WebApplicationContextConfig implements WebMvcConfigurer {

		@Override
		public void configureDefaultServletHandling(DefaultServletHandlerConfigurer configurer) {
			configurer.enable();
		}

		@Override
		public void addResourceHandlers(ResourceHandlerRegistry registry) {
			registry.addResourceHandler("/resources/**").addResourceLocations("classpath:/**")
					.setCacheControl(CacheControl.noCache()).resourceChain(true);//Prenting cache memory in RAM
		}
	}


}
