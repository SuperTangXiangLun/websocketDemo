package cn.utry.psd.call.center.service.commom;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;

public interface IBaseAspect {
	
	void doBefore(JoinPoint jp);
	
	Object doAround(ProceedingJoinPoint pjp) throws Throwable;
	
	void doAfter(JoinPoint jp);
	
	void doThrowing(JoinPoint jp, Throwable ex);
}
