package com.bingo.poker.mapper;

import com.bingo.core.mapper.IBaseMapper;
import com.bingo.poker.domain.PokerType;
import com.bingo.poker.domain.vo.PokerTypeVo;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 * 设置初始化牌的类型-数值 Mapper 接口
 * </p>
 *
 * @author 
 * @date 2018-12-04
 * @version 1.0
 */
 @Mapper
public interface IPokerTypeMapper extends IBaseMapper<PokerType, PokerTypeVo> {

}
