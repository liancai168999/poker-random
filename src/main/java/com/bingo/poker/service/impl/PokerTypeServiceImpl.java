package com.bingo.poker.service.impl;

import com.bingo.core.toolkit.BeanValidator;
import com.bingo.poker.domain.PokerType;
import com.bingo.poker.domain.vo.PokerTypeVo;
import com.bingo.poker.mapper.IPokerTypeMapper;
import com.bingo.poker.param.PokerTypeParam;
import com.bingo.poker.service.IPokerTypeService;
import com.bingo.core.service.impl.BaseServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.beans.BeanUtils;
import com.bingo.core.toolkit.CheckUtils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * <p>
 * 设置初始化牌的类型-数值 服务实现类
 * </p>
 *
 * @author Doudou
 * @date 2018-12-03
 * @version 1.0
 */
@Service
public class PokerTypeServiceImpl extends BaseServiceImpl<IPokerTypeMapper, PokerType, PokerTypeVo> implements IPokerTypeService {


 @Transactional(rollbackFor = Exception.class)
    @Override
    public int createPokerType(PokerTypeParam param) {

        BeanValidator.check(param);
        logger.info("createIPokerTypeService start ...");

        PokerType pokerType = new PokerType();
        BeanUtils.copyProperties(param, pokerType);

        insert(pokerType);
        logger.info("createIPokerTypeService success, id:" + pokerType.getId());

        return pokerType.getId();
    }

 @Transactional(rollbackFor = Exception.class)
    @Override
    public int updatePokerType(PokerTypeParam param) {

        BeanValidator.check(param);
        CheckUtils.check(null != param.getId(), "id.error", param.getId());
        logger.info("updateIPokerTypeService start ...");

        Optional<PokerType> pokerType = getById(param.getId());

        if(!pokerType.isPresent()){
            return 0;
        }

        PokerType v = pokerType.get();
        BeanUtils.copyProperties(param, v);
        update(v);
        logger.info("updatePokerType success, id:" + v.getId());

        return param.getId();
    }

	@Override
    public void setClassType() {
        this.clazz = PokerType.class;
        this.clazzVo = PokerTypeVo.class;
    }

    public PokerTypeServiceImpl() {
        setClassType();
    }

    public PokerTypeVo selectById(Integer pokerTypeId) {
        Optional<PokerType> pokerType = getById(pokerTypeId);

        if(!pokerType.isPresent()){
            return null;
        }

        PokerTypeVo pokerTypeVo = new PokerTypeVo();
        PokerType v = pokerType.get();
        BeanUtils.copyProperties(v, pokerTypeVo);
        return pokerTypeVo;
    }
}
