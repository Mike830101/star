package tw.mike.star.appcore.mapper.ext

import org.apache.ibatis.annotations.*
import org.apache.ibatis.type.JdbcType
import tw.mike.star.appcore.handler.UUIDTypeHandler
import tw.mike.star.appcore.model.user.UserGetResp
import tw.mike.star.appcore.model.user.UserListResp
import java.util.*

@Mapper
interface UserMapperExt {

    /**
     * 依健值單筆搜尋帳號
     * @see UserSqlProvider.findByUid
     */
    @Results(value = [
        Result(column="uid", property="uid", typeHandler= UUIDTypeHandler::class, jdbcType= JdbcType.OTHER, id=true),
        Result(column="account", property="account", jdbcType= JdbcType.VARCHAR),
        Result(column="user_name", property="name", jdbcType= JdbcType.VARCHAR),
        Result(column="password", property="password", jdbcType= JdbcType.VARCHAR),
        Result(column="tel_phone", property="telPhone", jdbcType= JdbcType.VARCHAR),
        Result(column="email", property="email", jdbcType= JdbcType.VARCHAR),
        Result(column="status", property="status", jdbcType= JdbcType.INTEGER),
        Result(column="last_login_time", property="lastLoginTime", jdbcType= JdbcType.TIMESTAMP),
        Result(column="updated_by", property="updatedBy", typeHandler= UUIDTypeHandler::class, jdbcType= JdbcType.OTHER),
        Result(column="updated_time", property="updatedTime", jdbcType= JdbcType.TIMESTAMP),
        Result(column="updated_user_name", property="updatedUserName", jdbcType= JdbcType.VARCHAR),
        Result(column="role_id", property="roleId", typeHandler= UUIDTypeHandler::class, jdbcType= JdbcType.OTHER),
        Result(column="role_code", property="roleCode", jdbcType= JdbcType.VARCHAR),
        Result(column="role_name", property="roleName", jdbcType= JdbcType.VARCHAR),
    ])
    @SelectProvider(type= UserSqlProvider::class, method="findByUid")
    fun findByUid(uid: UUID): UserGetResp?


    /**
     * 帳號-查詢多筆
     * @param account 帳號。支援模糊搜尋
     * @param name 姓名。支援模糊搜尋。
     * @param roleId 角色鍵值
     * @param status 停用或啟用。停用0、啟用1。
     * @see UserSqlProvider.searchUserList
     */
    @Results(value = [
        Result(column="uid", property="uid", typeHandler= UUIDTypeHandler::class, jdbcType= JdbcType.OTHER, id=true),
        Result(column="account", property="account", jdbcType= JdbcType.VARCHAR),
        Result(column="user_name", property="name", jdbcType= JdbcType.VARCHAR),
        Result(column="status", property="status", jdbcType= JdbcType.INTEGER),
        Result(column="last_login_time", property="lastLoginTime", jdbcType= JdbcType.TIMESTAMP),
        Result(column="role_name", property="roleName", jdbcType= JdbcType.VARCHAR),
    ])
    @SelectProvider(type= UserSqlProvider::class, method="searchUserList")
    fun searchUserList(@Param("account") account: String?,
                       @Param("name") name: String?,
                       @Param("roleId") roleId: UUID?,
                       @Param("status") status: Int?): List<UserListResp>
}