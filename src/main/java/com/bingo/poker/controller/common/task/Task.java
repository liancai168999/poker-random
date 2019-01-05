package com.bingo.poker.controller.common.task;

import com.bingo.core.model.JsonResult;
import com.bingo.poker.param.PokerResultParam;
import com.bingo.poker.service.IPokerResultService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @Author: doudo
 * @Date: 2018/11/3 16:53
 */

@SuppressWarnings("all")
@Component
public class Task {

    private static final Logger logger = LoggerFactory.getLogger(Task.class);

    public static Random random = new Random();

    private SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");


    @Autowired
    private IPokerResultService pokerResultService;

    @Async("taskExecutor")
    public void makePoker() throws Exception {
        logger.info("定时线程池任务 | 定时生成扑克牌（8幅） | 开始");
        PokerResultParam param = new PokerResultParam();
        param.setPokerNum(8);
        JsonResult jsonObject = pokerResultService.createAndVali(param);
        long start = System.currentTimeMillis();
        Thread.sleep(random.nextInt(10000));
        long end = System.currentTimeMillis();
        logger.info("定时线程池任务 | 定时生成扑克牌（8幅） | 完 | 耗时：" + (end - start) + "毫秒");
    }



}
