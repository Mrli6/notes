# leetcode 560



## 暴力法

```java
class Solution {
    public int subarraySum(int[] nums, int k) {
        int res = 0;

        for(int i = 0; i < nums.length; i++){
            if(nums[i] == k){
                res++;
            }
            int temp = nums[i];
            for(int j = i+1; j < nums.length; j++){
                temp += nums[j];
                if(temp == k){
                    res++;
                }
            }
        }

        return res;
    }
}
```







