package org.unidle.feature.step;

import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import org.openqa.selenium.By;
import org.openqa.selenium.Cookie;
import org.openqa.selenium.WebDriver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.web.WebAppConfiguration;
import org.unidle.feature.config.FeatureConfig;

import java.net.URL;

import static org.fest.assertions.Assertions.assertThat;

@ContextConfiguration(classes = FeatureConfig.class)
@WebAppConfiguration
public class StepDefs {

    @Autowired
    private WebDriver webDriver;

    @Autowired
    private URL baseUrl;

    @Given("^a user from \"([^\"]*)\" with the IP \"([^\"]*)\"$")
    public void a_user_from_with_the_IP(final String location,
                                        final String address) throws Throwable {
        webDriver.manage().addCookie(new Cookie("location", location));
        webDriver.manage().addCookie(new Cookie("address", address));
    }

    @Then("^the \"([^\"]*)\" element should contain the text \"([^\"]*)\"$")
    public void the_element_should_contain_the_text(final String element,
                                                    final String text) throws Throwable {

        final String elementText = webDriver.findElement(By.tagName(element)).getText();

        assertThat(elementText).contains(text);
    }

    @When("^they access the \"([^\"]*)\" page$")
    public void they_access_the_page(final String path) throws Throwable {
        webDriver.navigate().to(baseUrl + path);
    }

}
