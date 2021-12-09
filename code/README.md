### UDP编程简介

### 环境

+ 编译器：IDEA 2021.02 	语言：java	数据库：MYSQL 8.0.25+ Navicat P15



### 项目文件

+ src 客户端和服务端的代码
+ pom.xml Maven 项目配置

+ 数据库相关文件

​						<img src="C:%5CUsers%5CHP%5CAppData%5CRoaming%5CTypora%5Ctypora-user-images%5Cimage-20211209203931150.png" alt="image-20211209203931150" style="zoom:67%;" />

+ 聊天

   + ![image-20211209204141615](C:%5CUsers%5CHP%5CAppData%5CRoaming%5CTypora%5Ctypora-user-images%5Cimage-20211209204141615.png)

   + clientgui 客户端主类

   + servergui 服务端主类

   + 连接到本地数据库的配置文件

      ![image-20211209204216795](C:%5CUsers%5CHP%5CAppData%5CRoaming%5CTypora%5Ctypora-user-images%5Cimage-20211209204216795.png)

      

### 运行：

+ 直接导入需要配置Maven环境和使用 配置Tomat 才能运行；
+ 新建一个空白的Maven文件，将相关文件复制到对应的目录下，然后修改全局配置文件pom.xml  和 数据库配置文件 mybatis-config.xml 中的相关信息：

+ ![image-20211209204547258](C:%5CUsers%5CHP%5CAppData%5CRoaming%5CTypora%5Ctypora-user-images%5Cimage-20211209204547258.png)





**依赖：**

![image-20211209204632725](C:%5CUsers%5CHP%5CAppData%5CRoaming%5CTypora%5Ctypora-user-images%5Cimage-20211209204632725.png)





**数据库**

![image-20211209204755106](C:%5CUsers%5CHP%5CAppData%5CRoaming%5CTypora%5Ctypora-user-images%5Cimage-20211209204755106.png)



### 运行结果：









#### UDP

#### 文件列表

####  客户端

![image-20211209185254285](C:%5CUsers%5CHP%5CAppData%5CRoaming%5CTypora%5Ctypora-user-images%5Cimage-20211209185254285.png)



#### 服务端





![image-20211209185312962](C:%5CUsers%5CHP%5CAppData%5CRoaming%5CTypora%5Ctypora-user-images%5Cimage-20211209185312962.png)



![image-20211209185335931](C:%5CUsers%5CHP%5CAppData%5CRoaming%5CTypora%5Ctypora-user-images%5Cimage-20211209185335931.png)



![image-20211209185408697](C:%5CUsers%5CHP%5CAppData%5CRoaming%5CTypora%5Ctypora-user-images%5Cimage-20211209185408697.png)







#### 消息类

![image-20211209185458686](C:%5CUsers%5CHP%5CAppData%5CRoaming%5CTypora%5Ctypora-user-images%5Cimage-20211209185458686.png)



#### 用户类



![image-20211209185524274](C:%5CUsers%5CHP%5CAppData%5CRoaming%5CTypora%5Ctypora-user-images%5Cimage-20211209185524274.png)



#### 数据库

![image-20211209185620997](C:%5CUsers%5CHP%5CAppData%5CRoaming%5CTypora%5Ctypora-user-images%5Cimage-20211209185620997.png)





#### 客户端

登录输入错误密码

![image-20211209185834091](C:%5CUsers%5CHP%5CAppData%5CRoaming%5CTypora%5Ctypora-user-images%5Cimage-20211209185834091.png)



登录输入框没有输入内容

![image-20211209185909375](C:%5CUsers%5CHP%5CAppData%5CRoaming%5CTypora%5Ctypora-user-images%5Cimage-20211209185909375.png)



注册界面



![image-20211209190024820](C:%5CUsers%5CHP%5CAppData%5CRoaming%5CTypora%5Ctypora-user-images%5Cimage-20211209190024820.png)



注册已注册用户

![image-20211209190148774](C:%5CUsers%5CHP%5CAppData%5CRoaming%5CTypora%5Ctypora-user-images%5Cimage-20211209190148774.png)





注册成功

![image-20211209190232810](C:%5CUsers%5CHP%5CAppData%5CRoaming%5CTypora%5Ctypora-user-images%5Cimage-20211209190232810.png)



登录成功

![image-20211209190307428](C:%5CUsers%5CHP%5CAppData%5CRoaming%5CTypora%5Ctypora-user-images%5Cimage-20211209190307428.png)



好友列表

![image-20211209190542479](C:%5CUsers%5CHP%5CAppData%5CRoaming%5CTypora%5Ctypora-user-images%5Cimage-20211209190542479.png)



私聊

![image-20211209190730368](C:%5CUsers%5CHP%5CAppData%5CRoaming%5CTypora%5Ctypora-user-images%5Cimage-20211209190730368.png)



发送文件

![image-20211209190933567](C:%5CUsers%5CHP%5CAppData%5CRoaming%5CTypora%5Ctypora-user-images%5Cimage-20211209190933567.png)





![image-20211209191021053](C:%5CUsers%5CHP%5CAppData%5CRoaming%5CTypora%5Ctypora-user-images%5Cimage-20211209191021053.png)

保存

![image-20211209191117499](C:%5CUsers%5CHP%5CAppData%5CRoaming%5CTypora%5Ctypora-user-images%5Cimage-20211209191117499.png)

保存到桌面



![image-20211209191205503](C:%5CUsers%5CHP%5CAppData%5CRoaming%5CTypora%5Ctypora-user-images%5Cimage-20211209191205503.png)

#### 代码





