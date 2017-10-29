package com.wuhan.yq.datasource.handler;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.wuhan.yq.utils.GsonUtil;
import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;
import org.postgresql.util.PGobject;


public class JsonTypeHandler<T extends Object> extends BaseTypeHandler<T>
{
    
    // private static final ObjectMapper mapper = new ObjectMapper();
    
    private Class<T> clazz;
    
    public JsonTypeHandler(Class<T> clazz)
    {
        if (clazz == null)
            throw new IllegalArgumentException("Type argument cannot be null");
        this.clazz = clazz;
    }
    
    @Override
    public void setNonNullParameter(PreparedStatement ps, int i, Object parameter, JdbcType jdbcType)
        throws SQLException
    {
        // ps.setString(i, GsonUtil.toJson(parameter));
        PGobject jsonObject = new PGobject();
        jsonObject.setType("jsonb");
        jsonObject.setValue(GsonUtil.toJson(parameter));
        ps.setObject(i, jsonObject);
    }
    
    @Override
    public T getNullableResult(ResultSet rs, String columnName)
        throws SQLException
    {
        String result = rs.getString(columnName);
        return null == result ? null : GsonUtil.fromJson(rs.getString(columnName), clazz);
    }
    
    @Override
    public T getNullableResult(ResultSet rs, int columnIndex)
        throws SQLException
    {
        String result = rs.getString(columnIndex);
        return null == result ? null : GsonUtil.fromJson(rs.getString(columnIndex), clazz);
    }
    
    @Override
    public T getNullableResult(CallableStatement cs, int columnIndex)
        throws SQLException
    {
        String result = cs.getString(columnIndex);
        return null == result ? null : GsonUtil.fromJson(cs.getString(columnIndex), clazz);
    }
    
}
