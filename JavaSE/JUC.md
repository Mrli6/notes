# 生产者消费者



## synchronized

```java
public class Data {
    private int num = 0;

    public synchronized void produce() throws InterruptedException {
        while(num != 0){
            // 等待
            this.wait();
        }
        num++;
        System.out.println("produce---->"+num);
        // 唤醒
        notifyAll();
    }

    public synchronized void reduce() throws InterruptedException {
        while(num == 0){
            // 等待
            this.wait();
        }
        num--;
        System.out.println("reduce---->"+num);
        // 唤醒
        notifyAll();
    }
}
```

如果把方法中的while改为if，在3个及以上线程时，会出现 虚假唤醒。





## lock

```java
public class Data {
    private int num = 0;

    Lock lock = new ReentrantLock();
    Condition condition = lock.newCondition();

    public void produce(){
        lock.lock();
        try {
            while(num != 0){
                // 等待
                condition.await();
            }
            num++;
            System.out.println("produce---->"+num);
            // 唤醒
            condition.signalAll();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }

    public synchronized void reduce(){
        lock.lock();
        try {
            while(num == 0){
                // 等待
                condition.await();
            }
            num--;
            System.out.println("reduce---->"+num);
            // 唤醒
            condition.signalAll();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }
}
```

使用lock可以实现 **线程的精确唤醒**，代码如下：

```java
public class ThreePrint {
    private int num = 1;

    private Lock lock = new ReentrantLock();

    private Condition conditionA = lock.newCondition();
    private Condition conditionB = lock.newCondition();
    private Condition conditionC = lock.newCondition();


    public void printA(){
        lock.lock();
        try{
            while(num != 1){
                // 等待
                conditionA.await();
            }
            num = 2;
            System.out.println("AAA");
            // 指定唤醒B
            conditionB.signal();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }

    public void printB(){
        lock.lock();
        try{
            while(num != 2){
                // 等待
                conditionB.await();
            }
            num = 3;
            System.out.println("BBB");
            // 指定唤醒B
            conditionC.signal();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }

    public void printC(){
        lock.lock();
        try{
            while(num != 3){
                // 等待
                conditionC.await();
            }
            num = 1;
            System.out.println("CCC");
            // 指定唤醒B
            conditionA.signal();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }

}
```









# 常用辅助类



## CountDownLatch

​	规定线程数量n，每调用一次 countDown ，线程数量 -1，当 n 变为 0 时，可以调用 await，使得 之后的操作 一定在 这 n 个线程执行完毕后再执行。

​	如果 n 不减到 0，await 方法会让程序一直等待

```java
public class CountDownLatchTest {
    public static void main(String[] args) throws InterruptedException {
        CountDownLatch countDownLatch = new CountDownLatch(6);

        for(int i = 1; i <= 6; i++){
            new Thread(()->{
                System.out.println(Thread.currentThread().getName()+"   go out");
                countDownLatch.countDown();
            }).start();
        }
        countDownLatch.await();

        System.out.println("close door");
    }
}
```





## CyclicBarrier

```java
public class Test {
    public static void main(String[] args) {
        CyclicBarrier cyclicBarrier = new CyclicBarrier(7, ()->{
            System.out.println("召唤神龙成功！");
        });

        for(int i = 1; i <= 7; i++){
            final int temp = i;
            new Thread(()->{
                System.out.println(Thread.currentThread().getName()+"收集"+temp+"颗龙珠");
                try {
                    cyclicBarrier.await();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (BrokenBarrierException e) {
                    e.printStackTrace();
                }
            }).start();
        }
    }
}
```







## Semaphore

```java
public class Test {
    public static void main(String[] args) {
        // 场景：限流
        // 三个停车位六个车
        Semaphore semaphore = new Semaphore(3);

        for (int i = 1; i <= 6; i++) {
            new Thread(()->{
                try {
                    semaphore.acquire();    // 获取
                    System.out.println(Thread.currentThread().getName()+"   抢到车位");
                    TimeUnit.SECONDS.sleep(1);
                    System.out.println(Thread.currentThread().getName()+"   离开车位");
                } catch (InterruptedException e) {
                    e.printStackTrace();    // 释放
                } finally {
                    semaphore.release();
                }
            }).start();
        }
    }
}
```







# 读写锁

读锁：多线程

写锁：单线程

```java
ReentrantReadWriteLock reentrantReadWriteLock = new ReentrantReadWriteLock();

reentrantReadWriteLock.readLock().lock();
try {
    ...
} catch (Exception e) {
    e.printStackTrace();
} finally {
    reentrantReadWriteLock.readLock().unlock();
}


reentrantReadWriteLock.writeLock().lock();
try {
    ...
} catch (Exception e) {
    e.printStackTrace();
} finally {
    reentrantReadWriteLock.writeLock().unlock();
}
```









# 阻塞队列

需要在new时设置容量

| 无返回值，抛异常 | 有返回值，不抛异常 | 阻塞一直等待 | 阻塞超时退出 |
| ---------------- | ------------------ | ------------ | ------------ |
| add              | offer              | put          | offer        |
| remove           | poll               | take         | poll         |





# 同步队列

无容量。放一个元素，必须取出来后才能继续放







# 线程池



## 三大方法

```java
public class Test {
    public static void main(String[] args) {
        ExecutorService executorService = Executors.newSingleThreadExecutor();// 只有一个线程
        // Executors.newFixedThreadPool(5); // 5个线程
        // Executors.newCachedThreadPool();    // 大小可伸缩的


        try{
            for (int i = 0; i < 10; i++) {
                executorService.execute(() -> {
                    System.out.println(Thread.currentThread().getName() + " ok");
                });
            }
        } catch (Exception e){
            e.printStackTrace();
        } finally {
            executorService.shutdown();
        }
    }
}
```





## 七大参数

三大方法的本质是 `new ThreadPoolExecutor()`

```java
public ThreadPoolExecutor(int corePoolSize,	// 核心线程池大小
                          int maximumPoolSize,	// 最大核心线程池大小
                          long keepAliveTime,	// 超时没人调用就会释放
                          TimeUnit unit,		// 超时单位
                          BlockingQueue<Runnable> workQueue,	// 阻塞队列
                          ThreadFactory threadFactory,			// 线程工厂，用来创建线程
                          RejectedExecutionHandler handler) {	// 拒绝策略 
    ...
}
```

手动实现

```java
public class Test {
    public static void main(String[] args) {
        ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(
                2,
                5,
                3,
                TimeUnit.SECONDS,
                new LinkedBlockingDeque<>(3),
                Executors.defaultThreadFactory(),
                new ThreadPoolExecutor.AbortPolicy()
        );


        try{
            for (int i = 0; i < 9; i++) {
                threadPoolExecutor.execute(()->{
                    System.out.println(Thread.currentThread().getName()+"ok");
                });
            }
        } catch (Exception e){
            e.printStackTrace();
        } finally {
            threadPoolExecutor.shutdown();
        }
    }
}
```





## 四大拒绝策略

```java
AbortPolicy			//队列满抛异常
CallerRunsPolicy	//队列满，让多余的回去
DiscardPolicy		//队列满不抛异常，丢去多余的
DiscardOldestPolicy	//不抛异常，满时与最早的竞争
```





## 拓展

```java
最大线程数量如何定义？
	CPU密集型，内核数就是最大线程数，内核数通过Runtime.getRuntime().availableProcessors()获取
    IO密集型，有多少个消耗IO的程序，最大线程数 > 消耗IO程序数
```









# 四大函数式接口



## 函数型接口

```java
public class Test {
    public static void main(String[] args) {
        /*
        Function<String, String> function = new Function<String, String>() {
            @Override
            public String apply(String str) {
                return str;
            }
        };
        */
        Function<String, String> function = (str) -> {return str;};

        System.out.println(function.apply("123"));
    }
}
```





## 断定型接口

```java
public class Test {
    public static void main(String[] args) {
        /*
        // 判断字符串是否为空
        Predicate<String> predicate = new Predicate<String>() {
            @Override
            public boolean test(String str) {
                return str.isEmpty();
            }
        };
        */
        Predicate<String> predicate = (str) -> {return str.isEmpty();};


        System.out.println(predicate.test("1"));
    }
}
```





## 消费型接口

```java
public class Test {
    public static void main(String[] args) {
        /*
        Consumer<String> consumer = new Consumer<String>() {
            @Override
            public void accept(String str) {
                System.out.println(str);
            }
        };
        */
        Consumer<String> consumer = (str) -> { System.out.println(str); };

        consumer.accept("1");
    }
}
```





## 供给型接口

```java
public class Test {
    public static void main(String[] args) {
        /*
        Supplier supplier = new Supplier<Integer>() {
            @Override
            public Integer get() {
                return 1024;
            }
        };
        */
        Supplier supplier = () -> {return 1024;};

        System.out.println(supplier.get());
    }
}
```







# Stream流式计算

![image-20211006101027849](D:\面向工作学习\JavaSE\JUC.assets\image-20211006101027849.png)







# ForkJoin、并行流

forkjoin特点：工作窃取，维护的是双端队列。







# 异步回调

Future







# JMM

Volatile是java虚拟机提供的轻量级同步机制

1、保证可见性

对于一下代码，程序虽然输出1，但是并不会终止，因为不可见

```java
public class Test {
    private static int num = 0;

    public static void main(String[] args) {
        new Thread(()->{
            while(num == 0){
            }
        }).start();

        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        num = 1;
        System.out.println(num);
    }
}
```

用volative修饰可以是变量具有可见性，程序输出1后终止

```java
public class Test {
    private volatile static int num = 0;

    public static void main(String[] args) {
        new Thread(()->{
            while(num == 0){
            }
        }).start();

        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        num = 1;
        System.out.println(num);
    }
}
```



2、不保证原子性

```java
public class Test {
    private volatile static int num = 0;

    private static void add(){
        num++;
    }

    public static void main(String[] args) {
        for(int i = 0; i < 20; i++){
            new Thread(()->{
                for(int j = 0; j < 1000; j++){
                    add();
                }
            }).start();
        }
        
        while(Thread.activeCount() > 2){
            Thread.yield();
        }

        System.out.println(num);
    }
}
```

答案本来是2w，但volat不保证原子性，所以错误，可以在方法上加synchronized解决原子性问题

使用 JUC解决

```java
public class Test {
    private volatile static AtomicInteger num = new AtomicInteger();

    private static void add(){
        num.getAndIncrement();
    }

    public static void main(String[] args) {
        for(int i = 0; i < 20; i++){
            new Thread(()->{
                for(int j = 0; j < 1000; j++){
                    add();
                }
            }).start();
        }

        while(Thread.activeCount() > 2){
            Thread.yield();
        }

        System.out.println(num);
    }
}
```



3、禁止指令重排

```java
```







关于JMM的一些同步约定：

1、线程解锁前，必须把共享变量立刻刷回主存

2、线程加锁前，必须读取主存中的最新值到工作内存

3、加锁和解锁是同一把锁







# 单例模式









# 深入理解CAS







# 原子引用解决ABA问题
