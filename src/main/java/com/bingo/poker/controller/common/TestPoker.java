package com.bingo.poker.controller.common;

import java.util.Random;


/**
 * @author Doudou
 */
public class TestPoker {
    public static void main(String[] args){
        PokerLuo pokerLuo = new PokerLuo();
        //pokerLuo.initcard();
        pokerLuo.shufflecard();
        pokerLuo.dealcard("A");

        Random r1 = new Random();
        Random r2 = new Random();
    }


}
