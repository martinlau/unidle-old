package org.un_idle.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ro.isdc.wro.extensions.processor.css.RubySassCssProcessor;
import ro.isdc.wro.http.ConfigurableWroFilter;
import ro.isdc.wro.http.WroFilter;
import ro.isdc.wro.manager.factory.ConfigurableWroManagerFactory;
import ro.isdc.wro.manager.factory.WroManagerFactory;
import ro.isdc.wro.model.resource.processor.factory.ConfigurableProcessorsFactory;
import ro.isdc.wro.model.resource.processor.factory.ProcessorsFactory;

import java.util.Properties;

@Configuration
public class WroConfiguration {

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
        properties.put("postProcessors", RubySassCssProcessor.ALIAS);

        processorsFactory.setProperties(properties);

        return processorsFactory;
    }

}
