package tw.mike.star.appcore.handler

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.context.request.WebRequest
import tw.mike.star.appcore.utils.logger

// 全局異常處理器
@ControllerAdvice
@RestController
class GlobalExceptionHandler {
    private companion object val log = logger()

    // 處理一般的 Exception
    @ExceptionHandler(Exception::class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    fun handleAllExceptions(ex: Exception, request: WebRequest): Map<String, String> {
        log.debug("handleAllExceptions,ex:$ex,request:$request")
        return mapOf("message" to ex.localizedMessage)
    }

    // 處理特定的自定義異常
    @ExceptionHandler(IllegalArgumentException::class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    fun handleIllegalArgumentException(ex: IllegalArgumentException, request: WebRequest): Map<String, String> {
        return mapOf("message" to ex.localizedMessage)
    }
}