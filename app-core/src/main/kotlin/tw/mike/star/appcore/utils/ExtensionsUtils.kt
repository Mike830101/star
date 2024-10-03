package tw.mike.star.appcore.utils

import org.slf4j.Logger
import org.slf4j.LoggerFactory

/**
 * 擴充函式工具
 */
class ExtensionsUtils {
}

/**
 * 用於取得 slf4j Logger (名稱自動為呼叫的類別名稱)
 * @return Logger
 */
inline fun <reified T : Any> T.logger(): Logger = LoggerFactory.getLogger(T::class.java)
