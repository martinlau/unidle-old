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

public class QuestionTest {

    private Question subject;

    @Before
    public void setUp() throws Exception {
        subject = new Question();
    }

    @Test
    public void testGetAttachments() throws Exception {
        final List<Attachment> attachments = newArrayList(new Attachment(), new Attachment());
        setField(subject, "attachments", attachments);

        final List<Attachment> result = subject.getAttachments();

        assertThat(result).isEqualTo(attachments);
    }

    @Test
    public void testGetQuestion() throws Exception {
        setField(subject, "question", "question");

        final String result = subject.getQuestion();

        assertThat(result).isEqualTo("question");
    }

    @Test
    public void testGetResponses() throws Exception {
        final List<Response> responses = newArrayList(new Response(), new Response());
        setField(subject, "responses", responses);

        final List<Response> result = subject.getResponses();

        assertThat(result).isEqualTo(responses);
    }

    @Test
    public void testGetTags() throws Exception {
        final Set<String> tags = newHashSet("tag 1", "tag 2");
        setField(subject, "tags", tags);

        final Set<String> result = subject.getTags();

        assertThat(result).isEqualTo(tags);
    }

    @Test
    public void testSetAttachments() throws Exception {
        final List<Attachment> attachments = newArrayList(new Attachment(), new Attachment());

        subject.setAttachments(attachments);

        final Object result = getField(subject, "attachments");
        assertThat(result).isEqualTo(attachments);
    }

    @Test
    public void testSetQuestion() throws Exception {
        subject.setQuestion("question");

        final Object result = getField(subject, "question");
        assertThat(result).isEqualTo("question");
    }

    @Test
    public void testSetResponses() throws Exception {
        final List<Response> responses = newArrayList(new Response(), new Response());

        subject.setResponses(responses);

        final Object result = getField(subject, "responses");
        assertThat(result).isEqualTo(responses);
    }

    @Test
    public void testSetTags() throws Exception {
        final Set<String> tags = newHashSet("tag 1", "tag 2");

        subject.setTags(tags);

        final Object result = getField(subject, "tags");
        assertThat(result).isEqualTo(tags);
    }

}
