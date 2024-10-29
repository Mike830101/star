package tw.mike.star.appcore.service

import org.apache.poi.xssf.usermodel.XSSFWorkbook
import tw.mike.star.appcore.model.user.*
import tw.mike.star.appcore.utils.Paging
import tw.mike.star.appcore.utils.UUIdSimpleResp
import java.util.*

/**
 * 帳號管理API
 */
interface UserService{

    /**
     * 帳號-查詢單筆。
     * @param uid 帳號鍵值
     */
    fun getUser(uid: UUID): UserGetResp

    /**
     * 帳號-建立單筆
     */
    fun createUser(req: UserCreateReq): UUIdSimpleResp

    /**
     * 帳號-更新單筆
     */
    fun updateUser(req: UserUpdateReq): UUIdSimpleResp

    /**
     * 帳號-刪除單筆。供管理員刪除主帳號。
     * 刪除時標記為已刪除，但資料仍保存於資料庫中。
     * @param uid 帳號鍵值
     */
    fun removeUser(uid: UUID): UUIdSimpleResp

    /**
     * 帳號-查詢多筆。供管理員查詢主帳號清單。
     * 不支援模糊援尋的查詢條件，包括：帳號、姓名。其它文字輸入類型的欄位可支援模糊援尋。
     */
    fun searchUserList(req: UserListReq,paging: Paging): List<UserListResp>

    /**
     * 帳號-查詢多筆並匯出excel。
     */
    fun exportUserList(req: UserListReq): XSSFWorkbook
}