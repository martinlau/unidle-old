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
package org.unidle.feature.page;

import org.openqa.selenium.By;
import org.openqa.selenium.Cookie;
import org.openqa.selenium.HasCapabilities;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static org.openqa.selenium.remote.CapabilityType.SUPPORTS_JAVASCRIPT;

public class GenericPage {

    private static final Logger LOGGER = LoggerFactory.getLogger(GenericPage.class);

    private final WebDriver driver;

    @FindBy(tagName = "body")
    private WebElement bodyElement;

    public GenericPage(final WebDriver driver) {
        this.driver = driver;
    }

    public void addCookie(final String name,
                          final String value) {
        driver.manage().addCookie(new Cookie(name, value));
    }

    public String getText(final String element) {
        return bodyElement.findElement(By.id(element)).getText();
    }

    public String getText() {
        return bodyElement.getText();
    }

    public String getTitle() {
        return driver.getTitle();
    }

    public boolean isAcceptable() {
        if (driver instanceof HasCapabilities) {
            return ((HasCapabilities) driver).getCapabilities().is(SUPPORTS_JAVASCRIPT);
        }
        LOGGER.warn("WebDriver '{}' doesn't support capabilities - can't tell if we're alive - hold on and hope for the best", driver);
        return true;
    }

    public void navigateTo(final String page) {
        driver.navigate().to(page);
    }

}
