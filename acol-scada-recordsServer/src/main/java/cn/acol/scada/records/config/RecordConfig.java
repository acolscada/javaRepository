package cn.acol.scada.records.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import cn.acol.scada.records.core.Listerner;
import cn.acol.scada.records.core.RequestFilterChain;
import cn.acol.scada.records.core.Session;
import cn.acol.scada.records.core.WarningException;
import cn.acol.scada.records.core.anaysis.AnaysisException;
import cn.acol.scada.records.utils.Utils;

@Configuration
public class RecordConfig {
	private static final Logger log = LoggerFactory.getLogger(RecordConfig.class);

	/**
	 * 此链协议层进行数据处理链
	 * @return
	 */
	@Bean
	public RequestFilterChain requestFilterChain() {
		
		return new RequestFilterChain() {
			
			@Override
			protected void receivedHandle(byte[] data, int len) throws AnaysisException {
				// TODO Auto-generated method stub
				if(len == 0 || len<0) {
					log.info("0字节的请求长度为: "+ len);
					return;
				}
				if(len == 1500 || data[0] != 0x18 || data[len-1] != 0x16 ) {
					
					log.error("进入的数据长度为 :"+len +"首字节为： "+ data[0]+ "尾字节为"+ data[len-1]);
					throw new AnaysisException("收到的流 超出了  协议无法解析");
				}
				
			}
		};
	}
	/**
	 * TCP/IP协议流的监听器   
	 * TCP/IP流在RequestFilterChain
	 * 当且仅当  RequestFilterChain中出现了AnaysisException时  才会进行改监听
	 * @return
	 */
	@Bean
	public Listerner listerner() {
		return new Listerner() {
			@Override
			public void handler(byte[] data, int len, Session session) throws WarningException {
				log.info(session.getSocket().getInetAddress().getAddress() + Utils.bytesToHexStringForWatch(data, len));
				throw new WarningException(session, data, len);
			}

			
		};
	}
}
