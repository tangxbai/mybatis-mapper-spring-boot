package com.viiyue.plugins.mybatis.spring.boot.samples.common;

import java.util.Date;

import com.viiyue.plugins.mybatis.annotation.bean.DefaultOrderBy;
import com.viiyue.plugins.mybatis.annotation.bean.Table;
import com.viiyue.plugins.mybatis.annotation.member.Column;
import com.viiyue.plugins.mybatis.annotation.member.GeneratedKey;
import com.viiyue.plugins.mybatis.annotation.member.Id;
import com.viiyue.plugins.mybatis.annotation.member.Index;
import com.viiyue.plugins.mybatis.annotation.member.LogicallyDelete;
import com.viiyue.plugins.mybatis.annotation.member.Version;
import com.viiyue.plugins.mybatis.annotation.rule.ExpressionRule;
import com.viiyue.plugins.mybatis.annotation.rule.NamingRule;
import com.viiyue.plugins.mybatis.annotation.rule.ValueRule;
import com.viiyue.plugins.mybatis.enums.ExpressionStyle;
import com.viiyue.plugins.mybatis.enums.NameStyle;
import com.viiyue.plugins.mybatis.enums.Type;
import com.viiyue.plugins.mybatis.enums.ValueStyle;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Table( prefix = "t_" ) // 表名生成规则，可以配置更多详细说明
@NamingRule( NameStyle.UNDERLINE ) // 字段和数据库列之间的转换规则
@ValueRule( ValueStyle.SHORT ) // 值的生成规则，类似于：#{id, javaType=Long, jdbcType=BIGINT}
@ExpressionRule( ExpressionStyle.SHORT ) // 表达式生成规则，类似于: id = #{id, javaType=Long, jdbcType=BIGINT}
@DefaultOrderBy( "#pk" ) // #pk主键占位符，指向当前生效的主键字段，也可以直接写 "id"。
public class BaseModel {

	@Id // 主键可以配置多个，但是只会有一个生效，Api方法中如果想要使用其他主键请指明所在下标位置
	@Index( Integer.MIN_VALUE )
	@GeneratedKey( useGeneratedKeys = true ) // JDBC支持的自增主键获取方式
//	@GeneratedKey( valueProvider = SnowFlakeIdValueProvider.class ) // 雪花Id，插件提供的两种主键生成策略之一
//	@GeneratedKey( statement = "MYSQL" ) // 枚举引用
//	@GeneratedKey( statement = "SELECT LAST_INSERT_ID()" ) // 自增主键SQL查询语句
//	@GeneratedKey( statementProvider = YourCustomStatementProvider.class ) // 通过Provider提供SQL语句
	private Long id;
	
	@Index( Integer.MAX_VALUE - 4 )
	@Column( jdcbType = Type.CHAR )
	@LogicallyDelete( selectValue = "Y", deletedValue = "N" ) // 开启逻辑删除支持，只能配置一次
	private Boolean display;

	@Index( Integer.MAX_VALUE - 3 )
	private Date createTime;

	@Index( Integer.MAX_VALUE - 2 )
	private Date modifyTime;
	
	@Version // 开启乐观锁支持，只能配置一次
	@Index( Integer.MAX_VALUE - 1 )
	@Column( insertable = false )
	private Long version;

	// @Index注解对字段进行干扰以后，输出的顺序大概是这样：
	// => id, ..., display, create_time, modify_time, version
	// 如果您不使用@Index注解，那么字段的原始顺序就是这样的：
	// => id, display, create_time, modify_time, version, ...
	// 默认输出会将父类的字段排在最前面
	
}
