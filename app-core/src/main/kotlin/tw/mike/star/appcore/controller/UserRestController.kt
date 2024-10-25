package tw.mike.star.appcore.controller

import jakarta.validation.Valid
import lombok.AllArgsConstructor
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.*
import reactor.core.publisher.Mono
import tw.mike.star.appcore.exception.AuthException
import tw.mike.star.appcore.exception.UserException
import tw.mike.star.appcore.model.user.UserCreateReq
import tw.mike.star.appcore.model.user.UserGetResp
import tw.mike.star.appcore.model.user.UserListReq
import tw.mike.star.appcore.model.user.UserUpdateReq
import tw.mike.star.appcore.service.UserService
import tw.mike.star.appcore.utils.Paging
import tw.mike.star.appcore.utils.SysCode
import tw.mike.star.appcore.utils.UUIdSimpleResp
import java.util.*

/**
 * MD-3-User 帳號相關API
 */
@RestController
@AllArgsConstructor
@RequestMapping("/api/user/v1")
class UserRestController @Autowired constructor(
    private val userService: UserService
): BaseRestController() {

    /**
     * MD-3-User-Get
     * 帳號-查詢單筆。
     */
    @GetMapping
    private fun getUser(@RequestParam(name = "uid", required = true) uid: UUID): Mono<*> {
        val tag = "getUser"
        log.debug("{},uid:{}", tag, uid)
        try {
            val resp: UserGetResp = userService.getUser(uid)
            return ok(resp)
        }catch (ue:UserException){
            return badRequest(SysCode._1, ue.message)
        }catch (e: Exception) {
            e.printStackTrace()
            return badRequest(SysCode._3)
        }
    }

    /**
     * MD-3-User-Create
     * 帳號-建立單筆。管理員可手動建立帳號。
     */
    @PostMapping("/create")
    private fun createUser(@Valid @RequestBody req: UserCreateReq): Mono<*> {
        val tag = "createUser"
        log.debug("{},req:{}", tag, req)
        try {
            val resp: UUIdSimpleResp = userService.createUser(req)
            log.debug("{},resp:{}", tag, resp)
            return ok(resp)
        }catch (ue:UserException){
            return badRequest(SysCode._1, ue.message)
        }catch (ae: AuthException){
            return unauthorized(SysCode._2, ae.message)
        }catch (e: Exception) {
            e.printStackTrace()
            return badRequest(SysCode._3)
        }
    }

    /**
     * MD-3-User-Update
     * 帳號-更新單筆。供管理員更新主帳號。
     */
    @PostMapping("/update")
    private fun updateUser(@Valid @RequestBody req: UserUpdateReq): Mono<*> {
        val tag = "updateUser"
        log.debug("{},req:{}", tag, req)
        try {
            val resp = userService.updateUser(req)
            return ok(resp)
        }catch (ue:UserException){
            return badRequest(SysCode._1, ue.message)
        }catch (ae: AuthException){
            return unauthorized(SysCode._2, ae.message)
        }catch (e: Exception) {
            return badRequest(SysCode._3)
        }
    }

    /**
     * MD-3-User-Remove
     * 帳號-刪除單筆。供管理員刪除主帳號。
     * 刪除時標記為已刪除，但資料仍保存於資料庫中。
     */
    @PostMapping("/remove")
    private fun removeUser(@RequestParam(name = "uid", required = true) uid:UUID): Mono<*> {
        val tag = "removeUser"
        log.debug("{},uid:{}", tag, uid)
        try {
            val resp = userService.removeUser(uid)
            return ok(resp)
        }catch (ue:UserException){
            return badRequest(SysCode._1, ue.message)
        }catch (ae: AuthException){
            return unauthorized(SysCode._2, ae.message)
        }catch (e: Exception) {
            return badRequest(SysCode._3)
        }
    }

    /**
     * MD-3-User-List
     * 帳號-查詢多筆。供管理員查詢主帳號清單。
     * @param limit
     */
    @PostMapping("/list")
    private fun selectUserList(@Valid @RequestBody req: UserListReq,
                               @RequestParam(name = "limit", required = false) limit:Long?,
                               @RequestParam(name = "offset", required = false) offset:Long?): Mono<*> {
        val tag = "selectUserList"
        log.debug("{},limit:{},offset:{}", tag, limit, offset)
        try {
            val paging = Paging(limit, offset)
            val resp = userService.searchUserList(req,paging)
            return ok(resp,paging)
        }catch (ue:UserException){
            return badRequest(SysCode._1, ue.message)
        }catch (ae: AuthException){
            return unauthorized(SysCode._2, ae.message)
        }catch (e: Exception) {
            return badRequest(SysCode._3)
        }
    }
}
