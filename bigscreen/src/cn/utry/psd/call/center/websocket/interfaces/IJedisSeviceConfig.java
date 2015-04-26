package cn.utry.psd.call.center.websocket.interfaces;

public interface IJedisSeviceConfig {
	/**
	 * 得到对应的url
	 * 
	 * @author sharktang
	 * @date 2015-03-17
	 * @return
	 * 
	 */
	public String getIJedisSeviceUrl();

	/**
	 * 得到配置的权限对应的bean name
	 * 
	 * @author sharktang
	 * @date 2015-03-17
	 * @return
	 * 
	 */
	public String getPermissionName();
}
