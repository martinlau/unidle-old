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
import java.util.Set;

import static com.google.common.collect.Lists.newArrayList;
import static com.google.common.collect.Sets.newHashSet;
import static org.fest.assertions.Assertions.assertThat;
import static org.springframework.test.util.ReflectionTestUtils.getField;
import static org.springframework.test.util.ReflectionTestUtils.setField;

public class AttachmentTest {

    private Attachment subject;

    @Before
    public void setUp() throws Exception {
        subject = new Attachment();
    }

    @Test
    public void testGetContent() throws Exception {
        final byte[] content = "content".getBytes();
        setField(subject, "content", content);

        final byte[] result = subject.getContent();

        assertThat(result).isEqualTo(content);
    }

    @Test
    public void testGetContentType() throws Exception {
        setField(subject, "contentType", "content type");

        final String result = subject.getContentType();

        assertThat(result).isEqualTo("content type");
    }

    @Test
    public void testGetTitle() throws Exception {
        setField(subject, "title", "title");

        final String result = subject.getTitle();

        assertThat(result).isEqualTo("title");
    }

    @Test
    public void testSetContent() throws Exception {
        final byte[] content = "content".getBytes();

        subject.setContent(content);

        final Object result = getField(subject, "content");
        assertThat(result).isEqualTo(content);
    }

    @Test
    public void testSetContentType() throws Exception {
        subject.setContentType("content type");

        final Object result = getField(subject, "contentType");
        assertThat(result).isEqualTo("content type");
    }

    @Test
    public void testSetTitle() throws Exception {
        subject.setTitle("title");

        final Object result = getField(subject, "title");
        assertThat(result).isEqualTo("title");
    }

}
