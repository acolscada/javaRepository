package cn.acol.scada.core.sql.utils;

import java.util.Optional;

import cn.acol.scada.core.exception.SqlGarbageCollectionException;
import cn.acol.scada.core.exception.SqlNoExistException;
import cn.acol.scada.core.repository.BaseRepository;

public class SqlUtil {
	private static<T> boolean isUseful(Optional<T> optional,Long id) throws SqlNoExistException {
		if(!optional.equals(Optional.empty())) {
			return true;
		}
		throw new SqlNoExistException(id);
	}
	/**
	 * 
	 * @param baseRepository
	 * @param id
	 * @return 对象无用则 返回null  对象是期望的则返回对象
	 * @throws SqlNoExistException  数据库中不存在则抛出
	 * @throws SqlGarbageCollectionException  数据库中存在需要回收的资源时抛出异常
	 */
	public static <T> T findById(BaseRepository<T> baseRepository, Long id) throws SqlNoExistException, SqlGarbageCollectionException {
		if(id == null) {
			throw new SqlNoExistException("id参数为NULL");
		}
		Optional<T> findById = baseRepository.findById(id);
		if(isUseful(findById,id)) {
			return findById.get();
		}else {
			throw new SqlGarbageCollectionException(id);
		}
	}
}
