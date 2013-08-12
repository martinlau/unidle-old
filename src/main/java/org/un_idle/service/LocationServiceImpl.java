package org.un_idle.service;

import com.maxmind.geoip2.DatabaseReader;
import com.maxmind.geoip2.GeoIp2Provider;
import com.maxmind.geoip2.model.Omni;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.expression.spel.ast.QualifiedIdentifier;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;

import static java.net.InetAddress.getByName;

@Service
public class LocationServiceImpl implements LocationService {

    private final GeoIp2Provider geoIp2Provider;

    @Autowired
    public LocationServiceImpl(@Qualifier("geoLite2Database") final Resource database) throws IOException {
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
