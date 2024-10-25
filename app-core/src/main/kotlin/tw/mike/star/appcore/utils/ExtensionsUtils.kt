package tw.mike.star.appcore.utils

import org.slf4j.Logger
import org.slf4j.LoggerFactory
import java.util.Collections
import java.util.UUID

/**
 * 擴充函式工具
 */
class ExtensionsUtils {}

/**
 * 用於取得 slf4j Logger (名稱自動為呼叫的類別名稱)
 * @return Logger
 */
inline fun <reified T : Any> T.logger(): Logger = LoggerFactory.getLogger(T::class.java)

/**
 * 如果左側變數不為空則執行右側給予的方法  ( ?: 的反義用途)
 * @sample "123" ifNotNull throw Exception
 */
infix fun <T> T?.ifNotNull(block: (T) -> Unit): T? {
    if (this != null) {
        block(this)
    }
    return this
}
