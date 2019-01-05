package com.bingo.user;

import com.bingo.core.enums.GenderEnum;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Random;

//@RunWith(SpringRunner.class)
//@SpringBootTest
public class BingoDelCardApplicationTests {

    @Test
    public void contextLoads() {
        Random random = new Random();
        for (int i = 0; i < 100; i++) {
            int i1 = random.nextInt(20);
            //System.out.println(i1);
        }

    }

    @Test
    public void test() {
            //Original String
            /*String string = "[B@4f00e791";

            //Convert to byte[]
            byte[] bytes = string.getBytes();

            //Convert back to String
            String s = new String(bytes);

            //Check converted string against original String
            System.out.println("Decoded String : " + s);*/

    }

}
