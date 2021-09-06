# leetcode 25



## 递归

![image-20210822085336550](D:\面向工作学习\算法\链表\k个一维翻转链表.assets\image-20210822085336550.png)

```java
class Solution {
    public ListNode reverseKGroup(ListNode head, int k) {
        if(k == 1 || head == null || head.next == null){
            return head;
        }

        ListNode temp = head;
        int n = k-1;
        while(n > 0){
            n--;
            temp = temp.next;
            if(temp == null){
                return head;
            }
        }
        
        ListNode latter = reverseKGroup(temp.next, k);	//翻转k+1之后的结点
        ListNode left = head;
        ListNode right = head.next;

        ListNode pre = latter;		//为前k个结点指路
        while(right != temp){
            left.next = pre;
            pre = left;
            left = right;
            right = right.next;
        }
        left.next = pre;
        right.next = left;


        return temp;
    }
}
```

