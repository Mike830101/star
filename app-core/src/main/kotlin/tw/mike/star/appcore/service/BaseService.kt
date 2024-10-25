package tw.mike.star.appcore.service

import tw.mike.star.appcore.utils.IdToken
import tw.mike.star.appcore.utils.SequenceUtils
import tw.mike.star.appcore.utils.logger
import kotlin.random.Random

abstract class BaseService {
    companion object {
        val log = logger()
    }

    fun getIdToken(): IdToken? = IdToken(
        iss = "https://example.com", // 發行者 URL
        authTime = System.currentTimeMillis() / 1000, // 當前時間戳（秒）
        sub = SequenceUtils.sequenceUUID(), // 隨機生成的用戶唯一標識符
        exp = System.currentTimeMillis() / 1000 + Random.nextLong(3600, 7200), // 隨機設定令牌過期時間（1小時到2小時）
        iat = System.currentTimeMillis() / 1000, // 當前時間戳（秒）
        account = "user${Random.nextInt(1000)}@example.com", // 隨機生成的帳號
        name = "User ${Random.nextInt(1, 100)}", // 隨機生成的用戶名
        email = "user${Random.nextInt(1000)}@example.com", // 隨機生成的電子郵件
        mobile = "+886-9${Random.nextInt(10000000, 99999999)}", // 隨機生成的手機門號
        roleUid = SequenceUtils.sequenceUUID(), // 隨機生成的角色 UID
        roleName = "Role${Random.nextInt(1, 10)}" // 隨機生成的角色名稱
    )
}