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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Map;

public class SignUpPage extends GenericPage implements NavigablePage {

    public static final String EMAIL = "Email";

    public static final String FIRST_NAME = "First Name";

    public static final String LAST_NAME = "Last Name";

    private static final Logger LOGGER = LoggerFactory.getLogger(SignUpPage.class);

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

    public SignUpPage(final WebDriver driver) {
        super(driver);
    }

    @Override
    public void fillForm(final List<Map<String, String>> data) {
        for (Map<String, String> map : data) {

            final String name = map.get("Field Name");
            final String value = map.get("Value");

            switch (name) {
                case FIRST_NAME:
                    setField(firstName, value);
                    break;
                case LAST_NAME:
                    setField(lastName, value);
                    break;
                case EMAIL:
                    setField(email, value);
                    break;
            }

        }

        submit();
    }

    public void submit() {
        submit.click();
    }

    @Override
    public String getValidationError(final String name) {
        switch (name) {
            case FIRST_NAME:
                return firstNameValidation.getText();
            case LAST_NAME:
                return lastNameValidation.getText();
            case EMAIL:
                return emailValidation.getText();
            default:
                throw new IllegalArgumentException(name);
        }
    }

    @Override
    public String name() {
        return "Sign up";
    }

    public void browseTo() {
        LOGGER.warn("Browsing to the sign up page is probably not going to work");
        browseTo("/signup");
    }

    public void setEmail(final String email) {
        this.email.clear();
        this.email.sendKeys(email);
    }

    public void setFirstName(final String firstName) {
        this.firstName.clear();
        this.firstName.sendKeys(firstName);
    }

    public void setLastName(final String lastName) {
        this.lastName.clear();
        this.lastName.sendKeys(lastName);
    }

}
