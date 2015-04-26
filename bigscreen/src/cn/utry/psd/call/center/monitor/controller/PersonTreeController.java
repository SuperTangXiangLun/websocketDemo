package cn.utry.psd.call.center.monitor.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import utry.common.LoginInfoParams;
import cn.utry.psd.call.center.monitor.service.OrganizationTreeService;
import cn.utry.psd.call.center.websocket.vo.DeptTreeDTO;

/**
 * 
 * @ClassName: StaffInfoController 
 * @Description: 人员相关信息控制器
 * @author chenqinglin
 * @date 2015年3月12日 下午5:59:39
 */
@Controller
@RequestMapping("/personTree")
public class PersonTreeController {
	
	@Autowired
	private OrganizationTreeService organizationTreeService;
	
	/**
	 * 
	 * @Title: tree 
	 * @Description: 组织结构树数据
	 * @param @param request
	 * @param @return
	 * @return List<DeptTreeDTO>    返回类型 
	 * @throws
	 */
	@RequestMapping(value="/treedata")
	@ResponseBody
	public List<DeptTreeDTO> treedata()
	{
		List<DeptTreeDTO> result = organizationTreeService.tree(LoginInfoParams.getSp());
		return result;
	}
}
