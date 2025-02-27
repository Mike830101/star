package tw.mike.star.appcore

import org.springframework.boot.test.context.SpringBootTest
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.test.context.TestConstructor
import tw.mike.star.appcore.mapper.*
import tw.mike.star.appcore.service.UserService
import java.util.*
import kotlin.test.Test

@SpringBootTest
@TestConstructor(autowireMode = TestConstructor.AutowireMode.ALL)
class DBConnectTest(
    private val sysUserMapper: SysUserMapper,
    private val roleMapper: RoleMapper,
    private val userService: UserService,
) {
    val passwordEncoder: PasswordEncoder = BCryptPasswordEncoder()

    @Test
    fun testSelect() {
        println("----- selectAll method test ------")
//        val userList = sysUserMapper.select {
//            where { sysUser.uid.isNull() }
//        }
//        userList.forEach { println(it) }

        roleMapper.select {
            where {
                RoleDynamicSqlSupport.role.enable isEqualTo true
            }
        }.apply {
            println("roleList:$this")
        }
    }

    @Test
    fun searchUser(){
        sysUserMapper.select {}.apply {
            println("user:$this")
        }
    }

    @Test
    fun passwordEncoder() {
        val rawPassword = "1234" // 明文密碼
        val hashedPassword = passwordEncoder.encode(rawPassword) // 進行加密
        println("雜湊後密碼: $hashedPassword")


        // 驗證密碼是否匹配
        val pwd = "$2a$10\$Nt0i2GS9Jc/zyvhJ9UUHlekvGgSrbrbuLDMvleho5jKEFtXcC9xnq"
        println("比對雜湊碼: $pwd")
        val isMatch = passwordEncoder.matches(pwd, hashedPassword)
        println("密碼匹配: $isMatch")
    }

    @Test
    fun userServiceTest(){
        val uid = UUID.fromString("90647edf-e9cc-11ef-a30a-7872643982d0")
        val oldData = userService.getUser(uid,)
        userService.removeUser(uid)
        val newData = userService.getUser(uid)
        println("oldData:$oldData")
        println("newData:$newData")
    }
}