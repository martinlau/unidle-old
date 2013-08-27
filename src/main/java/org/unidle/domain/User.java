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

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.List;

import static java.lang.String.format;

@Entity
@Table(name = "users")
public class User extends BaseEntity {

    @OneToMany(mappedBy = "user")
    private List<UserConnection> connections;

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

    public List<UserConnection> getConnections() {
        return connections;
    }

    public void setConnections(final List<UserConnection> connections) {
        this.connections = connections;
    }

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

    @Override
    public String toString() {
        return format("User(email='%s', firstName='%s', lastName='%s')",
                      email,
                      firstName,
                      lastName);
    }

}
