package tw.mike.star.appcore.handler

import org.springframework.http.HttpStatus
import org.springframework.http.converter.HttpMessageNotReadableException
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.context.request.WebRequest
import tw.mike.star.appcore.utils.BaseReply
import tw.mike.star.appcore.utils.SysCode
import tw.mike.star.appcore.utils.logger

// 全局異常處理器
@ControllerAdvice
@RestController
class GlobalExceptionHandler {
    private companion object val log = logger()

    // 處理一般的 Exception
    @ExceptionHandler(Exception::class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    fun handleAllExceptions(ex: Exception, request: WebRequest): BaseReply {
        val tag = "handleAllExceptions"
        log.error("$tag,ex:$ex,request:$request")
        return BaseReply(SysCode._3, ex.localizedMessage, ex.javaClass.simpleName)
    }

    // 處理特定的自定義異常
    @ExceptionHandler(IllegalArgumentException::class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    fun handleIllegalArgumentException(ex: IllegalArgumentException, request: WebRequest): BaseReply {
        val tag = "handleIllegalArgumentException"
        log.error("$tag,ex:$ex,request:$request")
        return BaseReply(SysCode._3, ex.localizedMessage,ex.javaClass.simpleName)
    }

    // 處理效驗錯誤
    @ExceptionHandler(MethodArgumentNotValidException::class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    fun handleValidationExceptions(ex: MethodArgumentNotValidException): BaseReply {
        val tag = "handleValidationExceptions"
        log.error("$tag,ex:$ex")
        val errors = ex.bindingResult.allErrors.map { it.defaultMessage }.joinToString(", ")
        return BaseReply(SysCode._1001, errors)
    }


    /**
     * json解析失敗
     */
    @ExceptionHandler(HttpMessageNotReadableException::class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    fun handleHttpMessageNotReadableException(ex: HttpMessageNotReadableException): BaseReply {
        val tag = "handleHttpMessageNotReadableException"
        log.error("$tag,ex:$ex")
        val errors = ex.localizedMessage
        return BaseReply(SysCode._1002, errors,ex.javaClass.simpleName)
    }
}