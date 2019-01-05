package com.bingo.poker.controller;


import com.bingo.core.enums.ResultStatusEnum;
import com.bingo.poker.param.PokerResultParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

import com.bingo.poker.service.IPokerResultService;

import org.springframework.util.Assert;
import com.bingo.core.model.JsonResult;
import com.bingo.core.model.PageInfoRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * <p>
    * 出牌结果表 前端控制器
    * </p>
 *
 * @author Doudou
 * @date 2018-12-03
 * @version 1.0
 */
@RestController
@RequestMapping("/poker/result")
public class PokerResultController {

  private static final Logger logger = LoggerFactory.getLogger(PokerResultController.class);

    @Autowired
    private IPokerResultService pokerResultService;

    /**
     * 测试页面
     * @return
     */
    @RequestMapping(value = "/test", method = RequestMethod.GET)
    public String getTest() {
      Integer statusCode = Integer.valueOf(200);
      String statusMsg = "";
      Object data = null;
      return "/test";
    }

  /**
   * 查询
   * @param request
   * @return
   */
    @PostMapping(value = {"/query"})
    @ResponseBody
    public JsonResult list(@RequestBody PageInfoRequest request) throws Exception{
      //PageInfoResult <PokerResultVo> result = pokerResultService.queryByPageInfoRequest(request);
      return pokerResultService.queryByPageInfoRequest2(request);
    }


  /**
   * 添加(根据牌幅数量生成随机牌-加密返回)
   * @param param
   * @return
   * @throws Exception
   */
    @PostMapping("/add")
    @ResponseBody
    public JsonResult addPokerResults(@RequestBody PokerResultParam param) throws Exception {
      JsonResult jsonObject = pokerResultService.createAndVali(param);
      return jsonObject;
    }

  /**
   * 根据公钥返回解密数据
   * @param param
   * @return
   * @throws Exception
   */
    @PostMapping("/decrypt")
    @ResponseBody
    public JsonResult decryptPokerResults(@RequestBody PokerResultParam param) throws Exception {
      JsonResult jsonObject = pokerResultService.decryptResults(param);
      return jsonObject;
    }


    public JsonResult addPokerResult(@RequestBody PokerResultParam param) throws Exception {
      int dataId = pokerResultService.createPokerResult(param);
      return JsonResult.build(ResultStatusEnum.OK.getCode(), "添加成功。", dataId);
    }


    @ApiOperation(value = "修改出牌结果表", notes = "修改出牌结果表。")
    @ApiImplicitParams({
    @ApiImplicitParam(name = "id", value = "",dataType = "Integer", paramType = "record"),
    @ApiImplicitParam(name = "pokerResult", value = "发牌结果集",dataType = "String", paramType = "record"),
    @ApiImplicitParam(name = "pokerNum", value = "出牌时的牌幅数量",dataType = "Integer", paramType = "record"),
    @ApiImplicitParam(name = "modifyTime", value = "修改时间",dataType = "LocalDateTime", paramType = "record"),
    @ApiImplicitParam(name = "pokerSecret", value = "公布明文结果解密时所需密钥",dataType = "String", paramType = "record"),
    })
    @PostMapping(value = "/edit")
    @ResponseBody
    public JsonResult updatePokerResult(@RequestBody PokerResultParam param) throws Exception {

    int dataId = pokerResultService.updatePokerResult(param);

    return JsonResult.build(ResultStatusEnum.OK.getCode(),"修改成功。",dataId);
    }

    @ApiOperation(value = "删除出牌结果表", notes = "删除出牌结果表。")
    @ApiImplicitParams({
    @ApiImplicitParam(name = "id", value = "数据id",dataType = "String", paramType = "record")
    })
    @PostMapping(value = {"/delete/{id}"})
    @ResponseBody
    public JsonResult deletePokerResult(@PathVariable("id") String id) throws Exception {
    Assert.notNull(id, "请提供数据Id");

    boolean flag = pokerResultService.deleteById(id);

    return flag ? JsonResult.ok("删除成功。") : JsonResult.errorMsg("删除失败。");
    }







    }
