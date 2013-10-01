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

import com.google.common.base.Splitter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.unidle.domain.Attachment;
import org.unidle.domain.Question;
import org.unidle.repository.QuestionRepository;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

@Service
public class QuestionServiceImpl implements QuestionService {

    private static final Logger LOGGER = LoggerFactory.getLogger(QuestionServiceImpl.class);

    private final AttachmentService attachmentService;

    private final QuestionRepository questionRepository;

    @Autowired
    public QuestionServiceImpl(final AttachmentService attachmentService,
                               final QuestionRepository questionRepository) {

        this.attachmentService = attachmentService;
        this.questionRepository = questionRepository;
    }

    @Override
    @Transactional
    public Question createQuestion(final String question,
                                   final String tags,
                                   final List<? extends MultipartFile> attachments) {

        final Question questionEntity = new Question();

        questionEntity.setQuestion(question);
        for (String tag : Splitter.on(",")
                                  .trimResults()
                                  .omitEmptyStrings()
                                  .split(tags)) {

            questionEntity.getTags()
                          .add(tag);
        }

        if (attachments != null) {
            for (MultipartFile file : attachments) {

                final Attachment attachment;
                try {
                    attachment = attachmentService.createAttachment(file);
                }
                catch (IOException e) {
                    LOGGER.error("Error streaming file content for file: {}", file, e);
                    continue;
                }

                questionEntity.getAttachments()
                              .add(attachment);
            }
        }

        return questionRepository.save(questionEntity);
    }

    @Override
    @Transactional(readOnly = true)
    public boolean exists(final UUID uuid) {
        return questionRepository.exists(uuid);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Question> findAll(final Pageable pageable) {
        return questionRepository.findAll(pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public Question findOne(final UUID uuid) {
        return questionRepository.findOne(uuid);
    }

}
