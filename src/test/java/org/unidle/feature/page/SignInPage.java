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

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class SignInPage extends GenericPage implements NavigablePage {

    @FindBy(id = "signin_facebook")
    private WebElement facebook;

    @FindBy(id = "signin_twitter")
    private WebElement twitter;

    public SignInPage(final WebDriver driver) {
        super(driver);
    }

    @Override
    public String name() {
        return "Sign in";
    }

    @Override
    public void browseTo() {
        browseTo("/signin");
    }

    public void signInWithFacebook() {
        facebook.click();
    }

    public void signInWithTwitter() {
        twitter.click();
    }

}
