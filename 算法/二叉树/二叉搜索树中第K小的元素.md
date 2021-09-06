# leetcode 230



## 中序遍历

```java
class Solution {
    public int kthSmallest(TreeNode root, int k) {
        
        ArrayList<Integer> res = new ArrayList<Integer>();
        searchNum(root, res);

        return res.get(k-1);
    }

    public void searchNum(TreeNode root, ArrayList<Integer> list){
        if(root == null){
            return;
        }

        searchNum(root.left, list);
        list.add(root.val);
        searchNum(root.right, list);
    }
}
```





## 递归

```java
class Solution {
    public int kthSmallest(TreeNode root, int k) {
        int left = searchNum(root.left);
        
        if(left >= k){
            return kthSmallest(root.left, k);
        }else if(left+1 == k){
            return root.val;
        }else{
            return kthSmallest(root.right, k-left-1);
        }
    }

    public int searchNum(TreeNode root){
        if(root != null){
            return 1+searchNum(root.left)+searchNum(root.right);
        }else{
            return 0;
        }
    }
}
```

