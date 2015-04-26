package cn.utry.psd.call.center.websocket.thread;

import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

import javax.websocket.Session;

import cn.utry.psd.call.center.util.WebSocketConstants;

/**
 * WebSocket推送数据线程
 * 
 * date 2015-03-08
 * 
 * @author sharkTang
 * 
 */
public class DoTaskThread implements Runnable {
	private Map<Integer, Session> webSessionMap = new HashMap<Integer, Session>();
	private Integer webSocketHashCodeStatic = 0;
	public String sendDataStatic = "";
	private Logger logger_;

	public DoTaskThread() {
		logger_ = Logger.getLogger(this.getClass().getName());
	}

	public DoTaskThread(Integer webSocketHashCode, String sendData,
			Map<Integer, Session> webSessionMapParm) {
		// 构造方法初始化信息
		webSessionMap = webSessionMapParm;
		webSocketHashCodeStatic = webSocketHashCode;
		sendDataStatic = sendData;
	}

	/**
	 * deprecated: 发送数据方法，根据session的hashcode找对应sess客户端
	 * 
	 * 并发送数据，发送完return
	 * 
	 * date 2015-03-08
	 * 
	 * @author sharkTang
	 * @param session
	 * @param closeReason
	 */
	private void sendData() {
		if (null != webSessionMap && webSessionMap.size() > 0) {
			Session sess = webSessionMap.get(webSocketHashCodeStatic);
			try {
				if (null != sess && sess.isOpen()) {
					sess.getAsyncRemote().sendText(sendDataStatic);
				}
			} catch (Exception ex) {
				logger_.info(sess.hashCode() + " 客户端发送数据异常,异常信息:"
						+ ex.getMessage());
			}

		}
	}

	@Override
	public void run() {
		try {
			Thread.currentThread().sleep(1);
			sendData();
		} catch (InterruptedException ex) {
			// TODO Auto-generated catch block
			logger_.info("客户端发送数据线程异常,异常信息:" + ex.getMessage());
		}
	}

}
