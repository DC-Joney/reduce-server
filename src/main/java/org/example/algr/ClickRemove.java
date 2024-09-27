//package org.wroker.algr;
//
//import java.util.Collections;
//import java.util.List;
//import java.util.Stack;
//
//public class ClickRemove {
//
//    public static String remove(String s) {
//        Stack<Character> stack = new Stack<>();
//        for (int i = 0; i < s.length(); i++) {
//            char c = s.charAt(i);
//
//            if (!stack.isEmpty() && stack.peek() == c) {
//                stack.pop();
//                continue;
//            }
//
//            stack.push(c);
//        }
//        return stack.toString();
//    }
//
//    public class ListNode {
//        int val;
//        ListNode next = null;
//
//        public ListNode(int val) {
//            this.val = val;
//        }
//    }
//
//    public static class Solution {
//        /**
//         * 代码中的类名、方法名、参数名已经指定，请勿修改，直接返回方法规定的值即可
//         *
//         * @param head ListNode类
//         * @return ListNode类
//         */
//        public ListNode ReverseList(ListNode pHead1, ListNode pHead2) {
//
//            ListNode node1 = pHead1;
//            ListNode node2 = pHead2;
//            Stack<ListNode> listNodes = new Stack<>();
//
//            while (node1 != null || node2 != null) {
//                if (node1 != null && node1.val <= node2.val) {
//                    listNodes.push(node1);
//                    node1 = node1.next;
//                } else {
//                    listNodes.push(node2);
//                    node2 = node2.next;
//                }
//            }
//
//
//            return null;
//
//
//        }
//
//        /**
//         * 代码中的类名、方法名、参数名已经指定，请勿修改，直接返回方法规定的值即可
//         *
//         * @param head ListNode类
//         * @param val  int整型
//         * @return ListNode类
//         */
//        public ListNode deleteNode(ListNode head, int val) {
//            // write code here
//
//            ListNode node = head;
//            ListNode preNode = null;
//            while (node != null) {
//
//                if (node.val == val) {
//                    //prevNode == null 说明是head节点
//                    if (preNode == null) {
//                        return head.next;
//                    }
//
//                    preNode.next = node.next;
//                } else {
//                    preNode = node;
//                }
//
//                node = node.next;
//            }
//
//            return head;
//        }
//    }
//
//    public boolean hasCycle(ListNode head) {
//        //先判断链表为空的情况
//        if(head == null)
//            return false;
//        //快慢双指针
//        ListNode fast = head;
//        ListNode slow = head;
//        //如果没环快指针会先到链表尾
//        while(fast != null && fast.next != null){
//            //快指针移动两步
//            fast = fast.next.next;
//            //慢指针移动一步
//            slow = slow.next;
//            //相遇则有环
//            if(fast == slow)
//                return true;
//        }
//        //到末尾则没有环
//        return false;
//    }
//}
//
//
//    public static int binarySearch(int[] array, int val) {
//
//        int left = 0;
//        int right = array.length - 1;
//        while (left <= right) {
//            int mid = (left + right) >>> 1;
//            if (val < array[mid]) {
//                right = mid - 1;
//            } else if (val > array[mid]) {
//                left = mid + 1;
//            } else if (val == array[mid]) {
//                return mid;
//            }
//        }
//
//        return -1;
//    }
//
//
//    /**
//     * 快速排序调用方法
//     *
//     * @param ary   待排序数组
//     * @param left  左值
//     * @param right 右值
//     * @return int值
//     * @author Cansluck
//     */
//    //[5,2,6,4,9,3,4,10,11]
//    public static int getSortNum(int[] ary, int left, int right) {
//        // 定义一个中枢值pivot，让其等于数组的左值，枢轴选定后永远不变，最终在中间，前小后大
//        int pivot = ary[left];
//        while (left < right) {
//            // 看后面ary[right] > pivot比较，如果右边数组值大于中枢值，说明不需要调整位置，则让右值（right）自减1
//            while (left < right && ary[right] >= pivot) {
//                right--;  // 执行自减操作
//            }
//            // 如果上面循环不符合条件的，则说明右边数组的一个值，小于中枢值（pivot），则将其替换到左边数组中
//            ary[left] = ary[right];
//            // 看后面ary[left] < pivot比较，如果左边数组值小于中枢值，说明不需要调整位置，则让左值（left）自增1
//            while (left < right && ary[left] <= pivot) {
//                left++;  // 执行自增操作
//            }
//            // 如果上面循环不符合条件，则说明左边数组的一个值，大于中枢值（pivot），则将其替换到右边数组中
//            ary[right] = ary[left];
//        }
//        // 最后将中枢值给自增后的左边数组的一个值中
//        ary[left] = pivot;
//        // 返回左边数组下标
//        return left;
//    }
//
//    /**
//     * 快速排序递归方法
//     *
//     * @param ary   待排序数组
//     * @param left  左值
//     * @param right 右值
//     * @author Cansluck
//     */
//    public static void quickSort(int[] ary, int left, int right) {
//        // 定义中枢值
//        int pivot;
//        // 判断
//        if (left < right) {
//            // 根据方法得到了每次中枢值的位置
//            pivot = getSortNum(ary, left, right);
//            // 根据中枢值（pivot），来对左边数组进行递归调用快速排序
//            quickSort(ary, left, pivot - 1);
//            // 根据中枢值（pivot），来对右边数组进行递归调用快速排序
//            quickSort(ary, pivot + 1, right);
//        }
//    }
//
//
//    public static void sort(int[] array, int left, int right) {
//        if (left < right) {
//            int middle = getMiddle(array, left, right);
//            sort(array, left, middle - 1);
//            sort(array, middle + 1, right);
//        }
//
//
//    }
//
//    private static int getMiddle(int[] array, int left, int right) {
//
//        int middle = array[left];
//
//        while (left < right) {
//            while (left < right && array[right] >= middle) {
//                right--;
//            }
//
//            array[left] = array[right];
//
//
//            while (left < right && array[left] <= middle) {
//                left++;
//            }
//
//            array[right] = array[left];
//        }
//
//        array[left] = middle;
//
//        return left;
//    }
//
//
//    /**
//     * 代码中的类名、方法名、参数名已经指定，请勿修改，直接返回方法规定的值即可
//     *
//     * @param head ListNode类
//     * @param m    int整型
//     * @param n    int整型
//     * @return ListNode类
//     */
//    public ListNode reverseBetween(ListNode head, int m, int n) {
//        // write code here
//
//
//        int i = 0;
//
//
//        ListNode preNode = null;
//        ListNode lastNode = null;
//        ListNode node = head;
//
//        Stack<ListNode> stack = new Stack<>();
//
//        while (node != null) {
//            if (i <= n && i >= m) {
//                stack.push(node);
//            }
//
//            if (i < m) {
//                //上游的节点
//                preNode = node;
//            }
//
//            if (i > n) {
//                lastNode = node;
//            }
//
//            i++;
//            node = node.next;
//        }
//
//        //说明是head节点
//        if (preNode == null && lastNode == null) {
//            //说明是 head 和 tail节点
//        }else if (preNode == null) {
//            //从head节点开始反转
//        }else if (lastNode == null) {
//            //从tail节点开始反转
//        }else {
//
//        }
//
//
//
//    }
//
//    public static void main(String[] args) {
//        System.out.println(remove("abbc"));
//        System.out.println(remove("abba"));
//        System.out.println(remove("bbbb"));
//
//
//    }
//}
