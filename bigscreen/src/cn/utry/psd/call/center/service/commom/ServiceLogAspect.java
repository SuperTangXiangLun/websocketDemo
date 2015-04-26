package cn.utry.psd.call.center.service.commom;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class ServiceLogAspect implements IBaseAspect {

	private Logger logger = LoggerFactory.getLogger(ServiceLogAspect.class);

	@Override
	public void doBefore(JoinPoint jp) {
	}

	@Override
	public Object doAround(ProceedingJoinPoint pjp) throws Throwable {

		long time = System.currentTimeMillis();
		Object retVal = pjp.proceed();
		time = System.currentTimeMillis() - time;
		logger.info("{}.{} =>> process time: {} ms", pjp.getTarget().getClass().getName(), pjp.getSignature().getName(),time);
		return retVal;
	}

	@Override
	public void doAfter(JoinPoint jp) {
	}

	@Override
	public void doThrowing(JoinPoint jp, Throwable ex) {
		logger.error("method=> " + jp.getTarget().getClass().getName() + "."
				+ jp.getSignature().getName() + " throw exception", ex);
	}
}
