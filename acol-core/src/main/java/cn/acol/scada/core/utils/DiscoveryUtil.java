package cn.acol.scada.core.utils;


import org.springframework.stereotype.Component;


@Component
public class DiscoveryUtil {
	//private  Logger logger = LoggerFactory.getLogger(DiscoveryUtil.class);
	//@Autowired
	//private DiscoveryClient discoveryClient;
	
	
	public String getAcolOldScadaApiUrl() throws Exception {
		return "http://localhost:10000";
		/*List<ServiceInstance> instances = discoveryClient.getInstances("ACOL-OLD-SCADA-API");
		for(ServiceInstance serviceInstance : instances) {
			return serviceInstance.getUri().toString();
		}
		logger.error("应用：ACOL-OLD-SCADA-API在eureka服务器中无法找到可能已经下线");
		throw new Exception("请检查eureka服务器");*/
	}

	
	public String getAcolScadaManageUrl() throws Exception {
		/*List<ServiceInstance> instances = discoveryClient.getInstances("ACOL-SCADA-MANAGE");
		for(ServiceInstance serviceInstance : instances) {
			return serviceInstance.getUri().toString();
		}
		logger.error("应用：ACOL-SCADA-MANAGE在eureka服务器中无法找到可能已经下线");
		throw new Exception("请检查eureka服务器");*/
		
		return "http://112.65.214.63:8888";
	}
	
	public String getRecordsUrl() throws Exception{
		/*List<ServiceInstance> instances = discoveryClient.getInstances("ACOL-SCADA-RECORDS");
		for(ServiceInstance serviceInstance : instances) {
			return serviceInstance.getUri().toString();
		}
		logger.error("应用：ACOL-SCADA-RECORDS在eureka服务器中无法找到可能已经下线");
		throw new Exception("请检查eureka服务器");*/
		return "http://47.105.93.170:9999";
	}
	
}
