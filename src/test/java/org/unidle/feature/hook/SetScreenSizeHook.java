package org.unidle.feature.hook;

import cucumber.api.Scenario;
import cucumber.api.java.Before;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebDriver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.web.WebAppConfiguration;
import org.unidle.feature.config.FeatureConfig;

@ContextConfiguration(classes = FeatureConfig.class)
@WebAppConfiguration
public class SetScreenSizeHook {

    @Autowired(required = false)
    private Dimension dimension;

    @Autowired(required = false)
    private WebDriver webDriver;

    @Before
    public void beforeScenario(@SuppressWarnings("unused") final Scenario scenario) {
        if (webDriver == null) {
            return;
        }

        webDriver.manage().window().setSize(dimension);
    }

}
