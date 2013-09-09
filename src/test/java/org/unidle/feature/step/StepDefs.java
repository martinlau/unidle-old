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
package org.unidle.feature.step;

import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.web.WebAppConfiguration;
import org.unidle.feature.config.FeatureConfig;
import org.unidle.feature.page.GenericPage;
import org.unidle.test.KnownLocation;
import org.unidle.test.KnownPage;

import java.net.URL;

import static org.fest.assertions.Assertions.assertThat;

@ContextConfiguration(classes = FeatureConfig.class)
@WebAppConfiguration
public class StepDefs {

    @Autowired
    private URL baseUrl;

    @Autowired
    private GenericPage genericPage;

    @Given("^a user$")
    public void a_user() throws Throwable {
        assertThat(genericPage.isAcceptable()).isTrue();
    }

    @Given("^a user from \"([^\"]*)\"$")
    public void a_user_from(final String location) throws Throwable {

        genericPage.addCookie("address", KnownLocation.byDisplay(location).address);
    }

    @Then("^the \"([^\"]*)\" element should contain the text \"([^\"]*)\"$")
    public void the_element_should_contain_the_text(final String element,
                                                    final String text) throws Throwable {

        assertThat(genericPage.getText(element)).contains(text);
    }

    @Then("^the page should contain \"([^\"]*)\"$")
    public void the_page_should_contain(final String content) throws Throwable {
        assertThat(genericPage.getText()).contains(content);
    }

    @Then("^the title should contain \"([^\"]*)\"$")
    public void the_title_should_contain(final String title) throws Throwable {

        assertThat(genericPage.getTitle()).contains(title);
    }

    @When("^they access the \"([^\"]*)\" page$")
    public void they_access_the_page(final String name) throws Throwable {
        genericPage.navigateTo(baseUrl + KnownPage.byDisplay(name).path);
    }

}
