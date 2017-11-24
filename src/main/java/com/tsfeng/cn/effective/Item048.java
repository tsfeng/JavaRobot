package com.tsfeng.cn.effective;

import java.math.BigDecimal;
import java.text.Bidi;

/**
 * @author tsfeng
 * @version 创建时间 2017/11/22 20:47
 */
public class Item048 {
    public static void main(String[] args) {
//        float money = 1.00f;
//        int buyNum = 0;
//        for (float price = 0.1f; money >= price; price += 0.1) {
//            money -= price;
//            buyNum++;
//        }
//        System.out.println("buyNum=" + buyNum);
//        System.out.println("money=" + money);
        s();
    }

    public static void s(){
        BigDecimal money = new BigDecimal("1.00");
        final BigDecimal TEN_FEN = new BigDecimal("0.1");
        int buyNum = 0;
        for (BigDecimal price = TEN_FEN; money.compareTo(price) >= 0; price = price.add(TEN_FEN)) {
            money = money.subtract(price);
            buyNum++;
        }
        System.out.println("buyNum=" + buyNum);
        System.out.println("money=" + money);
    }

    public static void ss(){
        int money = 100;
        int buyNum = 0;
        for (int price = 10; money >= price; price += 10) {
            money -= price;
            buyNum++;
        }
        System.out.println("buyNum=" + buyNum);
        System.out.println("money=" + money);
    }
}
