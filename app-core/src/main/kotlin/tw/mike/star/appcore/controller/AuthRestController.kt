package tw.mike.star.appcore.controller

import jakarta.validation.Valid
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.web.bind.annotation.*
import reactor.core.publisher.Mono
import tw.mike.star.appcore.model.LoginReq
import tw.mike.star.appcore.repository.UserRepository
import tw.mike.star.appcore.service.JwtService
import tw.mike.star.appcore.utils.SysCode

@RestController
@RequestMapping("/api/auth/v1")
class AuthRestController(
    private val userRepository: UserRepository,
    private val jwtService: JwtService,
    private val authenticationManager: AuthenticationManager,
): BaseRestController() {
    /**
     * MD-1-Role-Create
     * 登入
     */
    @PostMapping("/login")
    fun createRole(@Valid @RequestBody req: LoginReq): Mono<*> {
        val tag = "login"
        log.debug("{},req:{}", tag, req)
        try {
            val passwordEncoder = BCryptPasswordEncoder()

            authenticationManager.authenticate(UsernamePasswordAuthenticationToken(req.acc,req.pwd))

            //與資料庫的帳密比對
            val user = userRepository.findByAccount(req.acc,false)?: return badRequest(SysCode._2002,"查無此帳號")
            if (!passwordEncoder.matches(req.pwd, user.password)) return badRequest(SysCode._2002)

            val token = jwtService.generateToken(user.acc!!)
            return ok(mapOf("token" to token))
        }catch (e: Exception) {
            e.printStackTrace()
            return badRequest(SysCode._3)
        }
    }
}