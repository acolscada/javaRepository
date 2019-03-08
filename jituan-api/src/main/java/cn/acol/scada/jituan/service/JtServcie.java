package cn.acol.scada.jituan.service;

import cn.acol.scada.core.dto.SimpleResponse;
import cn.acol.scada.jituan.dto.FailedSca;
import cn.acol.scada.jituan.dto.JtMessageLog;
import cn.acol.scada.jituan.dto.JtRMessage;
import cn.acol.scada.jituan.dto.JtReplayMessage;

public interface JtServcie {
	/**
	 * 
	 * @param failedSca
	 * @return
	 */
	 JtRMessage paigong(FailedSca failedSca);
	 
	 /**
	  * 
	  * @param jtMessageLog
	  * @return
	  */
	 JtRMessage messagelog(JtMessageLog jtMessageLog);
	 /**
	  * 
	  * @param jtReplayMessage
	  * @return
	  */
	 SimpleResponse huitian(JtReplayMessage jtReplayMessage);
}
