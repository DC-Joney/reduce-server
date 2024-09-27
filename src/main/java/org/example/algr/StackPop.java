package org.wroker.algr;

import java.util.Stack;

public class StackPop {

    public static boolean IsPopOrder(int [] pushA,int [] popA) {
        //表示栈空间的大小，初始化为0
        int n = 0;
        //出栈序列的下标
        int j = 0;
        //对于每个待入栈的元素
        for(int num : pushA){
            //加入栈顶
            pushA[n] = num;
            //当栈不为空且栈顶等于当前出栈序列
            while(n >= 0 && pushA[n] == popA[j]){
                //出栈，缩小栈空间
                j++;
                n--;
            }
            n++;
        }
        //最后的栈是否为空
        return n == 0;
    }


    public static boolean IsPopOrder1(int [] pushA,int [] popA) {

        int j = 0;

        Stack<Integer> stack = new Stack<>();

        for (int k : pushA) {
            stack.push(k);
            while (!stack.isEmpty() && stack.peek() == popA[j]) {
                j++;
                stack.pop();
            }

        }

        return stack.size() == 0;

    }


    public static void main(String[] args) {
        boolean b = IsPopOrder1(new int[]{1, 2, 3, 4, 5,6}, new int[]{5, 6,3, 3, 2, 1});

        System.out.println(b);
    }
}
