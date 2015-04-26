package cn.utry.psd.call.center.websocket.vo;

import java.util.List;

/**
 * 组织树dto类
 */
public class DeptTreeDTO {
	/**
	 * 组织id
	 */
	private String id;
	/**
	 * 组织名称
	 */
	private String name;
	/**
	 * 子节点数据
	 */
	private List<DeptTreeDTO> child;
	/**
	 * 租住code
	 */
	private String code;
	/**
	 * 父级id
	 */
	private String pId;
	/**
	 * 描述
	 */
	private String departmentName;
	/**
	 * 选中
	 */
	private boolean Selected;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<DeptTreeDTO> getChild() {
		return child;
	}

	public void setChild(List<DeptTreeDTO> child) {
		this.child = child;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getPId() {
		return pId;
	}

	public void setPId(String pId) {
		this.pId = pId;
	}

	public String getDepartmentName() {
		return departmentName;
	}

	public void setDepartmentName(String departmentName) {
		this.departmentName = departmentName;
	}

	public boolean isSelected() {
		return Selected;
	}

	public void setSelected(boolean selected) {
		Selected = selected;
	}
}
