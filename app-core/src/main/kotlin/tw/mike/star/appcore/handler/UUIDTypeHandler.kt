package tw.mike.star.appcore.handler

import org.apache.ibatis.type.BaseTypeHandler
import org.apache.ibatis.type.JdbcType
import org.apache.ibatis.type.MappedJdbcTypes
import org.apache.ibatis.type.MappedTypes
import java.sql.CallableStatement
import java.sql.PreparedStatement
import java.sql.ResultSet
import java.sql.SQLException
import java.util.*

/**
 * UUID Type Handler
 */
@MappedJdbcTypes(JdbcType.OTHER)
@MappedTypes(UUID::class)
class UUIDTypeHandler : BaseTypeHandler<UUID?>() {
    @Throws(SQLException::class)
    override fun setNonNullParameter(ps: PreparedStatement, i: Int, parameter: UUID?, jdbcType: JdbcType) {
        ps.setObject(i, parameter, jdbcType.TYPE_CODE)
    }

    @Throws(SQLException::class)
    override fun getNullableResult(rs: ResultSet, columnName: String): UUID? {
        return rs.getObject(columnName) as UUID?
    }

    @Throws(SQLException::class)
    override fun getNullableResult(rs: ResultSet, columnIndex: Int): UUID? {
        return rs.getObject(columnIndex) as UUID?
    }

    @Throws(SQLException::class)
    override fun getNullableResult(cs: CallableStatement, columnIndex: Int): UUID? {
        return cs.getObject(columnIndex) as UUID?
    }
}
