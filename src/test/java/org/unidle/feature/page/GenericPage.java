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
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.URI;
import java.util.Map;

public class GenericPage implements Page {

    private static final Logger LOGGER = LoggerFactory.getLogger(GenericPage.class);

    protected final WebDriver driver;

    @FindBy(tagName = "body")
    private WebElement bodyElement;

    public GenericPage(final WebDriver driver) {
        this.driver = driver;
    }

    public void addCookie(final String name,
                          final String value) {
        driver.manage().addCookie(new Cookie(name, value));
    }

    public void browseTo(final String path) {
        final String url = URI.create(driver.getCurrentUrl())
                              .resolve(path)
                              .toASCIIString();

        driver.navigate().to(url);
    }

    public void clearCookies() {
        driver.manage().deleteAllCookies();
    }

    public void clickElement(final String element) {
        driver.findElement(By.id(element)).click();
    }

    @Override
    public void fillForm(final Map<String, String> dataTable) {
        throw new UnsupportedOperationException();
    }

    @Override
    public String getPath() {
        return URI.create(driver.getCurrentUrl()).getPath();
    }

    @Override
    public String getText() {
        return bodyElement.getText();
    }

    @Override
    public String getTitle() {
        return driver.getTitle();
    }

    public String getText(final String element) {
        return bodyElement.findElement(By.id(element)).getText();
    }

}
