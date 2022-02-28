# leetcode 220



## 桶排序

```java
class Solution {
    private long size;
    public boolean containsNearbyAlmostDuplicate(int[] nums, int k, int t) {
        size = t+1L;
        Map<Long, Long> hash = new HashMap<>();

        for(int i = 0; i < nums.length; i++){
            long id = getId(nums[i]);
            long u = nums[i]*1L;

            if(hash.containsKey(id)){
                return true;
            }else{
                hash.put(id, u);
            }

            if(hash.containsKey(id-1) && u-hash.get(id-1) <= t){
                return true;
            }
            if(hash.containsKey(id+1) && hash.get(id+1)-u <= t){
                return true;
            }

            if(i >= k){
                hash.remove(getId(nums[i-k]));
            }
        }

        return false;
    }


    private long getId(int n){
        return n >= 0 ? n/size : ((n+1)/size)-1;
    }
}
```

