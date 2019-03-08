package cn.acol.bhServer.core;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cn.acol.bhServer.core.exception.AnaysisException;
import cn.acol.scada.core.dto.UpRecordDto;

public class Response {
	
	private static final Logger log = LoggerFactory.getLogger(Response.class);

	private Session session;
	
	private ResponseType responseType;
	
	/**
	 * 直接用解析器获取响应信息
	 */
	private Anaysis anaysis;
	public Response(Anaysis anaysis,Session session) {
		this.anaysis = anaysis;
		this.session = session;
	}
	public ResponseType getResponseType() {
		return responseType;
	}
	
	
	public Session getSession() {
		return session;
	}
	/**
	 * 分析下来一切都OK 则返回true
	 * @return
	 * @throws AnaysisException 
	 */
	public boolean isReady() throws AnaysisException {
		this.responseType = anaysis.anaysisType();
		if(responseType == ResponseType.ResponseHeader) {
			session.setICCID(anaysis.getIccid());
			session.setVolt(anaysis.getVolt());
			return true;
		}else if(responseType == ResponseType.upRecordsResponse) {
			return true;
		}
		return false;
	}
	/**
	 * anaysis.getDeviceNum()
			anaysis.getLocalNum()
			anaysis.getUserNum()
			anaysis.getUpRecordDtos();
			anaysis.getPrice();
	 */
	public String getDeviceNum() {
		try {
			return anaysis.getDeviceNum();
		} catch (AnaysisException e) {
			// TODO Auto-generated catch block
			log.error("无法获取设备号： "+ e.getMessage());
			return null;
		}
	}
	public String getUserNum() {
		try {
			return anaysis.getUserNum();
		} catch (AnaysisException e) {
			// TODO Auto-generated catch block
			log.error("无法获取用户号： "+ e.getMessage());
			return null;
		}
	}
	/**
	 * 为气量计价时返回null
	 * @return
	 */
	public Float getPrice() {
		try {
			float price = anaysis.getPrice();
			if(price == 0) {
				return null;
			}
			return price;
		} catch (AnaysisException e) {
			// TODO Auto-generated catch block
			log.error("单价获取错误： "+ e.getMessage());
			return null;
		}
	}
	public List<UpRecordDto> getUpRecordDtos(){
		try {
			return anaysis.getUpRecordDtos();
		} catch (AnaysisException e) {
			// TODO Auto-generated catch block
			log.error("记录获取失败： "+ e.getMessage());
			return null;
		}
	}
	public String getLocalNum() {
		try {
			return anaysis.getLocalNum();
		} catch (AnaysisException e) {
			// TODO Auto-generated catch block
			log.error("坐标位置获取失败： "+ e.getMessage());
			return null;
		}
	}
	
}
