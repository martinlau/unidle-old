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
import org.unidle.domain.Question;
import org.unidle.domain.User;
import org.unidle.form.QuestionForm;
import org.unidle.service.QuestionService;

import javax.validation.Valid;

import static java.lang.String.format;
import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

@Controller
public class QuestionCreateController {

    @Autowired
    private QuestionService questionService;

    @RequestMapping(value = "/question/create",
                    method = GET)
    public String create(final User user) {

        // TODO Put the following snippet somewhere reusable or use spring-security for protection
        if (user == null) {
            return "redirect:/signin";
        }
        return ".question-create";
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
            return ".question-create";
        }

        final Question question = questionService.createQuestion(questionForm.getQuestion(),
                                                                 questionForm.getTags(),
                                                                 questionForm.getAttachments());

        return format("redirect:/question/%s", question.getUuid());
    }

    @ModelAttribute("questionForm")
    public QuestionForm questionForm() {
        return new QuestionForm();
    }

}
