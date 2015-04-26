package cn.utry.psd.call.center.monitor.service.impl;

import java.util.LinkedList;
import java.util.List;

import org.springframework.stereotype.Service;

import utry.bo.LoginBean;
import cn.utry.psd.call.center.monitor.service.OrganizationTreeService;
import cn.utry.psd.call.center.websocket.vo.DeptTreeDTO;

/**
 * 
 * @ClassName: OrganizationTreeServiceImpl
 * @Description: 组织结构树
 * @author chenqinglin
 * @date 2015年3月21日 下午3:25:44
 */
@Service
public class OrganizationTreeServiceImpl implements OrganizationTreeService {
	@Override
	public List<DeptTreeDTO> tree(LoginBean loginBean) {
		// TStaffInfo staffInfo =
		// staffInfoService.findByNo(loginBean.getAccountID());
		// List<GroupInfoDto> groupInfoDtoList =
		// groupService.findGroupBySkillLine(staffInfo.getSkillLineId());
		// //调用接口

		List<DeptTreeDTO> fatherList = new LinkedList<DeptTreeDTO>();
		for (int j = 0; j < 5; j++) {
			DeptTreeDTO father = new DeptTreeDTO();
			father.setId("" + (j + 1));
			father.setName("father" + (j + 1));
			List<DeptTreeDTO> sonList = new LinkedList<DeptTreeDTO>();
			for (int i = 0; i < 5; i++) {
				DeptTreeDTO son = new DeptTreeDTO();
				son.setId(i + "");
				son.setName("son" + (i + 1));
				sonList.add(son);
			}
			father.setChild(sonList);
			fatherList.add(father);
		}

		DeptTreeDTO grandfather = new DeptTreeDTO();
		grandfather.setId("" + 1);
		grandfather.setName("浦发银行");
		grandfather.setChild(fatherList);
		List<DeptTreeDTO> result = new LinkedList<DeptTreeDTO>();
		result.add(grandfather);
		return result;
	}

}
