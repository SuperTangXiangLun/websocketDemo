package cn.utry.psd.call.center.db.common;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;
import org.apache.ibatis.session.SqlSession;


/**
 * @author chenql
 * @version 1.0
 */
public abstract class ExampleGenericMyBatisDaoImpl<T, PK extends Serializable, TExample> extends GenericMyBatisDaoImpl<T, PK> implements ExampleGenericDao<T, PK, TExample> {

    @Override
    public int countByExample(TExample example) {
        SqlSession session = getSqlSession();
        return session.selectOne(getMapperNamespace("countByExample"), example);
    }

    @Override
    public List<T> selectByExample(TExample example) {
        SqlSession session = getSqlSession();
        return session.selectList(getMapperNamespace("selectByExample"), example);
    }
    

    @Override
    public T selectOneByExample(TExample example) {
        SqlSession session = getSqlSession();
        return session.selectOne(getMapperNamespace("selectByExample"), example);
    }

    @Override
    public int updateByExample(T record, TExample example) {
        SqlSession session = getSqlSession();
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("record", record);
        params.put("example", example);
        return session.update(getMapperNamespace("updateByExample"), params);
    }

    @Override
    public int updateByExampleSelective(T record, TExample example) {
        SqlSession session = getSqlSession();
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("record", record);
        params.put("example", example);
        return session.update(getMapperNamespace("updateByExampleSelective"), params);
    }

    @Override
    public int deleteByExample(TExample example) {
        SqlSession session = getSqlSession();
        return session.delete(getMapperNamespace("deleteByExample"), example);
    }

    @Override
    public List<T> selectPagedByExample(TExample example, int offset, int limit) {
        SqlSession session = getSqlSession();
        return session.selectList(getMapperNamespace("selectByExample"), example, new RowBounds(offset, limit));
    }
}
