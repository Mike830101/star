package tw.mike.star.appcore.utils

enum class SysCode {
    OK("0", "OK"),

    /**
     * 1開頭參數相關
     * 10** 由全域攔截
     * 11** 由BindingResult 攔截
     * 12** 手動判斷
     */
    _1("1", "參數錯誤"),
    _1001("1001", "參數錯誤", "參數效驗失敗"),  //MethodArgumentNotValidException 效驗失敗
    _1002("1002", "參數錯誤", "參數效驗失敗"),  //HttpMessageNotReadableException  json解析失敗
    _1101("1101", "參數錯誤", "參數綁定錯誤"),  //BindingResult 攔截
    _1201("1201", "參數錯誤", "必填項目有誤"),  //手動判斷req必填項目

    /**
     * 2開頭權限相關
     * 21** token相關
     */
    _2("2", "權限不合法"),
    _2001("2001", "權限不合法", "apiKey錯誤"),
    _2002("2002", "帳號已停用"),  //DisabledException
    _2101("2101", "token 已過期"),
    _2102("2102", "token 驗證失敗"),
    _2103("2103", "token 為空"),

    _3("3", "系統異常"),  //未知Exception

    /**
     * 4開頭資料庫相關
     * 40** 資料庫查詢失敗
     * 41** 資料庫查詢成功,內容不符規定
     */
    _4("4", "資料庫查詢失敗"),  //由全域攔截
    _4000("4000", "資料庫失敗", "未知錯誤"),
    _4001("4001", "資料庫失敗", "自訂訊息錯誤"),
    _4100("4100", "資料庫查詢失敗"),
    _4101("4101", "已有重複項目"),
    _4102("4102", "查無此項目"),
    _4103("4103", "資料庫創建失敗"),
    _4104("4104", "資料庫更新失敗"),
    _4105("4105", "資料庫刪除失敗");

    /**
     * 錯誤碼
     */
    val code: String

    /**
     * 回覆訊息
     */
    val message: String

    /**
     * 備註
     */
    private val memo: String

    constructor(code: String, message: String) {
        this.code = code
        this.message = message
        this.memo = ""
    }

    constructor(code: String, message: String, memo: String) {
        this.code = code
        this.message = message
        this.memo = memo
    }
}