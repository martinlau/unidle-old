package org.un_idle.service;

import com.maxmind.geoip2.DatabaseReader;
import com.maxmind.geoip2.GeoIp2Provider;
import com.maxmind.geoip2.model.Omni;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;

import static java.net.InetAddress.getByName;

@Service
public class LocationServiceImpl implements LocationService {

    private final GeoIp2Provider geoIp2Provider;

    // TODO Remove this constructor and shift .mmdb to environment config
    public LocationServiceImpl() throws IOException {
        this(new ClassPathResource("/maxmind/GeoLite2-City.mmdb"));
    }

    public LocationServiceImpl(final Resource database) throws IOException {
        this(database.getFile());
    }

    public LocationServiceImpl(final File database) throws IOException {
        this.geoIp2Provider = new DatabaseReader(database);
    }

    @Override
    public Location locateAddress(final String address) throws Exception {
        final Omni omni = geoIp2Provider.omni(getByName(address));

        return new Location(omni.getCity().getName(),
                            omni.getMostSpecificSubdivision().getName(),
                            omni.getCountry().getName(),
                            omni.getContinent().getName());
    }

}
