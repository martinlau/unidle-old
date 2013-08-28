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
import javax.persistence.ManyToOne;
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
@Table(name = "responses")
public class Response extends BaseEntity implements Serializable {

    @OneToMany(mappedBy = "parentResponse")
    @OrderBy("createdDate ASC")
    private List<Response> childReplies = new LinkedList<>();

    @Column(name = "comments",
            nullable = false)
    private String comments = "";

    @JoinColumn(name = "parent_response_uuid")
    @ManyToOne
    private Response parentResponse;

    @JoinColumn(name = "question_uuid",
                nullable = false)
    @ManyToOne
    private Question question;

    @Column(name = "score",
            nullable = false)
    private Short score = 0;

    @Column(name = "tag")
    @ElementCollection
    @JoinTable(name = "response_tags",
               joinColumns = @JoinColumn(name = "response_uuid"))
    private Set<String> tags = new HashSet<>();

    public List<Response> getChildReplies() {
        return childReplies;
    }

    public void setChildReplies(final List<Response> childReplies) {
        this.childReplies = childReplies;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(final String comments) {
        this.comments = comments;
    }

    public Response getParentResponse() {
        return parentResponse;
    }

    public void setParentResponse(final Response parentResponse) {
        this.parentResponse = parentResponse;
    }

    public Question getQuestion() {
        return question;
    }

    public void setQuestion(final Question question) {
        this.question = question;
    }

    public Short getScore() {
        return score;
    }

    public void setScore(final Short score) {
        this.score = score;
    }

    public Set<String> getTags() {
        return tags;
    }

    public void setTags(final Set<String> tags) {
        this.tags = tags;
    }

    @Override
    public String toString() {
        return format("Response(childReplies=%s, parentResponse=%s, question=%s, tags=%s, comments='%s', score=%s)",
                      childReplies, parentResponse, question, tags, comments, score);
    }

}
