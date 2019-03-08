package cn.acol.scada.records.core.anaysis.aike;



import cn.acol.scada.records.core.anaysis.Check;
import cn.acol.scada.records.utils.Utils;

public abstract class AbstractAcolRequestCheck implements Check{
	/**
	 * 校验的使用
	 * @param data 
	 * @param len 校验数据长度
	 * @return 返回校验码 
	 */
	protected abstract short checkCode(byte[] data, int len);
	
	
	@Override
	public boolean dataCheck(byte[] data, int len) {
		// TODO Auto-generated method stub
		if(len<15 || data.length<len) {//按照协议长度不可能小于15 如小于直接返回false
			return false;
		}
		if((data[0] == 0x18) && (data[len-1] == 0x16)) {//起始符号  和 结束符号校验
			
			if(Utils.byteToshort(data[len-3], data[len-2]) == this.checkCode(data, len)) {
				return true;
			}
		}
		return false;
	}

	@Override
	public void entryCheckCode(byte[] data) {
		short checkCode = this.checkCode(data, data.length);
		data[data.length-3] = (byte) (0x00FF&(checkCode>>8));
		data[data.length-2] = (byte) (checkCode&0x00FF);
	}
}
