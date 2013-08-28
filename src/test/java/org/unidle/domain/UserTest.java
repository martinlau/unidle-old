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

import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static com.google.common.collect.Lists.newArrayList;
import static org.fest.assertions.Assertions.assertThat;
import static org.springframework.test.util.ReflectionTestUtils.getField;
import static org.springframework.test.util.ReflectionTestUtils.setField;

public class UserTest {

    private User subject;

    @Before
    public void setUp() throws Exception {
        subject = new User();
    }

    @Test
    public void testGetConnections() throws Exception {
        final List<UserConnection> connections = newArrayList(new UserConnection(), new UserConnection());
        setField(subject, "connections", connections);

        final List<UserConnection> result = subject.getConnections();

        assertThat(result).isEqualTo(connections);
    }

    @Test
    public void testGetEmail() throws Exception {
        setField(subject, "email", "email@example.com");

        final String result = subject.getEmail();

        assertThat(result).isEqualTo("email@example.com");
    }

    @Test
    public void testGetFirstName() throws Exception {
        setField(subject, "firstName", "first name");

        final String result = subject.getFirstName();

        assertThat(result).isEqualTo("first name");
    }

    @Test
    public void testGetLastName() throws Exception {
        setField(subject, "lastName", "last name");

        final String result = subject.getLastName();

        assertThat(result).isEqualTo("last name");
    }

    @Test
    public void testSetConnections() throws Exception {
        final List<UserConnection> connections = newArrayList(new UserConnection(), new UserConnection());

        subject.setConnections(connections);

        final Object result = getField(subject, "connections");
        assertThat(result).isEqualTo(connections);
    }

    @Test
    public void testSetEmail() throws Exception {
        subject.setEmail("email@example.com");

        final Object result = getField(subject, "email");
        assertThat(result).isEqualTo("email@example.com");
    }

    @Test
    public void testSetFirstName() throws Exception {
        subject.setFirstName("first name");

        final Object result = getField(subject, "firstName");
        assertThat(result).isEqualTo("first name");
    }

    @Test
    public void testSetLastName() throws Exception {
        subject.setLastName("last name");

        final Object result = getField(subject, "lastName");
        assertThat(result).isEqualTo("last name");
    }

}
