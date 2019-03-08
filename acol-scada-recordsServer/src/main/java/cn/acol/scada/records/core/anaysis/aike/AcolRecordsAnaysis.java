package cn.acol.scada.records.core.anaysis.aike;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Component;

import cn.acol.scada.records.core.Request;
import cn.acol.scada.records.core.anaysis.AnaysisException;
import cn.acol.scada.records.core.anaysis.RecordsAnaysis;
import cn.acol.scada.records.domain.UpRecord;
import cn.acol.scada.records.utils.Utils;

@Component
public class AcolRecordsAnaysis implements RecordsAnaysis{
	
	
	@Override
	public List<UpRecord> anaysisData(Request request) throws AnaysisException {
		// TODO Auto-generated method stub
		byte[] data = request.getData();
		if(((request.requestHeader().getLen()-7)%60)!= 0) {
			throw new AnaysisException("上传记录非60字节对齐",data);
		}
		List<UpRecord> records = new ArrayList<UpRecord>();
		for(int i=0;i<data.length-60;i +=60) {
			UpRecord record= new UpRecord();
			record.setUpTime(new Date());
			record.setScaNum(request.requestHeader().getScaNum());
			
			try {
				record.setColTime(Utils.upRecordDate(Utils.bytesToHexString(data, 20+i, 5)));
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				throw new AnaysisException("时间格式错误",data);
			}
			try {
				record.setScSum(Utils.changeBcdToFloat(data, 27+i, 6, 1000));
				record.setScFlow(Utils.changeBcdToFloat(data, 33+i, 4, 100));
				record.setWcSum(Utils.changeBcdToFloat(data, 37+i, 6,1000));
				record.setWcFlow(Utils.changeBcdToFloat(data, 43+i, 4,100));
				record.setPress(Utils.changeBcdToFloat(data, 47+i, 3,1000));
				record.setTemp(Utils.changeBcdToFloat(data, 50+i, 2,100));
				record.setMeterVolt(Utils.changeBcdToFloat(data, 52+i, 2,100));
				record.setSignal(request.requestHeader().getSignal());
				records.add(record);
			}catch(NumberFormatException e) {
				throw new AnaysisException("转换错误 " );
			}
		}
		return records;
	}
}
