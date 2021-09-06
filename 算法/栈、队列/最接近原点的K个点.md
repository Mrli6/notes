# leetcode 973



## 大顶堆

```java
class Solution {
    public int[][] kClosest(int[][] points, int k) {
        PriorityQueue<int[]> pq = new PriorityQueue<>(new Comparator<int[]>() {
            public int compare(int[] m, int[] n) {
                return n[0] - m[0];
            }
        });

        // [0]存距离的平方，[1]存下标
        for(int i = 0; i < points.length; i++){
            int n = points[i][0]*points[i][0] + points[i][1]*points[i][1];
            if(pq.size() < k){
                pq.offer(new int[]{n, i});
            }else{
                if(n < pq.peek()[0]){
                    pq.remove();
                    pq.offer(new int[]{n, i});
                }
            }
        }

        int[][] res = new int[k][2];
        for(int i = 0; i < k; i++){
            res[i] = points[pq.poll()[1]];
        }

        return res;
    }
}
```

