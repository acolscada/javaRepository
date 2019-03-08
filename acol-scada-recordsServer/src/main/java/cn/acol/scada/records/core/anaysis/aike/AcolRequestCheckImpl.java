package cn.acol.scada.records.core.anaysis.aike;

import org.springframework.stereotype.Component;

import cn.acol.scada.records.utils.Utils;
/**
 * 进行了简单的和校验
 * 使用ACOL协议  需要修改校验时只需 继承 AbstractAcolRequestCheck
 * @author DaveZhang
 *
 */
@Component
public class AcolRequestCheckImpl extends AbstractAcolRequestCheck{

	@Override
	protected short checkCode(byte[] data, int len) {
		// TODO Auto-generated method stub
		if(len <3) {
			return 0;
		}
		byte checkSum = 0;
		for(int i=0;i<len-3;i++) {
			checkSum = (byte) ((data[i]+checkSum)&0xFF);
		}
		return Utils.byteToshort(checkSum, data[len-2]);
	}

}
