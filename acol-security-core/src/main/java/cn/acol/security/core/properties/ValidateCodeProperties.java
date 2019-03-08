package cn.acol.security.core.properties;



public class ValidateCodeProperties {
	
	
	private ImageCodeProperties imageCode = new ImageCodeProperties();
	
	
	private SmsCodeProperties smsCode = new ImageCodeProperties();

	public ImageCodeProperties getImageCodeProperties() {
		return imageCode;
	}

	public void setImageCodeProperties(ImageCodeProperties imageCodeProperties) {
		this.imageCode = imageCodeProperties;
	}
	public SmsCodeProperties getSmsCodeProperties() {
		return smsCode;
	}

	public void setSmsCodeProperties(SmsCodeProperties smsCodeProperties) {
		this.smsCode = smsCodeProperties;
	}
	
	
}
