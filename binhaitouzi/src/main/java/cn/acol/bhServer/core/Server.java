package cn.acol.bhServer.core;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class Server {
	private Logger logger = LoggerFactory.getLogger(Server.class);
	private int port = 10100;
	public void start() {
		try {
			@SuppressWarnings("resource")
			ServerSocket serverSocket = new ServerSocket(port);
			while(true) {
					Socket socket = serverSocket.accept();
					/**
					 * 线程中解决
					 */
					try {
						new Thread(new ServerThread(socket)).start();
					}catch (Exception e) {
						// TODO: handle exception
						logger.error(e.getMessage());
					}
			}
		} catch (IOException e) {
			logger.error("采集器数据上传服务器启动失败：port="+port + "   "+" 错误为："+e.getMessage());
			throw new RuntimeException(e);
		}
	}
}
