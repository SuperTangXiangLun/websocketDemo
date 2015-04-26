package cn.utry.psd.call.center.websocket.server;

import org.springframework.stereotype.Service;

import cn.utry.psd.call.center.websocket.interfaces.IJedisSeviceConfig;

@Service
public class PufaJedisSevice   implements IJedisSeviceConfig {

	@Override
	public String getPermissionName() {
		// TODO Auto-generated method stub
		return "jedisService";
	}

	@Override
	public String getIJedisSeviceUrl() {
		// TODO Auto-generated method stub
		return null;
	}

}
