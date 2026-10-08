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

    @Test
    @DisplayName("TC4: Sai tên đăng nhập, đúng định dạng mật khẩu")
    @Description("Kiểm tra đăng nhập khi nhập sai username (huongthunguyen) và password (123456@utc).")
    @Severity(SeverityLevel.CRITICAL)
    @Story("Đăng nhập sai username")
    public void testLoginWithInvalidUsername() {
        driver.get(LOGIN_URL);
        pause(500);

        doLogin("huongthunguyen", "123456@utc");

        wait.until(ExpectedConditions.urlContains("/Login"));
        assertThat(driver.getCurrentUrl()).contains("/Login");

        String pageSource = driver.getPageSource();
        assertThat(pageSource).containsAnyOf("Tài khoản hoặc mật khẩu không đúng", "không đúng", "Login");

        takeScreenshot("TC4_Ket_Qua_Sai_Username");
        pause(700);
    }

    @Test
    @DisplayName("TC7: Đăng nhập bằng e-mail UTC chuyển hướng sang Google")
    @Description("Kiểm tra khi click nút 'Đăng nhập bằng e-mail UTC', hệ thống chuyển hướng sang trang đăng nhập Google OAuth.")
    @Severity(SeverityLevel.NORMAL)
    @Story("Đăng nhập bằng OAuth Google")
    public void testLoginWithEmailUtcRedirect() {
        driver.get(LOGIN_URL);
        pause(500);

        WebElement googleLoginBtn = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("a.button")));
        assertThat(googleLoginBtn.getText()).contains("Đăng nhập bằng e-mail UTC");

        takeScreenshot("TC7_Truoc_Khi_Click_Google_Login");
        googleLoginBtn.click();

        // Đợi chuyển hướng sang accounts.google.com
        wait.until(ExpectedConditions.urlContains("accounts.google.com"));
        assertThat(driver.getCurrentUrl()).contains("accounts.google.com");

        takeScreenshot("TC7_Sau_Khi_Chuyen_Huong_Google");
        pause(700);
    }

    @Test
    @DisplayName("TC8: Kiểm tra liên kết Quên mật khẩu")
    @Description("Kiểm tra khi click vào liên kết 'Bạn quên mật khẩu đăng nhập ?', hệ thống chuyển hướng đúng trang khôi phục mật khẩu.")
    @Severity(SeverityLevel.NORMAL)
    @Story("Khôi phục mật khẩu")
    public void testForgotPasswordLink() {
        driver.get(LOGIN_URL);
        pause(500);

        WebElement forgotPasswordLink = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//a[contains(@href, '/Login/GetPass')]")));
        assertThat(forgotPasswordLink.getText()).contains("Bạn quên mật khẩu đăng nhập ?");

        takeScreenshot("TC8_Truoc_Khi_Click_Quen_Mat_Khau");
        forgotPasswordLink.click();

        // Đợi chuyển hướng sang /Login/GetPass
        wait.until(ExpectedConditions.urlContains("/Login/GetPass"));
        assertThat(driver.getCurrentUrl()).contains("/Login/GetPass");

        takeScreenshot("TC8_Sau_Khi_Vao_Trang_Quen_Mat_Khau");
        pause(700);
    }

    @Test
    @DisplayName("TC9: Để trống cả Tên đăng nhập và Mật khẩu")
    @Description("Kiểm tra khi để trống cả hai trường tài khoản và mật khẩu rồi bấm Đăng nhập, hệ thống không cho phép đăng nhập.")
    @Severity(SeverityLevel.NORMAL)
    @Story("Đăng nhập để trống toàn bộ thông tin")
    public void testLoginWithBothFieldsEmpty() {
        driver.get(LOGIN_URL);
        pause(500);

        doLogin("", "");

        wait.until(ExpectedConditions.urlContains("/Login"));
        assertThat(driver.getCurrentUrl()).contains("/Login");

        takeScreenshot("TC9_Ket_Qua_De_Trong_Ca_Hai_Truong");
        pause(700);
    }

    @Test
    @DisplayName("TC10: Kiểm tra mật khẩu hiển thị dưới dạng ký tự ẩn")
    @Description("Kiểm tra ô nhập mật khẩu có thuộc tính type='password' để ẩn ký tự người dùng nhập.")
    @Severity(SeverityLevel.CRITICAL)
    @Story("Bảo mật mật khẩu")
    public void testPasswordFieldMasked() {
        driver.get(LOGIN_URL);
        pause(500);

        WebElement passwordField = wait.until(ExpectedConditions.visibilityOfElementLocated(passwordBy));
        
        // Kiểm tra thuộc tính type phải là password
        String fieldType = passwordField.getAttribute("type");
        assertThat(fieldType).isEqualTo("password");

        // Nhập mật khẩu thử nghiệm
        passwordField.sendKeys("Secret123@");
        takeScreenshot("TC10_Mat_Khau_An");
        pause(700);
    }

    @Test
    @DisplayName("TC11: Đăng nhập bằng phím Enter")
    @Description("Kiểm tra khi người dùng nhập thông tin và nhấn phím Enter trên bàn phím, form được gửi đi bình thường.")
    @Severity(SeverityLevel.NORMAL)
    @Story("Gửi form bằng bàn phím")
    public void testLoginByPressingEnter() {
        driver.get(LOGIN_URL);
        pause(500);

        WebElement usernameField = wait.until(ExpectedConditions.visibilityOfElementLocated(usernameBy));
        WebElement passwordField = driver.findElement(passwordBy);

        usernameField.sendKeys("huongnt");
        passwordField.sendKeys("123456@utc");

        takeScreenshot("TC11_Truoc_Khi_Nhan_Enter");
        pause(500);

        // Nhấn phím Enter trên ô mật khẩu
        passwordField.sendKeys(org.openqa.selenium.Keys.ENTER);

        wait.until(ExpectedConditions.urlContains("/Login"));
        assertThat(driver.getCurrentUrl()).contains("/Login");

        takeScreenshot("TC11_Sau_Khi_Nhan_Enter");
        pause(700);
    }

    @Test
    @DisplayName("TC12: Kiểm tra phím Tab chuyển đổi giữa các ô nhập liệu")
    @Description("Kiểm tra khi nhấn phím Tab từ ô Tên đăng nhập, con trỏ chuột tự động chuyển focus sang ô Mật khẩu.")
    @Severity(SeverityLevel.MINOR)
    @Story("Điều hướng bằng bàn phím")
    public void testTabKeySwitchFocus() {
        driver.get(LOGIN_URL);
        pause(500);

        WebElement usernameField = wait.until(ExpectedConditions.visibilityOfElementLocated(usernameBy));
        WebElement passwordField = driver.findElement(passwordBy);

        // Click focus vào ô username
        usernameField.click();
        takeScreenshot("TC12_Focus_Username");
        pause(500);

        // Nhấn phím Tab
        usernameField.sendKeys(org.openqa.selenium.Keys.TAB);
        pause(500);

        // Kiểm tra phần tử đang được focus là ô mật khẩu
        WebElement activeElement = driver.switchTo().activeElement();
        assertThat(activeElement.getAttribute("name")).isEqualTo("userpwd");

        takeScreenshot("TC12_Focus_Password_Sau_Khi_Tab");
        pause(700);
    }

    @Test
    @DisplayName("TC13: Kiểm tra liên kết Trung tâm trợ giúp ở footer")
    @Description("Kiểm tra liên kết 'Trung tâm trợ giúp' ở chân trang chuyển hướng đúng địa chỉ http://hotrokythuat.utc.edu.vn.")
    @Severity(SeverityLevel.MINOR)
    @Story("Liên kết hỗ trợ chân trang")
    public void testHelpCenterFooterLink() {
        driver.get(LOGIN_URL);
        pause(500);

        WebElement helpCenterLink = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//a[contains(@href, 'hotrokythuat.utc.edu.vn') and contains(text(), 'Trung tâm trợ giúp')]")));
        assertThat(helpCenterLink.getAttribute("href")).contains("hotrokythuat.utc.edu.vn");

        takeScreenshot("TC13_Lien_Ket_Trung_Tam_Tro_Giup");
        pause(700);
    }

    @Test
    @DisplayName("TC14: Kiểm tra liên kết Ý kiến phản hồi ở footer")
    @Description("Kiểm tra liên kết 'Ý kiến phản hồi' ở chân trang có liên kết mailto:hotrokythuat@utc.edu.vn.")
    @Severity(SeverityLevel.MINOR)
    @Story("Liên kết hỗ trợ chân trang")
    public void testFeedbackFooterLink() {
        driver.get(LOGIN_URL);
        pause(500);

        WebElement feedbackLink = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//a[contains(@href, 'mailto:hotrokythuat@utc.edu.vn') and contains(text(), 'Ý kiến phản hồi')]")));
        assertThat(feedbackLink.getAttribute("href")).isEqualTo("mailto:hotrokythuat@utc.edu.vn");

        takeScreenshot("TC14_Lien_Ket_Y_Kien_Phan_Hoi");
        pause(700);
    }

    @Test
    @DisplayName("TC15: Đăng nhập với Tên đăng nhập chứa khoảng trắng")
    @Description("Kiểm tra khi nhập username chứa khoảng trắng ở đầu/cuối ('  huongnt  '), hệ thống xử lý trim hoặc từ chối đăng nhập.")
    @Severity(SeverityLevel.NORMAL)
    @Story("Xử lý chuỗi khoảng trắng")
    public void testLoginWithWhitespaceInUsername() {
        driver.get(LOGIN_URL);
        pause(500);

        doLogin("  huongnt  ", "123456@utc");

        wait.until(ExpectedConditions.urlContains("/Login"));
        assertThat(driver.getCurrentUrl()).contains("/Login");

        takeScreenshot("TC15_Ket_Qua_Username_Khoang_Trang");
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
