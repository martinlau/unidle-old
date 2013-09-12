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
package org.unidle.feature.hook;

import cucumber.api.Scenario;
import cucumber.api.java.After;
import org.openqa.selenium.TakesScreenshot;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.unidle.feature.config.WebDriverConfiguration;

import static org.openqa.selenium.OutputType.BYTES;
import static org.springframework.http.MediaType.IMAGE_PNG_VALUE;

@ContextConfiguration(classes = WebDriverConfiguration.class)
public class ScreenShotHook {

    @Autowired(required = false)
    private TakesScreenshot takesScreenshot;

    @After
    public void afterScenario(final Scenario scenario) {
        if (takesScreenshot == null) {
            return;
        }

        final byte[] bytes = takesScreenshot.getScreenshotAs(BYTES);

        scenario.embed(bytes, IMAGE_PNG_VALUE);
    }

}
