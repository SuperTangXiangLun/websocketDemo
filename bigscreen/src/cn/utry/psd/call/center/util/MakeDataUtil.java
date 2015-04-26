package cn.utry.psd.call.center.util;

import java.util.ArrayList;
import java.util.List;

import cn.utry.psd.call.center.websocket.bo.CopyOfUserBo;
import cn.utry.psd.call.center.websocket.bo.UserBo;

/**
 * deprecated:数据装包工具类
 * 
 * date 2015-03-12
 * 
 * @author Tang
 */
public class MakeDataUtil {
	/**
	 * deprecated: 组装单行数据
	 * 
	 * date 2015-03-12
	 * 
	 * @author sharkTang
	 * @param List
	 *            <T> objParam
	 */
	public static <T> String makeListData(List<T> objParam) {
		// 属性与属性之间用逗号(,)隔开,换行用&符号隔开,数据组用$隔开
		StringBuffer returnStrData = new StringBuffer();
		if (null != objParam) {
			for (T obj : objParam) {
				String objString = obj.toString();
				returnStrData.append(objString).append(
						WebSocketConstants.YU_STR_ENG);
			}
		}
		return returnStrData.length() <= 0 ? "" : returnStrData.toString()
				.substring(0, returnStrData.length() - 1);
	}

	/**
	 * deprecated: 组装数据组数据
	 * 
	 * date 2015-03-12
	 * 
	 * @author sharkTang
	 * @param List
	 *            <List<Object>>
	 */
	public static String makeDataListObj(List<List<Object>> objParam) {
		StringBuffer str = new StringBuffer();
		if (null != objParam) {
			for (List<Object> obj : objParam) {
				str.append(makeListData(obj)).append(
						WebSocketConstants.USA_STR_ENG);
			}
		}
		if (str.length() > 0) {
			return str.length() <= 0 ? "" : str.toString().substring(0,
					str.length() - 1);
		} else {
			return "";
		}
	}

	/**
	 * deprecated: 组装模拟数据
	 * 
	 * date 2015-03-12
	 * 
	 * @author sharkTang
	 * @return List<List<Object>>
	 */
	public static List<List<Object>> setUserDataObj() {
		List<Object> users = getUserData();
		List<Object> users1 = getUserDataObj();
		List<List<Object>> a = new ArrayList();
		a.add(users);
		a.add(users1);
		return a;
	}

	// 模拟数据
	public static List<Object> getUserData() {
		// 模拟50条数据做测试
		List<Object> users = new ArrayList<Object>();
		for (int i = 1; i <= WebSocketConstants.USERSIZE; i++) {
			UserBo userbo = new UserBo();
			userbo.setUserName("userName" + i);
			if (i % 2 == 0) {
				userbo.setUsex("男");
			} else {
				userbo.setUsex("女");
			}
			userbo.setInfo("这是第" + i + "个用户 ");
			users.add(userbo);
		}
		return users;
	}

	// 模拟数据
	public static List<Object> getUserDataObj() {
		// 模拟50条数据做测试
		List<Object> users = new ArrayList<Object>();
		for (int i = 1; i <= WebSocketConstants.USERSIZE; i++) {
			CopyOfUserBo userbo = new CopyOfUserBo();
			if (i % 2 == 0) {
				userbo.setUsex("男");
			} else {
				userbo.setUsex("女");
			}
			userbo.setInfo("这是第" + i + "个用户");
			users.add(userbo);
		}
		return users;
	}

}
