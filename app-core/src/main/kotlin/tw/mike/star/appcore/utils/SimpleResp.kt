package tw.mike.star.appcore.utils

import java.util.UUID

/**
 * id 簡易回覆
 * @param id 編號 or 鍵值
 * @param subject id對應的標的的名稱
 */
data class IdSimpleResp(val id:Int, val subject:String ?= "")

/**
 * id 簡易回覆
 * @param uid 編號 or 鍵值
 * @param subject id對應的標的的名稱
 */
data class UUIdSimpleResp(val uid:UUID,val subject:String ?= "")