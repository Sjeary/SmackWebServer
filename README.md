# 如何在Windows Mac Ubuntu部署该项目
三个系统我都尝试过了，主要考虑的就是 java环境 mysql连接问题 maven换源

## Windows Mac 开发环境 
使用IDEA打开项目，右上角开始运行项目。

数据库配置的是我的远程数据库。数据库统一，不需要额外配置数据库了

需要配置java21

maven主要是需要连接中央仓库，可以搜一下如何换源。

## Ubuntu 部署环境
这里只能通过命令行做运行。

首先安装jdk21 安装mysql 安装maven

如果要连接远程数据库，则不需要进行相应的修改。如果想要本地开发部署数据库，那么教程如下：

首先打开自己的ubuntu，下载好mysql，设置好密码，在java中的application.properties修改数据库的ip 用户名 密码

创建自己的数据库，要和代码中的一样。
(创建配置数据库要开放连接权限，设置密码强度，还挺复杂的)

尝试连接数据库，成功了才行

进行文件打包的时候，建议在ubuntu环境进行maven ：
```shell
mvn clean install -U
```
如果速度太慢，先给maven换源到国内的

打包好了之后，使用
```shell
java -jar /target/........jar
```
运行jar包，观察报错，如果遇到了报错可以先来找我看看我遇到过没有，节约时间