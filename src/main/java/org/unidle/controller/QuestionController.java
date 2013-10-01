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
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.unidle.repository.QuestionRepository;
import org.unidle.service.QuestionService;

import java.util.UUID;

import static org.springframework.http.HttpStatus.NOT_FOUND;

@Controller
public class QuestionController {

    private final QuestionService questionService;

    @Autowired
    public QuestionController(final QuestionService questionService) {
        this.questionService = questionService;
    }

    @RequestMapping("/question/{uuid:.*}")
    public String question(@PathVariable("uuid") final UUID uuid,
                           final ModelMap modelMap) {

        if (!questionService.exists(uuid)) {
            throw new NoSuchQuestionException();
        }

        modelMap.put("question", questionService.findOne(uuid));

        return ".question";
    }

    @ResponseStatus(NOT_FOUND)
    public static class NoSuchQuestionException extends RuntimeException {
        // Nothing to see here
    }

}
