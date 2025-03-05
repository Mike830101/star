package tw.mike.star.appcore.controller

import jakarta.servlet.http.HttpServletResponse
import jakarta.validation.Valid
import org.apache.poi.xssf.usermodel.XSSFWorkbook
import org.springframework.http.HttpHeaders
import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.*
import reactor.core.publisher.Mono
import tw.mike.star.appcore.exception.UserException
import tw.mike.star.appcore.model.user.*
import tw.mike.star.appcore.service.UserService
import tw.mike.star.appcore.utils.Paging
import tw.mike.star.appcore.utils.SysCode
import tw.mike.star.appcore.utils.UUIdSimpleResp
import tw.mike.star.appcore.utils.encode
import java.io.ByteArrayOutputStream
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.*

/**
 * MD-3-User 帳號相關API
 */
@RestController
@RequestMapping("/api/user/v1")
class UserRestController(
    private val userService: UserService
): BaseRestController() {

    /**
     * MD-3-User-Get
     * 帳號-查詢單筆。
     * @see UserGetResp return
     */
    @GetMapping
    private fun getUser(@RequestParam(name = "uid", required = true) uid: UUID): Mono<*> {
        val tag = "getUser"
        log.debug("{},uid:{}", tag, uid)
        val resp: UserGetResp = userService.getUser(uid)?: return badRequest(SysCode._4102)
        return ok(resp)
    }

    /**
     * MD-3-User-Create
     * 帳號-建立單筆。管理員可手動建立帳號。
     * @see UUIdSimpleResp return
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
            return badRequest(SysCode._4001, ue.message)
        }
    }

    /**
     * MD-3-User-Update
     * 帳號-更新單筆。供管理員更新主帳號。
     * @see UUIdSimpleResp return
     */
    @PostMapping("/update")
    private fun updateUser(@Valid @RequestBody req: UserUpdateReq): Mono<*> {
        val tag = "updateUser"
        log.debug("{},req:{}", tag, req)
        try {
            val resp:UUIdSimpleResp = userService.updateUser(req)
            return ok(resp)
        }catch (ue:UserException){
            return badRequest(SysCode._4001, ue.message)
        }
    }

    /**
     * MD-3-User-Remove
     * 帳號-刪除單筆。供管理員刪除主帳號。
     * 刪除時標記為已刪除，但資料仍保存於資料庫中。
     * @see UUIdSimpleResp return
     */
    @GetMapping("/remove")
    private fun removeUser(@RequestParam(name = "uid", required = true) uid:UUID): Mono<*> {
        val tag = "removeUser"
        log.debug("{},uid:{}", tag, uid)
        try {
            val resp:UUIdSimpleResp = userService.removeUser(uid)
            return ok(resp)
        }catch (ue:UserException){
            return badRequest(SysCode._4001, ue.message)
        }
    }

    /**
     * MD-3-User-List
     * 帳號-查詢多筆。供管理員查詢主帳號清單。
     * @param limit 取得比數
     * @param offset 跳過比數
     * @see UserListResp return
     */
    @PostMapping("/list")
    private fun selectUserList(@Valid @RequestBody req: UserListReq,
                               @RequestParam(name = "limit", required = false) limit:Long?,
                               @RequestParam(name = "offset", required = false) offset:Long?): Mono<*> {
        val tag = "selectUserList"
        log.debug("{},limit:{},offset:{}", tag, limit, offset)
        val paging = Paging(limit, offset)
        val resp:List<UserListResp> = userService.searchUserList(req,paging)
        log.info("{},resp:{}", tag, resp)
        return ok(resp,paging)
    }

    /**
     * MD-3-User-Export
     * 帳號-匯出excel。
     */
    @PostMapping("/export")
    private fun exportUserList(@Valid @RequestBody req: UserListReq, response: HttpServletResponse) {
        val tag = "exportUserList"
        log.debug("{},req:{}", tag, req)

        try {

            // 設置文件名格式
            val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd_HHmmss")
            val fileName = "帳號列表匯出_${LocalDateTime.now().format(formatter)}.xlsx"

            // 設置回應標頭，指定內容類型和下載的文件名
            response.contentType = MediaType.APPLICATION_OCTET_STREAM_VALUE
            // 編碼文件名，確保支援非 ASCII 字符
            response.setHeader(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename*=utf-8''${fileName.encode()}")

            // 使用 use 函數自動關閉 ByteArrayOutputStream 和 workbook，避免內存洩漏。
            ByteArrayOutputStream().use { outputStream ->
                // 生成工作簿 (Excel 檔案)
                val workbook: XSSFWorkbook = userService.exportUserList(req)

                workbook.use { // 關閉 workbook 資源
                    it.write(outputStream)
                }
                response.outputStream.write(outputStream.toByteArray())
                response.flushBuffer()
            }
        }catch (e: Exception) {
            e.printStackTrace()
            serverError(response,SysCode._3,"匯出失敗")
        }
    }
}
