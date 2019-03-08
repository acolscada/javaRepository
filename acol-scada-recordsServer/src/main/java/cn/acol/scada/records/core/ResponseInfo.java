package cn.acol.scada.records.core;


public class ResponseInfo {
	private Long collectionTime = null;//ms
	private Long upTime = null;
	private Float price = null;
	
	private Boolean existDevice;//设备是否已经注册
	
	public void setCollectionMinTime(Integer mins) {
		this.collectionTime = mins.longValue()*1000*60;
	}
	public void setUpMinTime(Integer mins) {
		this.upTime = mins.longValue()*1000*60;
	}
	
	public Long getCollectionTime() {
		return collectionTime;
	}
	public Long getUpTime() {
		return upTime;
	}
	public Float getPrice() {
		return price;
	}
	public void setPrice(Float price) {
		this.price = price;
	}
	public void setCollectionTime(Long collectionTime) {
		this.collectionTime = collectionTime;
	}
	public void setUpTime(Long upTime) {
		this.upTime = upTime;
	}
	/**
	 * 
	 * @return 设备是否存在
	 */
	public Boolean isExistDevice() {
		return existDevice;
	}
	/**
	 * 设置设备是否存在
	 * @param existDevice
	 */
	public void setExistDevice(Boolean existDevice) {
		this.existDevice = existDevice;
	}
	
	
	
}
