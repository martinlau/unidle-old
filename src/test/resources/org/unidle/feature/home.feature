Feature: Home page customisation

    Scenario Outline:
        Given a user from "<Location>" with the IP "<Address>"
        When they access the "/" page
        Then the "blockquote" element should contain the text "<Fact>"
        And the "source" element should contain the text "<Source>"
        And the "summary" element should contain the text "<Summary>"

    # TODO Update the 'global' version to be something approaching literate
    # TODO Update 'people' to be 'person' if singular
    # TODO Update 'spend' to 'spends' if '1'
    # TODO Allow for variations on names (eg every day in 'the' Northern Territory)
    Examples:
        | Location                     | Address         | Fact                                                                              | Source                                            | Summary                                                                                        |
        | Global                       | 127.0.0.1       | Every day in 800,000 people spend 75 minutes on public transport.                 | Bureau of Transport Statistics, Transport for NSW | That's over 100 years spent checking facebook, reading the paper or staring out of the window. |
        | Oceania                      | 210.48.81.144   | Every day in Oceania 800,000 people spend 75 minutes on public transport.         | Bureau of Transport Statistics, Transport for NSW | That's over 100 years spent checking facebook, reading the paper or staring out of the window. |
        | Australia                    | 1.44.224.0      | Every day in Australia 800,000 people spend 75 minutes on public transport.       | Bureau of Transport Statistics, Transport for NSW | That's over 100 years spent checking facebook, reading the paper or staring out of the window. |
        | Western Australia            | 1.44.136.2      | Every day in Western Australia 1 people spend 1 minutes doing stuff.              | Martin's Imagination                              | That's over 1 years spent checking facebook, reading the paper or staring out of the window.   |
        | Northern Territory           | 113.197.6.245   | Every day in Northern Territory 1 people spend 1 minutes doing stuff.             | Martin's Imagination                              | That's over 1 years spent checking facebook, reading the paper or staring out of the window.   |
        | South Australia              | 1.44.230.109    | Every day in South Australia 1 people spend 1 minutes doing stuff.                | Martin's Imagination                              | That's over 1 years spent checking facebook, reading the paper or staring out of the window.   |
        | Queensland                   | 1.42.51.101     | Every day in Queensland 1 people spend 1 minutes doing stuff.                     | Martin's Imagination                              | That's over 1 years spent checking facebook, reading the paper or staring out of the window.   |
        | New South Wales              | 1.41.148.137    | Every day in New South Wales 800,000 people spend 75 minutes on public transport. | Bureau of Transport Statistics, Transport for NSW | That's over 100 years spent checking facebook, reading the paper or staring out of the window. |
        | Australian Capital Territory | 60.242.111.148  | Every day in Australian Capital Territory 1 people spend 1 minutes doing stuff.   | Martin's Imagination                              | That's over 1 years spent checking facebook, reading the paper or staring out of the window.   |
        | Tasmania                     | 121.223.152.179 | Every day in Tasmania 1 people spend 1 minutes doing stuff.                       | Martin's Imagination                              | That's over 1 years spent checking facebook, reading the paper or staring out of the window.   |
        | Victoria                     | 1.44.232.124    | Every day in Victoria 1 people spend 1 minutes doing stuff.                       | Martin's Imagination                              | That's over 1 years spent checking facebook, reading the paper or staring out of the window.   |
        | Perth                        | 165.118.1.50    | Every day in Perth 1 people spend 1 minutes doing stuff.                          | Martin's Imagination                              | That's over 1 years spent checking facebook, reading the paper or staring out of the window.   |
        | Darwin                       | 138.80.0.10     | Every day in Darwin 1 people spend 1 minutes doing stuff.                         | Martin's Imagination                              | That's over 1 years spent checking facebook, reading the paper or staring out of the window.   |
        | Adelaide                     | 203.6.146.5     | Every day in Adelaide 1 people spend 1 minutes doing stuff.                       | Martin's Imagination                              | That's over 1 years spent checking facebook, reading the paper or staring out of the window.   |
        | Brisbane                     | 132.234.251.230 | Every day in Brisbane 1 people spend 1 minutes doing stuff.                       | Martin's Imagination                              | That's over 1 years spent checking facebook, reading the paper or staring out of the window.   |
        | Sydney                       | 203.27.21.6     | Every day in Sydney 800,000 people spend 75 minutes on public transport.          | Bureau of Transport Statistics, Transport for NSW | That's over 100 years spent checking facebook, reading the paper or staring out of the window. |
        | Canberra                     | 203.6.77.2      | Every day in Canberra 1 people spend 1 minutes doing stuff.                       | Martin's Imagination                              | That's over 1 years spent checking facebook, reading the paper or staring out of the window.   |
        | Melbourne                    | 140.159.2.36    | Every day in Melbourne 1 people spend 1 minutes doing stuff.                      | Martin's Imagination                              | That's over 1 years spent checking facebook, reading the paper or staring out of the window.   |
        | Hobart                       | 147.41.128.8    | Every day in Hobart 1 people spend 1 minutes doing stuff.                         | Martin's Imagination                              | That's over 1 years spent checking facebook, reading the paper or staring out of the window.   |
