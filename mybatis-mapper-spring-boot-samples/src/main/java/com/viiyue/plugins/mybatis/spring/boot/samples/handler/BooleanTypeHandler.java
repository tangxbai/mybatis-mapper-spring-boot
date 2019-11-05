package com.viiyue.plugins.mybatis.spring.boot.samples.handler;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Objects;

import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.MappedTypes;

import com.viiyue.plugins.mybatis.utils.Assert;
import com.viiyue.plugins.mybatis.utils.ObjectUtil;

@MappedTypes( { boolean.class, Boolean.class } )
public class BooleanTypeHandler extends BaseTypeHandler<Boolean> {

	private static final String TRUE = "Y";
	private static final String FALSE = "N";
	
	@Override
	public void setNonNullParameter( PreparedStatement ps, int i, Boolean parameter, JdbcType jdbcType ) throws SQLException {
		ps.setString( i, parameter != null && parameter ? TRUE : FALSE );
	}

	@Override
	public Boolean getNullableResult( ResultSet rs, String columnName ) throws SQLException {
		Object result = rs.getObject( columnName );
		check( result );
		return Objects.equals( result, TRUE );
	}

	@Override
	public Boolean getNullableResult( ResultSet rs, int columnIndex ) throws SQLException {
		Object result = rs.getObject( columnIndex );
		check( result );
		return Objects.equals( result, TRUE );
	}

	@Override
	public Boolean getNullableResult( CallableStatement cs, int columnIndex ) throws SQLException {
		Object result = cs.getObject( columnIndex );
		check( result );
		return Objects.equals( result, TRUE );
	}
	
	private void check( Object result ) {
		Assert.isTrue( ObjectUtil.equalsAny( result, TRUE, FALSE ), "The boolean value can only be a character: " + TRUE + "/" + FALSE );
	}

}
