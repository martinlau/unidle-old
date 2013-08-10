package org.un_idle.service;

import org.un_idle.domain.LocationFact;

public interface LocationFactService {

    LocationFact findBestFact(final String city,
                              final String subdivision,
                              final String country,
                              final String continent);

}
