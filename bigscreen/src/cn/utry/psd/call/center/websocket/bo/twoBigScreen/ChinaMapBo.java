package cn.utry.psd.call.center.websocket.bo.twoBigScreen;

import java.io.Serializable;

public class ChinaMapBo implements Serializable {
	private static final long serialVersionUID = 784294920150407012L;
	private int hwl;
	private String sname;
	private int countHwl;
	public int getHwl() {
		return hwl;
	}
	public void setHwl(int hwl) {
		this.hwl = hwl;
	}
	public String getSname() {
		return sname;
	}
	public void setSname(String sname) {
		this.sname = sname;
	}
	public int getCountHwl() {
		return countHwl;
	}
	public void setCountHwl(int countHwl) {
		this.countHwl = countHwl;
	}
	
}
