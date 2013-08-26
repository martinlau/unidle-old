package org.unidle.feature.hook;

import cucumber.api.Scenario;
import cucumber.api.java.After;
import cucumber.api.java.Before;
import org.openqa.selenium.WebDriver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.web.WebAppConfiguration;
import org.unidle.feature.config.FeatureConfig;

@ContextConfiguration(classes = FeatureConfig.class)
@WebAppConfiguration
public class ClearCookiesHook {

    @Autowired
    private WebDriver webDriver;

    @After
    @Before
    public void clearCookies(@SuppressWarnings("unused") final Scenario scenario) {
        webDriver.manage()
                 .deleteAllCookies();
    }

}
