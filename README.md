# VueEShop

## 简介

小肥花商城是我从公司项目代码里总结出来的一个B2C商城网站, 项目原代码太过庞大, 现在集中抽取了一些关键的Vue需求和完整的SpringBoot 代码, 数据库采用网上搜集而来的公开商品信息, 暂时先开源前台网站. 

采用的技术栈为Vue2 + SpringBoot2 + MyBatis  + Mysql, 供Vue开发者一起讨论学习. 现有功能包括门户网站, 商品展示, 商品细节, 购物车, 简单的订单流程, 会员中心, 帮助中心, 友情链接. 

文档和教程会不日进行更新. 



## 功能列表

### 商城首页

![](http://static.yunshichen.com/images/Selection_235.png)

### 楼层商品(列表商品)

![](http://static.yunshichen.com/images/Selection_236.png)

### 注册登录

![](http://static.yunshichen.com/images/Selection_238.png)

### 商品细节

![](http://static.yunshichen.com/images/Selection_239.png)

### 购物车

![](http://static.yunshichen.com/images/Selection_241.png)

### 订单结算

![](http://static.yunshichen.com/images/Selection_240.png)

### 用户中心

![](http://static.yunshichen.com/images/Selection_243.png)

### 帮助文档

![](http://static.yunshichen.com/images/Selection_244.png)



## 下载运行

### 运行SpringBoot 工程

用eclipse导入springboot的两个工程, 然后搜索 MallWebApp , 运行这个类

### 运行Vue工程

进入vue / mall-web 目录, 依次运行

```ba
npm install 
npm run dev
```

然后就可以看到浏览器打开了商城首页

### 登录用户

如果你想体验登录的功能, 已经有一个预备好的用户供你使用. 注册名和密码分别如下:

weite2017  / 123456

如果你想注册新用户, 由于注册模块使用了阿里云的验证码功能, 需要你将配置文件里的阿里云验证码改成你自己的配置. 如果你没有购买阿里云验证码, 那你就暂时无法体验完整的注册功能. 

## TODO

现在只上了一个前台网站, 能比较顺畅地跑起来, 很多预想中的功能都没加上, 希望后续可以慢慢填坑. 

## 讨论组和商务合作

### 讨论组请加qq讨论组:  654110829

### 商务合作

邮件: imyunshi@163.com

微信: weite2017    (微信名字: 小肥花叔)



## 感谢

### Vue作者尤雨溪先生

自从Vue横空出世之后, 我所有项目的前端开发都采用了Vue, 带给我极大的效率提高, 非常感谢. 

### Vue多页面项目启发

Vue是单页面结构, 需要采用一定的技巧才能将之变成多页面工程, 感谢此作者造好了轮子: https://github.com/bluefox1688/vue-cli-multi-page

