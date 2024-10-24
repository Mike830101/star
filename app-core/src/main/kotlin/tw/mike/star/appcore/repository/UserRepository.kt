package tw.mike.star.appcore.repository

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Repository
import tw.mike.star.appcore.entity.SysUser
import tw.mike.star.appcore.mapper.*
import java.util.UUID

@Repository
class UserRepository @Autowired constructor(
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