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
package org.unidle.feature.config;

import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.PageFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.unidle.feature.page.AboutPage;
import org.unidle.feature.page.AccountPage;
import org.unidle.feature.page.FacebookAuthenticationPage;
import org.unidle.feature.page.GenericPage;
import org.unidle.feature.page.HomePage;
import org.unidle.feature.page.SignInPage;
import org.unidle.feature.page.SignUpPage;
import org.unidle.feature.page.TwitterAuthenticationPage;

import java.net.MalformedURLException;
import java.net.URL;

import static java.lang.String.format;

@Configuration
public class FeatureConfiguration {

    @Value("${tomcat.port}")
    private int port;

    @Bean
    public static PropertySourcesPlaceholderConfigurer propertySourcesPlaceholderConfigurer() {
        return new PropertySourcesPlaceholderConfigurer();
    }

    @Bean
    public AboutPage aboutPage() {
        return PageFactory.initElements(webDriver(), AboutPage.class);
    }

    @Bean(destroyMethod = "quit")
    public WebDriver webDriver() {
        return new FirefoxDriver();
    }

    @Bean
    public AccountPage accountPage() {
        return PageFactory.initElements(webDriver(), AccountPage.class);
    }

    @Bean
    public URL baseUrl() throws MalformedURLException {
        return new URL(format("http://127.0.0.1.xip.io:%d", port));
    }

    @Bean
    public Dimension dimension() {
        return new Dimension(320, 568);
    }

    @Bean
    public FacebookAuthenticationPage facebookAuthenticationPage() {
        return PageFactory.initElements(webDriver(), FacebookAuthenticationPage.class);
    }

    @Bean
    public GenericPage genericPage() {
        return PageFactory.initElements(webDriver(), GenericPage.class);
    }

    @Bean
    public HomePage homePage() {
        return PageFactory.initElements(webDriver(), HomePage.class);
    }

    @Bean
    public SignInPage signInPage() {
        return PageFactory.initElements(webDriver(), SignInPage.class);
    }

    @Bean
    public SignUpPage signUpPage() {
        return PageFactory.initElements(webDriver(), SignUpPage.class);
    }

    @Bean
    public TwitterAuthenticationPage twitterAuthenticationPage() {
        return PageFactory.initElements(webDriver(), TwitterAuthenticationPage.class);
    }

}
