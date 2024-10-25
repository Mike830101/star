package tw.mike.star.appcore.repository

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Repository
import tw.mike.star.appcore.entity.Role
import tw.mike.star.appcore.mapper.RoleDynamicSqlSupport
import tw.mike.star.appcore.mapper.RoleMapper
import tw.mike.star.appcore.mapper.select
import tw.mike.star.appcore.mapper.selectOne
import java.util.UUID

/**
 * 角色
 */
@Repository
class RoleRepository @Autowired constructor(
    private val roeMapper:RoleMapper
):BaseRepository(){

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
}