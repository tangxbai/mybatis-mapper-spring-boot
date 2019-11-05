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

import java.beans.PropertyDescriptor;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.sql.DataSource;

import org.apache.ibatis.mapping.DatabaseIdProvider;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.scripting.LanguageDriver;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.type.TypeHandler;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.boot.autoconfigure.ConfigurationCustomizer;
import org.mybatis.spring.boot.autoconfigure.MybatisAutoConfiguration;
import org.mybatis.spring.boot.autoconfigure.MybatisLanguageDriverAutoConfiguration;
import org.mybatis.spring.boot.autoconfigure.MybatisProperties;
import org.mybatis.spring.boot.autoconfigure.SpringBootVFS;
import org.springframework.beans.BeanWrapperImpl;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.condition.ConditionalOnSingleCandidate;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Bean;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import com.viiyue.plugins.mybatis.spring.MyBatisMapperSqlSessionFactoryBean;

/**
 * Mybatis-mapper's springboot auto-configuration startup class
 *
 * @author tangxbai
 * @since 1.3.0
 */
@org.springframework.context.annotation.Configuration
@EnableConfigurationProperties( { MybatisProperties.class, MybatisMapperProperties.class } )
@ConditionalOnProperty( prefix = MybatisMapperProperties.CONFIG_PREFIX, name = "enable", matchIfMissing = true, havingValue = "true" )
@ConditionalOnClass( { SqlSessionFactory.class, SqlSessionFactoryBean.class, MyBatisMapperSqlSessionFactoryBean.class } )
@ConditionalOnSingleCandidate( DataSource.class )
@AutoConfigureBefore( MybatisAutoConfiguration.class )
@AutoConfigureAfter( { DataSourceAutoConfiguration.class, MybatisLanguageDriverAutoConfiguration.class, MybatisMapperLanguageDriverAutoConfiguration.class } )
public class MybatisMapperAutoConfiguration implements InitializingBean, ApplicationListener<ContextRefreshedEvent> {

	private final MybatisProperties properties;
	private final MybatisMapperProperties mapperProperties;
	private final Interceptor [] interceptors;
	private final TypeHandler<?> [] typeHandlers;
	private final LanguageDriver [] languageDrivers;
	private final ResourceLoader resourceLoader;
	private final DatabaseIdProvider databaseIdProvider;
	private final List<ConfigurationCustomizer> configurationCustomizers;
	private final MyBatisMapperSqlSessionFactoryBean factory = new MyBatisMapperSqlSessionFactoryBean();

	@SuppressWarnings( "rawtypes" )
	public MybatisMapperAutoConfiguration( MybatisProperties properties, MybatisMapperProperties mapperProperties,
			ObjectProvider<Interceptor[]> interceptorsProvider, ObjectProvider<TypeHandler[]> typeHandlersProvider,
			ObjectProvider<LanguageDriver[]> languageDriversProvider, ResourceLoader resourceLoader,
			ObjectProvider<DatabaseIdProvider> databaseIdProvider,
			ObjectProvider<List<ConfigurationCustomizer>> configurationCustomizersProvider ) {
		this.properties = properties;
		this.mapperProperties = mapperProperties;
		this.interceptors = interceptorsProvider.getIfAvailable();
		this.typeHandlers = typeHandlersProvider.getIfAvailable();
		this.languageDrivers = languageDriversProvider.getIfAvailable();
		this.resourceLoader = resourceLoader;
		this.databaseIdProvider = databaseIdProvider.getIfAvailable();
		this.configurationCustomizers = configurationCustomizersProvider.getIfAvailable();
	}
	
	@Bean
	@ConditionalOnMissingBean
	public SqlSessionFactory sqlSessionFactory( DataSource dataSource ) throws Exception {
		boolean isEnableXmlSyntaxParsing = this.mapperProperties.isEnableXmlSyntaxParsing();
		
		this.factory.setDataSource( dataSource );
		this.factory.setVfs( SpringBootVFS.class );
		this.factory.setEnableLogger( this.mapperProperties.isEnableLogger() );
		this.factory.setEnableRuntimeLog( this.mapperProperties.isEnableRuntimeLog() );
		this.factory.setEnableMapperScanLog( this.mapperProperties.isEnableMapperScanLog() );
		this.factory.setEnableCompilationLog( this.mapperProperties.isEnableCompilationLog() );
		this.factory.setEnableXmlSyntaxParsing( isEnableXmlSyntaxParsing );
		this.factory.setEnableKeywordsToUppercase( this.mapperProperties.isEnableKeywordsToUppercase() );
		
		if ( StringUtils.hasText( this.mapperProperties.getDatabaseColumnStyle() ) ) {
			this.factory.setDatabaseColumnStyle( this.mapperProperties.getDatabaseColumnStyle() );
		}
		if ( StringUtils.hasText( this.properties.getConfigLocation() ) ) {
			this.factory.setConfigLocation( this.resourceLoader.getResource( this.properties.getConfigLocation() ) );
		}
		if ( this.properties.getConfigurationProperties() != null ) {
			this.factory.setConfigurationProperties( this.properties.getConfigurationProperties() );
		}
		applyConfiguration( this.factory );
		
		if ( !ObjectUtils.isEmpty( this.interceptors ) ) {
			this.factory.setPlugins( this.interceptors );
		}
		if ( this.databaseIdProvider != null ) {
			this.factory.setDatabaseIdProvider( this.databaseIdProvider );
		}
		if ( StringUtils.hasLength( this.properties.getTypeAliasesPackage() ) ) {
			this.factory.setTypeAliasesPackage( this.properties.getTypeAliasesPackage() );
		}
		if ( this.properties.getTypeAliasesSuperType() != null ) {
			this.factory.setTypeAliasesSuperType( this.properties.getTypeAliasesSuperType() );
		}
		if ( StringUtils.hasLength( this.properties.getTypeHandlersPackage() ) ) {
			this.factory.setTypeHandlersPackage( this.properties.getTypeHandlersPackage() );
		}
		if ( !ObjectUtils.isEmpty( this.typeHandlers ) ) {
			this.factory.setTypeHandlers( this.typeHandlers );
		}
		if ( !ObjectUtils.isEmpty( this.properties.resolveMapperLocations() ) ) {
			this.factory.setMapperLocations( this.properties.resolveMapperLocations() );
		}
		Set<String> factoryPropertyNames = Stream
				.of( new BeanWrapperImpl( SqlSessionFactoryBean.class ).getPropertyDescriptors() )
				.map( PropertyDescriptor::getName ).collect( Collectors.toSet() );
		Class<? extends LanguageDriver> defaultLanguageDriver = this.properties.getDefaultScriptingLanguageDriver();

		// Need to mybatis-spring 2.0.2+
		if ( factoryPropertyNames.contains( "scriptingLanguageDrivers" ) && !ObjectUtils.isEmpty( this.languageDrivers ) ) {
			this.factory.setScriptingLanguageDrivers( this.languageDrivers );
			if ( !isEnableXmlSyntaxParsing && defaultLanguageDriver == null && this.languageDrivers.length == 1 ) {
				defaultLanguageDriver = this.languageDrivers[ 0 ].getClass();
			}
		}
		
		// Need to mybatis-spring 2.0.2+
		if ( !isEnableXmlSyntaxParsing && factoryPropertyNames.contains( "defaultScriptingLanguageDriver" ) ) {
			this.factory.setDefaultScriptingLanguageDriver( defaultLanguageDriver );
		}
		return factory.getObject();
	}
	
	@Override
	public void afterPropertiesSet() {
		checkConfigFileExists();
	}
	
	@Override
	public void onApplicationEvent( ContextRefreshedEvent event ) {
		this.factory.onApplicationEvent( event );
	}

	private void checkConfigFileExists() {
		if ( this.properties.isCheckConfigLocation() && StringUtils.hasText( this.properties.getConfigLocation() ) ) {
			Resource resource = this.resourceLoader.getResource( this.properties.getConfigLocation() );
			Assert.state( resource.exists(), "Cannot find config location: " + resource + 
				" (please add config file or check your Mybatis configuration)" );
		}
	}
	
	private void applyConfiguration( MyBatisMapperSqlSessionFactoryBean factory ) {
		Configuration configuration = this.properties.getConfiguration();
		if ( configuration == null && !StringUtils.hasText( this.properties.getConfigLocation() ) ) {
			configuration = new Configuration();
		}
		if ( configuration != null && !CollectionUtils.isEmpty( this.configurationCustomizers ) ) {
			for ( ConfigurationCustomizer customizer : this.configurationCustomizers ) {
				customizer.customize( configuration );
			}
		}
		factory.setConfiguration( configuration );
	}

}
