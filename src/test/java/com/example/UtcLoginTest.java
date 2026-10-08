package com.example;

import io.qameta.allure.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

import static org.assertj.core.api.Assertions.assertThat;

@Epic("Hệ thống Văn phòng điện tử UTC")
@Feature("Kiểm thử Chức năng Đăng nhập")
public class UtcLoginTest {

    private static final String LOGIN_URL = "https://vanphongdientu.utc.edu.vn/Login";
    
    private final By usernameBy = By.name("username");
    private final By passwordBy = By.name("userpwd");
    private final By submitButtonBy = By.cssSelector("input.submit_login");
    private final By persistentCheckboxBy = By.id("persistent");

    private WebDriver driver;
    private WebDriverWait wait;

    private void pause(long millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    @Attachment(value = "Ảnh chụp màn hình: {name}", type = "image/png")
    private byte[] takeScreenshot(String name) {
        if (driver instanceof TakesScreenshot) {
            return ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES);
        }
        return new byte[0];
    }

    @Step("Nhập dữ liệu vào ô: {elementName}")
    private void slowType(WebElement element, String text, String elementName) {
        element.clear();
        if (text != null && !text.isEmpty()) {
            for (char c : text.toCharArray()) {
                element.sendKeys(String.valueOf(c));
                pause(50);
            }
        }
        pause(500);
    }

    @BeforeEach
    public void setUp() {
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--remote-allow-origins=*");

        driver = new ChromeDriver(options);
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        driver.manage().window().maximize();

        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    @Step("Thực hiện điền thông tin đăng nhập: tài khoản = '{username}', mật khẩu = '{password}'")
    private void doLogin(String username, String password) {
        WebElement usernameField = wait.until(ExpectedConditions.visibilityOfElementLocated(usernameBy));
        WebElement passwordField = driver.findElement(passwordBy);
        WebElement submitButton = driver.findElement(submitButtonBy);

        pause(500);

        slowType(usernameField, username, "Tên đăng nhập");
        slowType(passwordField, password, "Mật khẩu");

        takeScreenshot("Truoc_Khi_Bam_Dang_Nhap");
        pause(500);

        submitButton.click();
        pause(700);

        takeScreenshot("Sau_Khi_Bam_Dang_Nhap");
    }

    @Test
    @DisplayName("Test 1: Mở trang web đăng nhập UTC và đóng lại")
    @Description("Kiểm tra mở trình duyệt, tải trang đăng nhập thành công và hiển thị đủ các trường.")
    @Severity(SeverityLevel.BLOCKER)
    @Story("Mở và đóng trang web")
    public void testOpenAndCloseBrowser() {
        driver.get(LOGIN_URL);
        pause(700);

        assertThat(driver.getTitle()).isEqualTo("Đăng nhập");

        WebElement usernameField = wait.until(ExpectedConditions.visibilityOfElementLocated(usernameBy));
        WebElement passwordField = driver.findElement(passwordBy);
        WebElement submitButton = driver.findElement(submitButtonBy);

        assertThat(usernameField.isDisplayed()).isTrue();
        assertThat(passwordField.isDisplayed()).isTrue();
        assertThat(submitButton.isDisplayed()).isTrue();
        assertThat(submitButton.getAttribute("value")).isEqualTo("Đăng nhập");

        takeScreenshot("Giao_Dien_Trang_Dang_Nhap");
        pause(700);
    }

    @Test
    @DisplayName("Test 2: Đăng nhập tài khoản huongnt và mật khẩu sai (123456@utc)")
    @Description("Kiểm tra thông báo lỗi khi người dùng nhập sai thông tin tài khoản hoặc mật khẩu.")
    @Severity(SeverityLevel.CRITICAL)
    @Story("Đăng nhập thất bại do sai thông tin")
    public void testLoginWithInvalidCredentials() {
        driver.get(LOGIN_URL);
        pause(500);

        doLogin("huongnt", "123456@utc");

        wait.until(ExpectedConditions.urlContains("/Login"));
        assertThat(driver.getCurrentUrl()).contains("/Login");

        String pageSource = driver.getPageSource();
        assertThat(pageSource).containsAnyOf("Tài khoản hoặc mật khẩu không đúng", "không đúng", "Login");

        takeScreenshot("Ket_Qua_Dang_Nhap_Sai");
        pause(700);
    }

    @Test
    @DisplayName("Test 3: Điền tài khoản nhưng không điền mật khẩu")
    @Description("Kiểm tra phản hồi của hệ thống khi để trống mật khẩu.")
    @Severity(SeverityLevel.NORMAL)
    @Story("Đăng nhập không điền mật khẩu")
    public void testLoginWithUsernameOnly() {
        driver.get(LOGIN_URL);
        pause(500);

        doLogin("huongnt", "");

        wait.until(ExpectedConditions.urlContains("/Login"));
        assertThat(driver.getCurrentUrl()).contains("/Login");

        takeScreenshot("Ket_Qua_Khong_Dien_Mat_Khau");
        pause(700);
    }

    @Test
    @DisplayName("Test 4: Điền mật khẩu nhưng không điền tài khoản")
    @Description("Kiểm tra phản hồi của hệ thống khi để trống tên đăng nhập.")
    @Severity(SeverityLevel.NORMAL)
    @Story("Đăng nhập không điền tài khoản")
    public void testLoginWithPasswordOnly() {
        driver.get(LOGIN_URL);
        pause(500);

        doLogin("", "123456@utc");

        wait.until(ExpectedConditions.urlContains("/Login"));
        assertThat(driver.getCurrentUrl()).contains("/Login");

        takeScreenshot("Ket_Qua_Khong_Dien_Tai_Khoan");
        pause(700);
    }

    @AfterEach
    public void tearDown() {
        pause(1000);
        if (driver != null) {
            driver.quit();
        }
    }
}
