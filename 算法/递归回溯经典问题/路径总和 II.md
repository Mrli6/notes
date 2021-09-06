# leetcode 113



```java
class Solution {

    List<List<Integer>> res = new ArrayList<List<Integer>>();
    List<Integer> list = new LinkedList<Integer>();

    public List<List<Integer>> pathSum(TreeNode root, int targetSum) {

        backTrace(root, targetSum);

        return res;
    }

    public void backTrace(TreeNode root, int targetSum){
        if(root == null){
            return;
        }
        
        list.add(root.val);

        if(root.left == null && root.right == null && root.val == targetSum){
            res.add(new LinkedList<Integer>(list));
            list.remove(list.size()-1);
            return;
        }
        
        backTrace(root.left, targetSum - root.val);
        backTrace(root.right, targetSum - root.val);
        list.remove(list.size()-1);
    }
}
```

