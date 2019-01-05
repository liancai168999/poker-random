package com.bingo.poker.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.bingo.core.enums.ResultStatusEnum;
import com.bingo.core.model.JsonResult;
import com.bingo.core.model.PageInfoRequest;
import com.bingo.core.model.PageInfoResult;
import com.bingo.core.toolkit.BeanValidator;
import com.bingo.encrypt.util.RSAKeyPairFactory;
import com.bingo.encrypt.util.RSAKeyPairFactoryV2;
import com.bingo.encrypt.util.SecurityBase64;
import com.bingo.poker.controller.common.PokerCommon;
import com.bingo.poker.domain.PokerResult;
import com.bingo.poker.domain.vo.PokerResultFont;
import com.bingo.poker.domain.vo.PokerResultVo;
import com.bingo.poker.domain.vo.PokerTypeVo;
import com.bingo.poker.mapper.IPokerResultMapper;
import com.bingo.poker.param.PokerResultParam;
import com.bingo.poker.service.IPokerResultService;
import com.bingo.core.service.impl.BaseServiceImpl;
import com.bingo.poker.service.IPokerTypeService;
import com.google.common.collect.Lists;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.beans.BeanUtils;
import com.bingo.core.toolkit.CheckUtils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

import java.text.SimpleDateFormat;
import java.time.*;
import java.util.*;

/**
 * <p>
 * 出牌结果表 服务实现类
 * </p>
 *
 * @author Doudou
 * @date 2018-12-03
 * @version 1.0
 */
@Service
public class PokerResultServiceImpl extends BaseServiceImpl<IPokerResultMapper, PokerResult, PokerResultVo> implements IPokerResultService {

    @Autowired
    private IPokerTypeService pokerTypeService;

    @Autowired
    private IPokerResultMapper pokerResultMapper;

    @Autowired
    private PokerCommon pokerCommon;

 @Transactional(rollbackFor = Exception.class)
    @Override
    public int createPokerResult(PokerResultParam param) {

        BeanValidator.check(param);
        logger.info("createIPokerResultService start ...");

        PokerResult pokerResult = new PokerResult();
        BeanUtils.copyProperties(param, pokerResult);

        insert(pokerResult);
        logger.info("createIPokerResultService success, id:" + pokerResult.getId());

        return pokerResult.getId();
    }

 @Transactional(rollbackFor = Exception.class)
    @Override
    public int updatePokerResult(PokerResultParam param) {

        BeanValidator.check(param);
        CheckUtils.check(null != param.getId(), "id.error", param.getId());
        logger.info("updateIPokerResultService start ...");

        Optional<PokerResult> pokerResult = getById(param.getId());

        if(!pokerResult.isPresent()){
            return 0;
        }

        PokerResult v = pokerResult.get();
        BeanUtils.copyProperties(param, v);
        update(v);
        logger.info("updatePokerResult success, id:" + v.getId());

        return param.getId();
    }

    /*@Override
    public JsonResult createAndVali(PokerResultParam param) throws Exception{

        Integer num = param.getPokerNum();
        if (StringUtils.isEmpty(num) || num.equals("")) {
            return JsonResult.build(ResultStatusEnum.PARAMS_ERROR.getCode(), "pokerNum参数不能为空。", null);
        }

        if (num < 1 || num > 26) {
            return JsonResult.build(ResultStatusEnum.PARAMS_ERROR.getCode(), "牌幅数量需在1~26之间。", null);
        }

        PokerTypeVo pokerTypeVo = pokerTypeService.selectById(1);
        String nums = pokerTypeVo.getNum();
        String suits = pokerTypeVo.getType();
        nums.replace("\\s", "");
        suits.replace("\\s", "");
        String numes[] = nums.split(",");
        String suit[] = suits.split(","); // 牌-花色

        // 生成rsakey
        Map<String, Object> initKey = RSAKeyPairFactory.initKey();
        // 私钥加密数据
        String privateKey = RSAKeyPairFactory.getPrivateKey(initKey);
        // 公钥解密数据, 公钥存入库作为解密时传入的secret
        String publicKey = RSAKeyPairFactory.getPublicKey(initKey);
        // 获取牌面数据
        ArrayList<Object> datas = pokerCommon.pokerInput(pokerCommon.getChar(num), numes, suit);
        // 处理数据集
        String dataString = datas.toString();

        // 加密返回数据
        String encrypt = SecurityBase64.toBase64(RSAKeyPairFactory.encryptByPrivateKey(datas.toString().getBytes(), privateKey));
        // 解密数据
        byte[] decrypt = RSAKeyPairFactory.decryptByPublicKey(SecurityBase64.fromBase64(encrypt), publicKey);

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("encrypt", encrypt);
        jsonObject.put("decrypt", new String(decrypt));

        System.out.println("加密" + encrypt);
        System.out.println("解密" + new String(decrypt));

        // 入库操作
        param.setPokerResult(datas.toString().getBytes());
        param.setPokerPublicKey(publicKey);
        param.setPokerPrivateKey(privateKey);
        param.setPokerPrivateResult(encrypt.getBytes());

        int dataId = this.createPokerResult(param);
        return JsonResult.build(ResultStatusEnum.OK.getCode(), "添加成功。", jsonObject);
    }*/

    @Override
    public JsonResult createAndVali(PokerResultParam param) throws Exception{

        Integer num = param.getPokerNum();
        if (StringUtils.isEmpty(num) || num.equals("")) {
            return JsonResult.build(ResultStatusEnum.PARAMS_ERROR.getCode(), "pokerNum参数不能为空。", null);
        }

        if (num < 1 || num > 26) {
            return JsonResult.build(ResultStatusEnum.PARAMS_ERROR.getCode(), "牌幅数量需在1~26之间。", null);
        }

        PokerTypeVo pokerTypeVo = pokerTypeService.selectById(1);
        String nums = pokerTypeVo.getNum();
        String suits = pokerTypeVo.getType();
        nums.replace("\\s", "");
        suits.replace("\\s", "");
        String numes[] = nums.split(",");
        String suit[] = suits.split(","); // 牌-花色

        // 生成rsakey
        Map<String, Object> initKey = RSAKeyPairFactory.initKey();
        // 私钥加密数据
        String privateKey = RSAKeyPairFactory.getPrivateKey(initKey);
        // 公钥解密数据, 公钥存入库作为解密时传入的secret
        String publicKey = RSAKeyPairFactory.getPublicKey(initKey);
        // 获取牌面数据
        ArrayList<Object> datas = pokerCommon.pokerInput(pokerCommon.getChar(num), numes, suit);
        // 处理数据集


        JSONObject jsonObject = new JSONObject();
        // 加密返回数据
        //String encrypt = SecurityBase64.toBase64(RSAKeyPairFactory.encryptByPrivateKey(datas.toString().getBytes(), privateKey));
        String encrypt = RSAKeyPairFactoryV2.encryptByPrivateKey(datas.toString().getBytes(), privateKey).toString();
        // 解密数据
        //byte[] decrypt = RSAKeyPairFactory.decryptByPublicKey(encrypt.getBytes(), publicKey);

        jsonObject.put("encrypt", encrypt);
        //jsonObject.put("decrypt", new String(decrypt));

        //System.out.println("加密" + new String(encrypt));
        //System.out.println("解密" + new String(decrypt));

        // 入库操作
        param.setPokerResult(datas.toString().getBytes());
        param.setPokerPublicKey(publicKey);
        param.setPokerPrivateKey(privateKey);
        param.setPokerPrivateResult(encrypt.getBytes());

        int dataId = this.createPokerResult(param);
        return JsonResult.build(ResultStatusEnum.OK.getCode(), "添加成功。", jsonObject);
    }

    public boolean createAndValiTask() throws Exception{
        PokerResultVo pokerResultVo = pokerResultMapper.getTop();
        if (validateTime(pokerResultVo)) {
            return true;
        } else {
            return false;
        }
    }


    public static List<String> getStrList(String inputString, int length,
                                          int size) {
        List<String> list = new ArrayList<String>();
        for (int index = 0; index < size; index++) {
            String childStr = substring(inputString, index * length,
                    (index + 1) * length);
            list.add(childStr);
        }
        return list;
    }

    public static String substring(String str, int f, int t) {
        if (f > str.length())
            return null;
        if (t > str.length()) {
            return str.substring(f, str.length());
        } else {
            return str.substring(f, t);
        }
    }

    @Override
    public JsonResult queryByPageInfoRequest2(PageInfoRequest request) throws Exception{
        PageInfoResult <PokerResultVo> result = this.queryByPageInfoRequest(request);
        PageInfoResult <PokerResultFont> result2 = new PageInfoResult<>();
        List<PokerResultVo> pokerResultVoList = result.getList();

        List<PokerResultFont> pokerResultFontList = new LinkedList<>();
        if (null != pokerResultVoList && pokerResultVoList.size() > 0) {
            for (int i = 0; i < pokerResultVoList.size(); i++) {
                PokerResultFont pokerResultFont = new PokerResultFont();
                pokerResultFont.setCreateTime(pokerResultVoList.get(i).getCreateTime());
                pokerResultFont.setId(pokerResultVoList.get(i).getId());
                pokerResultFont.setPokerPrivateResult(new String(pokerResultVoList.get(i).getPokerPrivateResult()));

                if (validateTime(pokerResultVoList.get(i))) {
                    pokerResultFont.setPokerPublicKey(pokerResultVoList.get(i).getPokerPublicKey());
                    pokerResultFont.setPublishStatus("published");
                } else {
                    String time = getPublishRestTime(pokerResultVoList.get(i).getCreateTime());
                    pokerResultFont.setPokerPublicKey(time);
                    pokerResultFont.setPublishStatus("unPublish");
                }


                pokerResultFont.setTbStatus(pokerResultVoList.get(i).getTbStatus());
                pokerResultFont.setPokerNum(pokerResultVoList.get(i).getPokerNum());
                pokerResultFont.setUrl("http://tool.chacuo.net/cryptrsapubkey");
                pokerResultFontList.add(pokerResultFont);
            }

            // 排序
            Collections.sort(pokerResultFontList, new Comparator<PokerResultFont>() {
                @Override
                public int compare(PokerResultFont h1, PokerResultFont h2) {
                    return h2.getId() - h1.getId();
                }
            });
        }
        result2.setList(pokerResultFontList);
        result2.setTotal(result.getTotal());
        return JsonResult.build(ResultStatusEnum.OK.getCode(), "查询成功。", result2);
    }

    public boolean validateTime (PokerResultVo pokerResultVo) throws Exception{
        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        long timeCreate = sdf.parse(pokerResultVo.getCreateTime()).getTime();
        long timeLose = timeCreate + 2*60*60*1000;
        long timeNow = System.currentTimeMillis();
        if (timeNow >= timeLose) {
            logger.info("id为" + pokerResultVo.getId() + "的牌幅数据创建已超过两小时，可开放公钥");
            return true;
        } else {
            logger.info("id为" + pokerResultVo.getId() + "的牌幅数据创建未超过两小时，可开放公钥");
            return false;
        }
    }

    public String getPublishRestTime(String createTime) throws Exception{


        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        long timeCreate = sdf.parse(createTime).getTime();
        long timeLose = timeCreate + 2*60*60*1000;
        long timeNow = System.currentTimeMillis();


        Instant instantLose = new Date(timeLose).toInstant();
        Instant instantNow = new Date(timeNow).toInstant();
        ZoneId zoneIdLose = ZoneId.systemDefault();
        ZoneId zoneIdNow = ZoneId.systemDefault();

        LocalDateTime localDateLose = LocalDateTime.ofInstant(instantLose, zoneIdLose);
        LocalDateTime localDateNow = LocalDateTime.ofInstant(instantNow, zoneIdNow);
        //LocalDate localDateLose = instantRest.atZone(zoneIdRest).toLocalDate();
        Duration duration = Duration.between(localDateNow, localDateLose);
        duration.toMillis();
        return String.valueOf(duration.toMillis());


    }

    @Override
    public JsonResult decryptResults(PokerResultParam param) {
     if (null == param || null == param.getPokerPublicKey()) {
         return JsonResult.build(ResultStatusEnum.PARAMS_ERROR.getCode(), "请检查传入参数是否有误。", null);
     }
     JSONObject jsonObject = new JSONObject();
        List<PokerResult> pokerResults = this.listAll();
        if (null != pokerResults && pokerResults.size() > 0) {
            for (int i = 0; i < pokerResults.size(); i++) {
                String pokerPublicKey = param.getPokerPublicKey();
                pokerPublicKey = pokerPublicKey.replace("\t", "");
                if (pokerPublicKey.equals(pokerResults.get(i).getPokerPublicKey())) {
                    jsonObject.put("pokerResults", new String(pokerResults.get(i).getPokerResult()));
                    return JsonResult.build(ResultStatusEnum.OK.getCode(), "获取成功。", jsonObject);
                }
            }
        }
        return JsonResult.build(ResultStatusEnum.PARAMS_ERROR.getCode(), "请检查传入公钥是否有误。", jsonObject);
    }

    @Override
    public void setClassType() {
        this.clazz = PokerResult.class;
        this.clazzVo = PokerResultVo.class;
    }

    public PokerResultServiceImpl() {
        setClassType();
    }
    
}
