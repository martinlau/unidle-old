###
# #%L
# unidle
# %%
# Copyright (C) 2013 Martin Lau
# %%
# This program is free software: you can redistribute it and/or modify
# it under the terms of the GNU Affero General Public License as published by
# the Free Software Foundation, either version 3 of the License, or
# (at your option) any later version.
#
# This program is distributed in the hope that it will be useful,
# but WITHOUT ANY WARRANTY; without even the implied warranty of
# MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
# GNU General Public License for more details.
#
# You should have received a copy of the GNU Affero General Public License
# along with this program.  If not, see <http://www.gnu.org/licenses/>.
# #L%
###

Feature: Questions

    As a registered user
    In order to make the world a better place
    I want to be able to ask and respond to questions

    Scenario: Anonymous user views questions
        Given a user
        And other people have previously asked questions
        When I access the "Questions" page
        Then the "Questions" page should contain "5" "Questions" elements
        And the page should not contain "Ask a question"
        And the page should contain "Sign in"
        When I click the "Questions" page "Load More" element
        Then the "Questions" page should contain "10" "Questions" elements

    Scenario: Authenticated user views questions
        Given a user
        And I have previously registered via "Facebook"
        When I log in with "Facebook"
        And I access the "Questions" page
        Then the "Questions" page should contain "5" "Questions" elements
        And the page should contain "Ask a question"
        And the page should not contain "Sign in"
        When I click the "Questions" page "Load More" element
        Then the "Questions" page should contain "10" "Questions" elements

    Scenario: Authenticated user asks a question
        Given a user
        And I have previously registered via "Facebook"
        When I log in with "Facebook"
        And I access the "Questions" page
        And I click the "Questions" page "Ask A Question" element
        Then I should see the "Create Question" page
        When I fill in the "Create Question" form with:
            | Field Name  | Value                      |
            | Question    | This is a test question    |
            | Attachments | classpath:images/puppy.jpg |
            | Tags        | test, cucumber, question   |
#        Then I should see the "Question" page
#        And the page should contain "This is a test question"
#        And the page should contain "#test"
#        And the page should contain "#cucumber"
#        And the page should contain "#question"
