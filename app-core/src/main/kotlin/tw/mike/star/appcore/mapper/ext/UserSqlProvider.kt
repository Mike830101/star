package tw.mike.star.appcore.mapper.ext

import org.apache.ibatis.annotations.Param
import org.apache.ibatis.jdbc.SQL
import java.util.*

class UserSqlProvider {
    /**
     * 單筆帳號搜尋
     * @return UserGetResp
     */
    fun findByUid(uid: UUID) = """
        select su.uid,su.acc as account,su.name as user_name,su.password,su.tel_phone,su.email,su.status,su.last_login_time,r.name as role_name,
               su.updated_by,su.updated_time, (select su_inner.name from sys_user su_inner where su_inner.uid = su.updated_by) as updated_user_name,
               r.uid as role_id,r.code as role_code
        from sys_user su
        left join public.role r on su.role_id = r.uid
        where su.uid = '$uid'
    """.trimIndent()

    /**
     * 帳號-查詢多筆
     * @param account 帳號。支援模糊搜尋
     * @param name 姓名。支援模糊搜尋。
     * @param roleId 角色鍵值
     * @param status 停用或啟用。停用0、啟用1。
     * @return List<UserListResp>
     */
    fun searchUserList(@Param("account") account: String?,
                       @Param("name") name: String?,
                       @Param("roleId") roleId: UUID?,
                       @Param("status") status: Int?):String{
        val sql = SQL()
        sql.SELECT("su.uid,su.acc as account,su.name as user_name,su.status,su.last_login_time")
        sql.SELECT("r.name as role_name")
        sql.FROM("sys_user su")
        sql.LEFT_OUTER_JOIN("public.role r on su.role_id = r.uid")

        //非空值才加入條件
        account?.let { sql.WHERE("su.acc like concat ('%', #{account}, '%')") }
        name?.let { sql.WHERE("su.name like concat ('%', #{name}, '%')") }
        roleId?.let { sql.WHERE("su.role_id = '$roleId'") }
        status?.let { sql.WHERE("su.status = #{status}") }

        return sql.toString()
    }
}