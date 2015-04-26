package cn.utry.psd.call.center.db.common;

import java.io.Serializable;
import java.util.List;

/**
 * @author chenql
 * @version 1.0
 */
public interface ExampleGenericDao<T, PK extends Serializable, TExample> extends GenericDao<T, PK> {

    /**
     * 按条件统计
     * @param example 条件
     * @return 记录条数
     */
    int countByExample(TExample example);

    /**
     * 按条件查询记录
     * @param example 条件
     * @return 多条记录
     */
    List<T> selectByExample(TExample example);
    

    /**
     * 按条件查询分页记录
     * @param example 条件
     * @param offset
     * @param limit
     * @return 多条记录
     */
    List<T> selectPagedByExample(TExample example,int offset, int limit);

    /**
     * 按条件查询记录
     * @param example 条件
     * @return 单条记录
     */
    T selectOneByExample(TExample example);

    /**
     * 按条件更新记录
     * @param record 更新内容
     * @param example 条件
     * @return 影响行数
     */
    int updateByExample(T record, TExample example);

    /**
     * 按条件更新记录
     * @param record 更新内容
     * @param example 条件
     * @return 影响行数
     */
    int updateByExampleSelective(T record, TExample example);

    /**
     * 按条件删除记录
     * @param example 条件
     * @return 影响行数
     */
    int deleteByExample(TExample example);
}
