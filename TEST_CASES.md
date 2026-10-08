# Test Case Specifications - UTC Login Portal

**Project:** Software Testing (KTPM)  
**Target URL:** `https://vanphongdientu.utc.edu.vn/Login`  
**Test Framework:** Selenium WebDriver 4.x + JUnit 5 + Allure Report  
**Browser:** Google Chrome  

---

## Summary of Test Cases

| Test Case ID | Test Case Title | Test Type | Priority | Expected Result | Status |
| :--- | :--- | :--- | :--- | :--- | :--- |
| **TC_LOGIN_01** | Verify opening and closing the UTC login page | Functional / Smoke | High | Web page loads successfully with title "Đăng nhập", all login fields are visible, and browser closes cleanly. | **PASSED** |
| **TC_LOGIN_02** | Verify login failure with invalid username and password | Negative Functional | Critical | Error message displayed ("Tài khoản hoặc mật khẩu không đúng"), user remains on the `/Login` page. | **PASSED** |
| **TC_LOGIN_03** | Verify login failure when entering username only (empty password) | Negative Functional / Validation | High | Form is submitted, user is prevented from logging in and remains on the `/Login` page. | **PASSED** |
| **TC_LOGIN_04** | Verify login failure when entering password only (empty username) | Negative Functional / Validation | High | Form is submitted, user is prevented from logging in and remains on the `/Login` page. | **PASSED** |

---

## Detailed Test Case Specifications

### TC_LOGIN_01: Verify opening and closing the UTC login page
- **Objective:** Ensure the UTC login portal is reachable, elements are rendered properly, and the session can be closed cleanly.
- **Preconditions:** Chrome browser installed, network connection available.
- **Test Steps:**
  1. Launch the Chrome browser instance.
  2. Navigate to `https://vanphongdientu.utc.edu.vn/Login`.
  3. Verify the browser title is `"Đăng nhập"`.
  4. Verify the username input field (`name="username"`), password input field (`name="userpwd"`), and submit button (`input.submit_login`) are displayed.
  5. Close the browser.
- **Test Data:** N/A
- **Expected Result:** Page renders with all necessary login components and terminates without errors.
- **Automation Method:** `testOpenAndCloseBrowser()` in `UtcLoginTest.java`

---

### TC_LOGIN_02: Verify login failure with invalid username and password
- **Objective:** Verify that system rejects authentication with non-existent or incorrect credentials.
- **Preconditions:** User is on the login page `https://vanphongdientu.utc.edu.vn/Login`.
- **Test Steps:**
  1. Locate username input field and enter `"huongnt"`.
  2. Locate password input field and enter `"123456@utc"`.
  3. Click the `"Đăng nhập"` submit button.
  4. Wait for server response.
  5. Verify that the URL contains `/Login`.
  6. Verify that an error message (e.g., `"Tài khoản hoặc mật khẩu không đúng"`) is rendered.
- **Test Data:**
  - `Username`: `huongnt`
  - `Password`: `123456@utc`
- **Expected Result:** Login is rejected, user remains on the login page, error notification appears.
- **Automation Method:** `testLoginWithInvalidCredentials()` in `UtcLoginTest.java`

---

### TC_LOGIN_03: Verify login failure when entering username only (empty password)
- **Objective:** Verify system behavior when the password field is left empty.
- **Preconditions:** User is on the login page `https://vanphongdientu.utc.edu.vn/Login`.
- **Test Steps:**
  1. Locate username input field and enter `"huongnt"`.
  2. Leave password input field blank.
  3. Click the `"Đăng nhập"` submit button.
  4. Verify the system does not authenticate and remains on `/Login`.
- **Test Data:**
  - `Username`: `huongnt`
  - `Password`: `""` (Empty string)
- **Expected Result:** Authentication is not granted, user stays on the `/Login` page.
- **Automation Method:** `testLoginWithUsernameOnly()` in `UtcLoginTest.java`

---

### TC_LOGIN_04: Verify login failure when entering password only (empty username)
- **Objective:** Verify system behavior when the username field is left empty.
- **Preconditions:** User is on the login page `https://vanphongdientu.utc.edu.vn/Login`.
- **Test Steps:**
  1. Leave username input field blank.
  2. Locate password input field and enter `"123456@utc"`.
  3. Click the `"Đăng nhập"` submit button.
  4. Verify the system does not authenticate and remains on `/Login`.
- **Test Data:**
  - `Username`: `""` (Empty string)
  - `Password`: `123456@utc`
- **Expected Result:** Authentication is not granted, user stays on the `/Login` page.
- **Automation Method:** `testLoginWithPasswordOnly()` in `UtcLoginTest.java`
