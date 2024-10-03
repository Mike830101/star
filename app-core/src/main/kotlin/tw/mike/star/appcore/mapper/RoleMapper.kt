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
import tw.mike.star.appcore.entity.Role
import tw.mike.star.appcore.handler.UUIDTypeHandler
import tw.mike.star.appcore.mapper.RoleDynamicSqlSupport.code
import tw.mike.star.appcore.mapper.RoleDynamicSqlSupport.enable
import tw.mike.star.appcore.mapper.RoleDynamicSqlSupport.memo
import tw.mike.star.appcore.mapper.RoleDynamicSqlSupport.name
import tw.mike.star.appcore.mapper.RoleDynamicSqlSupport.role
import tw.mike.star.appcore.mapper.RoleDynamicSqlSupport.uid

@Mapper
interface RoleMapper : CommonCountMapper, CommonDeleteMapper, CommonInsertMapper<Role>, CommonUpdateMapper {
    @SelectProvider(type=SqlProviderAdapter::class, method="select")
    @Results(id="RoleResult", value = [
        Result(column="uid", property="uid", typeHandler=UUIDTypeHandler::class, jdbcType=JdbcType.OTHER, id=true),
        Result(column="name", property="name", jdbcType=JdbcType.VARCHAR),
        Result(column="enable", property="enable", jdbcType=JdbcType.BIT),
        Result(column="code", property="code", jdbcType=JdbcType.VARCHAR),
        Result(column="memo", property="memo", jdbcType=JdbcType.VARCHAR)
    ])
    fun selectMany(selectStatement: SelectStatementProvider): List<Role>

    @SelectProvider(type=SqlProviderAdapter::class, method="select")
    @ResultMap("RoleResult")
    fun selectOne(selectStatement: SelectStatementProvider): Role?
}

fun RoleMapper.count(completer: CountCompleter) =
    countFrom(this::count, role, completer)

fun RoleMapper.delete(completer: DeleteCompleter) =
    deleteFrom(this::delete, role, completer)

fun RoleMapper.deleteByPrimaryKey(uid_: UUID) =
    delete {
        where { uid isEqualTo uid_ }
    }

fun RoleMapper.insert(row: Role) =
    insert(this::insert, row, role) {
        map(uid) toProperty "uid"
        map(name) toProperty "name"
        map(enable) toProperty "enable"
        map(code) toProperty "code"
        map(memo) toProperty "memo"
    }

fun RoleMapper.insertMultiple(records: Collection<Role>) =
    insertMultiple(this::insertMultiple, records, role) {
        map(uid) toProperty "uid"
        map(name) toProperty "name"
        map(enable) toProperty "enable"
        map(code) toProperty "code"
        map(memo) toProperty "memo"
    }

fun RoleMapper.insertMultiple(vararg records: Role) =
    insertMultiple(records.toList())

fun RoleMapper.insertSelective(row: Role) =
    insert(this::insert, row, role) {
        map(uid).toPropertyWhenPresent("uid", row::uid)
        map(name).toPropertyWhenPresent("name", row::name)
        map(enable).toPropertyWhenPresent("enable", row::enable)
        map(code).toPropertyWhenPresent("code", row::code)
        map(memo).toPropertyWhenPresent("memo", row::memo)
    }

private val columnList = listOf(uid, name, enable, code, memo)

fun RoleMapper.selectOne(completer: SelectCompleter) =
    selectOne(this::selectOne, columnList, role, completer)

fun RoleMapper.select(completer: SelectCompleter) =
    selectList(this::selectMany, columnList, role, completer)

fun RoleMapper.selectDistinct(completer: SelectCompleter) =
    selectDistinct(this::selectMany, columnList, role, completer)

fun RoleMapper.selectByPrimaryKey(uid_: UUID) =
    selectOne {
        where { uid isEqualTo uid_ }
    }

fun RoleMapper.update(completer: UpdateCompleter) =
    update(this::update, role, completer)

fun KotlinUpdateBuilder.updateAllColumns(row: Role) =
    apply {
        set(uid) equalToOrNull row::uid
        set(name) equalToOrNull row::name
        set(enable) equalToOrNull row::enable
        set(code) equalToOrNull row::code
        set(memo) equalToOrNull row::memo
    }

fun KotlinUpdateBuilder.updateSelectiveColumns(row: Role) =
    apply {
        set(uid) equalToWhenPresent row::uid
        set(name) equalToWhenPresent row::name
        set(enable) equalToWhenPresent row::enable
        set(code) equalToWhenPresent row::code
        set(memo) equalToWhenPresent row::memo
    }

fun RoleMapper.updateByPrimaryKey(row: Role) =
    update {
        set(name) equalToOrNull row::name
        set(enable) equalToOrNull row::enable
        set(code) equalToOrNull row::code
        set(memo) equalToOrNull row::memo
        where { uid isEqualTo row.uid!! }
    }

fun RoleMapper.updateByPrimaryKeySelective(row: Role) =
    update {
        set(name) equalToWhenPresent row::name
        set(enable) equalToWhenPresent row::enable
        set(code) equalToWhenPresent row::code
        set(memo) equalToWhenPresent row::memo
        where { uid isEqualTo row.uid!! }
    }