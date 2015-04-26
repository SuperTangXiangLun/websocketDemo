package cn.utry.psd.call.center.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import utry.bo.LoginBean;
import utry.common.LoginInfoParams;

public class LoginInfoHandler extends HandlerInterceptorAdapter {
	/**
	 * 请求执行前拦截
	 * 
	 * @param request
	 *            请求对象
	 * @param response
	 *            响应对象
	 * @param
	 */
	@Override
	public boolean preHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler) throws Exception {

		LoginBean lb = new LoginBean();
		lb.setAccountID("1001");
		lb.setCompanyID("001");
		lb.setLoginName("1001");
		lb.setRealName("张三");

		LoginInfoParams.putSp(lb);
		return true;
	}

}