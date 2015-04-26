package cn.utry.psd.call.center.websocket.bo;

import java.io.Serializable;

//模拟数据用
public class CopyOfUserBo implements Serializable {
	private static final long serialVersionUID = 9842949201503162L;
	private String usex;
	private String info;

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
		StringBuffer str = new StringBuffer();
		str.append(usex).append(",").append(info);
		return str.toString();
	}

}
