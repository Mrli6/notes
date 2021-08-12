bis(x, y)；根据 y中为1的位，将 x对应位 置1

bic(x, y)；根据 y中为1的位，将 x对应位 置0



```java
x|y = bis(x,y);
x^y = bis(bic(x,y), bic(y,x));
```

