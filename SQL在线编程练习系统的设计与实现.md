## SQL在线编程练习系统的设计与实现

前端：沿用zkq学姐改进后设计的SQL练习系统，她的硕士毕业设计部分。

后端：原来的数据库考试系统架构从node.js变为java，原来创建的SQL运行环境在远端服务器的docker中，毕设中可以改为在宿主机的docker中，或者就在宿主机的数据库中。原先一道大题对应一个schema，现在也可以采用此策略。在宿主机中创建与大题数量一致的schema。由于限制了学生操作，只能查询不能更改，所以可以保证表结构和表内容的稳定。

### 容器化技术

如果要使用容器化技术，java需要用docker-java来远程操纵容器，docker remote API，若是node，则需要dockerode。

需要考虑容器的生命周期问题

### 数据库实例表结构变化

schemaName -> driverName -> DockerServerID -> ContainerIndex -> sqlDatabase

this.map 的结构  <schema,<mysql,<dockerYin,<sqlDatabase1,sqlDatabase2>>>>

itemOfMap 的结构 <mysql,<dockerYin,<sqlDatabase1,sqlDatabase2>>> inRoot, inSchema

dockerServer -> driver -> sqlDatabase -> schemaName



## 未来方向

SQL与自然语言相关的解释的工作

Towards Enhancing Database Education-Natural Language Generation Meets Query Execution Plans

Let the Database Talk Back Natural Language Explanations for SQL

A Natural Language Interface

ValueNet: A Natural Language-to-SQL System that Learns from Database Information

数据库类型及题目类型（查询性能相关）的扩展，

Database Benchmarking for Supporting Real-Time Interactive Querying of Large Data.



## 2022.7.17

- [x] 将启动容器中的mysql镜像和创建用户过程，改成Spring异步任务，加快环境构建速度
- [ ] Spring+MQ处理提交高峰，目前消息中间件选型待定
- [ ] 将每一个batch的创建、执行、获取结果的过程改为异步任务
- [x] 引入redis对频繁使用的标准结果集进行多级缓存
- [ ] 执行用户语句和从缓存中获取标准结果集可以启用异步，同时进行
- [ ] 在远程服务器上配置docker，并使用certPath进行加密连接
- [ ] 微服务的拆分
- [ ] sql任务执行完毕后的处理，以支持非查询语句
- [ ] 自动获取远程服务器上已占用的端口列表
- [ ] 支持mysql的非查询语句
- [ ] 支持其他数据库，优先考虑国产数据库（企业合作）
- [ ] 可视化、自动化容器的生命周期管理，作为管理员的功能

## 2022.7.31

异步调用只适用于对主线程没有影响的分支操作，但启动容器镜像的结果影响着后续连接数据库创建用户的操作。

## 2022.8.18

 未来引入消息队列之后，需要用异步任务创建batch并执行。

## 2022.8.19

已利用redis对于小题的标准结果集进行了粗糙的缓存设计，后续需要设计缓存内容的失效时间或者替换策略，亦或者设计多级缓存。

## 2022.9.28

丹丹姐原来的标签系统需要人接班

乔涤生原来的测试数据生成部分需要人接班

## 2022.10.11

新增任务：

- [ ] 基于openGauss的JDBC驱动，将其接入练习系统后台
- [ ] Oceanbase数据库大赛：基于miniob实现简单的数据库系统功能

项目进展汇报：

何文兵：将启动容器中的mysql镜像和创建用户过程，改成Spring异步任务，加快环境构建速度；配置了redis对频繁使用的标准结果集进行初步缓存。完成了MiniOB系统配置，实现drop-table功能。

沈祎辰：将SQL静态结构分析评分系统接入了练习系统。MiniOB系统入门。

徐浪轩：学习了解react和nodejs技术为后续工作做准备，初步掌握docker的使用及MiniOB系统入门。

王馨逸：熟悉了sqlplatform后端代码逻辑和数据库结构，正在向该项目添加vue框架。

王宇博：负责sql练习系统的后端基础功能完善。这段时间主要先看了看系统目前的后端代码，对于系统已有的功能有了大概了解，思考了系统还可以进一步完善的功能点，例如做题进度、错误提交的统计、题目分类练习等。

祁佳怡：学习typescript语法和nodejs开发，配置sqlexam项目环境。

## 2022.11.1

何文兵、沈祎辰、徐浪轩：参加oceanbase数据库大赛，目前180分，排名71，复赛线目前为200分

项目进展汇报：

王馨逸：完成了系统的登录注册模块，正在开发主页和个人中心的内容，包括题目列表和做题记录等功能

王宇博：向系统中添加了控制台日志输出，修改简化了部分实体类代码，计划新增一些接口。例如获取题目列表，获取用户做题记录等，并扩展数据库表，添加例如标签等题目相关信息的记录，以支持后续功能扩展

祁佳怡：继续学习node.js开发相关内容，了解sqlexam项目框架和具体实现。
