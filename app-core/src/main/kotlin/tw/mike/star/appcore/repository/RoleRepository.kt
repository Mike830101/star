package tw.mike.star.appcore.repository

import org.springframework.stereotype.Repository
import tw.mike.star.appcore.entity.Role
import tw.mike.star.appcore.mapper.*
import java.util.UUID

/**
 * 角色
 */
@Repository
class RoleRepository(
    private val roeMapper:RoleMapper
):BaseRepository(){

    /**
     * 依名稱or代碼 搜尋清單
     * @param name 角色名稱
     * @param code 角色代碼
     */
    fun findByNameOrCode(name:String,code:String):List<Role> = roeMapper.select {
        where {
            RoleDynamicSqlSupport.role.name isEqualTo name
            or { RoleDynamicSqlSupport.role.code isEqualTo code }
        }
    }

    /**
     * 依健值單筆搜尋
     * @param uid 健值
     * @param enable 是否為啟用 (預設true)
     */
    fun findByUid(uid:UUID,enable:Boolean? = true) = roeMapper.selectOne {
        where {
            RoleDynamicSqlSupport.role.uid isEqualTo uid
            and { RoleDynamicSqlSupport.role.enable isEqualToWhenPresent enable}
        }
    }

    /**
     * 依健值清單做搜尋
     * @param uidList 健值清單
     * @param enable 是否為啟用 (預設true)
     */
    fun findByUidList(uidList:List<UUID>, enable:Boolean? = true):List<Role>{
        if (uidList.isEmpty()) return listOf()
        return roeMapper.select {
            where {
                RoleDynamicSqlSupport.uid.isIn(uidList)
                and { RoleDynamicSqlSupport.role.enable isEqualToWhenPresent enable}
            }
        }
    }

    /**
     * 角色創建
     * @param role 角色
     */
    fun insertSelective(role:Role) = roeMapper.insertSelective(role) == 1


    /**
     * 更新角色
     * @param role 角色資訊 (null值不更新)
     */
    fun updateByPrimaryKeySelective(role:Role) = roeMapper.updateByPrimaryKeySelective(role) == 1

    /**
     * 角色刪除
     * @param uid 角色健值
     */
    fun removeByUid(uid:UUID) = roeMapper.deleteByPrimaryKey(uid) == 1
}