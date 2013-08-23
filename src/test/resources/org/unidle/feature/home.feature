Feature: Home page customisation

    Scenario Outline:
        Given a user from "<Location>" with the IP "<Address>"
        When they access the "/" page
        Then the "blockquote" element should contain the text "<Fact>"

    Examples:
        | Location                     | Address         | Fact                                                                             |
        | Global                       | 127.0.0.1       | xxx                                                                              |
        | Oceania                      | 210.48.81.144   | Every day in Oceania 800000 people spend 75 minutes on public transport.         |
        | Australia                    | 1.44.224.0      | Every day in Australia 800000 people spend 75 minutes on public transport.       |
        | Western Australia            | 1.44.136.2      | xxx                                                                              |
        | Northern Territory           | 113.197.6.245   | xxx                                                                              |
        | South Australia              | 1.44.230.109    | xxx                                                                              |
        | Queensland                   | 1.42.51.101     | xxx                                                                              |
        | New South Wales              | 1.41.148.137    | Every day in New South Wales 800000 people spend 75 minutes on public transport. |
        | Australian Capital Territory | 60.242.111.148  | xxx                                                                              |
        | Tasmania                     | 121.223.152.179 | xxx                                                                              |
        | Victoria                     | 1.44.232.124    | xxx                                                                              |
        | Perth                        | 165.118.1.50    | xxx                                                                              |
        | Darwin                       | 138.80.0.10     | xxx                                                                              |
        | Adelaide                     | 203.6.146.5     | xxx                                                                              |
        | Brisbane                     | 132.234.251.230 | xxx                                                                              |
        | Sydney                       | 203.27.21.6     | Every day in Sydney 800000 people spend 75 minutes on public transport.          |
        | Canberra                     | 203.6.77.2      | xxx                                                                              |
        | Melbourne                    | 140.159.2.36    | xxx                                                                              |
        | Hobart                       | 147.41.128.8    | xxx                                                                              |
