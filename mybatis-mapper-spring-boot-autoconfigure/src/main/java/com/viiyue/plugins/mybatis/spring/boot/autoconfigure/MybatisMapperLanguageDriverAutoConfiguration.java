/**
 * Copyright (C) 2019 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.viiyue.plugins.mybatis.spring.boot.autoconfigure;

import org.apache.ibatis.scripting.LanguageDriver;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.viiyue.plugins.mybatis.MyBatisMapperLanguageDriver;

/**
 * Mybatis-mapper about language-driven automatic configuration startup class
 *
 * @author tangxbai
 * @since 1.3.0
 */
@Configuration
@ConditionalOnClass( LanguageDriver.class )
@ConditionalOnProperty( prefix = MybatisMapperProperties.CONFIG_PREFIX, name = "enable", matchIfMissing = true, havingValue = "true" )
public class MybatisMapperLanguageDriverAutoConfiguration {

	@Bean
	@ConditionalOnMissingBean
	@ConditionalOnProperty( prefix = MybatisMapperProperties.CONFIG_PREFIX, name = "enable-xml-syntax-parsing", matchIfMissing = false, havingValue = "true" )
	MyBatisMapperLanguageDriver mapperLanguageDriver() {
		return new MyBatisMapperLanguageDriver();
	}

}
