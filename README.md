![mybatis](http://mybatis.github.io/images/mybatis-logo.png)

# Mybatis-mapper-spring-boot-starter
![version](https://img.shields.io/badge/release-1.3.0-blue) [![maven central](https://img.shields.io/badge/maven%20central-1.3.0-brightgreen)](https://maven-badges.herokuapp.com/maven-central/org.mybatis/mybatis) [![license](https://img.shields.io/badge/license-Apache%202.0-blue)](http://www.apache.org/licenses/LICENSE-2.0.html)

Mybatis-mapper关于springboot的整合组件，使用它将给你带来沉浸式的开发体验，极简化的配置，丰富的Api。



## 关联文档

关于纯java环境，请移步到：https://github.com/tangxbai/mybatis-mapper

关于整合spring，请移步到：https://github.com/tangxbai/mybatis-mapper-spring



## 项目演示

- java + mybatis-mapper - [点击获取]( https://github.com/tangxbai/mybatis-mapper-demo)
- spring + mybatis-mapper - [点击获取]( https://github.com/tangxbai/mybatis-mapper-spring-demo)
- springboot + mybatis-mapper - [点击获取](https://github.com/tangxbai/mybatis-mapper-spring-boot/tree/master/mybatis-mapper-spring-boot-samples)



## 快速开始

导入springboot-starter启动组件，配合配置参数即可正常使用插件Api。

```xml
<dependency>
    <groupId>com.viiyue.plugins</groupId>
    <artifactId>mybatis-mapper-spring-boot-starter</artifactId>
    <version>[VERSION]</version>
</dependency>
```

如何获取最新版本？[点击这里获取最新版本](https://search.maven.org/search?q=g:com.viiyue.plugins%20AND%20a:mybatis-mapper-spring-boot-starter&core=gav)



## 基础配置

```properties
# 启用/禁用mybatis-mapper插件，默认开启
# 注意：如果此项设置为false，那么以下所有配置将不会生效
mybatis-mapper.setting.enable = true

# 启用/禁用日志打印，默认开启
# 注意：如果此项设置为false，那么以下两项配置将不会生效
mybatis-mapper.setting.enable-logger = true

# 启用/禁用打印运行SQL编译日志，默认开启
mybatis-mapper.setting.enable-runtime-log = true

# 启用/禁用Mapper扫描日志，默认开启
mybatis-mapper.setting.enable-mapper-scan-log = true

# 启用/禁用打印编译日志，默认开启
mybatis-mapper.setting.enable-compilation-log = true

# 启用/禁用XML模板语法解析，默认禁用
# 注意：如果此项设置为false，将无法使用XML模板语法
mybatis-mapper.setting.enable-xml-syntax-parsing = false

# 启用/禁用关键字转大写，默认小写
mybatis-mapper.setting.enable-keywords-to-uppercase = false

# 设置数据库列样式，默认"#"
mybatis-mapper.setting.database-column-style = #
```

以上各配置项的样例值均为默认值，如果想要更改默认值的话，请帖上您的条例配置，如果您想继续使用这些默认值的话，可以省略这些配置直接使用。

这里贴的是properties的配置方式，各位可以自行转换成 `yml` 的配置形式。



## 配置数据库Bean

```java
@Table( prefix = "t_" ) // 表名生成规则，可以配置更多详细说明
@NamingRule( NameStyle.UNDERLINE ) // 字段和数据库列之间的转换规则
@ValueRule( ValueStyle.SHORT ) // 值的生成规则，类似于：#{id, javaType=Long, jdbcType=BIGINT}
@ExpressionRule( ExpressionStyle.SHORT ) // 表达式生成规则，类似于: id = #{id, javaType=Long, jdbcType=BIGINT}
@DefaultOrderBy( "#pk" ) // #pk主键占位符，指向当前生效的主键字段，也可以直接写 "id"。
public class YourModelBean {

    @Id // 主键可以配置多个，但是只会有一个生效，Api方法中如果想要使用其他主键请指明所在下标位置
    @Index( Integer.MIN_VALUE )
    @GeneratedKey( useGeneratedKeys = true ) // JDBC支持的自增主键获取方式
    //	@GeneratedKey( valueProvider = SnowFlakeIdValueProvider.class ) // 雪花Id，插件提供的两种主键生成策略之一
    //	@GeneratedKey( statement = "MYSQL" ) // 枚举引用
    //	@GeneratedKey( statement = "SELECT LAST_INSERT_ID()" ) // 自增主键SQL查询语句
    //	@GeneratedKey( statementProvider = YourCustomStatementProvider.class ) // 通过Provider提供SQL语句
    private Long id;

    @Index( Integer.MAX_VALUE - 4 )
    @Column( jdcbType = Type.CHAR ) // 对字段进行详细描述
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

    // @Index主要对字段出现顺序进行干扰，对字段进行干扰以后，输出的顺序大概是这样：
    // => id, ..., display, create_time, modify_time, version
    // 如果您未使用@Index注解，那么字段的原始顺序是这样的：
    // => id, display, create_time, modify_time, version, ...
    // 默认输出会将父类的字段排在最前面
    
    // setter/getter...

}
```



## 配置Mapper

```java
@Repository
public interface AccountMapper extends Mapper<Account, AccountDTO, Long> {
    // 你自己的一些Api方法
}
```

关于`@Repository`注解，您需要通过启动类配置`@MapperScan`来指定关于Mapper的扫描规则，各位根据自己的使用习惯进行配置，因为插件已经使用了Mapper这个名字，所以建议使用`@Mapper`这个注解。



## 使用方式

接下来就可以直接在任何可以使用Spring的地方注入或者获取Mapper接口了，关于mybatis-mapper的一些细则，你可以 [点击这里查看更详细的文档](https://github.com/tangxbai/mybatis-mapper#如何使用)。



## 关于作者

- QQ群：947460272
- 邮箱：tangxbai@hotmail.com
- 掘金： https://juejin.im/user/5da5621ce51d4524f007f35f
- 简书： https://www.jianshu.com/u/e62f4302c51f
- Issuse：https://github.com/tangxbai/mybatis-mapper-spring-boot/issues
