package tw.mike.star.appcore.config.security

import com.fasterxml.jackson.databind.ObjectMapper
import io.jsonwebtoken.ExpiredJwtException
import io.jsonwebtoken.security.SignatureException
import jakarta.servlet.FilterChain
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Component
import org.springframework.web.filter.OncePerRequestFilter
import tw.mike.star.appcore.service.JwtService
import tw.mike.star.appcore.utils.IdToken
import tw.mike.star.appcore.utils.SysCode
import tw.mike.star.appcore.utils.logger
import java.io.IOException
import java.io.Serializable


@Component
class JwtRequestFilter(
    private val jwtService: JwtService,
    private val objectMapper: ObjectMapper
) : OncePerRequestFilter() {
    private val log = logger()

    override fun doFilterInternal(request: HttpServletRequest,
        response: HttpServletResponse, filterChain: FilterChain) {

        log.debug("doFilterInternal 被執行")
        try {
            request.getHeader("Authorization")?.substringAfter("Bearer ")?.also {token->
                log.debug("doFilterInternal.Token: $token")
                val idToken: IdToken? = jwtService.verifyToken(token)
                if (idToken != null){
                    //權限腳色清單
                    val authorities = listOf(SimpleGrantedAuthority(idToken.roleName))
                    //完成身份驗證的時間戳
                    idToken.authTime = System.currentTimeMillis()

                    //如果JWT令牌有效，則創建一個UsernamePasswordAuthenticationToken
                    // 並將其設置到Spring Security的Security上下文中，以確保用戶已成功驗證。
                    val authentication: Authentication =
                        UsernamePasswordAuthenticationToken(idToken, null, authorities)
                    SecurityContextHolder.getContext().authentication = authentication
                    log.debug("jwt 驗證成功")
                }else{
                    log.debug("jwt 驗證失敗")
                    forbidden(response,SysCode._2102)
                    return
                }
            }
        } catch (e: ExpiredJwtException){
            log.debug("jwt 驗證異常: token is expired")
            forbidden(response,SysCode._2101)
            return
        } catch (e: SignatureException) {
            log.error("jwt 驗證異常: 簽名錯誤")
            forbidden(response,SysCode._2104)
            return
        } catch (e:Exception){
            e.printStackTrace()
            log.error("jwt 驗證異常: ${e.message}")
            forbidden(response,SysCode._2)
            return
        }

        filterChain.doFilter(request, response)
    }


    /**
     * 回覆 json 資料。
     *
     * @param response the HttpServletResponse to emit
     */
    fun forbidden(response: HttpServletResponse, sysCode: SysCode) {
        val dataMap: MutableMap<String, Serializable> = HashMap()
        dataMap["code"] = sysCode.code
        dataMap["message"] = sysCode.message

        // 輸出 json
        try {
            val json: String = objectMapper.writeValueAsString(dataMap)
            response.contentType = "application/json"
            response.characterEncoding = "UTF-8"
            response.status = HttpServletResponse.SC_FORBIDDEN
            response.writer.write(json)
        } catch (e: IOException) {
            log.error("Failed to write response", e)
        }
    }
}