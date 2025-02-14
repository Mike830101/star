package tw.mike.star.appcore.model

class AuthModel {}

/**
 * 登入
 * @param acc 帳號
 * @param pwd 密碼
 */
data class LoginReq(val acc: String, val pwd: String)