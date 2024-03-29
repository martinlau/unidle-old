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

import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import static java.lang.String.format;

@Entity
@Table(name = "questions")
public class Question extends BaseEntity implements Serializable {

    @JoinTable(name = "question_attachments",
               joinColumns = @JoinColumn(name = "question_uuid"),
               inverseJoinColumns = @JoinColumn(name = "attachment_uuid"))
    @OneToMany
    @OrderBy("createdDate ASC")
    private List<Attachment> attachments = new LinkedList<>();

    @Column(name = "question",
            nullable = false)
    private String question = "";

    @OneToMany(mappedBy = "question")
    @OrderBy("createdDate ASC")
    private List<Response> responses = new LinkedList<>();

    @Column(name = "tag")
    @ElementCollection
    @JoinTable(name = "question_tags",
               joinColumns = @JoinColumn(name = "question_uuid"))
    private Set<String> tags = new HashSet<>();

    public List<Attachment> getAttachments() {
        return attachments;
    }

    public void setAttachments(final List<Attachment> attachments) {
        this.attachments = attachments;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(final String question) {
        this.question = question;
    }

    public List<Response> getResponses() {
        return responses;
    }

    public void setResponses(final List<Response> responses) {
        this.responses = responses;
    }

    public Set<String> getTags() {
        return tags;
    }

    public void setTags(final Set<String> tags) {
        this.tags = tags;
    }

    @Override
    public String toString() {
        return format("Question(question='%s', responses=%s, attachments=%s, tags=%s)",
                      question, responses, attachments, tags);
    }

}
