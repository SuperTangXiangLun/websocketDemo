package cn.utry.psd.call.center.data.set;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Random;

import org.springframework.context.ApplicationContext;

import utry.common.JedisService;
import cn.utry.psd.call.center.util.GetBeanInSpringContextUtils;
import cn.utry.psd.call.center.util.MakeDataUtil;
import cn.utry.psd.call.center.util.UUIDUtils;
import cn.utry.psd.call.center.util.WebSocketConstants;
import cn.utry.psd.call.center.websocket.bo.RumTimeFromRedis;
import cn.utry.psd.call.center.websocket.bo.YuJingBo;
import cn.utry.psd.call.center.websocket.bo.twoBigScreen.CensusDataVo;
import cn.utry.psd.call.center.websocket.bo.twoBigScreen.SecBigScreenOneVo;
import cn.utry.psd.call.center.websocket.bo.twoBigScreen.SecBigScreenTwoVo;

public class BigScreenDataTwo {
	private static JedisService jedisFactoryAction;

	private static List<RumTimeFromRedis> makeRunTimeDate() {
		List<RumTimeFromRedis> rumTimeFromRedis = new ArrayList<RumTimeFromRedis>();
		for (int i = 1; i <= 30; i++) {
			RumTimeFromRedis rumtimeData = new RumTimeFromRedis();
			rumtimeData.setUid(UUIDUtils.getUUID());
			String skillLine = "";
			if (i % 3 == 0) {
				skillLine = "highend";
			} else if (i % 3 == 1) {
				skillLine = "platinum";
			} else if (i % 3 == 2) {
				skillLine = "putin";
			}
			rumtimeData.setSkillLine(skillLine);
			if (i % 2 == 0) {
				rumtimeData.setResoureLine(WebSocketConstants.FIVEBABA);
			} else {
				rumtimeData.setResoureLine(WebSocketConstants.ZHONGDAA);
			}
			rumtimeData.setJieqiCount(new Random().nextInt(100));
			rumtimeData.setSkillLineCount(new Random().nextInt(100));
			rumtimeData.setConnectRate(new Random().nextInt(5));
			rumtimeData.setBackOutCount(new Random().nextInt(3));
			rumtimeData.setWaitPhoneCount(new Random().nextInt(3));
			rumtimeData.setWaitTime(new Random().nextInt(4));
			rumtimeData.setPlanPersonCount(new Random().nextInt(3));
			rumtimeData.setCheckinPersonCount(new Random().nextInt(3));
			rumtimeData.setServiceHorizontal(new Random().nextInt(40));
			rumTimeFromRedis.add(rumtimeData);
		}
		return rumTimeFromRedis;
	}

	public static void setDataInRedis() {
		List<RumTimeFromRedis> rumTimeFromRedis = makeRunTimeDate();
		if (null == jedisFactoryAction) {
			jedisFactoryAction = GetBeanInSpringContextUtils
					.getJedisServiceByContext();
		}
		jedisFactoryAction
				.saveRedisObject("rumTimeFromRedis", rumTimeFromRedis);
	}

	public static List<Object> setYujingBo() {
		List<Object> yjbList = new ArrayList<>();
		YuJingBo yjb1 = new YuJingBo();
		yjb1.setYujingname("zongfwsp");
		yjb1.setYujinzhi(0);
		yjbList.add(yjb1);
		YuJingBo yjb2 = new YuJingBo();
		yjb2.setYujingname("zongjtl");
		yjb2.setYujinzhi(40);
		yjbList.add(yjb2);

		YuJingBo yjb3 = new YuJingBo();
		yjb3.setYujingname("588fwsp");
		yjb3.setYujinzhi(10);
		yjbList.add(yjb3);

		YuJingBo yjb4 = new YuJingBo();
		yjb4.setYujingname("588jtl");
		yjb4.setYujinzhi(10);
		yjbList.add(yjb4);
		return yjbList;
	}

	public static List<Object> setDataBySkillLine() {
		long begin = System.currentTimeMillis();

		/*
		 * if (null == jedisFactoryAction) { jedisFactoryAction =
		 * GetBeanInSpringContextUtils .getJedisServiceByContext(); }
		 * List<RumTimeFromRedis> rumTimeFromRedis = (List<RumTimeFromRedis>)
		 * jedisFactoryAction .getRedisObject("rumTimeFromRedis"); if (null ==
		 * rumTimeFromRedis || rumTimeFromRedis.size() <= 0) { setDataInRedis();
		 * // 设置完成以后 ，再取数据 rumTimeFromRedis = (List<RumTimeFromRedis>)
		 * jedisFactoryAction .getRedisObject("rumTimeFromRedis"); }
		 */
		List<RumTimeFromRedis> rumTimeFromRedis = makeRunTimeDate();
		// 拿到以后,组装实际发送的list
		Map<String, SecBigScreenOneVo> skillLineRumTimeVoMap = new HashMap<>();
		Map<String, RumTimeFromRedis> rumTimeFromRedisMap = new HashMap<>();
		List<Object> rumTimeVoList = new ArrayList<>();
		for (RumTimeFromRedis rtfr : rumTimeFromRedis) {
			String skillLine = rtfr.getSkillLine();
			SecBigScreenOneVo timeVo = SecBigScreenOneVo.cloneRumTimeVo(rtfr);
			SecBigScreenOneVo timeVoTemp = skillLineRumTimeVoMap.get(skillLine);
			rumTimeFromRedisMap.put(skillLine, rtfr);
			if (null != timeVoTemp) {
				// 已经存在 把值追加
				timeVoTemp.setBackOutCount(timeVo.getBackOutCount()
						+ timeVoTemp.getBackOutCount());
				timeVoTemp.setCheckinPersonCount(timeVo.getCheckinPersonCount()
						+ timeVoTemp.getCheckinPersonCount());
				timeVoTemp.setConnectRate(timeVo.getConnectRate()
						+ timeVoTemp.getConnectRate());
				timeVoTemp.setPlanPersonCount(timeVo.getPlanPersonCount()
						+ timeVoTemp.getPlanPersonCount());
				timeVoTemp.setServiceHorizontal(timeVo.getServiceHorizontal()
				/* + timeVoTemp.getPlanPersonCount() */);
				timeVoTemp.setSkillLineCount(timeVo.getSkillLineCount()
						+ timeVoTemp.getSkillLineCount());
				timeVoTemp.setWaitPhoneCount(timeVo.getWaitPhoneCount()
						+ timeVoTemp.getWaitPhoneCount());
				timeVoTemp.setWaitTime(timeVo.getWaitTime()
						+ timeVoTemp.getWaitTime());
				skillLineRumTimeVoMap.remove(skillLine);
			}
			if (null != timeVoTemp) {
				skillLineRumTimeVoMap.put(skillLine, timeVoTemp);
			} else {
				skillLineRumTimeVoMap.put(skillLine, timeVo);
			}
		}
		for (Entry<String, SecBigScreenOneVo> entry : skillLineRumTimeVoMap
				.entrySet()) {
			rumTimeVoList.add(entry.getValue());
		}
		// 循环完成再捏造一个总的数据对象
		SecBigScreenOneVo rvtemp = new SecBigScreenOneVo();
		rvtemp.setSkillLine("ensemble");
		List<Object> yjzbosList = setYujingBo();
		int yzjValue = 0;
		int zongjtlValue = 0;
		for (Object yzj : yjzbosList) {
			YuJingBo yjzbo = (YuJingBo) yzj;
			if ("zongfwsp".equals(yjzbo.getYujingname())) {
				yzjValue = yjzbo.getYujinzhi();
			}
			if ("zongjtl".equals(yjzbo.getYujingname())) {
				zongjtlValue = yjzbo.getYujinzhi();
			}
		}
		for (Object rvs : rumTimeVoList) {
			SecBigScreenOneVo rv = (SecBigScreenOneVo) rvs;
			rvtemp.setBackOutCount(rvtemp.getBackOutCount()
					+ rv.getBackOutCount());
			rvtemp.setCheckinPersonCount(rvtemp.getCheckinPersonCount()
					+ rv.getCheckinPersonCount());
			rvtemp.setConnectRate(rvtemp.getConnectRate() + rv.getConnectRate());

			rvtemp.setPlanPersonCount(rvtemp.getPlanPersonCount()
					+ rv.getPlanPersonCount());
			rvtemp.setServiceHorizontal(rvtemp.getServiceHorizontal()
					+ rv.getServiceHorizontal());

			rvtemp.setSkillLineCount(rvtemp.getSkillLineCount()
					+ rv.getSkillLineCount());
			rvtemp.setWaitPhoneCount(rvtemp.getWaitPhoneCount()
					+ rv.getWaitPhoneCount());
			int waitTime = rvtemp.getWaitTime();
			rvtemp.setWaitTime(waitTime);
			if (rv.getWaitTime() > waitTime) {
				rvtemp.setWaitTime(rv.getWaitTime());
			}
		}
		if (rvtemp.getConnectRate() > zongjtlValue) {
			rvtemp.setIsRedString("1");
		} else {
			rvtemp.setIsRedString("0");
		}
		if (rvtemp.getServiceHorizontal() > yzjValue) {
			rvtemp.setIsRedString(rvtemp.getIsRedString() + "*1");
		} else {
			rvtemp.setIsRedString(rvtemp.getIsRedString() + "*0");
		}
		rumTimeVoList.add(rvtemp);
		// System.out.println(rumTimeVoList.size());
		long end = System.currentTimeMillis();
		// System.out.println((end - begin));
		return rumTimeVoList;
	}

	// 588和中达技能线
	public static List<Object> makeSecBigScreenTwoVo() {
		List<Object> makeSecBigScreenTwoVoList = new ArrayList<Object>();
		List<RumTimeFromRedis> rumTimeFromRedis = makeRunTimeDate();
		SecBigScreenTwoVo jione = new SecBigScreenTwoVo();// 588中心
		SecBigScreenTwoVo jitwo = new SecBigScreenTwoVo();// 中达中心
		for (RumTimeFromRedis rtr : rumTimeFromRedis) {
			if (WebSocketConstants.FIVEBABA.equals(rtr.getResoureLine())) {
				int maxWateTime1 = jione.getWaitPhoneCount();
				int rtrWateTime1 = rtr.getWaitPhoneCount();
				if (maxWateTime1 < rtrWateTime1) {
					maxWateTime1 = rtrWateTime1;
				}
				jione.setJieqiCount(jione.getJieqiCount() + rtr.getJieqiCount());
				jione.setResoureLine(rtr.getResoureLine());
				jione.setSkillLineCount(rtr.getSkillLineCount()
						+ jione.getSkillLineCount());// 进线量
				jione.setWaitPhoneCount(rtr.getWaitPhoneCount()
						+ jione.getWaitPhoneCount());// 等待电话量
				jione.setWaitTime(maxWateTime1);// 等待时间（秒）
				jione.setServiceHorizontal(rtr.getServiceHorizontal()
						+ jione.getServiceHorizontal());// 服务水平
				jione.setJieqiCount(rtr.getJieqiCount() + jione.getJieqiCount());

			} else if (WebSocketConstants.ZHONGDAA.equals(rtr.getResoureLine())) {
				int maxWateTime2 = jitwo.getWaitPhoneCount();
				int rtrWateTime2 = rtr.getWaitPhoneCount();
				if (maxWateTime2 < rtrWateTime2) {
					maxWateTime2 = rtrWateTime2;
				}
				jitwo.setJieqiCount(jitwo.getJieqiCount() + rtr.getJieqiCount());
				jitwo.setResoureLine(rtr.getResoureLine());
				jitwo.setSkillLineCount(rtr.getSkillLineCount()
						+ jitwo.getSkillLineCount());// 进线量
				jitwo.setConnectRate(rtr.getConnectRate()
						+ jitwo.getConnectRate());// 接通率
				jitwo.setWaitPhoneCount(rtr.getWaitPhoneCount()
						+ jitwo.getWaitPhoneCount());// 等待电话量
				jitwo.setWaitTime(rtr.getWaitTime() + jitwo.getWaitTime());// 等待时间（秒）
				jitwo.setServiceHorizontal(rtr.getServiceHorizontal()
						+ jitwo.getServiceHorizontal());// 服务水平
			}
		}
		// 拿到接起量
		int jql1 = jione.getJieqiCount();
		int jql2 = jitwo.getJieqiCount();
		// 拿到进线量
		int jxl1 = jione.getSkillLineCount();
		int jxl2 = jitwo.getSkillLineCount();

		// 计算接通率
		float size1 = (float) jql1 / jxl1 *new Random().nextInt(50) ;
		DecimalFormat df = new DecimalFormat("0.00");// 格式化小数，不足的补0
		String jqlsize1 = df.format(size1);// 返回的是String类型的
		jione.setConnectRate(jqlsize1);
		float size2 = (float) jql2 / jxl2 *new Random().nextInt(50) ;
		String jqlsize2 = df.format(size2);// 返回的是String类型的
		jitwo.setConnectRate(jqlsize2);
		// 计算服务水平
		// 服务水平 20秒内接起量/进线量
		float fwsp1 = jione.getServiceHorizontal() * 100*new Random().nextInt(3) / jql1;
		float fwsp2 = jitwo.getServiceHorizontal() * 100*new Random().nextInt(3)  / jql2;
		String fwspString1 = df.format(fwsp1);
		String fwspString2 = df.format(fwsp2);
		jione.setFwspString(fwspString1);
		jitwo.setFwspString(fwspString2);
		int yujz588fwsp = 0;
		int yujz588jtl = 0;
		for (Object yj : setYujingBo()) {
			YuJingBo yjb = (YuJingBo) yj;
			if ("588fwsp".equals(yjb.getYujingname())) {
				yujz588fwsp = yjb.getYujinzhi();
			}
			if ("588jtl".equals(yjb.getYujingname())) {
				yujz588jtl = yjb.getYujinzhi();
			}
		}
		if (fwsp1 > yujz588fwsp) {
			jione.setIsRedFlag("1");
		} else {
			jione.setIsRedFlag("0");
		}
		if (fwsp2 > yujz588fwsp) {
			jitwo.setIsRedFlag("1");
		} else {
			jitwo.setIsRedFlag("0");
		}
		if (size1 > yujz588jtl) {
			jione.setIsRedFlag(jione.getIsRedFlag()+"*1");
		} else {
			jione.setIsRedFlag(jitwo.getIsRedFlag()+"*0");
		}
		if (size2 > yujz588jtl) {
			jitwo.setIsRedFlag(jione.getIsRedFlag()+"*1");
		} else {
			jitwo.setIsRedFlag(jitwo.getIsRedFlag()+"*0");
		}
		makeSecBigScreenTwoVoList.add(jione);
		makeSecBigScreenTwoVoList.add(jitwo);
		return makeSecBigScreenTwoVoList;
	}

	// 柱状图数据
	public static List<Object> makeCensusDataVo() {
		List<Object> makeCensusDataVoList = new ArrayList<Object>();
		CensusDataVo censusDataVo = new CensusDataVo();
		censusDataVo.setAprs(new Random().nextInt(100));
		censusDataVo.setHengding(new Random().nextInt(100));
		censusDataVo.setJtrs(new Random().nextInt(100));
		censusDataVo.setKxrs(new Random().nextInt(100));
		censusDataVo.setQrrs(new Random().nextInt(100));
		censusDataVo.setShclrs(new Random().nextInt(100));
		censusDataVo.setXxrs(new Random().nextInt(100));
		censusDataVo.setZxrs(new Random().nextInt(100));
		censusDataVo.setZxjcrs(new Random().nextInt(100));
		censusDataVo.setZxpxrs(new Random().nextInt(100));
		censusDataVo.setZxwhrs(new Random().nextInt(100));
		censusDataVo.setCountSize(censusDataVo.getAprs()+censusDataVo.getJtrs()+censusDataVo.getKxrs()
				+censusDataVo.getQrrs()+censusDataVo.getShclrs()+censusDataVo.getXxrs()+censusDataVo.getZxjcrs()
				+censusDataVo.getZxrs()+censusDataVo.getZxwhrs());
		makeCensusDataVoList.add(censusDataVo);
		return makeCensusDataVoList;
	}

	public static void main(String[] args) {
		/*
		 * float size1 = (float) 2 / 3; DecimalFormat df = new
		 * DecimalFormat("0.00");// 格式化小数，不足的补0 String jqlsize1 =
		 * df.format(size1);// 返回的是String类型的 System.out.println(jqlsize1); long
		 * begin = System.currentTimeMillis(); List<Object> rumTimeVoList =
		 * setDataBySkillLine(); long end = System.currentTimeMillis(); //
		 * System.out.println((end - begin)); for (Object rv : rumTimeVoList) {
		 * System.out.println((SecBigScreenOneVo) rv); }
		 */
		List<List<Object>> objs = new ArrayList<List<Object>>();
		List<Object> rtvos = BigScreenDataTwo.setDataBySkillLine();
		List<Object> chartDataBoList = BigScreenDataTwo.makeSecBigScreenTwoVo();
		// System.out.println(chartDataBoList.size());
		List<Object> censusDataVos = BigScreenDataTwo.makeCensusDataVo();
		objs.add(rtvos);
		objs.add(chartDataBoList);
		objs.add(censusDataVos);
		String dataSend = MakeDataUtil.makeDataListObj(objs);
		System.out.println(dataSend);
	}
}
