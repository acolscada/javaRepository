package cn.acol.scada.records.utils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import cn.acol.scada.core.dto.UpRecordDto;
import cn.acol.scada.records.domain.UpRecord;


public class Utils {
	public static String bytesToHexString(byte[] src,int len) {
		return bytesToHexString(src, 0, len);
	}
	
	public static String bytesToHexStringForWatch(byte[] src,int len) {
		return bytesToHexString(src, 0, len, "-");
	}
	/**
	 * 
	 * @param src 数据源
	 * @param offset 数据偏移
	 * @param len 数据长度
	 * @param separator  数据间分割符号
	 * @return
	 */
	public static String bytesToHexString(byte[] src, int offset, int len,String separator) {
		StringBuilder stringBuilder = new StringBuilder("");
		if (src == null || len <= 0) {
			return null;
		}
		for (int i = offset; i < offset+len; i++) {
			int v = src[i] & 0xFF;
			String hv = Integer.toHexString(v);
			if (hv.length() < 2) {
				stringBuilder.append(0);
			}
			stringBuilder.append(hv);
			if(i<len-1) {//不是最后一个
				stringBuilder.append(separator);
			}
		}
		return stringBuilder.toString();
	}
	public static String bytesToHexString(byte[] src, int offset, int len) {
		StringBuilder stringBuilder = new StringBuilder("");
		if (src == null || len <= 0) {
			return null;
		}
		for (int i = offset; i < offset+len; i++) {
			int v = src[i] & 0xFF;
			String hv = Integer.toHexString(v);
			if (hv.length() < 2) {
				stringBuilder.append(0);
			}
			stringBuilder.append(hv);
		}
		return stringBuilder.toString();
	}
	private static DateFormat df = new SimpleDateFormat("yyMMddHHmm");
	
	public static void main(String[] args) throws ParseException {
		System.out.println(df.parse("1812011336"));
	}
	public static Date upRecordDate(String time) throws ParseException {
		System.out.println(time);
		return df.parse(time);
	}
	public static int byteToInt(byte b) {
		return b&0xff;
	}
	public static void watchBytes(byte[] src, int offset, int len,String message) {
		System.out.println(message+"： "+Utils.bytesToHexString(src, offset,len));
	}
	/**
	 * 高8位+低8位最后形成 16位short
	 * @param high
	 * @param low
	 * @return
	 */
	public static short byteToshort(byte high,byte low) {
		return (short) (  ((high&0x00FF)<<8)  |  (0x00FF & low)  );
	}
	/**
	 * 
	 * @param data
	 * @param offset
	 * @param len
	 * @param div 保留2位小数则div = 100
	 * @return
	 */
	public static float changeBcdToFloat(byte[] data,int offset, int len, int div) {
		return (float)Long.parseLong(Utils.bytesToHexString(data, offset, len))/div;
	}
	public static void changeUpRecordToUpRecordDto(UpRecordDto upRecordDto, UpRecord upRecord) {
		upRecordDto.setColTime(upRecord.getColTime().getTime());
		upRecordDto.setDeviceVolt(upRecord.getDeviceVolt());
		upRecordDto.setMeterVolt(upRecord.getMeterVolt());
		upRecordDto.setPress(upRecord.getPress());
		upRecordDto.setSurplusSum(upRecord.getSurplusSum());
		upRecordDto.setScFlow(upRecord.getScFlow());
		upRecordDto.setScSum(upRecord.getScSum());
		upRecordDto.setSignal(upRecord.getSignal());
		upRecordDto.setTemp(upRecord.getTemp());
		upRecordDto.setUpTime(upRecord.getUpTime().getTime());
		upRecordDto.setValueStatus(upRecord.getValueStatus());
		upRecordDto.setWcFlow(upRecord.getWcFlow());
		upRecordDto.setWcSum(upRecord.getWcSum());
	}
	public static void changeUpRecordDtoToUpRecord(UpRecord upRecord,UpRecordDto upRecordDto) {
		upRecord.setColTime(new Date(upRecordDto.getColTime()));
		upRecord.setDeviceVolt(upRecordDto.getDeviceVolt());
		upRecord.setMeterVolt(upRecordDto.getMeterVolt());
		upRecord.setPress(upRecordDto.getPress());
		upRecord.setSurplusSum(upRecordDto.getSurplusSum());
		upRecord.setScFlow(upRecordDto.getScFlow());
		upRecord.setScSum(upRecordDto.getScSum());
		upRecord.setSignal(upRecordDto.getSignal());
		upRecord.setTemp(upRecordDto.getTemp());
		upRecord.setValueStatus(upRecordDto.getValueStatus());
		upRecord.setUpTime(new Date(upRecordDto.getUpTime()));
		upRecord.setWcFlow(upRecordDto.getWcFlow());
		upRecord.setWcSum(upRecordDto.getWcSum());
	}
}
