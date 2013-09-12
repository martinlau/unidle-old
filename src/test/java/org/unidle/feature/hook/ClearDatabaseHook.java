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
package org.unidle.feature.hook;

import cucumber.api.Scenario;
import cucumber.api.java.After;
import cucumber.api.java.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.unidle.feature.config.DataSourceConfiguration;

import javax.sql.DataSource;
import java.util.List;
import java.util.Map;

import static java.lang.String.format;

@ContextConfiguration(classes = DataSourceConfiguration.class)
public class ClearDatabaseHook {

    private static final Logger LOGGER = LoggerFactory.getLogger(ClearDatabaseHook.class);

    @Autowired
    private DataSource dataSource;

    @After
    @Before
    public void clearDatabase(@SuppressWarnings("unused") final Scenario scenario) {
        final JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);

        // pruneTable(jdbcTemplate, "DELETE FROM location_facts");

        pruneTable(jdbcTemplate, "question_tags");
        pruneTable(jdbcTemplate, "questions");
        pruneTable(jdbcTemplate, "response_tags");
        pruneTable(jdbcTemplate, "responses");
        pruneTable(jdbcTemplate, "user_connections");
        pruneTable(jdbcTemplate, "users");
    }

    public void pruneTable(final JdbcTemplate jdbcTemplate,
                           final String table) {

        dumpData(jdbcTemplate, table);
        jdbcTemplate.update("DELETE FROM " + table);
        dumpData(jdbcTemplate, table);
    }

    public void dumpData(final JdbcTemplate jdbcTemplate, final String table) {
        final List<Map<String, Object>> results = jdbcTemplate.queryForList(format("SELECT * FROM %s", table));

        LOGGER.info("Table: {} - Contents: {}", table, results);
    }

}
