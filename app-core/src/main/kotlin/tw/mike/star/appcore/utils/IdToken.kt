package tw.mike.star.appcore.utils

import java.io.Serializable

import com.fasterxml.jackson.annotation.JsonProperty
import java.util.UUID

/**
 * 代表身分的 id_token
 */
data class IdToken(
    @JsonProperty("iss") // 發行令牌的服務器的URL
    val iss: String,

    @JsonProperty("auth_time") // 用戶完成身份驗證的時間戳
    var authTime: Long = 0,

    @JsonProperty("sub") // 用戶的唯一標識符
    val sub: UUID,

    @JsonProperty("exp") // 令牌過期的時間戳
    val exp: Long,

    @JsonProperty("iat") // 令牌簽發的時間戳
    val iat: Long,

    @JsonProperty("account") // 用戶的帳號
    val account: String,

    @JsonProperty("name") // 用戶的全名
    val name: String,

    @JsonProperty("email") // 用戶的電子郵件
    val email: String,

    @JsonProperty("mobile") // 用戶的手機門號
    val mobile: String,

    @JsonProperty("roleUid") // 權限角色-鍵值
    val roleUid: UUID,

    @JsonProperty("roleName") // 權限角色-名稱
    val roleName: String
):Serializable