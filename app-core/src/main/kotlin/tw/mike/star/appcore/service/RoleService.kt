package tw.mike.star.appcore.service

import tw.mike.star.appcore.model.role.*
import tw.mike.star.appcore.utils.Paging
import tw.mike.star.appcore.utils.UUIdSimpleResp
import java.util.*

/**
 * 角色-管理API
 */
interface RoleService {

    /**
     * 角色-查詢單筆。
     * @param uid 角色鍵值
     */
    fun getRole(uid: UUID): RoleGetResp

    /**
     * 角色-建立單筆
     */
    fun createRole(req: RoleCreateReq): UUIdSimpleResp

    /**
     * 角色-更新單筆
     */
    fun updateRole(req: RoleUpdateReq): UUIdSimpleResp

    /**
     * 角色-刪除單筆。
     * @param uid 角色鍵值
     */
    fun removeRole(uid: UUID): UUIdSimpleResp

    /**
     * 角色-查詢多筆。
     */
    fun searchRoleList(req: RoleListReq, paging: Paging): List<RoleListResp>
}