package tw.mike.star.appcore.utils

import io.jsonwebtoken.Claims
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.security.Keys
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component
import java.util.*
import javax.crypto.SecretKey

@Component
class JwtUtils {

    // JWT 過期時間(秒)
    @Value("\${app.jwt.expiration}")
    private var expiration: Long = 0

    // JWT 密鑰
    @Value("\${app.jwt.secret}")
    private lateinit var secret: String

    private val signingKey :SecretKey by lazy {
        Keys.hmacShaKeyFor(secret.toByteArray())
    }

    /**
     *  產生 JWT Token
     * @param claims 負載內容
     * @param subject 標題
     * @return jwt token
     */
    fun generateToken(claims: Map<String, Any>, subject: String? = null): String {
        return Jwts.builder()
            .claims(claims)
            .id(SequenceUtils.sequenceUUID().toString()) //JWT ID，唯一標識符
            .subject(subject)
            .issuedAt(Date(System.currentTimeMillis()))
            .expiration(Date(System.currentTimeMillis() + expiration * 1000))
            .signWith(signingKey)
            .compact()
    }

    /**
     * 取得令牌 subject
     * @param token 令牌
     */
    fun extractSubject(token: String): String {
        return extractClaim(token) { it.subject }
    }

    /**
     * 取得令牌過期時間
     * @param token 令牌
     */
    fun extractExpiration(token: String): Date {
        return extractClaim(token) { it.expiration }
    }

    /**
     * 取得令牌發放時間
     * @param token 令牌
     */
    fun issuedDate(token: String): Date {
        return extractClaim(token) {it.issuedAt}
    }

    /**
     * 取出 JWT 特定的 Claim
     * @param token 令牌
     * @param key 鍵值
     * @param clazz 返回類型
     */
    fun <T> extractClaim(token: String, key: String, clazz: Class<T>): T {
        val claims = extractAllClaims(token)
        return claims[key, clazz]
    }

    /**
     * 取出 JWT 所有 的 Claim
     * @param token 令牌
     */
    fun extractAllClaims(token: String): Claims {
        return Jwts.parser()
            .verifyWith(signingKey)
            .build()
            .parseSignedClaims(token)
            .payload
    }

    /**
     * JWT 有沒有過期
     * @param token 令牌
     */
    fun isTokenExpired(token: String): Boolean {
        return extractExpiration(token).before(Date())
    }

    /**
     * 取出 JWT 特定的 Claim
     * @param token 令牌
     * @param claimsResolver 鍵值方法
     */
    private fun <T> extractClaim(token: String, claimsResolver: (Claims) -> T): T {
        val claims = extractAllClaims(token)
        return claimsResolver(claims)
    }
}
