1、Maven解析依赖信息时会到本地仓库中查找被依赖的jar包
	对于自己开发的Maven工程，使用mvn install命令安装后就可以进入仓库

2、依赖的范围
	2.1 compile范围依赖
		对主程序是否有效：  有效
		对测试程序是否有效：有效
		是否参与打包：		参与
		是否参与部署：		参与
		典型例子：spring-core

	2.2 test范围依赖
		无效
		有效
		不参与
		不参与
		典型例子：junit

	2.3 provided范围依赖
		有效
		有效
		不参与
		不参与
		典型例子：servlet-api.jar

3、依赖的传递性
	test、provide没有传递性

4、依赖排除
	<exclusions>
		<exclusion>
			<groupId></groupId>
			<artifactId></artifactId>
		</exclusion>
	</exclusions>

5、统一版本号
	<properties>
		<name>4.0.0.RELEASE</name>
	</properties>

	<version>${name}</version>