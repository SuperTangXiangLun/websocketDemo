package cn.utry.psd.call.center.util;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * sharkTang
 */
public class WebSocketManager {
	public static int webSocketClientCount = 0;
	public static Set<String>webQueryStringSet=new HashSet();
	public static void main(String[] args) {
		webQueryStringSet.add("1");
		webQueryStringSet.add("2");
		webQueryStringSet.add("3");
		webQueryStringSet.add("3");
		System.out.println(webQueryStringSet.size());
		webQueryStringSet.remove("2");
		System.out.println(webQueryStringSet.size());
		for(String str:webQueryStringSet){
			System.out.println(str+"...");
		}
	}
}
