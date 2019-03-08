package cn.acol.bhServer.core;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

import cn.acol.bhServer.core.exception.ConnectionException;
import cn.acol.bhServer.core.utils.Util;
import cn.acol.scada.core.dto.PricePlanDto;
import cn.acol.scada.core.dto.SimpleResponse;
import cn.acol.scada.core.exception.AnaysisException;
import cn.acol.scada.core.utils.ScaParamUtil;

public class Request {
	private Session session;
	private Check check = Check.getInStance();
	public Request(Session session) {
		this.session = session;
	}
	public Session getSession() {
		return session;
	}
	private SocketData getSocketData() throws ConnectionException {
		SocketData socketData = null;
		while((socketData = session.hasDataReached()).len != -1) {
			break;
		}
		return socketData;
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
	/**
	 * 会阻塞 直到响应后才会返回
	 * @param funCode 正常收到的功能码
	 * @param command
	 * @return
	 * @throws ConnectionException
	 */
	private synchronized SimpleResponse commandControl(byte[] command) throws ConnectionException {
		byte funCode = command[3];
		command[1] = (byte) ((command.length>>8)&0xff);
		command[2] = (byte) (command.length&0xff);
		check.entryCheckCode(command);
		try {
			System.out.println("发送了命令:  "+bytesToHexString(command, 0, command.length, "-"));
			session.getOutputStream().write(command);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			throw new ConnectionException("发送控制命令失败", e);
		}
		
		SocketData socketData = getSocketData();//接受到数据后
		if(!check.dataCheck(socketData.data, socketData.len)) {
			//return socketData;
			return new SimpleResponse(-1,"CRC校验未通过");
		}
		
		if(socketData.data[0] != 0x3E) {
			return new SimpleResponse(-1,"不是响应报文： 接受标识为："+ socketData.data[0]);
		}
		if(funCode != socketData.data[3]) {
			return new SimpleResponse(-1,"功能码错误 理论为： "+funCode+"实际为： "+socketData.data[3]);
		}
		return SimpleResponse.getNormalResponse();
	}
	/**
	 * 会阻塞
	 * @return 正常返回 SimpleResponse.getNormalResponse()
	 * @throws ConnectionException  连接错误
	 */
	public  SimpleResponse closeValueStatus() throws ConnectionException {//关阀指令
		byte[] data = new byte[] {0x3C,0x00,0x06,0x15,0x73,0x53};
		return  this.commandControl(data);
	}
	
	public SimpleResponse openValueStatus() throws ConnectionException {//开阀命令
		byte[] data = new byte[] {0x3C,0x00,0x06,0x14,0x73,0x53};
		return  this.commandControl(data);
	}
	private static DateFormat df = new SimpleDateFormat("yyMMddHHmmss");
	public SimpleResponse upDateTime() throws ConnectionException {
		byte[] data = new byte[] {0x3C,0x00,0x0C,0x18,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00};
		String format = df.format(new Date());
		byte[] hexStringToBytes = Util.hexStringToBytes(format);
		for(int i=0;i<hexStringToBytes.length;i++) {
			data[i+4] = hexStringToBytes[i];
		}
		return this.commandControl(data);
	}
	public SimpleResponse closeConnection() {
		try {
			session.getOutputStream().write(new byte[] {0x3C,0x00,0x06, 0x12, (byte) 0x8f,(byte) 0xD9});
			session.destory();
			return SimpleResponse.getNormalResponse();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			return new SimpleResponse(-1,"断开连接失败");
		}
	}
	/**
	 * 充值剩余量 充值
	 * @param sum
	 * @return
	 * @throws ConnectionException 
	 */
	public SimpleResponse addPlusSum(double sum , long totalPlus) throws ConnectionException {
		
		byte[] data = {0x3c,0x00,0x00,(byte) 0xa1,   0x00,0x00,0x00,0x00,  0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,  0x00,0x00};
		if(sum<0){
			data[3] = (byte) 0xa2;
			sum = -sum;
		}
		int plusSum = new Double(sum).intValue();//增量
		
		for (int i = 0; i < 4; i++) { 
			int offset = 64 - (i + 1) * 8; 
			data[i+4] = (byte) ((plusSum >> offset) & 0xff);
		}
		
		for (int i = 8; i < 16; i++) {
			int offset = 64 - (i -7) * 8; 
			data[i] = (byte) ((totalPlus >> offset) & 0xff);
		}
		return this.commandControl(data);
	}
	
	public SimpleResponse changePrice(PricePlanDto pricePlanDto, PricePlanDto exPricePlanDto) throws AnaysisException, ConnectionException {
		byte[] priceBytes = ScaParamUtil.getPriceBytes(pricePlanDto, exPricePlanDto);
		byte[] data = new byte[priceBytes.length+6];
		data[0] = 0x3c;
		data[3] = (byte) 0xa4;
		for(int i=0;i<priceBytes.length;i++) {
			data[i+4] = priceBytes[i];
		}
		return this.commandControl(data);
	}
	
	private static byte byteTobcdByte(byte data) throws AnaysisException {
		if(data >100) {
			throw new AnaysisException("参数不能大于100");
		}
		return (byte) (((data%10)&0x0f | ((data/10)<<4))&0xff);
		// (byte) (((byte)((data%10)&0x0f) | (byte)(((data/10)<<4) &0xf0))&0xff);
		//data%10
	}
	public static void main(String[] args) throws AnaysisException {
		System.out.println(byteTobcdByte((byte)(84&0xff)));
		System.out.println(0x50);
		System.out.println(0x54%10);
	}
	public SimpleResponse changeUpTime(int upTime) throws ConnectionException, AnaysisException {
		byte[] data =  new byte[31];
		data[0] = 0x3c;
		data[3] = 0x19;
		int times = 1440/upTime;
		if(times >=12 ) {
			times = 12;
		}else if(times<12 && times>8) {
			times = 12;
		}else if(times<=8 && times >6) {
			times = 8;
		}else if( times<=6 && times>4) {
			times = 6;
		}else if(times <=0){
			times = 1;
		}
		data[4] =  (byte) (times&0xff);
		Random random = new Random();
		int nextInt = random.nextInt();
		byte a = (byte) ((nextInt&0xff)/5);
		//一天2次 则 12   一天7次 只能变为
		byte deltHour = (byte) (24/times);
		for(int i=0;i<times;i++) {
			//将时间放入data中
			
			data[i*2+5] = byteTobcdByte((byte) (deltHour*i));
			data[i*2+6] = byteTobcdByte(a);
		}
		return this.commandControl(data);
	}
	public SimpleResponse changeColTime(int Time) throws ConnectionException {
		byte[] data = {0x3c,0x00,0x00,0x05,(byte) (Time&0xff),0x00,0x00};
		return this.commandControl(data);
	}
	/**
	 * @param plusSum
	 * @param price
	 * @return
	 * @throws ConnectionException
	 */
	public SimpleResponse commitPlusSumAndPrice(double plusSum,float price) throws ConnectionException {
		byte[] data = {0x3c,0x00,0x12,0x41,  0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,  0x00,0x00,0x00,0x00, 0x00, 0x00};
		
		long sum = new Double(plusSum*10000).longValue();
		
		byte[] buffer = new byte[8];
		for (int i = 0; i < 8; i++) { 
			int offset = 64 - (i + 1) * 8; 
			buffer[i] = (byte) ((sum >> offset) & 0xff);
		}
		for(int i=8;i>0;i--) { 
			data[3+i] = buffer[i-1];
		}
		
		Integer integer2 = new Float(price*10000).intValue();
		String string2 = integer2.toString();
		if(string2.length()%2 ==1) {
			string2 = "0"+string2;
		}
		byte[] p = Util.hexStringToBytes(string2);
		if(p.length>4) {
			return new SimpleResponse(-1,"单价过大");
		}
		for(int i=p.length;i>0;i--) {
			data[15-(p.length-i)] = p[i-1];
		}
		
		return this.commandControl(data);
	}
}
