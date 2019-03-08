package cn.acol.scada.core.utils;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import cn.acol.scada.core.dto.PricePlanDto;
import cn.acol.scada.core.dto.PriceType;
import cn.acol.scada.core.dto.ScaParamsDto;
import cn.acol.scada.core.exception.AnaysisException;

public class ScaParamUtil {
	private ScaParamsDto scaParamsDto = null;
	
	public synchronized void requestScaParamsDto() {
		
	}
	public synchronized ScaParamsDto getScaParams() throws InterruptedException {
		
		Thread.sleep(10000);
		scaParamsDto = new ScaParamsDto();
		return scaParamsDto;
	}
	public synchronized ScaParamsDto getScaParamsDto(){
		return scaParamsDto;
	}
	private static DateFormat df = new SimpleDateFormat("yyMMddHHmm");
	/**
	 * 此API不可用在非计量采集设备
	 * 
	 * @return
	 */
	
	private static byte charToByte(char c) {
	    return (byte) "0123456789ABCDEF".indexOf(c);
	}
	public static byte[] hexStringToBytes(String hexString) {
		if (hexString == null || hexString.equals("")) {
		return null;
		}
		hexString = hexString.toUpperCase();
		int length = hexString.length() / 2;
		char[] hexChars = hexString.toCharArray();
		byte[] d = new byte[length];
		for (int i = 0; i < length; i++) {
			int pos = i * 2;
			d[i] = (byte) (charToByte(hexChars[pos]) << 4 | charToByte(hexChars[pos + 1]));
		}
		return d;
	}
	public static byte[] getPriceBytes(PricePlanDto pricePlanDto, PricePlanDto exPricePlanDto) throws AnaysisException {
		if(pricePlanDto == null) {
			throw new AnaysisException("没有给出价格方案 此设备不支持价格");
		}
		byte[] data = new byte[91];
		if(pricePlanDto.getPriceType() == PriceType.Normal) {
			 if(pricePlanDto.getExDate()>new Date().getTime()) {
				 throw new AnaysisException("当前价格还未能够执行 检查服务器时间");
			 }
			 short price = (short) (pricePlanDto.getPrice()*100);
			 data[0] = (byte) (price>>8);
			 data[1] = (byte) (price&0xff);
			 data[2] = 0x01;
			 data[90] = 0x01;
		 }
		if(exPricePlanDto !=null) {
			 if(exPricePlanDto.getPriceType() == PriceType.Normal) {
				 short price = (short) (exPricePlanDto.getPrice()*100);
				 data[43] = (byte) (price>>8);
				 data[44] = (byte) (price&0xff);
				 Long exDate = exPricePlanDto.getExDate();
				 Date date = new Date(exDate);//期望时间
				 String format = df.format(date);
				byte[] d =  hexStringToBytes(format);
				for(int i=0;i<4;i++) {
					data[39+i] = d[i];
				}
			 }
		}
		return data;
	}
	
	public static void main(String[] args) throws InterruptedException {
		 ScaParamUtil scaParamUtil = new ScaParamUtil();
		 new Thread(new Runnable() {
			@Override
			public void run() {
				try {
					System.out.println(scaParamUtil.getScaParams());
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			
			}
		}).start();
		 Thread.sleep(100);
		 System.out.println(scaParamUtil.getScaParamsDto());
	}
}
