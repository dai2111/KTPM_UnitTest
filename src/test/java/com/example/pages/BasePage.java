package com.example.pages;

import io.qameta.allure.Attachment;
import io.qameta.allure.Step;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

/**
 * BasePage: Chứa các thao tác dùng chung cho toàn bộ Page Objects (wait, click, type, screenshot)
 */
public abstract class BasePage {

    protected WebDriver driver;
    protected WebDriverWait wait;

    public BasePage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    public void pause(long millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    @Attachment(value = "Ảnh chụp màn hình: {name}", type = "image/png")
    public byte[] takeScreenshot(String name) {
        if (driver instanceof TakesScreenshot) {
            return ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES);
        }
        return new byte[0];
    }

    public void openUrl(String url) {
        driver.get(url);
    }

    public String getPageTitle() {
        return driver.getTitle();
    }

    public String getCurrentUrl() {
        return driver.getCurrentUrl();
    }

    public String getPageSource() {
        return driver.getPageSource();
    }

    public WebElement waitForVisibility(By locator) {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
    }

    public WebElement waitForClickable(By locator) {
        return wait.until(ExpectedConditions.elementToBeClickable(locator));
    }

    public void click(By locator) {
        waitForClickable(locator).click();
    }

    public boolean isElementDisplayed(By locator) {
        try {
            return waitForVisibility(locator).isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    public String getAttribute(By locator, String attributeName) {
        return waitForVisibility(locator).getAttribute(attributeName);
    }

    public String getText(By locator) {
        return waitForVisibility(locator).getText();
    }

    @Step("Nhập dữ liệu vào ô: {elementName}")
    public void slowType(By locator, String text, String elementName) {
        WebElement element = waitForVisibility(locator);
        element.clear();
        if (text != null && !text.isEmpty()) {
            for (char c : text.toCharArray()) {
                element.sendKeys(String.valueOf(c));
                pause(50);
            }
        }
        pause(400);
    }
}
