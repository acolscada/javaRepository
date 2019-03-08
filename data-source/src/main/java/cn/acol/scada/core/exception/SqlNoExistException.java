package cn.acol.scada.core.exception;

public class SqlNoExistException extends NoExsitException{

	/**
	 * 
	 */
	private static final long serialVersionUID = -8002935970615607384L;

	public SqlNoExistException(Long id) {
		super("数据库中无法找到对应的对象:  对象id: "+id);
		// TODO Auto-generated constructor stub
	}
	public SqlNoExistException(String msg) {
		super(msg);
		// TODO Auto-generated constructor stub
	}
	
}
