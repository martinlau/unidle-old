package org.unidle.feature.config;

import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;

import java.net.MalformedURLException;
import java.net.URL;

import static java.lang.String.format;

@Configuration
public class FeatureConfig {

    @Bean
    public static PropertySourcesPlaceholderConfigurer propertySourcesPlaceholderConfigurer() {
        return new PropertySourcesPlaceholderConfigurer();
    }

    @Value("${tomcat.port}")
    private int port;

    @Bean
    public URL baseUrl() throws MalformedURLException {
        return new URL(format("http://localhost:%d", port));
    }

    @Bean
    public Dimension dimension() {
        return new Dimension(320, 568);
    }

    @Bean(destroyMethod = "quit")
    public WebDriver webDriver() {
        return new FirefoxDriver();
    }

}
