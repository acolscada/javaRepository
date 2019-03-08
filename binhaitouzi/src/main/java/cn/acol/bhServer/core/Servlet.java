package cn.acol.bhServer.core;

public interface Servlet {
	public void initHandler(Request request);
	public void upRecordsHandler(Response response,Request request);
}
