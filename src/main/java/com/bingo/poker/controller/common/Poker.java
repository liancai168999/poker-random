package com.bingo.poker.controller.common;


/**
 * @author Doudou
 */
public class Poker {
    public String num;
    public String suit;
    Poker(String n,String s)
    {
        this.num=n;
        this.suit=s;
    }
    public String toString()
    {
        String ss=suit+":"+num+"  ";
        return ss;
    }
}
