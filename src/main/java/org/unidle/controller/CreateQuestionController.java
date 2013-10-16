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
package org.unidle.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.unidle.analytics.Analytics;
import org.unidle.domain.Attachment;
import org.unidle.domain.Question;
import org.unidle.domain.User;
import org.unidle.form.QuestionForm;
import org.unidle.service.QuestionService;

import javax.validation.Valid;

import static java.lang.String.format;
import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;
import static org.unidle.analytics.AnalyticsEvent.ADD_A_TAG;
import static org.unidle.analytics.AnalyticsEvent.ASK_A_QUESTION;
import static org.unidle.analytics.AnalyticsEvent.ATTACH_A_FILE;
import static org.unidle.analytics.AnalyticsEvent.PROPERTY_ATTACHMENT_UUID;
import static org.unidle.analytics.AnalyticsEvent.PROPERTY_QUESTION;
import static org.unidle.analytics.AnalyticsEvent.PROPERTY_QUESTION_UUID;
import static org.unidle.analytics.AnalyticsEvent.PROPERTY_TAG;
import static org.unidle.analytics.AnalyticsEvent.PROPERTY_TITLE;

@Controller
public class CreateQuestionController {

    private final Analytics analytics;

    private final QuestionService questionService;


    @Autowired
    public CreateQuestionController(final Analytics analytics,
                                    final QuestionService questionService) {
        this.analytics = analytics;
        this.questionService = questionService;
    }

    @RequestMapping(value = "/question/create",
                    method = GET)
    public String create(final User user) {

        // TODO Put the following snippet somewhere reusable or use spring-security for protection
        if (user == null) {
            return "redirect:/signin";
        }
        return ".create-question";
    }

    @RequestMapping(value = "/question/create",
                    method = POST)
    public String create(@Valid final QuestionForm questionForm,
                         final Errors errors,
                         final User user) {

        // TODO Put the following snippet somewhere reusable or use spring-security for protection
        if (user == null) {
            return "redirect:/signin";
        }

        if (errors.hasErrors()) {
            return ".create-question";
        }

        final Question question = questionService.createQuestion(questionForm.getQuestion(),
                                                                 questionForm.getTags(),
                                                                 questionForm.getAttachments());

        // TODO Move this to an AOP interceptor around createQuestion() above
        analytics.track(user.getUuid(),
                        ASK_A_QUESTION,
                        PROPERTY_QUESTION_UUID, question.getUuid(),
                        PROPERTY_QUESTION, question.getQuestion());

        for (String tag : question.getTags()) {
            analytics.track(user.getUuid(),
                            ADD_A_TAG,
                            PROPERTY_QUESTION_UUID, question.getUuid(),
                            PROPERTY_TAG, tag);
        }

        for (Attachment attachment : question.getAttachments()) {
            analytics.track(user.getUuid(),
                            ATTACH_A_FILE,
                            PROPERTY_QUESTION_UUID, question.getUuid(),
                            PROPERTY_ATTACHMENT_UUID, attachment.getUuid(),
                            PROPERTY_TITLE, attachment.getTitle());
        }

        return format("redirect:/question/%s", question.getUuid());
    }

    @ModelAttribute("questionForm")
    public QuestionForm questionForm() {
        return new QuestionForm();
    }

}
