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

import org.joda.time.DateTime;
import org.junit.Before;
import org.junit.Test;

import java.util.UUID;

import static org.fest.assertions.Assertions.assertThat;
import static org.springframework.test.util.ReflectionTestUtils.getField;
import static org.springframework.test.util.ReflectionTestUtils.setField;

public class BaseEntityTest {

    private BaseEntity subject;

    @Before
    public void setUp() throws Exception {
        subject = new BaseEntity();
    }

    @Test
    public void testGetCreatedBy() throws Exception {
        final User user = new User();
        setField(subject, "createdBy", user);

        final User result = subject.getCreatedBy();

        assertThat(result).isEqualTo(user);
    }

    @Test
    public void testGetCreatedDate() throws Exception {
        final DateTime dateTime = DateTime.now();
        setField(subject, "createdDate", dateTime);

        final DateTime result = subject.getCreatedDate();

        assertThat(result).isEqualTo(dateTime);
    }

    @Test
    public void testGetId() throws Exception {
        final UUID uuid = UUID.randomUUID();
        subject.setUuid(uuid);

        final UUID result = subject.getId();

        assertThat(result).isEqualTo(uuid);
    }

    @Test
    public void testGetLastModifiedBy() throws Exception {
        final User user = new User();
        setField(subject, "lastModifiedBy", user);

        final User result = subject.getLastModifiedBy();

        assertThat(result).isEqualTo(user);
    }

    @Test
    public void testGetLastModifiedDate() throws Exception {
        final DateTime dateTime = DateTime.now();
        setField(subject, "lastModifiedDate", dateTime);

        final DateTime result = subject.getLastModifiedDate();

        assertThat(result).isEqualTo(dateTime);
    }

    @Test
    public void testGetRevision() throws Exception {
        setField(subject, "revision", 1234);

        final Integer result = subject.getRevision();

        assertThat(result).isEqualTo(1234);
    }

    @Test
    public void testGetUuid() throws Exception {
        final UUID uuid = UUID.randomUUID();
        setField(subject, "uuid", uuid);

        final UUID result = subject.getUuid();

        assertThat(result).isEqualTo(uuid);
    }

    @Test
    public void testIsNewWithUuid() throws Exception {
        subject.setUuid(UUID.randomUUID());

        final boolean result = subject.isNew();

        assertThat(result).isFalse();
    }

    @Test
    public void testIsNewWithoutUuid() throws Exception {
        final boolean result = subject.isNew();

        assertThat(result).isTrue();
    }

    @Test
    public void testSetCreatedBy() throws Exception {
        final User user = new User();

        subject.setCreatedBy(user);

        final Object result = getField(subject, "createdBy");
        assertThat(result).isEqualTo(user);
    }

    @Test
    public void testSetCreatedDate() throws Exception {
        final DateTime dateTime = DateTime.now();

        subject.setCreatedDate(dateTime);

        final Object result = getField(subject, "createdDate");
        assertThat(result).isEqualTo(dateTime);
    }

    @Test
    public void testSetLastModifiedBy() throws Exception {
        final User user = new User();

        subject.setLastModifiedBy(user);

        final Object result = getField(subject, "lastModifiedBy");
        assertThat(result).isEqualTo(user);
    }

    @Test
    public void testSetLastModifiedDate() throws Exception {
        final DateTime dateTime = DateTime.now();

        subject.setLastModifiedDate(dateTime);

        final Object result = getField(subject, "lastModifiedDate");
        assertThat(result).isEqualTo(dateTime);
    }

    @Test
    public void testSetRevision() throws Exception {
        subject.setRevision(1234);

        assertThat(getField(subject, "revision")).isEqualTo(1234);
    }

    @Test
    public void testSetUuid() throws Exception {
        final UUID uuid = UUID.randomUUID();

        subject.setUuid(uuid);

        final Object result = getField(subject, "uuid");
        assertThat(result).isEqualTo(uuid);
    }

}
