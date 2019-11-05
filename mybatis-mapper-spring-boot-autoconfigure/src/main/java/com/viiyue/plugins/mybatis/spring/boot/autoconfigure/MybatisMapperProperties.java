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

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * Mybatis-mapper preference configuration properties
 *
 * @author tangxbai
 * @since 1.3.0
 */
@ConfigurationProperties( prefix = MybatisMapperProperties.CONFIG_PREFIX, ignoreUnknownFields = true )
public class MybatisMapperProperties {

	public static final String CONFIG_PREFIX = "mybatis-mapper.setting";
	
	private boolean enableLogger; // default is true
	private boolean enableRuntimeLog; // default is true
	private boolean enableMapperScanLog; // default is true
	private boolean enableCompilationLog; // default is true
	private boolean enableXmlSyntaxParsing; // default is false
	private boolean enableKeywordsToUppercase; // default is false
	private String databaseColumnStyle; // defaults is '#'

	public boolean isEnableLogger() {
		return enableLogger;
	}

	public void setEnableLogger( boolean enableLogger ) {
		this.enableLogger = enableLogger;
	}

	public boolean isEnableRuntimeLog() {
		return enableRuntimeLog;
	}

	public void setEnableRuntimeLog( boolean enableRuntimeLog ) {
		this.enableRuntimeLog = enableRuntimeLog;
	}
	
	public boolean isEnableMapperScanLog() {
		return enableMapperScanLog;
	}

	public void setEnableMapperScanLog( boolean enableMapperScanLog ) {
		this.enableMapperScanLog = enableMapperScanLog;
	}

	public boolean isEnableCompilationLog() {
		return enableCompilationLog;
	}

	public void setEnableCompilationLog( boolean enableCompilationLog ) {
		this.enableCompilationLog = enableCompilationLog;
	}

	public boolean isEnableXmlSyntaxParsing() {
		return enableXmlSyntaxParsing;
	}

	public void setEnableXmlSyntaxParsing( boolean enableXmlSyntaxParsing ) {
		this.enableXmlSyntaxParsing = enableXmlSyntaxParsing;
	}

	public boolean isEnableKeywordsToUppercase() {
		return enableKeywordsToUppercase;
	}

	public void setEnableKeywordsToUppercase( boolean enableKeywordsToUppercase ) {
		this.enableKeywordsToUppercase = enableKeywordsToUppercase;
	}

	public String getDatabaseColumnStyle() {
		return databaseColumnStyle;
	}

	public void setDatabaseColumnStyle( String databaseColumnStyle ) {
		this.databaseColumnStyle = databaseColumnStyle;
	}

}
