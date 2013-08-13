package org.unidle.service;

import org.unidle.domain.LocationFact;

public interface LocationFactService {

    LocationFact findBestFact(final String city,
                              final String subdivision,
                              final String country,
                              final String continent);

}
