package com.bingo.poker.controller;


import com.bingo.core.enums.ResultStatusEnum;
import com.bingo.poker.param.PokerTypeParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PathVariable;

import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

import com.bingo.poker.service.IPokerTypeService;
import com.bingo.poker.domain.vo.PokerTypeVo;

import org.springframework.util.Assert;
import com.bingo.core.model.JsonResult;
import com.bingo.core.model.PageInfoRequest;
import com.bingo.core.model.PageInfoResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * <p>
    * 设置初始化牌的类型-数值 前端控制器
    * </p>
 *
 * @author Doudou
 * @date 2018-12-03
 * @version 1.0
 */
@RestController
@RequestMapping("/poker/type")
public class PokerTypeController {

  private static final Logger logger = LoggerFactory.getLogger(PokerTypeController.class);

    @Autowired
    private IPokerTypeService pokerTypeService;

    @PostMapping(value = {"/query"})
    @ResponseBody
    public JsonResult list(@RequestBody PageInfoRequest request) {

    PageInfoResult <PokerTypeVo> result = pokerTypeService.queryByPageInfoRequest(request);

    return JsonResult.ok(result);
    }


    @ApiOperation(value = "创建设置初始化牌的类型-数值", notes = "新建设置初始化牌的类型-数值。")
    @ApiImplicitParams({
    @ApiImplicitParam(name = "id", value = "",dataType = "Integer", paramType = "record"),
    @ApiImplicitParam(name = "num", value = "牌面对应数字",dataType = "String", paramType = "record"),
    @ApiImplicitParam(name = "type", value = "牌面花色",dataType = "String", paramType = "record"),
    @ApiImplicitParam(name = "modifyTime", value = "修改时间",dataType = "LocalDateTime", paramType = "record"),
    })
    @PostMapping("/add")
    @ResponseBody
    public JsonResult addPokerType(@RequestBody PokerTypeParam param) throws Exception {

    int dataId = pokerTypeService.createPokerType(param);

    return JsonResult.build(ResultStatusEnum.OK.getCode(), "添加成功。", dataId);
    }


    @ApiOperation(value = "修改设置初始化牌的类型-数值", notes = "修改设置初始化牌的类型-数值。")
    @ApiImplicitParams({
    @ApiImplicitParam(name = "id", value = "",dataType = "Integer", paramType = "record"),
    @ApiImplicitParam(name = "num", value = "牌面对应数字",dataType = "String", paramType = "record"),
    @ApiImplicitParam(name = "type", value = "牌面花色",dataType = "String", paramType = "record"),
    @ApiImplicitParam(name = "modifyTime", value = "修改时间",dataType = "LocalDateTime", paramType = "record"),
    })
    @PostMapping(value = "/edit")
    @ResponseBody
    public JsonResult updatePokerType(@RequestBody PokerTypeParam param) throws Exception {

    int dataId = pokerTypeService.updatePokerType(param);

    return JsonResult.build(ResultStatusEnum.OK.getCode(),"修改成功。",dataId);
    }

    @ApiOperation(value = "删除设置初始化牌的类型-数值", notes = "删除设置初始化牌的类型-数值。")
    @ApiImplicitParams({
    @ApiImplicitParam(name = "id", value = "数据id",dataType = "String", paramType = "record")
    })
    @PostMapping(value = {"/delete/{id}"})
    @ResponseBody
    public JsonResult deletePokerType(@PathVariable("id") String id) throws Exception {
    Assert.notNull(id, "请提供数据Id");

    boolean flag = pokerTypeService.deleteById(id);

    return flag ? JsonResult.ok("删除成功。") : JsonResult.errorMsg("删除失败。");
    }






    }
