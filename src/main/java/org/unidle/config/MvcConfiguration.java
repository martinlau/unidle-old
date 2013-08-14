package org.unidle.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.view.tiles3.TilesConfigurer;
import org.springframework.web.servlet.view.tiles3.TilesViewResolver;
import org.unidle.service.LocationService;
import org.unidle.web.BuildTimestampInterceptor;
import org.unidle.web.LocationHandlerMethodArgumentResolver;

import java.util.List;

@ComponentScan("org.unidle.controller")
@Configuration
@EnableWebMvc
public class MvcConfiguration extends WebMvcConfigurerAdapter {

    @Value("${unidle.build.timestamp}")
    private String buildTimestamp;

    @Autowired
    private LocationService locationService;

    @Bean
    public static PropertySourcesPlaceholderConfigurer propertySourcesPlaceholderConfigurer() {
        return new PropertySourcesPlaceholderConfigurer();
    }

    @Override
    public void addArgumentResolvers(final List<HandlerMethodArgumentResolver> argumentResolvers) {
        argumentResolvers.add(locationHandlerMethodArgumentResolver());
    }

    @Override
    public void addInterceptors(final InterceptorRegistry registry) {
        registry.addInterceptor(buildTimestampInterceptor());
    }

    @Bean
    public HandlerInterceptor buildTimestampInterceptor() {
        return new BuildTimestampInterceptor(buildTimestamp);
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
