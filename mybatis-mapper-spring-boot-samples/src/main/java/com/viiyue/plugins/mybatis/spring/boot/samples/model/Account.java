package com.viiyue.plugins.mybatis.spring.boot.samples.model;

import com.viiyue.plugins.mybatis.annotation.member.Column;
import com.viiyue.plugins.mybatis.spring.boot.samples.common.BaseModel;
import com.viiyue.plugins.mybatis.spring.boot.samples.enums.Gender;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Account extends BaseModel {

	private String nickName;

	@Column( nullable = false )
	private String loginName;

	@Column( updatable = false )
	private String password;

	private String remark;
	private Gender gender;
	private Integer age;

}
