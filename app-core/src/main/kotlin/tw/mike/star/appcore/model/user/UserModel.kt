package tw.mike.star.appcore.model.user

import jakarta.validation.constraints.Email
import jakarta.validation.constraints.Max
import jakarta.validation.constraints.NotBlank
import java.time.LocalDateTime
import java.util.UUID

/**
 * 管理員建立主帳號，請求參數
 */
data class UserCreateReq(
    /**
     * 角色鍵值
     */
    @NotBlank
    val roleId: UUID,

    /**
     * 帳號
     */
    @NotBlank
    val account: String,

    /**
     * 密碼
     */
    @NotBlank
    val password: String,

    /**
     * 姓名
     */
    var name: String?,

    /**
     * 電話
     */
    var telPhone: String?,

    /**
     * 電子郵件
     */
    @Email
    var email: String?,

    /**
     * 狀態。停用0、啟用1。
     */
    val status: Int,
)

/**
 * 查詢單筆主帳號，回應內容
 */
class UserGetResp {
    /**
     * 鍵值
     */
    lateinit var uid: UUID
    /**
     * 帳號
     */
    @NotBlank
    lateinit var account: String

    /**
     * 姓名
     */
    lateinit var name: String

    /**
     * 密碼
     */
    @NotBlank
    lateinit var password: String

    /**
     * 電話
     */
    var telPhone: String? = null

    /**
     * 電子郵件
     */
    @Email
    var email: String? = null

    /**
     * 狀態。停用0、啟用1。
     */
    var status: Int = 0

    /**
     * 最後修改人員之鍵值
     */
    var updatedBy: UUID? = null

    /**
     * 最後修改時間
     */
    var updatedTime: LocalDateTime? = null

    /**
     * 最後修改人員之鍵值
     */
    var updatedUserName: String? = null

    /**
     * 最後登入時間
     */
    var lastLoginTime: LocalDateTime? = null

    /**
     * 角色鍵值
     */
    lateinit var roleId: UUID

    /**
     * 角色代碼，最多20字元
     */
    @Max(20)
    lateinit var roleCode: String

    /**
     * 角色名稱，最多100字元
     */
    @Max(100)
    lateinit var roleName: String
}

/**
 * 管理員更新主帳號，請求參數
 */
data class UserUpdateReq(
    /**
     * 鍵值
     */
    val uid: UUID,

    /**
     * 姓名
     */
    val name: String?,

    /**
     * 電話
     */
    val telPhone: String?,

    /**
     * 電子郵件
     */
    @Email
    val email: String?,

    /**
     * 狀態。停用0、啟用1。
     */
    val status: Int,

    /**
     * 角色鍵值
     */
    val roleId: UUID,

    /**
     * 密碼
     */
    val password: String?
)

/**
 * 查詢主帳號列表清單，回應內容
 */
data class UserListReq(
    /**
     * 帳號。不支援模糊搜尋。
     */
    val account: String?,

    /**
     * 姓名。不支援模糊搜尋。
     */
    val name: String?,

    /**
     * 角色鍵值
     */
    val roleId: UUID?,

    /**
     * 停用或啟用。停用0、啟用1。
     */
    val status: Int?
)

/**
 * 主帳號列表查詢，回應內容
 */
data class UserListResp(
    /**
     * 鍵值
     */
    val id: UUID,

    /**
     * 帳號
     */
    val account: String,

    /**
     * 姓名
     */
    val name: String,

    /**
     * 角色名稱
     */
    val roleName: String,

    /**
     * 狀態。停用0、啟用1。
     */
    val status: Int,

    /**
     * 最後登入時間
     */
    val lastLoginTime: LocalDateTime?
)