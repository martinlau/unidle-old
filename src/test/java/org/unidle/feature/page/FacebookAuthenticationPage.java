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

import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class FacebookAuthenticationPage extends GenericPage {

    @FindBy(id = "email")
    private WebElement emailOrPhone;

    @FindBy(id = "persist_box")
    private WebElement keepMeLoggedIn;

    @FindBy(id = "u_0_1")
    private WebElement login;

    @FindBy(id = "pass")
    private WebElement password;

    public FacebookAuthenticationPage(final WebDriver driver) {
        super(driver);
    }

    public boolean isFacebookPage() {
        return currentUrl().contains("facebook.com");
    }

    public boolean requiresCredentials() {
        try {
            return currentUrl().contains("facebook.com") && emailOrPhone.isDisplayed();
        }
        catch (NoSuchElementException e) {
            return false;
        }
    }

    public void setEmailOrPhone(final String emailOrPhone) {
        this.emailOrPhone.clear();
        this.emailOrPhone.sendKeys(emailOrPhone);
    }

    public void setKeepMeLoggedIn(final boolean keepMeLoggedIn) {
        if (this.keepMeLoggedIn.isSelected() ^ keepMeLoggedIn) {
            this.keepMeLoggedIn.click();
        }
    }

    public void setPassword(final String password) {
        this.password.clear();
        this.password.sendKeys(password);
    }

    public void submit() {
        login.click();
    }

}
