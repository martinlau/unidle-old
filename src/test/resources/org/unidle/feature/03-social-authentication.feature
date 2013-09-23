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
            | Field Name | Value |
            | First Name |       |
            | Last Name  |       |
            | Email      |       |
        Then I should see the "Sign up" form validation errors:
            | Field Name | Message          |
            | First Name | may not be empty |
            | Last Name  | may not be empty |
            | Email      | may not be empty |
        When I fill in the "Sign up" form with:
            | Field Name | Value            |
            | First Name | John             |
            | Last Name  | Smith            |
            | Email      | john@example.com |
        Then I should see the "Account" page

    Scenario: New user registration via Twitter
        Given a user
        When I access the "Sign in" page
        And I choose to sign in with "Twitter"
        And I provide my "Twitter" credentials
        Then I should see the "Sign up" page
        When I fill in the "Sign up" form with:
            | Field Name | Value |
            | First Name |       |
            | Last Name  |       |
            | Email      |       |
        Then I should see the "Sign up" form validation errors:
            | Field Name | Message          |
            | First Name | may not be empty |
            | Last Name  | may not be empty |
            | Email      | may not be empty |
        When I fill in the "Sign up" form with:
            | Field Name | Value            |
            | First Name | John             |
            | Last Name  | Smith            |
            | Email      | john@example.com |
        Then I should see the "Sign up" form validation errors:
            | Field Name | Message                              |
            | Email      | That email address is already in use |
        When I fill in the "Sign up" form with:
            | Field Name | Value                 |
            | First Name | Johnathon             |
            | Last Name  | Smithers              |
            | Email      | johnathon@example.com |
        Then I should see the "Account" page

    Scenario: Existing user login via Facebook
        Given a user
        And I have previously registered via "Facebook"
        When I access the "Sign in" page
        And I choose to sign in with "Facebook"
        And I provide my "Facebook" credentials
        Then I should see the "Account" page

    Scenario: Existing user login via Twitter
        Given a user
        And I have previously registered via "Twitter"
        When I access the "Sign in" page
        And I choose to sign in with "Twitter"
        And I provide my "Twitter" credentials
        Then I should see the "Account" page
