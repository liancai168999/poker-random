package com.bingo.poker.controller.common.task;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;

/**
 * @Author: doudo
 * @Date: 2018/10/16 9:04
 */

@Component("BetCqsscTask")
@EnableScheduling
@SuppressWarnings("all")
public class PokerMakeTask {

    protected SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    private static final Logger logger = LoggerFactory.getLogger(PokerMakeTask.class);

    @Resource
    private Task task;

    @Scheduled(fixedRate = 1000 * 60)
    public void prizeHistory() throws Exception {
        task.makePoker();
        //Thread.currentThread().join();
    }




}
