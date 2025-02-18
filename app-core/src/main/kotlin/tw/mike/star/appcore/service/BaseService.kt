
package tw.mike.star.appcore.service

import org.springframework.security.core.Authentication
import org.springframework.security.core.context.SecurityContextHolder
import tw.mike.star.appcore.utils.IdToken
import tw.mike.star.appcore.utils.logger

abstract class BaseService {
    companion object {
        val log = logger()
    }

    fun getIdToken(): IdToken {
        val tag = "getIdToken"
        val authentication: Authentication = SecurityContextHolder.getContext().authentication
        val idToken: IdToken = authentication.principal as IdToken // 獲取使用者物件

        return idToken
    }
}