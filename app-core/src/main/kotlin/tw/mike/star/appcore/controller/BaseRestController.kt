package tw.mike.star.appcore.controller

import com.fasterxml.jackson.databind.ObjectMapper
import jakarta.servlet.http.HttpServletResponse
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import reactor.core.publisher.Mono
import tw.mike.star.appcore.utils.BaseReply
import tw.mike.star.appcore.utils.Paging
import tw.mike.star.appcore.utils.SysCode
import tw.mike.star.appcore.utils.logger
import java.io.IOException
import java.io.Serializable

/**
 * Base class for all REST controllers.
 */
open class BaseRestController {

    protected companion object {
        val log = logger()
        val objectMapper: ObjectMapper = ObjectMapper()
    }

    /**
     * 回覆 http 401 及錯誤代碼及訊息。
     * @param sysCode 錯誤代碼
     * @param data 輸出資料
     * @param information 回覆訊息
     * @param paging 分頁資訊
     */
    fun unauthorized(
        sysCode: SysCode? = null,
        data: Any? = null,
        information: String? = null,
        paging: Paging? = null
    ): Mono<ResponseEntity<*>> {
        return sendMessage(HttpStatus.UNAUTHORIZED, sysCode, information, data, paging)
    }

    /**
     * 回覆 http 403 及錯誤代碼及訊息。
     * @param sysCode 錯誤代碼
     */
    fun forbidden(sysCode: SysCode? = null): Mono<ResponseEntity<*>> {
        return sendMessage(HttpStatus.FORBIDDEN, sysCode, null, null, null)
    }


    /**
     * 回覆 http 200 及資料。
     * @param body 輸出資料
     * @param paging 分頁資訊
     */
    fun ok(body: Any? = null, paging: Paging? = null): Mono<ResponseEntity<*>> {
        log.debug("ok, body: {}, paging: {}", body, paging)
        return sendMessage(HttpStatus.OK, SysCode.OK, null, body, paging)
    }

    /**
     * 回覆 http 400 及錯誤代碼及訊息。
     * @param sysCode 錯誤代碼
     * @param information 回覆訊息
     * @param data 輸出資料
     */
    fun badRequest(sysCode: SysCode? = null, information: String? = null, data: Any? = null): Mono<ResponseEntity<*>> {
        return sendMessage(HttpStatus.BAD_REQUEST, sysCode, information, data, null)
    }

    /**
     * 回覆 http 500 及錯誤代碼及訊息。
     * @param sysCode 錯誤代碼
     * @param information 回覆訊息
     * @param data 輸出資料
     */
    fun serverError(sysCode: SysCode? = null, information: String? = null, data: Any? = null): Mono<ResponseEntity<*>> {
        return sendMessage(HttpStatus.INTERNAL_SERVER_ERROR, sysCode, information, data, null)
    }

    /**
     * 回覆
     * @param httpStatus http狀態
     * @param sysCode 錯誤代碼
     * @param information 回覆訊息(null時採用sysCode的訊息)
     * @param data 輸出資料
     */
    fun sendMessage(
        httpStatus: HttpStatus,
        sysCode: SysCode? = null,
        information: String? = null,
        data: Any? = null,
        paging: Paging? = null
    ): Mono<ResponseEntity<*>> {

        return Mono.just(ResponseEntity.status(httpStatus).body(BaseReply(sysCode, information, data, paging).apply {
            log.debug("sendMessage,BaseReply:$this")
        }))
    }


    //---------------------以下為透過修改 response 回覆 json ---------------------
    /**
     * 回覆 http 400 及錯誤代碼及訊息。
     */
    fun badRequest(response: HttpServletResponse, sysCode: SysCode, information: String?, data: Any?) {
        sendError(response, HttpStatus.BAD_REQUEST, sysCode, information, data)
    }

    /**
     * 回覆 http 500 及錯誤代碼及訊息。
     */
    fun serverError(response: HttpServletResponse, sysCode: SysCode, information: String?, data: Any?) {
        sendError(response, HttpStatus.INTERNAL_SERVER_ERROR, sysCode, information, data)
    }

    /**
     * 回覆 json 資料。
     *
     * @param response the HttpServletResponse to emit
     * @param httpStatus http狀態
     * @param sysCode 錯誤代碼
     * @param information 回覆訊息(null時採用sysCode的訊息)
     * @param data 輸出資料
     */
    private fun sendError(
        response: HttpServletResponse,
        httpStatus: HttpStatus,
        sysCode: SysCode,
        information: String?,
        data: Any?
    ) {
        val dataMap: MutableMap<String, Serializable> = HashMap()
        dataMap["code"] = sysCode.code
        dataMap["message"] = information ?: sysCode.message
        if (data != null) {
            dataMap["data"] = data as Serializable
        }

        // 輸出 json
        try {
            val json = objectMapper.writeValueAsString(dataMap)
            response.contentType = "application/json"
            response.characterEncoding = "UTF-8"
            response.status = httpStatus.value()
            response.writer.write(json)
        } catch (e: IOException) {
            log.error("Failed to write response", e)
        }
    }
}