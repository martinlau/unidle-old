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
package org.unidle.analytics.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.social.connect.web.ConnectInterceptor;
import org.unidle.analytics.Analytics;
import org.unidle.analytics.AnalyticsStubImpl;
import org.unidle.service.UserService;
import org.unidle.social.AnalyticTrackingConnectInterceptor;

@Configuration
public class AnalyticsTestConfiguration {

    @Autowired
    private UserService userService;

    @Bean
    public ConnectInterceptor<?> analyticTrackingConnectInterceptor() {
        return new AnalyticTrackingConnectInterceptor<>(analytics(), userService);
    }

    @Bean
    public AnalyticsStubImpl analytics() {
        return new AnalyticsStubImpl();
    }

}
