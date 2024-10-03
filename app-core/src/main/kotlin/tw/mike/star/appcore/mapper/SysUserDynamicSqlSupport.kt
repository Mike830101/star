/*
 * Auto-generated file. Created by MyBatis Generator
 */
package tw.mike.star.appcore.mapper

import java.sql.JDBCType
import java.time.LocalDateTime
import java.util.UUID
import org.mybatis.dynamic.sql.AliasableSqlTable
import org.mybatis.dynamic.sql.util.kotlin.elements.column

object SysUserDynamicSqlSupport {
    val sysUser = SysUser()

    val uid = sysUser.uid

    val acc = sysUser.acc

    val password = sysUser.password

    val name = sysUser.name

    val telPhone = sysUser.telPhone

    val email = sysUser.email

    val roleId = sysUser.roleId

    val status = sysUser.status

    val createdTime = sysUser.createdTime

    val createdBy = sysUser.createdBy

    val updatedTime = sysUser.updatedTime

    val updatedBy = sysUser.updatedBy

    val lastLoginTime = sysUser.lastLoginTime

    class SysUser : AliasableSqlTable<SysUser>("sys_user", ::SysUser) {
        val uid = column<UUID>(name = "\"uid\"", jdbcType = JDBCType.OTHER, typeHandler = "tw.mike.star.appcore.handler.UUIDTypeHandler")

        val acc = column<String>(name = "acc", jdbcType = JDBCType.VARCHAR)

        val password = column<String>(name = "\"password\"", jdbcType = JDBCType.VARCHAR)

        val name = column<String>(name = "\"name\"", jdbcType = JDBCType.VARCHAR)

        val telPhone = column<String>(name = "tel_phone", jdbcType = JDBCType.VARCHAR)

        val email = column<String>(name = "email", jdbcType = JDBCType.VARCHAR)

        val roleId = column<UUID>(name = "role_id", jdbcType = JDBCType.OTHER, typeHandler = "tw.mike.star.appcore.handler.UUIDTypeHandler")

        val status = column<Int>(name = "\"status\"", jdbcType = JDBCType.INTEGER)

        val createdTime = column<LocalDateTime>(name = "created_time", jdbcType = JDBCType.TIMESTAMP)

        val createdBy = column<UUID>(name = "created_by", jdbcType = JDBCType.OTHER, typeHandler = "tw.mike.star.appcore.handler.UUIDTypeHandler")

        val updatedTime = column<LocalDateTime>(name = "updated_time", jdbcType = JDBCType.TIMESTAMP)

        val updatedBy = column<UUID>(name = "updated_by", jdbcType = JDBCType.OTHER, typeHandler = "tw.mike.star.appcore.handler.UUIDTypeHandler")

        val lastLoginTime = column<LocalDateTime>(name = "last_login_time", jdbcType = JDBCType.TIMESTAMP)
    }
}