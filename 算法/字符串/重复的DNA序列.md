# leetcode 187



## 暴力法（超时）

```java
class Solution {
    public List<String> findRepeatedDnaSequences(String s) {
        List<String> res = new LinkedList<String>();
        int left = 0;
        while(left+9 < s.length()){
            String s1 = s.substring(left, left+10);
            for(int i = 0; i+9 < s.length(); i++){
                if(i != left){
                    String s2 = s.substring(i, i+10);
                    if(s1.equals(s2) && !res.contains(s1)){
                        res.add(s1);
                        break;
                    }
                }
            }
            left++;
        }

        return res;
    }
}
```





## 暴力法优化

```java
class Solution {
    public List<String> findRepeatedDnaSequences(String s) {
        Set<String> hashSet = new HashSet<String>();	//已经遍历的字符串
        List<String> res = new LinkedList<String>();	//结果
        int left = 0;
        while(left+9 < s.length()){
            String s1 = s.substring(left, left+10);
            if(hashSet.contains(s1) && !res.contains(s1)){	//当前字符串已经存在，并且不存在于结果中
                res.add(s1);
            }
            hashSet.add(s1);
            left++;
        }

        return res;
    }
}
```

