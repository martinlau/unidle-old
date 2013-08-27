package org.unidle.feature.page;

import org.openqa.selenium.By;
import org.openqa.selenium.Cookie;
import org.openqa.selenium.HasCapabilities;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static org.openqa.selenium.remote.CapabilityType.SUPPORTS_JAVASCRIPT;

public class GenericPage {

    private static final Logger LOGGER = LoggerFactory.getLogger(GenericPage.class);

    private final WebDriver driver;

    @FindBy(tagName = "body")
    private WebElement bodyElement;

    public GenericPage(final WebDriver driver) {
        this.driver = driver;
    }

    public void addCookie(final String name,
                          final String value) {
        driver.manage().addCookie(new Cookie(name, value));
    }

    public String getText(final String element) {
        return bodyElement.findElement(By.id(element)).getText();
    }

    public String getText() {
        return bodyElement.getText();
    }

    public String getTitle() {
        return driver.getTitle();
    }

    public boolean isAcceptable() {
        if (driver instanceof HasCapabilities) {
            return ((HasCapabilities) driver).getCapabilities().is(SUPPORTS_JAVASCRIPT);
        }
        LOGGER.warn("WebDriver '{}' doesn't support capabilities - can't tell if we're alive - hold on and hope for the best", driver);
        return true;
    }

    public void navigateTo(final String page) {
        driver.navigate().to(page);
    }

}
