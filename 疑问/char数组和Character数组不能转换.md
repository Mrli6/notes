```java
class Solution {
    public List<List<String>> groupAnagrams(String[] strs) {
        Map<Character[], List<String>> res = new HashMap<>();

        for(String s : strs){
            char[] temp = s.toCharArray();
            Arrays.sort(temp); 
            List<String> list = res.getOrDefault(temp, new ArrayList<String>());
            list.add(s);
            res.put(temp, list);
        }

        return new ArrayList<List<String>>(res.values());
    }
}
```



对于以上代码，报错char[] cannot be converted to Character[]

为什么







```java
class Solution {
    public List<List<String>> groupAnagrams(String[] strs) {
        Map<char[], List<String>> res = new HashMap<>();

        for(String s : strs){
            char[] temp = s.toCharArray();
            Arrays.sort(temp);
            List<String> list = res.getOrDefault(temp, new ArrayList<String>());
            list.add(s);
            res.put(temp, list);
        }

        return new ArrayList<List<String>>(res.values());
    }
}
```

对于以上代码，key值的内容相同，但却认为是不同的key，为什么

 在内容相同情况下，String会hash得到相同的key，由于char[]特殊机制，相同内容的在hash后值不会相同。 因此Map中必须使用String作为key。

char[]的本质是数组，没有重写hashcode而已，而String重写了

