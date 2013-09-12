
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

Feature: Social authentication

    As a prospective user
    In order to easily register for and log in to the site
    I want to be able to register and log in to the site using other services

    Scenario: New user registration via Facebook
        Given a user
        When I access the "Sign in" page
        And I choose to sign in with "Facebook"
        And I provide my "Facebook" credentials
        Then I should see the "Sign up" page
        When I fill in the "Sign up" form with:
            | First Name | Last Name | Email            |
            | John       | Smith     | john@example.com |
        Then I should see the "Account" page
        Then the page should contain "John Smith"
        And the page should contain "john@example.com"
        And the page should contain "Facebook"

    Scenario: New user registration via Twitter
        Given a user
        When I access the "Sign in" page
        And I choose to sign in with "Twitter"
        And I provide my "Twitter" credentials
        Then I should see the "Sign up" page
        When I fill in the "Sign up" form with:
            | First Name | Last Name | Email            |
            | John       | Smith     | john@example.com |
        Then I should see the "Account" page
        Then the page should contain "John Smith"
        And the page should contain "john@example.com"
        And the page should contain "Twitter"

#    Scenario: Existing user login via Facebook
#        Given a user
#        And I have previously registered via "Facebook" with:
#            | First Name | Last Name | Email            |
#            | John       | Smith     | john@example.com |
#        When I access the "Sign in" page
#        And I provide my "Facebook" credentials
#        And I authorize "Facebook" access
#        Then I should see the "Account" page
#        Then the page should contain "John Smith"
#        And the page should contain "john@example.com"
#        And the page should contain "Facebook"
#
#    Scenario: Existing user login via Twitter
#        Given a user
#        And I have previously registered via "Twitter" with:
#            | First Name | Last Name | Email            |
#            | John       | Smith     | john@example.com |
#        When I access the "Sign in" page
#        And I provide my "Twitter" credentials
#        And I authorize "Twitter" access
#        Then I should see the "Account" page
#        Then the page should contain "John Smith"
#        And the page should contain "john@example.com"
#        And the page should contain "Twitter"

