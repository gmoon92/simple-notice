package com.moong.notice.aspect;

import java.util.Arrays;
import java.util.stream.IntStream;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.servlet.http.HttpSession;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.moong.notice.api.advice.exception.UserNotFoundException;
import com.moong.notice.domain.member.Member;
import com.moong.notice.repository.BoardRepository;
import com.moong.notice.service.dto.BoardParam;

/**
 * @author moong
 **/
@Aspect
@Component
public class BoardAspect {

	@PersistenceContext
	private EntityManager em;
	
	@Pointcut(value="@annotation(boardValidation)"
			,argNames="boardValidation"
			 )
	public void boardValidation(BoardValidation boardValidation) {}
	
	@Around(value="boardValidation(boardValidation)")
	public Object validMethod(ProceedingJoinPoint jp
							, BoardValidation boardValidation
							){
		
		Object target = null;
		try {
			Object[] args = isMemberAuthAndLoginMemberCheck(jp, boardValidation);
			target = jp.proceed(args);
			return target;
		} catch (Throwable e) {
			e.printStackTrace();
		}
		return target;
	}
	
	
	private Object[] isMemberAuthAndLoginMemberCheck(ProceedingJoinPoint jp
												,BoardValidation boardValidation){
		HttpSession session = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes())
																			  .getRequest()
																			  .getSession();
		if( session != null) {
			Member loginMember = (Member)session.getAttribute("loginUser");
			if(loginMember != null) {
				// 권한이 맞는지
				boolean isAuth = Arrays.stream(boardValidation.value())
							  		   .anyMatch(rule-> rule==loginMember.getRule());
				
				if(isAuth) {
					return findArg(jp.getArgs(), loginMember);
				}
			}
		}
		return jp.getArgs();
	}
	
	private Object[] findArg(Object[] args, Member loginMember) {
		int findIdx = IntStream.range(0, args.length)
				   		   	   .filter(i -> BoardParam.class.isInstance(args[i]))
				   		   	   .findFirst()
				   		   	   .orElse(-1);
		if(findIdx > -1) {
			BoardParam param = BoardParam.class.cast(args[findIdx]);
			
			// 로그인 유저id와 파라미터 유저id 비교
			if(!loginMember.getUId().equals(param.getU_id())) 
				throw new UserNotFoundException(param.getU_id());
			
			param.setMember(em.merge(loginMember));
			args[findIdx] = param;
		}
		return args;
	}
}
