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

import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.ContextHierarchy;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.context.WebApplicationContext;
import org.unidle.config.CacheConfiguration;
import org.unidle.config.DataConfiguration;
import org.unidle.config.I18NConfiguration;
import org.unidle.config.MvcConfiguration;
import org.unidle.config.ServiceConfiguration;
import org.unidle.config.WroConfiguration;
import org.unidle.service.test.Locations;

import java.util.Locale;

import static java.util.Locale.ENGLISH;
import static org.hamcrest.Matchers.equalToIgnoringWhiteSpace;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;
import static org.unidle.service.test.Locations.NORTHERN_TERRITORY;
import static org.unidle.service.test.Locations.SYDNEY;
import static org.unidle.test.RequestProcessors.remoteAddr;

@ContextHierarchy({@ContextConfiguration(classes = CacheConfiguration.class),
                   @ContextConfiguration(classes = DataConfiguration.class),
                   @ContextConfiguration(classes = I18NConfiguration.class),
                   @ContextConfiguration(classes = ServiceConfiguration.class),
                   @ContextConfiguration(classes = WroConfiguration.class),
                   @ContextConfiguration(classes = MvcConfiguration.class)})
@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
public class HomeControllerTest {

    private MockMvc subject;

    @Autowired
    private WebApplicationContext webApplicationContext;

    @Before
    public void setUp() throws Exception {
        subject = webAppContextSetup(webApplicationContext).build();
    }

    @Test
    public void testHome() throws Exception {
        subject.perform(get("/").with(remoteAddr(SYDNEY.address))
                                .locale(ENGLISH))
               .andExpect(status().isOk())
               .andExpect(view().name(".home"))
               .andExpect(model().attribute("fact", equalToIgnoringWhiteSpace("Every day in Sydney 800,000 people spend 75 minutes on public transport.")))
               .andExpect(model().attribute("source", equalToIgnoringWhiteSpace("Bureau of Transport Statistics, Transport for NSW")))
               .andExpect(model().attribute("summary", equalToIgnoringWhiteSpace("That's over 100 years spent checking facebook, reading the paper or staring out of the window.")));
    }

    @Test
    public void testHomeWithLocationPrefix() throws Exception {
        subject.perform(get("/").with(remoteAddr(NORTHERN_TERRITORY.address))
                                .locale(ENGLISH))
               .andExpect(status().isOk())
               .andExpect(view().name(".home"))
               .andExpect(model().attribute("fact", equalToIgnoringWhiteSpace("Every day in the Northern Territory 1 person spends 1 minute doing stuff.")))
               .andExpect(model().attribute("source", equalToIgnoringWhiteSpace("Martin's Imagination")))
               .andExpect(model().attribute("summary", equalToIgnoringWhiteSpace("That's over 1 year spent checking facebook, reading the paper or staring out of the window.")));
    }

    @Test
    public void testHomeWithUnknownLocation() throws Exception {
        subject.perform(get("/").with(remoteAddr("127.0.0.1"))
                                .locale(ENGLISH))
               .andExpect(status().isOk())
               .andExpect(view().name(".home"))
               .andExpect(model().attribute("fact", equalToIgnoringWhiteSpace("Every day in the world 1 person spends 1 minute doing stuff.")))
               .andExpect(model().attribute("source", equalToIgnoringWhiteSpace("Martin's Imagination")))
               .andExpect(model().attribute("summary", equalToIgnoringWhiteSpace("That's over 1 year spent checking facebook, reading the paper or staring out of the window.")));
    }

}
