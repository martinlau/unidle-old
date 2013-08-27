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

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;
import org.joda.time.DateTime;
import org.springframework.data.domain.Auditable;

import javax.persistence.Cacheable;
import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MappedSuperclass;
import javax.persistence.Version;
import java.io.Serializable;
import java.util.UUID;

@Cacheable
@MappedSuperclass
public class BaseEntity implements Auditable<User, UUID>, Serializable {

    @JoinColumn(name = "created_by_user_uuid")
    @ManyToOne
    private User createdBy;

    @Column(name = "created_date",
            nullable = false)
    @Type(type = "org.jadira.usertype.dateandtime.joda.PersistentDateTime")
    private DateTime createdDate;

    @JoinColumn(name = "last_modified_by_user_uuid")
    @ManyToOne
    private User lastModifiedBy;

    @Column(name = "last_modified_date",
            nullable = false)
    @Type(type = "org.jadira.usertype.dateandtime.joda.PersistentDateTime")
    private DateTime lastModifiedDate;

    @Column(name = "revision")
    @Version
    private Integer revision;

    @Column(name = "uuid",
            nullable = false)
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2",
                      strategy = "uuid2")
    @Id
    @Type(type = "uuid-char")
    private UUID uuid;

    @Override
    public User getCreatedBy() {
        return createdBy;
    }

    @Override
    public void setCreatedBy(final User createdBy) {
        this.createdBy = createdBy;
    }

    @Override
    public DateTime getCreatedDate() {
        return createdDate;
    }

    @Override
    public void setCreatedDate(final DateTime createdDate) {
        this.createdDate = createdDate;
    }

    @Override
    public User getLastModifiedBy() {
        return lastModifiedBy;
    }

    @Override
    public void setLastModifiedBy(final User lastModifiedBy) {
        this.lastModifiedBy = lastModifiedBy;
    }

    @Override
    public DateTime getLastModifiedDate() {
        return lastModifiedDate;
    }

    @Override
    public void setLastModifiedDate(final DateTime lastModifiedDate) {
        this.lastModifiedDate = lastModifiedDate;
    }

    @Override
    public UUID getId() {
        return uuid;
    }

    @Override
    public boolean isNew() {
        return uuid == null;
    }

    public Integer getRevision() {
        return revision;
    }

    public void setRevision(final Integer revision) {
        this.revision = revision;
    }

    public UUID getUuid() {
        return uuid;
    }

    public void setUuid(final UUID uuid) {
        this.uuid = uuid;
    }

}
