```java
leetcode 39
class Solution {
    List<List<Integer>> res = new LinkedList<List<Integer>>();
    public List<List<Integer>> combinationSum(int[] candidates, int target) {
        Arrays.sort(candidates);
        backTrace(candidates, target, 0, 0, new LinkedList<>());

        return res;
    }

    public void backTrace(int[] candidates, int target, int sum, int start, LinkedList<Integer> list){
        if(sum == target){
            res.add(new LinkedList<>(list));
            return;
        }

        for(int i = start; i < candidates.length; i++){
            if(sum + candidates[i] > target){
                return;
            }
            list.add(candidates[i]);
            backTrace(candidates, target, sum+candidates[i], i, list);
            list.removeLast();
        }
    }
}
```

```java
leetcode 40
class Solution {
    List<List<Integer>> res = new LinkedList<List<Integer>>();
    public List<List<Integer>> combinationSum2(int[] candidates, int target) {
        Arrays.sort(candidates);
        backTrace(candidates, 0, 0, target, new LinkedList<Integer>());
        return res;
    }

    public void backTrace(int[] candidates, int sum, int start, int target, LinkedList<Integer> list){
        if(sum == target){
            res.add(new LinkedList<>(list));
            return;
        }

        for(int i = start; i < candidates.length; i++){
            if(sum + candidates[i] > target){
                break;
            }
            if(i > start && candidates[i] == candidates[i-1]){
                continue;
            }
            list.add(candidates[i]);
            backTrace(candidates, sum+candidates[i], i+1, target, list);
            list.removeLast();
        }
    }
}
```

```java
class Solution {
    List<List<Integer>> res = new LinkedList<List<Integer>>();
    public List<List<Integer>> permuteUnique(int[] nums) {
        Arrays.sort(nums);
        boolean[] visit = new boolean[nums.length];
        backTrace(nums, visit, new LinkedList<Integer>());
        return res;
    }

    public void backTrace(int[] nums, boolean[] visit, LinkedList<Integer> list){
        if(list.size() == nums.length){
            res.add(new LinkedList<Integer>(list));
            return;
        }

        for(int i = 0; i < nums.length; i++){
            //限制访问顺序
            if(visit[i] || (i > 0 && nums[i] == nums[i-1] && !visit[i-1])){
                continue;
            }
            list.add(nums[i]);
            visit[i] = true;
            backTrace(nums, visit, list);
            list.removeLast();
            visit[i] = false;
        }
    }
}
```

这三题都涉及到 去重问题，值得学习