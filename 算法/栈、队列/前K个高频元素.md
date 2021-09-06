# leetcode 347



```java
class Solution {
    public int[] topKFrequent(int[] nums, int k) {
        Map<Integer, Integer> hashMap = new HashMap<Integer, Integer>();

        for(int n : nums){
            if(hashMap.containsKey(n)){
                hashMap.put(n, hashMap.get(n)+1);
            }else{
                hashMap.put(n, 1);
            }
        }
		
        // [0]存数字，[1]存数量，按数量大小，从小到大排序
        PriorityQueue<int[]> pq = new PriorityQueue<>(new Comparator<int[]>() {
            public int compare(int[] m, int[] n) {
                return m[1] - n[1];
            }
        });

        for(Integer key : hashMap.keySet()){
            int n = hashMap.get(key);
            if(pq.size() >= k){
                if(n > pq.peek()[1]){
                    pq.remove();
                    pq.offer(new int[]{key, n});
                }
            }else{
                
            }
        }

        int[] res = new int[k];
        for(int i = 0; i < res.length; i++){
            res[i] = pq.poll()[0];
        }

        return res;
    }
}
```

改进排序方法，变为从大到小排，可以舍去后面的数量逻辑判断

```java
class Solution {
    public int[] topKFrequent(int[] nums, int k) {
        Map<Integer, Integer> hashMap = new HashMap<Integer, Integer>();

        for(int n : nums){
            if(hashMap.containsKey(n)){
                hashMap.put(n, hashMap.get(n)+1);
            }else{
                hashMap.put(n, 1);
            }
        }

        // 按数量大小，从大到小排序
        PriorityQueue<int[]> pq = new PriorityQueue<>(new Comparator<int[]>() {
            public int compare(int[] m, int[] n) {
                return n[1] - m[1];
            }
        });

        // 不用判断，直接取前k的就行
        for(Integer key : hashMap.keySet()){
            int n = hashMap.get(key);
            pq.offer(new int[]{key, n});
        }

        int[] res = new int[k];
        for(int i = 0; i < res.length; i++){
            res[i] = pq.poll()[0];
        }

        return res;
    }
}
```

