package cn.acol.scada.records.core.anaysis;

import java.util.List;

import cn.acol.scada.records.core.Request;
import cn.acol.scada.records.domain.UpRecord;

/**
 *上传信息的解析器
 * @author DaveZhang
 *
 */
public interface RecordsAnaysis {
	public abstract List<UpRecord> anaysisData(Request request) throws AnaysisException;
}
