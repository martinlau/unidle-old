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
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class TwitterAuthenticationPage extends GenericPage {

    @FindBy(id = "allow")
    private WebElement allow;

    @FindBy(id = "password")
    private WebElement password;

    @FindBy(id = "remember")
    private WebElement remember;

    @FindBy(id = "username_or_email")
    private WebElement usernameOrEmail;

    public TwitterAuthenticationPage(final WebDriver driver) {
        super(driver);
    }

    public boolean requiresCredentials() {
        try {
            return usernameOrEmail.isDisplayed();
        }
        catch (NoSuchElementException e) {
            return false;
        }
    }

    public void setPassword(final String password) {
        this.password.clear();
        this.password.sendKeys(password);
    }

    public void setRemember(final boolean checked) {
        if (remember.isSelected() ^ checked) {
            remember.click();
        }
    }

    public void setUsername(final String usernameOrEmail) {
        this.usernameOrEmail.clear();
        this.usernameOrEmail.sendKeys(usernameOrEmail);
    }

    public void submit() {
        allow.click();
    }

}
