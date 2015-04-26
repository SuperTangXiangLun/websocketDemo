package cn.utry.psd.call.center.websocket.thread;

import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.logging.Logger;

import javax.websocket.Session;

import cn.utry.psd.call.center.util.GetBeanInSpringContextUtils;
import cn.utry.psd.call.center.util.WebSocketManager;
import cn.utry.psd.call.center.websocket.server.UtryWebSocketServer;
import cn.utry.psd.call.center.websocket.start.DaPingWebSocketServerStart;
import cn.utry.psd.call.center.websocket.vo.WebSocketBo;

public class DaPingThreadAction implements Runnable {
	private static int threadCount = 0;
	private Logger logger_;

	public DaPingThreadAction() {
		logger_ = Logger.getLogger(this.getClass().getName());
	}

	@Override
	public void run() {
		while (true) {
			try {
				Thread.sleep(5000);
				// System.out.println(threadCount++ + "   ");
				doThreadFunction();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				logger_.info("数据推送主线程发生异常！");
			}
		}
	}

	/**
	 * 创建一个线程，随容器启动运行，不受其他程序影响而终止
	 * 
	 * 这个线程先获取数据，再根据已连接的websocket客户端数量推送数据
	 * 
	 * date 2015-03-06
	 * 
	 * @author sharkTang
	 */
	public void doThreadFunction() {
		try {
			// TODO Auto-generated method stub
			// 获得已连接的websocket服务数
			// Map<Integer, WebSocketBo> webSocketMap =
			// PuFaBigPingWebSocketServer.webSocketMap;
			// int webSocketConntionSize = 0;
			// if (null != webSocketMap) {
			// webSocketConntionSize = webSocketMap.size();
			// }
			//
			int webSocketConntionSize = WebSocketManager.webSocketClientCount;
			// System.out
			// .println("webSocketConntionSize:" + webSocketConntionSize);
			if (webSocketConntionSize > 0) {
				Set<String> webQueryArray = WebSocketManager.webQueryStringSet;
				if (null != webQueryArray && webQueryArray.size() > 0) {
					for (String beanNameString : webQueryArray) {
						// 根据queryString拿bean
						Object obj = GetBeanInSpringContextUtils
								.getObjectByContext(beanNameString);
						if (null != obj) {
							UtryWebSocketServer utryWebSocketServer = (UtryWebSocketServer) obj
									.getClass().cast(obj);
							Map<Integer, WebSocketBo> webSocketMap = utryWebSocketServer
									.getWebSocketMap();
							Map<Integer, Session> webSessionMap = utryWebSocketServer
									.getWebSesssionMap();
							String strData = utryWebSocketServer.getSentData();
							// System.out.println(strData);
							doSomeTask(webSocketMap, webSessionMap, strData);
							// System.out.println(webSocketMap.size());
						}

					}
				}

			}
		} catch (Exception e) {
			// TODO: handle exception
		}
	}

	private static void doSomeTask(Map<Integer, WebSocketBo> webSocketMap,
			Map<Integer, Session> webSessionMap, String strData) {
		for (Entry<Integer, WebSocketBo> entry : webSocketMap.entrySet()) {
			DaPingTreadPool treadPool = DaPingWebSocketServerStart.executor;
			// 根据当前启动一个新线程(线程池) 异步发送数据
			// 获得线程池
			if (null == treadPool) {
				treadPool = DaPingTreadPool.getUtryTreadPool();
			}
			treadPool.execute(new DoTaskThread(entry.getKey(), strData,
					webSessionMap));
		}
	}
}
