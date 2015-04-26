package cn.utry.psd.call.center.websocket.bo.twoBigScreen;

import java.io.Serializable;

public class ChinaMapVo implements Serializable {
	private static final long serialVersionUID = 784294920150407022L;
	public int hwl;
	private String sname;
	private String bfb;

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

	public String getBfb() {
		return bfb;
	}

	public void setBfb(String bfb) {
		this.bfb = bfb;
	}

	@Override
	public String toString() {
		return sname + "," + hwl + "," + bfb;
	}

}
