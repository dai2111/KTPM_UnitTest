# Dự án Kiểm thử phần mềm (Selenium WebDriver + JUnit 5 + Maven)

Dự án Java Maven đã được thiết lập sẵn với các thư viện kiểm thử tự động:
- **Selenium Java** (Selenium 4.x hỗ trợ tự động quản lý driver thông qua Selenium Manager)
- **WebDriverManager** (Hỗ trợ tải/quản lý driver cho Chrome, Firefox, Edge, ...)
- **JUnit 5 (Jupiter)** (Framework viết Unit & Automation Tests)
- **AssertJ** (Thư viện Assertion linh hoạt)
- **SLF4J** (Hỗ trợ logging)

---

## 📁 Cấu trúc thư mục

```
KTPM_UnitTest/
├── pom.xml                                   # Cấu hình dependencies Maven
├── .gitignore
├── README.md
└── src/
    ├── main/java/com/example/
    │   └── App.java                          # Mã nguồn chính
    └── test/java/com/example/
        ├── SeleniumSampleTest.java           # Test case mẫu
        └── UtcLoginTest.java                 # Bộ 4 test case cho trang Văn phòng điện tử UTC
```

---

## 🧪 Chi tiết các Test Case ([UtcLoginTest.java](file:///q:/KTPM/KTPM_UnitTest/src/test/java/com/example/UtcLoginTest.java))

Trang kiểm thử: `https://vanphongdientu.utc.edu.vn/Login`

1. **Test 1 (`testOpenAndCloseBrowser`)**: Mở trang web kiểm tra tiêu đề, kiểm tra form đăng nhập hiển thị và tự động đóng trình duyệt.
2. **Test 2 (`testLoginWithInvalidCredentials`)**: Nhập tài khoản `huongnt`, mật khẩu sai `123456@utc`, bấm Đăng nhập và kiểm tra thông báo lỗi.
3. **Test 3 (`testLoginWithUsernameOnly`)**: Nhập tài khoản `huongnt`, để trống mật khẩu, bấm Đăng nhập và xác minh hệ thống giữ nguyên trang đăng nhập.
4. **Test 4 (`testLoginWithPasswordOnly`)**: Để trống tài khoản, nhập mật khẩu `123456@utc`, bấm Đăng nhập và xác minh hệ thống giữ nguyên trang đăng nhập.

---

## 🚀 Hướng dẫn chạy

### 1. Chạy trong IDE (IntelliJ IDEA / Eclipse / VS Code)
1. Mở file [UtcLoginTest.java](file:///q:/KTPM/KTPM_UnitTest/src/test/java/com/example/UtcLoginTest.java).
2. Nhấn nút **Run** (biểu tượng mũi tên xanh cạnh tên class hoặc từng phương thức test).

### 2. Chạy qua dòng lệnh Terminal
```powershell
# Chạy bộ test UTC Login (sẽ tự động sinh dữ liệu báo cáo Allure)
.\mvnw test -Dtest=UtcLoginTest
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
.\mvnw clean test -Dtest=UtcLoginTest
.\mvnw allure:serve
```