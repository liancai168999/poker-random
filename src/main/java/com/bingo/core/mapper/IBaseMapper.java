package com.bingo.core.mapper;

import com.bingo.core.exceptions.DaoException;
import org.apache.ibatis.annotations.*;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import java.util.Map;

public interface IBaseMapper<T extends Serializable, V extends Serializable> {


    /**
     * insert
     */

    @InsertProvider(type = BaseSqlProvider.class, method = "insert")
    @SelectKey(statement="SELECT LAST_INSERT_ID()", keyProperty="id", before=false, resultType=Integer.class)
    int insert(T t) throws DaoException;

    @InsertProvider(type = BaseSqlProvider.class, method = "insertByBatch")
    int insertByBatch(@Param("entitiesList") Collection<T> entitiesList)throws DaoException;


    /**
     * update
     */

    @UpdateProvider(type = BaseSqlProvider.class, method = "update")
    int update(T t);

//    @UpdateProvider(type = BaseSqlProvider.class, method = "updateByBatch")
//    int updateByBatch(@Param("entityList") Collection<T> entityList);



    /**
     *   逻辑删除
     */

    @DeleteProvider(type = BaseSqlProvider.class, method = "deleteAll")
    int deleteAll(@Param("class") Class clazz);

    @DeleteProvider(type = BaseSqlProvider.class, method = "deleteById")
    int deleteById(@Param("class") Class clazz, @Param("id") Serializable id);

    @DeleteProvider(type = BaseSqlProvider.class, method = "deleteByEntity")
    int deleteByEntity(T t);

    @DeleteProvider(type = BaseSqlProvider.class, method = "deleteByIds")
    int deleteByIds(@Param("class") Class clazz, @Param("ids") Serializable[] ids);

    @DeleteProvider(type = BaseSqlProvider.class, method = "deleteByField")
    int deleteByField(@Param("class") Class clazz, @Param("fieldName") String fieldName, @Param("fieldValue") Object fieldValue);

    @DeleteProvider(type = BaseSqlProvider.class, method = "deleteByFields")
    int deleteByFields(@Param("class") Class clazz, @Param("fieldNames") String[] fieldNames, @Param("fieldValues") Object[] fieldValues);

    @DeleteProvider(type = BaseSqlProvider.class, method = "deleteByMap")
    int deleteByMap(@Param("class") Class clazz, @Param("params") Map<String, String> params);


    /**
     *  物理删除
     */

    @DeleteProvider(type = BaseSqlProvider.class, method = "deleteByIdAndTrue")
    int deleteByIdAndTrue(@Param("class") Class clazz, @Param("id") Serializable id);


    /**
     * get
     */

    @SelectProvider(type = BaseSqlProvider.class, method = "getById")
    T getById(@Param("class") Class clazz, @Param("id") Serializable id);

    @SelectProvider(type = BaseSqlProvider.class, method = "getByMap")
    T getByMap(@Param("class") Class clazz, @Param("params") Map<String, String> params);

    @SelectProvider(type = BaseSqlProvider.class, method = "getByField")
    T getByField(@Param("class") Class clazz, @Param("fieldName") String fieldName, @Param("fieldValue") Object fieldValue);

    @SelectProvider(type = BaseSqlProvider.class, method = "getByFields")
    T getByFields(@Param("class") Class clazz, @Param("fieldNames") String[] fieldNames, @Param("fieldValues") Object[] fieldValues);


    @SelectProvider(type = BaseSqlProvider.class, method = "getVoById")
    V getVoById(@Param("class") Class clazzVo, @Param("id") Serializable id);

    @SelectProvider(type = BaseSqlProvider.class, method = "getVoByMap")
    V getVoByMap(@Param("class") Class clazz, @Param("params") Map<String, String> params);

    @SelectProvider(type = BaseSqlProvider.class, method = "getVoByField")
    V getVoByField(@Param("class") Class clazz, @Param("fieldName") String fieldName, @Param("fieldValue") Object fieldValue);

    @SelectProvider(type = BaseSqlProvider.class, method = "getVoByFields")
    V getVoByFields(@Param("class") Class clazz, @Param("fieldNames") String[] fieldNames, @Param("fieldValues") Object[] fieldValues);


    /**
     * list
     */
    @SelectProvider(type = BaseSqlProvider.class, method = "listAll")
    public List<T> listAll(@Param("class") Class clazz);

    @SelectProvider(type = BaseSqlProvider.class, method = "listAllAndOrder")
    public List<T> listAllAndOrder(@Param("class") Class clazz, @Param("orderName") String orderName, @Param("ascending") boolean ascending)  ;

    @SelectProvider(type = BaseSqlProvider.class, method = "listByIds")
    public List<T> listByIds(@Param("class") Class clazz, @Param("ids") Serializable[] ids);

    @SelectProvider(type = BaseSqlProvider.class, method = "listByField")
    public List<T> listByField(@Param("class") Class clazz, @Param("fieldName") String fieldName, @Param("fieldValue") Object fieldValue);

    @SelectProvider(type = BaseSqlProvider.class, method = "listByFields")
    public List<T> listByFields(@Param("class") Class clazz, @Param("fieldNames") String[] fieldNames, @Param("fieldValues") Object[] fieldValues);

    @SelectProvider(type = BaseSqlProvider.class, method = "listByFieldAndOrder")
    public List<T> listByFieldAndOrder(@Param("class") Class clazz, @Param("fieldName") String fieldName, @Param("fieldValue") Object fieldValue, @Param("orderName") String orderName, @Param("ascending") boolean ascending);

    @SelectProvider(type = BaseSqlProvider.class, method = "listByFieldsAndOrder")
    public List<T> listByFieldsAndOrder(@Param("class") Class clazz, @Param("fieldNames") String[] fieldNames, @Param("fieldValues") Object[] fieldValue, @Param("orderName") String orderName, @Param("ascending") boolean ascending);

    @SelectProvider(type = BaseSqlProvider.class, method = "listByFieldsAndOrders")
    public List<T> listByFieldsAndOrders(@Param("class") Class clazz, @Param("fieldNames") String[] fieldNames, @Param("fieldValues") Object[] fieldValues, @Param("orderNames") String[] orderNames, @Param("ascendings") boolean[] ascendings);

    @SelectProvider(type = BaseSqlProvider.class, method = "listByMap")
    public List<T> listByMap(@Param("class") Class clazz, @Param("params") Map<String, String> params);

    @SelectProvider(type = BaseSqlProvider.class, method = "listByMapAndOrder")
    public List<T> listByMapAndOrder(@Param("class") Class clazz, @Param("params") Map<String, String> params, @Param("orderName") String orderName, @Param("ascending") boolean ascending);

    @SelectProvider(type = BaseSqlProvider.class, method = "listByMapAndOrders")
    public List<T> listByMapAndOrders(@Param("class") Class clazz, @Param("params") Map<String, String> params, @Param("orderNames") String[] orderNames, @Param("ascendings") boolean[] ascendings);


    /**
     *  query
     */

    @SelectProvider(type = BaseSqlProvider.class, method = "queryAll")
    public List<V> queryAll(@Param("class") Class clazz, @Param("pageNo") int pageNo, @Param("pageSize") int pageSize);

    @SelectProvider(type = BaseSqlProvider.class, method = "queryAllAndOrder")
    public List<V> queryAllAndOrder(@Param("class") Class clazz, @Param("pageNo") int pageNo, @Param("pageSize") int pageSize, @Param("orderName") String orderName, @Param("ascending") boolean ascending)  ;

    @SelectProvider(type = BaseSqlProvider.class, method = "queryByField")
    public List<V> queryByField(@Param("class") Class clazz, @Param("pageNo") int pageNo, @Param("pageSize") int pageSize, @Param("fieldName") String fieldName, @Param("fieldValue") Object fieldValue);

    @SelectProvider(type = BaseSqlProvider.class, method = "queryByFields")
    public List<V> queryByFields(@Param("class") Class clazz, @Param("pageNo") int pageNo, @Param("pageSize") int pageSize, @Param("fieldNames") String[] fieldNames, @Param("fieldValues") Object[] fieldValues);

    @SelectProvider(type = BaseSqlProvider.class, method = "queryByFieldsAndOrder")
    public List<V> queryByFieldsAndOrder(@Param("class") Class clazz, @Param("pageNo") int pageNo, @Param("pageSize") int pageSize, @Param("fieldNames") String[] fieldNames, @Param("fieldValues") Object[] fieldValue, @Param("orderName") String orderName, @Param("ascending") boolean ascending);

    @SelectProvider(type = BaseSqlProvider.class, method = "queryByFieldsAndOrders")
    public List<V> queryByFieldsAndOrders(@Param("class") Class clazz, @Param("pageNo") int pageNo, @Param("pageSize") int pageSize, @Param("fieldNames") String[] fieldNames, @Param("fieldValues") Object[] fieldValues, @Param("orderNames") String[] orderNames, @Param("ascendings") boolean[] ascendings);

    @SelectProvider(type = BaseSqlProvider.class, method = "queryByMap")
    public List<V>  queryByMap(@Param("class") Class clazz, @Param("params") Map<String, String> params, @Param("pageNo") int pageNo, @Param("pageSize") int pageSize);

    @SelectProvider(type = BaseSqlProvider.class, method = "queryByMapAndOrder")
    public List<V>  queryByMapAndOrder(@Param("class") Class clazz, @Param("params") Map<String, String> params, @Param("pageNo") int pageNo, @Param("pageSize") int pageSize, @Param("orderName") String orderName, @Param("ascending") boolean ascending);

    @SelectProvider(type = BaseSqlProvider.class, method = "queryByMapAndOrders")
    public List<V> queryByMapAndOrders(@Param("class") Class clazz, @Param("params") Map<String, String> params, @Param("pageNo") int pageNo, @Param("pageSize") int pageSize, @Param("orderNames") String[] orderNames, @Param("ascendings") Boolean[] ascendings);



    /**
     * count
     */

    @SelectProvider(type = BaseSqlProvider.class, method = "count")
    public int count(@Param("class") Class clazz);

    @SelectProvider(type = BaseSqlProvider.class, method = "countByField")
    public int countByField(@Param("class") Class clazz, @Param("fieldName") String fieldName, @Param("fieldValue") Object fieldValue);

    @SelectProvider(type = BaseSqlProvider.class, method = "countByFields")
    public int countByFields(@Param("class") Class clazz, @Param("fieldNames") String[] fieldNames, @Param("fieldValues") Object[] fieldValues);

    @SelectProvider(type = BaseSqlProvider.class, method = "countByMap")
    public int countByMap(@Param("class") Class clazz, @Param("params") Map<String, String> params);





}
