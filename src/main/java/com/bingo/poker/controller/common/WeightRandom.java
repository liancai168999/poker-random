package com.bingo.poker.controller.common;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * @author Doudou
 */
public class WeightRandom {
    static List<WeightCategory> categorys = new ArrayList<WeightCategory>();
    private static Random random = new Random();

    public static void initData() {
        WeightCategory wc1 = new WeightCategory("A",80);
        WeightCategory wc2 = new WeightCategory("B",20);
        WeightCategory wc3 = new WeightCategory("C",10);
        WeightCategory wc4 = new WeightCategory("D",90);
        categorys.add(wc1);
        categorys.add(wc2);
        categorys.add(wc3);
        categorys.add(wc4);
    }

    public static void main(String[] args) {
        initData();
        Integer weightSum = 0;
        for (WeightCategory wc : categorys) {
            weightSum += wc.getWeight();
        }

        System.out.println("weight: " + weightSum);
        if (weightSum <= 0) {
            System.err.println("Error: weightSum=" + weightSum.toString());
            return;
        }
        Integer n = random.nextInt(weightSum); // n in [0, weightSum)
        Integer m = 0;
        for (WeightCategory wc : categorys) {
            if (m <= n && n < m + wc.getWeight()) {
                System.out.println("This Random Category is "+wc.getCategory());
                break;
            }
            m += wc.getWeight();
        }


    }
}
