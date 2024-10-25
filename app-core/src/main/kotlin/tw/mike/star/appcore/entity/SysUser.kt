/*
 * Auto-generated file. Created by MyBatis Generator
 */
package tw.mike.star.appcore.entity

import java.io.Serializable
import java.time.LocalDateTime
import java.util.UUID

data class SysUser(
    var uid: UUID? = null,
    var acc: String? = null,
    var password: String? = null,
    var name: String? = null,
    var telPhone: String? = null,
    var email: String? = null,
    var roleId: UUID? = null,
    var status: Int? = null,
    var createdTime: LocalDateTime? = null,
    var createdBy: UUID? = null,
    var updatedTime: LocalDateTime? = null,
    var updatedBy: UUID? = null,
    var deleted: Boolean? = null,
    var deletedTime: LocalDateTime? = null,
    var deletedBy: UUID? = null,
    var lastLoginTime: LocalDateTime? = null
) : Serializable