package cn.acol.security.core.properties;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class SocialProperties {
	@Autowired
	private QQProperties qqProperties;

	public QQProperties getQqProperties() {
		return qqProperties;
	}

	public void setQqProperties(QQProperties qqProperties) {
		this.qqProperties = qqProperties;
	}
	
}
