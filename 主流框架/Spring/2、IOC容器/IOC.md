# IOC底层原理

技术实现：xml解析、工厂模式、反射

![image-20210802160515424](C:\Users\94125\AppData\Roaming\Typora\typora-user-images\image-20210802160515424.png)

![image-20210802161238636](C:\Users\94125\AppData\Roaming\Typora\typora-user-images\image-20210802161238636.png)

![image-20210802161855508](C:\Users\94125\AppData\Roaming\Typora\typora-user-images\image-20210802161855508.png)







# IOC接口

## 	1、IOC思想基于IOC容器完成，IOC容器底层就是对象工厂



## 	2、Spring提供IOC容易实现两种方式（两个接口）



### 		2.1 BeanFactory：IOC容器基本实现，一般不提供给开发者使用

***加载配置文件时不会创建对象，在获取对象时才创建对象**



### 		2.2 ApplicationContext：BeanFactory接口的子接口，提供更多更强大功能，一般由开发人员使用

***加载配置文件时就创建了对象**







## 	3、ApplicationContext接口的实现类



### 		3.1 FileSystemXmlApplicationContext

*路径要写带盘符的路径



### 		3.2 ClassPathXmlApplicationContext

*src文件下的路径