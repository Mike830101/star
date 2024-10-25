package tw.mike.star.appcore.mapper.ext

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
}