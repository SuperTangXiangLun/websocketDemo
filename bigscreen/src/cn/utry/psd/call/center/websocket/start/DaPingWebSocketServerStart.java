package cn.utry.psd.call.center.websocket.start;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.springframework.stereotype.Component;

import cn.utry.psd.call.center.websocket.server.BigScreenServer;
import cn.utry.psd.call.center.websocket.thread.DaPingThreadAction;
import cn.utry.psd.call.center.websocket.thread.DaPingTreadPool;
import cn.utry.psd.call.center.websocket.vo.WebSocketBo;

/**
 * 跟随服务器启动，初始化类
 * 
 * date 2015-03-06
 * 
 * @author sharkTang
 * 
 */
@Component
public class DaPingWebSocketServerStart {
	public static DaPingTreadPool executor =null;

	@PostConstruct
	public void threadInit() {
		// jedisFactory.saveRedisObject("ss", "ssdasdasds");
		// System.out.println(jedisFactory.getRedisObject("ss"));
		// TODO Auto-generated method stub
		// 获得已连接的websocket数
		Map<Integer, WebSocketBo> webSocketMap = BigScreenServer.webSocketMap;
		int webSocketConntionSize = 0;
		if (null != webSocketMap) {
			webSocketConntionSize = webSocketMap.size();
		}
		System.out.println("随tomcat启动。。。");
		DaPingThreadAction managerActionThread = new DaPingThreadAction();
		Thread thread = new Thread(managerActionThread);
		thread.start();
		System.out.println("线程启动完成。。。");

	}

}
