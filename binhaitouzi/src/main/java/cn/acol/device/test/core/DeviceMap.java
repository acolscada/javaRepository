package cn.acol.device.test.core;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.acol.bhServer.core.Request;

public class DeviceMap {
	/**
	 * 保存设备号  和响应信息
	 */
	private static Map<String, Request> map = new HashMap<>();
	/**
	 * 获取控制器
	 * @param deviceNum
	 * @return
	 */
	public static Request getRequestByDeviceNum(String deviceNum) {
		return map.get(deviceNum);
	}
	/**
	 * 添加控制器
	 * @param deviceNum
	 * @param response
	 */
	public static void addDevice(String deviceNum, Request request) {
		map.put(deviceNum, request);
		try {
			request.getSession().sleep(60);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	/**
	 * 去除此设备
	 * @param deviceNum
	 * @throws IOException
	 */
	public static void moveDevice(String deviceNum){
		map.remove(deviceNum);
	}
	
	public static List<String> getAllDevices(){
		List<String> retList = new ArrayList<>();
		for(String keyString : map.keySet()) {
			retList.add(keyString);
		}
		return retList;
	}
	public static void moveAll() throws IOException {
		for(String keyString : map.keySet()) {
			moveDevice(keyString);
		}
	}
	
}
