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

    // 產生 JWT Token，用使用者的 username 來當成 subject
    fun generateToken(claims: Map<String, Any>, subject: String): String {
        return Jwts.builder()
            .claims(claims)
            .subject(subject)
            .issuedAt(Date(System.currentTimeMillis()))
            .expiration(Date(System.currentTimeMillis() + expiration * 1000))
            .signWith(getSigningKey())
            .compact()
    }

    // 從 JWT 取出裡面的 Subject
    fun extractSubject(token: String): String {
        return extractClaim(token) { it.subject }
    }

    // 把 properties 裡面的 JWT 密鑰，轉換成 Java 的 SecretKey (簽名密鑰)
    private fun getSigningKey(): SecretKey {
        val keyBytes = secret.toByteArray()
        return Keys.hmacShaKeyFor(keyBytes)
    }

    // 從 JWT 取出裡面的 Expiration (過期時間)
    fun extractExpiration(token: String): Date {
        return extractClaim(token) { it.expiration }
    }

    // 取出 JWT 特定的 Claim
    fun <T> extractClaim(token: String, key: String, clazz: Class<T>): T {
        val claims = extractAllClaims(token)
        return claims[key, clazz]
    }

    // 取出 JWT 的所有 Claims
    private fun extractAllClaims(token: String?): Claims {
        return Jwts.parser()
            .verifyWith(getSigningKey())
            .build()
            .parseSignedClaims(token)
            .payload
    }

    // JWT 有沒有過期
    private fun isTokenExpired(token: String): Boolean {
        return extractExpiration(token).before(Date())
    }

    // 取出 JWT 特定的 Claim
    private fun <T> extractClaim(token: String, claimsResolver: (Claims) -> T): T {
        val claims = extractAllClaims(token)
        return claimsResolver(claims)
    }
}
