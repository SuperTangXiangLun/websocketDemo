package cn.utry.psd.call.center.websocket.vo;

import java.io.Serializable;

/**
 * deprecated websocket中封装类
 * 
 * date 2015-03-06
 * 
 * @author sharkTang
 */
public class WebSocketBo implements Serializable {
	private static final long serialVersionUID = 901201503061055001l;
	private String wsId;// id
	private Integer wsHashCode;// Session的hashcode
	private String pageType;//用来保存页面分类
    private String messageStatus="";//消息状态
    
	public String getMessageStatus() {
		return messageStatus;
	}

	public void setMessageStatus(String messageStatus) {
		this.messageStatus = messageStatus;
	}

	public String getPageType() {
		return pageType;
	}

	public void setPageType(String pageType) {
		this.pageType = pageType;
	}

	public String getWsId() {
		return wsId;
	}

	public void setWsId(String wsId) {
		this.wsId = wsId;
	}

	public Integer getWsHashCode() {
		return wsHashCode;
	}

	public void setWsHashCode(Integer wsHashCode) {
		this.wsHashCode = wsHashCode;
	}

}
