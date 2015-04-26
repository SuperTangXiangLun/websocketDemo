package cn.utry.psd.call.center.websocket.bo;

import java.io.Serializable;

//模拟数据用
public class UserBo implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 9842949201503161L;
	private String userName;
	private String usex;
	private String info;

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getUsex() {
		return usex;
	}

	public void setUsex(String usex) {
		this.usex = usex;
	}

	public String getInfo() {
		return info;
	}

	public void setInfo(String info) {
		this.info = info;
	}

	@Override
	public String toString() {
		/*
		 * return "userName:" + userName + "," + "usex:" + usex + "," + "info:"
		 * + "info"+"userName:" + userName + "," + "usex:" + usex + "," +
		 * "info:" + "info"+"userName:" + userName + "," + "usex:" + usex + ","
		 * + "info:" + "info";
		 */
		StringBuffer str = new StringBuffer();
		str.append(userName).append(",").append(usex).append(",").append(info);
		return str.toString();
	}

}
