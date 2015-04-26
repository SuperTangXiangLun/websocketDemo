package cn.utry.psd.call.center.websocket.server;

import java.util.List;

import utry.common.JedisService;
import cn.utry.psd.call.center.util.GetBeanInSpringContextUtils;
import cn.utry.psd.call.center.util.MakeDataUtil;

/**
 * 获取初始化数据，用于客户端初始化连接时的数据获取并推送
 * 
 * date 2015-03-16
 * 
 * @author sharkTang
 * 
 */

public class DataInitServer {
	private static JedisService jedisFactory = null;

	/**
	 * 从redis获取最新的初始化数据
	 * 
	 * date 2015-03-16
	 * 
	 * @author sharkTang
	 * 
	 */
	public static String getNewInitData() {
		jedisFactory = GetBeanInSpringContextUtils.getJedisServiceByContext();
		String dataInitString = null;
		if (null != jedisFactory) {
			setInitData(jedisFactory);
			List<List<Object>> makeDatas = (List<List<Object>>) jedisFactory
					.getRedisObject("makeDatas");/**/// 从redis获取初始化数据
			dataInitString = MakeDataUtil.makeDataListObj(makeDatas);
		}
		return (null == dataInitString || "".equals(dataInitString)) ? ""
				: dataInitString + "从redis获取初始化数据测试";
	}

	/**
	 * 模拟数据
	 * 
	 * date 2015-03-17
	 * 
	 * @author sharkTang
	 * 
	 */
	public static void setInitData(JedisService jedisFactory) {
		// 充redis获取数据,这里手动设置
		// 模拟存数据到redis
		getNewData(jedisFactory);
	}

	/**
	 * 模拟数据
	 * 
	 * date 2015-03-17
	 * 
	 * @author sharkTang
	 * 
	 */
	public static List<List<Object>> getNewData(JedisService jedisFactory) {
		if (null == jedisFactory) {
			jedisFactory = GetBeanInSpringContextUtils.getJedisServiceByContext();
		}
		List<List<Object>> makeDatas = MakeDataUtil.setUserDataObj();
		jedisFactory.saveRedisObject("makeDatas", makeDatas);
		return (List<List<Object>>) jedisFactory.getRedisObject("makeDatas");
	}
}
