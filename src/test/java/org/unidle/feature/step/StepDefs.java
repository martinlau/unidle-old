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

import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.ContextHierarchy;
import org.unidle.config.CacheConfiguration;
import org.unidle.config.DataConfiguration;
import org.unidle.domain.Question;
import org.unidle.domain.UserConnection;
import org.unidle.feature.config.PageConfiguration;
import org.unidle.feature.config.SocialCredentialsConfiguration;
import org.unidle.feature.config.WebDriverConfiguration;
import org.unidle.feature.page.FacebookAuthenticationPage;
import org.unidle.feature.page.GenericPage;
import org.unidle.feature.page.SignInPage;
import org.unidle.feature.page.TwitterAuthenticationPage;
import org.unidle.repository.QuestionRepository;
import org.unidle.repository.UserConnectionRepository;
import org.unidle.test.KnownLocation;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.net.URL;
import java.util.List;
import java.util.Map;

import static com.google.common.collect.Sets.newHashSet;
import static java.lang.String.format;
import static org.fest.assertions.Assertions.assertThat;
import static org.junit.Assert.fail;
import static org.unidle.domain.UserConnection_.providerId;

@ContextHierarchy({@ContextConfiguration(classes = WebDriverConfiguration.class),
                   @ContextConfiguration(classes = PageConfiguration.class),
                   @ContextConfiguration(classes = CacheConfiguration.class),
                   @ContextConfiguration(classes = DataConfiguration.class),
                   @ContextConfiguration(classes = SocialCredentialsConfiguration.class)})
public class StepDefs {

    public static final int QUESTION_COUNT = 50;

    @Autowired
    private URL baseUrl;

    @Autowired
    private FacebookAuthenticationPage facebookAuthenticationPage;

    @Autowired
    private String facebookPassword;

    @Autowired
    private String facebookUsername;

    @Autowired
    private List<GenericPage> genericPages;

    @Autowired
    private QuestionRepository questionRepository;

    @Autowired
    private SignInPage signInPage;

    @Autowired
    private TwitterAuthenticationPage twitterAuthenticationPage;

    @Autowired
    private String twitterPassword;

    @Autowired
    private String twitterUsername;

    @Autowired
    private UserConnectionRepository userConnectionRepository;

    @When("^I access the \"([^\"]*)\" page$")
    public void I_access_the_page(final String name) {
        GenericPage target = findPage(name);
        target.browseTo();
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

    @When("^I click the \"([^\"]*)\" \"([^\"]*)\" element")
    public void I_click_the_nth_element(final String index,
                                        final String element) {
        switch (index) {
            case "first":
                genericPage().clickElement(0, element);
                break;
            default:
                fail("Unknown index: " + index);
        }
    }

    protected GenericPage genericPage() {
        return findPage("Generic Page");
    }

    private GenericPage findPage(final String name) {
        for (GenericPage genericPage : genericPages) {
            if (genericPage.getName().equals(name)) {
                return genericPage;
            }
        }
        fail("Unknown page: " + name);
        return null;
    }

    @When("^I click the \"([^\"]*)\" page \"([^\"]*)\" element$")
    public void I_click_the_page_element(final String page,
                                         final String element) {
        findPage(page).clickElement(element);
    }

    @When("^I fill in the \"([^\"]*)\" form with:$")
    public void I_fill_in_the_form_with(final String name,
                                        final List<Map<String, String>> data) {
        findPage(name).fillForm(data);
    }

    @Given("^I have previously connected to \"([^\"]*)\"$")
    public void I_have_previously_connected_to(final String service) throws Throwable {
        switch (service) {
            case "Facebook":
            case "Twitter":
                assertThat(userConnectionRepository.count(hasService(service.toLowerCase()))).isPositive();
                break;
            default:
                fail("Unknown service: " + service);
        }
    }

    public static Specification<UserConnection> hasService(final String service) {
        return new Specification<UserConnection>() {
            @Override
            public Predicate toPredicate(final Root<UserConnection> root,
                                         final CriteriaQuery<?> criteriaQuery,
                                         final CriteriaBuilder criteriaBuilder) {
                return criteriaBuilder.equal(root.get(providerId), service);
            }
        };
    }

    @Given("^I have previously registered via \"([^\"]*)\"$")
    public void I_have_previously_registered_via(final String service) throws Throwable {
        switch (service) {
            case "Facebook":
            case "Twitter":
                assertThat(userConnectionRepository.count(hasService(service.toLowerCase()))).isPositive();
                break;
            default:
                fail("Unknown service: " + service);
        }
    }

    @When("^I log in with \"([^\"]*)\"$")
    public void I_log_in_with(final String service) throws Throwable {
        switch (service) {
            case "Facebook":
                I_log_in_with_facebook();
                break;
            case "Twitter":
                I_log_in_with_twitter();
                break;
            default:
                fail("Unknown service: " + service);
        }
    }

    @When("^I log in with Facebook$")
    public void I_log_in_with_facebook() {
        I_access_the_page("Sign in");
        I_choose_to_sign_in_with("Facebook");
        I_provide_my_credentials("Facebook");
    }

    @When("^I log in with Twitter$")
    public void I_log_in_with_twitter() {
        I_access_the_page("Sign in");
        I_choose_to_sign_in_with("Twitter");
        I_provide_my_credentials("Twitter");
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
        if (facebookAuthenticationPage.isFacebookPage()) {
            if (facebookAuthenticationPage.requiresCredentials()) {
                facebookAuthenticationPage.setEmailOrPhone(facebookUsername);
                facebookAuthenticationPage.setPassword(facebookPassword);
                facebookAuthenticationPage.setKeepMeLoggedIn(false);
            }
            facebookAuthenticationPage.submit();
        }
    }

    @When("^I provide my Twitter credentials$")
    public void I_provide_my_twitter_credentials() {
        if (twitterAuthenticationPage.isTwitterPage()) {
            if (twitterAuthenticationPage.requiresCredentials()) {
                twitterAuthenticationPage.setUsername(twitterUsername);
                twitterAuthenticationPage.setPassword(twitterPassword);
                twitterAuthenticationPage.setRemember(false);
            }
            twitterAuthenticationPage.submit();
        }
    }

    @Then("^I should see the \"([^\"]*)\" form validation errors:$")
    public void I_should_see_the_form_validation_errors(final String name,
                                                        final List<Map<String, String>> data) throws Throwable {

        final GenericPage genericPage = findPage(name);

        for (Map<String, String> row : data) {

            final String field = row.get("Field Name");
            final String message = row.get("Message");

            if (message != null && !"".equals(message)) {
                assertThat(genericPage.getValidationError(field)).contains(message);
            }
        }
    }

    @Then("^I should see the \"([^\"]*)\" page$")
    public void I_should_see_the_page(final String page) {
        assertThat(genericPage().getPath()).endsWith(findPage(page).getPath());
    }

    @Given("^a user$")
    public void a_user() {
        genericPage().browseTo(baseUrl.toExternalForm());
    }

    @Given("^a user from \"([^\"]*)\"$")
    public void a_user_from(final String location) {
        a_user();

        genericPage().addCookie("address", KnownLocation.byDisplay(location).address);
    }

    @And("^other people have previously asked questions$")
    public void other_people_have_previously_asked_questions() throws Throwable {
        if (questionRepository.count() < QUESTION_COUNT) {
            for (int i = 1; i <= QUESTION_COUNT; i++) {
                final Question question = new Question();

                question.setQuestion(format("This is cucumber dummy question %d", i));
                question.setTags(newHashSet("cucumber", "question"));

                questionRepository.save(question);
            }
        }
    }

    @Then("^the \"([^\"]*)\" element should contain the text \"([^\"]*)\"$")
    public void the_element_should_contain_the_text(final String element,
                                                    final String text) {

        assertThat(genericPage().getText(element)).contains(text);
    }

    @Then("^the \"([^\"]*)\" element should not contain the text \"([^\"]*)\"$")
    public void the_element_should_not_contain_the_text(final String element,
                                                        final String text) {

        assertThat(genericPage().getText(element)).doesNotContain(text);
    }

    @Then("^the page should contain \"([^\"]*)\"$")
    public void the_page_should_contain(final String content) {
        assertThat(genericPage().getText()).contains(content);
    }

    @Then("^the \"([^\"]*)\" page should contain \"([^\"]*)\" \"([^\"]*)\" elements$")
    public void the_page_should_contain(final String page,
                                        final int count,
                                        final String element) {
        assertThat(findPage(page).countElements(element)).isEqualTo(count);
    }

    @Then("^the page should not contain \"([^\"]*)\"$")
    public void the_page_should_not_contain(final String content) {
        assertThat(genericPage().getText()).doesNotContain(content);
    }

    @Then("^the title should contain \"([^\"]*)\"$")
    public void the_title_should_contain(final String title) {

        assertThat(genericPage().getTitle()).contains(title);
    }

}
