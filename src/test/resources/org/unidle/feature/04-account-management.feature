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

Feature: Account management

    As a registered user
    In order to manage my persona
    I want to be able to update my details

    Scenario: Access user account
        Given a user
        And I have previously registered via "Facebook"
        When I log in with "Facebook"
        Then I should see the "Account" page
        And the page should contain "John Smith"
        And the page should contain "john@example.com"
        And the page should contain "Facebook"

    Scenario: Update personal details
        Given a user
        And I have previously registered via "Facebook"
        When I log in with "Facebook"
        And I click the "update" element
        And I fill in the "Account" form with:
            | Field Name | Value                 |
            | First Name | Johnathon             |
            | Last Name  | Smithers              |
            | Email      | johnathon@example.com |
        Then I should see the "Account" form validation errors:
            | Field Name | Message                              |
            | Email      | That email address is already in use |
        When I fill in the "Account" form with:
            | Field Name | Value                          |
            | First Name | Johnathon                      |
            | Last Name  | Smithers                       |
            | Email      | johnathon.smithers@example.com |
        Then the page should contain "Your details have been updated"
        When I click the "close" element
        Then I should see the "Account" page
        And the page should contain "Johnathon Smithers"
        And the page should contain "johnathon.smithers@example.com"
        And the page should contain "Facebook"

    Scenario: Add a social network
        Given a user
        And I have previously registered via "Facebook"
        When I log in with "Facebook"
        And I click the "add_twitter" element
        And I provide my "Twitter" credentials
        Then the page should contain "Facebook"
        And the page should contain "Twitter"

    Scenario: Remove a social network

    Scenario: Remove account
