package com.niaobulashi.leetcode;

import java.util.HashSet;
import java.util.Set;

/**
 * 给定一个字符串，请你找出其中不含有重复字符的 最长子串 的长度。
 * <p>
 *  
 * <p>
 * 示例 1:
 * <p>
 * 输入: s = "abcabcbb"
 * 输出: 3
 * 解释: 因为无重复字符的最长子串是 "abc"，所以其长度为 3。
 * 示例 2:
 * <p>
 * 输入: s = "bbbbb"
 * 输出: 1
 * 解释: 因为无重复字符的最长子串是 "b"，所以其长度为 1。
 * 示例 3:
 * <p>
 * 输入: s = "pwwkew"
 * 输出: 3
 * 解释: 因为无重复字符的最长子串是 "wke"，所以其长度为 3。
 *      请注意，你的答案必须是 子串 的长度，"pwke" 是一个子序列，不是子串。
 * 示例 4:
 * <p>
 * 输入: s = ""
 * 输出: 0
 *  
 * <p>
 * 提示：
 * <p>
 * 0 <= s.length <= 5 * 104
 * s 由英文字母、数字、符号和空格组成
 * <p>
 * 来源：力扣（LeetCode）
 * 链接：https://leetcode-cn.com/problems/longest-substring-without-repeating-characters
 * 著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。
 */
public class LengthOfLongestSubstring {
    
    /**
     * 傻批暴力解法，无脑，但是会判定超时。。。
     * @param s
     * @return
     */
    public static int lengthOfLongestSubstring(String s) {
        int maxCount = 0;
        Set<String> set = new HashSet<>();
        Set<Integer> setCount = new HashSet<>();
        
        if (s != null && s.length() > 0) {
            for (int i = 0; i < s.length() - 1; i++) {
                for (int j = s.length(); j > i; j--) {
                    set.add(s.substring(i, j));
                }
            }
            for (String str : set) {
                if (!str.matches(".*(.)(.*\\1).*")) {
                    setCount.add(str.length());
                }
            }
            int[] array = new int[setCount.size()];
            int m = 0;
            for (int b : setCount) {
                array[m] = b;
                m++;
            }
            for (int k = 0; k < array.length - 1; k++) {
                for (int d = 0; d < array.length - k - 1; d++) {
                    if (array[d] < array[d + 1]) {
                        int t = array[d];
                        array[d] = array[d + 1];
                        array[d + 1] = t;
                    }
                }
            }
            if (s.trim().isEmpty()) {
                maxCount = 1;
            } else if (s.length() == 1) {
                maxCount = 1;
            } else {
                maxCount = array != null && array.length > 0 ? array[0] : 0;
            }
        }
        System.out.println(maxCount);
        return maxCount;
        
    }
    
    /**
     * 官方解答
     * @param s
     * @return
     */
    public static int lengthOfLongestSubstring2(String s) {
        // 哈希集合，记录每个字符是否出现过
        Set<Character> occ = new HashSet<Character>();
        int n = s.length();
        // 右指针，初始值为 -1，相当于我们在字符串的左边界的左侧，还没有开始移动
        int rk = -1, ans = 0;
        for (int i = 0; i < n; ++i) {
            if (i != 0) {
                // 左指针向右移动一格，移除一个字符
                occ.remove(s.charAt(i - 1));
            }
            while (rk + 1 < n && !occ.contains(s.charAt(rk + 1))) {
                // 不断地移动右指针
                occ.add(s.charAt(rk + 1));
                ++rk;
            }
            // 第 i 到 rk 个字符是一个极长的无重复字符子串
            ans = Math.max(ans, rk - i + 1);
        }
        System.out.println(ans);
        return ans;
    }
    
    public static void main(String[] args) {
//        lengthOfLongestSubstring("ieblgmgiyhhxygivtwvfzvtgmikwndryisjqeradzhczpmujirqjojpbuzxhdohnjqdpkdulnykekgnszddnpsojsnsxeaknspec");
        lengthOfLongestSubstring2("ieblgmgiyhhxygivtwvfzvtgmikwndryisjqeradzhczpmujirqjojpbuzxhdohnjqdpkdulnykekgnszddnpsojsnsxeaknspec");
        
    }
}
