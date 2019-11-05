# Mybatis-mapper-spring-boot-autoconfigure
![version](https://img.shields.io/badge/release-1.3.0-blue) [![maven central](https://img.shields.io/badge/maven%20central-1.3.0-brightgreen)](https://maven-badges.herokuapp.com/maven-central/org.mybatis/mybatis) [![license](https://img.shields.io/badge/license-Apache%202.0-blue)](http://www.apache.org/licenses/LICENSE-2.0.html)

Mybatis-mapper关于springboot的自动装配组件，在springboot中是比较核心的一个部件，无法单独使用，需要通过mybatis-mapper-spring-boot-starter来导入，通过它能带给我们沉浸式的开发体验，极简化的配置，丰富的Api。



## 核心逻辑

- MybatisMapperAutoConfiguration - 核心自动装配类，用于自动载入mybatis-mapper插件功能

- MybatisMapperLanguageDriverAutoConfiguration - 在适当的场景下载入语言解析驱动

- MybatisMapperProperties - 关于springboot的插件配置属性

- META-INF/spring.factories - 自动装配类的列表清单



## 关于作者

- QQ群：947460272
- 邮箱：tangxbai@hotmail.com
- 掘金： https://juejin.im/user/5da5621ce51d4524f007f35f
- 简书： https://www.jianshu.com/u/e62f4302c51f
- Issuse：https://github.com/tangxbai/mybatis-mapper-spring-boot/issues
