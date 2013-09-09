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
package org.unidle.service.test;

import com.google.common.base.Predicate;
import com.google.common.collect.MapMaker;
import com.maxmind.geoip2.DatabaseReader;
import com.maxmind.geoip2.exception.GeoIp2Exception;
import com.maxmind.geoip2.model.Omni;
import org.springframework.core.io.ClassPathResource;

import java.io.File;
import java.io.IOException;
import java.net.InetAddress;
import java.util.Date;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.ScheduledExecutorService;

import static com.google.common.collect.Iterables.all;
import static java.lang.Boolean.TRUE;
import static java.lang.String.format;
import static java.util.concurrent.Executors.newFixedThreadPool;
import static java.util.concurrent.Executors.newSingleThreadScheduledExecutor;
import static java.util.concurrent.TimeUnit.SECONDS;

public class LocationGenerator {

    private static final int THREADS = 12;

    private static final ExecutorService executorService;

    private static final ScheduledExecutorService scheduledExecutorService;

    static {
        executorService = newFixedThreadPool(THREADS);
        scheduledExecutorService = newSingleThreadScheduledExecutor();
    }

    private static Predicate<Boolean> areTrue() {
        return new Predicate<Boolean>() {
            @Override
            public boolean apply(final Boolean input) {
                return input;
            }
        };
    }

    private static void displayRecord(final Omni omni) {
        System.out.printf("%s -- %s; %s; %s; %s%n",
                          omni.getTraits().getIpAddress(),
                          omni.getContinent().getName(),
                          omni.getCountry().getName(),
                          omni.getMostSpecificSubdivision().getName(),
                          omni.getCity().getName());
    }

    public static void main(final String... args) throws Exception {

        final ConcurrentMap<String, Boolean> locations = locations();

        scheduledExecutorService.scheduleAtFixedRate(new ShutdownWatcher(locations),
                                                     15L,
                                                     15L,
                                                     SECONDS);

        final DatabaseReader databaseReader = databaseReader();

        for (int i = 0; i < 256; i++) {
            for (int j = 0; j < 256; j++) {
                for (int k = 0; k < 256; k++) {

                    final InetAddress address = InetAddress.getByName(format("%d.%d.%d.0", i, j, k));

                    if (executorService.isShutdown() || executorService.isTerminated()) {
                        return;
                    }

                    executorService.execute(new AddressChecker(databaseReader, address, locations));
                }
            }
        }
    }

    private static ConcurrentMap<String, Boolean> locations() {

        final ConcurrentMap<String, Boolean> locations = new MapMaker().concurrencyLevel(THREADS).makeMap();

        for (Locations location : Locations.values()) {

            final String name = formatLocation(location.continent,
                                               location.country,
                                               location.subdivision,
                                               location.city);

            locations.put(name, !"".equals(location.address));
        }

        return locations;
    }

    private static String formatLocation(final String continent,
                                         final String country,
                                         final String subdivision,
                                         final String city) {

        return format("%s:%s:%s:%s",
                      continent == null || "".equals(continent) ? "XXX" : continent,
                      country == null || "".equals(country) ? "XXX" : country,
                      subdivision == null || "".equals(subdivision) ? "XXX" : subdivision,
                      city == null || "".equals(city) ? "XXX" : city);
    }

    private static DatabaseReader databaseReader() throws IOException {
        final File file = new ClassPathResource("/maxmind/GeoLite2-City.mmdb").getFile();

        return new DatabaseReader(file);
    }

    private static class AddressChecker implements Runnable {

        private final InetAddress address;

        private final DatabaseReader databaseReader;

        private final ConcurrentMap<String, Boolean> locations;

        public AddressChecker(final DatabaseReader databaseReader, final InetAddress address, final ConcurrentMap<String, Boolean> locations) {
            this.databaseReader = databaseReader;
            this.address = address;
            this.locations = locations;
        }

        @Override
        public void run() {

            final Omni omni;
            try {
                omni = databaseReader.omni(address);
            }
            catch (IOException | GeoIp2Exception e) {
                // Ignore
                return;
            }

            final String continentName = omni.getContinent().getName();
            final String countryName = omni.getCountry().getName();
            final String subdivisionName = omni.getMostSpecificSubdivision().getName();
            final String cityName = omni.getCity().getName();

            final String cityKey = formatLocation(continentName, countryName, subdivisionName, cityName);
            final String subdivisionKey = formatLocation(continentName, countryName, subdivisionName, null);
            final String countryKey = formatLocation(continentName, countryName, null, null);
            final String continentKey = formatLocation(continentName, null, null, null);
            final String globalKey = formatLocation(null, null, null, null);

            if (locations.containsKey(cityKey)) {
                if (!locations.get(cityKey)) {

                    displayRecord(omni);

                    locations.put(cityKey, TRUE);
                }
            }
            else if (locations.containsKey(subdivisionKey)) {
                if (!locations.get(subdivisionKey)) {

                    displayRecord(omni);

                    locations.put(subdivisionKey, TRUE);
                }
            }
            else if (locations.containsKey(countryKey)) {
                if (!locations.get(countryKey)) {

                    displayRecord(omni);

                    locations.put(countryKey, TRUE);
                }
            }
            else if (locations.containsKey(continentKey)) {
                if (!locations.get(continentKey)) {

                    displayRecord(omni);

                    locations.put(continentKey, TRUE);
                }
            }
            else if (locations.containsKey(globalKey)) {
                if (!locations.get(globalKey)) {

                    displayRecord(omni);

                    locations.put(globalKey, TRUE);
                }
            }
        }
    }

    private static class ShutdownWatcher implements Runnable {

        private final ConcurrentMap<String, Boolean> locations;

        public ShutdownWatcher(final ConcurrentMap<String, Boolean> locations) {
            this.locations = locations;
        }

        @Override
        public void run() {
            System.err.println(locations);
            if (all(locations.values(), areTrue())) {
                executorService.shutdownNow();
                scheduledExecutorService.shutdownNow();
            }
        }
    }
}
