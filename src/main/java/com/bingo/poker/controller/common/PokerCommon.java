package com.bingo.poker.controller.common;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.LinkedList;

/**
 * @author Doudou
 */
@Component
public class PokerCommon {
    private static final Logger logger = LoggerFactory.getLogger(PokerCommon.class);

    /*public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("几副牌");
        Integer num = scanner.nextInt();
        pokerInput(getChar(num));

    }*/

    public static ArrayList<Object> pokerInput(LinkedList<String> strings, String num[], String suit[]) {
        ArrayList<Object> pokerAll = new ArrayList<>();
        ArrayList<String> pokeres = new ArrayList<>();
        // 调用--
        for (int i = 0; i < strings.size(); i++) {
            PokerLuo pokerLuo = new PokerLuo();
            pokerLuo.initcard(num, suit);
            pokerLuo.shufflecard();
            pokeres = pokerLuo.dealcard(strings.get(i));
            pokerAll.add(pokeres);
        }
        logger.info("出牌结果：：： | " + pokerAll.toString());
        return pokerAll;
    }

    public static LinkedList<String> getChar(Integer num) {
        LinkedList<String> chars = new LinkedList<>();
        char[] abc = new char[num];
        for(int i = 0; i < abc.length; i++) {
            abc[i] = (char)('a' + i);
        }
        for(int i = 0; i < abc.length; i++) {
            chars.add(String.valueOf(abc[i]).toUpperCase());
        }
        return chars;
    }
}
