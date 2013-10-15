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

public class AccountPage extends GenericPage {

    @FindBy(id = "add_twitter")
    private WebElement addTwitter;

    @FindBy(id = "close")
    private WebElement close;

    @FindBy(id = "email")
    private WebElement email;

    @FindBy(id = "email_validation")
    private WebElement emailValidation;

    @FindBy(id = "firstName")
    private WebElement firstName;

    @FindBy(id = "firstName_validation")
    private WebElement firstNameValidation;

    @FindBy(id = "lastName")
    private WebElement lastName;

    @FindBy(id = "lastName_validation")
    private WebElement lastNameValidation;

    @FindBy(id = "submit")
    private WebElement submit;

    @FindBy(id = "update")
    private WebElement update;

    public AccountPage(final WebDriver driver) {
        super(driver, "Account", "/account");
    }

    @Override
    public void submit() {
        submit.click();
    }

}
