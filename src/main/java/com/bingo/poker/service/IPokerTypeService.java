package com.bingo.poker.service;


import com.bingo.core.toolkit.BeanValidator;
import com.bingo.core.toolkit.CheckUtils;
import com.bingo.poker.domain.PokerType;
import com.bingo.poker.domain.vo.PokerTypeVo;
import com.bingo.core.service.IBaseService;
import com.bingo.poker.param.PokerTypeParam;

/**
 * <p>
 * 设置初始化牌的类型-数值 服务类
 * </p>
 *
 * @author Doudou
 * @date 2018-12-03
 * @version 1.0
 */
public interface IPokerTypeService extends IBaseService<PokerType, PokerTypeVo> {

    int createPokerType(PokerTypeParam pokerTypeParam);

    int updatePokerType(PokerTypeParam pokerTypeParam);

    PokerTypeVo selectById(Integer pokerTypeId);
}
