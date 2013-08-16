package org.unidle.domain;

import org.springframework.data.jpa.domain.AbstractPersistable;
import org.unidle.geo.HasCity;
import org.unidle.geo.HasContinent;
import org.unidle.geo.HasCountry;
import org.unidle.geo.HasSubdivision;

import javax.persistence.AttributeOverride;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Enumerated;
import javax.persistence.Table;
import javax.persistence.Version;

import static java.lang.String.format;
import static javax.persistence.EnumType.STRING;
import static org.springframework.util.StringUtils.hasText;
import static org.unidle.domain.TimeUnit.UNKNOWN;

@AttributeOverride(column = @Column(name = "ID",
                                    nullable = false),
                   name = "id")
@Entity
@Table(name = "LOCATION_FACT")
public class LocationFact extends AbstractPersistable<Long> implements HasCity,
                                                                       HasSubdivision,
                                                                       HasCountry,
                                                                       HasContinent {

    @Column(name = "CITY",
            nullable = false)
    private String city = "";

    @Column(name = "CONTINENT",
            nullable = false)
    private String continent = "";

    @Column(name = "COUNTRY",
            nullable = false)
    private String country = "";

    @Column(name = "REVISION")
    @Version
    private Integer revision;

    @Column(name = "SOURCE",
            nullable = false)
    private String source = "";

    @Column(name = "SUBDIVISION",
            nullable = false)
    private String subdivision = "";

    @Column(name = "SUMMARY_DURATION",
            nullable = false)
    private Integer summaryDuration = 0;

    @Column(name = "SUMMARY_DURATION_TIME_UNIT",
            nullable = false)
    @Enumerated(STRING)
    private TimeUnit summaryDurationTimeUnit = UNKNOWN;

    @Column(name = "TASK_CODE",
            nullable = false)
    private String taskCode = "";

    @Column(name = "TASK_DURATION",
            nullable = false)
    private Integer taskDuration = 0;

    @Column(name = "TASK_DURATION_TIME_UNIT",
            nullable = false)
    @Enumerated(STRING)
    private TimeUnit taskDurationTimeUnit = UNKNOWN;

    @Column(name = "TASK_PEOPLE",
            nullable = false)
    private Integer taskPeople = 0;

    @Column(name = "TASK_TIME_UNIT",
            nullable = false)
    @Enumerated(STRING)
    private TimeUnit taskTimeUnit = UNKNOWN;

    @Override
    public String getCity() {
        return city;
    }

    public void setCity(final String city) {
        this.city = city;
    }

    @Override
    public String getContinent() {
        return continent;
    }

    public void setContinent(final String continent) {
        this.continent = continent;
    }

    @Override
    public String getCountry() {
        return country;
    }

    public void setCountry(final String country) {
        this.country = country;
    }

    public String getLocation() {
        return hasText(city) ? city
                             : hasText(subdivision) ? subdivision
                                                    : hasText(country) ? country
                                                                       : hasText(continent) ? continent
                                                                                            : null;
    }

    public Integer getRevision() {
        return revision;
    }

    public void setRevision(final Integer revision) {
        this.revision = revision;
    }

    public String getSource() {
        return source;
    }

    public void setSource(final String source) {
        this.source = source;
    }

    @Override
    public String getSubdivision() {
        return subdivision;
    }

    public void setSubdivision(final String subdivision) {
        this.subdivision = subdivision;
    }

    public Integer getSummaryDuration() {
        return summaryDuration;
    }

    public void setSummaryDuration(final Integer summaryDuration) {
        this.summaryDuration = summaryDuration;
    }

    public TimeUnit getSummaryDurationTimeUnit() {
        return summaryDurationTimeUnit;
    }

    public void setSummaryDurationTimeUnit(final TimeUnit summaryDurationTimeUnit) {
        this.summaryDurationTimeUnit = summaryDurationTimeUnit;
    }

    public String getTaskCode() {
        return taskCode;
    }

    public void setTaskCode(final String taskCode) {
        this.taskCode = taskCode;
    }

    public Integer getTaskDuration() {
        return taskDuration;
    }

    public void setTaskDuration(final Integer taskDuration) {
        this.taskDuration = taskDuration;
    }

    public TimeUnit getTaskDurationTimeUnit() {
        return taskDurationTimeUnit;
    }

    public void setTaskDurationTimeUnit(final TimeUnit taskDurationTimeUnit) {
        this.taskDurationTimeUnit = taskDurationTimeUnit;
    }

    public Integer getTaskPeople() {
        return taskPeople;
    }

    public void setTaskPeople(final Integer taskPeople) {
        this.taskPeople = taskPeople;
    }

    public TimeUnit getTaskTimeUnit() {
        return taskTimeUnit;
    }

    public void setTaskTimeUnit(final TimeUnit taskTimeUnit) {
        this.taskTimeUnit = taskTimeUnit;
    }

    @Override
    public String toString() {
        return format(
                "LocationFact(city='%s', continent='%s', country='%s', revision=%d, source='%s', subdivision='%s', summaryDuration=%d, summaryDurationTimeUnit=%s, taskCode='%s', taskDuration=%d, taskDurationTimeUnit=%s, taskPeople=%d, taskTimeUnit=%s)",
                city,
                continent,
                country,
                revision,
                source,
                subdivision,
                summaryDuration,
                summaryDurationTimeUnit,
                taskCode,
                taskDuration,
                taskDurationTimeUnit,
                taskPeople,
                taskTimeUnit);
    }

}
