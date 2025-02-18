package tw.mike.star.appcore.service

import io.jsonwebtoken.*
import io.jsonwebtoken.security.SignatureException
import org.springframework.stereotype.Service
import tw.mike.star.appcore.entity.Role
import tw.mike.star.appcore.entity.SysUser
import tw.mike.star.appcore.utils.IdToken
import tw.mike.star.appcore.utils.JwtUtils
import tw.mike.star.appcore.utils.logger
import java.util.*


@Service
class JwtService(val jwtUtils: JwtUtils) {
    private final val log = logger()

    /**
     * 產生token
     * @param user 使用者資訊
     * @param role 腳色資訊
     * @return jwt.token
     *
     */
    fun generateToken(user: SysUser,role: Role): String {
        val claims: MutableMap<String, Any> = HashMap()
        claims["iss"] = "tw.mike.star"
        claims["acc"] = user.acc!!
        claims["name"] = user.name!!
        claims["email"] = user.email!!
        claims["mobile"] = user.telPhone!!
        claims["roleId"] = role.uid!!.toString()
        claims["roleName"] = role.name!!

        val token: String = jwtUtils.generateToken(claims, user.uid!!.toString())
        return token
    }

    /**
     * 驗證 Token 是否正確
     * @param token 用戶提交的 Token
     * @return 有效:IdToken , 無效:null
     * @exception ExpiredJwtException 過期
     */
    @Throws(ExpiredJwtException::class)
    fun verifyToken(token: String): IdToken? {
        try {
            val claims = jwtUtils.extractAllClaims(token)
            log.debug("JWT verifyToken ,claims: {}", claims)
            val iss = claims["iss"] as String
            val sub = claims["sub"] as String
            //過期判斷
            val exp = jwtUtils.extractExpiration(token).time
            val iat = claims.issuedAt.time
            val acc = claims["acc"] as String
            val name = claims["name"] as String
            val email = claims["email"] as String
            val mobile = claims["mobile"] as String
            val roleId = claims["roleId"] as String
            val roleName = claims["roleName"] as String

            val idToken = IdToken(iss,0,UUID.fromString(sub),exp,iat,acc,
                name,email,mobile,UUID.fromString(roleId),roleName)

            return idToken
        } catch (e: ExpiredJwtException) {
            throw e
        } catch (e: UnsupportedJwtException) {
            log.warn("Request to parse unsupported JWT : {} failed : {}", token, e.message)
        } catch (e: MalformedJwtException) {
            log.warn("Request to parse invalid JWT : {} failed : {}", token, e.message)
        } catch (e: IllegalArgumentException) {
            e.printStackTrace()
            log.warn("Request to parse empty or null JWT : {} failed : {}", token, e.message)
        } catch (e: SignatureException) {
            log.warn("JWT signature does not match locally computed signature JWT : {} failed : {}", token, e.message)
        } catch (e: Exception) {
            log.warn("JWT parse error : {} failed : {}", token, e.message)
        }
        return null
    }
}
