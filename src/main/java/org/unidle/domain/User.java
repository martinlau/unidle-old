package org.unidle.domain;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.data.jpa.domain.AbstractPersistable;

import javax.persistence.AttributeOverride;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Version;

import java.util.List;
import java.util.Map;

import static java.lang.String.format;

@AttributeOverride(column = @Column(name = "id", nullable = false),
                   name = "id")
@Entity
@Table(name = "users")
public class User extends AbstractPersistable<Long> {

    @Column(name = "email",
            nullable = false)
    @Email
    @NotEmpty
    private String email;

    @Column(name = "first_name",
            nullable = false)
    @NotEmpty
    private String firstName;

    @Column(name = "last_name",
            nullable = false)
    @NotEmpty
    private String lastName;

    @Column(name = "revision")
    @Version
    private Integer revision;

    @OneToMany(mappedBy = "user")
    private List<UserConnection> connections;

    public String getEmail() {
        return email;
    }

    public void setEmail(final String email) {
        this.email = email;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(final String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(final String lastName) {
        this.lastName = lastName;
    }

    public Integer getRevision() {
        return revision;
    }

    public void setRevision(final Integer revision) {
        this.revision = revision;
    }

    @Override
    public String toString() {
        return format("User(email='%s', firstName='%s', lastName='%s', revision=%d)",
                      email,
                      firstName,
                      lastName,
                      revision);
    }

}
