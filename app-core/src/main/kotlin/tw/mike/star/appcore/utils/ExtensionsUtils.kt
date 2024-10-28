package tw.mike.star.appcore.utils

import org.slf4j.Logger
import org.slf4j.LoggerFactory
import java.io.UnsupportedEncodingException
import java.net.URLEncoder
import java.util.regex.Pattern

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

/**
 * 字串中文編碼
 */
fun String.encode(): String {
    try {
        return URLEncoder.encode(this, "UTF-8").replace("+", "%20")
    } catch (e: UnsupportedEncodingException) {
        e.printStackTrace()
        return this
    }
}

/**
 * 獲取字符長度
 */
fun String?.getStrLength(): Int {
    if (this.isNullOrEmpty()) {
        return 0
    }

    // 中文匹配
    val pattern = Pattern.compile("[\u4e00-\u9fa5]")
    var lengthPtn = 0
    val array = this.toCharArray()

    for (char in array) {
        val matcher = pattern.matcher(char.toString())
        if (matcher.matches()) {
            lengthPtn++
        }
    }

    val lengthNotPtn = array.size - lengthPtn
    return lengthPtn * 3 + lengthNotPtn * 2
}