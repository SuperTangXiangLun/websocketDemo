package cn.utry.psd.call.center.util;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Service;

import utry.common.JedisService;
import cn.utry.psd.call.center.websocket.interfaces.IJedisSeviceConfig;

/**
 * 权限的常用方法
 * 
 * @author 唐远
 * 
 *         2015-03-21
 */
@Service
public class GetBeanInSpringContextUtils implements ApplicationContextAware {
	/**
	 * 根据spring容器得到权限的服务
	 * 
	 * @param context
	 * @return
	 */
	public static ApplicationContext context;

	public static JedisService getJedisServiceByContext() {
		JedisService service = null;
		try {
			IJedisSeviceConfig dapingConfig = context
					.getBean(IJedisSeviceConfig.class);
			String permissionName = dapingConfig.getPermissionName();
			service = (JedisService) context.getBean(permissionName);
		} catch (NoSuchBeanDefinitionException e) {
			service = (JedisService) context.getBean("jedisService");
		}
		return service;
	}

	public static Object getObjectByContext(String beanNameString) {
		Object objectClass = null;
		try {
			objectClass = context.getBean(beanNameString);
		} catch (NoSuchBeanDefinitionException e) {
		}
		return objectClass;
	}

	@Override
	public void setApplicationContext(ApplicationContext context)
			throws BeansException {
		this.context = context;
		getJedisServiceByContext();
	}
}
