package org.unidle.config;

import de.bripkens.gravatar.DefaultImage;
import de.bripkens.gravatar.Gravatar;
import de.bripkens.gravatar.Rating;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.DefaultServletHandlerConfigurer;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.view.RedirectView;
import org.springframework.web.servlet.view.tiles3.TilesConfigurer;
import org.springframework.web.servlet.view.tiles3.TilesViewResolver;
import org.unidle.service.LocationService;
import org.unidle.web.GoogleAnalyticsIdInterceptor;
import org.unidle.web.BuildTimestampInterceptor;
import org.unidle.web.LocationHandlerMethodArgumentResolver;

import java.util.List;

import static java.lang.Boolean.FALSE;

@ComponentScan("org.unidle.controller")
@Configuration
@EnableWebMvc
@EnableTransactionManagement
public class MvcConfiguration extends WebMvcConfigurerAdapter {

    @Value("${unidle.build.timestamp}")
    private String buildTimestamp;

    @Value("${unidle.google.analyticsId}")
    private String googleAnalyticsId;

    @Value("${unidle.gravatar.https}")
    private boolean gravatarHttps;

    @Value("${unidle.gravatar.rating}")
    private Rating gravatarRating;

    @Value("${unidle.gravatar.size}")
    private int gravatarSize;

    @Value("${unidle.gravatar.defaultImage}")
    private DefaultImage gravatarStandardDefaultImages;

    @Autowired
    private LocationService locationService;

    @Value("${unidle.resource.cachePeriod}")
    private int resourceCachePeriod;

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
        registry.addInterceptor(googleAnalyticsIdInterceptor());
    }

    @Bean
    public HandlerInterceptor buildTimestampInterceptor() {
        return new BuildTimestampInterceptor(buildTimestamp);
    }

    @Bean
    public HandlerInterceptor googleAnalyticsIdInterceptor() {
        return new GoogleAnalyticsIdInterceptor(googleAnalyticsId);
    }

    @Override
    public void addResourceHandlers(final ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/favicon.ico")
                .addResourceLocations("/WEB-INF/image/favicon.ico")
                .setCachePeriod(resourceCachePeriod);
        registry.addResourceHandler("/font/*")
                .addResourceLocations("classpath:/META-INF/resources/webjars/font-awesome/3.2.1/font/")
                .setCachePeriod(resourceCachePeriod);
        registry.addResourceHandler("/fonts/*")
                .addResourceLocations("classpath:/META-INF/resources/webjars/bootstrap/3.0.0/fonts/")
                .setCachePeriod(resourceCachePeriod);
        registry.addResourceHandler("/image/*")
                .addResourceLocations("/WEB-INF/image/")
                .setCachePeriod(resourceCachePeriod);
        registry.addResourceHandler("/script/*")
                .addResourceLocations("/WEB-INF/script/")
                .setCachePeriod(resourceCachePeriod);
    }

    @Bean
    public LocationHandlerMethodArgumentResolver locationHandlerMethodArgumentResolver() {
        return new LocationHandlerMethodArgumentResolver(locationService);
    }

    @Bean
    public Gravatar gravatar() {
        final Gravatar gravatar = new Gravatar();

        gravatar.setRating(gravatarRating);
        gravatar.setSize(gravatarSize);
        gravatar.setHttps(gravatarHttps);
        gravatar.setStandardDefaultImage(gravatarStandardDefaultImages);

        return gravatar;
    }

    @Bean
    public TilesConfigurer tilesConfigurer() {
        return new TilesConfigurer();
    }

    @Bean
    public ViewResolver viewResolver() {
        final TilesViewResolver viewResolver = new TilesViewResolver();

        viewResolver.setExposePathVariables(FALSE);

        return viewResolver;
    }


}
