package org.unidle.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@ComponentScan("org.unidle.service")
@Configuration
@EnableTransactionManagement
public class ServiceConfiguration {

    // Nothing to see here

}
