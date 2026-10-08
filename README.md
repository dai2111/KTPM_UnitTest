# Dự án Kiểm thử phần mềm (Selenium WebDriver + JUnit 5 + Maven)

Dự án Java Maven đã được thiết lập sẵn với các thư viện kiểm thử tự động:
- **Selenium Java** (Selenium 4.x hỗ trợ tự động quản lý driver thông qua Selenium Manager)
- **WebDriverManager** (Hỗ trợ tải/quản lý driver cho Chrome, Firefox, Edge, ...)
- **JUnit 5 (Jupiter)** (Framework viết Unit & Automation Tests)
- **AssertJ** (Thư viện Assertion linh hoạt)
- **SLF4J** (Hỗ trợ logging)

---

## 📁 Cấu trúc thư mục chuẩn Page Object Model (POM)

```
KTPM_UnitTest/
├── pom.xml
├── .gitignore
├── README.md
└── src/
    ├── main/java/com/example/
    │   └── App.java
    └── test/java/com/example/
        ├── base/
        │   └── BaseTest.java             # Quản lý khởi tạo WebDriver, timeouts, tearDown
        ├── pages/
        │   ├── BasePage.java             # Thao tác dùng chung: wait, click, type, screenshot
        │   └── LoginPage.java            # Page Object cho Form đăng nhập UTC
        └── tests/
            └── LoginE2ETest.java         # Toàn bộ Test Scripts E2E cho chức năng Đăng nhập
```

---

## 🚀 Hướng dẫn chạy

### 1. Chạy trong IDE (IntelliJ IDEA / Eclipse / VS Code)
1. Mở file [LoginE2ETest.java](file:///q:/KTPM/KTPM_UnitTest/src/test/java/com/example/tests/LoginE2ETest.java).
2. Nhấn nút **Run** (biểu tượng mũi tên xanh ▶️ cạnh tên class hoặc từng test case).

### 2. Chạy qua dòng lệnh Terminal
```powershell
# Chạy toàn bộ Test E2E
.\mvnw test -Dtest=LoginE2ETest
```

---

## 📊 Xem báo cáo kiểm thử Allure Report trực quan

Sau khi chạy test xong, bạn chỉ cần gõ lệnh sau để mở trang báo cáo tương tác Allure:

```powershell
.\mvnw allure:serve
```

Lệnh này sẽ tự động:
1. Tạo dashboard báo cáo HTML đầy đủ biểu đồ, thời gian thực thi, thống kê Passed/Failed.
2. Đính kèm đầy đủ từng bước thực hiện (`@Step`) và hình ảnh chụp màn hình (`Screenshots`).
3. Tự động mở trang báo cáo trên trình duyệt mặc định của bạn.

> Để dừng máy chủ xem báo cáo Allure, nhấn tổ hợp phím `Ctrl + C` trong Terminal.

> 💡 **Mẹo:** Nếu muốn xóa kết quả cũ và chạy lại từ đầu cho sạch sẽ:
```powershell
.\mvnw clean test -Dtest=LoginE2ETest
.\mvnw allure:serve
```