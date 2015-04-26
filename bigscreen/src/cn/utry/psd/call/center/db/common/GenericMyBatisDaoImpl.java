package cn.utry.psd.call.center.db.common;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;

import javax.annotation.Resource;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.support.SqlSessionDaoSupport;

/**
 * 数据访问层单表操作基类 
 * @author chenql
 * @createDate 2012-11-28
 * @param <T>
 * @param <PK>
 */
public abstract class GenericMyBatisDaoImpl<T, PK extends Serializable> extends
		SqlSessionDaoSupport implements GenericDao<T, PK> {

	private Class<T> entityClass;

	private String mapperNamespace;

	private static final String packageName = "cn.utry.psd.call.center.db.xml.mysql.";

	@SuppressWarnings("unchecked")
	public GenericMyBatisDaoImpl() {
		//通过反射获取T的类型信息实例
		this.entityClass = (Class<T>) ((ParameterizedType) this.getClass().getGenericSuperclass())
				.getActualTypeArguments()[0];
		this.mapperNamespace = packageName + entityClass.getSimpleName() + "Mapper";
	}

    @Resource
    public void setSqlSessionFactory(SqlSessionFactory sqlSessionFactory){
        super.setSqlSessionFactory(sqlSessionFactory);
    }

	/**
	 * 获取xml的命名空间
	 * @return
	 */
	public String getMapperNamespace(String methodName) {
		return mapperNamespace + "." + methodName;
	}

    /**
     * 根据主键删除记录
     * @param id 主键
     * @return
     */
	public int deleteByPrimaryKey(PK id) {
		SqlSession session = getSqlSession();
		Integer count = session.delete(getMapperNamespace("deleteByPrimaryKey"), id);
		//		if(count == 0){
		//			throw new RuntimeException("删除的数据不存在！");
		//		}
		return count;
	}

	/**
	 * 选择性的保存，属性为null不保存
	 */
	public int insertSelective(T entity) {
		SqlSession session = getSqlSession();
		Integer count = session.insert(getMapperNamespace("insertSelective"), entity);
		//		if(count == 0){
		//			throw new RuntimeException("数据保存失败！");
		//		}
		return count;
	}

	/**
	 * 根据主键查询
	 */
	public T selectByPrimaryKey(PK id) {
		SqlSession session = getSqlSession();
		T entity = session.selectOne(getMapperNamespace("selectByPrimaryKey"), id);
		return entity;
	}

	/**
	 * 选择性的根据主键更新
	 */
	public int updateByPrimaryKeySelective(T entity) {
		SqlSession session = getSqlSession();
		Integer count = session.update(getMapperNamespace("updateByPrimaryKeySelective"), entity);
		//		if(count == 0){
		//			throw new RuntimeException("更新的数据不存在！");
		//		}
		return count;
	}

	/**
	 * 全表更新
	 */
	public int updateByPrimaryKey(T entity) {
		SqlSession session = getSqlSession();
		Integer count = session.update(getMapperNamespace("updateByPrimaryKey"), entity);
		//		if(count == 0){
		//			throw new RuntimeException("更新的数据不存在！");
		//		}
		return count;
	}
}
