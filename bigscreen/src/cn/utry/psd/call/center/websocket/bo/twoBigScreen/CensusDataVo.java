package cn.utry.psd.call.center.websocket.bo.twoBigScreen;

import java.io.Serializable;

import cn.utry.psd.call.center.websocket.bo.RumTimeFromRedis;

/**
 * 大屏2柱状图
 * 
 * date 2015-04-02
 * 
 * @author sharkTang
 * 
 */
public class CensusDataVo implements Serializable {
	private static final long serialVersionUID = 784294920150402032L;
	private int aprs;// 安排人数
	private int qrrs;// 签入人数
	private int kxrs;// 空闲人数
	private int zxrs;// 在线人数
	private int jtrs;// 接听人数
	private int zxjcrs;// 在线就餐人数
	private int zxpxrs;// 在线培训人数
	private int zxwhrs;// 在线外呼人数
	private int shclrs;// 事后处理人数
	private int xxrs;// 小休人数
	private int hengding;// 恒定值
	private int countSize;// 总人数

	@Override
	public String toString() {
		StringBuffer str = new StringBuffer();
		str.append(aprs).append(",").append(qrrs).append(",").append(kxrs)
				.append(",").append(zxrs).append(",").append(jtrs).append(",")
				.append(zxjcrs).append(",").append(zxpxrs).append(",")
				.append(zxwhrs).append(",").append(shclrs).append(",")
				.append(xxrs).append(",").append(hengding).append(",")
				.append(countSize);
		return str.toString();
	}

	public int getAprs() {
		return aprs;
	}

	public void setAprs(int aprs) {
		this.aprs = aprs;
	}

	public int getQrrs() {
		return qrrs;
	}

	public void setQrrs(int qrrs) {
		this.qrrs = qrrs;
	}

	public int getKxrs() {
		return kxrs;
	}

	public void setKxrs(int kxrs) {
		this.kxrs = kxrs;
	}

	public int getZxrs() {
		return zxrs;
	}

	public void setZxrs(int zxrs) {
		this.zxrs = zxrs;
	}

	public int getJtrs() {
		return jtrs;
	}

	public void setJtrs(int jtrs) {
		this.jtrs = jtrs;
	}

	public int getZxjcrs() {
		return zxjcrs;
	}

	public void setZxjcrs(int zxjcrs) {
		this.zxjcrs = zxjcrs;
	}

	public int getZxpxrs() {
		return zxpxrs;
	}

	public void setZxpxrs(int zxpxrs) {
		this.zxpxrs = zxpxrs;
	}

	public int getZxwhrs() {
		return zxwhrs;
	}

	public void setZxwhrs(int zxwhrs) {
		this.zxwhrs = zxwhrs;
	}

	public int getShclrs() {
		return shclrs;
	}

	public void setShclrs(int shclrs) {
		this.shclrs = shclrs;
	}

	public int getXxrs() {
		return xxrs;
	}

	public void setXxrs(int xxrs) {
		this.xxrs = xxrs;
	}

	public int getHengding() {
		return hengding;
	}

	public void setHengding(int hengding) {
		this.hengding = hengding;
	}

	public int getCountSize() {
		return countSize;
	}

	public void setCountSize(int countSize) {
		this.countSize = countSize;
	}

}
