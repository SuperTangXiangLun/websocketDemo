package cn.utry.psd.call.center.data.set;

import java.io.Serializable;

public class ChartDataBo implements Serializable{
	private static final long serialVersionUID = 9842949201503232L;
	private int hwl;// 话务量
	private float fwsp;// 服务水平
	private float sjfwsp;// 实际服务水平
	private float yqsp;// 预期值

	public int getHwl() {
		return hwl;
	}

	public void setHwl(int hwl) {
		this.hwl = hwl;
	}

	public float getFwsp() {
		return fwsp;
	}

	public void setFwsp(float fwsp) {
		this.fwsp = fwsp;
	}

	public float getSjfwsp() {
		return sjfwsp;
	}

	public void setSjfwsp(float sjfwsp) {
		this.sjfwsp = sjfwsp;
	}

	public float getYqsp() {
		return yqsp;
	}

	public void setYqsp(float yqsp) {
		this.yqsp = yqsp;
	}

	@Override
	public String toString() {
		StringBuffer str = new StringBuffer();
		str.append(hwl).append(",").append(fwsp).append(",").append(sjfwsp)
				.append(",").append(yqsp);
		return str.toString();
	}

}
