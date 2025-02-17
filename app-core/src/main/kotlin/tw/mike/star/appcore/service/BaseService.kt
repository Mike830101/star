
package tw.mike.star.appcore.service

import org.springframework.security.core.Authentication
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.context.SecurityContextHolder
import tw.mike.star.appcore.config.security.MyUserDetails
import tw.mike.star.appcore.utils.IdToken
import tw.mike.star.appcore.utils.logger
import kotlin.random.Random

abstract class BaseService {
    companion object {
        val log = logger()
    }

    fun getIdToken(): IdToken {
        val tag = "getIdToken"
        val authentication: Authentication = SecurityContextHolder.getContext().authentication
        val principal: MyUserDetails = authentication.principal as MyUserDetails // 獲取使用者物件
        val roles: Collection<GrantedAuthority?> = authentication.authorities // 獲取角色權限
        log.debug("$tag,roles:$roles")

        val user = principal.user
        val role = principal.role

        return IdToken(
            iss = "https://example.com", // 發行者 URL
            authTime = System.currentTimeMillis() / 1000, // 當前時間戳（秒）
            sub = user.uid!!, //用戶唯一標識符
            exp = System.currentTimeMillis() / 1000 + Random.nextLong(3600, 7200), // 隨機設定令牌過期時間（1小時到2小時）
            iat = System.currentTimeMillis() / 1000, // 當前時間戳（秒）
            account = user.acc!!, //帳號
            name = user.name!!, //用戶名
            email = user.email!!, //電子郵件
            mobile = user.telPhone!!, //手機門號
            roleUid = user.roleId!!, //角色 UID
            roleName = role.name!! //角色名稱
        )
    }
}