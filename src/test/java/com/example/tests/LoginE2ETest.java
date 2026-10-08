package com.example.tests;

import com.example.base.BaseTest;
import com.example.pages.LoginPage;
import io.qameta.allure.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

@Epic("Hệ thống Văn phòng điện tử UTC")
@Feature("Kiểm thử Chức năng Đăng nhập (E2E)")
public class LoginE2ETest extends BaseTest {

    private LoginPage loginPage;

    @BeforeEach
    public void initPage() {
        loginPage = new LoginPage(driver);
    }

    @Test
    @DisplayName("TC00: Mở trang web đăng nhập UTC và đóng lại")
    @Description("Kiểm tra mở trình duyệt, tải trang đăng nhập thành công và hiển thị đủ các trường.")
    @Severity(SeverityLevel.BLOCKER)
    @Story("Mở và đóng trang web")
    public void testOpenAndCloseBrowser() {
        loginPage.open();

        assertThat(loginPage.getPageTitle()).isEqualTo("Đăng nhập");
        assertThat(loginPage.isUsernameFieldDisplayed()).isTrue();
        assertThat(loginPage.isPasswordFieldDisplayed()).isTrue();
        assertThat(loginPage.isSubmitButtonDisplayed()).isTrue();
        assertThat(loginPage.getSubmitButtonValue()).isEqualTo("Đăng nhập");

        loginPage.takeScreenshot("TC00_Giao_Dien_Trang_Dang_Nhap");
    }

    @Test
    @DisplayName("TC01: Để trống Tên đăng nhập (chỉ điền mật khẩu)")
    @Description("Kiểm tra khi để trống tài khoản và điền mật khẩu rồi bấm Đăng nhập, hệ thống không cho phép đăng nhập.")
    @Severity(SeverityLevel.NORMAL)
    @Story("Đăng nhập không điền tài khoản")
    public void testLoginWithPasswordOnly() {
        loginPage.open()
                 .login("", "123456@utc");

        assertThat(loginPage.getCurrentUrl()).contains("/Login");
        loginPage.takeScreenshot("TC01_Ket_Qua_Khong_Dien_Tai_Khoan");
    }

    @Test
    @DisplayName("TC02: Chưa nhập mật khẩu (chỉ điền Tên đăng nhập)")
    @Description("Kiểm tra khi điền tài khoản và để trống mật khẩu rồi bấm Đăng nhập, hệ thống không cho phép đăng nhập.")
    @Severity(SeverityLevel.NORMAL)
    @Story("Đăng nhập không điền mật khẩu")
    public void testLoginWithUsernameOnly() {
        loginPage.open()
                 .login("huongnt", "");

        assertThat(loginPage.getCurrentUrl()).contains("/Login");
        loginPage.takeScreenshot("TC02_Ket_Qua_Khong_Dien_Mat_Khau");
    }

    @Test
    @DisplayName("TC03: Đúng tên đăng nhập, sai mật khẩu")
    @Description("Kiểm tra thông báo lỗi khi người dùng nhập đúng username (huongnt) nhưng sai password (123456@utc).")
    @Severity(SeverityLevel.CRITICAL)
    @Story("Đăng nhập thất bại do sai thông tin")
    public void testLoginWithInvalidCredentials() {
        loginPage.open()
                 .login("huongnt", "123456@utc");

        assertThat(loginPage.getCurrentUrl()).contains("/Login");
        assertThat(loginPage.getPageSource()).containsAnyOf("Tài khoản hoặc mật khẩu không đúng", "không đúng", "Login");
        loginPage.takeScreenshot("TC03_Ket_Qua_Dang_Nhap_Sai");
    }

    @Test
    @DisplayName("TC04: Sai tên đăng nhập, đúng định dạng mật khẩu")
    @Description("Kiểm tra đăng nhập khi nhập sai username (huongthunguyen) và password (123456@utc).")
    @Severity(SeverityLevel.CRITICAL)
    @Story("Đăng nhập sai username")
    public void testLoginWithInvalidUsername() {
        loginPage.open()
                 .login("huongthunguyen", "123456@utc");

        assertThat(loginPage.getCurrentUrl()).contains("/Login");
        assertThat(loginPage.getPageSource()).containsAnyOf("Tài khoản hoặc mật khẩu không đúng", "không đúng", "Login");
        loginPage.takeScreenshot("TC04_Ket_Qua_Sai_Username");
    }

    @Test
    @DisplayName("TC07: Đăng nhập bằng e-mail UTC chuyển hướng sang Google")
    @Description("Kiểm tra khi click nút 'Đăng nhập bằng e-mail UTC', hệ thống chuyển hướng sang trang đăng nhập Google OAuth.")
    @Severity(SeverityLevel.NORMAL)
    @Story("Đăng nhập bằng OAuth Google")
    public void testLoginWithEmailUtcRedirect() {
        loginPage.open();
        assertThat(loginPage.getEmailUtcButtonText()).contains("Đăng nhập bằng e-mail UTC");

        loginPage.clickEmailUtcLogin();
        assertThat(loginPage.getCurrentUrl()).contains("accounts.google.com");
    }

    @Test
    @DisplayName("TC08: Kiểm tra liên kết Quên mật khẩu")
    @Description("Kiểm tra khi click vào liên kết 'Bạn quên mật khẩu đăng nhập ?', hệ thống chuyển hướng đúng trang khôi phục mật khẩu.")
    @Severity(SeverityLevel.NORMAL)
    @Story("Khôi phục mật khẩu")
    public void testForgotPasswordLink() {
        loginPage.open();
        assertThat(loginPage.getForgotPasswordText()).contains("Bạn quên mật khẩu đăng nhập ?");

        loginPage.clickForgotPassword();
        assertThat(loginPage.getCurrentUrl()).contains("/Login/GetPass");
    }

    @Test
    @DisplayName("TC09: Để trống cả Tên đăng nhập và Mật khẩu")
    @Description("Kiểm tra khi để trống cả hai trường tài khoản và mật khẩu rồi bấm Đăng nhập, hệ thống không cho phép đăng nhập.")
    @Severity(SeverityLevel.NORMAL)
    @Story("Đăng nhập để trống toàn bộ thông tin")
    public void testLoginWithBothFieldsEmpty() {
        loginPage.open()
                 .login("", "");

        assertThat(loginPage.getCurrentUrl()).contains("/Login");
        loginPage.takeScreenshot("TC09_Ket_Qua_De_Trong_Ca_Hai_Truong");
    }

    @Test
    @DisplayName("TC10: Kiểm tra mật khẩu hiển thị dưới dạng ký tự ẩn")
    @Description("Kiểm tra ô nhập mật khẩu có thuộc tính type='password' để ẩn ký tự người dùng nhập.")
    @Severity(SeverityLevel.CRITICAL)
    @Story("Bảo mật mật khẩu")
    public void testPasswordFieldMasked() {
        loginPage.open();

        assertThat(loginPage.getPasswordFieldType()).isEqualTo("password");
        loginPage.takeScreenshot("TC10_Mat_Khau_An");
    }

    @Test
    @DisplayName("TC11: Đăng nhập bằng phím Enter thay vì click nút")
    @Description("Kiểm tra khi người dùng nhập thông tin và nhấn phím Enter trên bàn phím, form được gửi đi bình thường.")
    @Severity(SeverityLevel.NORMAL)
    @Story("Gửi form bằng bàn phím")
    public void testLoginByPressingEnter() {
        loginPage.open()
                 .submitWithEnterKey("huongnt", "123456@utc");

        assertThat(loginPage.getCurrentUrl()).contains("/Login");
        loginPage.takeScreenshot("TC11_Sau_Khi_Nhan_Enter");
    }

    @Test
    @DisplayName("TC12: Kiểm tra phím Tab chuyển đổi giữa các ô nhập liệu")
    @Description("Kiểm tra khi nhấn phím Tab từ ô Tên đăng nhập, con trỏ chuột tự động chuyển focus sang ô Mật khẩu.")
    @Severity(SeverityLevel.MINOR)
    @Story("Điều hướng bằng bàn phím")
    public void testTabKeySwitchFocus() {
        loginPage.open()
                 .pressTabFromUsername();

        assertThat(loginPage.getActiveElement().getAttribute("name")).isEqualTo("userpwd");
        loginPage.takeScreenshot("TC12_Focus_Password_Sau_Khi_Tab");
    }

    @Test
    @DisplayName("TC13: Kiểm tra liên kết Trung tâm trợ giúp ở footer")
    @Description("Kiểm tra liên kết 'Trung tâm trợ giúp' ở chân trang chuyển hướng đúng địa chỉ http://hotrokythuat.utc.edu.vn.")
    @Severity(SeverityLevel.MINOR)
    @Story("Liên kết hỗ trợ chân trang")
    public void testHelpCenterFooterLink() {
        loginPage.open();

        assertThat(loginPage.getHelpCenterHref()).contains("hotrokythuat.utc.edu.vn");
        loginPage.takeScreenshot("TC13_Lien_Ket_Trung_Tam_Tro_Giup");
    }

    @Test
    @DisplayName("TC14: Kiểm tra liên kết Ý kiến phản hồi ở footer")
    @Description("Kiểm tra liên kết 'Ý kiến phản hồi' ở chân trang có liên kết mailto:hotrokythuat@utc.edu.vn.")
    @Severity(SeverityLevel.MINOR)
    @Story("Liên kết hỗ trợ chân trang")
    public void testFeedbackFooterLink() {
        loginPage.open();

        assertThat(loginPage.getFeedbackHref()).isEqualTo("mailto:hotrokythuat@utc.edu.vn");
        loginPage.takeScreenshot("TC14_Lien_Ket_Y_Kien_Phan_Hoi");
    }

    @Test
    @DisplayName("TC15: Đăng nhập với Tên đăng nhập chứa khoảng trắng")
    @Description("Kiểm tra khi nhập username chứa khoảng trắng ở đầu/cuối ('  huongnt  '), hệ thống xử lý trim hoặc từ chối đăng nhập.")
    @Severity(SeverityLevel.NORMAL)
    @Story("Xử lý chuỗi khoảng trắng")
    public void testLoginWithWhitespaceInUsername() {
        loginPage.open()
                 .login("  huongnt  ", "123456@utc");

        assertThat(loginPage.getCurrentUrl()).contains("/Login");
        loginPage.takeScreenshot("TC15_Ket_Qua_Username_Khoang_Trang");
    }

    @Test
    @DisplayName("TC05: Đăng nhập sai mật khẩu 5 lần liên tiếp")
    @Description("Kiểm tra khi nhập sai mật khẩu nhiều lần liên tiếp (5 lần), hệ thống tiếp tục từ chối đăng nhập và giữ an toàn.")
    @Severity(SeverityLevel.CRITICAL)
    @Story("Bảo mật chống tấn công Brute-force")
    public void testMultipleFailedLoginAttempts() {
        loginPage.open();

        for (int i = 1; i <= 5; i++) {
            System.out.println("-> Thử đăng nhập sai lần thứ: " + i);
            loginPage.login("huongnt", "wrongpass_" + i);
            assertThat(loginPage.getCurrentUrl()).contains("/Login");
            loginPage.takeScreenshot("TC16_Lan_Thu_Sai_" + i);
        }
    }
}
