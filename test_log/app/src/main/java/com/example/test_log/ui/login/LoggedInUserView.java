package com.example.test_log.ui.login;

/**
 * Class exposing authenticated user details to the UI.
 * 类向UI公开已验证的用户详细信息。
 */
class LoggedInUserView {
    private String displayName;
    //... other data fields that may be accessible to the UI
//    ... 用户界面可以访问的其他数据字段

    LoggedInUserView(String displayName) {
        this.displayName = displayName;
    }

    String getDisplayName() {
        return displayName;
    }
}
