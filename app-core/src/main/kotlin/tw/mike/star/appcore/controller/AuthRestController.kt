package tw.mike.star.appcore.controller

import jakarta.servlet.http.HttpServletRequest
import jakarta.validation.Valid
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.BadCredentialsException
import org.springframework.security.authentication.DisabledException
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.web.bind.annotation.*
import reactor.core.publisher.Mono
import tw.mike.star.appcore.config.security.MyUserDetails
import tw.mike.star.appcore.model.LoginReq
import tw.mike.star.appcore.service.JwtService
import tw.mike.star.appcore.utils.SysCode


@RestController
@RequestMapping("/api/auth/v1")
class AuthRestController(
    private val jwtService: JwtService,
    private val authenticationManager: AuthenticationManager,
): BaseRestController() {

    /**
     * 登入
     */
    @PostMapping("/login")
    fun login(@Valid @RequestBody req: LoginReq): Mono<*> {
        val tag = "login"
        log.debug("{},req:{}", tag, req)

        try {
            // 嘗試驗證使用者帳密
            val authentication: Authentication = authenticationManager.authenticate(
                UsernamePasswordAuthenticationToken(req.acc, req.pwd)
            )

            // 取得用戶資訊
            val myUserDetails: MyUserDetails = authentication.principal as MyUserDetails
            val user = myUserDetails.user
            val role = myUserDetails.role

            // 產生 JWT Token
            val jwtToken = jwtService.generateToken(user,role)

            return ok(mapOf("token" to jwtToken))
        }catch (e: BadCredentialsException) {
            return badRequest(SysCode._2002)
        }catch (e: DisabledException) {
            return badRequest(SysCode._2001)
        }catch (e: Exception) {
            e.printStackTrace()
            return badRequest(SysCode._3)
        }
    }

    /**
     * 登出
     */
    @GetMapping("/logout")
    fun logout(request: HttpServletRequest?): Mono<*> {
        val tag = "logout"
        log.debug("{},request:{}", tag, request)

        // 清除 SecurityContext 中的認證信息
        SecurityContextHolder.clearContext()

        // 如果使用了 session，可以使其無效
         request?.getSession(false)?.invalidate()
        return ok()
    }
}