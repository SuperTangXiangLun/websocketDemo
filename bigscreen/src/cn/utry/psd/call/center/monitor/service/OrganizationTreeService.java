package cn.utry.psd.call.center.monitor.service;

import java.util.List;

import utry.bo.LoginBean;
import cn.utry.psd.call.center.websocket.vo.DeptTreeDTO;

/**
 * 
 * @ClassName: OrganizationTreeService 
 * @Description: 组织树
 * @author chenqinglin
 * @date 2015年3月21日 下午2:57:26
 */
public interface OrganizationTreeService {
	
	/**
	 * 
	 * @Title: tree 
	 * @Description: 组织结构树
	 * @param @param loginBean
	 * @param @return
	 * @return List<DeptTreeDTO>    返回类型 
	 * @throws
	 */
	List<DeptTreeDTO> tree(LoginBean loginBean);
}
