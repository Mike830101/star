package tw.mike.star.appcore.config.security

import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.stereotype.Service
import tw.mike.star.appcore.entity.Role
import tw.mike.star.appcore.entity.SysUser
import tw.mike.star.appcore.repository.RoleRepository
import tw.mike.star.appcore.repository.UserRepository
import javax.management.relation.RoleNotFoundException

/**
 * spring 登入認證用
 */
@Service
class MyUserDetailsService(
    private val userRepository: UserRepository,
    private val roleRepository: RoleRepository
) : UserDetailsService {
    override fun loadUserByUsername(acc: String): UserDetails {
        val user = userRepository.findByAccount(acc,deleted = false)?:throw UsernameNotFoundException("User $acc not found")
        val role = roleRepository.findByUid(user.roleId!!)?:throw RoleNotFoundException("Role $user.roleId not found")
        return MyUserDetails(user,role)
    }
}

class MyUserDetails(private val user: SysUser,private val role: Role): UserDetails{
    override fun getAuthorities(): Collection<GrantedAuthority> = listOf(SimpleGrantedAuthority(role.code)) //權限腳色
    override fun getPassword(): String = user.password!!
    override fun getUsername(): String = user.acc!!
    override fun isAccountNonExpired(): Boolean = true  //true 表示帳戶未過期
    override fun isAccountNonLocked(): Boolean = true   //true 表示帳戶未被鎖定
    override fun isCredentialsNonExpired(): Boolean = true  //true 表示憑證有效
    override fun isEnabled(): Boolean = true    //true 表示帳號啟用

    override fun toString(): String {
        return "MyUserDetails(authorities=$authorities,username='${user.acc}', password='$password',isAccountNonExpired=$isAccountNonExpired,isAccountNonLocked=$isAccountNonLocked,isCredentialsNonExpired=$isCredentialsNonExpired,isEnabled=$isEnabled)"
    }
}