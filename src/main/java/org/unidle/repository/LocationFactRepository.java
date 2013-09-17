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
package org.unidle.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;
import org.unidle.domain.LocationFact;

import java.util.UUID;

public interface LocationFactRepository extends JpaRepository<LocationFact, UUID>,
                                                JpaSpecificationExecutor<LocationFact> {

    @Query("SELECT lf FROM LocationFact lf WHERE lf.city = :city AND lf.subdivision = :subdivision AND lf.country = :country AND lf.continent = :continent")
    @Transactional(readOnly = true)
    LocationFact findOne(@Param("city") final String city,
                         @Param("subdivision") final String subdivision,
                         @Param("country") final String country,
                         @Param("continent") final String continent);

    @Query("SELECT lf FROM LocationFact lf WHERE lf.city = '' AND lf.subdivision = :subdivision AND lf.country = :country AND lf.continent = :continent")
    @Transactional(readOnly = true)
    LocationFact findOne(@Param("subdivision") final String subdivision,
                         @Param("country") final String country,
                         @Param("continent") final String continent);

    @Query("SELECT lf FROM LocationFact lf WHERE lf.city = '' AND lf.subdivision = '' AND lf.country = :country AND lf.continent = :continent")
    @Transactional(readOnly = true)
    LocationFact findOne(@Param("country") final String country,
                         @Param("continent") final String continent);

    @Query("SELECT lf FROM LocationFact lf WHERE lf.city = '' AND lf.subdivision = '' AND lf.country = '' AND lf.continent = :continent")
    @Transactional(readOnly = true)
    LocationFact findOne(@Param("continent") final String continent);

}
