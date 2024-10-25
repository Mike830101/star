/*
 * Auto-generated file. Created by MyBatis Generator
 */
package tw.mike.star.appcore.mapper

import java.util.UUID
import org.apache.ibatis.annotations.Mapper
import org.apache.ibatis.annotations.Result
import org.apache.ibatis.annotations.ResultMap
import org.apache.ibatis.annotations.Results
import org.apache.ibatis.annotations.SelectProvider
import org.apache.ibatis.type.JdbcType
import org.mybatis.dynamic.sql.select.render.SelectStatementProvider
import org.mybatis.dynamic.sql.util.SqlProviderAdapter
import org.mybatis.dynamic.sql.util.kotlin.CountCompleter
import org.mybatis.dynamic.sql.util.kotlin.DeleteCompleter
import org.mybatis.dynamic.sql.util.kotlin.KotlinUpdateBuilder
import org.mybatis.dynamic.sql.util.kotlin.SelectCompleter
import org.mybatis.dynamic.sql.util.kotlin.UpdateCompleter
import org.mybatis.dynamic.sql.util.kotlin.mybatis3.countFrom
import org.mybatis.dynamic.sql.util.kotlin.mybatis3.deleteFrom
import org.mybatis.dynamic.sql.util.kotlin.mybatis3.insert
import org.mybatis.dynamic.sql.util.kotlin.mybatis3.insertMultiple
import org.mybatis.dynamic.sql.util.kotlin.mybatis3.selectDistinct
import org.mybatis.dynamic.sql.util.kotlin.mybatis3.selectList
import org.mybatis.dynamic.sql.util.kotlin.mybatis3.selectOne
import org.mybatis.dynamic.sql.util.kotlin.mybatis3.update
import org.mybatis.dynamic.sql.util.mybatis3.CommonCountMapper
import org.mybatis.dynamic.sql.util.mybatis3.CommonDeleteMapper
import org.mybatis.dynamic.sql.util.mybatis3.CommonInsertMapper
import org.mybatis.dynamic.sql.util.mybatis3.CommonUpdateMapper
import tw.mike.star.appcore.entity.SysUser
import tw.mike.star.appcore.handler.UUIDTypeHandler
import tw.mike.star.appcore.mapper.SysUserDynamicSqlSupport.acc
import tw.mike.star.appcore.mapper.SysUserDynamicSqlSupport.createdBy
import tw.mike.star.appcore.mapper.SysUserDynamicSqlSupport.createdTime
import tw.mike.star.appcore.mapper.SysUserDynamicSqlSupport.deleted
import tw.mike.star.appcore.mapper.SysUserDynamicSqlSupport.deletedBy
import tw.mike.star.appcore.mapper.SysUserDynamicSqlSupport.deletedTime
import tw.mike.star.appcore.mapper.SysUserDynamicSqlSupport.email
import tw.mike.star.appcore.mapper.SysUserDynamicSqlSupport.lastLoginTime
import tw.mike.star.appcore.mapper.SysUserDynamicSqlSupport.name
import tw.mike.star.appcore.mapper.SysUserDynamicSqlSupport.password
import tw.mike.star.appcore.mapper.SysUserDynamicSqlSupport.roleId
import tw.mike.star.appcore.mapper.SysUserDynamicSqlSupport.status
import tw.mike.star.appcore.mapper.SysUserDynamicSqlSupport.sysUser
import tw.mike.star.appcore.mapper.SysUserDynamicSqlSupport.telPhone
import tw.mike.star.appcore.mapper.SysUserDynamicSqlSupport.uid
import tw.mike.star.appcore.mapper.SysUserDynamicSqlSupport.updatedBy
import tw.mike.star.appcore.mapper.SysUserDynamicSqlSupport.updatedTime

@Mapper
interface SysUserMapper : CommonCountMapper, CommonDeleteMapper, CommonInsertMapper<SysUser>, CommonUpdateMapper {
    @SelectProvider(type=SqlProviderAdapter::class, method="select")
    @Results(id="SysUserResult", value = [
        Result(column="uid", property="uid", typeHandler=UUIDTypeHandler::class, jdbcType=JdbcType.OTHER, id=true),
        Result(column="acc", property="acc", jdbcType=JdbcType.VARCHAR),
        Result(column="password", property="password", jdbcType=JdbcType.VARCHAR),
        Result(column="name", property="name", jdbcType=JdbcType.VARCHAR),
        Result(column="tel_phone", property="telPhone", jdbcType=JdbcType.VARCHAR),
        Result(column="email", property="email", jdbcType=JdbcType.VARCHAR),
        Result(column="role_id", property="roleId", typeHandler=UUIDTypeHandler::class, jdbcType=JdbcType.OTHER),
        Result(column="status", property="status", jdbcType=JdbcType.INTEGER),
        Result(column="created_time", property="createdTime", jdbcType=JdbcType.TIMESTAMP),
        Result(column="created_by", property="createdBy", typeHandler=UUIDTypeHandler::class, jdbcType=JdbcType.OTHER),
        Result(column="updated_time", property="updatedTime", jdbcType=JdbcType.TIMESTAMP),
        Result(column="updated_by", property="updatedBy", typeHandler=UUIDTypeHandler::class, jdbcType=JdbcType.OTHER),
        Result(column="deleted", property="deleted", jdbcType=JdbcType.BIT),
        Result(column="deleted_time", property="deletedTime", jdbcType=JdbcType.TIMESTAMP),
        Result(column="deleted_by", property="deletedBy", typeHandler=UUIDTypeHandler::class, jdbcType=JdbcType.OTHER),
        Result(column="last_login_time", property="lastLoginTime", jdbcType=JdbcType.TIMESTAMP)
    ])
    fun selectMany(selectStatement: SelectStatementProvider): List<SysUser>

    @SelectProvider(type=SqlProviderAdapter::class, method="select")
    @ResultMap("SysUserResult")
    fun selectOne(selectStatement: SelectStatementProvider): SysUser?
}

fun SysUserMapper.count(completer: CountCompleter) =
    countFrom(this::count, sysUser, completer)

fun SysUserMapper.delete(completer: DeleteCompleter) =
    deleteFrom(this::delete, sysUser, completer)

fun SysUserMapper.deleteByPrimaryKey(uid_: UUID) =
    delete {
        where { uid isEqualTo uid_ }
    }

fun SysUserMapper.insert(row: SysUser) =
    insert(this::insert, row, sysUser) {
        map(uid) toProperty "uid"
        map(acc) toProperty "acc"
        map(password) toProperty "password"
        map(name) toProperty "name"
        map(telPhone) toProperty "telPhone"
        map(email) toProperty "email"
        map(roleId) toProperty "roleId"
        map(status) toProperty "status"
        map(createdTime) toProperty "createdTime"
        map(createdBy) toProperty "createdBy"
        map(updatedTime) toProperty "updatedTime"
        map(updatedBy) toProperty "updatedBy"
        map(deleted) toProperty "deleted"
        map(deletedTime) toProperty "deletedTime"
        map(deletedBy) toProperty "deletedBy"
        map(lastLoginTime) toProperty "lastLoginTime"
    }

fun SysUserMapper.insertMultiple(records: Collection<SysUser>) =
    insertMultiple(this::insertMultiple, records, sysUser) {
        map(uid) toProperty "uid"
        map(acc) toProperty "acc"
        map(password) toProperty "password"
        map(name) toProperty "name"
        map(telPhone) toProperty "telPhone"
        map(email) toProperty "email"
        map(roleId) toProperty "roleId"
        map(status) toProperty "status"
        map(createdTime) toProperty "createdTime"
        map(createdBy) toProperty "createdBy"
        map(updatedTime) toProperty "updatedTime"
        map(updatedBy) toProperty "updatedBy"
        map(deleted) toProperty "deleted"
        map(deletedTime) toProperty "deletedTime"
        map(deletedBy) toProperty "deletedBy"
        map(lastLoginTime) toProperty "lastLoginTime"
    }

fun SysUserMapper.insertMultiple(vararg records: SysUser) =
    insertMultiple(records.toList())

fun SysUserMapper.insertSelective(row: SysUser) =
    insert(this::insert, row, sysUser) {
        map(uid).toPropertyWhenPresent("uid", row::uid)
        map(acc).toPropertyWhenPresent("acc", row::acc)
        map(password).toPropertyWhenPresent("password", row::password)
        map(name).toPropertyWhenPresent("name", row::name)
        map(telPhone).toPropertyWhenPresent("telPhone", row::telPhone)
        map(email).toPropertyWhenPresent("email", row::email)
        map(roleId).toPropertyWhenPresent("roleId", row::roleId)
        map(status).toPropertyWhenPresent("status", row::status)
        map(createdTime).toPropertyWhenPresent("createdTime", row::createdTime)
        map(createdBy).toPropertyWhenPresent("createdBy", row::createdBy)
        map(updatedTime).toPropertyWhenPresent("updatedTime", row::updatedTime)
        map(updatedBy).toPropertyWhenPresent("updatedBy", row::updatedBy)
        map(deleted).toPropertyWhenPresent("deleted", row::deleted)
        map(deletedTime).toPropertyWhenPresent("deletedTime", row::deletedTime)
        map(deletedBy).toPropertyWhenPresent("deletedBy", row::deletedBy)
        map(lastLoginTime).toPropertyWhenPresent("lastLoginTime", row::lastLoginTime)
    }

private val columnList = listOf(uid, acc, password, name, telPhone, email, roleId, status, createdTime, createdBy, updatedTime, updatedBy, deleted, deletedTime, deletedBy, lastLoginTime)

fun SysUserMapper.selectOne(completer: SelectCompleter) =
    selectOne(this::selectOne, columnList, sysUser, completer)

fun SysUserMapper.select(completer: SelectCompleter) =
    selectList(this::selectMany, columnList, sysUser, completer)

fun SysUserMapper.selectDistinct(completer: SelectCompleter) =
    selectDistinct(this::selectMany, columnList, sysUser, completer)

fun SysUserMapper.selectByPrimaryKey(uid_: UUID) =
    selectOne {
        where { uid isEqualTo uid_ }
    }

fun SysUserMapper.update(completer: UpdateCompleter) =
    update(this::update, sysUser, completer)

fun KotlinUpdateBuilder.updateAllColumns(row: SysUser) =
    apply {
        set(uid) equalToOrNull row::uid
        set(acc) equalToOrNull row::acc
        set(password) equalToOrNull row::password
        set(name) equalToOrNull row::name
        set(telPhone) equalToOrNull row::telPhone
        set(email) equalToOrNull row::email
        set(roleId) equalToOrNull row::roleId
        set(status) equalToOrNull row::status
        set(createdTime) equalToOrNull row::createdTime
        set(createdBy) equalToOrNull row::createdBy
        set(updatedTime) equalToOrNull row::updatedTime
        set(updatedBy) equalToOrNull row::updatedBy
        set(deleted) equalToOrNull row::deleted
        set(deletedTime) equalToOrNull row::deletedTime
        set(deletedBy) equalToOrNull row::deletedBy
        set(lastLoginTime) equalToOrNull row::lastLoginTime
    }

fun KotlinUpdateBuilder.updateSelectiveColumns(row: SysUser) =
    apply {
        set(uid) equalToWhenPresent row::uid
        set(acc) equalToWhenPresent row::acc
        set(password) equalToWhenPresent row::password
        set(name) equalToWhenPresent row::name
        set(telPhone) equalToWhenPresent row::telPhone
        set(email) equalToWhenPresent row::email
        set(roleId) equalToWhenPresent row::roleId
        set(status) equalToWhenPresent row::status
        set(createdTime) equalToWhenPresent row::createdTime
        set(createdBy) equalToWhenPresent row::createdBy
        set(updatedTime) equalToWhenPresent row::updatedTime
        set(updatedBy) equalToWhenPresent row::updatedBy
        set(deleted) equalToWhenPresent row::deleted
        set(deletedTime) equalToWhenPresent row::deletedTime
        set(deletedBy) equalToWhenPresent row::deletedBy
        set(lastLoginTime) equalToWhenPresent row::lastLoginTime
    }

fun SysUserMapper.updateByPrimaryKey(row: SysUser) =
    update {
        set(acc) equalToOrNull row::acc
        set(password) equalToOrNull row::password
        set(name) equalToOrNull row::name
        set(telPhone) equalToOrNull row::telPhone
        set(email) equalToOrNull row::email
        set(roleId) equalToOrNull row::roleId
        set(status) equalToOrNull row::status
        set(createdTime) equalToOrNull row::createdTime
        set(createdBy) equalToOrNull row::createdBy
        set(updatedTime) equalToOrNull row::updatedTime
        set(updatedBy) equalToOrNull row::updatedBy
        set(deleted) equalToOrNull row::deleted
        set(deletedTime) equalToOrNull row::deletedTime
        set(deletedBy) equalToOrNull row::deletedBy
        set(lastLoginTime) equalToOrNull row::lastLoginTime
        where { uid isEqualTo row.uid!! }
    }

fun SysUserMapper.updateByPrimaryKeySelective(row: SysUser) =
    update {
        set(acc) equalToWhenPresent row::acc
        set(password) equalToWhenPresent row::password
        set(name) equalToWhenPresent row::name
        set(telPhone) equalToWhenPresent row::telPhone
        set(email) equalToWhenPresent row::email
        set(roleId) equalToWhenPresent row::roleId
        set(status) equalToWhenPresent row::status
        set(createdTime) equalToWhenPresent row::createdTime
        set(createdBy) equalToWhenPresent row::createdBy
        set(updatedTime) equalToWhenPresent row::updatedTime
        set(updatedBy) equalToWhenPresent row::updatedBy
        set(deleted) equalToWhenPresent row::deleted
        set(deletedTime) equalToWhenPresent row::deletedTime
        set(deletedBy) equalToWhenPresent row::deletedBy
        set(lastLoginTime) equalToWhenPresent row::lastLoginTime
        where { uid isEqualTo row.uid!! }
    }