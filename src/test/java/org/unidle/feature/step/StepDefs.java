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

import cucumber.api.DataTable;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.ContextHierarchy;
import org.unidle.feature.config.PageConfiguration;
import org.unidle.feature.config.SocialCredentialsConfiguration;
import org.unidle.feature.config.WebDriverConfiguration;
import org.unidle.feature.page.FacebookAuthenticationPage;
import org.unidle.feature.page.GenericPage;
import org.unidle.feature.page.NavigablePage;
import org.unidle.feature.page.SignInPage;
import org.unidle.feature.page.TwitterAuthenticationPage;
import org.unidle.test.KnownLocation;

import java.net.URL;
import java.util.List;

import static org.fest.assertions.Assertions.assertThat;
import static org.junit.Assert.fail;

@ContextHierarchy({@ContextConfiguration(classes = WebDriverConfiguration.class),
                   @ContextConfiguration(classes = SocialCredentialsConfiguration.class),
                   @ContextConfiguration(classes = PageConfiguration.class)})
public class StepDefs {

    @Autowired
    private URL baseUrl;

    @Autowired
    private FacebookAuthenticationPage facebookAuthenticationPage;

    @Autowired
    private String facebookPassword;

    @Autowired
    private String facebookUsername;

    @Autowired
    private GenericPage genericPage;

    @Autowired
    private List<NavigablePage> navigablePages;

    @Autowired
    private SignInPage signInPage;

    @Autowired
    private TwitterAuthenticationPage twitterAuthenticationPage;

    @Autowired
    private String twitterPassword;

    @Autowired
    private String twitterUsername;

    @When("^I access the \"([^\"]*)\" page$")
    public void I_access_the_page(final String name) {
        NavigablePage target = findPage(name);
        target.browseTo();
    }

    @When("^I authorize \"([^\"]*)\" access$")
    public void I_authorize_access(final String service) {
        switch (service) {
            case "Facebook":
                I_authorize_twitter_access();
                break;
            case "Twitter":
                I_authorize_facebook_access();
                break;
            default:
                fail("Unknown service: " + service);
        }
    }

    @When("^I authorize Twitter access$")
    public void I_authorize_twitter_access() {
        twitterAuthenticationPage.submit();
    }

    @When("^I authorize Facebook access$")
    public void I_authorize_facebook_access() {
        facebookAuthenticationPage.authorize();
    }

    @When("^I choose to sign in with \"([^\"]*)\"$")
    public void I_choose_to_sign_in_with(final String service) {
        switch (service) {
            case "Facebook":
                signInPage.signInWithFacebook();
                break;
            case "Twitter":
                signInPage.signInWithTwitter();
                break;
            default:
                fail("Unknown service: " + service);
        }
    }

    @When("^I click the \"([^\"]*)\" element")
    public void I_click_the_element(final String element) {
        genericPage.clickElement(element);
    }

    @When("^I fill in the \"([^\"]*)\" form with:$")
    public void I_fill_in_the_form_with(final String name,
                                        final DataTable dataTable) {
        findPage(name).fillForm(dataTable.asMaps().get(0));
    }

    @Given("^I have previously registered via Facebook with:$")
    public void I_have_previously_registered_via_facebook_with(final DataTable dataTable) {
        I_access_the_page("Sign in");
        I_choose_to_sign_in_with("Facebook");
        I_provide_my_facebook_credentials();
        I_fill_in_the_form_with("Sign up", dataTable);
    }

    @Given("^I have previously registered via Twitter with:$")
    public void I_have_previously_registered_via_twitter_with(final DataTable dataTable) {
        I_access_the_page("Sign in");
        I_choose_to_sign_in_with("Twitter");
        I_provide_my_twitter_credentials();
        I_fill_in_the_form_with("Sign up", dataTable);
    }

    @Given("^I have previously registered via \"([^\"]*)\" with:$")
    public void I_have_previously_registered_via_with(final String service,
                                                      final DataTable dataTable) throws Throwable {
        switch (service) {
            case "Facebook":
                I_have_previously_registered_via_facebook_with(dataTable);
                break;
            case "Twitter":
                I_have_previously_registered_via_twitter_with(dataTable);
                break;
            default:
                fail("Unknown service: " + service);
        }
    }

    @When("^I provide my \"([^\"]*)\" credentials$")
    public void I_provide_my_credentials(final String service) {
        switch (service) {
            case "Facebook":
                I_provide_my_facebook_credentials();
                break;
            case "Twitter":
                I_provide_my_twitter_credentials();
                break;
            default:
                fail("Unknown service: " + service);
        }
    }

    @When("^I provide my Facebook credentials$")
    public void I_provide_my_facebook_credentials() {
        facebookAuthenticationPage.setUsername(facebookUsername);
        facebookAuthenticationPage.setPassword(facebookPassword);
        facebookAuthenticationPage.setRemember(false);
        facebookAuthenticationPage.submit();
    }

    @When("^I provide my Twitter credentials$")
    public void I_provide_my_twitter_credentials() {
        twitterAuthenticationPage.setUsername(twitterUsername);
        twitterAuthenticationPage.setPassword(twitterPassword);
        twitterAuthenticationPage.setRemember(false);
        twitterAuthenticationPage.submit();
    }

    @Then("^I should see the \"([^\"]*)\" page$")
    public void I_should_see_the_page(final String page) {
        assertThat(genericPage.getPath()).endsWith(findPage(page).getPath());
    }

    private NavigablePage findPage(final String name) {
        NavigablePage target = null;
        for (NavigablePage navigablePage : navigablePages) {
            if (navigablePage.name().equals(name)) {
                target = navigablePage;
            }
        }
        if (target == null) {
            fail("Unknown page: " + name);
        }
        return target;
    }

    @Given("^a user from \"([^\"]*)\"$")
    public void a_user_from(final String location) {
        a_user();

        genericPage.addCookie("address", KnownLocation.byDisplay(location).address);
    }

    @Given("^a user$")
    public void a_user() {
        genericPage.browseTo(baseUrl.toExternalForm());
    }

    @Then("^the \"([^\"]*)\" element should contain the text \"([^\"]*)\"$")
    public void the_element_should_contain_the_text(final String element,
                                                    final String text) {

        assertThat(genericPage.getText(element)).contains(text);
    }

    @Then("^the page should contain \"([^\"]*)\"$")
    public void the_page_should_contain(final String content) {
        assertThat(genericPage.getText()).contains(content);
    }

    @Then("^the title should contain \"([^\"]*)\"$")
    public void the_title_should_contain(final String title) {

        assertThat(genericPage.getTitle()).contains(title);
    }

}
