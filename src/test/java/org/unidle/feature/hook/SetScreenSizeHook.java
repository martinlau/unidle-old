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

    @Autowired
    private Dimension dimension;

    @Autowired
    private WebDriver webDriver;

    @Before
    public void beforeScenario(@SuppressWarnings("unused") final Scenario scenario) {
        webDriver.manage()
                 .window()
                 .setSize(dimension);
    }

}
