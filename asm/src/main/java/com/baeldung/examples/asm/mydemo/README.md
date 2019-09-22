### asm demo
网上的一个例子：https://blog.csdn.net/sweatOtt/article/details/88114002  
把Hello.java中的
```java
@Add
public int add(int x, int y) {
    return x + y;
}
```
改成
```java
@Add
public int add(int x, int y) {
    int z = x + y;
    return z;
}
```
然后输出到新文件Hello.class