package cn.utry.psd.call.center.data.set;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import cn.utry.psd.call.center.websocket.bo.twoBigScreen.ChinaMapBo;
import cn.utry.psd.call.center.websocket.bo.twoBigScreen.ChinaMapVo;

public class ChinaMapDataSet {
	public static List<Object> makeChinaMapBoData() {
		List<Object> chinaMapList = new ArrayList<>();
		for (int i = 0; i < 32; i++) {
			ChinaMapBo chb = new ChinaMapBo();
			if (i % 32 == 1) {
				chb.setSname("广东");
			} else if (i % 32 == 2) {
				chb.setSname("湖南");
			} else if (i % 32 == 3) {
				chb.setSname("江苏");
			} else if (i % 32 == 4) {
				chb.setSname("黑龙江");
			} else if (i % 32 == 5) {
				chb.setSname("湖南");
			} else if (i % 32 == 6) {
				chb.setSname("青海");
			} else if (i % 32 == 7) {
				chb.setSname("西藏");
			} else if (i % 32 == 8) {
				chb.setSname("福建");
			} else if (i % 32 == 9) {
				chb.setSname("宁夏");
			} else if (i % 32 == 10) {
				chb.setSname("陕西");
			} else if (i % 32 == 11) {
				chb.setSname("山西");
			} else if (i % 32 == 12) {
				chb.setSname("云南");
			} else if (i % 32 == 13) {
				chb.setSname("北京");
			} else if (i % 32 == 14) {
				chb.setSname("天津");
			} else if (i % 32 == 15) {
				chb.setSname("重庆");
			} else if (i % 32 == 16) {
				chb.setSname("江苏");
			} else if (i % 32 == 17) {
				chb.setSname("山东");
			} else if (i % 32 == 18) {
				chb.setSname("吉林");
			} else if (i % 32 == 19) {
				chb.setSname("辽宁");
			} else if (i % 32 == 20) {
				chb.setSname("黑龙江");
			} else if (i % 32 == 21) {
				chb.setSname("贵州");
			} else if (i % 32 == 22) {
				chb.setSname("海南");
			} else if (i % 32 == 23) {
				chb.setSname("台湾");
			} else if (i % 32 == 24) {
				chb.setSname("香港");
			} else if (i % 32 == 25) {
				chb.setSname("澳门");
			} else if (i % 32 == 26) {
				chb.setSname("安徽");
			} else if (i % 32 == 27) {
				chb.setSname("广西");
			} else if (i % 32 == 28) {
				chb.setSname("四川");
			} else if (i % 32 == 29) {
				chb.setSname("黑龙江");
			} else if (i % 32 == 30) {
				chb.setSname("黑龙江");
			} else if (i % 32 == 31) {
				chb.setSname("内蒙古");
			} else if (i % 32 == 32) {
				chb.setSname("新疆");
			}else{
				chb.setSname("南海诸岛");
			}
			chb.setCountHwl(300);
			chb.setHwl(new Random().nextInt(100));
			chinaMapList.add(chb);
		}
		return chinaMapList;
	}

	public static List<Object> makeChinaMapVoData() {
		List<Object> chinaBoMapList = makeChinaMapBoData();
		List<Object> chinaVoMapList = new ArrayList<>();
		for (Object obj : chinaBoMapList) {
			ChinaMapVo chinaMapVo = new ChinaMapVo();
			ChinaMapBo cMapBo = (ChinaMapBo) obj;
			chinaMapVo.setHwl(cMapBo.getHwl());
			chinaMapVo.setSname(cMapBo.getSname());
			float bfbFloat = (float) (cMapBo.getHwl() * 1.0 / cMapBo
					.getCountHwl());
			DecimalFormat df = new DecimalFormat("0.00");// 格式化小数，不足的补0
			String bfbFloatString = df.format(bfbFloat * 100);// 返回的是String类型的
			chinaMapVo.setBfb(bfbFloatString.substring(0,
					bfbFloatString.length() - 3));
			chinaVoMapList.add(chinaMapVo);
		}
		return chinaVoMapList;
	}

	public static void chinaVoMapListSort(List<Object> sortList) {
		if (null != sortList && sortList.size() > 0) {
			for (int i = 0; i < sortList.size(); i++) {
				for (int j = 0; j < sortList.size() - i - 1; j++) {
					ChinaMapVo cMapVo1 = (ChinaMapVo) sortList.get(j);
					ChinaMapVo cMapVo2 = (ChinaMapVo) sortList.get(j + 1);
					if (cMapVo1.getHwl() < cMapVo2.getHwl()) {
						ChinaMapVo cMapVoTemp = cMapVo1;
						sortList.set(j, sortList.get(j + 1));
						sortList.set(j + 1, cMapVoTemp);
					}
				}
			}

		}
	}

	public static List<Object> makeChinaVoMapListOderByHwl() {
		List<Object> sortList = makeChinaMapVoData();
		ChinaMapDataSet.chinaVoMapListSort(sortList);
		List<Object> chinaVoMapList = new ArrayList<>();
		for (int i = 0; i < 5; i++) {
			chinaVoMapList.add(sortList.get(i));
		}
		for (Object obj : chinaVoMapList) {
			ChinaMapVo cMapVo = (ChinaMapVo) obj;
		}
		return chinaVoMapList;
	}

	public static void main(String[] args) {
		makeChinaVoMapListOderByHwl();
	}
}
