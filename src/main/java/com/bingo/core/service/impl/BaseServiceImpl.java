package com.bingo.core.service.impl;

import com.bingo.core.mapper.IBaseMapper;
import com.bingo.core.model.PageInfoRequest;
import com.bingo.core.model.PageInfoResult;
import com.bingo.core.service.IBaseService;
import com.bingo.core.toolkit.Constants;
import com.bingo.core.toolkit.sql.SqlHelper;
import com.google.common.collect.Lists;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Optional;


public abstract class BaseServiceImpl<M extends IBaseMapper<T, V>, T extends Serializable, V extends Serializable> implements InitializingBean, IBaseService<T, V> {

    protected Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    protected M mapper;

    // 具体操作的实体类
    protected Class<T> clazz;

    // 具体操作的实体类Vo
    protected Class<V> clazzVo;

    @Override
    public abstract void setClassType();

    @Override
    public boolean insert(T t) {
        Assert.notNull(t, "insert entity must be not null");
        return SqlHelper.retBool(mapper.insert(t));
    }

    @Override
    public boolean insertByBatch(Collection<T> entitiesList) {
        Assert.notNull(entitiesList, "insert entities must be not null");
        return SqlHelper.retBool(mapper.insertByBatch(entitiesList));
    }

    @Override
    public boolean update(T t) {
        Assert.notNull(t, "update entity must be not null");
        return SqlHelper.retBool(mapper.update(t));
    }

    @Override
    public boolean updateByBatch(Collection<T> entityList) {
        Assert.notNull(entityList, "update entities must be not null");

        entityList.forEach(e -> mapper.update(e));

        return SqlHelper.retBool(1);
    }

    @Override
    public boolean deleteAll() {
        return SqlHelper.delBool(mapper.deleteAll(this.clazz));
    }

    @Override
    public boolean deleteById(Serializable id) {
        Assert.notNull(id, "id must be not null");
        return SqlHelper.delBool(mapper.deleteById(this.clazz, id));
    }

    @Override
    public boolean deleteByEntity(T t) {
        Assert.notNull(t, "delete entity must be not null");
        return SqlHelper.delBool(mapper.deleteByEntity(t));
    }

    @Override
    public boolean deleteByIds(Serializable[] ids) {
        Assert.notNull(ids, "ids must be not null");
        return SqlHelper.delBool(mapper.deleteByIds(this.clazz, ids));
    }

    @Override
    public boolean deleteByField(String fieldName, Object fieldValue) {
        Assert.notNull(fieldName, "fieldName must be not null");
        Assert.notNull(fieldValue, "fieldValue must be not null");
        return SqlHelper.delBool(mapper.deleteByField(this.clazz, fieldName, fieldValue));
    }

    @Override
    public boolean deleteByFields(String[] fieldNames, Object[] fieldValues) {
        Assert.notNull(fieldNames, "fieldNames must be not null");
        Assert.notNull(fieldValues, "fieldValues must be not null");
        return SqlHelper.delBool(mapper.deleteByFields(this.clazz, fieldNames, fieldValues));
    }

    @Override
    public boolean deleteByMap(Map<String, String> params) {
        Assert.notNull(params, "params must be not null");
        return SqlHelper.delBool(mapper.deleteByMap(this.clazz, params));
    }

    @Override
    public Optional<T> getById(Serializable id) {
        Assert.notNull(id, "id must be not null");
        return Optional.ofNullable(mapper.getById(this.clazz, id));
    }

    @Override
    public Optional<T> getByMap(Map<String, String> params) {
        Assert.notNull(params, "params must be not null");
        return Optional.ofNullable(mapper.getByMap(this.clazz, params));
    }

    @Override
    public Optional<T> getByField(String fieldName, Object fieldValue) {
        Assert.notNull(fieldName, "fieldName must be not null");
        Assert.notNull(fieldValue, "fieldValue must be not null");
        return Optional.ofNullable(mapper.getByField(this.clazz, fieldName, fieldValue));
    }

    @Override
    public Optional<T> getByFields(String[] fieldNames, Object[] fieldValues) {
        Assert.notNull(fieldNames, "fieldNames must be not null");
        Assert.notNull(fieldValues, "fieldValues must be not null");
        return Optional.ofNullable(mapper.getByFields(this.clazz, fieldNames, fieldValues));
    }

    @Override
    public Optional<V> getVoById(Serializable id) {
        Assert.notNull(id, "id must be not null");
        return Optional.ofNullable(mapper.getVoById(this.clazzVo, id));
    }

    @Override
    public Optional<V> getVoByMap(Map<String, String> params) {
        Assert.notNull(params, "params must be not null");
        return Optional.ofNullable(mapper.getVoByMap(this.clazzVo, params));
    }

    @Override
    public Optional<V> getVoByField(String fieldName, Object fieldValue) {
        Assert.notNull(fieldName, "fieldName must be not null");
        Assert.notNull(fieldValue, "fieldValue must be not null");
        return Optional.ofNullable(mapper.getVoByField(this.clazzVo, fieldName, fieldValue));
    }

    @Override
    public Optional<V> getVoByFields(String[] fieldNames, Object[] fieldValues) {
        Assert.notNull(fieldNames, "fieldNames must be not null");
        Assert.notNull(fieldValues, "fieldValues must be not null");
        return Optional.ofNullable(mapper.getVoByFields(this.clazzVo, fieldNames, fieldValues));
    }

    @Override
    public List<T> listAll() {
        return mapper.listAll(this.clazz);
    }

    @Transactional(readOnly = true)
    @Override
    public List<T> listAllAndOrder(String orderName, boolean ascending) {
        Assert.notNull(orderName, "orderName must be not null");
        return mapper.listAllAndOrder(this.clazz, orderName, ascending);
    }

    @Override
    public List<T> listByIds(Serializable[] ids) {
        Assert.notNull(ids, "ids must be not null");
        return mapper.listByIds(this.clazz, ids);
    }

    @Override
    public List<T> listByField(String fieldName, Object fieldValue) {
        Assert.notNull(fieldName, "fieldName must be not null");
        Assert.notNull(fieldValue, "fieldValue must be not null");
        return mapper.listByField(this.clazz, fieldName, fieldValue);
    }

    @Override
    public List<T> listByFields(String[] fieldNames, Object[] fieldValues) {
        Assert.notNull(fieldNames, "params must be not null");
        Assert.notNull(fieldValues, "fieldValues must be not null");
        return mapper.listByFields(this.clazz, fieldNames, fieldValues);
    }

    @Override
    public List<T> listByFieldAndOrder(String fieldName, Object fieldValue, String orderName, boolean ascending) {
        Assert.notNull(fieldName, "fieldName must be not null");
        Assert.notNull(fieldValue, "fieldValue must be not null");
        Assert.notNull(orderName, "orderName must be not null");
        return mapper.listByFieldAndOrder(this.clazz, fieldName, fieldValue, orderName, ascending);
    }

    @Override
    public List<T> listByFieldsAndOrder(String[] fieldNames, Object[] fieldValues, String orderName, boolean ascending) {
        Assert.notNull(fieldNames, "fieldNames must be not null");
        Assert.notNull(fieldValues, "fieldValues must be not null");
        Assert.notNull(orderName, "orderName must be not null");
        return mapper.listByFieldsAndOrder(this.clazz, fieldNames, fieldValues, orderName, ascending);
    }

    @Override
    public List<T> listByFieldsAndOrders(String[] fieldNames, Object[] fieldValues, String[] orderNames, Boolean[] ascendings) {
        Assert.notNull(fieldNames, "fieldNames must be not null");
        Assert.notNull(fieldValues, "fieldValues must be not null");
        Assert.notNull(orderNames, "orderNames must be not null");

        boolean[] ascendings2 = new boolean[ascendings.length];
        for (int i = 0, len = ascendings.length; i < len; i++) {
            ascendings2[i] = ascendings[i];
        }
        return mapper.listByFieldsAndOrders(this.clazz, fieldNames, fieldValues, orderNames, ascendings2);
    }

    @Override
    public List<T> listByMap(Map<String, String> params) {
        Assert.notNull(params, "params must be not null");
        return mapper.listByMap(this.clazz, params);
    }

    @Override
    public List<T> listByMapAndOrder(Map<String, String> params, String orderName, boolean ascending) {
        Assert.notNull(params, "params must be not null");
        Assert.notNull(orderName, "orderName must be not null");
        return mapper.listByMapAndOrder(this.clazz, params, orderName, ascending);
    }


    @Override
    public List<T> listByMapAndOrders(Map<String, String> params, String[] orderNames, boolean[] ascendings) {
        Assert.notNull(params, "params must be not null");
        Assert.notNull(orderNames, "orderNames must be not null");
        Assert.notNull(ascendings, "ascendings must be not null");
        return mapper.listByMapAndOrders(this.clazz, params, orderNames, ascendings);
    }


    @Override
    public int count() {
        return SqlHelper.retCount(mapper.count(this.clazz));
    }

    @Transactional(readOnly = true)
    @Override
    public int countByField(String fieldName, Object fieldValue) {
        Assert.notNull(fieldName, "fieldName must be not null");
        Assert.notNull(fieldValue, "fieldValue must be not null");
        return SqlHelper.retCount(mapper.countByField(this.clazz, fieldName, fieldValue));
    }


    @Override
    public int countByFields(String[] fieldNames, Object[] fieldValues) {
        Assert.notNull(fieldNames, "fieldNames must be not null");
        Assert.notNull(fieldValues, "fieldValues must be not null");
        return SqlHelper.retCount(mapper.countByFields(this.clazz, fieldNames, fieldValues));
    }

    @Override
    public int countByMap(Map<String, String> params) {
        Assert.notNull(params, "params must be not null");
        return SqlHelper.retCount(mapper.countByMap(this.clazz, params));
    }

    @Override
    public PageInfoResult<V> queryByPageInfoRequest(PageInfoRequest request) {
        Assert.notNull(request, "PageInfoRequest must be not null");
        
        PageInfoResult result = new PageInfoResult();
        
        int pageNo = request.getPageNo(), pageSize = request.getPageSize();
        pageNo = validPageNo(pageNo);
        pageSize = validPageSize(pageSize);

        Map<String, String> params = request.getParamsByFilter();
        String[] orders = request.getOrdersBySort();
        Boolean[] ascendings = request.getAscendingsBySort();

        int total = countByMap(params);
        result.setTotal(total);

        List<V> datas;
        if (total > 0) {
            datas = queryByMapAndOrders(params, pageNo, pageSize, orders, ascendings);
        } else {
            datas = Lists.newArrayList();
        }
        result.setList(datas);

        return result;
    }

    @Override
    public PageInfoResult<V> queryByPageInfoRequest(int pageNo, int pageSize, String orderName, boolean ascending) {
        Assert.notNull(orderName, "orderName must be not null");

        PageInfoResult result = new PageInfoResult();

        pageNo = validPageNo(pageNo);
        pageSize = validPageSize(pageSize);

        int total = count();
        result.setTotal(total);

        List<V> datas = null;
        if (total > 0) {
            datas = queryAllAndOrder(pageNo, pageSize, orderName, ascending);
        } else {
            datas = Lists.newArrayList();
        }
        result.setList(datas);

        return result;
    }

    @Override
    public PageInfoResult<V> queryByPageInfoRequest(int pageNo, int pageSize, String[] fieldNames, Object[] fieldValue, String orderName, boolean ascending) {

        Assert.notNull(fieldNames, "fieldNames must be not null");
        Assert.notNull(fieldValue, "fieldValue must be not null");
        Assert.notNull(orderName, "orderName must be not null");

        PageInfoResult result = new PageInfoResult();

        pageNo = validPageNo(pageNo);
        pageSize = validPageSize(pageSize);

        int total = countByFields(fieldNames, fieldValue);
        result.setTotal(total);

        List<V> datas;
        if (total > 0) {
            datas = queryByFieldsAndOrder(pageNo, pageSize, fieldNames, fieldValue, orderName, ascending);
        } else {
            datas = Lists.newArrayList();
        }
        result.setList(datas);

        return result;
    }

    private int validPageNo(int pageNo) {
        if (0 >= pageNo) {
            return 1;
        }
        return pageNo;
    }

    private int validPageSize(int pageSize) {
        if (0 >= pageSize) {
            pageSize = Constants.PAGE_SIZE;
        }
        if (pageSize > Constants.PAGE_SIZE_MAX) {
            pageSize = Constants.PAGE_SIZE_MAX;
        }
        return pageSize;
    }

    @Override
    public List<V> queryAll(int pageNo, int pageSize) {
        pageNo = validPageNo(pageNo);
        pageSize = validPageSize(pageSize);
        return mapper.queryAll(this.clazzVo, pageNo, pageSize);
    }

    @Override
    public List<V> queryAllAndOrder(int pageNo, int pageSize, String orderName, boolean ascending) {
        Assert.notNull(orderName, "orderName must be not null");
        pageNo = validPageNo(pageNo);
        pageSize = validPageSize(pageSize);
        return mapper.queryAllAndOrder(this.clazzVo, pageNo, pageSize, orderName, ascending);
    }

    @Override
    public List<V> queryByField(int pageNo, int pageSize, String fieldName, Object fieldValue) {
        Assert.notNull(fieldName, "fieldName must be not null");
        Assert.notNull(fieldValue, "fieldValue must be not null");

        pageNo = validPageNo(pageNo);
        pageSize = validPageSize(pageSize);
        return mapper.queryByField(this.clazzVo, pageNo, pageSize, fieldName, fieldValue);
    }

    @Override
    public List<V> queryByFields(int pageNo, int pageSize, String[] fieldNames, Object[] fieldValues) {
        Assert.notNull(fieldNames, "fieldNames must be not null");
        Assert.notNull(fieldValues, "fieldValues must be not null");
        pageNo = validPageNo(pageNo);
        pageSize = validPageSize(pageSize);
        return mapper.queryByFields(this.clazzVo, pageNo, pageSize, fieldNames, fieldValues);
    }

    @Override
    public List<V> queryByFieldsAndOrder(int pageNo, int pageSize, String[] fieldNames, Object[] fieldValue, String orderName, boolean ascending) {
        Assert.notNull(fieldNames, "fieldNames must be not null");
        Assert.notNull(fieldValue, "fieldValue must be not null");
        Assert.notNull(orderName, "orderName must be not null");
        pageNo = validPageNo(pageNo);
        pageSize = validPageSize(pageSize);
        return mapper.queryByFieldsAndOrder(this.clazzVo, pageNo, pageSize, fieldNames, fieldValue, orderName, ascending);
    }

    @Override
    public List<V> queryByFieldsAndOrders(int pageNo, int pageSize, String[] fieldNames, Object[] fieldValues, String[] orderNames, boolean[] ascendings) {
        Assert.notNull(fieldNames, "fieldNames must be not null");
        Assert.notNull(fieldValues, "fieldValues must be not null");
        Assert.notNull(orderNames, "orderNames must be not null");
        pageNo = validPageNo(pageNo);
        pageSize = validPageSize(pageSize);
        return mapper.queryByFieldsAndOrders(this.clazzVo, pageNo, pageSize, fieldNames, fieldValues, orderNames, ascendings);
    }

    @Override
    public List<V> queryByMap(Map<String, String> params, int pageNo, int pageSize) {
        Assert.notNull(params, "params must be not null");
        pageNo = validPageNo(pageNo);
        pageSize = validPageSize(pageSize);
        return mapper.queryByMap(this.clazzVo, params, pageNo, pageSize);
    }

    @Override
    public List<V> queryByMapAndOrder(Map<String, String> params, int pageNo, int pageSize, String orderName, boolean ascending) {
        Assert.notNull(params, "params must be not null");
        Assert.notNull(orderName, "orderName must be not null");
        pageNo = validPageNo(pageNo);
        pageSize = validPageSize(pageSize);
        return mapper.queryByMapAndOrder(this.clazzVo, params, pageNo, pageSize, orderName, ascending);
    }

    @Override
    public List<V> queryByMapAndOrders(Map<String, String> params, int pageNo, int pageSize, String[] orderNames, Boolean[] ascendings) {
        Assert.notNull(params, "params must be not null");
        Assert.notNull(orderNames, "orderNames must be not null");
        Assert.notNull(ascendings, "ascendings must be not null");
        pageNo = validPageNo(pageNo);
        pageSize = validPageSize(pageSize);
        return mapper.queryByMapAndOrders(this.clazzVo, params, pageNo, pageSize, orderNames, ascendings);
    }

    /*
     * 获取泛型的class
     *
     * @see
     * org.springframework.beans.factory.InitializingBean#afterPropertiesSet()
     */
    @Override
    public void afterPropertiesSet() throws Exception {
        Type type = this.getClass().getGenericSuperclass();
        ParameterizedType pt = (ParameterizedType) type;
        clazz = Class.class.cast(pt.getActualTypeArguments()[1]);
        Class<?> interfaces = Class.class.cast(pt.getActualTypeArguments()[0]);
        logger.info("\nthe {} service's entity is {}", getClass().getName(), clazz.getName());
        logger.info("\nthe {} service's mapper is {}", getClass().getName(), interfaces.getName());
    }


}
