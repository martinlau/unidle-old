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
    I want to read more information
    To learn more about the site

    Scenario Outline: Information pages
        Given a user
        When they access the "<Page>" page
        Then the title should contain "<Title>"
        And the page should contain "<Content>"

    Examples:
        | Page        | Title        | Content                                                                                                    |
        | /about      | About us     | Unidle is platform for people to ask questions and provide solutions in those idle moments during the day. |
        | /commercial | Commercial   | If you want to use our service for commercial means, that's awesome.                                       |
        | /open       | Open         | We're always looking for help - in any form.                                                               |
        | /signin     | Sign in      | We leave managing your password security to Twitter and Facebook.                                          |
        | /terms      | Terms of use | Please read these Terms and Conditions                                                                     |

