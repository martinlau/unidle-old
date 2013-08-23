package org.unidle.feature;

import cucumber.api.junit.Cucumber;
import cucumber.api.junit.Cucumber.Options;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@Options(format = {"html:target/cucumber",
                   "progress"},
         glue = {"org.unidle.feature.config",
                 "org.unidle.feature.hook",
                 "org.unidle.feature.step"})
public class CucumberIT {

    // Nothing to see here

}
