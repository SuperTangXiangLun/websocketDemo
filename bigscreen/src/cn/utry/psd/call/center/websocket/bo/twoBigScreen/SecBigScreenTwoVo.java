package cn.utry.psd.call.center.websocket.bo.twoBigScreen;

import java.io.Serializable;

import cn.utry.psd.call.center.websocket.bo.RumTimeFromRedis;

/**
 * 实时监控按资源线分组数据Vo
 * 
 * date 2015-03-21
 * 
 * @author sharkTang
 * 
 */
public class SecBigScreenTwoVo implements Serializable {
	private static final long serialVersionUID = 784294920150402022L;
	private String resoureLine;// 资源线
	private int skillLineCount;// 进线量
	private String connectRate;// 接通率
	private int waitPhoneCount;// 等待电话量
	private int waitTime;// 等待时间（秒）
	private float serviceHorizontal;// 服务水平
	private String fwspString;
	private int jieqiCount;// 接起量
	private String isRedFlag;//是否红色

	@Override
	public String toString() {
		StringBuffer str = new StringBuffer();
		str.append(resoureLine).append(",").append(skillLineCount).append(",").append(connectRate).append(",")
				.append(waitPhoneCount).append(",").append(waitTime)
				.append(",").append(fwspString).append(",").append(isRedFlag);
		return str.toString();
	}
	
	public String getIsRedFlag() {
		return isRedFlag;
	}

	public void setIsRedFlag(String isRedFlag) {
		this.isRedFlag = isRedFlag;
	}

	public String getFwspString() {
		return fwspString;
	}

	public void setFwspString(String fwspString) {
		this.fwspString = fwspString;
	}

	public int getJieqiCount() {
		return jieqiCount;
	}

	public void setJieqiCount(int jieqiCount) {
		this.jieqiCount = jieqiCount;
	}

	public String getResoureLine() {
		return resoureLine;
	}

	public void setResoureLine(String resoureLine) {
		this.resoureLine = resoureLine;
	}

	public int getSkillLineCount() {
		return skillLineCount;
	}

	public void setSkillLineCount(int skillLineCount) {
		this.skillLineCount = skillLineCount;
	}

	public String getConnectRate() {
		return connectRate;
	}

	public void setConnectRate(String connectRate) {
		this.connectRate = connectRate;
	}

	public int getWaitPhoneCount() {
		return waitPhoneCount;
	}

	public void setWaitPhoneCount(int waitPhoneCount) {
		this.waitPhoneCount = waitPhoneCount;
	}

	public int getWaitTime() {
		return waitTime;
	}

	public void setWaitTime(int waitTime) {
		this.waitTime = waitTime;
	}

	public float getServiceHorizontal() {
		return serviceHorizontal;
	}

	public void setServiceHorizontal(float serviceHorizontal) {
		this.serviceHorizontal = serviceHorizontal;
	}

	//
	// 进线量 = 求和
	// 接通率 = 接起量/进线量
	// 放弃量 = 放弃量/进线量
	// 等待电话=求和
	// 等待时间 =求max（）
	// 安排人数 求和
	// 签入人数 求和
	// 服务水平 20秒内接起量/进线量

}
