package org.un_idle.service;

import org.un_idle.geo.Location;

public interface LocationService {

    Location forAddress(String address) throws Exception;

}
