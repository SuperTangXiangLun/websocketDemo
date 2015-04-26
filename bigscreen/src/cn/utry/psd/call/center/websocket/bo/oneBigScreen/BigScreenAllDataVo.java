package cn.utry.psd.call.center.websocket.bo.oneBigScreen;

import java.io.Serializable;

public class BigScreenAllDataVo implements Serializable {
	private static final long serialVersionUID = 9842949201503252L;
	private int qrrs;
	private int jtrs;
	private int kxrs;
	private int qtrs;
	private int pjjtsc;
	private int fangqiliang;

	public int getQrrs() {
		return qrrs;
	}

	public void setQrrs(int qrrs) {
		this.qrrs = qrrs;
	}

	public int getJtrs() {
		return jtrs;
	}

	public void setJtrs(int jtrs) {
		this.jtrs = jtrs;
	}

	public int getKxrs() {
		return kxrs;
	}

	public void setKxrs(int kxrs) {
		this.kxrs = kxrs;
	}

	public int getQtrs() {
		return qtrs;
	}

	public void setQtrs(int qtrs) {
		this.qtrs = qtrs;
	}

	public int getPjjtsc() {
		return pjjtsc;
	}

	public void setPjjtsc(int pjjtsc) {
		this.pjjtsc = pjjtsc;
	}

	public int getFangqiliang() {
		return fangqiliang;
	}

	public void setFangqiliang(int fangqiliang) {
		this.fangqiliang = fangqiliang;
	}

	@Override
	public String toString() {
		StringBuffer str = new StringBuffer();
		str.append(qrrs).append(",").append(jtrs).append(",").append(kxrs)
				.append(",").append(qtrs).append(",").append(pjjtsc)
				.append(",").append(fangqiliang);
		return str.toString();
	}
}
