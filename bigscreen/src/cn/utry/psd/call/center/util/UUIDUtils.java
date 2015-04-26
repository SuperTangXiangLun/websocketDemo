package cn.utry.psd.call.center.util;

/*类名：UUID工具类*/
/*
 	 * 创建人：chenjie 
 	 * 创建时间：2014/07/14
 	 * 备注：新建
*/
 

import java.util.UUID;

/**
 * UUID工具类
 */
public class UUIDUtils {
	
	/**
	 * 获取UUID
	 * @return 字符串
	 */
	public static String getUUID(){
		String uuid=UUID.randomUUID().toString();
		return uuid.replaceAll("-", "");
	}

}
