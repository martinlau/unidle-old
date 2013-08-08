package org.un_idle.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.view.tiles3.TilesConfigurer;
import org.springframework.web.servlet.view.tiles3.TilesViewResolver;

@Configuration
public class TilesConfiguration {

    @Bean
    public TilesConfigurer tilesConfigurer() {
        final TilesConfigurer tilesConfigurer = new TilesConfigurer();

        return tilesConfigurer;
    }

    @Bean
    public ViewResolver viewResolver() {
        final TilesViewResolver viewResolver = new TilesViewResolver();

        return viewResolver;
    }

}
