package cn.acol.scada.records.core.anaysis.aike;

import org.springframework.stereotype.Component;

import cn.acol.scada.records.core.anaysis.Encryption;

/**
 * 
 * @author DaveZhang
 *
 */
@Component
public class AcolEncryptionImpl implements Encryption{
	
	@Override
	public byte[] decrpt(byte[] data, int len) {
		// TODO Auto-generated method stub
		if(data.length<len) {
			return null;
		}
		byte[] ret = new byte[len];
		int i=0;
		while(i<13) {
			ret[i] = data[i];
			i++;
		}
		while(i<len-3) {
			ret[i] = (byte) (data[i]-0x33);
			i++;
		}
		while(i<len) {
			ret[i] = data[i];
			i++;
		}
		return ret;
	}

	@Override
	public byte[] encrpt(byte[] data, int len) {
		// TODO Auto-generated method stub
		for(int i=6;i<data.length-3;i++) {
			data[i] = (byte) (data[i] + 0x33);
		}
		return data;
	}

}
