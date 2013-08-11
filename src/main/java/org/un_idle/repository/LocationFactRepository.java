package org.un_idle.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;
import org.un_idle.domain.LocationFact;

public interface LocationFactRepository extends JpaRepository<LocationFact, Long> {

    @Query("SELECT lf " +
           "FROM LocationFact lf " +
           "WHERE lf.city = :city " +
           "AND lf.subdivision = :subdivision " +
           "AND lf.country = :country " +
           "AND lf.continent = :continent")
    @Transactional(readOnly = true)
    LocationFact findOne(@Param("city")
                         String city,
                         @Param("subdivision")
                         String subdivision,
                         @Param("country")
                         String country,
                         @Param("continent")
                         String continent);

    @Query("SELECT lf " +
           "FROM LocationFact lf " +
           "WHERE lf.subdivision = :subdivision " +
           "AND lf.country = :country " +
           "AND lf.continent = :continent")
    @Transactional(readOnly = true)
    LocationFact findOne(@Param("subdivision")
                         String subdivision,
                         @Param("country")
                         String country,
                         @Param("continent")
                         String continent);

    @Query("SELECT lf " +
           "FROM LocationFact lf " +
           "WHERE lf.country = :country " +
           "AND lf.continent = :continent")
    @Transactional(readOnly = true)
    LocationFact findOne(@Param("country")
                         String country,
                         @Param("continent")
                         String continent);

    @Query("SELECT lf " +
           "FROM LocationFact lf " +
           "WHERE lf.continent = :continent")
    @Transactional(readOnly = true)
    LocationFact findOne(@Param("continent")
                         String continent);

}