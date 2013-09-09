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

Feature: Home page customisation

    As a prospective user
    In order to feel motivated to join the site
    I want to be shown information about time wasted

    Scenario Outline: Location facts
        Given a user from "<Location>"
        When they access the "/" page
        Then the "blockquote" element should contain the text "<Fact>"
        Then the "source" element should contain the text "<Source>"
        Then the "summary" element should contain the text "<Summary>"

    Scenarios:
        | Location                     | Fact                                                                                | Source                                            | Summary                                                                                        |
        | Global                       | Every day in the world 1 person spends 1 minute doing stuff.                        | Martin's Imagination                              | That's over 1 year spent checking facebook, reading the paper or staring out of the window.    |
        | Oceania                      | Every day in Oceania 800,000 people spend 75 minutes on public transport.           | Bureau of Transport Statistics, Transport for NSW | That's over 100 years spent checking facebook, reading the paper or staring out of the window. |
        | Australia                    | Every day in Australia 800,000 people spend 75 minutes on public transport.         | Bureau of Transport Statistics, Transport for NSW | That's over 100 years spent checking facebook, reading the paper or staring out of the window. |
        | Western Australia            | Every day in Western Australia 1 person spends 1 minute doing stuff.                | Martin's Imagination                              | That's over 1 year spent checking facebook, reading the paper or staring out of the window.    |
        | Northern Territory           | Every day in the Northern Territory 1 person spends 1 minute doing stuff.           | Martin's Imagination                              | That's over 1 year spent checking facebook, reading the paper or staring out of the window.    |
        | South Australia              | Every day in South Australia 1 person spends 1 minute doing stuff.                  | Martin's Imagination                              | That's over 1 year spent checking facebook, reading the paper or staring out of the window.    |
        | Queensland                   | Every day in Queensland 1 person spends 1 minute doing stuff.                       | Martin's Imagination                              | That's over 1 year spent checking facebook, reading the paper or staring out of the window.    |
        | New South Wales              | Every day in New South Wales 800,000 people spend 75 minutes on public transport.   | Bureau of Transport Statistics, Transport for NSW | That's over 100 years spent checking facebook, reading the paper or staring out of the window. |
        | Australian Capital Territory | Every day in the Australian Capital Territory 1 person spends 1 minute doing stuff. | Martin's Imagination                              | That's over 1 year spent checking facebook, reading the paper or staring out of the window.    |
        | Tasmania                     | Every day in Tasmania 1 person spends 1 minute doing stuff.                         | Martin's Imagination                              | That's over 1 year spent checking facebook, reading the paper or staring out of the window.    |
        | Victoria                     | Every day in Victoria 1 person spends 1 minute doing stuff.                         | Martin's Imagination                              | That's over 1 year spent checking facebook, reading the paper or staring out of the window.    |
        | Perth                        | Every day in Perth 1 person spends 1 minute doing stuff.                            | Martin's Imagination                              | That's over 1 year spent checking facebook, reading the paper or staring out of the window.    |
        | Darwin                       | Every day in Darwin 1 person spends 1 minute doing stuff.                           | Martin's Imagination                              | That's over 1 year spent checking facebook, reading the paper or staring out of the window.    |
        | Adelaide                     | Every day in Adelaide 1 person spends 1 minute doing stuff.                         | Martin's Imagination                              | That's over 1 year spent checking facebook, reading the paper or staring out of the window.    |
        | Brisbane                     | Every day in Brisbane 1 person spends 1 minute doing stuff.                         | Martin's Imagination                              | That's over 1 year spent checking facebook, reading the paper or staring out of the window.    |
        | Sydney                       | Every day in Sydney 800,000 people spend 75 minutes on public transport.            | Bureau of Transport Statistics, Transport for NSW | That's over 100 years spent checking facebook, reading the paper or staring out of the window. |
        | Canberra                     | Every day in Canberra 1 person spends 1 minute doing stuff.                         | Martin's Imagination                              | That's over 1 year spent checking facebook, reading the paper or staring out of the window.    |
        | Melbourne                    | Every day in Melbourne 1 person spends 1 minute doing stuff.                        | Martin's Imagination                              | That's over 1 year spent checking facebook, reading the paper or staring out of the window.    |
        | Hobart                       | Every day in Hobart 1 person spends 1 minute doing stuff.                           | Martin's Imagination                              | That's over 1 year spent checking facebook, reading the paper or staring out of the window.    |
