package cn.acol.scada.records.core;

import java.io.IOException;
import java.net.Socket;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cn.acol.scada.records.core.anaysis.AnaysisException;
import cn.acol.scada.records.utils.SpringUtils;


public class ServerThread implements Runnable{
	private Session session;
	private static Logger logger = LoggerFactory.getLogger(ServerThread.class);
	public ServerThread(Socket socket) {
		this.session = new Session(socket);
	}
	@Override
	public void run() {
		
		Servlet servlet = SpringUtils.getServlet();
		try {
			SocketData requestData;
			while((requestData = session.hasDataReached()).len != -1) { //保证为一条完整的请求信息了 且请求头信息正确
				Request request = SpringUtils.getRequest(session);//进入request时除非全部请求结束否则永远不会结束
				if(request.isReached(requestData.data, requestData.len)) {
					Response response = SpringUtils.getResponse(session);
					RequestType requestType = request.requestHeader().getRequestType();
					if(requestType == RequestType.Init) {
						servlet.initHandler(request.requestHeader(), response);
					}else if(requestType == RequestType.UpRecords) {
						servlet.upRecordsHandler(request, response);
					}else if(requestType == RequestType.Warning) {
						return;
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
				servlet.afterLastRequestHandlered(session);
				session.destory();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				logger.error(e.getMessage());
			}
		}
	}
}
