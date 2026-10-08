package com.example.pages;

import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;

/**
 * LoginPage: Page Object đại diện cho trang Đăng nhập Văn phòng điện tử UTC
 */
public class LoginPage extends BasePage {

    public static final String PAGE_URL = "https://vanphongdientu.utc.edu.vn/Login";

    // Locators
    private final By usernameField = By.name("username");
    private final By passwordField = By.name("userpwd");
    private final By submitLoginBtn = By.cssSelector("input.submit_login");
    private final By emailUtcLoginBtn = By.cssSelector("a.button");
    private final By forgotPasswordLink = By.xpath("//a[contains(@href, '/Login/GetPass')]");
    private final By helpCenterLink = By.xpath("//a[contains(@href, 'hotrokythuat.utc.edu.vn') and contains(text(), 'Trung tâm trợ giúp')]");
    private final By feedbackLink = By.xpath("//a[contains(@href, 'mailto:hotrokythuat@utc.edu.vn') and contains(text(), 'Ý kiến phản hồi')]");

    public LoginPage(WebDriver driver) {
        super(driver);
    }

    @Step("Mở trang đăng nhập Văn phòng điện tử UTC")
    public LoginPage open() {
        openUrl(PAGE_URL);
        pause(700);
        return this;
    }

    public boolean isUsernameFieldDisplayed() {
        return isElementDisplayed(usernameField);
    }

    public boolean isPasswordFieldDisplayed() {
        return isElementDisplayed(passwordField);
    }

    public boolean isSubmitButtonDisplayed() {
        return isElementDisplayed(submitLoginBtn);
    }

    public String getSubmitButtonValue() {
        return getAttribute(submitLoginBtn, "value");
    }

    public String getPasswordFieldType() {
        return getAttribute(passwordField, "type");
    }

    public String getHelpCenterHref() {
        return getAttribute(helpCenterLink, "href");
    }

    public String getFeedbackHref() {
        return getAttribute(feedbackLink, "href");
    }

    public String getEmailUtcButtonText() {
        return getText(emailUtcLoginBtn);
    }

    public String getForgotPasswordText() {
        return getText(forgotPasswordLink);
    }

    @Step("Thực hiện đăng nhập với tài khoản: '{username}', mật khẩu: '{password}'")
    public void login(String username, String password) {
        pause(400);
        slowType(usernameField, username, "Tên đăng nhập");
        slowType(passwordField, password, "Mật khẩu");
        takeScreenshot("Truoc_Khi_Bam_Dang_Nhap");

        pause(400);
        click(submitLoginBtn);
        pause(700);
        takeScreenshot("Sau_Khi_Bam_Dang_Nhap");
    }

    @Step("Click vào nút 'Đăng nhập bằng e-mail UTC'")
    public void clickEmailUtcLogin() {
        takeScreenshot("Truoc_Khi_Click_Email_UTC");
        click(emailUtcLoginBtn);
        wait.until(ExpectedConditions.urlContains("accounts.google.com"));
        takeScreenshot("Sau_Khi_Chuyen_Huong_Google");
    }

    @Step("Click vào liên kết 'Bạn quên mật khẩu đăng nhập ?'")
    public void clickForgotPassword() {
        takeScreenshot("Truoc_Khi_Click_Quen_Mat_Khau");
        click(forgotPasswordLink);
        wait.until(ExpectedConditions.urlContains("/Login/GetPass"));
        takeScreenshot("Sau_Khi_Vao_Trang_Quen_Mat_Khau");
    }

    @Step("Nhập thông tin và nhấn phím Enter để gửi form")
    public void submitWithEnterKey(String username, String password) {
        WebElement uField = waitForVisibility(usernameField);
        WebElement pField = waitForVisibility(passwordField);

        uField.sendKeys(username);
        pField.sendKeys(password);
        takeScreenshot("Truoc_Khi_Nhan_Enter");

        pField.sendKeys(Keys.ENTER);
        pause(700);
        takeScreenshot("Sau_Khi_Nhan_Enter");
    }

    @Step("Focus ô username và nhấn phím Tab")
    public void pressTabFromUsername() {
        WebElement uField = waitForVisibility(usernameField);
        uField.click();
        pause(400);
        uField.sendKeys(Keys.TAB);
        pause(400);
    }

    public WebElement getActiveElement() {
        return driver.switchTo().activeElement();
    }
}
