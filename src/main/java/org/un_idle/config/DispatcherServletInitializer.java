package org.un_idle.config;

import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.web.bind.support.WebArgumentResolver;
import org.springframework.web.filter.DelegatingFilterProxy;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.i18n.AcceptHeaderLocaleResolver;
import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;
import org.springframework.web.servlet.view.tiles3.TilesConfigurer;
import org.springframework.web.servlet.view.tiles3.TilesViewResolver;
import org.un_idle.geo.LocationArgumentResolver;
import ro.isdc.wro.extensions.processor.css.RubySassCssProcessor;
import ro.isdc.wro.http.ConfigurableWroFilter;
import ro.isdc.wro.http.WroFilter;
import ro.isdc.wro.manager.factory.ConfigurableWroManagerFactory;
import ro.isdc.wro.manager.factory.WroManagerFactory;
import ro.isdc.wro.model.resource.processor.factory.ConfigurableProcessorsFactory;
import ro.isdc.wro.model.resource.processor.factory.ProcessorsFactory;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import java.io.IOException;
import java.util.EnumSet;
import java.util.Properties;

import static javax.servlet.SessionTrackingMode.COOKIE;

public class DispatcherServletInitializer extends AbstractAnnotationConfigDispatcherServletInitializer {

    @Override
    protected Class<?>[] getRootConfigClasses() {
        return new Class<?>[]{ContextConfiguration.class};
    }

    @Override
    protected Class<?>[] getServletConfigClasses() {
        return new Class<?>[]{MvcConfiguration.class};
    }

    @Override
    public void onStartup(final ServletContext servletContext) throws ServletException {
        super.onStartup(servletContext);

        servletContext.setSessionTrackingModes(EnumSet.of(COOKIE));

        final DelegatingFilterProxy wroFilter = servletContext.createFilter(DelegatingFilterProxy.class);
        wroFilter.setTargetFilterLifecycle(true);

        servletContext.addFilter("wroFilter",
                                 wroFilter)
                      .addMappingForUrlPatterns(null,
                                                false,
                                                "/resources/*");
    }

    @Override
    protected String[] getServletMappings() {
        return new String[]{"/"};
    }

    @Configuration
    public static class ContextConfiguration {

        @Bean
        public LocaleResolver localeResolver() {
            return new AcceptHeaderLocaleResolver();
        }

        @Bean
        public WebArgumentResolver locationArgumentResolver() throws IOException {
            final Resource resource = new ClassPathResource("/maxmind/GeoLite2-City.mmdb");

            return new LocationArgumentResolver(resource);
        }

        @Bean
        public MessageSource messageSource() {
            final ResourceBundleMessageSource messageSource = new ResourceBundleMessageSource();

            messageSource.setDefaultEncoding("UTF-8");
            messageSource.setBasename("messages.un-idle");

            return messageSource;
        }

    }

    @ComponentScan("org.un_idle.controller")
    @Configuration
    @EnableWebMvc
    public static class MvcConfiguration extends WebMvcConfigurerAdapter {

        @Override
        public void addResourceHandlers(final ResourceHandlerRegistry registry) {
            registry.addResourceHandler("/font/*")
                    .addResourceLocations("classpath:/META-INF/resources/webjars/font-awesome/3.2.1/font/");
        }

        @Bean
        public TilesConfigurer tilesConfigurer() {
            return new TilesConfigurer();
        }

        @Bean
        public ViewResolver viewResolver() {
            return new TilesViewResolver();
        }

        @Bean
        public WroFilter wroFilter() {
            final ConfigurableWroFilter wroFilter = new ConfigurableWroFilter();

            wroFilter.setWroManagerFactory(wroManagerFactory());
            wroFilter.setCacheUpdatePeriod(5L);
            wroFilter.setDebug(false);
            wroFilter.setEncoding("UTF-8");

            return wroFilter;
        }

        @Bean
        public WroManagerFactory wroManagerFactory() {
            final ConfigurableWroManagerFactory wroManagerFactory = new ConfigurableWroManagerFactory();

            wroManagerFactory.setProcessorsFactory(processorsFactory());

            return wroManagerFactory;
        }

        @Bean
        public ProcessorsFactory processorsFactory() {
            final ConfigurableProcessorsFactory processorsFactory = new ConfigurableProcessorsFactory();

            final Properties properties = new Properties();
            properties.put("postProcessors",
                           RubySassCssProcessor.ALIAS);

            processorsFactory.setProperties(properties);

            return processorsFactory;
        }

    }

}
