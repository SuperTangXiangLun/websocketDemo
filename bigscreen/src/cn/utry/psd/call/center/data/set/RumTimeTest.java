package cn.utry.psd.call.center.data.set;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Random;

import utry.common.JedisService;
import cn.utry.psd.call.center.util.GetBeanInSpringContextUtils;
import cn.utry.psd.call.center.util.UUIDUtils;
import cn.utry.psd.call.center.websocket.bo.RumTimeFromRedis;

public class RumTimeTest {
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
			rumtimeData.setResoureLine("ResoureLine" + i);
			rumtimeData.setSkillLineCount(new Random().nextInt(100));
			rumtimeData.setConnectRate(new Random().nextInt(5));
			// System.out.println(rumtimeData.getConnectRate());
			rumtimeData.setBackOutCount(new Random().nextInt(3));
			rumtimeData.setWaitPhoneCount(new Random().nextInt(3));
			rumtimeData.setWaitTime(new Random().nextInt(4));
			rumtimeData.setPlanPersonCount(new Random().nextInt(3));
			rumtimeData.setCheckinPersonCount(new Random().nextInt(3));
			rumtimeData.setServiceHorizontal(new Random().nextInt(7));
			rumTimeFromRedis.add(rumtimeData);
			// System.out.println(rumtimeData.toString());
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

	public static List<Object> setDataByTest() {
		long begin = System.currentTimeMillis();
		setDataInRedis(); // 设置完成以后 ，再取数据
		if (null == jedisFactoryAction) {
			jedisFactoryAction = GetBeanInSpringContextUtils
					.getJedisServiceByContext();
		}
		List<RumTimeFromRedis> rumTimeFromRedis = (List<RumTimeFromRedis>) jedisFactoryAction
				.getRedisObject("rumTimeFromRedis");

		// List<RumTimeFromRedis> rumTimeFromRedis = makeRunTimeDate();
		// 拿到以后,组装实际发送的list
		Map<String, RumTimeVo> skillLineRumTimeVoMap = new HashMap<>();
		Map<String, RumTimeFromRedis> rumTimeFromRedisMap = new HashMap<>();
		List<Object> rumTimeVoList = new ArrayList<>();
		for (RumTimeFromRedis rtfr : rumTimeFromRedis) {
			String skillLine = rtfr.getSkillLine();
			RumTimeVo timeVo = RumTimeVo.cloneRumTimeVo(rtfr);
			RumTimeVo timeVoTemp = skillLineRumTimeVoMap.get(skillLine);
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
		for (Entry<String, RumTimeVo> entry : skillLineRumTimeVoMap.entrySet()) {
			rumTimeVoList.add(entry.getValue());
		}
		// 循环完成再捏造一个总的数据对象
		RumTimeVo rvtemp = new RumTimeVo();
		rvtemp.setUid(UUIDUtils.getUUID());
		rvtemp.setSkillLine("ensemble");

		for (Object rvs : rumTimeVoList) {
			RumTimeVo rv = (RumTimeVo) rvs;
			if (null == rvtemp.getResoureLine()
					|| "".equals(rvtemp.getResoureLine())) {
				rvtemp.setResoureLine(rv.getResoureLine());
			}
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
		rumTimeVoList.add(rvtemp);
		// System.out.println(rumTimeVoList.size());
		long end = System.currentTimeMillis();
		// System.out.println((end - begin));
		return rumTimeVoList;
	}

	private static int forCount = 0;

	public static List<Object> makeChartDataBoDate() {
		List<Object> chartDataBoList = new ArrayList<Object>();
		forCount++;
		if (forCount == 48) {
			forCount = 0;
		}
		for (int i = 1; i <= forCount; i++) {
			ChartDataBo chartDataBo = new ChartDataBo();
			chartDataBo.setHwl(new Random().nextInt(100));
			chartDataBo.setFwsp(new Random().nextInt(18));
			chartDataBo.setSjfwsp(new Random().nextInt(17));
			chartDataBo.setYqsp(10);
			chartDataBoList.add(chartDataBo);
		}
		return chartDataBoList;
	}

	public static void main(String[] args) {
		long begin = System.currentTimeMillis();
		List<Object> rumTimeVoList = setDataByTest();
		long end = System.currentTimeMillis();
		System.out.println((end - begin));
		for (Object rv : rumTimeVoList) {
			System.out.println((RumTimeVo) rv);
		}
	}
}
