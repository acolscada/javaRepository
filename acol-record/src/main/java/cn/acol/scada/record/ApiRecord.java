package cn.acol.scada.record;

import org.springframework.web.client.RestTemplate;

import cn.acol.scada.core.dto.ScaParamsDto;

/**
 * 此接口用与记录服务器公共API 包
 * @author DaveZhang
 *
 */
public interface ApiRecord {
	
	//private ScaParamsDto scaParamsDto;
	
//	private RestTemplate restTemplate;
	/**
	 * 请求设备的参数  
	 * 	如果回话中需要用到此参数请   
	 * 推荐当获取到deviceNum时马上掉用此API 
	 * @param deviceNum
	 */
	public void requestDeviceParams(String deviceNum);
	
	/**
	 * 获取requestDeviceParams 方法请求到的设备参数
	 * @return
	 */
	public ScaParamsDto getDeviceParams();
	
	/*{
		try {
			
			scaParamsDto = restTemplate.getForObject(url+"/device/"+deviceNum+"/scaParams", ScaParamsDto.class);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			log.error("无法请求参数信息： "+e.getMessage());
			throw new RuntimeException();
		}
	}*/
}
