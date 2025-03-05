package tw.mike.star.appcore.utils
import com.fasterxml.jackson.annotation.JsonIgnore
import com.fasterxml.jackson.annotation.JsonPropertyOrder


@JsonPropertyOrder("code", "message", "paging", "data")
data class BaseReply(
    @JsonIgnore
    val sysCode: SysCode? = SysCode.OK,
    @JsonIgnore
    val information: String? = null,
    //回應內容
    val data: Any? = null,
    //頁碼
    val paging: Paging? = null,
) {
    //顯示的錯誤代碼
    val code: String
        get() = sysCode!!.code

    //顯示的訊息文字 (information為空時以 sysCode 預設文字替代)
    val message: String
        get() = information ?: sysCode!!.message
}