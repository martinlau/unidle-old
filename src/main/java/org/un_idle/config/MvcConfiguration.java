package org.un_idle.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.view.tiles3.TilesConfigurer;
import org.springframework.web.servlet.view.tiles3.TilesViewResolver;
import org.un_idle.service.LocationService;
import org.un_idle.web.LocationHandlerMethodArgumentResolver;

import java.util.List;

@ComponentScan({"org.un_idle.controller"})
@Configuration
@EnableWebMvc
public class MvcConfiguration extends WebMvcConfigurerAdapter {

    @Autowired
    private LocationService locationService;

    @Override
    public void addArgumentResolvers(final List<HandlerMethodArgumentResolver> argumentResolvers) {
        argumentResolvers.add(locationHandlerMethodArgumentResolver());
    }

    @Override
    public void addResourceHandlers(final ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/font/*")
                .addResourceLocations("classpath:/META-INF/resources/webjars/font-awesome/3.2.1/font/");
    }

    @Bean
    public LocationHandlerMethodArgumentResolver locationHandlerMethodArgumentResolver() {
        return new LocationHandlerMethodArgumentResolver(locationService);
    }

    @Bean
    public TilesConfigurer tilesConfigurer() {
        return new TilesConfigurer();
    }

    @Bean
    public ViewResolver viewResolver() {
        return new TilesViewResolver();
    }

}
