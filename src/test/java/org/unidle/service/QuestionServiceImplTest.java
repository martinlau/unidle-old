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
package org.unidle.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.ContextHierarchy;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;
import org.unidle.config.CacheConfiguration;
import org.unidle.config.DataConfiguration;
import org.unidle.config.I18NConfiguration;
import org.unidle.config.ServiceConfiguration;
import org.unidle.config.WroConfiguration;
import org.unidle.domain.Question;
import org.unidle.repository.AttachmentRepository;
import org.unidle.repository.QuestionRepository;

import static java.util.Arrays.asList;
import static org.fest.assertions.Assertions.assertThat;

@ContextHierarchy({@ContextConfiguration(classes = CacheConfiguration.class),
                   @ContextConfiguration(classes = DataConfiguration.class),
                   @ContextConfiguration(classes = I18NConfiguration.class),
                   @ContextConfiguration(classes = ServiceConfiguration.class),
                   @ContextConfiguration(classes = WroConfiguration.class)})
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
public class QuestionServiceImplTest {

    @Autowired
    private AttachmentRepository attachmentRepository;

    @Autowired
    private QuestionRepository questionRepository;

    @Autowired
    private QuestionService subject;

    @Test
    public void testCreateQuestion() throws Exception {
        final Question question = subject.createQuestion("this is a question",
                                                         "tag1, tag2 , ,, tag3,tag4",
                                                         asList(new MockMultipartFile("test.txt",
                                                                                      "test.txt",
                                                                                      "text/plain",
                                                                                      "this is a text file".getBytes())));

        assertThat(questionRepository.count()).isEqualTo(1L);
        assertThat(attachmentRepository.count()).isEqualTo(1L);
        assertThat(question.getQuestion()).isEqualTo("this is a question");
        assertThat(question.getTags()).containsOnly("tag1","tag2","tag3","tag4");
        assertThat(question.getAttachments()).hasSize(1);
        assertThat(question.getAttachments().get(0).getContent()).isEqualTo("this is a text file".getBytes());
        assertThat(question.getAttachments().get(0).getContentType()).isEqualTo("text/plain");
        assertThat(question.getAttachments().get(0).getTitle()).isEqualTo("test.txt");

    }

}
