package tw.mike.star.appcore.controller

import jakarta.validation.Valid
import org.springframework.web.bind.annotation.*
import reactor.core.publisher.Mono
import tw.mike.star.appcore.exception.RoleException
import tw.mike.star.appcore.model.role.*
import tw.mike.star.appcore.service.RoleService
import tw.mike.star.appcore.utils.Paging
import tw.mike.star.appcore.utils.SysCode
import tw.mike.star.appcore.utils.UUIdSimpleResp
import java.util.*

@RestController
@RequestMapping("/api/role/v1")
class RoleRestController(
    private val roleService: RoleService
): BaseRestController() {

    /**
     * MD-1-Role-Get
     * 角色-查詢單筆。
     * @param uid 角色健值
     * @see RoleGetResp return
     */
    @GetMapping
    fun getRole(@RequestParam(name = "uid") uid: UUID): Mono<*> {
        val tag = "getRole"
        log.debug("{},uid:{}", tag, uid)

        val resp: RoleGetResp = roleService.getRole(uid)?: return badRequest(SysCode._4102)
        return ok(resp)
    }

    /**
     * MD-1-Role-Create
     * 角色-新增單筆
     * @see UUIdSimpleResp return
     */
    @PostMapping("/create")
    fun createRole(@Valid @RequestBody req: RoleCreateReq): Mono<*> {
        val tag = "createRole"
        log.debug("{},req:{}", tag, req)
        try {
            val resp: UUIdSimpleResp = roleService.createRole(req)
            return ok(resp)
        }catch (ue: RoleException){
            return badRequest(SysCode._4001, ue.message)
        }
    }

    /**
     * MD-1-Role-Update
     * 角色-更新單筆。
     * @see UUIdSimpleResp return
     */
    @PostMapping("/update")
    fun updateRole(@Valid @RequestBody req: RoleUpdateReq): Mono<*> {
        val tag = "updateRole"
        log.debug("{},req:{}", tag, req)
        try {
            val resp: UUIdSimpleResp = roleService.updateRole(req)
            return ok(resp)
        }catch (ue: RoleException){
            return badRequest(SysCode._4001, ue.message)
        }
    }

    /**
     * MD-1-Role-Remove
     * 角色-刪除單筆。
     * 必須確認角色沒有被帳號使用才可被刪除。
     * @param uid 角色健值
     * @see UUIdSimpleResp return
     */
    @GetMapping("/remove")
    fun removeRole(@RequestParam(name = "uid") uid: UUID): Mono<*> {
        val tag = "removeRole"
        log.debug("{},uid:{}", tag, uid)
        try {
            val resp: UUIdSimpleResp = roleService.removeRole(uid)
            return ok(resp)
        }catch (ue: RoleException){
            return badRequest(SysCode._4001, ue.message)
        }
    }

    /**
     * MD-1-Role-List
     * 角色-查詢多筆
     * @param limit 取得比數
     * @param offset 跳過比數
     * @see RoleListResp return
     */
    @PostMapping("/list")
    fun selectRoleList(@Valid @RequestBody req: RoleListReq,
                       @RequestParam(name = "limit", required = false) limit:Long?,
                       @RequestParam(name = "offset", required = false) offset:Long?): Mono<*> {
        val tag = "selectRoleList"
        log.debug("$tag,req:$req,limit:$limit, offset:$offset")

        val paging = Paging(limit, offset)
        val resp: List<RoleListResp> = roleService.searchRoleList(req,paging)
        return ok(resp,paging)
    }
}