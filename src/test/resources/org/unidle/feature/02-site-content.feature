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

Feature: Site content

    As a site user
    In order to learn more about the site
    I want to read more information

    Scenario Outline: Information pages
        Given a user
        When I access the "<Page>" page
        Then I should see the "<Page>" page
        And the title should contain "<Title>"
        And the page should contain "<Content>"

    Scenarios:
        | Page    | Title    | Content                                                                                                    |
        | Home    | Unidle   | Find out more                                                                                              |
        | About   | About us | Unidle is platform for people to ask questions and provide solutions in those idle moments during the day. |
        | Sign in | Sign in  | We leave managing your password security to Twitter and Facebook.                                          |

