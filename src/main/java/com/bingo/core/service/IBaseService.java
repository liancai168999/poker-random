package com.bingo.core.service;

import com.bingo.core.model.PageInfoRequest;
import com.bingo.core.model.PageInfoResult;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface IBaseService<T,V> {

    abstract void setClassType();

    /**
     * insert
     */
    boolean insert(T t);

    boolean insertByBatch(Collection<T> entitiesList);


    /**
     * update
     */
    boolean update(T t);

    boolean updateByBatch(Collection<T> entityList);
    

    /**
     * 删除
     */

    boolean deleteAll();

    boolean deleteById(Serializable id);

    boolean deleteByEntity(T t);

    boolean deleteByIds(Serializable[] ids);

    boolean deleteByField(String fieldName, Object fieldValue);

    boolean deleteByFields(String[] fieldNames, Object[] fieldValues);

    boolean deleteByMap(Map<String, String> params);


    /**
     * get
     */

    Optional<T> getById(Serializable id);

    Optional<T> getByMap(Map<String, String> params);

    Optional<T> getByField(String fieldName, Object fieldValue);

    Optional<T> getByFields(String[] fieldNames, Object[] fieldValues);

    /**
     * get vo
     */

    Optional<V> getVoById(Serializable id);

    Optional<V> getVoByMap(Map<String, String> params);

    Optional<V> getVoByField(String fieldName, Object fieldValue);

    Optional<V> getVoByFields(String[] fieldNames, Object[] fieldValues);


    /**
     * list
     */
    public List<T> listAll();

    public List<T> listAllAndOrder(String orderName, boolean ascending)  ;

    public List<T> listByIds(Serializable[] ids);

    public List<T> listByField(String fieldName, Object fieldValue);

    public List<T> listByFields(String[] fieldNames, Object[] fieldValues);

    public List<T> listByFieldAndOrder(String fieldName, Object fieldValue, String orderName, boolean ascending);

    public List<T> listByFieldsAndOrder(String[] fieldNames, Object[] fieldValues, String orderName, boolean ascending);

    public List<T> listByFieldsAndOrders(String[] fieldNames, Object[] fieldValues, String[] orderNames, Boolean[] ascendings);

    public List<T> listByMap(Map<String, String> params);

    public List<T> listByMapAndOrder(Map<String, String> params, String orderName, boolean ascending);

    public List<T> listByMapAndOrders(Map<String, String> params, String[] orderNames, boolean[] ascendings);


    /**
     * count
     */

    public int count();

    public int countByField(String fieldName, Object fieldValue);

    public int countByFields(String[] fieldNames, Object[] fieldValues);

    public int countByMap(Map<String, String> params);


    /**
     * page
     */

    public PageInfoResult<V> queryByPageInfoRequest(PageInfoRequest request);

    public PageInfoResult<V> queryByPageInfoRequest(int pageNo, int pageSize, String orderName, boolean ascending);

    public PageInfoResult<V> queryByPageInfoRequest(int pageNo, int pageSize, String[] fieldNames, Object[] fieldValue, String orderName, boolean ascending);


    /**
     * query
     */

    public List<V> queryAll(int pageNo, int pageSize);

    public List<V> queryAllAndOrder(int pageNo, int pageSize, String orderName, boolean ascending)  ;

    public List<V> queryByField(int pageNo, int pageSize, String fieldName, Object fieldValue);

    public List<V> queryByFields(int pageNo, int pageSize, String[] fieldNames, Object[] fieldValues);

    public List<V> queryByFieldsAndOrder(int pageNo, int pageSize, String[] fieldNames, Object[] fieldValue, String orderName, boolean ascending);

    public List<V> queryByFieldsAndOrders(int pageNo, int pageSize, String[] fieldNames, Object[] fieldValues, String[] orderNames, boolean[] ascendings);

    public List<V>  queryByMap(Map<String, String> params, int pageNo, int pageSize);

    public List<V>  queryByMapAndOrder(Map<String, String> params, int pageNo, int pageSize, String orderName, boolean ascending);

    public List<V> queryByMapAndOrders(Map<String, String> params, int pageNo, int pageSize, String[] orderNames, Boolean[] ascendings);



}
