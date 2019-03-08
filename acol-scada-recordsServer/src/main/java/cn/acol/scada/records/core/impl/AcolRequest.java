package cn.acol.scada.records.core.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;


import cn.acol.scada.records.core.Listerner;
import cn.acol.scada.records.core.Request;
import cn.acol.scada.records.core.RequestFilterChain;
import cn.acol.scada.records.core.RequestHeader;
import cn.acol.scada.records.core.RequestType;
import cn.acol.scada.records.core.Session;
import cn.acol.scada.records.core.WarningException;
import cn.acol.scada.records.core.anaysis.AnaysisException;
import cn.acol.scada.records.core.anaysis.RecordsAnaysis;
import cn.acol.scada.records.domain.UpRecord;
import cn.acol.scada.records.core.anaysis.Check;
import cn.acol.scada.records.core.anaysis.Encryption;
import cn.acol.scada.records.utils.Utils;

@Component
@Scope("prototype")
public class AcolRequest extends Request{
	private static final Logger log = LoggerFactory.getLogger(AcolRequest.class);

	private byte[] data;
	
	private List<UpRecord> list;
	@Autowired
	private Check check;
	
	@Autowired
	private RequestFilterChain requestFilterChain;
	
	@Autowired
	private Listerner listerner;
	
	@Autowired
	private Encryption encryption;
	//RecordsAnaysis recordsAnaysis = new AcolRecordsAnaysis();
	@Autowired
	private RecordsAnaysis recordsAnaysis;
	
	public AcolRequest(Session session) {
		super(session);
		// TODO Auto-generated constructor stub
	}
	
	public AcolRequest() {
		super();
	}
	
	private void dataLengthCheck() throws AnaysisException{
		int length =  (Utils.byteToInt(data[4])<<8) | Utils.byteToInt(data[5]);
		if(data.length != (length+9)) {
			throw new AnaysisException("请求头中数据长度与收到数据长度不匹配:",data);
		}
	}
	@Override
	public boolean isReached(byte[] data,int len) throws AnaysisException{
		try {
			requestFilterChain.doFilter(data, len);
		}catch(AnaysisException anaysisException) { //解析失败
			try {
				listerner.handler(data, len, session);//监听
			} catch (WarningException e) {
				// TODO Auto-generated catch block
				log.warn(e.getMessage());
				return false;
			}
		}
		
		
		//07015529  9518
		if(check.dataCheck(data, len)) { //校验信息可靠则
			this.data = encryption.decrpt(data, len);
			initRequestHeader();//初始化头部
			
			if(requestHeader.getRequestType() == RequestType.UpRecords) {
				
			}
			if(data !=null) {
				return true;
			}
		}
		return false;
	}
	
	private void initRequestHeader() throws AnaysisException{
		this.dataLengthCheck();//头长度校验
		this.requestHeader = new RequestHeader(data);
	}
	
	@Override
	public byte[] getData() {
		// TODO Auto-generated method stub
		return this.data;
	}
	@Override
	public List<UpRecord> getUpRecord() throws AnaysisException {
		if(this.list == null) {
			log.info("有进行解析");
			this.list =  recordsAnaysis.anaysisData(this);
		}
		return list;
	}
	
}
