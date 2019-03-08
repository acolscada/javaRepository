package cn.acol.scada.records.core.command;



import java.util.Calendar;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import cn.acol.scada.records.core.ScadaControl;
import cn.acol.scada.records.core.anaysis.Check;
import cn.acol.scada.records.core.anaysis.Encryption;
import cn.acol.scada.records.utils.Utils;

@Component
@Scope("prototype")
public class AcolScadaControl implements ScadaControl,InitializingBean{
	private byte[] sendData = new byte[37];
	@Autowired
	private Check check;
	@Autowired
	private Encryption encryption;
	@Override
	public void afterPropertiesSet() throws Exception {
		// TODO Auto-generated method stub
		sendData[0] = 0x18;//起始符
		sendData[1] = 1; //版本
		sendData[2] = 0;//控制符号
		sendData[3] = 0;//说明码
		sendData[4] = 0;//数据长度H
		sendData[5] = 0x1C;//数据长度L   28
	    sendData[36] = 0x16;//结束符
	}
	/**
	 * 
	 * @param b 字节
	 * @param position 大小
	 */
	private void byteToByteBcd(int b, int position) {
		if(b>=100) {
			sendData[position] = 0;
		}
		int a = ((b/10)<<4) +(b%10);
		sendData[position] = (byte) a;
	}
	/**
	 * 
	 * @param data   数据
	 * @param lposition  低位所在位置
	 * @param len   长度
	 */
	private void longToByte(long data, int lposition, int len) {
		int i;
		for(i=0; (data>0)&&(i<len);i++) {  //data = 100  len = 3  i = 2
			byteToByteBcd((int)(data%100), lposition-i);
			data = data/100;
		}
		while(i<len) {
			sendData[lposition-i] = 0;
			i++;
		}
	}
	public static void main(String[] args) throws Exception {
		
		AcolScadaControl acolScadaControl = new AcolScadaControl();
		acolScadaControl.afterPropertiesSet();
		acolScadaControl.flushTimes();
		acolScadaControl.setCollectionTimes(8665200000L);
		acolScadaControl.setUpTimes(86400000L);
		acolScadaControl.setPrice((float)74.3);
		//Utils.bytesToHexString(acolScadaControl.getControllerCommand(), 7, 13, "  ");
		String bytesToHexString = Utils.bytesToHexStringForWatch(acolScadaControl.getControllerCommand(), 30);
		
		
		System.out.println(bytesToHexString);
	}
	@Override
	public void setCollectionTimes(Long ms) {
		// TODO Auto-generated method stub
		sendData[3] = (byte) (sendData[3] | 0x40);
		this.longToByte(ms/60000, 14, 3);
	}

	@Override
	public void setUpTimes(Long ms) {
		// TODO Auto-generated method stub
		sendData[3] = (byte) (sendData[3] | 0x20);
		longToByte(ms/60000, 17,3);
	}

	@Override
	public void flushTimes() {
		// TODO Auto-generated method stub
		sendData[3] = (byte) (sendData[3] | 0x80);
		Calendar now = Calendar.getInstance();
		this.longToByte((long)now.get(Calendar.YEAR)-2000,7,1);
		this.longToByte((long)now.get(Calendar.MONTH)+1,8,1);
		this.longToByte((long)now.get(Calendar.DAY_OF_MONTH),9,1);
		this.longToByte((long)now.get(Calendar.HOUR_OF_DAY),10,1);
		this.longToByte((long)now.get(Calendar.MINUTE),11,1);
	}


	@Override
	public byte[] getControllerCommand() {
		// TODO Auto-generated method stub
		byte[] encrpt = encryption.encrpt(sendData, sendData.length);
		check.entryCheckCode(encrpt);
		return encrpt;
	}

	@Override
	public void setPrice(float price) {
		// TODO Auto-generated method stub
		//sendData[3] = (byte) (sendData[3] | 0x10);
		//longToByte((long)(price*10), 19,2);
	}
}
