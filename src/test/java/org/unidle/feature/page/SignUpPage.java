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

import java.util.Map;

public class SignUpPage extends GenericPage implements NavigablePage {

    private static final Logger LOGGER = LoggerFactory.getLogger(SignUpPage.class);

    @FindBy(id = "email")
    private WebElement email;

    @FindBy(id = "firstName")
    private WebElement firstName;

    @FindBy(id = "lastName")
    private WebElement lastName;

    @FindBy(id = "submit")
    private WebElement submit;

    public SignUpPage(final WebDriver driver) {
        super(driver);
    }

    @Override
    public void fillForm(final Map<String, String> dataTable) {
        setFirstName(dataTable.get("First Name"));
        setLastName(dataTable.get("Last Name"));
        setEmail(dataTable.get("Email"));

        submit();
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

    public void submit() {
        submit.click();
    }

    @Override
    public String name() {
        return "Sign up";
    }

    public void browseTo() {
        LOGGER.warn("Browsing to the sign up page is probably not going to work");
        browseTo("/signup");
    }

}
