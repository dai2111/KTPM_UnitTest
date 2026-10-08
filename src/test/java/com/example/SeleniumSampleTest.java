package com.example;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import java.time.Duration;

import static org.assertj.core.api.Assertions.assertThat;

public class SeleniumSampleTest {

    private WebDriver driver;

    @BeforeEach
    public void setUp() {
        // Cấu hình ChromeOptions (chạy không giao diện / headless nếu cần hoặc mở browser trực tiếp)
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--remote-allow-origins=*");
        // Bỏ comment dòng dưới nếu muốn chạy ngầm (không hiện cửa sổ trình duyệt):
        // options.addArguments("--headless=new");

        // Selenium 4 tự động quản lý driver qua Selenium Manager (hoặc có thể dùng WebDriverManager)
        driver = new ChromeDriver(options);
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        driver.manage().window().maximize();
    }

    @Test
    @DisplayName("Kiểm tra mở Google và tìm kiếm từ khóa")
    public void testGoogleSearch() {
        driver.get("https://www.google.com");

        // Kiểm tra tiêu đề trang
        assertThat(driver.getTitle()).contains("Google");

        // Tìm thanh tìm kiếm và gõ từ khóa
        WebElement searchBox = driver.findElement(By.name("q"));
        searchBox.sendKeys("Selenium WebDriver");
        searchBox.submit();

        // Đảm bảo kết quả tìm kiếm hiển thị
        assertThat(driver.getTitle()).isNotEmpty();
    }

    @AfterEach
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}
