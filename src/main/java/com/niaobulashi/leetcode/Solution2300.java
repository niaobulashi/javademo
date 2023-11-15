package com.niaobulashi.leetcode;

public class Solution2300 {
    /**
     * https://leetcode.cn/problems/successful-pairs-of-spells-and-potions/
     * @param args
     */
    public static void main(String[] args) {
        int[] spells = {5,1,3};
        int[] potions = {1,2,3,4,5};
        long success  = 7;
        System.out.println(successfulPairs(spells, potions, success));
    }
    public static int[] successfulPairs(int[] spells, int[] potions, long success) {
        int[] pairs = new int[spells.length];
        for (int i = 0; i < spells.length; i++) {
            int num = 0;
            for (int j = 0; j < potions.length; j++) {
                if (spells[i] * potions[j] >= success) {
                    num++;
                }
            }
            pairs[i] = num;
        }
        return pairs;
    }
}
