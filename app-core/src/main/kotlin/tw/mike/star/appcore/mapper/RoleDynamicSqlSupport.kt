/*
 * Auto-generated file. Created by MyBatis Generator
 */
package tw.mike.star.appcore.mapper

import java.sql.JDBCType
import java.util.UUID
import org.mybatis.dynamic.sql.AliasableSqlTable
import org.mybatis.dynamic.sql.util.kotlin.elements.column

object RoleDynamicSqlSupport {
    val role = Role()

    val uid = role.uid

    val name = role.name

    val enable = role.enable

    val code = role.code

    val memo = role.memo

    class Role : AliasableSqlTable<Role>("role", ::Role) {
        val uid = column<UUID>(name = "\"uid\"", jdbcType = JDBCType.OTHER, typeHandler = "tw.mike.star.appcore.handler.UUIDTypeHandler")

        val name = column<String>(name = "\"name\"", jdbcType = JDBCType.VARCHAR)

        val enable = column<Boolean>(name = "\"enable\"", jdbcType = JDBCType.BIT)

        val code = column<String>(name = "code", jdbcType = JDBCType.VARCHAR)

        val memo = column<String>(name = "memo", jdbcType = JDBCType.VARCHAR)
    }
}