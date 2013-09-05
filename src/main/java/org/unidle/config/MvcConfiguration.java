/*
 * #%L
 * unidle
 * %%
 * Copyright (C) 2013 Martin Lau
 * %%
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU Affero General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 * #L%
 */
package org.unidle.config;

import com.github.segmentio.Analytics;
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
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.view.tiles3.TilesConfigurer;
import org.springframework.web.servlet.view.tiles3.TilesViewResolver;
import org.unidle.service.LocationService;
import org.unidle.service.UserService;
import org.unidle.web.BuildTimestampInterceptor;
import org.unidle.web.CurrentUserInterceptor;
import org.unidle.web.CurrentUserMethodArgumentResolver;
import org.unidle.web.LocationMethodArgumentResolver;
import org.unidle.web.SegmentIoApiKeyInterceptor;

import javax.annotation.PostConstruct;
import java.util.List;

import static com.github.segmentio.Analytics.initialize;
import static java.lang.Boolean.FALSE;

@ComponentScan("org.unidle.controller")
@Configuration
@EnableWebMvc
@EnableTransactionManagement
public class MvcConfiguration extends WebMvcConfigurerAdapter {

    @Value("${unidle.build.timestamp}")
    private String buildTimestamp;

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

    @Value("${unidle.segment.io.apiKey}")
    private String segmentIoApiKey;

    @Value("${unidle.segment.io.secret}")
    private String segmentIoSecret;

    @Autowired
    private UserService userService;

    @Bean
    public static PropertySourcesPlaceholderConfigurer propertySourcesPlaceholderConfigurer() {
        return new PropertySourcesPlaceholderConfigurer();
    }

    @Override
    public void addArgumentResolvers(final List<HandlerMethodArgumentResolver> argumentResolvers) {
        argumentResolvers.add(locationMethodArgumentResolver());
        argumentResolvers.add(currentUserMethodArgumentResolver());
    }

    @Bean
    public CurrentUserMethodArgumentResolver currentUserMethodArgumentResolver() {
        return new CurrentUserMethodArgumentResolver(userService);
    }

    @Override
    public void addInterceptors(final InterceptorRegistry registry) {
        registry.addInterceptor(buildTimestampInterceptor());
        registry.addInterceptor(currentUserInterceptor());
        registry.addInterceptor(segmentIoApiKeyInterceptor());
    }

    @Bean
    public HandlerInterceptor buildTimestampInterceptor() {
        return new BuildTimestampInterceptor(buildTimestamp);
    }

    @Bean
    public HandlerInterceptor segmentIoApiKeyInterceptor() {
        return new SegmentIoApiKeyInterceptor(segmentIoApiKey);
    }

    @Bean
    public HandlerInterceptor currentUserInterceptor() {
        return new CurrentUserInterceptor(userService);
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
    public LocationMethodArgumentResolver locationMethodArgumentResolver() {
        return new LocationMethodArgumentResolver(locationService);
    }

    @PostConstruct
    public void initializeAnalytics() {
        initialize(segmentIoSecret);
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
