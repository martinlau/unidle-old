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
package org.unidle.domain;

import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Enumerated;
import javax.persistence.Table;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

import static java.lang.String.format;
import static javax.persistence.EnumType.STRING;
import static org.springframework.util.StringUtils.hasText;
import static org.unidle.domain.LocationFact.TimeUnit.UNKNOWN;

@Entity
@Table(name = "location_facts")
public class LocationFact extends BaseEntity implements Serializable {

    @Column(name = "city",
            nullable = false)
    private String city = "";

    @Column(name = "continent",
            nullable = false)
    private String continent = "";

    @Column(name = "country",
            nullable = false)
    private String country = "";

    @Column(name = "location_prefix",
            nullable = false)
    private String locationPrefix = "";

    @Column(name = "source",
            nullable = false)
    @NotEmpty
    private String source = "";

    @Column(name = "subdivision",
            nullable = false)
    private String subdivision = "";

    @Column(name = "summary_duration",
            nullable = false)
    @Min(0)
    @NotNull
    private Integer summaryDuration = 0;

    @Column(name = "summary_duration_time_unit",
            nullable = false)
    @Enumerated(STRING)
    @NotNull
    private TimeUnit summaryDurationTimeUnit = UNKNOWN;

    @Column(name = "task_code",
            nullable = false)
    @NotEmpty
    private String taskCode = "";

    @Column(name = "task_duration",
            nullable = false)
    @Min(0)
    @NotNull
    private Integer taskDuration = 0;

    @Column(name = "task_duration_time_unit",
            nullable = false)
    @Enumerated(STRING)
    @NotNull
    private TimeUnit taskDurationTimeUnit = UNKNOWN;

    @Column(name = "task_people",
            nullable = false)
    @Min(0)
    @NotNull
    private Integer taskPeople = 0;

    @Column(name = "task_time_unit",
            nullable = false)
    @Enumerated(STRING)
    @NotNull
    private TimeUnit taskTimeUnit = UNKNOWN;

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

    public String getLocation() {
        return hasText(city) ? city
                             : hasText(subdivision) ? subdivision
                                                    : hasText(country) ? country
                                                                       : hasText(continent) ? continent
                                                                                            : null;
    }

    public String getLocationPrefix() {
        return locationPrefix;
    }

    public void setLocationPrefix(final String locationPrefix) {
        this.locationPrefix = locationPrefix;
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
                "LocationFact(city='%s', continent='%s', country='%s', source='%s', locationPrefix='%s', subdivision='%s', summaryDuration=%d, summaryDurationTimeUnit=%s, taskCode='%s', taskDuration=%d, taskDurationTimeUnit=%s, taskPeople=%d, taskTimeUnit=%s)",
                city,
                continent,
                country,
                locationPrefix,
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

    public static enum TimeUnit {

        SECOND("common.period.second"),

        MINUTE("common.period.minute"),

        HOUR("common.period.hour"),

        DAY("common.period.day"),

        WEEK("common.period.week"),

        MONTH("common.period.month"),

        YEAR("common.period.year"),

        UNKNOWN("common.period.unknown");

        private final String messageKey;

        TimeUnit(final String messageKey) {
            this.messageKey = messageKey;
        }

        public String getMessageKey() {
            return messageKey;
        }

    }
}
