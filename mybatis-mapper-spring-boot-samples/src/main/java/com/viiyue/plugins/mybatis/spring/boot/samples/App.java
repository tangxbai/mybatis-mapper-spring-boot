package com.viiyue.plugins.mybatis.spring.boot.samples;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Repository;

import com.viiyue.plugins.mybatis.spring.boot.samples.mapper.AccountMapper;
import com.viiyue.plugins.mybatis.spring.boot.samples.service.AccountServiceImpl;

@SpringBootApplication
@MapperScan( basePackages = "com.viiyue.plugins.mybatis.spring.boot.samples.mapper", annotationClass = Repository.class )
public class App {
	
	public static void main( String [] args ) {
		ApplicationContext context = SpringApplication.run( App.class, args );
		System.out.println( context.getBean( AccountMapper.class ).selectAll() );
		System.out.println( context.getBean( AccountServiceImpl.class ).loginByName( "tangxbai", "tangxbai@hotmail.com" ) );
	}

}
