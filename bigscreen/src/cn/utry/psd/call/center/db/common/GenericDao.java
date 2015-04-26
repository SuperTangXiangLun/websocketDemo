package cn.utry.psd.call.center.db.common;

import java.io.Serializable;

/**
 * The basic GenericDao interface with CRUD methods Finders are added with
 * interface inheritance and AOP introductions for concrete implementations
 * 
 * Extended interfaces may declare methods starting with find... list...
 * iterate... or scroll... They will execute a preconfigured query that is
 * looked up based on the rest of the method name
 * @author chenql
 */
public interface GenericDao<T, PK extends Serializable> {
	
	int deleteByPrimaryKey(PK id);

	int insertSelective(T entity);

	T selectByPrimaryKey(PK id);

	int updateByPrimaryKeySelective(T entity);

	int updateByPrimaryKey(T entity);
}
