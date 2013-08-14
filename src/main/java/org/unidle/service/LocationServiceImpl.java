package org.unidle.service;

import com.maxmind.geoip2.DatabaseReader;
import com.maxmind.geoip2.GeoIp2Provider;
import com.maxmind.geoip2.exception.AddressNotFoundException;
import com.maxmind.geoip2.model.Omni;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;

import static java.net.InetAddress.getByName;

@Service
public class LocationServiceImpl implements LocationService {

    private static final Logger LOGGER = LoggerFactory.getLogger(LocationServiceImpl.class.getName());

    private final GeoIp2Provider geoIp2Provider;

    @Autowired
    public LocationServiceImpl(@Value("${unidle.maxmind.database}") final Resource database) throws IOException {
        this(database.getFile());
    }

    public LocationServiceImpl(final File database) throws IOException {
        this.geoIp2Provider = new DatabaseReader(database);
    }

    @Override
    public Location locateAddress(final String address) throws Exception {
        try {
            final Omni omni = geoIp2Provider.omni(getByName(address));

            return new Location(omni.getCity().getName(),
                                omni.getMostSpecificSubdivision().getName(),
                                omni.getCountry().getName(),
                                omni.getContinent().getName());
        }
        catch (AddressNotFoundException e) {
            LOGGER.warn("Unknown location: {}", address);
        }

        return new Location();
    }

}
