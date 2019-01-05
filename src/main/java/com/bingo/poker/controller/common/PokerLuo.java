package com.bingo.poker.controller.common;

import com.bingo.poker.domain.vo.PokerTypeVo;
import com.bingo.poker.mapper.IPokerTypeMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Random;


/**
 * @author Doudou
 */
@Component
public class PokerLuo {
    Poker pokers[];

    //初始化
    public void initcard(String num[], String suit[]){
        /*String num[] = {"A","2","3","4","5","6","7","8","9","10","J","Q","K"};
        String suit[] = {"方块","梅花","红桃","黑桃"};*/

        /*String num[] = {"01","02","03","04","05","06","07","08","09","10","11","12","13"}; // 牌-数字
        String suit[] = {"1","2","3","4"}; // 牌-花色*/
        /*PokerTypeVo pokerTypeVo = pokerTypeService.getVoById(PokerTypeVo.class, 1);
        String nums = pokerTypeVo.getNum();
        String suits = pokerTypeVo.getType();
        nums.replace("\\s", "");
        suits.replace("\\s", "");
        String num[] = nums.split(",");
        String suit[] = suits.split(","); // 牌-花色*/
        pokers = new Poker[52];
        for(int i = 0; i < 52; i++)
        {
            pokers[i] = new Poker(num[i%13], suit[i/13]);
        }
    }

    //洗牌-随机一
    public void shufflecard(){
        Random rd = new Random();
        for(int i = 0; i < 52; i++)
        {
            int j = rd.nextInt(52);//生成随机数
            Poker temp = pokers[i];//交换
            pokers[i] = pokers[j];
            pokers[j] = temp;
        }
    }


    //洗牌-随机二
    /*public void shufflecard2(){

    }*/

    //发牌
    public ArrayList<String> dealcard(String numString){
        ArrayList<String> list = new ArrayList<>();
        for(int i = 0; i < 52; i++)
        {
            if(i % 8 == 0){
            }
            list.add(numString + pokers[i].num + pokers[i].suit);
        }
        return list;
    }


}
