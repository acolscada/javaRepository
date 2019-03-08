package cn.acol.bhServer.core;


import cn.acol.bhServer.core.exception.ConnectionException;
import cn.acol.device.test.core.DeviceMap;
import cn.acol.scada.core.dto.SimpleResponse;

//@Component
public class ServletTestImpl implements Servlet{
	@Override
	public void initHandler(Request request) {
		// TODO Auto-generated method stub
	}

	@Override
	public void upRecordsHandler(Response response, Request request) {
		// TODO Auto-generated method stub
		try {
			SimpleResponse	upDateTime = request.upDateTime();
			if(!upDateTime.isOk()) {
				System.out.println("设备号为："+response.getDeviceNum()+"的设备更新时间失败原因为： "+upDateTime.getMessage());
			}
		} catch (ConnectionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		DeviceMap.addDevice(response.getDeviceNum(), request);
		
		
	}

}
