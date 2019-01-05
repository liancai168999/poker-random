package com.bingo.poker.controller;


import com.bingo.poker.controller.common.PokerCommon;
import com.bingo.poker.domain.vo.PokerResultVo;
import com.bingo.poker.service.IPokerResultService;
import com.bingo.poker.service.IPokerTypeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;


/**
 * <p>
    * 出牌结果表 前端控制器
    * </p>
 *
 * @author Doudou
 * @date 2018-12-03
 * @version 1.0
 */

@Controller
@RequestMapping("")
public class MainController {

  private static final Logger logger = LoggerFactory.getLogger(MainController.class);

    @Autowired
    private IPokerResultService pokerResultService;

    @Autowired
    private IPokerTypeService pokerTypeService;

    @Autowired
    private PokerCommon pokerCommon;

    /**
     * 测试页面
     * @return
     */
    @RequestMapping(value = "", method = RequestMethod.GET)
    public String getTest() {
      Integer statusCode = Integer.valueOf(200);
      String statusMsg = "";
      Object data = null;
      return "/index";
    }

    @RequestMapping(value = "/record", method = RequestMethod.GET)
    public String getRecord() {
      Integer statusCode = Integer.valueOf(200);
      String statusMsg = "";
      Object data = null;
      return "/record";
    }



}
