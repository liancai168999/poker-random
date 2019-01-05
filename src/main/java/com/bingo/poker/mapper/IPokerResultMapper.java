package com.bingo.poker.mapper;

import com.bingo.core.mapper.IBaseMapper;
import com.bingo.poker.domain.PokerResult;
import com.bingo.poker.domain.vo.PokerResultVo;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 * 出牌结果表 Mapper 接口
 * </p>
 *
 * @author 
 * @date 2018-12-04
 * @version 1.0
 */
 @Mapper
public interface IPokerResultMapper extends IBaseMapper<PokerResult, PokerResultVo> {

    PokerResultVo getTop();
}
