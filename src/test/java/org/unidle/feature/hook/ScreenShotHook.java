package org.unidle.feature.hook;

import cucumber.api.Scenario;
import cucumber.api.java.After;
import org.openqa.selenium.TakesScreenshot;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.web.WebAppConfiguration;
import org.unidle.feature.config.FeatureConfig;

import static org.openqa.selenium.OutputType.BYTES;
import static org.springframework.http.MediaType.IMAGE_PNG_VALUE;

@ContextConfiguration(classes = FeatureConfig.class)
@WebAppConfiguration
public class ScreenShotHook {

    @Autowired(required = false)
    private TakesScreenshot takesScreenshot;

    @After
    public void afterScenario(final Scenario scenario) {
        if (takesScreenshot == null) {
            return;
        }

        final byte[] bytes = takesScreenshot.getScreenshotAs(BYTES);

        scenario.write("<p>");
        scenario.embed(bytes, IMAGE_PNG_VALUE);
        scenario.write("</p>");
    }

}
