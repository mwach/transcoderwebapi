package itti.com.pl.transcoder.service.aop;

import java.lang.reflect.Method;

import org.apache.commons.logging.LogFactory;
import org.springframework.aop.MethodBeforeAdvice;

public class LoggerAdvice implements MethodBeforeAdvice{

	@Override
	public void before(Method methodName, Object[] args, Object instance) throws Throwable {
		LogFactory.getLog(instance.getClass()).info(String.format("%s, %s", methodName.getName(), args));
	}

}
