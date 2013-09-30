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
package org.unidle.form;

import com.google.common.collect.Lists;
import org.junit.Before;
import org.junit.Test;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

import static org.fest.assertions.Assertions.assertThat;
import static org.springframework.test.util.ReflectionTestUtils.getField;
import static org.springframework.test.util.ReflectionTestUtils.setField;

public class QuestionFormTest {

    private QuestionForm subject;

    @Before
    public void setUp() throws Exception {
        subject = new QuestionForm();
    }

    @Test
    public void testGetAttachments() throws Exception {
        final List<? extends MultipartFile> attachments = Lists.newArrayList(new MockMultipartFile("empty", new byte[0]),
                                                                             new MockMultipartFile("empty", new byte[0]));
        setField(subject, "attachments", attachments);

        final List<? extends MultipartFile> result = subject.getAttachments();

        assertThat(result).isEqualTo(attachments);
    }

    @Test
    public void testGetQuestion() throws Exception {
        setField(subject, "question", "question");

        final String result = subject.getQuestion();

        assertThat(result).isEqualTo("question");
    }

    @Test
    public void testGetTags() throws Exception {
        setField(subject, "tags", "tags");

        final String result = subject.getTags();

        assertThat(result).isEqualTo("tags");
    }

    @Test
    public void testSetAttachments() throws Exception {
        final List<? extends MultipartFile> attachments = Lists.newArrayList(new MockMultipartFile("empty", new byte[0]),
                                                                             new MockMultipartFile("empty", new byte[0]));

        subject.setAttachments(attachments);

        final Object result = getField(subject, "attachments");
        assertThat(result).isEqualTo(attachments);
    }

    @Test
    public void testSetQuestion() throws Exception {
        subject.setQuestion("question");

        Object result = getField(subject, "question");
        assertThat(result).isEqualTo("question");
    }

    @Test
    public void testSetTags() throws Exception {
        subject.setTags("tags");

        Object result = getField(subject, "tags");
        assertThat(result).isEqualTo("tags");
    }
}
