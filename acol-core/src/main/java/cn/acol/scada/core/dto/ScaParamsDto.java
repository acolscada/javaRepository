package cn.acol.scada.core.dto;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import cn.acol.scada.core.exception.AnaysisException;
import lombok.Data;

/**
 * 采集器参数
 * @author DaveZhang
 */
@Data
public class ScaParamsDto {
	private String deviceNum; //设备参数如果需要则使用
	private Long id;//id
	private int upTime;//上传周期
	private int colTime;//采集周期
	private boolean valueStatus;//当前阀门状态 true 开阀
	private Double desAddPlusSum;//目标增加的剩余气量
	private int desUpTime;//目标上传周期
	private int desColTime;//目标采集周期
	private boolean desValueStatus;//目标阀门状态  关阀
	private double surplusSum;//剩余气量
	private Long changeDatel;  //上一次参数修改的时间  
	private Long synchronizationDatel;  //上一次同步参数的时间
	private PricePlanDto pricePlanDto; //当前方案
	private PricePlanDto exPricePlanDto;//计划方案
	
	private Long totalRecharge;//总充值额
	
	
	
	public boolean needChangeUpTime() {
		return desUpTime !=0 && upTime != desUpTime;
	}
	public boolean needChangeColTime() {
		return desColTime !=0 &&colTime != desColTime;
	}
	
	public boolean needChangeValueStatus() {
		return valueStatus != desValueStatus;
	}
	public boolean needChange() {
		if(synchronizationDatel == null) {
			synchronizationDatel = 0L;
		}
		return changeDatel>=synchronizationDatel;
	}
	/**
	 * 此API不可用在非计量采集设备
	 * 
	 * @return
	 */
	private static DateFormat df = new SimpleDateFormat("yyMMddHHmm");
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
	
	public byte[] priceBytes() throws AnaysisException{
		if(this.pricePlanDto == null) {
			throw new AnaysisException("没有给出价格方案 此设备不支持价格");
		}
		byte[] data = new byte[95];
	
		 if(pricePlanDto.getPriceType() == PriceType.Normal) {
			 if(pricePlanDto.getExDate()>new Date().getTime()) {
				 throw new AnaysisException("当前价格还未能够执行 检查服务器时间");
			 }
			 short price = (short) (this.pricePlanDto.getPrice()*100);
			 data[0] = (byte) (price>>8);
			 data[1] = (byte) (price&0xff);
			 data[2] = 0x01;
		 }
		 if(exPricePlanDto.getPriceType() == PriceType.Normal) {
			 short price = (short) (this.exPricePlanDto.getPrice()*100);
			 data[43] = (byte) (price>>8);
			 data[44] = (byte) (price&0xff);
			 Long exDate = exPricePlanDto.getExDate();
			 
			 Date date = new Date(exDate);//期望时间
			 String format = df.format(date);
			byte[] d =  hexStringToBytes(format);
			System.out.println(bytesToHexString(d, 0, d.length, " "));
			for(int i=0;i<4;i++) {
				data[39+i] = d[i];
			}
		 }
		 return data;
	}
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
	public static void main(String[] args) throws AnaysisException {
		ScaParamsDto scaParamsDto = new ScaParamsDto();
		PricePlanDto pricePlanDto = new PricePlanDto();
		pricePlanDto.setPrice(3.15f);
		pricePlanDto.setPriceType(PriceType.Normal);
		pricePlanDto.setExDate(new Date().getTime()-30);
		PricePlanDto exPricePlanDto = new PricePlanDto();
		exPricePlanDto.setPrice(3.46f);
		exPricePlanDto.setPriceType(PriceType.Normal);
		exPricePlanDto.setExDate(new Date().getTime()+3000000);
		scaParamsDto.setPricePlanDto(pricePlanDto);
		scaParamsDto.setExPricePlanDto(exPricePlanDto);
		byte[] priceBytes = scaParamsDto.priceBytes();
		System.out.println(bytesToHexString(priceBytes, 0, priceBytes.length, ", 0x"));
		
	}
}
