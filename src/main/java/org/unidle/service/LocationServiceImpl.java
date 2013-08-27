/*
 * #%L
 * unidle
 * %%
 * Copyright (C) 2013 Martin Lau
 * %%
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU Affero General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 * #L%
 */
package org.unidle.service;

import com.maxmind.geoip2.DatabaseReader;
import com.maxmind.geoip2.GeoIp2Provider;
import com.maxmind.geoip2.exception.AddressNotFoundException;
import com.maxmind.geoip2.exception.GeoIp2Exception;
import com.maxmind.geoip2.model.Omni;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.net.UnknownHostException;
import java.util.Set;

import static java.net.InetAddress.getByName;
import static org.springframework.util.StringUtils.commaDelimitedListToStringArray;
import static org.springframework.util.StringUtils.hasText;
import static org.unidle.service.Location.DEFAULT;

@Service
public class LocationServiceImpl implements LocationService {

    private static final Logger LOGGER = LoggerFactory.getLogger(LocationServiceImpl.class.getName());

    private final GeoIp2Provider geoIp2Provider;

    private final Set<String> internalIps;

    @Autowired
    public LocationServiceImpl(@Value("${unidle.maxmind.database}") final Resource database,
                               @Value("#{'${unidle.internal.ips}'.split(',')}") final Set<String> internalIps) throws IOException {
        this(database.getFile(), internalIps);
    }

    public LocationServiceImpl(final File database,
                               final Set<String> internalIps) throws IOException {
        this.geoIp2Provider = new DatabaseReader(database);
        this.internalIps = internalIps;
    }

    @Cacheable("org.unidle.service.LocationService")
    @Override
    public Location locateAddress(final String address) {

        if (!hasText(address)) {
            return DEFAULT;
        }

        final String firstAddress = commaDelimitedListToStringArray(address)[0];

        if (internalIps.contains(firstAddress)) {
            return DEFAULT;
        }

        try {
            final Omni omni = geoIp2Provider.omni(getByName(firstAddress));

            return new Location(omni.getCity().getName(),
                                omni.getMostSpecificSubdivision().getName(),
                                omni.getCountry().getName(),
                                omni.getContinent().getName());
        }
        catch (UnknownHostException | AddressNotFoundException e) {
            LOGGER.warn("Unknown location: {}", address);
        }
        catch (GeoIp2Exception | IOException e) {
            LOGGER.error("Error looking up address: {}", address, e);
        }

        return DEFAULT;
    }

}
