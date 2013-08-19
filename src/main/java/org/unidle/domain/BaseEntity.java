package org.unidle.domain;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;
import org.springframework.data.domain.Persistable;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.Version;
import java.io.Serializable;
import java.util.UUID;

@MappedSuperclass
public class BaseEntity implements Persistable<UUID>, Serializable {

    @Column(name = "revision")
    @Version
    private Integer revision;

    @Column(name = "uuid",
            nullable = false)
    @GenericGenerator(name = "uuid2",
                      strategy = "uuid2")
    @GeneratedValue(generator = "uuid2")
    @Id
    @Type(type = "uuid-char")
    private UUID uuid;

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
