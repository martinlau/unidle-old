package org.unidle.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;
import org.unidle.domain.LocationFact;

import java.util.UUID;

public interface LocationFactRepository extends JpaRepository<LocationFact, UUID> {

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
