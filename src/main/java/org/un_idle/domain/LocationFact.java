package org.un_idle.domain;

import org.springframework.data.jpa.domain.AbstractPersistable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Version;

import static java.lang.String.format;

@Entity
@Table(name = "LOCATION_FACT")
public class LocationFact extends AbstractPersistable<Long> {

    @Column(nullable = false)
    private String city;

    @Column(nullable = false)
    private String continent;

    @Column(nullable = false)
    private String country;

    @Column(nullable = false)
    private String fact;

    @Column(nullable = false)
    private String source;

    @Column(nullable = false)
    private String subdivision;

    @Column(nullable = false)
    private String summary;

    @Version
    private Integer version;

    public String getCity() {
        return city;
    }

    public void setCity(final String city) {
        this.city = city;
    }

    public String getContinent() {
        return continent;
    }

    public void setContinent(final String continent) {
        this.continent = continent;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(final String country) {
        this.country = country;
    }

    public String getFact() {
        return fact;
    }

    public void setFact(final String fact) {
        this.fact = fact;
    }

    public String getSource() {
        return source;
    }

    public void setSource(final String source) {
        this.source = source;
    }

    public String getSubdivision() {
        return subdivision;
    }

    public void setSubdivision(final String subdivision) {
        this.subdivision = subdivision;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(final String summary) {
        this.summary = summary;
    }

    public Integer getVersion() {
        return version;
    }

    public void setVersion(final Integer version) {
        this.version = version;
    }

    @Override
    public String toString() {
        return format("LocationFact(city='%s', continent='%s', country='%s', fact='%s', source='%s', subdivision='%s', summary='%s', version=%d)",
                      city,
                      continent,
                      country,
                      fact,
                      source,
                      subdivision,
                      summary,
                      version);
    }

}
