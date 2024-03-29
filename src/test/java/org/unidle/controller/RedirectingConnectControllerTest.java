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

import org.junit.Before;
import org.junit.Test;
import org.springframework.web.servlet.view.RedirectView;

import static org.fest.assertions.Assertions.assertThat;

public class RedirectingConnectControllerTest {

    private RedirectingConnectController subject;

    @Before
    public void setUp() throws Exception {
        subject = new RedirectingConnectController(null, null);
    }

    @Test
    public void testConnectedView() throws Exception {
        final String result = subject.connectedView("provider id");

        assertThat(result).isEqualTo("redirect:/questions");
    }

    @Test
    public void testConnectionStatusRedirect() throws Exception {
        final RedirectView result = subject.connectionStatusRedirect("provider id", null);

        assertThat(result.getUrl()).isEqualTo("/questions");
    }

}
