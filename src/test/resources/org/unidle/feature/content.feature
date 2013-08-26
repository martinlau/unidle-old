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

