Feature: Home page customisation

    Scenario Outline: Location facts
        Given a user from "<Location>" with the IP "<Address>"
        When they access the "/" page
        Then the "blockquote" element should contain the text "<Fact>"

    # TODO Update the 'global' version to be something approaching literate
    # TODO Allow for variations on names (eg every day in 'the' Northern Territory)

    Examples:
        | Location                     | Address         | Fact                                                                              |
        | Global                       | 127.0.0.1       | Every day in 800,000 people spend 75 minutes on public transport.                 |
        | Oceania                      | 210.48.81.144   | Every day in Oceania 800,000 people spend 75 minutes on public transport.         |
        | Australia                    | 1.44.224.0      | Every day in Australia 800,000 people spend 75 minutes on public transport.       |
        | Western Australia            | 1.44.136.2      | Every day in Western Australia 1 person spends 1 minute doing stuff.              |
        | Northern Territory           | 113.197.6.245   | Every day in Northern Territory 1 person spends 1 minute doing stuff.             |
        | South Australia              | 1.44.230.109    | Every day in South Australia 1 person spends 1 minute doing stuff.                |
        | Queensland                   | 1.42.51.101     | Every day in Queensland 1 person spends 1 minute doing stuff.                     |
        | New South Wales              | 1.41.148.137    | Every day in New South Wales 800,000 people spend 75 minutes on public transport. |
        | Australian Capital Territory | 60.242.111.148  | Every day in Australian Capital Territory 1 person spends 1 minute doing stuff.   |
        | Tasmania                     | 121.223.152.179 | Every day in Tasmania 1 person spends 1 minute doing stuff.                       |
        | Victoria                     | 1.44.232.124    | Every day in Victoria 1 person spends 1 minute doing stuff.                       |
        | Perth                        | 165.118.1.50    | Every day in Perth 1 person spends 1 minute doing stuff.                          |
        | Darwin                       | 138.80.0.10     | Every day in Darwin 1 person spends 1 minute doing stuff.                         |
        | Adelaide                     | 203.6.146.5     | Every day in Adelaide 1 person spends 1 minute doing stuff.                       |
        | Brisbane                     | 132.234.251.230 | Every day in Brisbane 1 person spends 1 minute doing stuff.                       |
        | Sydney                       | 203.27.21.6     | Every day in Sydney 800,000 people spend 75 minutes on public transport.          |
        | Canberra                     | 203.6.77.2      | Every day in Canberra 1 person spends 1 minute doing stuff.                       |
        | Melbourne                    | 140.159.2.36    | Every day in Melbourne 1 person spends 1 minute doing stuff.                      |
        | Hobart                       | 147.41.128.8    | Every day in Hobart 1 person spends 1 minute doing stuff.                         |

    Scenario Outline: Location fact sources
        Given a user from "<Location>" with the IP "<Address>"
        When they access the "/" page
        Then the "source" element should contain the text "<Source>"

    Examples:
        | Location                     | Address         | Source                                            |
        | Global                       | 127.0.0.1       | Bureau of Transport Statistics, Transport for NSW |
        | Oceania                      | 210.48.81.144   | Bureau of Transport Statistics, Transport for NSW |
        | Australia                    | 1.44.224.0      | Bureau of Transport Statistics, Transport for NSW |
        | Western Australia            | 1.44.136.2      | Martin's Imagination                              |
        | Northern Territory           | 113.197.6.245   | Martin's Imagination                              |
        | South Australia              | 1.44.230.109    | Martin's Imagination                              |
        | Queensland                   | 1.42.51.101     | Martin's Imagination                              |
        | New South Wales              | 1.41.148.137    | Bureau of Transport Statistics, Transport for NSW |
        | Australian Capital Territory | 60.242.111.148  | Martin's Imagination                              |
        | Tasmania                     | 121.223.152.179 | Martin's Imagination                              |
        | Victoria                     | 1.44.232.124    | Martin's Imagination                              |
        | Perth                        | 165.118.1.50    | Martin's Imagination                              |
        | Darwin                       | 138.80.0.10     | Martin's Imagination                              |
        | Adelaide                     | 203.6.146.5     | Martin's Imagination                              |
        | Brisbane                     | 132.234.251.230 | Martin's Imagination                              |
        | Sydney                       | 203.27.21.6     | Bureau of Transport Statistics, Transport for NSW |
        | Canberra                     | 203.6.77.2      | Martin's Imagination                              |
        | Melbourne                    | 140.159.2.36    | Martin's Imagination                              |
        | Hobart                       | 147.41.128.8    | Martin's Imagination                              |

    Scenario Outline: Location fact summaries
        Given a user from "<Location>" with the IP "<Address>"
        When they access the "/" page
        Then the "summary" element should contain the text "<Summary>"

    Examples:
        | Location                     | Address         | Summary                                                                                        |
        | Global                       | 127.0.0.1       | That's over 100 years spent checking facebook, reading the paper or staring out of the window. |
        | Oceania                      | 210.48.81.144   | That's over 100 years spent checking facebook, reading the paper or staring out of the window. |
        | Australia                    | 1.44.224.0      | That's over 100 years spent checking facebook, reading the paper or staring out of the window. |
        | Western Australia            | 1.44.136.2      | That's over 1 year spent checking facebook, reading the paper or staring out of the window.    |
        | Northern Territory           | 113.197.6.245   | That's over 1 year spent checking facebook, reading the paper or staring out of the window.    |
        | South Australia              | 1.44.230.109    | That's over 1 year spent checking facebook, reading the paper or staring out of the window.    |
        | Queensland                   | 1.42.51.101     | That's over 1 year spent checking facebook, reading the paper or staring out of the window.    |
        | New South Wales              | 1.41.148.137    | That's over 100 years spent checking facebook, reading the paper or staring out of the window. |
        | Australian Capital Territory | 60.242.111.148  | That's over 1 year spent checking facebook, reading the paper or staring out of the window.    |
        | Tasmania                     | 121.223.152.179 | That's over 1 year spent checking facebook, reading the paper or staring out of the window.    |
        | Victoria                     | 1.44.232.124    | That's over 1 year spent checking facebook, reading the paper or staring out of the window.    |
        | Perth                        | 165.118.1.50    | That's over 1 year spent checking facebook, reading the paper or staring out of the window.    |
        | Darwin                       | 138.80.0.10     | That's over 1 year spent checking facebook, reading the paper or staring out of the window.    |
        | Adelaide                     | 203.6.146.5     | That's over 1 year spent checking facebook, reading the paper or staring out of the window.    |
        | Brisbane                     | 132.234.251.230 | That's over 1 year spent checking facebook, reading the paper or staring out of the window.    |
        | Sydney                       | 203.27.21.6     | That's over 100 years spent checking facebook, reading the paper or staring out of the window. |
        | Canberra                     | 203.6.77.2      | That's over 1 year spent checking facebook, reading the paper or staring out of the window.    |
        | Melbourne                    | 140.159.2.36    | That's over 1 year spent checking facebook, reading the paper or staring out of the window.    |
        | Hobart                       | 147.41.128.8    | That's over 1 year spent checking facebook, reading the paper or staring out of the window.    |
