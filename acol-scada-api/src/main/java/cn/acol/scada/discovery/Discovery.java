package cn.acol.scada.discovery;

import cn.acol.scada.core.utils.AdminUtils;
import cn.acol.scada.core.utils.DiscoveryUtil;

public class Discovery {
	private DiscoveryUtil discoveryUtil;
	public String getScadaManageUrl() {
		try {
			return discoveryUtil.getAcolScadaManageUrl()+"/" +AdminUtils.getUserName();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			throw new RuntimeException(e);
		}
	}
}
