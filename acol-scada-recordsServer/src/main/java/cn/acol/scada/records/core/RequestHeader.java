package cn.acol.scada.records.core;

import cn.acol.scada.records.core.anaysis.AnaysisException;
import cn.acol.scada.records.utils.Utils;
import lombok.Data;

@Data
public class RequestHeader {
	private String scaNum; //对应设备号
	private String version;//对应版本号
	private RequestType requestType;//对应说明码
	private int len;//数据长度
	private int signal;//信号强度
	public RequestHeader(byte[] data) throws AnaysisException {
		super();
		this.version = Utils.bytesToHexString(data, 1, 1);
		this.requestType = identyRequestType(data);
		this.len = data.length - 9;
		this.signal = Utils.byteToInt(data[6]);
		this.scaNum = Utils.bytesToHexString(data, 7, 6);
		
	}
	private  RequestType identyRequestType(byte[] data) throws AnaysisException {
		// TODO Auto-generated method stub
		if(data[3] == 0) {
			return RequestType.UpRecords;
		}else if(data[3] == 1) {
			return RequestType.Init;
		}else if(data[3]== 9) {
			return RequestType.Warning;
		}else {
			throw new AnaysisException( "说明码错误 无法识别说明码 错误数据为",data);
		}
	}
	
}
