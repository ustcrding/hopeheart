# hope_project

#### 介绍
后端框架：JFinal  
数据库：Mysql  
连接池：Druid  
序列化：Fastjson  
web容器：Jfinal-Undertow

#### 如何进行开发
1、代码pull到本地  
2、导入maven项目  
3、右键Run As-Java Application运行DemoConfig.java  
4、访问http://localhost

#### 开发规范
1、数据库表、字段名单词之间使用下划线隔开  
2、接口成功返回：{"code":"0","data":{...}}，失败返回：{"code":"1","msg":"..."}  