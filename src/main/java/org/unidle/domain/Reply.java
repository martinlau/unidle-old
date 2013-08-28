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
@Table(name = "replies")
public class Reply extends BaseEntity implements Serializable {

    @OneToMany(mappedBy = "parentReply")
    @OrderBy("createdDate ASC")
    private List<Reply> childReplies = new LinkedList<>();

    @JoinColumn(name = "parent_reply_uuid")
    @ManyToOne
    private Reply parentReply;

    @JoinColumn(name = "question_uuid",
                nullable = false)
    @ManyToOne
    private Question question;

    @Column(name = "reply",
            nullable = false)
    private String reply = "";

    @Column(name = "score",
            nullable = false)
    private Short score = 0;

    @Column(name = "tag")
    @ElementCollection
    @JoinTable(name = "reply_tags",
               joinColumns = @JoinColumn(name = "reply_uuid"))
    private Set<String> tags = new HashSet<>();

    public List<Reply> getChildReplies() {
        return childReplies;
    }

    public void setChildReplies(final List<Reply> childReplies) {
        this.childReplies = childReplies;
    }

    public Reply getParentReply() {
        return parentReply;
    }

    public void setParentReply(final Reply parentReply) {
        this.parentReply = parentReply;
    }

    public Question getQuestion() {
        return question;
    }

    public void setQuestion(final Question question) {
        this.question = question;
    }

    public String getReply() {
        return reply;
    }

    public void setReply(final String reply) {
        this.reply = reply;
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
        return format("Reply(childReplies=%s, parentReply=%s, question=%s, tags=%s, reply='%s', score=%s)",
                      childReplies, parentReply, question, tags, reply, score);
    }

}
