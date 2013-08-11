package org.un_idle.service;

import org.un_idle.geo.Location;

public interface LocationService {

    Location locateAddress(String address) throws Exception;

}
