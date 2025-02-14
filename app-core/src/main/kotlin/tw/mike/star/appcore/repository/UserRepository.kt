package tw.mike.star.appcore.repository

import org.springframework.stereotype.Repository
import tw.mike.star.appcore.entity.SysUser
import tw.mike.star.appcore.mapper.*
import java.util.UUID

@Repository
class UserRepository(
    private val userMapper: SysUserMapper
):BaseRepository(){

    /**
     * 依健值單筆搜尋
     * @param uid 健值
     * @param deleted 是否為刪除 (預設false)
     */
    fun findByUid(uid:UUID,deleted:Boolean? = false) = userMapper.selectOne {
        where {
            SysUserDynamicSqlSupport.sysUser.uid isEqualTo uid
            and { SysUserDynamicSqlSupport.sysUser.deleted isEqualToWhenPresent deleted }
        }
    }

    /**
     * 依帳號搜尋
     * @param account 帳號
     * @param deleted 是否為刪除 (預設false)
     */
    fun findByAccount(account:String,deleted:Boolean? = false) = userMapper.selectOne {
        where {
            SysUserDynamicSqlSupport.sysUser.acc isEqualTo account
            and { SysUserDynamicSqlSupport.sysUser.deleted isEqualToWhenPresent deleted }
        }
    }

    /**
     * 依角色健值做搜尋
     * @param roleUid 角色健值
     * @param enable 是否為啟用 (預設true)
     */
    fun findByRoleUid(roleUid:UUID, enable:Boolean? = true):List<SysUser>{
        return userMapper.select {
            join(RoleDynamicSqlSupport.role){
                on(SysUserDynamicSqlSupport.roleId) equalTo RoleDynamicSqlSupport.uid
            }
            where {
                SysUserDynamicSqlSupport.roleId isEqualTo roleUid
                and { RoleDynamicSqlSupport.role.enable isEqualToWhenPresent enable}
            }
        }
    }

    /**
     * 帳號創建
     * @param user 帳號資訊
     */
    fun insertSelective(user:SysUser) = userMapper.insertSelective(user) == 1

    /**
     * 更新帳號
     * @param user 帳號資訊 (null值不更新)
     */
    fun updateByPrimaryKeySelective(user:SysUser) = userMapper.updateByPrimaryKeySelective(user) == 1
}