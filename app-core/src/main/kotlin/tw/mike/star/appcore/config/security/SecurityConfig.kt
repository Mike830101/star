package tw.mike.star.appcore.config.security

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.AuthenticationProvider
import org.springframework.security.authentication.ProviderManager
import org.springframework.security.authentication.dao.DaoAuthenticationProvider
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.web.SecurityFilterChain
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter
import org.springframework.web.cors.CorsConfiguration
import org.springframework.web.cors.CorsConfigurationSource
import org.springframework.web.cors.UrlBasedCorsConfigurationSource
import org.springframework.web.servlet.View
import tw.mike.star.appcore.utils.logger


@Configuration
@EnableWebSecurity
class SecurityConfig(
    private val myUserDetailsService: MyUserDetailsService,
    private val jwtRequestFilter: JwtRequestFilter
){
    private val log = logger()

    @Bean
    fun securityFilterChain(http: HttpSecurity, error: View) :SecurityFilterChain{
        http
            .csrf { it.disable() }
            .cors {  }
            .authorizeHttpRequests {
                it.requestMatchers("/api/auth/**","/error").permitAll()
                    .requestMatchers("/api/role/v1/list").hasAnyAuthority("Admin")
                it.anyRequest().authenticated()
            }
//            .securityContext { it.requireExplicitSave(false) } //關閉session
            .sessionManagement { it.sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED) }  // 必要時創建 Session
//            .authenticationProvider(authenticationProvider())
            .authenticationManager (authenticationManager())
            .addFilterAt(jwtRequestFilter, UsernamePasswordAuthenticationFilter::class.java)
        return http.build()
    }

    /**
     * 執行身份驗證的關鍵組件
     */
    @Bean
    fun authenticationProvider(): AuthenticationProvider {
        val authProvider = DaoAuthenticationProvider()
        authProvider.setUserDetailsService(myUserDetailsService)
        authProvider.setPasswordEncoder(passwordEncoder())
        return authProvider
    }

    /**
     * 管理身份驗證的組件
     */
    @Bean
    fun authenticationManager(): AuthenticationManager {
        return ProviderManager(authenticationProvider())
    }

    /**
     * 以便將用戶密碼進行安全的雜湊存儲
     */
    @Bean
    fun passwordEncoder(): PasswordEncoder {
        return BCryptPasswordEncoder()
    }

    /**
     * 跨域設定
     */
    @Bean
    fun corsConfigurationSource(): CorsConfigurationSource {
        val configuration = CorsConfiguration()
        configuration.allowedOrigins = listOf("*")  //允許來源網址
        configuration.allowedMethods = listOf("*")
        configuration.allowedHeaders = listOf("*")
        configuration.exposedHeaders = mutableListOf("Authorization", "Content-Type")
        val source = UrlBasedCorsConfigurationSource()

        source.registerCorsConfiguration("/**", configuration)
        return source
    }
}