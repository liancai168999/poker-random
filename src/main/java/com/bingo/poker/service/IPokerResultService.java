package com.bingo.poker.service;


import com.bingo.core.model.JsonResult;
import com.bingo.core.model.PageInfoRequest;
import com.bingo.core.model.PageInfoResult;
import com.bingo.core.toolkit.BeanValidator;
import com.bingo.core.toolkit.CheckUtils;
import com.bingo.poker.domain.PokerResult;
import com.bingo.poker.domain.vo.PokerResultVo;
import com.bingo.core.service.IBaseService;
import com.bingo.poker.param.PokerResultParam;

/**
 * <p>
 * 出牌结果表 服务类
 * </p>
 *
 * @author Doudou
 * @date 2018-12-03
 * @version 1.0
 */
public interface IPokerResultService extends IBaseService<PokerResult, PokerResultVo> {

    int createPokerResult(PokerResultParam pokerResultParam);

    int updatePokerResult(PokerResultParam pokerResultParam);

    JsonResult createAndVali(PokerResultParam param) throws Exception;

    JsonResult queryByPageInfoRequest2(PageInfoRequest request) throws Exception;

    boolean createAndValiTask() throws Exception;

    JsonResult decryptResults(PokerResultParam param);
}
