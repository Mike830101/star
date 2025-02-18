package tw.mike.star.appcore.config.security

import io.jsonwebtoken.ExpiredJwtException
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
import tw.mike.star.appcore.utils.logger


@Component
class JwtRequestFilter(
    private val jwtService: JwtService,
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
                }
            }
        }catch (e: ExpiredJwtException){
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED,"Token is expired")
            log.error("jwt 驗證異常: token is expired")
            return
        }catch (e:Exception){
            e.printStackTrace()
            log.error("jwt 驗證異常: ${e.message}")
        }

        filterChain.doFilter(request, response)
    }
}