package tw.mike.star.appcore.service.impl

import lombok.AllArgsConstructor
import org.mybatis.dynamic.sql.util.kotlin.GroupingCriteriaCollector.Companion.where
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import tw.mike.star.appcore.entity.Role
import tw.mike.star.appcore.entity.SysUser
import tw.mike.star.appcore.exception.AuthException
import tw.mike.star.appcore.exception.UserException
import tw.mike.star.appcore.mapper.*
import tw.mike.star.appcore.mapper.ext.UserMapperExt
import tw.mike.star.appcore.model.user.*
import tw.mike.star.appcore.repository.RoleRepository
import tw.mike.star.appcore.repository.UserRepository
import tw.mike.star.appcore.service.BaseService
import tw.mike.star.appcore.service.UserService
import tw.mike.star.appcore.utils.*
import java.time.LocalDateTime
import java.util.*

/**
 * 帳號管理API
 */
@Service
@AllArgsConstructor
class UserServiceImpl @Autowired constructor(
    private val userMapperExt: UserMapperExt,
    private val userRepository: UserRepository,
    private val roleRepository: RoleRepository,
    private val userMapper: SysUserMapper
) : UserService, BaseService() {

    /**
     * 帳號-查詢單筆。
     * @param uid 賬號鍵值
     * @exception UserException 查無此帳號
     *                          找不到對應的角色
     */
    override fun getUser(uid: UUID): UserGetResp {
        val tag = "getUser"
        log.debug("$tag,uid:$uid")

        return userMapperExt.findByUid(uid)
//        val user:SysUser = userRepository.findByUid(uid)?: throw UserException("查無此帳號")
//        val role:Role = roleRepository.findByUid(user.roleId!!,null)?: throw UserException("找不到對應的角色")
//
//        return UserGetResp(roleId = role.uid!!, roleCode = role.code!!, roleName = role.name!!, account = user.acc!!,
//            password = user.password!!, status = user.status!!, uid = user.uid!!).apply {
//            name = user.name!!
//            telPhone = user.telPhone
//            email = user.email
//            updatedBy = user.updatedBy
//            updatedUserName = user.name
//            updatedTime = user.updatedTime
//        }
    }

    /**
     * 帳號-建立單筆
     * @exception UserException 查無此角色
     *                          帳號已被使用
     *                          新增資料庫失敗
     * @exception AuthException 無登入資訊
     */
    override fun createUser(req: UserCreateReq): UUIdSimpleResp {
        val tag = "createUser"
        log.debug("$tag,req:$req")

        val idToken = getIdToken()?: throw AuthException("無登入資訊")

        val insertUser = SysUser(
            uid = SequenceUtils.sequenceUUID(),
            acc = req.account,
            password = req.password,
            name = req.name,
            telPhone = req.telPhone,
            email = req.email,
            roleId = req.roleId,
            status = req.status,
            createdTime = LocalDateTime.now(),
            createdBy = idToken.sub,
            updatedTime = LocalDateTime.now(),
            updatedBy = idToken.sub,
        )

        //防止異步執行
        synchronized(this){
            // 檢查角色
            roleRepository.findByUid(req.roleId)?: throw UserException("查無此角色")
            // 檢查帳號
            userRepository.findByAccount(req.account,null) ifNotNull {throw UserException("帳號已被使用")}
        }

        if (userRepository.insertSelective(insertUser)){
            return UUIdSimpleResp(insertUser.uid!!,insertUser.acc)
        }else{
            throw UserException("新增資料庫失敗")
        }
    }

    /**
     * 帳號-更新單筆
     * @exception UserException 查無此角色
     *                          查無此角色
     *                          更新帳號失敗
     * @exception AuthException 無登入資訊
     */
    override fun updateUser(req: UserUpdateReq): UUIdSimpleResp {
        val tag = "updateUser"
        log.debug("$tag,req:$req")

        val idToken = getIdToken()?: throw AuthException("無登入資訊")

        // 檢查帳號
        val oldUser = userRepository.findByUid(req.uid)?: throw UserException("查無此帳號")
        // 檢查角色
        roleRepository.findByUid(req.roleId)?: throw UserException("查無此角色")

        val updateUser = oldUser.apply {
            uid = req.uid
            name = req.name
            telPhone = req.telPhone
            email = req.email
            roleId = req.roleId
            status = req.status
            password = req.password
            updatedTime = LocalDateTime.now()
            updatedBy = idToken.sub
        }

        if (userRepository.updateByPrimaryKeySelective(updateUser)){
            return UUIdSimpleResp(updateUser.uid!!,oldUser.acc)
        }else{
            throw UserException("更新帳號失敗")
        }
    }

    /**
     * 帳號-刪除單筆。供管理員刪除主帳號。
     * 刪除時標記為已刪除，但資料仍保存於資料庫中。
     * @param uid 賬號鍵值
     * @exception UserException 查無此角色
     *                          刪除帳號失敗
     * @exception AuthException 無登入資訊
     */
    override fun removeUser(uid: UUID): UUIdSimpleResp {
        val tag = "removeUser"
        log.debug("$tag,uid:$uid")

        val idToken = getIdToken()?: throw AuthException("無登入資訊")

        // 檢查帳號
        val oldUser = userRepository.findByUid(uid)?: throw UserException("查無此帳號")

        val updateUser = SysUser(
            uid = oldUser.uid,
            status = 0,
            deleted = true,
            updatedTime = LocalDateTime.now(),
            updatedBy = idToken.sub,
            deletedBy = idToken.sub,
            deletedTime = LocalDateTime.now(),
        )

        if (userRepository.updateByPrimaryKeySelective(updateUser)){
            return UUIdSimpleResp(oldUser.uid!!,oldUser.acc)
        }else{
            throw UserException("刪除帳號失敗")
        }
    }

    /**
     * 帳號-查詢多筆。供管理員查詢主帳號清單。
     */
    override fun searchUserList(req: UserListReq, paging: Paging): List<UserListResp> {
        val tag = "searchUserList"
        log.debug("$tag,req:$req,paging:$paging")

        //搜尋條件
        val whereFun = where {
            SysUserDynamicSqlSupport.acc isLike "%${req.account?:""}%"
            and { SysUserDynamicSqlSupport.name isLike "%${req.name?:""}%" }
            and { SysUserDynamicSqlSupport.roleId isEqualToWhenPresent req.roleId }
            and { SysUserDynamicSqlSupport.status isEqualToWhenPresent req.status }
        }

        //帳號清單
        val userList:List<SysUser> = userMapper.select {
            where(whereFun)
            paging.offset?.let { offset(it) }
            paging.limit?.let { limit(it) }
            orderBy(SysUserDynamicSqlSupport.updatedTime.descending(), SysUserDynamicSqlSupport.createdTime.descending())
        }
        //有的角色清單 (依照角色id做分類)
        val roleIdList:List<UUID> = userList.groupBy { it.roleId!! }.map { it.key }
        //角色清單
        val roleList:List<Role> = roleRepository.findByUidList(roleIdList)
        // 依照角色id做分類並對應相對名稱 (key:角色健值,value:角色名稱)
        val roleNameMap:Map<UUID,String> = roleList.associate { it.uid!! to it.name!! }

        //整合回傳的資料
        val respList:List<UserListResp> = userList.map {
            UserListResp(
                id = it.roleId!!,
                account = it.acc!!,
                name = it.name!!,
                roleName = roleNameMap[it.roleId]!!,
                status = it.status!!,
                lastLoginTime = it.lastLoginTime,
            )
        }

        //設定總頁數
        paging.total = userMapper.count {
            where(whereFun)
        }

        return respList
    }

    /**
     * 帳號-查詢多筆並匯出excel。
     */
    override fun exportUserList(req: UserListReq): List<UserListResp> {
        TODO("Not yet implemented")
    }
}