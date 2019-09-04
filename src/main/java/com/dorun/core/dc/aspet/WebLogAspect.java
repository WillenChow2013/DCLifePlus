package com.dorun.core.dc.aspet;

import com.dorun.core.dc.utils.Utils;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;

/**
 * 
 * @ClassName: WebLogAspect
 * @Description: 网络请求日志切面
 * @author aleex007
 * @date 2018年7月12日 下午5:10:07
 *
 */
@Aspect
@Component
@Slf4j
public class WebLogAspect {

	/**
	 * 	切入点
	 * 
	 * @author aleex007
	 * @date 2018年7月12日 下午5:09:42
	 */
	@Pointcut("execution(public * com.dorun.core..*.*(..))")
	public void webLog() {
	}
	
	@Before("webLog()")
	public void doBefore(JoinPoint joinPoint) {
		// 接收到请求，记录请求内容
		ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();

		HttpServletRequest request = attributes.getRequest();
		// 记录下请求内容
		log.info("[HTTP_METHOD:{}][IP:{}][CLASS_METHOD:{}][ARGS:{}]", Utils.getClientIpAddress(request),
				request.getMethod(), Utils.getClientIpAddress(request),
				joinPoint.getSignature().getDeclaringTypeName().concat(".").concat(joinPoint.getSignature().getName()),
				Arrays.toString(joinPoint.getArgs()));
	}
	
	@AfterReturning(returning = "ret", pointcut = "webLog()")
	public void doAfterReturning(Object ret) {
		// 处理完请求，返回内容
		log.info("RESPONSE:[{}]", ret);
	}

}
