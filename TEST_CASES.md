# Full Test Case Specifications - UTC Login Portal

**Project:** Software Testing (KTPM)  
**Target URL:** `https://vanphongdientu.utc.edu.vn/Login`  
**Test Framework:** Selenium WebDriver 4.x + JUnit 5 + Allure Report  
**Browser:** Google Chrome  
**Excel Specification:** `TEST_CASES.xlsx` / `TEST_CASES_FULL.xlsx`

---

## 📋 Summary of All Implemented Test Cases

| Test Case ID | Feature | Test Case Title | Test Type | Priority | Status |
| :--- | :--- | :--- | :--- | :--- | :--- |
| **TC_LOGIN_01** | Authentication | Verify opening and closing UTC login page | Functional / Smoke | Blocker | **PASSED** |
| **TC_LOGIN_02** | Authentication | Verify login failure with invalid credentials (TC3 in spec) | Negative Functional | Critical | **PASSED** |
| **TC_LOGIN_03** | Authentication | Verify login failure when entering username only (TC2 in spec) | Negative / Validation | High | **PASSED** |
| **TC_LOGIN_04** | Authentication | Verify login failure when entering password only (TC1 in spec) | Negative / Validation | High | **PASSED** |
| **TC_LOGIN_05** | Authentication | Verify login failure with non-existent username (TC4 in spec) | Negative Functional | Critical | **PASSED** |
| **TC_LOGIN_06** | SSO / OAuth | Verify login with UTC email redirects to Google OAuth (TC7 in spec) | Functional / Integration | Normal | **PASSED** |
| **TC_LOGIN_07** | Account Recovery | Verify Forgot Password link redirects to recovery page (TC8 in spec) | Functional / Navigation | Normal | **PASSED** |
| **TC_LOGIN_08** | Authentication | Verify login failure when both fields are empty (TC9 in spec) | Negative / Validation | Normal | **PASSED** |
| **TC_LOGIN_09** | Security / UI | Verify password field is masked with type='password' (TC10 in spec) | Security / UI | Critical | **PASSED** |
| **TC_LOGIN_10** | Keyboard Nav | Verify submitting login form by pressing Enter key (TC11 in spec) | Functional / Usability | Normal | **PASSED** |
| **TC_LOGIN_11** | Keyboard Nav | Verify Tab key switches focus from username to password (TC12 in spec) | Usability / Accessibility | Minor | **PASSED** |
| **TC_LOGIN_12** | Footer Nav | Verify Help Center & Feedback footer links (TC13, TC14 in spec) | Navigation / UI | Minor | **PASSED** |
| **TC_LOGIN_13** | Input Sanitization | Verify login handling with whitespace in username (TC15 in spec) | Negative / Validation | Normal | **PASSED** |
| **TC_LOGIN_14** | Security | Verify system security on multiple consecutive failed logins (TC16 in spec) | Security / Reliability | Critical | **PASSED** |
