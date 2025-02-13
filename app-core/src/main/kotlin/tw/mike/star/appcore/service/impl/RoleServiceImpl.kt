package tw.mike.star.appcore.service.impl

import org.mybatis.dynamic.sql.util.kotlin.GroupingCriteriaCollector.Companion.where
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import tw.mike.star.appcore.entity.Role
import tw.mike.star.appcore.exception.RoleException
import tw.mike.star.appcore.mapper.*
import tw.mike.star.appcore.model.role.*
import tw.mike.star.appcore.repository.RoleRepository
import tw.mike.star.appcore.repository.UserRepository
import tw.mike.star.appcore.service.BaseService
import tw.mike.star.appcore.service.RoleService
import tw.mike.star.appcore.utils.Paging
import tw.mike.star.appcore.utils.UUIdSimpleResp
import tw.mike.star.appcore.utils.ifNotEmpty
import java.util.*

@Service
class RoleServiceImpl @Autowired constructor(
    private val userRepository: UserRepository,
    private val roleRepository: RoleRepository,
    private val roleMapper: RoleMapper,
): RoleService ,BaseService(){

    /**
     * 角色-查詢單筆。
     * @param uid 角色鍵值
     * @exception RoleException 查無此角色
     */
    override fun getRole(uid: UUID): RoleGetResp {
        val tag = "getRole"
        val role = roleRepository.findByUid(uid)?: throw RoleException("查無此角色")
        return RoleGetResp().apply {
            this.uid = role.uid!!
            this.code = role.code!!
            this.name = role.name!!
            this.memo = role.memo!!
            this.enabled = role.enable!!
        }
    }

    /**
     * 角色-建立單筆
     * @exception RoleException 角色名稱&代碼已被使用
     *                          角色創建失敗
     * @exception RuntimeException 角色已被使用
     */
    override fun createRole(req: RoleCreateReq): UUIdSimpleResp {
        val tag = "createRole"

        val insertRole = Role().apply {
            uid = UUID.randomUUID()
            code = req.code
            name = req.name
            memo = req.memo
            enable = req.enabled
        }
        //防止異步執行
        synchronized(this){
            //判別角色名稱＆代碼是否可使用
            roleRepository.findByNameOrCode(req.name,req.code).ifNotEmpty {
                val msg = mutableSetOf<String>()
                it.forEach {oldRole->
                    if (oldRole.name == req.name) msg.add("名稱")
                    if (oldRole.code == req.code) msg.add("代碼")
                }
                if (msg.isNotEmpty()) {
                    throw RoleException(msg.joinToString(separator = "&", prefix = "角色", postfix = "已被使用"))
                }
            }

            if (roleRepository.insertSelective(insertRole)){
                return UUIdSimpleResp(insertRole.uid!!, insertRole.name)
            }else{
                throw RoleException("角色創建失敗")
            }
        }
    }

    /**
     * 角色-更新單筆
     * @exception RoleException 查無此角色
     *                          更新角色失敗
     */
    override fun updateRole(req: RoleUpdateReq): UUIdSimpleResp {
        val tag = "updateRole"
        val oldRole = roleRepository.findByUid(req.uid,null)?: throw RoleException("查無此角色")

        val updateRole = Role().apply {
            uid = oldRole.uid
            code = req.code
            name = req.name
            memo = req.memo
            enable = req.enabled
        }

        //防止異步執行
        synchronized(this) {
            //判別角色名稱＆代碼是否可使用
            roleRepository.findByNameOrCode(req.name,req.code).ifNotEmpty {
                val msg = mutableSetOf<String>()
                it.forEach {oldRole->
                    if (oldRole.uid != req.uid) { //同id情況略過
                        if (oldRole.name == req.name) msg.add("名稱")
                        if (oldRole.code == req.code) msg.add("代碼")
                    }
                }
                if (msg.isNotEmpty()) {
                    throw RoleException(msg.joinToString(separator = "&", prefix = "角色", postfix = "已被使用"))
                }
            }

            if (roleRepository.updateByPrimaryKeySelective(updateRole)) {
                return UUIdSimpleResp(updateRole.uid!!, updateRole.name)
            } else {
                throw RoleException("更新角色失敗")
            }
        }
    }

    /**
     * 角色-刪除單筆。
     * @param uid 角色鍵值
     * @exception RoleException 查無此角色
     *                          尚有?名帳號使用該角色,故無法刪除
     *                          刪除角色失敗
     */
    override fun removeRole(uid: UUID): UUIdSimpleResp {
        val tag = "removeRole"

        val oldRole = roleRepository.findByUid(uid)?: throw RoleException("查無此角色")
        //沒有被帳號使用才可被刪除
        val userCount = userRepository.findByRoleUid(oldRole.uid!!,null).size
        if (userCount > 0) throw RoleException("尚有${userCount}名帳號使用該角色,故無法刪除")


        if (roleRepository.removeByUid(uid)){
            return UUIdSimpleResp(oldRole.uid!!, oldRole.name)
        }else{
            throw RoleException("刪除角色失敗")
        }
    }

    /**
     * 角色-查詢多筆。
     */
    override fun searchRoleList(req: RoleListReq, paging: Paging): List<RoleListResp> {
        val tag = "searchRoleList"

        //搜尋條件
        val whereFun = where {
            RoleDynamicSqlSupport.name isLike "%${req.name?:""}%"
            and { RoleDynamicSqlSupport.role.enable isEqualToWhenPresent req.enabled }
        }

        //帳號清單
        val roleList:List<Role> = roleMapper.select {
            where(whereFun)
            paging.offset?.let { offset(it) }
            paging.limit?.let { limit(it) }
            orderBy(RoleDynamicSqlSupport.uid.descending())
        }

        //設定總頁數
        paging.total = roleMapper.count {
            where(whereFun)
        }

        return roleList.map {
            RoleListResp().apply {
                uid = it.uid!!
                code = it.code!!
                name = it.name!!
                memo = it.memo
                enabled = it.enable!!
            }
        }
    }
}