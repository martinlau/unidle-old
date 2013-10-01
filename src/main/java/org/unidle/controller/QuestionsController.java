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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.unidle.domain.Question;
import org.unidle.repository.QuestionRepository;
import org.unidle.service.QuestionService;

import static org.springframework.data.domain.Sort.Direction.DESC;

@Controller
public class QuestionsController {

    private final QuestionService questionService;

    @Autowired
    public QuestionsController(final QuestionService questionService) {
        this.questionService = questionService;
    }

    @RequestMapping("/questions")
    public String questions() {
        return ".questions";
    }

    @ModelAttribute("questions")
    public Page<Question> questions(@PageableDefault(size = 5, sort = "createdDate", direction = DESC) final Pageable pageable) {
        return questionService.findAll(pageable);
    }

}
