package tw.mike.star.appcore.service

import io.jsonwebtoken.*
import io.jsonwebtoken.security.SignatureException
import org.springframework.stereotype.Service
import tw.mike.star.appcore.utils.JwtUtils
import tw.mike.star.appcore.utils.logger
import java.util.*


@Service
class JwtService(val jwtUtils: JwtUtils) {
    private final val log = logger()

    /**
     * 產生token
     * @return jwt.token
     */
    fun generateToken(acc: String): String {
        val claims: MutableMap<String, Any> = HashMap()

        val token: String = jwtUtils.generateToken(claims, acc)
        return token
    }


    /**
     * 驗證 Token 是否正確
     * @param token 用戶提交的 Token
     * @return 是否有效 0: 無效 , 1: 有效 , -1:過期
     */
    fun verifyToken(token: String,userName: String): Int {
        try {
            //內容是否正確
            if (userName == jwtUtils.extractSubject(token)) {
                //過期判斷
                if (jwtUtils.extractExpiration(token).before(Date())) return -1
                return 1
            }
        } catch (e: ExpiredJwtException) {
            e.printStackTrace()
            log.warn("Request to parse expired JWT : {} failed : {}", token, e.message)
            return -1
        } catch (e: UnsupportedJwtException) {
            log.warn("Request to parse unsupported JWT : {} failed : {}", token, e.message)
        } catch (e: MalformedJwtException) {
            log.warn("Request to parse invalid JWT : {} failed : {}", token, e.message)
        } catch (e: IllegalArgumentException) {
            log.warn("Request to parse empty or null JWT : {} failed : {}", token, e.message)
        } catch (e: SignatureException) {
            log.warn("JWT signature does not match locally computed signature JWT : {} failed : {}", token, e.message)
        } catch (e: Exception) {
            log.warn("JWT parse error : {} failed : {}", token, e.message)
        }
        return 0
    }
}
