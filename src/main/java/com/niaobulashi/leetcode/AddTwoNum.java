package com.niaobulashi.leetcode;

/**
 题目描述
 给出两个 非空 的链表用来表示两个非负的整数。其中，它们各自的位数是按照 逆序 的方式存储的，并且它们的每个节点只能存储 一位 数字。
 
 如果，我们将这两个数相加起来，则会返回一个新的链表来表示它们的和。
 
 您可以假设除了数字 0 之外，这两个数都不会以 0 开头。
 
 示例：
 
 输入：(2 -> 4 -> 3) + (5 -> 6 -> 4)
 输出：7 -> 0 -> 8
 原因：342 + 465 = 807
 题目解析
 设立一个表示进位的变量carried，建立一个新链表，把输入的两个链表从头往后同时处理，每两个相加，将结果加上carried后的值作为一个新节点到新链表后面。
 */
public class AddTwoNum {
    
    public static void main(String[] args) {
        ListNode l1 = new ListNode(2);
        ListNode l2 = new ListNode(5);
        l1.next = new ListNode(4);
        l1.next.next = new ListNode(3);
        l2.next = new ListNode(6);
        l2.next.next = new ListNode(6);
        l2.next.next.next = new ListNode(7);
        l2.next.next.next.next = new ListNode(3);
        getNode(l1);
        getNode(l2);
    
        getNode(addTwoNumbers(l1,l2));
    }
    
    /**
     * 两数相加
     * @param l1 链表1
     * @param l2 链表2
     * @return 相加结果
     */
    public static ListNode addTwoNumbers(ListNode l1, ListNode l2) {
        // 定义预设链表
        ListNode pre = new ListNode(0);
        // 当前链表
        ListNode cur = pre;
        // 进位数，默认为0
        int carry = 0;
        // 知道l1,l2都为空才结束
        while (l1 != null || l2 != null) {
            int x = l1 != null ? l1.val : 0;
            int y = l2 != null ? l2.val : 0;
            // 底位数开始相加，下一次相加将上一次的进位数也加起来
            int sum = x + y + carry;
            // 获取进位数
            carry = sum / 10;
            // 获取余数
            sum = sum % 10;
            // 将余数设置为当前的一个节点值
            cur.next = new ListNode(sum);
            // 向后移动
            cur = cur.next;
            // l1,l2也需要向后移动
            if (l1 != null) {
                l1 = l1.next;
            }
            if (l2 != null) {
                l2 = l2.next;
            }
        }
        // 如果最后还有进位数，则需要最后在进一位，上1
        if (carry == 1) {
            cur.next = new ListNode(carry);
        }
        return pre.next;
    }
    
    /**
     * 遍历list
     * @param listNode
     * @return
     */
    public static ListNode getNode(ListNode listNode){
        if (listNode != null) {
            System.out.print(listNode.val +"->");
            return getNode(listNode.next);
        }
        else{
            System.out.println("NULL");
            return null;
        }
    }
}

/**
 * 定义链表
 */
class ListNode {
    int val;
    ListNode next;
    
    ListNode(int x) {
        val = x;
    }
}


