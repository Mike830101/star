package tw.mike.star.appcore

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import tw.mike.star.appcore.mapper.*
import kotlin.test.Test

@SpringBootTest
class DBConnectTest @Autowired constructor(
    private val sysUserMapper: SysUserMapper,
    private val roleMapper: RoleMapper
) {

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
}