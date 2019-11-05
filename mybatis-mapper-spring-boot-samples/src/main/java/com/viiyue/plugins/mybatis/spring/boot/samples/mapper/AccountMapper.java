package com.viiyue.plugins.mybatis.spring.boot.samples.mapper;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.viiyue.plugins.mybatis.annotation.mark.EnableResultMap;
import com.viiyue.plugins.mybatis.mapper.Mapper;
import com.viiyue.plugins.mybatis.spring.boot.samples.bean.AccountDTO;
import com.viiyue.plugins.mybatis.spring.boot.samples.model.Account;

@Repository
public interface AccountMapper extends Mapper<Account, AccountDTO, Long> {
	
	@EnableResultMap
	List<AccountDTO> selectByLoginName( String loginName );
	
}
