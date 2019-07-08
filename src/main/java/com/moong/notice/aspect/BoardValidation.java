package com.moong.notice.aspect;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.springframework.core.annotation.AliasFor;

import com.moong.notice.domain.member.MemberRules;

@Documented
@Inherited
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface BoardValidation {
	@AliasFor("accessType")
	public MemberRules[] value() default {MemberRules.MEMBER, MemberRules.ADMIN};
	
	@AliasFor("value")
	public MemberRules[] accessType() default {MemberRules.MEMBER, MemberRules.ADMIN};
}
