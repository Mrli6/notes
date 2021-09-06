# leetcode 215





```java
class Solution {
    public int findKthLargest(int[] nums, int k) {
        PriorityQueue<Integer> max = new PriorityQueue<>();

        for(int i = 0; i < nums.length; i++){
            max.offer(nums[i]);
            if(max.size() > k){
                max.poll();
            }
        }

        return max.peek();
    }
}
```



