package cn.acol.security.core.validate.code.sms;

public class DefaultSmsCodeSender implements SmsCodeSender{

	@Override
	public void send(String mobile, String code) {
		// TODO Auto-generated method stub
		System.out.println("向手机"+mobile+"发送短信验证码"+code);
	}

}
