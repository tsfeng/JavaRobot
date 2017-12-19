package com.tsfeng.cn.core.base;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author tsfeng
 * @version 创建时间 2017/12/18 16:55
 */
public class HashCodeDemo {

    /**
     * 计算hashCode
     * @param str 入参字符串
     * @param multiplier 乘数
     * @return
     */
    public static Integer hashCode(String str, Integer multiplier) {
        int hash = 0;
        for (int i = 0; i < str.length(); i++) {
            hash = multiplier * hash + str.charAt(i);
        }
        return hash;
    }

    /**
     * 计算 hash code 冲突率，顺便分析一下 hash code 最大值和最小值，并输出
     * @param multiplier 乘数
     * @param hashs 所有计算出的hashCode
     */
    public static void calculateConflictRate(Integer multiplier, List<Integer> hashs) {
        Comparator<Integer> cp = (x, y) -> x > y ? 1 : (x < y ? -1 : 0);
        int maxHash = hashs.stream().max(cp).get();
        int minHash = hashs.stream().min(cp).get();

        // 计算冲突数及冲突率
        int uniqueHashNum = (int) hashs.stream().distinct().count();
        int conflictNum = hashs.size() - uniqueHashNum;
        double conflictRate = (conflictNum * 1.0) / hashs.size();
        System.out.print("hashNum = " + hashs.size() + "  ");
        System.out.println(String.format("multiplier=%4d, minHash=%11d, maxHash=%10d, conflictNum=%6d, conflictRate=%.4f%%",
                multiplier, minHash, maxHash, conflictNum, conflictRate * 100));
    }


    public static void main(String[] args) throws IOException {
        System.out.println(Integer.MAX_VALUE);
        // Java8用流的方式读文件，更加高效
        List<String> wordList = Files.lines(Paths.get("F:\\linux.words"), StandardCharsets.UTF_8)
                .filter(word -> !word.contains(","))
                .filter(word -> !word.contains("-"))
                .collect(Collectors.toList());
        List<Integer> list = Arrays.asList(2, 3, 5, 7, 11, 13, 17, 19, 29, 31, 32, 33, 36, 37, 41, 47, 97, 101, 199);
        System.out.println("wordNum：" + wordList.size());
        System.out.println("unique wordNum：" + wordList.stream().distinct().count());
        for(Integer multiplier : list) {
            List<Integer> hashList = new ArrayList<>();
            for (String str : wordList) {
                int hash = hashCode(str, multiplier);
                hashList.add(hash);
            }
            calculateConflictRate(multiplier, hashList);
        }

    }
}
