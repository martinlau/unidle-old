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
package org.unidle.analytics;

public enum AnalyticsEvent {

    ADD_A_TAG("Add a Tag"),

    ASK_A_QUESTION("Ask a Question"),

    ATTACH_A_FILE("Attach a File"),

    CONNECT("Connect an Account"),

    SIGN_UP("Sign Up");

    public static final String PROPERTY_ATTACHMENT_UUID = "uuid";

    public static final String PROPERTY_DISPLAY_NAME = "displayName";

    public static final String PROPERTY_PROVIDER_ID = "providerId";

    public static final String PROPERTY_PROVIDER_USER_ID = "providerUserId";

    public static final String PROPERTY_QUESTION = "question";

    public static final String PROPERTY_QUESTION_UUID = "uuid";

    public static final String PROPERTY_TAG = "tag";

    public static final String PROPERTY_TITLE = "title";

    public static final String TRAIT_CREATED = "created";

    public static final String TRAIT_EMAIL = "email";

    public static final String TRAIT_FIRST_NAME = "firstName";

    public static final String TRAIT_LAST_NAME = "lastName";

    private final String name;

    private AnalyticsEvent(final String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
