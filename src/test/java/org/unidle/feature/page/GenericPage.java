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
package org.unidle.feature.page;

import com.google.common.base.Predicate;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableMap.Builder;
import org.openqa.selenium.By;
import org.openqa.selenium.Cookie;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.data.util.ReflectionUtils.AnnotationFieldFilter;
import org.springframework.util.ReflectionUtils;
import org.springframework.util.ReflectionUtils.FieldCallback;
import org.springframework.util.ResourceUtils;

import javax.annotation.PostConstruct;
import java.io.File;
import java.io.FileNotFoundException;
import java.lang.reflect.Field;
import java.net.URI;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import static java.lang.String.format;
import static java.util.concurrent.TimeUnit.SECONDS;
import static org.apache.commons.lang3.StringUtils.join;
import static org.apache.commons.lang3.StringUtils.lowerCase;
import static org.apache.commons.lang3.StringUtils.splitByCharacterTypeCamelCase;
import static org.junit.Assert.fail;

public class GenericPage {

    private final String name;

    private final String path;

    private final WebDriver webDriver;

    @FindBy(tagName = "body")
    private WebElement bodyElement;

    private Map<String, List<WebElement>> knownElements;

    public GenericPage(final WebDriver webDriver) {
        this(webDriver, "Generic Page", null);
    }

    public GenericPage(final WebDriver webDriver,
                       final String name,
                       final String path) {
        this.webDriver = webDriver;
        this.name = name;
        this.path = path;
    }

    public void addCookie(final String name,
                          final String value) {
        webDriver.manage().addCookie(new Cookie(name, value));
    }

    public void browseTo() {
        if (path == null) {
            throw new UnsupportedOperationException();
        }
        browseTo(path);
    }

    public void browseTo(final String path) {
        final String url = URI.create(webDriver.getCurrentUrl())
                              .resolve(path)
                              .toASCIIString();

        webDriver.navigate().to(url);
    }

    public void clickElement(final String element) {
        findElement(element).click();
        waitForLoad();
    }

    protected void waitForLoad() {
        try {
            Thread.sleep(1_000); // Wait for a moment before we start
        }
        catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        new WebDriverWait(webDriver, 10).pollingEvery(1, SECONDS)
                                        .until(new Predicate<WebDriver>() {
                                            @Override
                                            public boolean apply(final WebDriver webDriver) {
                                                try {
                                                    return !webDriver.findElement(By.id("loader")).isDisplayed();
                                                }
                                                catch (NoSuchElementException e) {
                                                    return true;
                                                }
                                            }
                                        });
    }

    protected WebElement findElement(final String element) {
        if (!knownElements.containsKey(lowerCase(element))) {
            fail(format("Unknown element: %s", lowerCase(element)));
        }

        return knownElements.get(lowerCase(element)).get(0);
    }

    public void clickElement(final int index,
                             final String element) {
        webDriver.findElement(By.id(element + "_" + index)).click();
    }

    public int countElements(final String element) {
        return findElements(element).size();
    }

    protected List<WebElement> findElements(final String element) {
        return knownElements.containsKey(lowerCase(element))
               ? knownElements.get(lowerCase(element))
               : Collections.<WebElement>emptyList();
    }

    public String currentUrl() {
        return webDriver.getCurrentUrl();
    }

    public void fillForm(final List<Map<String, String>> data) {
        for (Map<String, String> map : data) {

            final String name = map.get("Field Name");
            final String value = map.get("Value");

            setField(findElement(name), value);
        }

        submit();
        waitForLoad();
    }

    protected void setField(final WebElement webElement,
                            final String value) {
        if (value.startsWith("classpath:")) {
            try {
                final File file = ResourceUtils.getFile(value);
                webElement.sendKeys(file.getAbsolutePath());
            }
            catch (FileNotFoundException e) {
                throw new RuntimeException(format("Error loading file: %s", value), e);
            }
        }
        else {
            webElement.clear();
            webElement.sendKeys(value);
        }
    }

    public void submit() {
        throw new UnsupportedOperationException();
    }

    public String getName() {
        return name;
    }

    public String getPath() {
        return URI.create(webDriver.getCurrentUrl()).getPath();
    }

    public String getText(final String element) {
        return bodyElement.findElement(By.id(element)).getText();
    }

    public String getText() {
        return bodyElement.getText();
    }

    public String getTitle() {
        return webDriver.getTitle();
    }

    public String getValidationError(String name) {
        return findElement(name + " Validation").getText();
    }

    @PostConstruct
    protected void initializeKnownElements() {

        final Builder<String, List<WebElement>> builder = ImmutableMap.builder();

        ReflectionUtils.doWithFields(getClass(),
                                     new FieldCallback() {
                                         @Override
                                         @SuppressWarnings("unchecked")
                                         public void doWith(final Field field) throws IllegalArgumentException, IllegalAccessException {
                                             final String name = lowerCase(join(splitByCharacterTypeCamelCase(field.getName()), ' '));

                                             field.setAccessible(true);
                                             final Object value = ReflectionUtils.getField(field, GenericPage.this);

                                             if (value instanceof List) {
                                                 builder.put(name, (List<WebElement>) value);
                                             }
                                             else if (value instanceof WebElement) {
                                                 builder.put(name, Collections.singletonList((WebElement) value));
                                             }
                                         }
                                     },
                                     new AnnotationFieldFilter(FindBy.class));

        knownElements = builder.build();
    }

}
