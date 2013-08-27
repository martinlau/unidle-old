package org.unidle.feature.config;

import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.PageFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.unidle.feature.page.GenericPage;

import java.net.MalformedURLException;
import java.net.URL;

import static java.lang.String.format;

@Configuration
public class FeatureConfig {

    @Value("${tomcat.port}")
    private int port;

    @Bean
    public static PropertySourcesPlaceholderConfigurer propertySourcesPlaceholderConfigurer() {
        return new PropertySourcesPlaceholderConfigurer();
    }

    @Bean
    public GenericPage genericPage() {
        return PageFactory.initElements(webDriver(), GenericPage.class);
    }

    @Bean
    public URL baseUrl() throws MalformedURLException {
        return new URL(format("http://127.0.0.1.xip.io:%d", port));
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
