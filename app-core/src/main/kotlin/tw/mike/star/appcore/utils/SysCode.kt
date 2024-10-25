package tw.mike.star.appcore.utils

enum class SysCode {
    OK(0, "OK"),
    _1(1, "參數錯誤"),
    _2(2, "權限不合法"),
    _3(3, "系統異常"),
    _4(4, "因故無法執行"),
    _5(5, "登入失敗"),
    _1000(1000, "缺少必要參數或參數格式不正確"),
    _1001(1001, "密碼驗證失敗"),
    _1002(1002, "更新 accessToken 失敗"),
    _1003(1003, "帳號已存在"),
    _1004(1004, "資料不存在，無法被更新或刪除"),
    _2001(2001, "帳號無效或已停用"),
    _2002(2002, "帳號或密碼錯誤"),
    _2003(2003, "系統驗證機制錯誤", "codeChallenge 驗證失敗"),
    _2004(2004, "系統驗證機制錯誤", "codeVerifier 驗證失敗"),
    _2005(2005, "登入資訊有誤", "session 驗證失敗"),
    _2006(2006, "登入資訊有誤", "session 缺少必要屬性"),
    _2007(2007, "登入逾時", "Token 已過期"),
    _3001(3001, "系統存取資料異常", "資料庫存取異常"),
    _3002(3002, "電子郵件發送異常"),
    _3004(3004, "系統存取資料異常", "REDIS存取異常"),
    _3005(3005, "查無符合條件的路徑"),
    _4001(4001, "系統排程執行中，目前無法手動觸發執行"),
    _4002(4002, "此系統排程無法手動觸發執行"),
    _4003(4003, "此時段為無效，故無法執行"),
    _4004(4004, "模組被設定為停用");

    val code: Int
    val message: String
    var systemMessage: String = ""
        private set

    constructor(code: Int, message: String) {
        this.code = code
        this.message = message
    }

    constructor(code: Int, message: String, systemMessage: String) {
        this.code = code
        this.message = message
        this.systemMessage = systemMessage
    }
}
