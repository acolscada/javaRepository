package cn.acol.scada.core.exception;

public class SqlGarbageCollectionException extends AnaysisException{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public SqlGarbageCollectionException(Long id) {
		super("数据库中存在无效的资源：id为  "+id);
		// TODO Auto-generated constructor stub
	}

}
