package cn.utry.psd.call.center.websocket.thread;


import java.util.List;
import java.util.Map;
import java.util.concurrent.locks.ReentrantLock;

import utry.common.JedisService;
import cn.utry.psd.call.center.util.GetBeanInSpringContextUtils;
import cn.utry.psd.call.center.util.MakeDataUtil;
import cn.utry.psd.call.center.util.WebSocketConstants;
import cn.utry.psd.call.center.websocket.server.DataInitServer;

/**
 * 数据获取主线程
 * 
 * date 2015-03-16
 * 
 * @author sharkTang
 * 
 */
public class DataFindAction implements Runnable {
	private static JedisService jedisFactoryAction;
	// 锁处理
	private final static ReentrantLock lock = new ReentrantLock();
	// 全局Map数据对象
	public static Map<String, List<List<Object>>> listMapPublic = null;
	// 全局缓存List数据对象
	public static List<List<Object>> objListPublic = null;
	// 需要发送的数据
	public static String sendString = WebSocketConstants.SPACESTR_ENG;
	private DataInitServer dataInitServer = null;

	public DataFindAction() {

	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		while (true) {
			try {
				Thread.sleep(5000);// 每10秒获取一次最新数据,减缓redis压力
				if (null == jedisFactoryAction) {
					jedisFactoryAction = GetBeanInSpringContextUtils.getJedisServiceByContext();
				}
				List<List<Object>> listObjsOne = getDataFromRedisFunction(jedisFactoryAction);
				// 筛选数据
				List<List<Object>> listObjsTwo = putDataInList(listObjsOne);
				// 筛选完成后存入静态字符串
				sendString = MakeDataUtil.makeDataListObj(listObjsTwo);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	/**
	 * 从redis获取数据
	 * 
	 * date 2015-03-16
	 * 
	 * @author sharkTang
	 */
	public List<List<Object>> getDataFromRedisFunction(JedisService jedisFactory) {
		if (null != dataInitServer) {
			dataInitServer = new DataInitServer();
		}
		List<List<Object>> makeDatas = dataInitServer.getNewData(jedisFactory);
		return null == makeDatas ? null : makeDatas;
	}

	/**
	 * 对全局map数据操作(加锁)
	 * 
	 * date 2015-03-16
	 * 
	 * @param map
	 *            全局数据存储Map
	 * @param mapKey
	 *            存入的key
	 * @param object
	 *            存入的Value
	 * @param action
	 *            执行动作,true表示add,false 表示remove
	 * @author sharkTang
	 */
	private void publicMapAction(Map map, String mapKey, Object object,
			boolean action) {
		try {
			lock.lock();
			if (action) {
				map.put(mapKey, object);
			} else {
				map.remove(mapKey);
			}
		} catch (Exception e) {
			// TODO: handle exception
			lock.unlock();
		} finally {
			lock.unlock();
		}
	}

	/**
	 * 筛选数据
	 * 
	 * date 2015-03-16
	 * 
	 * @author sharkTang
	 */
	private List<List<Object>> putDataInList(List<List<Object>> objs) {
		// 因现在是使用模拟数据，筛选数据没有实际意义，只是获取而已!
		return objs;
	}

	/**
	 * 对全局缓存数据操作，装包成字符串
	 * 
	 * 缓存完后删除全局缓存List数据
	 * 
	 * date 2015-03-16
	 * 
	 * @author sharkTang
	 */
	public static String getSendDataString() {
		String str = "";
		if (null != objListPublic && objListPublic.size() > 0) {
			str = MakeDataUtil.makeDataListObj(objListPublic);
		}
		sendString = str;
		return str;
	}
}
