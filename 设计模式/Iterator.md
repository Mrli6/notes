# Aggregate接口

​	实现这个接口的类 表示 集合，可以创建一个迭代器

```java
public interface Aggregate {
    Iterator iterator();	// 创建迭代器的方法
}
```



# Iterator接口

​	提供了迭代器的基本方法

```java
public interface Iterator {
    boolean hasNext();	// 判断是否还有下一本书
    Object next();		// 返回下一本书的具体信息
}
```



# Book类

​	代表具体的书本

```java
public class Book {
    private String name;	// 书本名字

    public Book(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
```



# BookShelfIterator类

​	是一个迭代器类，实现了Iterator接口

```java
public class BookShelfIterator implements Iterator {
    private BookShelf books;
    private int index = 0;

    public BookShelfIterator(BookShelf books) {
        this.books = books;
    }

    @Override
    public boolean hasNext() {
        return books.getLength() > index ? true : false;
    }

    @Override
    public Object next() {
        Book book = books.getBookAt(index);
        index++;
        return book;
    }
}
```



# BookShelf类

```java
public class BookShelf implements Aggregate {

    private ArrayList<Book> books;
    private int last = 0;	// 书本总数

    public BookShelf() {
        this.books = new ArrayList<Book>();
    }

    public Book getBookAt(int index){	// 获取下标为index的书本
        return books.get(index);
    }
    public int getLength(){	// 获取书本总数
        return last;
    }
    public void appendBook(Book book){	// 添加书本
        this.books.add(book);
        last++;
    }

    @Override
    public Iterator iterator() {
        return new BookShelfIterator(this);
    }
}
```

