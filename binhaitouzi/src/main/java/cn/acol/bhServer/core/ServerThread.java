package cn.acol.bhServer.core;

import java.io.IOException;
import java.net.Socket;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cn.acol.bhServer.core.exception.AnaysisException;
import cn.acol.bhServer.core.exception.ConnectionException;
import cn.acol.bhServer.core.utils.SpringUtils;



public class ServerThread implements Runnable {
	private Session session;
	private static Logger logger = LoggerFactory.getLogger(ServerThread.class);
	public ServerThread(Socket socket) {
		this.session = new Session(socket);
	}
	@Override
	public void run() {
		// TODO Auto-generated method stub
		session.setThread(Thread.currentThread());
		try {
			
			Servlet servlet = SpringUtils.getServlet();
			SocketData socketData;
			while((socketData = session.hasDataReached()).len != -1) { //保证为一条完整的请求信息了 且请求头信息正确
				Anaysis anaysis = new Anaysis(socketData); //解析类型
				Response response = new Response(anaysis,session); 
				if(response.isReady()) {
					if(response.getResponseType() == ResponseType.ResponseHeader) {
						servlet.initHandler(new Request(session));
					}else if(response.getResponseType() == ResponseType.upRecordsResponse) {
						servlet.upRecordsHandler(response, new Request(session));
					}
				}
			}
		} catch (ConnectionException e) {
			// TODO Auto-generated catch block
			logger.error(e.getMessage());
		} catch (AnaysisException e) {
			// TODO Auto-generated catch block
			logger.error(e.getMessage());
		}catch(Exception e) {
			e.printStackTrace();
		}
		finally {
			try {
				session.destory();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

}
