package tw.mike.star.appcore.model.role
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.Size
import java.util.UUID

/**
 * 角色基本欄位
 */
open class RoleBase {

    /**
     * 角色代碼，最大長度20
     */
    @field:NotBlank(message = "角色代碼不可為空")
    @field:Size(max = 20,message = "角色代碼最大長度20")
    var code: String = ""

    /**
     * 角色名稱，最大長度100
     */
    @field:NotBlank(message = "角色名稱不可為空")
    @field:Size(max = 100,message = "角色名稱最大長度100")
    var name: String = ""

    /**
     * 備註，最大長度1024
     */
    @field:Size(max = 1024,message = "角色備註最大長度1024")
    var memo: String? = null

    /**
     * 是否啟用
     */
    var enabled: Boolean = true
}

/**
 * 角色資料
 */
open class RoleEntity : RoleBase() {

    /**
     * 鍵值
     */
    lateinit var uid: UUID
}

/**
 * 建立角色的請求參數
 */
class RoleCreateReq : RoleBase()

/**
 * 角色 - 查詢單筆的回應內容
 */
class RoleGetResp : RoleEntity()

/**
 * 更新角色的請求參數
 */
class RoleUpdateReq : RoleEntity()

/**
 * 查詢角色列表的請求參數
 */
class RoleListReq {

    /**
     * 角色名稱 (支援模糊查詢)
     */
    var name: String? = null

    /**
     * 是否啟用
     */
    var enabled: Boolean? = null
}

/**
 * 查詢角色列表的回應內容
 */
class RoleListResp {

    /**
     * 鍵值
     */
    lateinit var uid: UUID

    /**
     * 角色代碼
     */
    var code: String = ""

    /**
     * 角色名稱
     */
    var name: String = ""

    /**
     * 是否啟用
     */
    var enabled: Boolean = true

    /**
     * 備註
     */
    var memo: String? = null
}
