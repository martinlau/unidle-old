package org.unidle.feature.page;

import org.openqa.selenium.By;
import org.openqa.selenium.Cookie;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class GenericPage {

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

    public void navigateTo(final String page) {
        driver.navigate().to(page);
    }

}
