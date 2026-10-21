package com.example.TheFinalBookStore.configs;

import com.example.TheFinalBookStore.TheFinalBookStoreApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

// Används när applikationen deployas som WAR till en extern servlet-container.
public class ServletInitializer extends SpringBootServletInitializer {

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(TheFinalBookStoreApplication.class);
    }
}
