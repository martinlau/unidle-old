package org.unidle.controller;

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
import org.unidle.config.MvcConfiguration;
import org.unidle.config.RootContextConfiguration;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;

@ContextHierarchy({@ContextConfiguration(classes = RootContextConfiguration.class),
                   @ContextConfiguration(classes = MvcConfiguration.class)})
@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
public class OpenControllerTest {

    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext webApplicationContext;

    @Before
    public void setUp() throws Exception {
        mockMvc = webAppContextSetup(webApplicationContext).build();
    }

    @Test
    public void testOpen() throws Exception {
        mockMvc.perform(get("/open"))
               .andExpect(status().isOk())
               .andExpect(view().name(".static.open"));
    }

}
