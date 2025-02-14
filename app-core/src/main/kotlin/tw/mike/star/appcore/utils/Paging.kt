package tw.mike.star.appcore.utils

/**
 * 分頁
 * @param limit 取得比數
 * @param offset 跳過比數
 * @param total 總數量
 */
data class Paging(var limit: Long?,var offset:Long?, var total: Long = 0)