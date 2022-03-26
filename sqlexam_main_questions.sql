INSERT INTO sqlexam.main_questions (id, teacher_id, title, `desc`, db_path, created_at, updated_at, file_name, total_difficulty, sub_count) VALUES (1, 'fc05133a-9c8e-4199-be7d-9c863df1fa33', '公司职员', '设有一个公司内部信息管理数据库，表结构如下：

有`employees`表，
```
+--------------+---------+
| Column Name  | Type    |
+--------------+---------+
|  eno         | int     |
|  ename       | varchar |
|  salary      | int     |
|  dno         | int     |
+--------------+---------+
eno为该表主键。
该表包含员工的工号，姓名，工资，部门编号等信息
```

有`projects`表，
```
+--------------+---------+
| Column Name  | Type    |
+--------------+---------+
|  pno         | int     |
|  pname       | varchar |
|  city        | varchar |
|  dno         | int     |
+--------------+---------+
pno为该表主键。
该表包含项目的项目编号，项目名称，所在城市，负责部门编号等信息。
```

有`works`表，
```
+--------------+---------+
| Column Name  | Type    |
+--------------+---------+
|  eno         | int     |
|  pno         | int     |
|  hours       | int     |
+--------------+---------+
(eno,pno)为该表主键。
该表包含员工的工号，参与的项目，在项目上工作的时间等信息
```

有`relations`表，
```
+--------------+---------+
| Column Name  | Type    |
+--------------+---------+
|  eno         | int     |
|  rname       | varchar |
|  sex         | varchar |
+--------------+---------+
(eno,name)为该表主键。
该表包含员工的工号，家属姓名，家属性别等信息
```

表的示例如下：

`employees`表：
```
+-----+-------+--------+-----+
| eno | ename | salary | dno |
+-----+-------+--------+-----|
|  1  |  Tom  | 128500 |  1  |
|  2  | Jerry | 184300 |  2  |
+-----+-------+--------+-----+
```
`projects`表：
```
+-----+-------+----------+-----+
| pno | pname |   city   | dno |
+-----+-------+----------+-----+
|  1  | Java  | Beijing  |  1  |
|  2  | C++   | Shanghai |  2  |
+-----+-------+----------+-----+
```
`works`表：
```
+-----+-------+-------+
| eno | pname | hours |
+-----+-------+-------+
|  1  | Java  | 400   |
|  2  | C++   | 650   |
+-----+-------+-------+
```
`relations`表：
```
+-----+-------+-----+
| eno | rname | sex |
+-----+-------+-----+
|  1  | Bob   | 男  |
|  2  | Alice | 女  |
+-----+-------+-----+
```
', 'examDataFiles/upload_43ce1f7120ff71473174188ce1cc5bff.sql', '2020-11-25 12:48:02', '2021-11-13 19:07:43', 'tt1.sql', 5, 4);
INSERT INTO sqlexam.main_questions (id, teacher_id, title, `desc`, db_path, created_at, updated_at, file_name, total_difficulty, sub_count) VALUES (2, 'fc05133a-9c8e-4199-be7d-9c863df1fa33', '船只租赁', '设有一个船员租赁船只系统，表结构如下：

有`sailors`表，
```
+--------------+---------+
| Column Name  | Type    |
+--------------+---------+
|  sid         | int     |
|  sname       | varchar |
|  rating      | int     |
|  age         | int     |
+--------------+---------+
sid为该表主键。
该表包含船员的编号，姓名，等级和年龄
```

有`boats`表，
```
+--------------+---------+
| Column Name  | Type    |
+--------------+---------+
|  bid         | int     |
|  bname       | varchar |
|  color       | varchar |
+--------------+---------+
bid为该表主键。
该表包含船只编号，船只名称和船只颜色
```


有`reserves`表，
```
+---------------+---------+
| Column Name   | Type    |
+---------------+---------+
|  sid          | int     |
|  bid          | int     |
|  reserve_date | date    |
+---------------+---------+
(sid,bid)为该表主键。
该表包含船员编号，船员预定的船只编号，船员预定船只的日期
```

表的示例如下：

`sailors`表：
```
+-----+-------+--------+-----+
| sid | sname | rating | age |
+-----+-------+--------+-----+
| 1   | Tom   |  5     | 26  |
| 2   | Rodje |  7     | 34  |
+-----+-------+--------+-----+
```
`boats`表：
```
+-----+----------------+-------+
| bid |     bname      | color |
+-----+----------------+-------+
| 1   |   BlackPearl   | BLACK |
| 2   | FlyingDutchman | YELLOW|
+-----+----------------+-------+
```

`reserves`表：
```
+-----+-----+------------+
| sid | bid |reserve_date|
+-----+-----+------------+
| 1   | 1   | 2020-10-09 |
| 2   | 2   | 2020-11-11 |
+-----+-----+------------+
```
', 'examDataFiles/upload_05ad4609a85bdd74bfaeae9e010ff971.sql', '2020-11-25 13:11:05', '2021-04-13 15:59:04', 'tt2.sql', 6, 4);
INSERT INTO sqlexam.main_questions (id, teacher_id, title, `desc`, db_path, created_at, updated_at, file_name, total_difficulty, sub_count) VALUES (3, 'fc05133a-9c8e-4199-be7d-9c863df1fa33', '顾客与商品', '对于顾客购买产品，系统中有以下表结构：

表: `customers`
```
+---------------+---------+
| Column Name   | Type    |
+---------------+---------+
| customer_id   | int     |
| name          | varchar |
+---------------+---------+
customer_id 是该表主键。
该表包含消费者的id和姓名.
```

表: `orders`
```
+---------------+---------+
| Column Name   | Type    |
+---------------+---------+
| order_id      | int     |
| order_date    | date    |
| customer_id   | int     |
| product_id    | int     |
+---------------+---------+
order_id 是该表主键。
该表包含消费者产生的订单编号，订单日期，顾客id和商品id。
不会有商品被相同的用户在一天内下单超过一次。
```

表: `products`
```
+---------------+---------+
| Column Name   | Type    |
+---------------+---------+
| product_id    | int     |
| product_name  | varchar |
| price         | int     |
+---------------+---------+
product_id 是该表主键。
该表包含所有商品id，商品名称和商品价格.
```

表的示例如下:

`customers`
```
+-------------+-----------+
| customer_id | name      |
+-------------+-----------+
| 1           | Winston   |
| 2           | Jonathan  |
| 3           | Annabelle |
| 4           | Marwan    |
| 5           | Khaled    |
+-------------+-----------+
```
`orders`
```
+----------+------------+-------------+------------+
| order_id | order_date | customer_id | product_id |
+----------+------------+-------------+------------+
| 1        | 2020-07-31 | 1           | 1          |
| 2        | 2020-07-30 | 2           | 2          |
| 3        | 2020-08-29 | 3           | 3          |
| 4        | 2020-07-29 | 4           | 1          |
| 5        | 2020-06-10 | 1           | 2          |
| 6        | 2020-08-01 | 2           | 1          |
| 7        | 2020-08-01 | 3           | 1          |
| 8        | 2020-08-03 | 1           | 2          |
| 9        | 2020-08-07 | 2           | 3          |
| 10       | 2020-07-15 | 1           | 2          |
+----------+------------+-------------+------------+
```

`products`
```
+------------+--------------+-------+
| product_id | product_name | price |
+------------+--------------+-------+
| 1          | keyboard     | 120   |
| 2          | mouse        | 80    |
| 3          | screen       | 600   |
| 4          | hard disk    | 450   |
+------------+--------------+-------+
```', 'examDataFiles/upload_71966b752a7434908be2df37451b05ac.sql', '2020-11-25 14:57:09', '2021-04-27 11:48:27', 'title3.sql', 7, 4);
INSERT INTO sqlexam.main_questions (id, teacher_id, title, `desc`, db_path, created_at, updated_at, file_name, total_difficulty, sub_count) VALUES (4, 'fc05133a-9c8e-4199-be7d-9c863df1fa33', '选手与比赛', '在一场赛事中，选手经过分组后参加比赛，有以下表结构，
`players `玩家表
```
+-------------+-------+
| Column Name | Type  |
+-------------+-------+
| player_id   | int   |
| group_id    | int   |
+-------------+-------+
player_id是此表的主键。
该表包含选手id以及所在的组别
```
`matches` 赛事表
```
+---------------+---------+
| Column Name   | Type    |
+---------------+---------+
| match_id      | int     |
| first_player  | int     |
| second_player | int     | 
| first_score   | int     |
| second_score  | int     |
| group_id      | int     |
+---------------+---------+
match_id 是此表的主键。
该表包含一场比赛的比赛id，参与比赛的第一位选手和第二位选手，第一位选手的分数和第二位选手的分数以及两位选手所处的组别。
```

表的示例如下：

`players` 表:
```
+-----------+------------+
| player_id | group_id   |
+-----------+------------+
| 15        | 1          |
| 25        | 1          |
| 30        | 1          |
| 45        | 1          |
| 10        | 2          |
| 35        | 2          |
| 50        | 2          |
| 20        | 3          |
| 40        | 3          |
+-----------+------------+
```

`matches` 表:
```
+------------+--------------+---------------+-------------+--------------+----------+
| match_id   | first_player | second_player | first_score | second_score | group_id |
+------------+--------------+---------------+-------------+--------------+----------+
| 1          | 15           | 45            | 3           | 0            | 1        |
| 2          | 30           | 25            | 1           | 2            | 1        |
| 3          | 30           | 15            | 2           | 0            | 1        |
| 4          | 40           | 20            | 5           | 2            | 3        |
| 5          | 35           | 50            | 1           | 1            | 2        |
+------------+--------------+---------------+-------------+--------------+----------|
```', 'examDataFiles/upload_76d9037c6ac2cc5a85f5d37f1882ea48.sql', '2020-11-25 15:26:47', '2021-03-30 22:29:39', 'tt4.sql', 8, 4);
INSERT INTO sqlexam.main_questions (id, teacher_id, title, `desc`, db_path, created_at, updated_at, file_name, total_difficulty, sub_count) VALUES (5, 'fc05133a-9c8e-4199-be7d-9c863df1fa33', '顾客与商品2', '一位用户，既可以作为卖家也可以作为买家参与一场交易，以下为相关的表结构，

表: `users`
```
+----------------+---------+
| Column Name    | Type    |
+----------------+---------+ 
| user_id        | int     |
| join_date      | date    |
| favorite_brand | varchar |
+----------------+---------+
user_id 是该表的主键
表中包含一位某网站用户的个人id，注册时间和最喜欢的品牌。
```

表: `orders`
```
---------------+---------+
| Column Name   | Type    |
+---------------+---------+
| order_id      | int     |
| order_date    | date    |
| item_id       | int     |
| buyer_id      | int     |
| seller_id     | int     |
+---------------+---------+
order_id 是该表的主键
该表包含订单的id，日期，商品id，买方id和卖方id
```

表: `items`
```
+---------------+---------+
| Column Name   | Type    |
+---------------+---------+
| item_id       | int     |
| item_brand    | varchar |
+---------------+---------+
item_id 是该表的主键
该表包含商品id和商品品牌
```

表的示例如下：

`users` 表:
```
+---------+------------+----------------+
| user_id | join_date  | favorite_brand |
+---------+------------+----------------+
| 1       | 2019-01-01 | Lenovo         |
| 2       | 2019-02-09 | Samsung        |
| 3       | 2019-01-19 | LG             |
| 4       | 2019-05-21 | HP             |
+---------+------------+----------------+
```

`orders` 表:
```
+----------+------------+---------+----------+-----------+
| order_id | order_date | item_id | buyer_id | seller_id |
+----------+------------+---------+----------+-----------+
| 1        | 2019-08-01 | 4       | 1        | 2         |
| 2        | 2019-08-02 | 2       | 1        | 3         |
| 3        | 2019-08-03 | 3       | 2        | 3         |
| 4        | 2019-08-04 | 1       | 4        | 2         |
| 5        | 2019-08-04 | 1       | 3        | 4         |
| 6        | 2019-08-05 | 2       | 2        | 4         |
+----------+------------+---------+----------+-----------+
```

`items` 表:
```
+---------+------------+
| item_id | item_brand |
+---------+------------+
| 1       | Samsung    |
| 2       | Lenovo     |
| 3       | LG         |
| 4       | HP         |
+---------+------------+
```
', 'examDataFiles/upload_f875a1fd9a382b3ee6aee0bb476b58c6.sql', '2020-11-25 15:40:42', '2021-04-14 00:12:51', 'tt5.sql', 8, 4);
INSERT INTO sqlexam.main_questions (id, teacher_id, title, `desc`, db_path, created_at, updated_at, file_name, total_difficulty, sub_count) VALUES (6, 'fc05133a-9c8e-4199-be7d-9c863df1fa33', '好友申请', '在 Facebook 或者 Twitter 这样的社交应用中，人们经常会发好友申请也会收到其他人的好友申请。现在给如下两个表：

表: `friend_requests`
```
+----------------+---------+
| Column Name    | Type    |
+----------------+---------+ 
| sender_id      | int     |
| send_to_id     | int     |
| request_date   | date    |
+----------------+---------+
(sender_id,send_to_id,request_date) 是该表的主键
一个人可能会向另一个人发送多条申请
```

表: `accepted_requests`
```
+----------------+---------+
| Column Name    | Type    |
+----------------+---------+ 
| requester_id   | int     |
| accepter__id   | int     |
| accept_date    | date    |
+----------------+---------+
(request_id,accepter_id,accept_date) 是该表的主键
一个人可能会多次同意其他人的申请
```

表的示例如下：

`friend_requests`表：
```
| sender_id | send_to_id |request_date|
|-----------|------------|------------|
| 1         | 2          | 2016_06-01 |
| 1         | 3          | 2016_06-01 |
| 1         | 4          | 2016_06-01 |
| 2         | 3          | 2016_06-02 |
| 3         | 4          | 2016-06-09 |
```


`accepted_requests`表： 

```
| requester_id | accepter_id |accept_date |
|--------------|-------------|------------|
| 1            | 2           | 2016_06-03 |
| 1            | 3           | 2016-06-08 |
| 2            | 3           | 2016-06-08 |
| 3            | 4           | 2016-06-09 |
| 3            | 4           | 2016-06-10 |
```

', 'examDataFiles/upload_b283494b294bc356f2ea79a5128e02fb.sql', '2020-11-25 16:29:09', '2021-04-10 21:29:14', 'title6.sql', 8, 4);
INSERT INTO sqlexam.main_questions (id, teacher_id, title, `desc`, db_path, created_at, updated_at, file_name, total_difficulty, sub_count) VALUES (7, 'fc05133a-9c8e-4199-be7d-9c863df1fa33', '阅读记录', '用户经常在网站上阅读文章，以下为记录阅读记录的表，

`views` 表：
```
+---------------+---------+
| Column Name   | Type    |
+---------------+---------+
| article_id    | int     |
| author_id     | int     |
| viewer_id     | int     |
| view_date     | date    |
+---------------+---------+
此表无主键，因此可能会存在重复行。
此表包含读者的id，阅读的文章id，文章作者的id和阅读的日期。
请注意，同一人的 author_id 和 viewer_id 是相同的。
```

表的示例如下：

`views` 表：
```
+------------+-----------+-----------+------------+
| article_id | author_id | viewer_id | view_date  |
+------------+-----------+-----------+------------+
| 1          | 3         | 5         | 2019-08-01 |
| 1          | 3         | 6         | 2019-08-02 |
| 2          | 7         | 7         | 2019-08-01 |
| 2          | 7         | 6         | 2019-08-02 |
| 4          | 7         | 1         | 2019-07-22 |
| 3          | 4         | 4         | 2019-07-21 |
| 3          | 4         | 4         | 2019-07-21 |
+------------+-----------+-----------+------------+
```', 'examDataFiles/upload_b6c8ef6c90114b2d9c0d5df68bbf0479.sql', '2020-11-25 16:42:10', '2021-04-07 11:16:15', 'title7.sql', 7, 4);
INSERT INTO sqlexam.main_questions (id, teacher_id, title, `desc`, db_path, created_at, updated_at, file_name, total_difficulty, sub_count) VALUES (8, 'fc05133a-9c8e-4199-be7d-9c863df1fa33', '网吧登录', '用户在网吧使用机子时，会产生如下表，

`logins` 表：
```
+---------------+---------+
| Column Name   | Type    |
+---------------+---------+
| id            | int     |
| user_id       | int     |
| client_id     | int     |
| login_date    | date    |
+---------------+---------+
id是该表的主键。
该表包含了该次登录的id号，登录用户的id，登录设备的id以及登录日期。
```

表的示例如下：

`logins` 表：
```
+--------+-----------+-----------+------------+
| id     |  user_id  | client_id | login_date |
+--------+-----------+-----------+------------+
| 1      | 1         | 2         | 2019-08-01 |
| 2      | 2         | 1         | 2019-08-02 |
| 3      | 3         | 3         | 2019-08-01 |
| 4      | 1         | 1         | 2019-08-02 |
| 5      | 5         | 2         | 2019-07-22 |
| 6      | 4         | 2         | 2019-07-21 |
| 7      | 4         | 4         | 2019-07-21 |
+--------+-----------+-----------+------------+
```', 'examDataFiles/upload_1a1059a102b4f59752d0fe0e0f972a84.sql', '2020-11-25 16:52:34', '2021-04-12 11:04:12', 'tt8.sql', 9, 4);
INSERT INTO sqlexam.main_questions (id, teacher_id, title, `desc`, db_path, created_at, updated_at, file_name, total_difficulty, sub_count) VALUES (9, 'fc05133a-9c8e-4199-be7d-9c863df1fa33', '岗位成绩', '一个公司的各个岗位，组织了考试让人参与，得到的表如下，

`grades` 表：

```
+---------------+---------+
| Column Name   | Type    |
+---------------+---------+
|  user_id      | int     |
|  job          | varchar |
|  score        | int     |
+---------------+---------+
user_id是该表的主键。
该表包含人员id，参与的岗位名称以及成绩
```

表的示例如下：

`grades` 表：
```
+-----------+-----------+------------+
|  user_id  |   job     |  score     |
+-----------+-----------+------------+
|  1        |   C++     |  99        |
|  2        |   C++     |  84        |
|  3        |   Java    |  74        |
|  4        |   Python  |  85        |
|  5        |   Java    |  94        |
+-----------+-----------+------------+
```', 'examDataFiles/upload_1fe0d9609c0ad2305155981062c73072.sql', '2020-11-25 18:50:52', '2021-04-07 00:07:37', 'tt9.sql', 7, 4);
INSERT INTO sqlexam.main_questions (id, teacher_id, title, `desc`, db_path, created_at, updated_at, file_name, total_difficulty, sub_count) VALUES (10, 'fc05133a-9c8e-4199-be7d-9c863df1fa33', '公司职员2', '公司员工信息和部门信息包含在以下两张表中，

有`employees`表，
```
+---------------+---------+
| Column Name   | Type    |
+---------------+---------+
| id            | int     |
| name          | varchar |
| salary        | int     |
| department_id | int     |
+---------------+---------+
eno为该表主键。
该表包含员工的工号，姓名，工资，部门编号等信息
```

有`departments`表，
```
+-----------------+---------+
| Column Name     | Type    |
+-----------------+---------+
| department_id   | int     |
| departmant_name | varchar |
+-----------------+---------+
department_id为该表主键。
该表包含部门编号，部门名称等信息。
```

表的示例如下：

`employees` 表
```
+----+-------+--------+--------------+
| id | name  | salary | department_id|
+----+-------+--------+--------------+
| 1  | Joe   | 84000  | 1            |
| 2  | Henry | 80000  | 2            |
| 3  | Sam   | 60000  | 2            |
| 4  | Max   | 90000  | 1            |
| 5  | Janet | 69000  | 1            |
| 6  | Randy | 85000  | 1            |
| 7  | Will  | 70000  | 1            |
+----+-------+--------+--------------+
```

`departments` 表
```
+---------------+------------------+
| department_id | department_name  |
+---------------+------------------+
|       1       |       IT         |
|       2       |      Sales       |
+---------------+------------------+
```
', 'examDataFiles/upload_e0015c5a7cb2bc9ac8f200623c5485ea.sql', '2020-11-25 19:10:23', '2021-03-30 23:07:16', 'tt10.sql', 9, 4);
INSERT INTO sqlexam.main_questions (id, teacher_id, title, `desc`, db_path, created_at, updated_at, file_name, total_difficulty, sub_count) VALUES (11, 'fc05133a-9c8e-4199-be7d-9c863df1fa33', '参加活动情景', '表: friends。
id 是朋友的 id 和该表的主键。name 是朋友的名字。activity 是朋友参加的活动的名字

```
friends 表:
+------+--------------+---------------+
| id   | name         | activity      |
+------+--------------+---------------+
| 1    | Jonathan D   | eating        |
| 2    | Jade W       | singing       |
| 3    | Victor J     | singing       |
| 4    | Elvis Q      | singing       |
| 5    | Daniel A     | horse riding  |
| 6    | Bob B        | horse riding  |
+------+--------------+---------------+
```



表: activities。id 是该表的主键。name 是活动的名字。

```
activities 表:
+------------+--------------+-------------+-------------+
| ID         | activity     | startDate   | endDate     |
+------------+--------------+-------------+-------------+
| 1          | eating       | 2020-02-12  | 2020-02-20  |
| 2          | singing      | 2020-02-21  | 2020-02-23  |
| 3          | horse riding | 2020-02-24  | 2020-02-28  |
+------------+--------------+-------------+-------------+

```
', 'examDataFiles/upload_7799bab2300280fcf98079020f1984b7.sql', '2020-11-26 11:21:22', '2021-04-14 00:16:17', 'useractivityscenario.sql', 7, 4);
INSERT INTO sqlexam.main_questions (id, teacher_id, title, `desc`, db_path, created_at, updated_at, file_name, total_difficulty, sub_count) VALUES (12, 'fc05133a-9c8e-4199-be7d-9c863df1fa33', '电影相关情景', '电影相关情景：



表：users。user_id 是表的主键。

```
+-------------+--------------+
| user_id     |  name        |
+-------------+--------------+
| 1           | Daniel       |
| 2           | Monica       |
| 3           | Maria        |
+-------------+--------------+
```





表：movies。movie_id 是这个表的主键。title 是电影的名字。

```
+-------------+--------------+
| movie_id    |  title       |
+-------------+--------------+
| 1           | Avengers     |
| 2           | Frozen 2     |
| 3           | Joker        |
+-------------+--------------+
```





表：movie_rating。(movie_id, user_id) 是这个表的主键。这个表包含用户在其评论中对电影的评分 rating 。
created_at 是用户的点评日期。 

```
+-------------+--------------+--------------+-------------+
| movie_id    | user_id      | rating       | created_at  |
+-------------+--------------+--------------+-------------+
| 1           | 1            | 3            | 2020-01-12  |
| 1           | 2            | 4            | 2020-02-11  |
| 1           | 3            | 2            | 2020-02-12  |
| 2           | 1            | 5            | 2020-02-17  | 
| 2           | 3            | 2            | 2020-03-01  |
| 3           | 1            | 3            | 2020-02-22  | 
+-------------+--------------+--------------+-------------+
```

', 'examDataFiles/upload_6438b3862666484781e9bc5505196afe.sql', '2020-11-26 11:32:57', '2021-04-12 10:35:12', 'movierelatedscenario.sql', 11, 5);
INSERT INTO sqlexam.main_questions (id, teacher_id, title, `desc`, db_path, created_at, updated_at, file_name, total_difficulty, sub_count) VALUES (13, 'fc05133a-9c8e-4199-be7d-9c863df1fa33', '发票相关情景', '发票相关场景



顾客表：customers。customer_id 是这张表的主键。此表的每一行包含了某在线商店顾客的姓名和电子邮件。

```
customers table:
+-------------+---------------+------------------------+
| customer_id | customer_name | email                  |
+-------------+---------------+------------------------+
| 1           | Alice         | alice@smail.nju.edu.cn |
| 2           | Alex          | bob@smail.nju.edu.cn   |
| 13          | Bob           | john@smail.nju.edu.cn  |
| 6           | John          | alex@smail.nju.edu.cn  |
+-------------+---------------+------------------------+
```



联系方式表：contacts。(user_id, contact_email) 是这张表的主键。此表的每一行表示编号为 user_id 的顾客的某位联系人的姓名和电子邮件。此表包含每位顾客的联系人信息，但顾客的联系人不一定存在于顾客表中。

```
contacts table:
+-------------+--------------+------------------------+
| user_id     | contact_name | contact_email          |
+-------------+--------------+------------------------+
| 1           | Jal          | jal@smail.nju.edu.cn   |
| 2           | Omar         | omar@smail.nju.edu.cn  |
| 2           | Meir         | meir@smail.nju.edu.cn  |
| 6           | Alice        | alice@smail.nju.edu.cn |
+-------------+--------------+------------------------+

```



发票表：invoices。invoice_id 是这张表的主键。此表的每一行分别表示编号为 user_id 的顾客拥有有一张编号为 invoice_id、价格为 price 的发票。

```
invoices table:
+------------+-------+---------+
| invoice_id | price | user_id |
+------------+-------+---------+
| 77         | 100   | 1       |
| 88         | 200   | 1       |
| 99         | 300   | 2       |
| 44         | 60    | 6       |
+------------+-------+---------+
```

注意：小题中可能涉及到的一些字段解释：
customer_name：与发票相关的顾客名称。
price：发票的价格。
contacts_cnt：该顾客的联系人数量。
trusted_contacts_cnt：可信联系人的数量：既是该顾客的联系人又是商店顾客的联系人数量（即：可信联系人的电子邮件存在于客户表中）。

', 'examDataFiles/upload_4c5a71e0dfb67c98128d189502457a91.sql', '2020-11-26 11:38:29', '2021-04-12 10:56:02', 'invoicerelatedscenarios.sql', 10, 5);
INSERT INTO sqlexam.main_questions (id, teacher_id, title, `desc`, db_path, created_at, updated_at, file_name, total_difficulty, sub_count) VALUES (14, 'fc05133a-9c8e-4199-be7d-9c863df1fa33', '锦标赛情景', '训练赛情景：

players 训练赛玩家表。玩家 ID 是此表的主键。此表的每一行表示每个玩家的组。注：每个组中可能既有男性又有女性。

```
players 表:
+-----------+------------+------------+
| player_id | player_name|  group_id  |
+-----------+------------+------------+
| 15        |   Joe      |      2     |
| 25        |  Alice     |      1     |   
| 30        |  Bajrang   |      1     |
| 45        |  Khali     |      1     |
| 10        |  Slaman    |      3     |
| 35        |  Aron      |      2     |
| 50        |  Priya     |      2     |
| 20        |   Jose     |      3     |
+-----------+------------+------------+

```



matches 训练赛赛事表。match_id 是此表的主键。
每一行是一场比赛的记录，第一名和第二名球员包含每场比赛的球员 ID。
第一个玩家和第二个玩家的分数分别包含第一个玩家和第二个玩家的分数。
你可以假设，在每一场比赛中，球员都属于同一组。

```
matches 表:
+------------+--------------+---------------+-------------+--------------+
| match_id   | first_player | second_player | first_score | second_score |
+------------+--------------+---------------+-------------+--------------+
| 1          | 30           | 45            | 3           | 0            |
| 2          | 50           | 15            | 1           | 2            |
| 3          | 30           | 25            | 2           | 0            |
| 4          | 35           | 15            | 1           | 1            |
+------------+--------------+---------------+-------------+--------------+
```



表: scores。(gender, day)是该表的主键。该表独立于Matches表。是另一场模拟训练赛的比赛分数。
该表的每一行表示一个名叫 (player_name) 性别为 (gender) 的参赛者在某一天获得了 (score_points) 的分数
如果参赛者是女性，那么 gender 列为 ''F''，如果参赛者是男性，那么 gender 列为 ''M''

```
scores表:
+-------------+--------+------------+--------------+
| player_name | gender | day        | score_points |
+-------------+--------+------------+--------------+
| Aron        | F      | 2020-01-01 | 17           |
| Alice       | F      | 2020-01-07 | 23           |
| Bajrang     | M      | 2020-01-07 | 7            |
| Khali       | M      | 2019-12-25 | 11           |
| Slaman      | M      | 2019-12-30 | 13           |
| Joe         | M      | 2019-12-31 | 3            |
| Jose        | M      | 2019-12-18 | 2            |
| Priya       | F      | 2019-12-31 | 23           |
+-------------+--------+------------+--------------+
```

', 'examDataFiles/upload_2cd196144f39eb1dbd5a8e70635bc764.sql', '2020-11-26 11:55:08', '2021-02-24 13:47:44', 'championshipscenario.sql', 10, 5);
INSERT INTO sqlexam.main_questions (id, teacher_id, title, `desc`, db_path, created_at, updated_at, file_name, total_difficulty, sub_count) VALUES (15, 'fc05133a-9c8e-4199-be7d-9c863df1fa33', '商品订单情景', '### 商品订单信息场景：



表：customers。customer_id 是该表主键，该表包含消费者的信息。

```html
+-------------+-----------+
| customer_id | name      |
+-------------+-----------+
| 1           | Marwan    |
| 2           | John      |
| 3           | Anna      |
| 4           | Winston   |
+-------------+-----------+

```



表：orders。order_id 是该表主键，该表包含id为customer_id的消费者的订单信息。

每一个消费者 每天一笔订单，且没有顾客会在一天内订购相同的商品 **多于一次**。

```html
orders
+----------+------------+-------------+------------+
| order_id | order_date | customer_id | product_id |  
+----------+------------+-------------+------------+
| 1        | 2020-07-31 | 1           | 1          |    
| 2        | 2020-07-30 | 2           | 2          |    
| 3        | 2020-08-29 | 3           | 3          |    
| 4        | 2020-07-29 | 4           | 1          |    
| 5        | 2020-06-10 | 1           | 2          |    
| 6        | 2020-08-01 | 2           | 1          |      
+----------+------------+-------------+------------+

```





表: products。product_id 是该表主键，该表包含所有商品的信息。

```HTML
products
+------------+--------------+-------+
| product_id | product_name | price |
+------------+--------------+-------+
| 1          | keyboard     | 600   |
| 2          |  screen      | 800   |
| 3          |  mouse       | 200   |
| 4          | hard disk    | 450   |
+------------+--------------+-------+
```



', 'examDataFiles/upload_6a7c4b7de2e8748e1c97dccc5da63ab5.sql', '2020-11-26 14:13:33', '2021-04-10 12:03:32', 'commodityorderscenario.sql', 11, 5);
INSERT INTO sqlexam.main_questions (id, teacher_id, title, `desc`, db_path, created_at, updated_at, file_name, total_difficulty, sub_count) VALUES (16, 'fc05133a-9c8e-4199-be7d-9c863df1fa33', '图书借阅情景', '图书借阅情景：

表：books。存放图书相关信息。book_id是主键，代表图书号。sort是图书分类编号。output是出版社信息。

```
+------------+--------+--------------+----------+--------------+----------+
|  book_id   | sort   |  book_name   |  writer  | output       |  price   |
+------------+--------+--------------+----------+--------------+----------+
| 112266     | TP3/12 |   FoxBase    | 李三      | 电子工业出版社 |  23.600  |
| 113388     | TR7/90 |   大学英语    | 胡玲      | 清华大学出版社 |  12.500  |
| 114455     | TR9/12 |   线性代数    | 孙业      | 北京大学出版社 |  20.800  |
| 118801     | TP4/15 |   计算机网络  | 黄力钧     | 高等教育出版社 |  21.800  |
| 118802     | TP4/15 |   计算机网络  | 黄力钧     | 高等教育出版社 |  21.800  |
| 332211     | TP5/10 |   计算机基础  | 李伟       | 高等教育出版社 |  18.000  |
| 445501     | TP3/12 |   数据库导论  | 王强       | 科学出版社    |  17.900  |
| 445502     | TP3/12 |   数据库导论  | 王强       | 科学出版社    |  17.900  |
| 445503     | TP3/12 |   数据库导论  | 王强       | 科学出版社    |  17.900  |
| 446601     | TP4/13 |   数据库基础  | 马凌云     | 人民邮电出版社 |  22.500  |
| 665544     | TS7/21 |   高等数学    | 刘明      | 高等教育出版社  | 20.000  |
+------------+--------+------ -------+----------+---------------+---------+

```




表：readers。reader_id是主键。存放读者信息。其中company是读者的系，grade是读职称，addr是读者地址。

```
+------------+--------------+---------------+------+----------+----------+
| reader_id  | company      | name          | sex  |  grade   |  addr    |
+------------+--------------+---------------+-------------+--------------+
| 111        | 信息系        | 赵正义         |  女    |   教授   | 1号楼424 |
| 112        | 财会系        | 李丽           |  女    |   副教授 | 2号楼316 |
| 113        | 经济系        | 张三           |  男    |   讲师   | 3号楼105 |
| 114        | 信息系        | 周华发         |  男    |   讲师   |1号楼316  |
| 115        | 信息系        | 王小花         |  女    |   工程师 | 1号楼224 |
| 116        | 信息系        | 李明           |  男    |   副教授 | 1号楼318 |
| 117        | 计算机系      | 李小峰         |  男    |   助教   | 1号楼214 |
| 118        | 计算机系      | 许鹏飞         |  男    |   助工   | 1号楼216 |
| 119        | 计算机系      | 刘大龙         |  男    |   教授   | 1号楼318 |
+------------+--------------+---------------+-------------+---------------+
```



表：borrows。存放借阅信息。（reader_id, book_id）是主键。

```
+----------+------------+-------------+
|reader_id | book_id    | borrow_date |
+----------+------------+-------------+
| 111      | 445503     | 2006-08-21  |
| 111      | 112266     | 2006-03-14  |
| 112      | 445501     | 2006-03-09  |
| 112      | 449901     | 2006-10-23  |
| 112      | 665544     | 2006-10-21  |
| 115      | 449902     | 2006-08-21  |
| 118      | 118801     | 2006-09-10  |
+----------+-------------+------------+
```', 'examDataFiles/upload_5efadfb8a1fb320750c816839c41bcdf.sql', '2020-11-26 14:23:50', '2021-04-10 12:02:58', 'bookborrowingscenario.sql', 9, 5);
INSERT INTO sqlexam.main_questions (id, teacher_id, title, `desc`, db_path, created_at, updated_at, file_name, total_difficulty, sub_count) VALUES (17, 'fc05133a-9c8e-4199-be7d-9c863df1fa33', '系统状态情景', '系统状态情景：系统 **每天** 运行一个任务。每个任务都独立于先前的任务。任务的状态可以是失败或是成功。

表: failed。该表主键为 fail_date。该表包含任务失败的日期.

```
failed table:
+-------------------+----------------+
| fail_date         |  event_type    |
+-------------------+----------------+
| 2018-12-28        |   reviews      |
| 2018-12-29        |   ads          |
| 2019-01-04        |   ads          |
| 2019-01-05        |   reviews      |
+-------------------+----------------+
```

表: succeeded。该表主键为 success_date。该表包含任务成功的日期.

```
succeeded table:
+-------------------+---------------+
| success_date      |   event_type   |
+-------------------+---------------+
| 2018-12-30        |   page views  |
| 2018-12-31        |   page views  |
| 2019-01-01        |   ads         |
| 2019-01-02        |   ads         |
| 2019-01-03        |   reviews     |
| 2019-01-06        |   reviews     |
+-------------------+---------------+

```



事件表：events。该表是一个独立于前两个表的表。此表的主键是 (business_id, event_type)。表中的每一行记录了某种类型的事件在某些业务中多次发生的信息。

```
events table:
+-------------+------------+------------+
| business_id | event_type | occurences |
+-------------+------------+------------+
| 1           | page views | 7          |
| 1           | reviews    | 3          |
| 2           | ads        | 11         |
| 2           | reviews    | 3          |
| 3           | page views | 12         |
+-------------+------------+------------+
```

注：和小题相关的一个解释：
如果一个业务的某个事件类型的发生次数（字段occurences）大于此事件类型在所有业务中的平均发生次数，并且该业务至少有两个这样的事件类型，那么该业务就可被看做是活跃业务。

', 'examDataFiles/upload_8694f32bd8609308b96ca63e10d89ac7.sql', '2020-11-26 14:27:58', '2021-04-14 00:38:37', 'systemstatescenario.sql', 12, 5);
INSERT INTO sqlexam.main_questions (id, teacher_id, title, `desc`, db_path, created_at, updated_at, file_name, total_difficulty, sub_count) VALUES (18, 'fc05133a-9c8e-4199-be7d-9c863df1fa33', '学生选课情景', '学生选课情景：



学生表：students。s_id是主键。

```
+-------------+--------+------------+--------------+
| s_id        | s_name |  s_birthday| s_sex        |
+-------------+--------+------------+--------------+
| 1           | 赵雷    | 1990-01-01 | 男           |
| 2           | 钱电    | 1990-12-21 | 男           |
| 3           | 孙风    | 1990-05-20 | 男           |
| 4           | 李云    | 1990-08-06 | 男           |
| 5           | 周梅    | 1991-12-01 | 女           |
| 6           | 吴兰    | 1992-03-01 | 女           |
+-------------+--------+------------+--------------+

```



课程表：courses.。c_id是主键。

```
+--------+------------+---------+
| c_id   |  c_name    |   t_id  |
+--------+------------+---------+
| 1      | 英语        |    2    |
| 2      | 数学        |    1    |
| 3      | 语文        |    3    |
+--------+-------------+--------+
```



教师表：teachers。t_id是主键。

```
+--------+------------+
| t_id   |  t_name    | 
+--------+------------+
| 1      | 张三        | 
| 2      | 李四        | 
| 3      | 王五        | 
+--------+------------+
```





成绩表scores。（s_id, c_id）是主键。

```
+--------+------------+---------+
|  s_id  |   c_id     | s_score |
+--------+------------+---------+
| 1      | 1          | 80      |
| 1      | 2          | 90      |
| 1      | 3          | 99      |
| 2      | 1          | 70      |
| 2      | 3          | 60      |
| 3      | 2          | 80      |
| 3      | 3          | 80      |
| 4      | 1          | 50      |
| 4      | 2          | 30      |
| 4      | 3          | 20      |
| 5      | 1          | 76      |
| 6      | 1          | 31      |
| 6      | 3          | 34      |
+--------+------------+---------+
```

', 'examDataFiles/upload_847104b940b08871137eeaeccd5bba73.sql', '2020-11-26 14:41:59', '2021-04-14 11:16:16', 'studentsandcoursesscenario.sql', 11, 5);
INSERT INTO sqlexam.main_questions (id, teacher_id, title, `desc`, db_path, created_at, updated_at, file_name, total_difficulty, sub_count) VALUES (19, 'fc05133a-9c8e-4199-be7d-9c863df1fa33', '公司部门职员情景', '`employee` 表包含所有员工信息，每个员工有其对应的工号 `Id`，姓名 `Name`，工资 `Salary` 和部门编号 `DepartmentId` 。他们的经理也属于员工。每个员工还有一列对应员工的经理的 Id。

employee：

```HTML
+----+-------+--------+--------------+-------------+
| Id | Name  | Salary | DepartmentId |  ManagerId  |
+----+-------+--------+--------------+-------------+
| 1  | Joe   | 84000  | 1            |    4        |  
| 2  | Henry | 80000  | 2            |    8        |   
| 3  | Sam   | 60000  | 2            |    8        |
| 4  | Max   | 120000 | 1            |    null     |
| 5  | Janet | 69000  | 2            |    8        |
| 6  | Randy | 85000  | 1            |    4        |
| 7  | Will  | 70000  | 1            |    4        |
| 8  | Jim   | 131000 | 2            |    null     |
+----+-------+--------+--------------+-------------+


```



`department` 表包含公司所有部门的信息。

```html
+----+----------+
| Id | Name     |
+----+----------+
| 1  | IT       |
| 2  | Sales    |
+----+----------+

```

', 'examDataFiles/upload_52a983a01f9fd601031afb0f8db3ae76.sql', '2020-11-26 15:01:37', '2021-04-10 12:01:42', 'employeesalary.sql', 8, 4);
INSERT INTO sqlexam.main_questions (id, teacher_id, title, `desc`, db_path, created_at, updated_at, file_name, total_difficulty, sub_count) VALUES (20, 'fc05133a-9c8e-4199-be7d-9c863df1fa33', '薪资等级情景', '表：dept。deptno是主键，dname是部门名称，loc是部门地址。

```
+--------+------------+---------+
| deptno |  dname     |  loc    |
+--------+------------+---------+
| 10     | Accounting | new york|
| 20     | Research   | dallas  |
| 30     | Sales      | chicago |
| 40     | Operations | boston  |
+--------+-------------+--------+
```



表：emp。job是员工工作，mgr是员工直属领导编号，可以为空。hiredate员工入职时间。sal是员工月薪工资，comm是奖金。

```
+----------+--------------+---------------+-------+-------------+-------+-----+---------+
| empno    | ename        | job           | mgr   | hiredate    | sal   | comm | deptno |
+----------+--------------+---------------+-------+-------------+-------+------+--------+
| 7369     | smith        | CLERK         | 7902  | 1980-12-17  | 800   | NULL | 20     |
| 7566     | jones        | manager       | 7839  | 1981-04-02  | 2975  | NULL | 20     |
| 7654     | martin       | salesman      | 7698  | 1981-09-28  | 1250  | 1400 | 30     |
| 7698     | blake        | manager       | 7839  | 1981-05-01  | 2850  | NULL | 30     |
| 7782     | clark        | manager       | 7839  | 1981-06-09  | 2450  | NULL | 10     |
| 7839     | king         | president     | null  | 1981-11-17  | 5000  | 2000 | 10     |
| 7844     | turner       | salesman      | 7698  | 1981-09-08  | 1500  | 800  | 30     |
| 7900     | james        | CLERK         | 7698  | 1981-12-03  | 950   | NULL | 30     |
| 7902     | ford         | analyst       | 7566  | 1981-12-03  | 3000  | NULL | 20     |
| 7934     | miller       | CLERK         | 7782  | 1981-01-23  | 1300  | NULL | 10     |
+----------+--------------+---------------+-------+-------------+-------+------+--------+
```



表：salGrade。 grade是等级，losal是最低工资，hisal是最高工资 。

```
+--------+------------+---------+
| grade  |  losal     |  hisal  |
+--------+------------+---------+
| 1      |  700       |  1000   |
| 2      |  1001      |  1400   |
| 3      |  1401      |  2000   |
| 4      |  2001      |  4000   |
| 5      |  4001      |  9999   |
+--------+-------------+--------+
```

', 'examDataFiles/upload_43e10a0317b933f8dcf733086108c315.sql', '2020-11-26 15:24:33', '2021-04-13 22:12:56', 'salarylevelscenario.sql', 9, 5);
INSERT INTO sqlexam.main_questions (id, teacher_id, title, `desc`, db_path, created_at, updated_at, file_name, total_difficulty, sub_count) VALUES (125, 'cc305c61-59db-4153-ae68-f79c41188a14', 'Tom 和 Jerry 的存款', '有如下表：

`blank_data`表：

``` 
+----+-------+--------+
| id | name  | amount |
+----+-------+--------+
| 1  | Tom   | ?      |
| 2  | Jerry | ?      |
+----+-------+--------+
```

`address_data`表：

```
+-------+---------+
| name  | address |
+-------+--------+
| Tom   | ?       |
| Jerry | ?       |
+-------+---------+
```', 'examDataFiles/upload_c6f362fb0a5733a6aa292736ab8cb34a.sql', '2020-11-16 07:15:24', '2020-12-29 15:49:16', 'sqlexamexec.sql', 2, 2);
INSERT INTO sqlexam.main_questions (id, teacher_id, title, `desc`, db_path, created_at, updated_at, file_name, total_difficulty, sub_count) VALUES (175, '0', '组合两个表', '表1: Person

```
+-------------+---------+
| 列名         | 类型     |
+-------------+---------+
| PersonId    | int     |
| FirstName   | varchar |
| LastName    | varchar |
+-------------+---------+
```

PersonId 是上表主键

表2: Address

```
+-------------+---------+
| 列名         | 类型    |
+-------------+---------+
| AddressId   | int     |
| PersonId    | int     |
| City        | varchar |
| State       | varchar |
+-------------+---------+
```

AddressId 是上表主键


', 'examDataFiles/auto_upload_175_1637126775641.sql', '2021-11-17 13:26:15', '2021-11-30 20:41:48', 'auto_upload_175_1637126775641.sql', 1, 1);
INSERT INTO sqlexam.main_questions (id, teacher_id, title, `desc`, db_path, created_at, updated_at, file_name, total_difficulty, sub_count) VALUES (176, '0', '第二高的薪水', 'Employee表

```
+----+--------+
| Id | Salary |
+----+--------+
| 1  | 100    |
| 2  | 200    |
| 3  | 300    |
+----+--------+
```

例如上述Employee表，SQL查询应该返回200 作为第二高的薪水。如果不存在第二高的薪水，那么查询应返回 null。

```
+---------------------+
| SecondHighestSalary |
+---------------------+
| 200                 |
+---------------------+
```

', 'examDataFiles/auto_upload_176_1637126775641.sql', '2021-11-17 13:26:15', '2021-11-30 21:05:13', 'auto_upload_176_1637126775641.sql', 2, 1);
INSERT INTO sqlexam.main_questions (id, teacher_id, title, `desc`, db_path, created_at, updated_at, file_name, total_difficulty, sub_count) VALUES (177, '0', '第N高的薪水', '编写一个 SQL 查询，获取 Employee 表中第n高的薪水（Salary）。

```
+----+--------+
| Id | Salary |
+----+--------+
| 1  | 100    |
| 2  | 200    |
| 3  | 300    |
+----+--------+
```

例如上述Employee表，n = 2时，应返回第二高的薪水200。如果不存在第n高的薪水，那么查询应返回null。

```
+------------------------+
| getNthHighestSalary(2) |
+------------------------+
| 200                    |
+------------------------+
```

', 'examDataFiles/auto_upload_177_1637126775641.sql', '2021-11-17 13:26:15', '2021-11-17 13:26:15', 'auto_upload_177_1637126775641.sql', 2, 1);
INSERT INTO sqlexam.main_questions (id, teacher_id, title, `desc`, db_path, created_at, updated_at, file_name, total_difficulty, sub_count) VALUES (178, '0', '分数排名', '编写一个 SQL 查询来实现分数排名。

如果两个分数相同，则两个分数排名（Rank）相同。请注意，平分后的下一个名次应该是下一个连续的整数值。换句话说，名次之间不应该有“间隔”。

```
+----+-------+
| Id | Score |
+----+-------+
| 1  | 3.50  |
| 2  | 3.65  |
| 3  | 4.00  |
| 4  | 3.85  |
| 5  | 4.00  |
| 6  | 3.65  |
+----+-------+
```

例如，根据上述给定的Scores 表，你的查询应该返回（按分数从高到低排列）：

```
+-------+------+
| Score | Rank |
+-------+------+
| 4.00  | 1    |
| 4.00  | 1    |
| 3.85  | 2    |
|3.65  | 3    |
| 3.65  | 3    |
| 3.50  | 4    |
+-------+------+
```

重要提示：对于 MySQL 解决方案，如果要转义用作列名的保留字，可以在关键字之前和之后使用撇号。例如 `Rank`

', 'examDataFiles/auto_upload_178_1637126775641.sql', '2021-11-17 13:26:15', '2021-11-17 13:26:15', 'auto_upload_178_1637126775641.sql', 2, 1);
INSERT INTO sqlexam.main_questions (id, teacher_id, title, `desc`, db_path, created_at, updated_at, file_name, total_difficulty, sub_count) VALUES (180, '0', '连续出现的数字', '表：Logs

```
+-------------+---------+
| Column Name | Type    |
+-------------+---------+
| id          | int     |
| num         | varchar |
+-------------+---------+
```

id 是这个表的主键。

编写一个 SQL 查询，查找所有至少连续出现三次的数字。

返回的结果表中的数据可以按 任意顺序 排列。

查询结果格式如下面的例子所示：

Logs 表：

```
+----+-----+
| Id | Num |
+----+-----+
| 1  | 1   |
| 2  | 1   |
| 3  | 1   |
| 4  | 2   |
| 5  | 1   |
| 6  | 2   |
| 7  | 2   |
+----+-----+
```

Result 表：

```
+-----------------+
| ConsecutiveNums |
+-----------------+
| 1               |
+-----------------+
```

1 是唯一连续出现至少三次的数字。

', 'examDataFiles/auto_upload_180_1637126775641.sql', '2021-11-17 13:26:15', '2021-11-17 13:26:15', 'auto_upload_180_1637126775641.sql', 2, 1);
INSERT INTO sqlexam.main_questions (id, teacher_id, title, `desc`, db_path, created_at, updated_at, file_name, total_difficulty, sub_count) VALUES (181, '0', '超过经理收入的员工', 'Employee表包含所有员工，他们的经理也属于员工。每个员工都有一个 Id，此外还有一列对应员工的经理的 Id。

```
+----+-------+--------+-----------+
| Id | Name  | Salary | ManagerId |
+----+-------+--------+-----------+
| 1  | Joe   | 70000  | 3         |
| 2  | Henry | 80000  | 4         |
| 3  | Sam   | 60000  | NULL      |
| 4  | Max   | 90000  | NULL      |
+----+-------+--------+-----------+
```

给定Employee表，编写一个 SQL 查询，该查询可以获取收入超过他们经理的员工的姓名。在上面的表格中，Joe 是唯一一个收入超过他的经理的员工。

```
+----------+
| Employee |
+----------+
| Joe      |
+----------+
```

', 'examDataFiles/auto_upload_181_1637126775641.sql', '2021-11-17 13:26:15', '2021-11-17 13:26:15', 'auto_upload_181_1637126775641.sql', 1, 1);
INSERT INTO sqlexam.main_questions (id, teacher_id, title, `desc`, db_path, created_at, updated_at, file_name, total_difficulty, sub_count) VALUES (182, '0', '查找重复的电子邮箱', '编写一个 SQL 查询，查找Person 表中所有重复的电子邮箱。

示例：

```
+----+---------+
| Id | Email   |
+----+---------+
| 1  | a@b.com |
| 2  | c@d.com |
| 3  | a@b.com |
+----+---------+
```

根据以上输入，你的查询应返回以下结果：

```
+---------+
| Email   |
+---------+
| a@b.com |
+---------+
```

说明：所有电子邮箱都是小写字母。

', 'examDataFiles/auto_upload_182_1637126775641.sql', '2021-11-17 13:26:15', '2021-11-17 13:26:15', 'auto_upload_182_1637126775641.sql', 1, 1);
INSERT INTO sqlexam.main_questions (id, teacher_id, title, `desc`, db_path, created_at, updated_at, file_name, total_difficulty, sub_count) VALUES (183, '0', '从不订购的客户', '某网站包含两个表，Customers 表和 Orders 表。编写一个 SQL 查询，找出所有从不订购任何东西的客户。

Customers 表：

```
+----+-------+
| Id | Name  |
+----+-------+
| 1  | Joe   |
| 2  | Henry |
| 3  | Sam   |
| 4  | Max   |
+----+-------+
```

Orders 表：

```
+----+------------+
| Id | CustomerId |
+----+------------+
| 1  | 3          |
| 2  | 1          |
+----+------------+
```

例如给定上述表格，你的查询应返回：

```
+-----------+
| Customers |
+-----------+
| Henry     |
| Max       |
+-----------+
```

', 'examDataFiles/auto_upload_183_1637126775642.sql', '2021-11-17 13:26:15', '2021-11-17 13:26:15', 'auto_upload_183_1637126775642.sql', 1, 1);
INSERT INTO sqlexam.main_questions (id, teacher_id, title, `desc`, db_path, created_at, updated_at, file_name, total_difficulty, sub_count) VALUES (184, '0', '部门工资最高的员工', 'Employee 表包含所有员工信息，每个员工有其对应的Id, salary 和 department Id。

```
+----+-------+--------+--------------+
| Id | Name  | Salary | DepartmentId |
+----+-------+--------+--------------+
| 1  | Joe   | 70000  | 1            |
| 2 | Jim  | 90000 | 1      |
| 3  | Henry | 80000  | 2            |
| 4  | Sam   | 60000  | 2            |
| 5  | Max   | 90000  | 1            |
+----+-------+--------+--------------+
```

Department表包含公司所有部门的信息。

```
+----+----------+
| Id | Name     |
+----+----------+
| 1  | IT       |
| 2  | Sales    |
+----+----------+
```

编写一个 SQL 查询，找出每个部门工资最高的员工。对于上述表，您的 SQL 查询应返回以下行（行的顺序无关紧要）。

```
+------------+----------+--------+
| Department | Employee | Salary |
+------------+----------+--------+
| IT         | Max      | 90000  |
| IT     | Jim   | 90000 |
| Sales      | Henry    | 80000  |
+------------+----------+--------+
```

解释：

Max 和 Jim 在 IT 部门的工资都是最高的，Henry 在销售部的工资最高。

', 'examDataFiles/auto_upload_184_1637126775642.sql', '2021-11-17 13:26:15', '2021-11-17 13:26:15', 'auto_upload_184_1637126775642.sql', 2, 1);
INSERT INTO sqlexam.main_questions (id, teacher_id, title, `desc`, db_path, created_at, updated_at, file_name, total_difficulty, sub_count) VALUES (185, '0', '部门工资前三高的所有员工', 'Employee 表包含所有员工信息，每个员工有其对应的工号Id，姓名 Name，工资 Salary 和部门编号 DepartmentId 。

```
+----+-------+--------+--------------+
| Id | Name  | Salary | DepartmentId |
+----+-------+--------+--------------+
| 1  | Joe   | 85000  | 1            |
| 2  | Henry | 80000  | 2            |
| 3  | Sam   | 60000  | 2            |
| 4  | Max   | 90000  | 1            |
| 5  | Janet | 69000  | 1            |
| 6  | Randy | 85000  | 1            |
| 7  | Will  | 70000  | 1            |
+----+-------+--------+--------------+
```

Department 表包含公司所有部门的信息。

```
+----+----------+
| Id | Name     |
+----+----------+
| 1  | IT       |
| 2  | Sales    |
+----+----------+
```

编写一个SQL 查询，找出每个部门获得前三高工资的所有员工。例如，根据上述给定的表，查询结果应返回：

```
+------------+----------+--------+
| Department | Employee | Salary |
+------------+----------+--------+
| IT         | Max      | 90000  |
| IT         | Randy    | 85000  |
| IT         | Joe      | 85000  |
| IT         | Will     | 70000  |
| Sales      | Henry    | 80000  |
| Sales      | Sam      | 60000  |
+------------+----------+--------+
```

解释：

IT 部门中，Max 获得了最高的工资，Randy 和 Joe 都拿到了第二高的工资，Will 的工资排第三。销售部门（Sales）只有两名员工，Henry 的工资最高，Sam 的工资排第二。

', 'examDataFiles/auto_upload_185_1637126775642.sql', '2021-11-17 13:26:15', '2021-11-17 13:26:15', 'auto_upload_185_1637126775642.sql', 3, 1);
INSERT INTO sqlexam.main_questions (id, teacher_id, title, `desc`, db_path, created_at, updated_at, file_name, total_difficulty, sub_count) VALUES (196, '0', '删除重复的电子邮箱', '编写一个 SQL 查询，来删除Person表中所有重复的电子邮箱，重复的邮箱里只保留Id最小的那个。

```
+----+------------------+
| Id | Email            |
+----+------------------+
| 1  | john@example.com |
| 2  | bob@example.com  |
| 3  | john@example.com |
+----+------------------+
```

Id 是这个表的主键。

例如，在运行你的查询语句之后，上面的 Person 表应返回以下几行:

```
+----+------------------+
| Id | Email            |
+----+------------------+
| 1  | john@example.com |
| 2  | bob@example.com  |
+----+------------------+
```

提示：

执行 SQL 之后，输出是整个 Person表。

使用 delete 语句。

', 'examDataFiles/auto_upload_196_1637126775642.sql', '2021-11-17 13:26:15', '2021-11-17 13:26:15', 'auto_upload_196_1637126775642.sql', 1, 1);
INSERT INTO sqlexam.main_questions (id, teacher_id, title, `desc`, db_path, created_at, updated_at, file_name, total_difficulty, sub_count) VALUES (197, '0', '上升的温度', '表 Weather

```
+---------------+---------+
| Column Name   | Type    |
+---------------+---------+
| id            | int     |
| recordDate    | date    |
| temperature   | int     |
+---------------+---------+
```

id 是这个表的主键

该表包含特定日期的温度信息

编写一个 SQL 查询，来查找与之前（昨天的）日期相比温度更高的所有日期的 id 。

返回结果 不要求顺序 。

查询结果格式如下例：

Weather

```
+----+------------+-------------+
| id | recordDate | Temperature |
+----+------------+-------------+
| 1  | 2015-01-01 | 10          |
| 2  | 2015-01-02 | 25          |
| 3  | 2015-01-03 | 20          |
| 4  | 2015-01-04 | 30          |
+----+------------+-------------+
```

Result table:

```
+----+
| id |
+----+
| 2  |
| 4  |
+----+
```

2015-01-02 的温度比前一天高（10 -> 25）

2015-01-04 的温度比前一天高（20 -> 30）

', 'examDataFiles/auto_upload_197_1637126775642.sql', '2021-11-17 13:26:15', '2021-11-17 13:26:15', 'auto_upload_197_1637126775642.sql', 1, 1);
INSERT INTO sqlexam.main_questions (id, teacher_id, title, `desc`, db_path, created_at, updated_at, file_name, total_difficulty, sub_count) VALUES (262, '0', '行程和用户', '表：Trips

```
+-------------+----------+
| Column Name | Type     |
+-------------+----------+
| Id          | int      |
| Client_Id   | int      |
| Driver_Id   | int      |
| City_Id     | int      |
| Status      | enum     |
| Request_at  | date     |     
+-------------+----------+
```

Id 是这张表的主键。

这张表中存所有出租车的行程信息。每段行程有唯一 Id ，其中 Client_Id 和 Driver_Id 是 Users 表中 Users_Id 的外键。

Status 是一个表示行程状态的枚举类型，枚举成员为(‘completed’, ‘cancelled_by_driver’, ‘cancelled_by_client’) 。

表：Users

```
+-------------+----------+
| Column Name | Type     |
+-------------+----------+
| Users_Id    | int      |
| Banned      | enum     |
| Role        | enum     |
+-------------+----------+
```

Users_Id 是这张表的主键。

这张表中存所有用户，每个用户都有一个唯一的 Users_Id ，Role 是一个表示用户身份的枚举类型，枚举成员为 (‘client’, ‘driver’, ‘partner’) 。

Banned 是一个表示用户是否被禁止的枚举类型，枚举成员为 (‘Yes’, ‘No’) 。

写一段 SQL 语句查出"2013-10-01"至"2013-10-03"期间非禁止用户（乘客和司机都必须未被禁止）的取消率。非禁止用户即 Banned 为 No 的用户，禁止用户即 Banned 为 Yes 的用户。

取消率 的计算方式如下：(被司机或乘客取消的非禁止用户生成的订单数量) / (非禁止用户生成的订单总数)。

返回结果表中的数据可以按任意顺序组织。其中取消率 Cancellation Rate 需要四舍五入保留 两位小数 。

查询结果格式如下例所示：

Trips 表：

```
+----+-----------+-----------+---------+---------------------+------------+
| Id | Client_Id | Driver_Id | City_Id | Status              | Request_at |
+----+-----------+-----------+---------+---------------------+------------+
| 1  | 1         | 10        | 1       | completed           | 2013-10-01 |
| 2  | 2         | 11        | 1       | cancelled_by_driver | 2013-10-01 |
| 3  | 3         | 12        | 6       | completed           | 2013-10-01 |
| 4  | 4         | 13        | 6       | cancelled_by_client | 2013-10-01 |
| 5  | 1         | 10        | 1       | completed           | 2013-10-02 |
| 6  | 2         | 11        | 6       | completed           | 2013-10-02 |
| 7  | 3         | 12        | 6       | completed           | 2013-10-02 |
| 8  | 2         | 12        | 12      | completed           | 2013-10-03 |
| 9  | 3         | 10        | 12      | completed           | 2013-10-03 |
| 10 | 4         | 13        | 12      | cancelled_by_driver | 2013-10-03 |
+----+-----------+-----------+---------+---------------------+------------+
```

Users 表：

```
+----------+--------+--------+
| Users_Id | Banned | Role   |
+----------+--------+--------+
| 1        | No     | client |
| 2        | Yes    | client |
| 3        | No     | client |
| 4        | No     | client |
| 10       | No     | driver |
| 11       | No     | driver |
| 12       | No     | driver |
| 13       | No     | driver |
+----------+--------+--------+
```

Result 表：

```
+------------+-------------------+
| Day        | Cancellation Rate |
+------------+-------------------+
| 2013-10-01 | 0.33              |
| 2013-10-02 | 0.00              |
| 2013-10-03 | 0.50              |
+------------+-------------------+
```

2013-10-01：

  - 共有 4 条请求，其中 2 条取消。

  - 然而，Id=2 的请求是由禁止用户（User_Id=2）发出的，所以计算时应当忽略它。

  - 因此，总共有 3 条非禁止请求参与计算，其中 1 条取消。

  - 取消率为 (1 / 3) = 0.33

2013-10-02：

  - 共有 3 条请求，其中 0 条取消。

  - 然而，Id=6 的请求是由禁止用户发出的，所以计算时应当忽略它。

  - 因此，总共有 2 条非禁止请求参与计算，其中 0 条取消。

  - 取消率为 (0 / 2) = 0.00

2013-10-03：

  - 共有 3 条请求，其中 1 条取消。

  - 然而，Id=8 的请求是由禁止用户发出的，所以计算时应当忽略它。

  - 因此，总共有 2 条非禁止请求参与计算，其中 1 条取消。

  - 取消率为 (1 / 2) = 0.50

', 'examDataFiles/auto_upload_262_1637126775642.sql', '2021-11-17 13:26:15', '2021-11-17 13:26:15', 'auto_upload_262_1637126775642.sql', 3, 1);
INSERT INTO sqlexam.main_questions (id, teacher_id, title, `desc`, db_path, created_at, updated_at, file_name, total_difficulty, sub_count) VALUES (569, '0', '员工薪水中位数', 'Employee 表包含所有员工。Employee 表有三列：员工Id，公司名和薪水。

```
+-----+------------+--------+
|Id   | Company    | Salary |
+-----+------------+--------+
|1    | A          | 2341   |
|2    | A          | 341    |
|3    | A          | 15     |
|4    | A          | 15314  |
|5    | A          | 451    |
|6    | A          | 513    |
|7    | B          | 15     |
|8    | B          | 13     |
|9    | B          | 1154   |
|10   | B          | 1345   |
|11   | B          | 1221   |
|12   | B          | 234    |
|13   | C          | 2345   |
|14   | C          | 2645   |
|15   | C          | 2645   |
|16   | C          | 2652   |
|17   | C          | 65     |
+-----+------------+--------+
```

请编写SQL查询来查找每个公司的薪水中位数。挑战点：你是否可以在不使用任何内置的SQL函数的情况下解决此问题。

```
+-----+------------+--------+
|Id   | Company    | Salary |
+-----+------------+--------+
|5    | A          | 451    |
|6    | A          | 513    |
|12   | B          | 234    |
|9    | B          | 1154   |
|14   | C          | 2645   |
+-----+------------+--------+
```

', 'examDataFiles/auto_upload_569_1637126775642.sql', '2021-11-17 13:26:15', '2021-11-17 13:26:15', 'auto_upload_569_1637126775642.sql', 3, 1);
INSERT INTO sqlexam.main_questions (id, teacher_id, title, `desc`, db_path, created_at, updated_at, file_name, total_difficulty, sub_count) VALUES (570, '0', '至少有5名直接下属的经理', 'Employee 表包含所有员工和他们的经理。每个员工都有一个 Id，并且还有一列是经理的 Id。

```
+------+----------+-----------+----------+
|Id    |Name 	  |Department |ManagerId |
+------+----------+-----------+----------+
|101   |John 	  |A 	      |null      |
|102   |Dan 	  |A 	      |101       |
|103   |James 	  |A 	      |101       |
|104   |Amy 	  |A 	      |101       |
|105   |Anne 	  |A 	      |101       |
|106   |Ron 	  |B 	      |101       |
+------+----------+-----------+----------+
```

给定 Employee 表，请编写一个SQL查询来查找至少有5名直接下属的经理。对于上表，您的SQL查询应该返回：

```
+-------+
| Name  |
+-------+
| John  |
+-------+
```

注意:

没有人是自己的下属。

', 'examDataFiles/auto_upload_570_1637126775642.sql', '2021-11-17 13:26:15', '2021-11-17 13:26:15', 'auto_upload_570_1637126775642.sql', 2, 1);
INSERT INTO sqlexam.main_questions (id, teacher_id, title, `desc`, db_path, created_at, updated_at, file_name, total_difficulty, sub_count) VALUES (571, '0', '给定数字的频率查询中位数', 'Numbers 表保存数字的值及其频率。

```
+----------+-------------+
|  Number  |  Frequency  |
+----------+-------------|
|  0       |  7          |
|  1       |  1          |
|  2       |  3          |
|  3       |  1          |
+----------+-------------+
```

在此表中，数字为 0, 0, 0, 0, 0, 0, 0, 1, 2, 2, 2, 3，所以中位数是 (0 + 0) / 2 = 0。

```
+--------+
| median |
+--------|
| 0.0000 |
+--------+
```

请编写一个查询来查找所有数字的中位数并将结果命名为 median 。

', 'examDataFiles/auto_upload_571_1637126775642.sql', '2021-11-17 13:26:15', '2021-11-17 13:26:15', 'auto_upload_571_1637126775642.sql', 3, 1);
INSERT INTO sqlexam.main_questions (id, teacher_id, title, `desc`, db_path, created_at, updated_at, file_name, total_difficulty, sub_count) VALUES (574, '0', '当选者', '表: Candidate

```
+-----+---------+
| id  | Name    |
+-----+---------+
| 1   | A       |
| 2   | B       |
| 3   | C       |
| 4   | D       |
| 5   | E       |
+-----+---------+  
```

表: Vote

```
+-----+--------------+
| id  | CandidateId  |
+-----+--------------+
| 1   |     2        |
| 2   |     4        |
| 3   |     3        |
| 4   |     2        |
| 5   |     5        |
+-----+--------------+
```

id 是自动递增的主键，

CandidateId 是 Candidate 表中的 id.

请编写 sql 语句来找到当选者的名字，上面的例子将返回当选者 B.

```
+------+
| Name |
+------+
| B    |
+------+
```

注意:

你可以假设没有平局，换言之，最多只有一位当选者。

', 'examDataFiles/auto_upload_574_1637126775642.sql', '2021-11-17 13:26:15', '2021-11-17 13:26:15', 'auto_upload_574_1637126775642.sql', 2, 1);
INSERT INTO sqlexam.main_questions (id, teacher_id, title, `desc`, db_path, created_at, updated_at, file_name, total_difficulty, sub_count) VALUES (577, '0', '员工奖金', '选出所有 bonus < 1000 的员工的 name 及其 bonus。

Employee 表单

```
+-------+--------+-----------+--------+
| empId |  name  | supervisor| salary |
+-------+--------+-----------+--------+
|   1   | John   |  3        | 1000   |
|   2   | Dan    |  3        | 2000   |
|   3   | Brad   |  null     | 4000   |
|   4   | Thomas |  3        | 4000   |
+-------+--------+-----------+--------+
```

empId 是这张表单的主关键字

Bonus 表单

```
+-------+-------+
| empId | bonus |
+-------+-------+
| 2     | 500   |
| 4     | 2000  |
+-------+-------+
```

empId 是这张表单的主关键字

输出示例：

```
+-------+-------+
| name  | bonus |
+-------+-------+
| John  | null  |
| Dan   | 500   |
| Brad  | null  |
+-------+-------+
```

', 'examDataFiles/auto_upload_577_1637126775642.sql', '2021-11-17 13:26:15', '2021-11-17 13:26:15', 'auto_upload_577_1637126775642.sql', 1, 1);
INSERT INTO sqlexam.main_questions (id, teacher_id, title, `desc`, db_path, created_at, updated_at, file_name, total_difficulty, sub_count) VALUES (578, '0', '查询回答率最高的问题', '从 survey_log 表中获得回答率最高的问题，survey_log 表包含这些列：id, action, question_id, answer_id, q_num, timestamp。

id 表示用户 id；action 有以下几种值："show"，"answer"，"skip"；当 action 值为 "answer" 时 answer_id 非空，而 action 值为 "show" 或者 "skip" 时 answer_id 为空；q_num 表示当前会话中问题的编号。

请编写 SQL 查询来找到具有最高回答率的问题。

示例：

输入：

```
+------+-----------+--------------+------------+-----------+------------+
| id   | action    | question_id  | answer_id  | q_num     | timestamp  |
+------+-----------+--------------+------------+-----------+------------+
| 5    | show      | 285          | null       | 1         | 123        |
| 5    | answer    | 285          | 124124     | 1         | 124        |
| 5    | show      | 369          | null       | 2         | 125        |
| 5    | skip      | 369          | null       | 2         | 126        |
+------+-----------+--------------+------------+-----------+------------+
```

输出：

```
+-------------+
| survey_log  |
+-------------+
|    285      |
+-------------+
```

解释：

问题 285 的回答率为 1/1，而问题 369 回答率为 0/1，因此输出 285 。

提示：回答率最高的含义是：同一问题编号中回答数占显示数的比例最高。

', 'examDataFiles/auto_upload_578_1637126775642.sql', '2021-11-17 13:26:15', '2021-11-17 13:26:15', 'auto_upload_578_1637126775642.sql', 2, 1);
INSERT INTO sqlexam.main_questions (id, teacher_id, title, `desc`, db_path, created_at, updated_at, file_name, total_difficulty, sub_count) VALUES (579, '0', '查询员工的累计薪水', 'Employee 表保存了一年内的薪水信息。

请你编写 SQL 语句，对于每个员工，查询他除最近一个月（即最大月）之外，剩下每个月的近三个月的累计薪水（不足三个月也要计算）。

结果请按 Id 升序，然后按 Month 降序显示。

示例：

输入：

| Id | Month | Salary |

|----|-------|--------|

| 1  | 1     | 20     |

| 2  | 1     | 20     |

| 1  | 2     | 30     |

| 2  | 2     | 30     |

| 3  | 2     | 40     |

| 1  | 3     | 40     |

| 3  | 3     | 60     |

| 1  | 4     | 60     |

| 3  | 4     | 70     |

输出：

| Id | Month | Salary |

|----|-------|--------|

| 1  | 3     | 90     |

| 1  | 2     | 50     |

| 1  | 1     | 20     |

| 2  | 1     | 20     |

| 3  | 3     | 100    |

| 3  | 2     | 40     |

解释：

员工 ''1''除去最近一个月（月份 ''4''），有三个月的薪水记录：月份 ''3''薪水为40，月份 ''2''薪水为 30，月份 ''1''薪水为 20。

所以近 3 个月的薪水累计分别为(40 + 30 + 20) =90，(30 + 20) = 50 和 20。

| Id | Month | Salary |

|----|-------|--------|

| 1  | 3     | 90     |

| 1  | 2     | 50     |

| 1  | 1     | 20     |

员工 ''2'' 除去最近的一个月（月份 ''2''）的话，只有月份 ''1'' 这一个月的薪水记录。

| Id | Month | Salary |

|----|-------|--------|

| 2  | 1     | 20     |

员工 ''3'' 除去最近一个月（月份 ''4''）后有两个月，分别为：月份 ''3'' 薪水为 60 和 月份 ''2'' 薪水为 40。所以各月的累计情况如下：

| Id | Month | Salary |

|----|-------|--------|

| 3  | 3     | 100    |

| 3  | 2     | 40     |

', 'examDataFiles/auto_upload_579_1637126775642.sql', '2021-11-17 13:26:15', '2021-11-17 13:26:15', 'auto_upload_579_1637126775642.sql', 3, 1);
INSERT INTO sqlexam.main_questions (id, teacher_id, title, `desc`, db_path, created_at, updated_at, file_name, total_difficulty, sub_count) VALUES (580, '0', '统计各专业学生人数', '一所大学有 2 个数据表，分别是student和department，这两个表保存着每个专业的学生数据和院系数据。

写一个查询语句，查询department表中每个专业的学生人数 （即使没有学生的专业也需列出）。

将你的查询结果按照学生人数降序排列。 如果有两个或两个以上专业有相同的学生数目，将这些部门按照部门名字的字典序从小到大排列。

student 表格如下：

| Column Name  | Type      |

|--------------|-----------|

| student_id   | Integer   |

| student_name | String    |

| gender       | Character |

| dept_id      | Integer   |

其中， student_id 是学生的学号， student_name 是学生的姓名， gender 是学生的性别， dept_id 是学生所属专业的专业编号。

department 表格如下：

| Column Name | Type    |

|-------------|---------|

| dept_id     | Integer |

| dept_name   | String  |

dept_id 是专业编号， dept_name 是专业名字。

这里是一个示例输入：

student表格：

| student_id | student_name | gender | dept_id |

|------------|--------------|--------|---------|

| 1          | Jack         | M      | 1       |

| 2          | Jane         | F      | 1       |

| 3          | Mark         | M      | 2       |

department 表格：

| dept_id | dept_name   |

|---------|-------------|

| 1       | Engineering |

| 2       | Science     |

| 3       | Law         |

示例输出为：

| dept_name   | student_number |

|-------------|----------------|

| Engineering | 2              |

| Science     | 1              |

| Law         | 0              |

', 'examDataFiles/auto_upload_580_1637126775642.sql', '2021-11-17 13:26:15', '2021-11-17 13:26:15', 'auto_upload_580_1637126775642.sql', 2, 1);
INSERT INTO sqlexam.main_questions (id, teacher_id, title, `desc`, db_path, created_at, updated_at, file_name, total_difficulty, sub_count) VALUES (584, '0', '寻找用户推荐人', '给定表customer，里面保存了所有客户信息和他们的推荐人。

```
+------+------+-----------+
| id   | name | referee_id|
+------+------+-----------+
|    1 | Will |      NULL |
|    2 | Jane |      NULL |
|    3 | Alex |         2 |
|    4 | Bill |      NULL |
|    5 | Zack |         1 |
|    6 | Mark |         2 |
+------+------+-----------+
```

写一个查询语句，返回一个客户列表，列表中客户的推荐人的编号都不是 2。

对于上面的示例数据，结果为：

```
+------+
| name |
+------+
| Will |
| Jane |
| Bill |
| Zack |
+------+
```

', 'examDataFiles/auto_upload_584_1637126775642.sql', '2021-11-17 13:26:15', '2021-11-17 13:26:15', 'auto_upload_584_1637126775642.sql', 1, 1);
INSERT INTO sqlexam.main_questions (id, teacher_id, title, `desc`, db_path, created_at, updated_at, file_name, total_difficulty, sub_count) VALUES (585, '0', '2016年的投资', '写一个查询语句，将2016 年 (TIV_2016) 所有成功投资的金额加起来，保留 2 位小数。

对于一个投保人，他在 2016 年成功投资的条件是：

他在 2015 年的投保额(TIV_2015) 至少跟一个其他投保人在 2015 年的投保额相同。

他所在的城市必须与其他投保人都不同（也就是说维度和经度不能跟其他任何一个投保人完全相同）。

输入格式:

表insurance 格式如下：

| Column Name | Type          |

|-------------|---------------|

| PID         | INTEGER(11)   |

| TIV_2015    | NUMERIC(15,2) |

| TIV_2016    | NUMERIC(15,2) |

| LAT         | NUMERIC(5,2)  |

| LON         | NUMERIC(5,2)  |

PID字段是投保人的投保编号，TIV_2015 是该投保人在2015年的总投保金额，TIV_2016 是该投保人在2016年的投保金额，LAT 是投保人所在城市的维度，LON是投保人所在城市的经度。

样例输入

| PID | TIV_2015 | TIV_2016 | LAT | LON |

|-----|----------|----------|-----|-----|

| 1   | 10       | 5        | 10  | 10  |

| 2   | 20       | 20       | 20  | 20  |

| 3   | 10       | 30       | 20  | 20  |

| 4   | 10       | 40       | 40  | 40  |

样例输出

| TIV_2016 |

|----------|

| 45.00    |

解释

就如最后一个投保人，第一个投保人同时满足两个条件：

1. 他在 2015 年的投保金额 TIV_2015 为 ''10'' ，与第三个和第四个投保人在 2015 年的投保金额相同。

2. 他所在城市的经纬度是独一无二的。

第二个投保人两个条件都不满足。他在 2015 年的投资 TIV_2015 与其他任何投保人都不相同。

且他所在城市的经纬度与第三个投保人相同。基于同样的原因，第三个投保人投资失败。

所以返回的结果是第一个投保人和最后一个投保人的 TIV_2016 之和，结果是 45 。

', 'examDataFiles/auto_upload_585_1637126775642.sql', '2021-11-17 13:26:15', '2021-11-17 13:26:15', 'auto_upload_585_1637126775642.sql', 2, 1);
INSERT INTO sqlexam.main_questions (id, teacher_id, title, `desc`, db_path, created_at, updated_at, file_name, total_difficulty, sub_count) VALUES (586, '0', '订单最多的客户', '在表orders中找到订单数最多客户对应的customer_number。

数据保证订单数最多的顾客恰好只有一位。

表orders 定义如下：

| Column            | Type      |

|-------------------|-----------|

| order_number (PK) | int       |

| customer_number   | int       |

| order_date        | date      |

| required_date     | date      |

| shipped_date      | date      |

| status            | char(15)  |

| comment           | char(200) |

样例输入

| order_number | customer_number | order_date | required_date | shipped_date | status | comment |

|--------------|-----------------|------------|---------------|--------------|--------|---------|

| 1            | 1               | 2017-04-09 | 2017-04-13    | 2017-04-12   | Closed |         |

| 2            | 2               | 2017-04-15 | 2017-04-20    | 2017-04-18   | Closed |         |

| 3            | 3               | 2017-04-16 | 2017-04-25    | 2017-04-20   | Closed |         |

| 4            | 3               | 2017-04-18 | 2017-04-28    | 2017-04-25   | Closed |         |

样例输出

| customer_number |

|-----------------|

| 3               |

解释

customer_number 为 ''3'' 的顾客有两个订单，比顾客 ''1'' 或者 ''2'' 都要多，因为他们只有一个订单

所以结果是该顾客的 customer_number ，也就是 3 。

进阶： 如果有多位顾客订单数并列最多，你能找到他们所有的 customer_number 吗？

', 'examDataFiles/auto_upload_586_1637126775642.sql', '2021-11-17 13:26:15', '2021-11-17 13:26:15', 'auto_upload_586_1637126775642.sql', 1, 1);
INSERT INTO sqlexam.main_questions (id, teacher_id, title, `desc`, db_path, created_at, updated_at, file_name, total_difficulty, sub_count) VALUES (595, '0', '大的国家', '这里有张World 表

```
+-----------------+------------+------------+--------------+---------------+
| name            | continent  | area       | population   | gdp           |
+-----------------+------------+------------+--------------+---------------+
| Afghanistan     | Asia       | 652230     | 25500100     | 20343000      |
| Albania         | Europe     | 28748      | 2831741      | 12960000      |
| Algeria         | Africa     | 2381741    | 37100000     | 188681000     |
| Andorra         | Europe     | 468        | 78115        | 3712000       |
| Angola          | Africa     | 1246700    | 20609294     | 100990000     |
+-----------------+------------+------------+--------------+---------------+
```

如果一个国家的面积超过 300 万平方公里，或者人口超过 2500 万，那么这个国家就是大国家。

编写一个 SQL 查询，输出表中所有大国家的名称、人口和面积。

例如，根据上表，我们应该输出:

```
+--------------+-------------+--------------+
| name         | population  | area         |
+--------------+-------------+--------------+
| Afghanistan  | 25500100    | 652230       |
| Algeria      | 37100000    | 2381741      |
+--------------+-------------+--------------+
```

', 'examDataFiles/auto_upload_595_1637126775642.sql', '2021-11-17 13:26:15', '2021-11-17 13:26:15', 'auto_upload_595_1637126775642.sql', 1, 1);
INSERT INTO sqlexam.main_questions (id, teacher_id, title, `desc`, db_path, created_at, updated_at, file_name, total_difficulty, sub_count) VALUES (596, '0', '超过5名学生的课', '有一个courses 表 ，有: student(学生) 和 class (课程)。

请列出所有超过或等于5名学生的课。

例如，表：

```
+---------+------------+
| student | class      |
+---------+------------+
| A       | Math       |
| B       | English    |
| C       | Math       |
| D       | Biology    |
| E       | Math       |
| F       | Computer   |
| G       | Math       |
| H       | Math       |
| I       | Math       |
+---------+------------+
```

应该输出:

```
+---------+
| class   |
+---------+
| Math    |
+---------+
```

提示：

学生在每个课中不应被重复计算。

', 'examDataFiles/auto_upload_596_1637126775642.sql', '2021-11-17 13:26:15', '2021-11-17 13:26:15', 'auto_upload_596_1637126775642.sql', 1, 1);
INSERT INTO sqlexam.main_questions (id, teacher_id, title, `desc`, db_path, created_at, updated_at, file_name, total_difficulty, sub_count) VALUES (597, '0', '好友申请 I：总体通过率', '在 Facebook 或者 Twitter 这样的社交应用中，人们经常会发好友申请也会收到其他人的好友申请。

表：FriendRequest

```
+----------------+---------+
| Column Name    | Type    |
+----------------+---------+
| sender_id      | int     |
| send_to_id     | int     |
| request_date   | date    |
+----------------+---------+
```

此表没有主键，它可能包含重复项。

该表包含发送请求的用户的 ID ，接受请求的用户的 ID 以及请求的日期。

表：RequestAccepted

```
+----------------+---------+
| Column Name    | Type    |
+----------------+---------+
| requester_id   | int     |
| accepter_id    | int     |
| accept_date    | date    |
+----------------+---------+
```

此表没有主键，它可能包含重复项。

该表包含发送请求的用户的 ID ，接受请求的用户的 ID 以及请求通过的日期。

写一个查询语句，求出好友申请的通过率，用 2 位小数表示。通过率由接受好友申请的数目除以申请总数。

提示：

通过的好友申请不一定都在表friend_request中。你只需要统计总的被通过的申请数（不管它们在不在表FriendRequest中），并将它除以申请总数，得到通过率

一个好友申请发送者有可能会给接受者发几条好友申请，也有可能一个好友申请会被通过好几次。这种情况下，重复的好友申请只统计一次。

如果一个好友申请都没有，通过率为 0.00 。

查询结果应该如下例所示：

FriendRequest 表：

```
+-----------+------------+--------------+
| sender_id | send_to_id | request_date |
+-----------+------------+--------------+
| 1         | 2          | 2016/06/01   |
| 1         | 3          | 2016/06/01   |
| 1         | 4          | 2016/06/01   |
| 2         | 3          | 2016/06/02   |
| 3         | 4          | 2016/06/09   |
+-----------+------------+--------------+
```

RequestAccepted 表：

```
+--------------+-------------+-------------+
| requester_id | accepter_id | accept_date |
+--------------+-------------+-------------+
| 1            | 2           | 2016/06/03  |
| 1            | 3           | 2016/06/08  |
| 2            | 3           | 2016/06/08  |
| 3            | 4           | 2016/06/09  |
| 3            | 4           | 2016/06/10  |
+--------------+-------------+-------------+
```

Result 表：

```
+-------------+
| accept_rate |
+-------------+
| 0.8         |
+-------------+
```

总共有 5 个请求，有 4 个不同的通过请求，所以通过率是 0.80

进阶:

你能写一个查询语句得到每个月的通过率吗？

你能求出每一天的累计通过率吗？

', 'examDataFiles/auto_upload_597_1637126775642.sql', '2021-11-17 13:26:15', '2021-11-17 13:26:15', 'auto_upload_597_1637126775642.sql', 1, 1);
INSERT INTO sqlexam.main_questions (id, teacher_id, title, `desc`, db_path, created_at, updated_at, file_name, total_difficulty, sub_count) VALUES (601, '0', '体育馆的人流量', '表：Stadium

```
+---------------+---------+
| Column Name   | Type    |
+---------------+---------+
| id            | int     |
| visit_date    | date    |
| people        | int     |
+---------------+---------+
```

visit_date 是表的主键

每日人流量信息被记录在这三列信息中：序号 (id)、日期 (visit_date)、人流量 (people)

每天只有一行记录，日期随着 id 的增加而增加

编写一个 SQL 查询以找出每行的人数大于或等于 100 且 id 连续的三行或更多行记录。

返回按 visit_date 升序排列的结果表。

查询结果格式如下所示。

Stadium table:

```
+------+------------+-----------+
| id   | visit_date | people    |
+------+------------+-----------+
| 1    | 2017-01-01 | 10        |
| 2    | 2017-01-02 | 109       |
| 3    | 2017-01-03 | 150       |
| 4    | 2017-01-04 | 99        |
| 5    | 2017-01-05 | 145       |
| 6    | 2017-01-06 | 1455      |
| 7    | 2017-01-07 | 199       |
| 8    | 2017-01-09 | 188       |
+------+------------+-----------+
```

Result table:

```
+------+------------+-----------+
| id   | visit_date | people    |
+------+------------+-----------+
| 5    | 2017-01-05 | 145       |
| 6    | 2017-01-06 | 1455      |
| 7    | 2017-01-07 | 199       |
| 8    | 2017-01-09 | 188       |
+------+------------+-----------+
```

id 为 5、6、7、8 的四行 id 连续，并且每行都有 >= 100 的人数记录。

请注意，即使第 7 行和第 8 行的 visit_date 不是连续的，输出也应当包含第 8 行，因为我们只需要考虑 id 连续的记录。

不输出 id 为 2 和 3 的行，因为至少需要三条 id 连续的记录。

', 'examDataFiles/auto_upload_601_1637126775642.sql', '2021-11-17 13:26:15', '2021-11-17 13:26:15', 'auto_upload_601_1637126775642.sql', 3, 1);
INSERT INTO sqlexam.main_questions (id, teacher_id, title, `desc`, db_path, created_at, updated_at, file_name, total_difficulty, sub_count) VALUES (602, '0', '好友申请 II ：谁有最多的好友', '在 Facebook 或者 Twitter 这样的社交应用中，人们经常会发好友申请也会收到其他人的好友申请。

表request_accepted存储了所有好友申请通过的数据记录，其中， requester_id和 accepter_id都是用户的编号。

| requester_id | accepter_id | accept_date|

|--------------|-------------|------------|

| 1            | 2           | 2016_06-03 |

| 1            | 3           | 2016-06-08 |

| 2            | 3           | 2016-06-08 |

| 3            | 4           | 2016-06-09 |

写一个查询语句，求出谁拥有最多的好友和他拥有的好友数目。对于上面的样例数据，结果为：

| id | num |

|----|-----|

| 3  | 3   |

注意：

保证拥有最多好友数目的只有 1 个人。

好友申请只会被接受一次，所以不会有requester_id和accepter_id值都相同的重复记录。

解释：

编号为 ''3'' 的人是编号为 ''1''，''2'' 和 ''4'' 的好友，所以他总共有 3 个好友，比其他人都多。

进阶：

在真实世界里，可能会有多个人拥有好友数相同且最多，你能找到所有这些人吗？

', 'examDataFiles/auto_upload_602_1637126775642.sql', '2021-11-17 13:26:15', '2021-11-17 13:26:15', 'auto_upload_602_1637126775642.sql', 2, 1);
INSERT INTO sqlexam.main_questions (id, teacher_id, title, `desc`, db_path, created_at, updated_at, file_name, total_difficulty, sub_count) VALUES (603, '0', '连续空余座位', '几个朋友来到电影院的售票处，准备预约连续空余座位。

你能利用表cinema，帮他们写一个查询语句，获取所有空余座位，并将它们按照 seat_id 排序后返回吗？

| seat_id | free |

|---------|------|

| 1       | 1    |

| 2       | 0    |

| 3       | 1    |

| 4       | 1    |

| 5       | 1    |

对于如上样例，你的查询语句应该返回如下结果。

| seat_id |

|---------|

| 3       |

| 4       |

| 5       |

注意：

seat_id 字段是一个自增的整数，free 字段是布尔类型（''1'' 表示空余， ''0'' 表示已被占据）。

连续空余座位的定义是大于等于 2 个连续空余的座位。

', 'examDataFiles/auto_upload_603_1637126775642.sql', '2021-11-17 13:26:15', '2021-11-17 13:26:15', 'auto_upload_603_1637126775642.sql', 1, 1);
INSERT INTO sqlexam.main_questions (id, teacher_id, title, `desc`, db_path, created_at, updated_at, file_name, total_difficulty, sub_count) VALUES (607, '0', '销售员', '描述

给定 3 个表：salesperson，company，orders。

输出所有表salesperson中，没有向公司 ''RED'' 销售任何东西的销售员。

示例：

输入

表：salesperson

```
+----------+------+--------+-----------------+-----------+
| sales_id | name | salary | commission_rate | hire_date |
+----------+------+--------+-----------------+-----------+
|   1      | John | 100000 |     6           | 4/1/2006  |
|   2      | Amy  | 120000 |     5           | 5/1/2010  |
|   3      | Mark | 65000  |     12          | 12/25/2008|
|   4      | Pam  | 25000  |     25          | 1/1/2005  |
|   5      | Alex | 50000  |     10          | 2/3/2007  |
+----------+------+--------+-----------------+-----------+
```

表salesperson 存储了所有销售员的信息。每个销售员都有一个销售员编号sales_id 和他的名字name。

表：company

```
+---------+--------+------------+
| com_id  |  name  |    city    |
+---------+--------+------------+
|   1     |  RED   |   Boston   |
|   2     | ORANGE |   New York |
|   3     | YELLOW |   Boston   |
|   4     | GREEN  |   Austin   |
+---------+--------+------------+
```

表company存储了所有公司的信息。每个公司都有一个公司编号com_id和它的名字 name。

表：orders

```
+----------+------------+---------+----------+--------+
| order_id | order_date | com_id  | sales_id | amount |
+----------+------------+---------+----------+--------+
| 1        |   1/1/2014 |    3    |    4     | 100000 |
| 2        |   2/1/2014 |    4    |    5     | 5000   |
| 3        |   3/1/2014 |    1    |    1     | 50000  |
| 4        |   4/1/2014 |    1    |    4     | 25000  |
+----------+----------+---------+----------+--------+
```

表orders存储了所有的销售数据，包括销售员编号 sales_id 和公司编号 com_id。

输出

```
+------+
| name | 
+------+
| Amy  | 
| Mark | 
| Alex |
+------+
```

解释

根据表orders中的订单 ''3'' 和 ''4'' ，容易看出只有 ''John'' 和 ''Pam'' 两个销售员曾经向公司 ''RED'' 销售过。

所以我们需要输出表salesperson中所有其他人的名字。

', 'examDataFiles/auto_upload_607_1637126775642.sql', '2021-11-17 13:26:15', '2021-11-17 13:26:15', 'auto_upload_607_1637126775642.sql', 1, 1);
INSERT INTO sqlexam.main_questions (id, teacher_id, title, `desc`, db_path, created_at, updated_at, file_name, total_difficulty, sub_count) VALUES (608, '0', '树节点', '给定一个表tree，id 是树节点的编号，p_id是它父节点的id 。

```
+----+------+
| id | p_id |
+----+------+
| 1  | null |
| 2  | 1    |
| 3  | 1    |
| 4  | 2    |
| 5  | 2    |
+----+------+
```

树中每个节点属于以下三种类型之一：

叶子：如果这个节点没有任何孩子节点。

根：如果这个节点是整棵树的根，即没有父节点。

内部节点：如果这个节点既不是叶子节点也不是根节点。

写一个查询语句，输出所有节点的编号和节点的类型，并将结果按照节点编号排序。上面样例的结果为：

```
+----+------+
| id | Type |
+----+------+
| 1  | Root |
| 2  | Inner|
| 3  | Leaf |
| 4  | Leaf |
| 5  | Leaf |
+----+------+
```

解释

节点 ''1'' 是根节点，因为它的父节点是 NULL ，同时它有孩子节点 ''2'' 和 ''3'' 。

节点 ''2'' 是内部节点，因为它有父节点 ''1'' ，也有孩子节点 ''4'' 和 ''5'' 。

节点 ''3'', ''4'' 和 ''5'' 都是叶子节点，因为它们都有父节点同时没有孩子节点。

样例中树的形态如下：

	

			  1

			/   \\

                      2       3

                    /   \\

                  4       5

注意

如果树中只有一个节点，你只需要输出它的根属性。

', 'examDataFiles/auto_upload_608_1637126775642.sql', '2021-11-17 13:26:15', '2021-11-17 13:26:15', 'auto_upload_608_1637126775642.sql', 2, 1);
INSERT INTO sqlexam.main_questions (id, teacher_id, title, `desc`, db_path, created_at, updated_at, file_name, total_difficulty, sub_count) VALUES (612, '0', '平面上的最近距离', '表point_2d保存了所有点（多于 2 个点）的坐标 (x,y) ，这些点在平面上两两不重合。

写一个查询语句找到两点之间的最近距离，保留 2 位小数。

| x  | y  |

|----|----|

| -1 | -1 |

| 0  | 0  |

| -1 | -2 |

最近距离在点 (-1,-1) 和(-1,2) 之间，距离为 1.00 。所以输出应该为：

| shortest |

|----------|

| 1.00     |

注意：任意点之间的最远距离小于 10000 。

', 'examDataFiles/auto_upload_612_1637126775642.sql', '2021-11-17 13:26:15', '2021-11-17 13:26:15', 'auto_upload_612_1637126775642.sql', 2, 1);
INSERT INTO sqlexam.main_questions (id, teacher_id, title, `desc`, db_path, created_at, updated_at, file_name, total_difficulty, sub_count) VALUES (613, '0', '直线上的最近距离', '表point保存了一些点在 x 轴上的坐标，这些坐标都是整数。

写一个查询语句，找到这些点中最近两个点之间的距离。

| x   |

|-----|

| -1  |

| 0   |

| 2   |

最近距离显然是 ''1'' ，是点 ''-1'' 和 ''0'' 之间的距离。所以输出应该如下：

| shortest|

|---------|

| 1       |

注意：每个点都与其他点坐标不同，表table不会有重复坐标出现。

进阶：如果这些点在 x 轴上从左到右都有一个编号，输出结果时需要输出最近点对的编号呢？

', 'examDataFiles/auto_upload_613_1637126775642.sql', '2021-11-17 13:26:15', '2021-11-17 13:26:15', 'auto_upload_613_1637126775642.sql', 1, 1);
INSERT INTO sqlexam.main_questions (id, teacher_id, title, `desc`, db_path, created_at, updated_at, file_name, total_difficulty, sub_count) VALUES (614, '0', '二级关注者', '在 facebook 中，表follow会有 2 个字段： followee, follower，分别表示被关注者和关注者。

请写一个 sql 查询语句，对每一个关注者，查询关注他的关注者的数目。

比方说：

```
+-------------+------------+
| followee    | follower   |
+-------------+------------+
|     A       |     B      |
|     B       |     C      |
|     B       |     D      |
|     D       |     E      |
+-------------+------------+
```

应该输出：

```
+-------------+------------+
| follower    | num        |
+-------------+------------+
|     B       |  2         |
|     D       |  1         |
+-------------+------------+
```

解释：

B 和 D 都在在follower字段中出现，作为被关注者，B 被 C 和 D 关注，D 被 E 关注。A 不在 follower字段内，所以A不在输出列表中。

注意：

被关注者永远不会被他 / 她自己关注。

将结果按照字典序返回。

', 'examDataFiles/auto_upload_614_1637126775642.sql', '2021-11-17 13:26:15', '2021-11-17 13:26:15', 'auto_upload_614_1637126775642.sql', 2, 1);
INSERT INTO sqlexam.main_questions (id, teacher_id, title, `desc`, db_path, created_at, updated_at, file_name, total_difficulty, sub_count) VALUES (618, '0', '学生地理信息报告', '一所美国大学有来自亚洲、欧洲和美洲的学生，他们的地理信息存放在如下student 表中。

| name   | continent |

|--------|-----------|

| Jack   | America   |

| Pascal | Europe    |

| Xi     | Asia      |

| Jane   | America   |

写一个查询语句实现对大洲（continent）列的透视表 操作，使得每个学生按照姓名的字母顺序依次排列在对应的大洲下面。输出的标题应依次为美洲（America）、亚洲（Asia）和欧洲（Europe）。

对于样例输入，它的对应输出是：

| America | Asia | Europe |

|---------|------|--------|

| Jack    | Xi   | Pascal |

| Jane    |      |        |

进阶：如果不能确定哪个大洲的学生数最多，你可以写出一个查询去生成上述学生报告吗？

', 'examDataFiles/auto_upload_618_1637126775642.sql', '2021-11-17 13:26:15', '2021-11-17 13:26:15', 'auto_upload_618_1637126775642.sql', 3, 1);
INSERT INTO sqlexam.main_questions (id, teacher_id, title, `desc`, db_path, created_at, updated_at, file_name, total_difficulty, sub_count) VALUES (619, '0', '只出现一次的最大数字', '表my_numbers的 num字段包含很多数字，其中包括很多重复的数字。

你能写一个 SQL 查询语句，找到只出现过一次的数字中，最大的一个数字吗？

```
+---+
|num|
+---+
| 8 |
| 8 |
| 3 |
| 3 |
| 1 |
| 4 |
| 5 |
| 6 | 
对于上面给出的样例数据，你的查询语句应该返回如下结果：
+---+
|num|
+---+
| 6 |
注意：
如果没有只出现一次的数字，输出null。
', 'examDataFiles/auto_upload_619_1637126775642.sql', '2021-11-17 13:26:15', '2021-11-17 13:26:15', 'auto_upload_619_1637126775642.sql', 1, 1);
INSERT INTO sqlexam.main_questions (id, teacher_id, title, `desc`, db_path, created_at, updated_at, file_name, total_difficulty, sub_count) VALUES (620, '0', '有趣的电影', '某城市开了一家新的电影院，吸引了很多人过来看电影。该电影院特别注意用户体验，专门有个 LED显示板做电影推荐，上面公布着影评和相关电影描述。

作为该电影院的信息部主管，您需要编写一个 SQL查询，找出所有影片描述为非boring(不无聊)的并且 id 为奇数的影片，结果请按等级 rating 排列。

例如，下表 cinema:

```
+---------+-----------+--------------+-----------+
|   id    | movie     |  description |  rating   |
+---------+-----------+--------------+-----------+
|   1     | War       |   great 3D   |   8.9     |
|   2     | Science   |   fiction    |   8.5     |
|   3     | irish     |   boring     |   6.2     |
|   4     | Ice song  |   Fantacy    |   8.6     |
|   5     | House card|   Interesting|   9.1     |
+---------+-----------+--------------+-----------+
```

对于上面的例子，则正确的输出是为：

```
+---------+-----------+--------------+-----------+
|   id    | movie     |  description |  rating   |
+---------+-----------+--------------+-----------+
|   5     | House card|   Interesting|   9.1     |
|   1     | War       |   great 3D   |   8.9     |
+---------+-----------+--------------+-----------+
```

', 'examDataFiles/auto_upload_620_1637126775642.sql', '2021-11-17 13:26:15', '2021-11-17 13:26:15', 'auto_upload_620_1637126775642.sql', 1, 1);
INSERT INTO sqlexam.main_questions (id, teacher_id, title, `desc`, db_path, created_at, updated_at, file_name, total_difficulty, sub_count) VALUES (626, '0', '换座位', '小美是一所中学的信息科技老师，她有一张 seat座位表，平时用来储存学生名字和与他们相对应的座位 id。

其中纵列的id是连续递增的

小美想改变相邻俩学生的座位。

你能不能帮她写一个 SQL query来输出小美想要的结果呢？

示例：

```
+---------+---------+
|    id   | student |
+---------+---------+
|    1    | Abbot   |
|    2    | Doris   |
|    3    | Emerson |
|    4    | Green   |
|    5    | Jeames  |
+---------+---------+
```

假如数据输入的是上表，则输出结果如下：

```
+---------+---------+
|    id   | student |
+---------+---------+
|    1    | Doris   |
|    2    | Abbot   |
|    3    | Green   |
|    4    | Emerson |
|    5    | Jeames  |
+---------+---------+
```

注意：

如果学生人数是奇数，则不需要改变最后一个同学的座位。

', 'examDataFiles/auto_upload_626_1637126775642.sql', '2021-11-17 13:26:15', '2021-11-17 13:26:15', 'auto_upload_626_1637126775642.sql', 2, 1);
INSERT INTO sqlexam.main_questions (id, teacher_id, title, `desc`, db_path, created_at, updated_at, file_name, total_difficulty, sub_count) VALUES (627, '0', '变更性别', '给定一个salary表，如下所示，有 m = 男性 和 f = 女性 的值。交换所有的 f 和 m 值（例如，将所有 f 值更改为 m，反之亦然）。要求只使用一个更新（Update）语句，并且没有中间的临时表。

注意，您必只能写一个 Update 语句，请不要编写任何 Select 语句。

例如：

| id | name | sex | salary |

|----|------|-----|--------|

| 1  | A    | m   | 2500   |

| 2  | B    | f   | 1500   |

| 3  | C    | m   | 5500   |

| 4  | D    | f   | 500    |

运行你所编写的更新语句之后，将会得到以下表:

| id | name | sex | salary |

|----|------|-----|--------|

| 1  | A    | f   | 2500   |

| 2  | B    | m   | 1500   |

| 3  | C    | f   | 5500   |

| 4  | D    | m   | 500    |

', 'examDataFiles/auto_upload_627_1637126775642.sql', '2021-11-17 13:26:15', '2021-11-17 13:26:15', 'auto_upload_627_1637126775642.sql', 1, 1);
INSERT INTO sqlexam.main_questions (id, teacher_id, title, `desc`, db_path, created_at, updated_at, file_name, total_difficulty, sub_count) VALUES (1135, '0', '买下所有产品的客户', 'Customer表：

```
+-------------+---------+
| Column Name | Type    |
+-------------+---------+
| customer_id | int     |
| product_key | int     |
+-------------+---------+
```

product_key 是 Customer 表的外键。

Product表：

```
+-------------+---------+
| Column Name | Type    |
+-------------+---------+
| product_key | int     |
+-------------+---------+
```

product_key 是这张表的主键。

写一条 SQL 查询语句，从 Customer 表中查询购买了 Product 表中所有产品的客户的 id。

示例：

Customer 表：

```
+-------------+-------------+
| customer_id | product_key |
+-------------+-------------+
| 1           | 5           |
| 2           | 6           |
| 3           | 5           |
| 3           | 6           |
| 1           | 6           |
+-------------+-------------+
```

Product 表：

```
+-------------+
| product_key |
+-------------+
| 5           |
| 6           |
+-------------+
```

Result 表：

```
+-------------+
| customer_id |
+-------------+
| 1           |
| 3           |
+-------------+
```

购买了所有产品（5 和 6）的客户的 id 是 1 和 3 。

', 'examDataFiles/auto_upload_1135_1637126775642.sql', '2021-11-17 13:26:15', '2021-11-17 13:26:15', 'auto_upload_1135_1637126775642.sql', 2, 1);
INSERT INTO sqlexam.main_questions (id, teacher_id, title, `desc`, db_path, created_at, updated_at, file_name, total_difficulty, sub_count) VALUES (1136, '0', '合作过至少三次的演员和导演', 'ActorDirector表：

```
+-------------+---------+
| Column Name | Type    |
+-------------+---------+
| actor_id    | int     |
| director_id | int     |
| timestamp   | int     |
+-------------+---------+
```

timestamp 是这张表的主键.

写一条SQL查询语句获取合作过至少三次的演员和导演的 id 对(actor_id, director_id)

示例：

ActorDirector 表：

```
+-------------+-------------+-------------+
| actor_id    | director_id | timestamp   |
+-------------+-------------+-------------+
| 1           | 1           | 0           |
| 1           | 1           | 1           |
| 1           | 1           | 2           |
| 1           | 2           | 3           |
| 1           | 2           | 4           |
| 2           | 1           | 5           |
| 2           | 1           | 6           |
+-------------+-------------+-------------+
```

Result 表：

```
+-------------+-------------+
| actor_id    | director_id |
+-------------+-------------+
| 1           | 1           |
+-------------+-------------+
```

唯一的 id 对是 (1, 1)，他们恰好合作了 3 次。

', 'examDataFiles/auto_upload_1136_1637126775642.sql', '2021-11-17 13:26:15', '2021-11-17 13:26:15', 'auto_upload_1136_1637126775642.sql', 1, 1);
INSERT INTO sqlexam.main_questions (id, teacher_id, title, `desc`, db_path, created_at, updated_at, file_name, total_difficulty, sub_count) VALUES (1161, '0', '项目员工 I', '项目表Project：

```
+-------------+---------+
| Column Name | Type    |
+-------------+---------+
| project_id  | int     |
| employee_id | int     |
+-------------+---------+
```

主键为 (project_id, employee_id)。

employee_id 是员工表 Employee 表的外键。

员工表Employee：

```
+------------------+---------+
| Column Name      | Type    |
+------------------+---------+
| employee_id      | int     |
| name             | varchar |
| experience_years | int     |
+------------------+---------+
```

主键是 employee_id。

请写一个 SQL语句，查询每一个项目中员工的平均工作年限，精确到小数点后两位。

查询结果的格式如下：

Project 表：

```
+-------------+-------------+
| project_id  | employee_id |
+-------------+-------------+
| 1           | 1           |
| 1           | 2           |
| 1           | 3           |
| 2           | 1           |
| 2           | 4           |
+-------------+-------------+
```

Employee 表：

```
+-------------+--------+------------------+
| employee_id | name   | experience_years |
+-------------+--------+------------------+
| 1           | Khaled | 3                |
| 2           | Ali    | 2                |
| 3           | John   | 1                |
| 4           | Doe    | 2                |
+-------------+--------+------------------+
```

Result 表：

```
+-------------+---------------+
| project_id  | average_years |
+-------------+---------------+
| 1           | 2.00          |
| 2           | 2.50          |
+-------------+---------------+
```

第一个项目中，员工的平均工作年限是 (3 + 2 + 1) / 3 = 2.00；第二个项目中，员工的平均工作年限是 (3 + 2) / 2 = 2.50

', 'examDataFiles/auto_upload_1161_1637126775642.sql', '2021-11-17 13:26:15', '2021-11-17 13:26:15', 'auto_upload_1161_1637126775642.sql', 1, 1);
INSERT INTO sqlexam.main_questions (id, teacher_id, title, `desc`, db_path, created_at, updated_at, file_name, total_difficulty, sub_count) VALUES (1162, '0', '项目员工II', 'Table:Project

```
+-------------+---------+
| Column Name | Type    |
+-------------+---------+
| project_id  | int     |
| employee_id | int     |
+-------------+---------+
```

主键为 (project_id, employee_id)。

employee_id 是员工表 Employee 表的外键。

Table:Employee

```
+------------------+---------+
| Column Name      | Type    |
+------------------+---------+
| employee_id      | int     |
| name             | varchar |
| experience_years | int     |
+------------------+---------+
```

主键是 employee_id。

编写一个SQL查询，报告所有雇员最多的项目。

查询结果格式如下所示：

Project table:

```
+-------------+-------------+
| project_id  | employee_id |
+-------------+-------------+
| 1           | 1           |
| 1           | 2           |
| 1           | 3           |
| 2           | 1           |
| 2           | 4           |
+-------------+-------------+
```

Employee table:

```
+-------------+--------+------------------+
| employee_id | name   | experience_years |
+-------------+--------+------------------+
| 1           | Khaled | 3                |
| 2           | Ali    | 2                |
| 3           | John   | 1                |
| 4           | Doe    | 2                |
+-------------+--------+------------------+
```

Result table:

```
+-------------+
| project_id  |
+-------------+
| 1           |
+-------------+
```

第一个项目有3名员工，第二个项目有2名员工。

', 'examDataFiles/auto_upload_1162_1637126775642.sql', '2021-11-17 13:26:15', '2021-11-17 13:26:15', 'auto_upload_1162_1637126775642.sql', 1, 1);
INSERT INTO sqlexam.main_questions (id, teacher_id, title, `desc`, db_path, created_at, updated_at, file_name, total_difficulty, sub_count) VALUES (1163, '0', '项目员工 III', '项目表Project：

```
+-------------+---------+
| Column Name | Type    |
+-------------+---------+
| project_id  | int     |
| employee_id | int     |
+-------------+---------+
```

(project_id, employee_id) 是这个表的主键

employee_id 是员工表 Employee 的外键

员工表Employee：

```
+------------------+---------+
| Column Name      | Type    |
+------------------+---------+
| employee_id      | int     |
| name             | varchar |
| experience_years | int     |
+------------------+---------+
```

employee_id 是这个表的主键

写 一个 SQL 查询语句，报告在每一个项目中经验最丰富的雇员是谁。如果出现经验年数相同的情况，请报告所有具有最大经验年数的员工。

查询结果格式在以下示例中：

Project 表：

```
+-------------+-------------+
| project_id  | employee_id |
+-------------+-------------+
| 1           | 1           |
| 1           | 2           |
| 1           | 3           |
| 2           | 1           |
| 2           | 4           |
+-------------+-------------+
```

Employee 表：

```
+-------------+--------+------------------+
| employee_id | name   | experience_years |
+-------------+--------+------------------+
| 1           | Khaled | 3                |
| 2           | Ali    | 2                |
| 3           | John   | 3                |
| 4           | Doe    | 2                |
+-------------+--------+------------------+
```

Result 表：

```
+-------------+---------------+
| project_id  | employee_id   |
+-------------+---------------+
| 1           | 1             |
| 1           | 3             |
| 2           | 1             |
+-------------+---------------+
```

employee_id 为 1 和 3 的员工在 project_id 为 1 的项目中拥有最丰富的经验。在 project_id 为 2 的项目中，employee_id 为 1 的员工拥有最丰富的经验。

', 'examDataFiles/auto_upload_1163_1637126775642.sql', '2021-11-17 13:26:15', '2021-11-17 13:26:15', 'auto_upload_1163_1637126775642.sql', 2, 1);
INSERT INTO sqlexam.main_questions (id, teacher_id, title, `desc`, db_path, created_at, updated_at, file_name, total_difficulty, sub_count) VALUES (1172, '0', '销售分析 I ', '产品表：Product

```
+--------------+---------+
| Column Name  | Type    |
+--------------+---------+
| product_id   | int     |
| product_name | varchar |
| unit_price   | int     |
+--------------+---------+
```

product_id 是这个表的主键.

销售表：Sales

```
+-------------+---------+
| Column Name | Type    |
+-------------+---------+
| seller_id   | int     |
| product_id  | int     |
| buyer_id    | int     |
| sale_date   | date    |
| quantity    | int     |
| price       | int     |
+------ ------+---------+
```

这个表没有主键，它可以有重复的行.

product_id 是 Product 表的外键.

编写一个 SQL 查询，查询总销售额最高的销售者，如果有并列的，就都展示出来。

查询结果格式如下所示：

Product 表：

```
+------------+--------------+------------+
| product_id | product_name | unit_price |
+------------+--------------+------------+
| 1          | S8           | 1000       |
| 2          | G4           | 800        |
| 3          | iPhone       | 1400       |
+------------+--------------+------------+
```

Sales 表：

```
+-----------+------------+----------+------------+----------+-------+
| seller_id | product_id | buyer_id | sale_date  | quantity | price |
+-----------+------------+----------+------------+----------+-------+
| 1         | 1          | 1        | 2019-01-21 | 2        | 2000  |
| 1         | 2          | 2        | 2019-02-17 | 1        | 800   |
| 2         | 2          | 3        | 2019-06-02 | 1        | 800   |
| 3         | 3          | 4        | 2019-05-13 | 2        | 2800  |
+-----------+------------+----------+------------+----------+-------+
```

Result 表：

```
+-------------+
| seller_id   |
+-------------+
| 1           |
| 3           |
+-------------+
```

Id 为 1 和 3 的销售者，销售总金额都为最高的 2800。

', 'examDataFiles/auto_upload_1172_1637126775642.sql', '2021-11-17 13:26:15', '2021-11-17 13:26:15', 'auto_upload_1172_1637126775642.sql', 1, 1);
INSERT INTO sqlexam.main_questions (id, teacher_id, title, `desc`, db_path, created_at, updated_at, file_name, total_difficulty, sub_count) VALUES (1173, '0', '销售分析 II', 'Table:Product

```
+--------------+---------+
| Column Name  | Type    |
+--------------+---------+
| product_id   | int     |
| product_name | varchar |
| unit_price   | int     |
+--------------+---------+
```

product_id 是这张表的主键

Table:Sales

```
+-------------+---------+
| Column Name | Type    |
+-------------+---------+
| seller_id   | int     |
| product_id  | int     |
| buyer_id    | int     |
| sale_date   | date    |
| quantity    | int     |
| price       | int     |
+------ ------+---------+
```

这个表没有主键，它可以有重复的行.

product_id 是 Product 表的外键.

编写一个 SQL 查询，查询购买了 S8 手机却没有购买 iPhone 的买家。注意这里 S8 和 iPhone 是 Product 表中的产品。

查询结果格式如下图表示：

Product table:

```
+------------+--------------+------------+
| product_id | product_name | unit_price |
+------------+--------------+------------+
| 1          | S8           | 1000       |
| 2          | G4           | 800        |
| 3          | iPhone       | 1400       |
+------------+--------------+------------+
```

Sales table:

```
+-----------+------------+----------+------------+----------+-------+
| seller_id | product_id | buyer_id | sale_date  | quantity | price |
+-----------+------------+----------+------------+----------+-------+
| 1         | 1          | 1        | 2019-01-21 | 2        | 2000  |
| 1         | 2          | 2        | 2019-02-17 | 1        | 800   |
| 2         | 1          | 3        | 2019-06-02 | 1        | 800   |
| 3         | 3          | 3        | 2019-05-13 | 2        | 2800  |
+-----------+------------+----------+------------+----------+-------+
```

Result table:

```
+-------------+
| buyer_id    |
+-------------+
| 1           |
+-------------+
```

id 为 1 的买家购买了一部 S8，但是却没有购买 iPhone，而 id 为 3 的买家却同时购买了这 2 部手机。

', 'examDataFiles/auto_upload_1173_1637126775642.sql', '2021-11-17 13:26:15', '2021-11-17 13:26:15', 'auto_upload_1173_1637126775642.sql', 1, 1);
INSERT INTO sqlexam.main_questions (id, teacher_id, title, `desc`, db_path, created_at, updated_at, file_name, total_difficulty, sub_count) VALUES (1174, '0', '销售分析III', 'Table:Product

```
+--------------+---------+
| Column Name  | Type    |
+--------------+---------+
| product_id   | int     |
| product_name | varchar |
| unit_price   | int     |
+--------------+---------+
```

product_id 是这个表的主键

Table:Sales

```
+-------------+---------+
| Column Name | Type    |
+-------------+---------+
| seller_id   | int     |
| product_id  | int     |
| buyer_id    | int     |
| sale_date   | date    |
| quantity    | int     |
| price       | int     |
+------ ------+---------+
```

这个表没有主键，它可以有重复的行.

product_id 是 Product 表的外键.

编写一个SQL查询，报告2019年春季才售出的产品。即仅在2019-01-01至2019-03-31（含）之间出售的商品。

查询结果格式如下所示：

Product table:

```
+------------+--------------+------------+
| product_id | product_name | unit_price |
+------------+--------------+------------+
| 1          | S8           | 1000       |
| 2          | G4           | 800        |
| 3          | iPhone       | 1400       |
+------------+--------------+------------+
```

Sales table:

```
+-----------+------------+----------+------------+----------+-------+
| seller_id | product_id | buyer_id | sale_date  | quantity | price |
+-----------+------------+----------+------------+----------+-------+
| 1         | 1          | 1        | 2019-01-21 | 2        | 2000  |
| 1         | 2          | 2        | 2019-02-17 | 1        | 800   |
| 2         | 2          | 3        | 2019-06-02 | 1        | 800   |
| 3         | 3          | 4        | 2019-05-13 | 2        | 2800  |
+-----------+------------+----------+------------+----------+-------+
```

Result table:

```
+-------------+--------------+
| product_id  | product_name |
+-------------+--------------+
| 1           | S8           |
+-------------+--------------+
```

id为1的产品仅在2019年春季销售，其他两个产品在之后销售。

', 'examDataFiles/auto_upload_1174_1637126775642.sql', '2021-11-17 13:26:15', '2021-11-17 13:26:15', 'auto_upload_1174_1637126775642.sql', 1, 1);
INSERT INTO sqlexam.main_questions (id, teacher_id, title, `desc`, db_path, created_at, updated_at, file_name, total_difficulty, sub_count) VALUES (1179, '0', '游戏玩法分析 I', '活动表Activity：

```
+--------------+---------+
| Column Name  | Type    |
+--------------+---------+
| player_id    | int     |
| device_id    | int     |
| event_date   | date    |
| games_played | int     |
+--------------+---------+
```

表的主键是 (player_id, event_date)。

这张表展示了一些游戏玩家在游戏平台上的行为活动。

每行数据记录了一名玩家在退出平台之前，当天使用同一台设备登录平台后打开的游戏的数目（可能是 0 个）。

写一条 SQL查询语句获取每位玩家 第一次登陆平台的日期。

查询结果的格式如下所示：

Activity 表：

```
+-----------+-----------+------------+--------------+
| player_id | device_id | event_date | games_played |
+-----------+-----------+------------+--------------+
| 1         | 2         | 2016-03-01 | 5            |
| 1         | 2         | 2016-05-02 | 6            |
| 2         | 3         | 2017-06-25 | 1            |
| 3         | 1         | 2016-03-02 | 0            |
| 3         | 4         | 2018-07-03 | 5            |
+-----------+-----------+------------+--------------+
```

Result 表：

```
+-----------+-------------+
| player_id | first_login |
+-----------+-------------+
| 1         | 2016-03-01  |
| 2         | 2017-06-25  |
| 3         | 2016-03-02  |
+-----------+-------------+
```

', 'examDataFiles/auto_upload_1179_1637126775642.sql', '2021-11-17 13:26:15', '2021-11-17 13:26:15', 'auto_upload_1179_1637126775642.sql', 1, 1);
INSERT INTO sqlexam.main_questions (id, teacher_id, title, `desc`, db_path, created_at, updated_at, file_name, total_difficulty, sub_count) VALUES (1180, '0', '游戏玩法分析 II', 'Table:Activity

```
+--------------+---------+
| Column Name  | Type    |
+--------------+---------+
| player_id    | int     |
| device_id    | int     |
| event_date   | date    |
| games_played | int     |
+--------------+---------+
```

(player_id, event_date) 是这个表的两个主键

这个表显示的是某些游戏玩家的游戏活动情况

每一行是在某天使用某个设备登出之前登录并玩多个游戏（可能为0）的玩家的记录

请编写一个 SQL 查询，描述每一个玩家首次登陆的设备名称

查询结果格式在以下示例中：

Activity table:

```
+-----------+-----------+------------+--------------+
| player_id | device_id | event_date | games_played |
+-----------+-----------+------------+--------------+
| 1         | 2         | 2016-03-01 | 5            |
| 1         | 2         | 2016-05-02 | 6            |
| 2         | 3         | 2017-06-25 | 1            |
| 3         | 1         | 2016-03-02 | 0            |
| 3         | 4         | 2018-07-03 | 5            |
+-----------+-----------+------------+--------------+
```

Result table:

```
+-----------+-----------+
| player_id | device_id |
+-----------+-----------+
| 1         | 2         |
| 2         | 3         |
| 3         | 1         |
+-----------+-----------+
```

', 'examDataFiles/auto_upload_1180_1637126775642.sql', '2021-11-17 13:26:15', '2021-11-17 13:26:15', 'auto_upload_1180_1637126775642.sql', 1, 1);
INSERT INTO sqlexam.main_questions (id, teacher_id, title, `desc`, db_path, created_at, updated_at, file_name, total_difficulty, sub_count) VALUES (1181, '0', '游戏玩法分析 III', 'Table:Activity

```
+--------------+---------+
| Column Name  | Type    |
+--------------+---------+
| player_id    | int     |
| device_id    | int     |
| event_date   | date    |
| games_played | int     |
+--------------+---------+
```

（player_id，event_date）是此表的主键。

这张表显示了某些游戏的玩家的活动情况。

每一行是一个玩家的记录，他在某一天使用某个设备注销之前登录并玩了很多游戏（可能是 0 ）。

编写一个 SQL 查询，同时报告每组玩家和日期，以及玩家到目前为止玩了多少游戏。也就是说，在此日期之前玩家所玩的游戏总数。详细情况请查看示例。

查询结果格式如下所示：

Activity table:

```
+-----------+-----------+------------+--------------+
| player_id | device_id | event_date | games_played |
+-----------+-----------+------------+--------------+
| 1         | 2         | 2016-03-01 | 5            |
| 1         | 2         | 2016-05-02 | 6            |
| 1         | 3         | 2017-06-25 | 1            |
| 3         | 1         | 2016-03-02 | 0            |
| 3         | 4         | 2018-07-03 | 5            |
+-----------+-----------+------------+--------------+
```

Result table:

```
+-----------+------------+---------------------+
| player_id | event_date | games_played_so_far |
+-----------+------------+---------------------+
| 1         | 2016-03-01 | 5                   |
| 1         | 2016-05-02 | 11                  |
| 1         | 2017-06-25 | 12                  |
| 3         | 2016-03-02 | 0                   |
| 3         | 2018-07-03 | 5                   |
+-----------+------------+---------------------+
```

对于 ID 为 1 的玩家，2016-05-02 共玩了 5+6=11 个游戏，2017-06-25 共玩了 5+6+1=12 个游戏。

对于 ID 为 3 的玩家，2018-07-03 共玩了 0+5=5 个游戏。

请注意，对于每个玩家，我们只关心玩家的登录日期。

', 'examDataFiles/auto_upload_1181_1637126775642.sql', '2021-11-17 13:26:15', '2021-11-17 13:26:15', 'auto_upload_1181_1637126775642.sql', 2, 1);
INSERT INTO sqlexam.main_questions (id, teacher_id, title, `desc`, db_path, created_at, updated_at, file_name, total_difficulty, sub_count) VALUES (1182, '0', '游戏玩法分析 IV', 'Table:Activity

```
+--------------+---------+
| Column Name  | Type    |
+--------------+---------+
| player_id    | int     |
| device_id    | int     |
| event_date   | date    |
| games_played | int     |
+--------------+---------+
```

（player_id，event_date）是此表的主键。

这张表显示了某些游戏的玩家的活动情况。

每一行是一个玩家的记录，他在某一天使用某个设备注销之前登录并玩了很多游戏（可能是 0）。

编写一个 SQL 查询，报告在首次登录的第二天再次登录的玩家的比率，四舍五入到小数点后两位。换句话说，您需要计算从首次登录日期开始至少连续两天登录的玩家的数量，然后除以玩家总数。

查询结果格式如下所示：

Activity table:

```
+-----------+-----------+------------+--------------+
| player_id | device_id | event_date | games_played |
+-----------+-----------+------------+--------------+
| 1         | 2         | 2016-03-01 | 5            |
| 1         | 2         | 2016-03-02 | 6            |
| 2         | 3         | 2017-06-25 | 1            |
| 3         | 1         | 2016-03-02 | 0            |
| 3         | 4         | 2018-07-03 | 5            |
+-----------+-----------+------------+--------------+
```

Result table:

```
+-----------+
| fraction  |
+-----------+
| 0.33      |
+-----------+
```

只有 ID 为 1 的玩家在第一天登录后才重新登录，所以答案是 1/3 = 0.33

', 'examDataFiles/auto_upload_1182_1637126775642.sql', '2021-11-17 13:26:15', '2021-11-17 13:26:15', 'auto_upload_1182_1637126775642.sql', 2, 1);
INSERT INTO sqlexam.main_questions (id, teacher_id, title, `desc`, db_path, created_at, updated_at, file_name, total_difficulty, sub_count) VALUES (1193, '0', '游戏玩法分析 V', 'Activity 活动记录表

```
+--------------+---------+
| Column Name  | Type    |
+--------------+---------+
| player_id    | int     |
| device_id    | int     |
| event_date   | date    |
| games_played | int     |
+--------------+---------+
```

（player_id，event_date）是此表的主键

这张表显示了某些游戏的玩家的活动情况

每一行表示一个玩家的记录，在某一天使用某个设备注销之前，登录并玩了很多游戏（可能是 0）

玩家的 安装日期 定义为该玩家的第一个登录日。

玩家的 第一天留存率 定义为：假定安装日期为 X的玩家的数量为 N ，其中在 X之后的一天重新登录的玩家数量为 M ，M/N 就是第一天留存率，四舍五入到小数点后两位。

编写一个 SQL 查询，报告所有安装日期、当天安装游戏的玩家数量和玩家的第一天留存率。

查询结果格式如下所示：

Activity 表：

```
+-----------+-----------+------------+--------------+
| player_id | device_id | event_date | games_played |
+-----------+-----------+------------+--------------+
| 1         | 2         | 2016-03-01 | 5            |
| 1         | 2         | 2016-03-02 | 6            |
| 2         | 3         | 2017-06-25 | 1            |
| 3         | 1         | 2016-03-01 | 0            |
| 3         | 4         | 2016-07-03 | 5            |
+-----------+-----------+------------+--------------+
```

Result 表：

```
+------------+----------+----------------+
| install_dt | installs | Day1_retention |
+------------+----------+----------------+
| 2016-03-01 | 2        | 0.50           |
| 2017-06-25 | 1        | 0.00           |
+------------+----------+----------------+
```

玩家 1 和 3 在 2016-03-01 安装了游戏，但只有玩家 1 在 2016-03-02 重新登录，所以 2016-03-01 的第一天留存率是 1/2=0.50

玩家 2 在 2017-06-25 安装了游戏，但在 2017-06-26 没有重新登录，因此 2017-06-25 的第一天留存率为 0/1=0.00

', 'examDataFiles/auto_upload_1193_1637126775642.sql', '2021-11-17 13:26:15', '2021-11-17 13:26:15', 'auto_upload_1193_1637126775642.sql', 3, 1);
INSERT INTO sqlexam.main_questions (id, teacher_id, title, `desc`, db_path, created_at, updated_at, file_name, total_difficulty, sub_count) VALUES (1198, '0', '小众书籍', '书籍表Books：

```
+----------------+---------+
| Column Name    | Type    |
+----------------+---------+
| book_id        | int     |
| name           | varchar |
| available_from | date    |
+----------------+---------+
```

book_id 是这个表的主键。

订单表Orders：

```
+----------------+---------+
| Column Name    | Type    |
+----------------+---------+
| order_id       | int     |
| book_id        | int     |
| quantity       | int     |
| dispatch_date  | date    |
+----------------+---------+
```

order_id 是这个表的主键。

book_id  是 Books 表的外键。

你需要写一段 SQL 命令，筛选出过去一年中订单总量少于10本的书籍。

注意：不考虑上架（available from）距今不满一个月 的书籍。并且假设今天是2019-06-23。

下面是样例输出结果：

Books 表：

```
+---------+--------------------+----------------+
| book_id | name               | available_from |
+---------+--------------------+----------------+
| 1       | "Kalila And Demna" | 2010-01-01     |
| 2       | "28 Letters"       | 2012-05-12     |
| 3       | "The Hobbit"       | 2019-06-10     |
| 4       | "13 Reasons Why"   | 2019-06-01     |
| 5       | "The Hunger Games" | 2008-09-21     |
+---------+--------------------+----------------+
```

Orders 表：

```
+----------+---------+----------+---------------+
| order_id | book_id | quantity | dispatch_date |
+----------+---------+----------+---------------+
| 1        | 1       | 2        | 2018-07-26    |
| 2        | 1       | 1        | 2018-11-05    |
| 3        | 3       | 8        | 2019-06-11    |
| 4        | 4       | 6        | 2019-06-05    |
| 5        | 4       | 5        | 2019-06-20    |
| 6        | 5       | 9        | 2009-02-02    |
| 7        | 5       | 8        | 2010-04-13    |
+----------+---------+----------+---------------+
```

Result 表：

```
+-----------+--------------------+
| book_id   | name               |
+-----------+--------------------+
| 1         | "Kalila And Demna" |
| 2         | "28 Letters"       |
| 5         | "The Hunger Games" |
+-----------+--------------------+
```

', 'examDataFiles/auto_upload_1198_1637126775642.sql', '2021-11-17 13:26:15', '2021-11-17 13:26:15', 'auto_upload_1198_1637126775642.sql', 2, 1);
INSERT INTO sqlexam.main_questions (id, teacher_id, title, `desc`, db_path, created_at, updated_at, file_name, total_difficulty, sub_count) VALUES (1204, '0', '每日新用户统计', 'Traffic表：

```
+---------------+---------+
| Column Name   | Type    |
+---------------+---------+
| user_id       | int     |
| activity      | enum    |
| activity_date | date    |
+---------------+---------+
```

该表没有主键，它可能有重复的行。

activity 列是 ENUM 类型，可能取 (''login'', ''logout'', ''jobs'', ''groups'', ''homepage'') 几个值之一。

编写一个 SQL 查询，以查询从今天起最多 90 天内，每个日期该日期首次登录的用户数。假设今天是2019-06-30.

查询结果格式如下例所示：

Traffic 表：

```
+---------+----------+---------------+
| user_id | activity | activity_date |
+---------+----------+---------------+
| 1       | login    | 2019-05-01    |
| 1       | homepage | 2019-05-01    |
| 1       | logout   | 2019-05-01    |
| 2       | login    | 2019-06-21    |
| 2       | logout   | 2019-06-21    |
| 3       | login    | 2019-01-01    |
| 3       | jobs     | 2019-01-01    |
| 3       | logout   | 2019-01-01    |
| 4       | login    | 2019-06-21    |
| 4       | groups   | 2019-06-21    |
| 4       | logout   | 2019-06-21    |
| 5       | login    | 2019-03-01    |
| 5       | logout   | 2019-03-01    |
| 5       | login    | 2019-06-21    |
| 5       | logout   | 2019-06-21    |
+---------+----------+---------------+
```

Result 表：

```
+------------+-------------+
| login_date | user_count  |
+------------+-------------+
| 2019-05-01 | 1           |
| 2019-06-21 | 2           |
+------------+-------------+
```

请注意，我们只关心用户数非零的日期.

ID 为 5 的用户第一次登陆于 2019-03-01，因此他不算在 2019-06-21 的的统计内。

', 'examDataFiles/auto_upload_1204_1637126775642.sql', '2021-11-17 13:26:15', '2021-11-17 13:26:15', 'auto_upload_1204_1637126775642.sql', 2, 1);
INSERT INTO sqlexam.main_questions (id, teacher_id, title, `desc`, db_path, created_at, updated_at, file_name, total_difficulty, sub_count) VALUES (1214, '0', '每位学生的最高成绩', '表：Enrollments

```
+---------------+---------+
| Column Name   | Type    |
+---------------+---------+
| student_id    | int     |
| course_id     | int     |
| grade         | int     |
+---------------+---------+
```

(student_id, course_id) 是该表的主键。

编写一个 SQL 查询，查询每位学生获得的最高成绩和它所对应的科目，若科目成绩并列，取course_id最小的一门。查询结果需按student_id增序进行排序。

查询结果格式如下所示：

Enrollments 表：

```
+------------+-------------------+
| student_id | course_id | grade |
+------------+-----------+-------+
| 2          | 2         | 95    |
| 2          | 3         | 95    |
| 1          | 1         | 90    |
| 1          | 2         | 99    |
| 3          | 1         | 80    |
| 3          | 2         | 75    |
| 3          | 3         | 82    |
+------------+-----------+-------+
```

Result 表：

```
+------------+-------------------+
| student_id | course_id | grade |
+------------+-----------+-------+
| 1          | 2         | 99    |
| 2          | 2         | 95    |
| 3          | 3         | 82    |
+------------+-----------+-------+
```

', 'examDataFiles/auto_upload_1214_1637126775642.sql', '2021-11-17 13:26:15', '2021-11-17 13:26:15', 'auto_upload_1214_1637126775642.sql', 2, 1);
INSERT INTO sqlexam.main_questions (id, teacher_id, title, `desc`, db_path, created_at, updated_at, file_name, total_difficulty, sub_count) VALUES (1215, '0', '报告的记录', '动作表：Actions

```
+---------------+---------+
| Column Name   | Type    |
+---------------+---------+
| user_id       | int     |
| post_id       | int     |
| action_date   | date    | 
| action        | enum    |
| extra         | varchar |
+---------------+---------+
```

此表没有主键，所以可能会有重复的行。

action 字段是 ENUM 类型的，包含:(''view'', ''like'', ''reaction'', ''comment'', ''report'', ''share'')

extra 字段是可选的信息（可能为 null），其中的信息例如有：1.报告理由(a reason for report) 2.反应类型(a type of reaction)

编写一条SQL，查询每种报告理由（report reason）在昨天的不同报告数量（post_id）。假设今天是2019-07-05。

查询及结果的格式示例：

Actions table:

```
+---------+---------+-------------+--------+--------+
| user_id | post_id | action_date | action | extra  |
+---------+---------+-------------+--------+--------+
| 1       | 1       | 2019-07-01  | view   | null   |
| 1       | 1       | 2019-07-01  | like   | null   |
| 1       | 1       | 2019-07-01  | share  | null   |
| 2       | 4       | 2019-07-04  | view   | null   |
| 2       | 4       | 2019-07-04  | report | spam   |
| 3       | 4       | 2019-07-04  | view   | null   |
| 3       | 4       | 2019-07-04  | report | spam   |
| 4       | 3       | 2019-07-02  | view   | null   |
| 4       | 3       | 2019-07-02  | report | spam   |
| 5       | 2       | 2019-07-04  | view   | null   |
| 5       | 2       | 2019-07-04  | report | racism |
| 5       | 5       | 2019-07-04  | view   | null   |
| 5       | 5       | 2019-07-04  | report | racism |
+---------+---------+-------------+--------+--------+
```

Result table:

```
+---------------+--------------+
| report_reason | report_count |
+---------------+--------------+
| spam          | 1            |
| racism        | 2            |
+---------------+--------------+ 
```

注意，我们只关心报告数量非零的结果。

', 'examDataFiles/auto_upload_1215_1637126775642.sql', '2021-11-17 13:26:15', '2021-11-17 13:26:15', 'auto_upload_1215_1637126775642.sql', 1, 1);
INSERT INTO sqlexam.main_questions (id, teacher_id, title, `desc`, db_path, created_at, updated_at, file_name, total_difficulty, sub_count) VALUES (1225, '0', '查询活跃业务', '事件表：Events

```
+---------------+---------+
| Column Name   | Type    |
+---------------+---------+
| business_id   | int     |
| event_type    | varchar |
| occurences    | int     | 
+---------------+---------+
```

此表的主键是 (business_id, event_type)。

表中的每一行记录了某种类型的事件在某些业务中多次发生的信息。

写一段 SQL 来查询所有活跃的业务。

如果一个业务的某个事件类型的发生次数大于此事件类型在所有业务中的平均发生次数，并且该业务至少有两个这样的事件类型，那么该业务就可被看做是活跃业务。

查询结果格式如下所示：

Events table:

```
+-------------+------------+------------+
| business_id | event_type | occurences |
+-------------+------------+------------+
| 1           | reviews    | 7          |
| 3           | reviews    | 3          |
| 1           | ads        | 11         |
| 2           | ads        | 7          |
| 3           | ads        | 6          |
| 1           | page views | 3          |
| 2           | page views | 12         |
+-------------+------------+------------+
```

结果表

```
+-------------+
| business_id |
+-------------+
| 1           |
+-------------+ 
```

''reviews''、 ''ads'' 和 ''page views'' 的总平均发生次数分别是 (7+3)/2=5, (11+7+6)/3=8, (3+12)/2=7.5。

id 为 1 的业务有 7 个 ''reviews'' 事件（大于 5）和 11 个 ''ads'' 事件（大于 8），所以它是活跃业务。

', 'examDataFiles/auto_upload_1225_1637126775642.sql', '2021-11-17 13:26:15', '2021-11-17 13:26:15', 'auto_upload_1225_1637126775642.sql', 2, 1);
INSERT INTO sqlexam.main_questions (id, teacher_id, title, `desc`, db_path, created_at, updated_at, file_name, total_difficulty, sub_count) VALUES (1226, '0', '用户购买平台', '支出表: Spending

```
+-------------+---------+
| Column Name | Type    |
+-------------+---------+
| user_id     | int     |
| spend_date  | date    |
| platform    | enum    | 
| amount      | int     |
+-------------+---------+
```

这张表记录了用户在一个在线购物网站的支出历史，该在线购物平台同时拥有桌面端（''desktop''）和手机端（''mobile''）的应用程序。

这张表的主键是 (user_id, spend_date, platform)。

平台列 platform 是一种 ENUM ，类型为（''desktop'', ''mobile''）。

写一段 SQL 来查找每天仅使用手机端用户、仅使用桌面端用户和同时使用桌面端和手机端的用户人数和总支出金额。

查询结果格式如下例所示：

Spending table:

```
+---------+------------+----------+--------+
| user_id | spend_date | platform | amount |
+---------+------------+----------+--------+
| 1       | 2019-07-01 | mobile   | 100    |
| 1       | 2019-07-01 | desktop  | 100    |
| 2       | 2019-07-01 | mobile   | 100    |
| 2       | 2019-07-02 | mobile   | 100    |
| 3       | 2019-07-01 | desktop  | 100    |
| 3       | 2019-07-02 | desktop  | 100    |
+---------+------------+----------+--------+
```

Result table:

```
+------------+----------+--------------+-------------+
| spend_date | platform | total_amount | total_users |
+------------+----------+--------------+-------------+
| 2019-07-01 | desktop  | 100          | 1           |
| 2019-07-01 | mobile   | 100          | 1           |
| 2019-07-01 | both     | 200          | 1           |
| 2019-07-02 | desktop  | 100          | 1           |
| 2019-07-02 | mobile   | 100          | 1           |
| 2019-07-02 | both     | 0            | 0           |
+------------+----------+--------------+-------------+ 
```

在 2019-07-01, 用户1 同时 使用桌面端和手机端购买, 用户2 仅 使用了手机端购买，而用户3 仅 使用了桌面端购买。

在 2019-07-02, 用户2 仅 使用了手机端购买, 用户3 仅 使用了桌面端购买，且没有用户 同时 使用桌面端和手机端购买。

', 'examDataFiles/auto_upload_1226_1637126775642.sql', '2021-11-17 13:26:15', '2021-11-17 13:26:15', 'auto_upload_1226_1637126775642.sql', 3, 1);
INSERT INTO sqlexam.main_questions (id, teacher_id, title, `desc`, db_path, created_at, updated_at, file_name, total_difficulty, sub_count) VALUES (1237, '0', '报告的记录 II', '动作表：Actions

```
+---------------+---------+
| Column Name   | Type    |
+---------------+---------+
| user_id       | int     |
| post_id       | int     |
| action_date   | date    |
| action        | enum    |
| extra         | varchar |
+---------------+---------+
```

这张表没有主键，并有可能存在重复的行。

action 列的类型是 ENUM，可能的值为 (''view'', ''like'', ''reaction'', ''comment'', ''report'', ''share'')。

extra 列拥有一些可选信息，例如：报告理由（a reason for report）或反应类型（a type of reaction）等。

移除表：Removals

```
+---------------+---------+
| Column Name   | Type    |
+---------------+---------+
| post_id       | int     |
| remove_date   | date    | 
+---------------+---------+
```

这张表的主键是 post_id。

这张表的每一行表示一个被移除的帖子，原因可能是由于被举报或被管理员审查。

编写一段 SQL 来查找：在被报告为垃圾广告的帖子中，被移除的帖子的每日平均占比，四舍五入到小数点后 2 位。

查询结果的格式如下：

Actions table:

```
+---------+---------+-------------+--------+--------+
| user_id | post_id | action_date | action | extra  |
+---------+---------+-------------+--------+--------+
| 1       | 1       | 2019-07-01  | view   | null   |
| 1       | 1       | 2019-07-01  | like   | null   |
| 1       | 1       | 2019-07-01  | share  | null   |
| 2       | 2       | 2019-07-04  | view   | null   |
| 2       | 2       | 2019-07-04  | report | spam   |
| 3       | 4       | 2019-07-04  | view   | null   |
| 3       | 4       | 2019-07-04  | report | spam   |
| 4       | 3       | 2019-07-02  | view   | null   |
| 4       | 3       | 2019-07-02  | report | spam   |
| 5       | 2       | 2019-07-03  | view   | null   |
| 5       | 2       | 2019-07-03  | report | racism |
| 5       | 5       | 2019-07-03  | view   | null   |
| 5       | 5       | 2019-07-03  | report | racism |
+---------+---------+-------------+--------+--------+
```

Removals table:

```
+---------+-------------+
| post_id | remove_date |
+---------+-------------+
| 2       | 2019-07-20  |
| 3       | 2019-07-18  |
+---------+-------------+
```

Result table:

```
+-----------------------+
| average_daily_percent |
+-----------------------+
| 75.00                 |
+-----------------------+
```

2019-07-04 的垃圾广告移除率是 50%，因为有两张帖子被报告为垃圾广告，但只有一个得到移除。

2019-07-02 的垃圾广告移除率是 100%，因为有一张帖子被举报为垃圾广告并得到移除。

其余几天没有收到垃圾广告的举报，因此平均值为：(50 + 100) / 2 = 75%

注意，输出仅需要一个平均值即可，我们并不关注移除操作的日期。

', 'examDataFiles/auto_upload_1237_1637126775642.sql', '2021-11-17 13:26:15', '2021-11-17 13:26:15', 'auto_upload_1237_1637126775642.sql', 2, 1);
INSERT INTO sqlexam.main_questions (id, teacher_id, title, `desc`, db_path, created_at, updated_at, file_name, total_difficulty, sub_count) VALUES (1245, '0', '查询近30天活跃用户数', '活动记录表：Activity

```
+---------------+---------+
| Column Name   | Type    |
+---------------+---------+
| user_id       | int     |
| session_id    | int     |
| activity_date | date    |
| activity_type | enum    |
+---------------+---------+
```

该表是用户在社交网站的活动记录。

该表没有主键，可能包含重复数据。

activity_type 字段为以下四种值 (''open_session'', ''end_session'', ''scroll_down'', ''send_message'')。

每个 session_id 只属于一个用户。

请写SQL查询出截至2019-07-27（包含2019-07-27），近30天的每日活跃用户数（当天只要有一条活动记录，即为活跃用户）。

查询结果示例如下：

Activity table:

```
+---------+------------+---------------+---------------+
| user_id | session_id | activity_date | activity_type |
+---------+------------+---------------+---------------+
| 1       | 1          | 2019-07-20    | open_session  |
| 1       | 1          | 2019-07-20    | scroll_down   |
| 1       | 1          | 2019-07-20    | end_session   |
| 2       | 4          | 2019-07-20    | open_session  |
| 2       | 4          | 2019-07-21    | send_message  |
| 2       | 4          | 2019-07-21    | end_session   |
| 3       | 2          | 2019-07-21    | open_session  |
| 3       | 2          | 2019-07-21    | send_message  |
| 3       | 2          | 2019-07-21    | end_session   |
| 4       | 3          | 2019-06-25    | open_session  |
| 4       | 3          | 2019-06-25    | end_session   |
+---------+------------+---------------+---------------+
```

Result table:

```
+------------+--------------+ 
| day        | active_users |
+------------+--------------+ 
| 2019-07-20 | 2            |
| 2019-07-21 | 2            |
+------------+--------------+ 
```

非活跃用户的记录不需要展示。

', 'examDataFiles/auto_upload_1245_1637126775642.sql', '2021-11-17 13:26:15', '2021-11-17 13:26:15', 'auto_upload_1245_1637126775642.sql', 1, 1);
INSERT INTO sqlexam.main_questions (id, teacher_id, title, `desc`, db_path, created_at, updated_at, file_name, total_difficulty, sub_count) VALUES (1246, '0', '过去30天的用户活动 II', 'Table: Activity

```
+---------------+---------+
| Column Name   | Type    |
+---------------+---------+
| user_id       | int     |
| session_id    | int     |
| activity_date | date    |
| activity_type | enum    |
+---------------+---------+
```

该表没有主键，它可能有重复的行。

activity_type 列是 ENUM（“ open_session”，“ end_session”，“ scroll_down”，“ send_message”）中的某一类型。

该表显示了社交媒体网站的用户活动。

请注意，每个会话完全属于一个用户。

编写SQL查询以查找截至2019年7月27日（含）的30天内每个用户的平均会话数，四舍五入到小数点后两位。我们只统计那些会话期间用户至少进行一项活动的有效会话。

查询结果格式如下例所示：

Activity table:

```
+---------+------------+---------------+---------------+
| user_id | session_id | activity_date | activity_type |
+---------+------------+---------------+---------------+
| 1       | 1          | 2019-07-20    | open_session  |
| 1       | 1          | 2019-07-20    | scroll_down   |
| 1       | 1          | 2019-07-20    | end_session   |
| 2       | 4          | 2019-07-20    | open_session  |
| 2       | 4          | 2019-07-21    | send_message  |
| 2       | 4          | 2019-07-21    | end_session   |
| 3       | 2          | 2019-07-21    | open_session  |
| 3       | 2          | 2019-07-21    | send_message  |
| 3       | 2          | 2019-07-21    | end_session   |
| 3       | 5          | 2019-07-21    | open_session  |
| 3       | 5          | 2019-07-21    | scroll_down   |
| 3       | 5          | 2019-07-21    | end_session   |
| 4       | 3          | 2019-06-25    | open_session  |
| 4       | 3          | 2019-06-25    | end_session   |
+---------+------------+---------------+---------------+
```

Result table:

```
+---------------------------+ 
| average_sessions_per_user |
+---------------------------+ 
| 1.33                      |
+---------------------------+ 
```

User 1 和 2 在过去30天内各自进行了1次会话，而用户3进行了2次会话，因此平均值为（1 +1 + 2）/ 3 = 1.33。

', 'examDataFiles/auto_upload_1246_1637126775642.sql', '2021-11-17 13:26:15', '2021-11-17 13:26:15', 'auto_upload_1246_1637126775642.sql', 1, 1);
INSERT INTO sqlexam.main_questions (id, teacher_id, title, `desc`, db_path, created_at, updated_at, file_name, total_difficulty, sub_count) VALUES (1258, '0', '文章浏览 I', 'Views表：

```
+---------------+---------+
| Column Name   | Type    |
+---------------+---------+
| article_id    | int     |
| author_id     | int     |
| viewer_id     | int     |
| view_date     | date    |
+---------------+---------+
```

此表无主键，因此可能会存在重复行。

此表的每一行都表示某人在某天浏览了某位作者的某篇文章。

请注意，同一人的 author_id 和 viewer_id 是相同的。

请编写一条 SQL 查询以找出所有浏览过自己文章的作者，结果按照 id 升序排列。

查询结果的格式如下所示：

Views 表：

```
+------------+-----------+-----------+------------+
| article_id | author_id | viewer_id | view_date  |
+------------+-----------+-----------+------------+
| 1          | 3         | 5         | 2019-08-01 |
| 1          | 3         | 6         | 2019-08-02 |
| 2          | 7         | 7         | 2019-08-01 |
| 2          | 7         | 6         | 2019-08-02 |
| 4          | 7         | 1         | 2019-07-22 |
| 3          | 4         | 4         | 2019-07-21 |
| 3          | 4         | 4         | 2019-07-21 |
+------------+-----------+-----------+------------+
```

结果表：

```
+------+
| id   |
+------+
| 4    |
| 7    |
+------+
```

', 'examDataFiles/auto_upload_1258_1637126775642.sql', '2021-11-17 13:26:15', '2021-11-17 13:26:15', 'auto_upload_1258_1637126775642.sql', 1, 1);
INSERT INTO sqlexam.main_questions (id, teacher_id, title, `desc`, db_path, created_at, updated_at, file_name, total_difficulty, sub_count) VALUES (1259, '0', '文章浏览 II', 'Table: Views

```
+---------------+---------+
| Column Name   | Type    |
+---------------+---------+
| article_id    | int     |
| author_id     | int     |
| viewer_id     | int     |
| view_date     | date    |
+---------------+---------+
```

此表无主键，因此可能会存在重复行。此表的每一行都表示某人在某天浏览了某位作者的某篇文章。 请注意，同一人的 author_id 和 viewer_id 是相同的。

编写一条 SQL 查询来找出在同一天阅读至少两篇文章的人，结果按照 id 升序排序。

查询结果的格式如下：

Views table:

```
+------------+-----------+-----------+------------+
| article_id | author_id | viewer_id | view_date  |
+------------+-----------+-----------+------------+
| 1          | 3         | 5         | 2019-08-01 |
| 3          | 4         | 5         | 2019-08-01 |
| 1          | 3         | 6         | 2019-08-02 |
| 2          | 7         | 7         | 2019-08-01 |
| 2          | 7         | 6         | 2019-08-02 |
| 4          | 7         | 1         | 2019-07-22 |
| 3          | 4         | 4         | 2019-07-21 |
| 3          | 4         | 4         | 2019-07-21 |
+------------+-----------+-----------+------------+
```

Result table:

```
+------+
| id   |
+------+
| 5    |
| 6    |
+------+
```

', 'examDataFiles/auto_upload_1259_1637126775642.sql', '2021-11-17 13:26:15', '2021-11-17 13:26:15', 'auto_upload_1259_1637126775642.sql', 2, 1);
INSERT INTO sqlexam.main_questions (id, teacher_id, title, `desc`, db_path, created_at, updated_at, file_name, total_difficulty, sub_count) VALUES (1268, '0', '市场分析 I', 'Table: Users

```
+----------------+---------+
| Column Name    | Type    |
+----------------+---------+
| user_id        | int     |
| join_date      | date    |
| favorite_brand | varchar |
+----------------+---------+
```

此表主键是 user_id，表中描述了购物网站的用户信息，用户可以在此网站上进行商品买卖。

Table: Orders

```
+---------------+---------+
| Column Name   | Type    |
+---------------+---------+
| order_id      | int     |
| order_date    | date    |
| item_id       | int     |
| buyer_id      | int     |
| seller_id     | int     |
+---------------+---------+
```

此表主键是 order_id，外键是 item_id 和（buyer_id，seller_id）。

Table: Item

```
+---------------+---------+
| Column Name   | Type    |
+---------------+---------+
| item_id       | int     |
| item_brand    | varchar |
+---------------+---------+
```

此表主键是 item_id。

请写出一条SQL语句以查询每个用户的注册日期和在 2019 年作为买家的订单总数。

查询结果格式如下：

Users table:

```
+---------+------------+----------------+
| user_id | join_date  | favorite_brand |
+---------+------------+----------------+
| 1       | 2018-01-01 | Lenovo         |
| 2       | 2018-02-09 | Samsung        |
| 3       | 2018-01-19 | LG             |
| 4       | 2018-05-21 | HP             |
+---------+------------+----------------+
```

Orders table:

```
+----------+------------+---------+----------+-----------+
| order_id | order_date | item_id | buyer_id | seller_id |
+----------+------------+---------+----------+-----------+
| 1        | 2019-08-01 | 4       | 1        | 2         |
| 2        | 2018-08-02 | 2       | 1        | 3         |
| 3        | 2019-08-03 | 3       | 2        | 3         |
| 4        | 2018-08-04 | 1       | 4        | 2         |
| 5        | 2018-08-04 | 1       | 3        | 4         |
| 6        | 2019-08-05 | 2       | 2        | 4         |
+----------+------------+---------+----------+-----------+
```

Items table:

```
+---------+------------+
| item_id | item_brand |
+---------+------------+
| 1       | Samsung    |
| 2       | Lenovo     |
| 3       | LG         |
| 4       | HP         |
+---------+------------+
```

Result table:

```
+-----------+------------+----------------+
| buyer_id  | join_date  | orders_in_2019 |
+-----------+------------+----------------+
| 1         | 2018-01-01 | 1              |
| 2         | 2018-02-09 | 2              |
| 3         | 2018-01-19 | 0              |
| 4         | 2018-05-21 | 0              |
+-----------+------------+----------------+
```

', 'examDataFiles/auto_upload_1268_1637126775642.sql', '2021-11-17 13:26:15', '2021-11-17 13:26:15', 'auto_upload_1268_1637126775642.sql', 2, 1);
INSERT INTO sqlexam.main_questions (id, teacher_id, title, `desc`, db_path, created_at, updated_at, file_name, total_difficulty, sub_count) VALUES (1269, '0', '市场分析 II', '表: Users

```
+----------------+---------+
| Column Name    | Type    |
+----------------+---------+
| user_id        | int     |
| join_date      | date    |
| favorite_brand | varchar |
+----------------+---------+
```

user_id 是该表的主键

表中包含一位在线购物网站用户的个人信息，用户可以在该网站出售和购买商品。

表: Orders

```
+---------------+---------+
| Column Name   | Type    |
+---------------+---------+
| order_id      | int     |
| order_date    | date    |
| item_id       | int     |
| buyer_id      | int     |
| seller_id     | int     |
+---------------+---------+
```

order_id 是该表的主键

item_id 是 Items 表的外键

buyer_id 和 seller_id 是 Users 表的外键

表: Items

```
+---------------+---------+
| Column Name   | Type    |
+---------------+---------+
| item_id       | int     |
| item_brand    | varchar |
+---------------+---------+
```

item_id 是该表的主键

写一个 SQL 查询确定每一个用户按日期顺序卖出的第二件商品的品牌是否是他们最喜爱的品牌。如果一个用户卖出少于两件商品，查询的结果是 no 。

题目保证没有一个用户在一天中卖出超过一件商品

下面是查询结果格式的例子：

Users table:

```
+---------+------------+----------------+
| user_id | join_date  | favorite_brand |
+---------+------------+----------------+
| 1       | 2019-01-01 | Lenovo         |
| 2       | 2019-02-09 | Samsung        |
| 3       | 2019-01-19 | LG             |
| 4       | 2019-05-21 | HP             |
+---------+------------+----------------+
```

Orders table:

```
+----------+------------+---------+----------+-----------+
| order_id | order_date | item_id | buyer_id | seller_id |
+----------+------------+---------+----------+-----------+
| 1        | 2019-08-01 | 4       | 1        | 2         |
| 2        | 2019-08-02 | 2       | 1        | 3         |
| 3        | 2019-08-03 | 3       | 2        | 3         |
| 4        | 2019-08-04 | 1       | 4        | 2         |
| 5        | 2019-08-04 | 1       | 3        | 4         |
| 6        | 2019-08-05 | 2       | 2        | 4         |
+----------+------------+---------+----------+-----------+
```

Items table:

```
+---------+------------+
| item_id | item_brand |
+---------+------------+
| 1       | Samsung    |
| 2       | Lenovo     |
| 3       | LG         |
| 4       | HP         |
+---------+------------+
```

Result table:

```
+-----------+--------------------+
| seller_id | 2nd_item_fav_brand |
+-----------+--------------------+
| 1         | no                 |
| 2         | yes                |
| 3         | yes                |
| 4         | no                 |
+-----------+--------------------+
```

id 为 1 的用户的查询结果是 no，因为他什么也没有卖出

id为 2 和 3 的用户的查询结果是 yes，因为他们卖出的第二件商品的品牌是他们自己最喜爱的品牌

id为 4 的用户的查询结果是 no，因为他卖出的第二件商品的品牌不是他最喜爱的品牌

', 'examDataFiles/auto_upload_1269_1637126775642.sql', '2021-11-17 13:26:15', '2021-11-17 13:26:15', 'auto_upload_1269_1637126775642.sql', 3, 1);
INSERT INTO sqlexam.main_questions (id, teacher_id, title, `desc`, db_path, created_at, updated_at, file_name, total_difficulty, sub_count) VALUES (1278, '0', '指定日期的产品价格', '产品数据表: Products

```
+---------------+---------+
| Column Name   | Type    |
+---------------+---------+
| product_id    | int     |
| new_price     | int     |
| change_date   | date    |
+---------------+---------+
```

这张表的主键是 (product_id, change_date)。

这张表的每一行分别记录了 某产品 在某个日期 更改后 的新价格。

写一段 SQL来查找在2019-08-16 时全部产品的价格，假设所有产品在修改前的价格都是10。

查询结果格式如下例所示：

Products table:

```
+------------+-----------+-------------+
| product_id | new_price | change_date |
+------------+-----------+-------------+
| 1          | 20        | 2019-08-14  |
| 2          | 50        | 2019-08-14  |
| 1          | 30        | 2019-08-15  |
| 1          | 35        | 2019-08-16  |
| 2          | 65        | 2019-08-17  |
| 3          | 20        | 2019-08-18  |
+------------+-----------+-------------+
```

Result table:

```
+------------+-------+
| product_id | price |
+------------+-------+
| 2          | 50    |
| 1          | 35    |
| 3          | 10    |
+------------+-------+
```

', 'examDataFiles/auto_upload_1278_1637126775642.sql', '2021-11-17 13:26:15', '2021-11-17 13:26:15', 'auto_upload_1278_1637126775642.sql', 2, 1);
INSERT INTO sqlexam.main_questions (id, teacher_id, title, `desc`, db_path, created_at, updated_at, file_name, total_difficulty, sub_count) VALUES (1291, '0', '即时食物配送 I', '配送表: Delivery

```
+-----------------------------+---------+
| Column Name                 | Type    |
+-----------------------------+---------+
| delivery_id                 | int     |
| customer_id                 | int     |
| order_date                  | date    |
| customer_pref_delivery_date | date    |
+-----------------------------+---------+
```

delivery_id 是表的主键。

该表保存着顾客的食物配送信息，顾客在某个日期下了订单，并指定了一个期望的配送日期（和下单日期相同或者在那之后）。

如果顾客期望的配送日期和下单日期相同，则该订单称为 「即时订单」，否则称为「计划订单」。

写一条 SQL查询语句获取即时订单所占的百分比，保留两位小数。

查询结果如下所示：

Delivery 表:

```
+-------------+-------------+------------+-----------------------------+
| delivery_id | customer_id | order_date | customer_pref_delivery_date |
+-------------+-------------+------------+-----------------------------+
| 1           | 1           | 2019-08-01 | 2019-08-02                  |
| 2           | 5           | 2019-08-02 | 2019-08-02                  |
| 3           | 1           | 2019-08-11 | 2019-08-11                  |
| 4           | 3           | 2019-08-24 | 2019-08-26                  |
| 5           | 4           | 2019-08-21 | 2019-08-22                  |
| 6           | 2           | 2019-08-11 | 2019-08-13                  |
+-------------+-------------+------------+-----------------------------+
```

Result 表:

```
+----------------------+
| immediate_percentage |
+----------------------+
| 33.33                |
+----------------------+
```

2 和 3 号订单为即时订单，其他的为计划订单。

', 'examDataFiles/auto_upload_1291_1637126775642.sql', '2021-11-17 13:26:15', '2021-11-17 13:26:15', 'auto_upload_1291_1637126775642.sql', 1, 1);
INSERT INTO sqlexam.main_questions (id, teacher_id, title, `desc`, db_path, created_at, updated_at, file_name, total_difficulty, sub_count) VALUES (1292, '0', '即时食物配送 II', '配送表: Delivery

```
+-----------------------------+---------+
| Column Name                 | Type    |
+-----------------------------+---------+
| delivery_id                 | int     |
| customer_id                 | int     |
| order_date                  | date    |
| customer_pref_delivery_date | date    |
+-----------------------------+---------+
```

delivery_id 是表的主键。

该表保存着顾客的食物配送信息，顾客在某个日期下了订单，并指定了一个期望的配送日期（和下单日期相同或者在那之后）。

如果顾客期望的配送日期和下单日期相同，则该订单称为 「即时订单」，否则称为「计划订单」。

「首次订单」是顾客最早创建的订单。我们保证一个顾客只会有一个「首次订单」。

写一条 SQL 查询语句获取即时订单在所有用户的首次订单中的比例。保留两位小数。

查询结果如下所示：

Delivery 表：

```
+-------------+-------------+------------+-----------------------------+
| delivery_id | customer_id | order_date | customer_pref_delivery_date |
+-------------+-------------+------------+-----------------------------+
| 1           | 1           | 2019-08-01 | 2019-08-02                  |
| 2           | 2           | 2019-08-02 | 2019-08-02                  |
| 3           | 1           | 2019-08-11 | 2019-08-12                  |
| 4           | 3           | 2019-08-24 | 2019-08-24                  |
| 5           | 3           | 2019-08-21 | 2019-08-22                  |
| 6           | 2           | 2019-08-11 | 2019-08-13                  |
| 7           | 4           | 2019-08-09 | 2019-08-09                  |
+-------------+-------------+------------+-----------------------------+
```

Result 表：

```
+----------------------+
| immediate_percentage |
+----------------------+
| 50.00                |
+----------------------+
```

1 号顾客的 1 号订单是首次订单，并且是计划订单。

2 号顾客的 2 号订单是首次订单，并且是即时订单。

3 号顾客的 5 号订单是首次订单，并且是计划订单。

4 号顾客的 7 号订单是首次订单，并且是即时订单。

因此，一半顾客的首次订单是即时的。

', 'examDataFiles/auto_upload_1292_1637126775642.sql', '2021-11-17 13:26:15', '2021-11-17 13:26:15', 'auto_upload_1292_1637126775642.sql', 2, 1);
INSERT INTO sqlexam.main_questions (id, teacher_id, title, `desc`, db_path, created_at, updated_at, file_name, total_difficulty, sub_count) VALUES (1301, '0', '重新格式化部门表', '部门表Department：

```
+---------------+---------+
| Column Name   | Type    |
+---------------+---------+
| id            | int     |
| revenue       | int     |
| month         | varchar |
+---------------+---------+
```

(id, month) 是表的联合主键。

这个表格有关于每个部门每月收入的信息。

月份（month）可以取下列值 ["Jan","Feb","Mar","Apr","May","Jun","Jul","Aug","Sep","Oct","Nov","Dec"]。

编写一个 SQL 查询来重新格式化表，使得新的表中有一个部门 id 列和一些对应每个月 的收入（revenue）列。

查询结果格式如下面的示例所示：

Department 表：

```
+------+---------+-------+
| id   | revenue | month |
+------+---------+-------+
| 1    | 8000    | Jan   |
| 2    | 9000    | Jan   |
| 3    | 10000   | Feb   |
| 1    | 7000    | Feb   |
| 1    | 6000    | Mar   |
+------+---------+-------+
```

查询得到的结果表：

```
+------+-------------+-------------+-------------+-----+-------------+
| id   | Jan_Revenue | Feb_Revenue | Mar_Revenue | ... | Dec_Revenue |
+------+-------------+-------------+-------------+-----+-------------+
| 1    | 8000        | 7000        | 6000        | ... | null        |
| 2    | 9000        | null        | null        | ... | null        |
| 3    | null        | 10000       | null        | ... | null        |
+------+-------------+-------------+-------------+-----+-------------+
```

注意，结果表有 13 列 (1个部门 id 列 + 12个月份的收入列)。

', 'examDataFiles/auto_upload_1301_1637126775642.sql', '2021-11-17 13:26:15', '2021-11-17 13:26:15', 'auto_upload_1301_1637126775642.sql', 1, 1);
INSERT INTO sqlexam.main_questions (id, teacher_id, title, `desc`, db_path, created_at, updated_at, file_name, total_difficulty, sub_count) VALUES (1317, '0', '每月交易 I', 'Table: Transactions

```
+---------------+---------+
| Column Name   | Type    |
+---------------+---------+
| id            | int     |
| country       | varchar |
| state         | enum    |
| amount        | int     |
| trans_date    | date    |
+---------------+---------+
```

id 是这个表的主键。

该表包含有关传入事务的信息。

state 列类型为 “[”批准“，”拒绝“] 之一。

编写一个 sql 查询来查找每个月和每个国家/地区的事务数及其总金额、已批准的事务数及其总金额。

查询结果格式如下所示：

Transactions table:

```
+------+---------+----------+--------+------------+
| id   | country | state    | amount | trans_date |
+------+---------+----------+--------+------------+
| 121  | US      | approved | 1000   | 2018-12-18 |
| 122  | US      | declined | 2000   | 2018-12-19 |
| 123  | US      | approved | 2000   | 2019-01-01 |
| 124  | DE      | approved | 2000   | 2019-01-07 |
+------+---------+----------+--------+------------+
```

Result table:

```
+----------+---------+-------------+----------------+--------------------+-----------------------+
| month    | country | trans_count | approved_count | trans_total_amount | approved_total_amount |
+----------+---------+-------------+----------------+--------------------+-----------------------+
| 2018-12  | US      | 2           | 1              | 3000               | 1000                  |
| 2019-01  | US      | 1           | 1              | 2000               | 2000                  |
| 2019-01  | DE      | 1           | 1              | 2000               | 2000                  |
+----------+---------+-------------+----------------+--------------------+-----------------------+
```

', 'examDataFiles/auto_upload_1317_1637126775642.sql', '2021-11-17 13:26:15', '2021-11-17 13:26:15', 'auto_upload_1317_1637126775642.sql', 2, 1);
INSERT INTO sqlexam.main_questions (id, teacher_id, title, `desc`, db_path, created_at, updated_at, file_name, total_difficulty, sub_count) VALUES (1318, '0', '锦标赛优胜者', 'Players玩家表

```
+-------------+-------+
| Column Name | Type  |
+-------------+-------+
| player_id   | int   |
| group_id    | int   |
+-------------+-------+
```

player_id 是此表的主键。

此表的每一行表示每个玩家的组。

Matches赛事表

```
+---------------+---------+
| Column Name   | Type    |
+---------------+---------+
| match_id      | int     |
| first_player  | int     |
| second_player | int     | 
| first_score   | int     |
| second_score  | int     |
+---------------+---------+
```

match_id 是此表的主键。

每一行是一场比赛的记录，first_player 和 second_player 表示该场比赛的球员 ID。

first_score 和 second_score 分别表示 first_player 和 second_player 的得分。

你可以假设，在每一场比赛中，球员都属于同一组。

每组的获胜者是在组内累积得分最高的选手。如果平局，player_id 最小的选手获胜。

编写一个 SQL 查询来查找每组中的获胜者。

查询结果格式如下所示

Players 表:

```
+-----------+------------+
| player_id | group_id   |
+-----------+------------+
| 15        | 1          |
| 25        | 1          |
| 30        | 1          |
| 45        | 1          |
| 10        | 2          |
| 35        | 2          |
| 50        | 2          |
| 20        | 3          |
| 40        | 3          |
+-----------+------------+
```

Matches 表:

```
+------------+--------------+---------------+-------------+--------------+
| match_id   | first_player | second_player | first_score | second_score |
+------------+--------------+---------------+-------------+--------------+
| 1          | 15           | 45            | 3           | 0            |
| 2          | 30           | 25            | 1           | 2            |
| 3          | 30           | 15            | 2           | 0            |
| 4          | 40           | 20            | 5           | 2            |
| 5          | 35           | 50            | 1           | 1            |
+------------+--------------+---------------+-------------+--------------+
```

Result 表:

```
+-----------+------------+
| group_id  | player_id  |
+-----------+------------+ 
| 1         | 15         |
| 2         | 35         |
| 3         | 40         |
+-----------+------------+
```

', 'examDataFiles/auto_upload_1318_1637126775642.sql', '2021-11-17 13:26:15', '2021-11-17 13:26:15', 'auto_upload_1318_1637126775642.sql', 3, 1);
INSERT INTO sqlexam.main_questions (id, teacher_id, title, `desc`, db_path, created_at, updated_at, file_name, total_difficulty, sub_count) VALUES (1327, '0', '最后一个能进入电梯的人', '表: Queue

```
+-------------+---------+
| Column Name | Type    |
+-------------+---------+
| person_id   | int     |
| person_name | varchar |
| weight      | int     |
| turn        | int     |
+-------------+---------+
```

person_id 是这个表的主键。

该表展示了所有等待电梯的人的信息。

表中 person_id 和 turn 列将包含从 1 到 n 的所有数字，其中 n 是表中的行数。

电梯最大载重量为 1000。

写一条 SQL 查询语句查找最后一个能进入电梯且不超过重量限制的 person_name 。题目确保队列中第一位的人可以进入电梯 。

查询结果如下所示 :

Queue 表

```
+-----------+-------------------+--------+------+
| person_id | person_name       | weight | turn |
+-----------+-------------------+--------+------+
| 5         | George Washington | 250    | 1    |
| 3         | John Adams        | 350    | 2    |
| 6         | Thomas Jefferson  | 400    | 3    |
| 2         | Will Johnliams    | 200    | 4    |
| 4         | Thomas Jefferson  | 175    | 5    |
| 1         | James Elephant    | 500    | 6    |
+-----------+-------------------+--------+------+
```

Result 表

```
+-------------------+
| person_name       |
+-------------------+
| Thomas Jefferson  |
+-------------------+
```

为了简化，Queue 表按 turn 列由小到大排序。

上例中 George Washington(id 5), John Adams(id 3) 和 Thomas Jefferson(id 6) 将可以进入电梯,因为他们的体重和为 250 + 350 + 400 = 1000。

Thomas Jefferson(id 6) 是最后一个体重合适并进入电梯的人。

', 'examDataFiles/auto_upload_1327_1637126775642.sql', '2021-11-17 13:26:15', '2021-11-17 13:26:15', 'auto_upload_1327_1637126775642.sql', 2, 1);
INSERT INTO sqlexam.main_questions (id, teacher_id, title, `desc`, db_path, created_at, updated_at, file_name, total_difficulty, sub_count) VALUES (1328, '0', '每月交易II', 'Transactions 记录表

```
+----------------+---------+
| Column Name    | Type    |
+----------------+---------+
| id             | int     |
| country        | varchar |
| state          | enum    |
| amount         | int     |
| trans_date     | date    |
+----------------+---------+
```

id 是这个表的主键。

该表包含有关传入事务的信息。

状态列是类型为 [approved（已批准）、declined（已拒绝）] 的枚举。

Chargebacks 表

```
+----------------+---------+
| Column Name    | Type    |
+----------------+---------+
| trans_id       | int     |
| trans_date     | date    |
+----------------+---------+
```

退单包含有关放置在事务表中的某些事务的传入退单的基本信息。

trans_id 是 transactions 表的 id 列的外键。

每项退单都对应于之前进行的交易，即使未经批准。

编写一个 SQL查询，以查找每个月和每个国家/地区的信息：已批准交易的数量及其总金额、退单的数量及其总金额。

注意：在您的查询中，只需显示给定月份和国家，忽略所有为零的行。

查询结果格式如下所示：

Transactions 表：

```
+-----+---------+----------+--------+------------+
| id  | country | state    | amount | trans_date |
+-----+---------+----------+--------+------------+
| 101 | US      | approved | 1000   | 2019-05-18 |
| 102 | US      | declined | 2000   | 2019-05-19 |
| 103 | US      | approved | 3000   | 2019-06-10 |
| 104 | US      | declined | 4000   | 2019-06-13 |
| 105 | US      | approved | 5000   | 2019-06-15 |
+-----+---------+----------+--------+------------+
```

Chargebacks 表：

```
+----------+------------+
| trans_id | trans_date |
+----------+------------+
| 102      | 2019-05-29 |
| 101      | 2019-06-30 |
| 105      | 2019-09-18 |
+----------+------------+
```

Result 表：

```
+---------+---------+----------------+-----------------+------------------+-------------------+
| month   | country | approved_count | approved_amount | chargeback_count | chargeback_amount |
+---------+---------+----------------+-----------------+------------------+-------------------+
| 2019-05 | US      | 1              | 1000            | 1                | 2000              |
| 2019-06 | US      | 2              | 8000            | 1                | 1000              |
| 2019-09 | US      | 0              | 0               | 1                | 5000              |
+---------+---------+----------------+-----------------+------------------+-------------------+
```

', 'examDataFiles/auto_upload_1328_1637126775642.sql', '2021-11-17 13:26:15', '2021-11-17 13:26:15', 'auto_upload_1328_1637126775642.sql', 2, 1);
INSERT INTO sqlexam.main_questions (id, teacher_id, title, `desc`, db_path, created_at, updated_at, file_name, total_difficulty, sub_count) VALUES (1338, '0', '查询结果的质量和占比', '查询表 Queries：

```
+-------------+---------+
| Column Name | Type    |
+-------------+---------+
| query_name  | varchar |
| result      | varchar |
| position    | int     |
| rating      | int     |
+-------------+---------+
```

此表没有主键，并可能有重复的行。

此表包含了一些从数据库中收集的查询信息。

“位置”（position）列的值为 1 到 500 。

“评分”（rating）列的值为 1 到 5 。评分小于 3 的查询被定义为质量很差的查询。

将查询结果的质量 quality 定义为：

各查询结果的评分与其位置之间比率的平均值。

将劣质查询百分比poor_query_percentage 为：

评分小于 3 的查询结果占全部查询结果的百分比。

编写一组 SQL 来查找每次查询的名称(query_name)、质量(quality) 和劣质查询百分比(poor_query_percentage)。

质量(quality) 和劣质查询百分比(poor_query_percentage) 都应四舍五入到小数点后两位。

查询结果格式如下所示：

Queries table:

```
+------------+-------------------+----------+--------+
| query_name | result            | position | rating |
+------------+-------------------+----------+--------+
| Dog        | Golden Retriever  | 1        | 5      |
| Dog        | German Shepherd   | 2        | 5      |
| Dog        | Mule              | 200      | 1      |
| Cat        | Shirazi           | 5        | 2      |
| Cat        | Siamese           | 3        | 3      |
| Cat        | Sphynx            | 7        | 4      |
+------------+-------------------+----------+--------+
```

Result table:

```
+------------+---------+-----------------------+
| query_name | quality | poor_query_percentage |
+------------+---------+-----------------------+
| Dog        | 2.50    | 33.33                 |
| Cat        | 0.66    | 33.33                 |
+------------+---------+-----------------------+
```

Dog 查询结果的质量为 ((5 / 1) + (5 / 2) + (1 / 200)) / 3 = 2.50

Dog 查询结果的劣质查询百分比为 (1 / 3) * 100 = 33.33

Cat 查询结果的质量为 ((2 / 5) + (3 / 3) + (4 / 7)) / 3 = 0.66

Cat 查询结果的劣质查询百分比为 (1 / 3) * 100 = 33.33

', 'examDataFiles/auto_upload_1338_1637126775642.sql', '2021-11-17 13:26:15', '2021-11-17 13:26:15', 'auto_upload_1338_1637126775642.sql', 1, 1);
INSERT INTO sqlexam.main_questions (id, teacher_id, title, `desc`, db_path, created_at, updated_at, file_name, total_difficulty, sub_count) VALUES (1339, '0', '查询球队积分', 'Table: Teams

```
+---------------+----------+
| Column Name   | Type     |
+---------------+----------+
| team_id       | int      |
| team_name     | varchar  |
+---------------+----------+
```

此表的主键是 team_id，表中的每一行都代表一支独立足球队。

Table:Matches

```
+---------------+---------+
| Column Name   | Type    |
+---------------+---------+
| match_id      | int     |
| host_team     | int     |
| guest_team    | int     | 
| host_goals    | int     |
| guest_goals   | int     |
+---------------+---------+
```

此表的主键是 match_id，表中的每一行都代表一场已结束的比赛，比赛的主客队分别由它们自己的 id 表示，他们的进球由 host_goals 和 guest_goals 分别表示。

积分规则如下：

赢一场得三分；

平一场得一分；

输一场不得分。

写出一条SQL语句以查询每个队的team_id，team_name 和 num_points。结果根据num_points 降序排序，如果有两队积分相同，那么这两队按team_id 升序排序。

查询结果格式如下：

Teams table:

```
+-----------+--------------+
| team_id   | team_name    |
+-----------+--------------+
| 10        | Leetcode FC  |
| 20        | NewYork FC   |
| 30        | Atlanta FC   |
| 40        | Chicago FC   |
| 50        | Toronto FC   |
+-----------+--------------+
```

Matches table:

```
+------------+--------------+---------------+-------------+--------------+
| match_id   | host_team    | guest_team    | host_goals  | guest_goals  |
+------------+--------------+---------------+-------------+--------------+
| 1          | 10           | 20            | 3           | 0            |
| 2          | 30           | 10            | 2           | 2            |
| 3          | 10           | 50            | 5           | 1            |
| 4          | 20           | 30            | 1           | 0            |
| 5          | 50           | 30            | 1           | 0            |
+------------+--------------+---------------+-------------+--------------+
```

Result table:

```
+------------+--------------+---------------+
| team_id    | team_name    | num_points    |
+------------+--------------+---------------+
| 10         | Leetcode FC  | 7             |
| 20         | NewYork FC   | 3             |
| 50         | Toronto FC   | 3             |
| 30         | Atlanta FC   | 1             |
| 40         | Chicago FC   | 0             |
+------------+--------------+---------------+
```

', 'examDataFiles/auto_upload_1339_1637126775642.sql', '2021-11-17 13:26:15', '2021-11-17 13:26:15', 'auto_upload_1339_1637126775642.sql', 2, 1);
INSERT INTO sqlexam.main_questions (id, teacher_id, title, `desc`, db_path, created_at, updated_at, file_name, total_difficulty, sub_count) VALUES (1357, '0', '报告系统状态的连续日期', 'Table: Failed

```
+--------------+---------+
| Column Name  | Type    |
+--------------+---------+
| fail_date    | date    |
+--------------+---------+
```

该表主键为 fail_date。

该表包含失败任务的天数.

Table: Succeeded

```
+--------------+---------+
| Column Name  | Type    |
+--------------+---------+
| success_date | date    |
+--------------+---------+
```

该表主键为 success_date。

该表包含成功任务的天数.

系统 每天 运行一个任务。每个任务都独立于先前的任务。任务的状态可以是失败或是成功。

编写一个 SQL 查询2019-01-01到2019-12-31 期间任务连续同状态period_state的起止日期（start_date 和 end_date）。即如果任务失败了，就是失败状态的起止日期，如果任务成功了，就是成功状态的起止日期。

最后结果按照起始日期start_date排序

查询结果样例如下所示:

Failed table:

```
+-------------------+
| fail_date         |
+-------------------+
| 2018-12-28        |
| 2018-12-29        |
| 2019-01-04        |
| 2019-01-05        |
+-------------------+
```

Succeeded table:

```
+-------------------+
| success_date      |
+-------------------+
| 2018-12-30        |
| 2018-12-31        |
| 2019-01-01        |
| 2019-01-02        |
| 2019-01-03        |
| 2019-01-06        |
+-------------------+
```

Result table:

```
+--------------+--------------+--------------+
| period_state | start_date   | end_date     |
+--------------+--------------+--------------+
| succeeded    | 2019-01-01   | 2019-01-03   |
| failed       | 2019-01-04   | 2019-01-05   |
| succeeded    | 2019-01-06   | 2019-01-06   |
+--------------+--------------+--------------+
```

结果忽略了 2018 年的记录，因为我们只关心从 2019-01-01 到 2019-12-31 的记录

从 2019-01-01 到 2019-01-03 所有任务成功，系统状态为 "succeeded"。

从 2019-01-04 到 2019-01-05 所有任务失败，系统状态为 "failed"。

从 2019-01-06 到 2019-01-06 所有任务成功，系统状态为 "succeeded"。

', 'examDataFiles/auto_upload_1357_1637126775642.sql', '2021-11-17 13:26:15', '2021-11-17 13:26:15', 'auto_upload_1357_1637126775642.sql', 3, 1);
INSERT INTO sqlexam.main_questions (id, teacher_id, title, `desc`, db_path, created_at, updated_at, file_name, total_difficulty, sub_count) VALUES (1377, '0', '每个帖子的评论数', '表Submissions 结构如下：

```
+---------------+----------+
| 列名           | 类型     |
+---------------+----------+
| sub_id        | int      |
| parent_id     | int      |
+---------------+----------+
```

上表没有主键, 所以可能会出现重复的行。

每行可以是一个帖子或对该帖子的评论。

如果是帖子的话，parent_id 就是 null。

对于评论来说，parent_id 就是表中对应帖子的 sub_id。

编写 SQL 语句以查找每个帖子的评论数。

结果表应包含帖子的post_id 和对应的评论数number_of_comments 并且按post_id升序排列。

Submissions 可能包含重复的评论。您应该计算每个帖子的唯一评论数。

Submissions 可能包含重复的帖子。您应该将它们视为一个帖子。

查询结果格式如下例所示：

Submissions table:

```
+---------+------------+
| sub_id  | parent_id  |
+---------+------------+
| 1       | Null       |
| 2       | Null       |
| 1       | Null       |
| 12      | Null       |
| 3       | 1          |
| 5       | 2          |
| 3       | 1          |
| 4       | 1          |
| 9       | 1          |
| 10      | 2          |
| 6       | 7          |
+---------+------------+
```

结果表：

```
+---------+--------------------+
| post_id | number_of_comments |
+---------+--------------------+
| 1       | 3                  |
| 2       | 2                  |
| 12      | 0                  |
+---------+--------------------+
```

表中 ID 为 1 的帖子有 ID 为 3、4 和 9 的三个评论。表中 ID 为 3 的评论重复出现了，所以我们只对它进行了一次计数。

表中 ID 为 2 的帖子有 ID 为 5 和 10 的两个评论。

ID 为 12 的帖子在表中没有评论。

表中 ID 为 6 的评论是对 ID 为 7 的已删除帖子的评论，因此我们将其忽略。

', 'examDataFiles/auto_upload_1377_1637126775642.sql', '2021-11-17 13:26:15', '2021-11-17 13:26:15', 'auto_upload_1377_1637126775642.sql', 1, 1);
INSERT INTO sqlexam.main_questions (id, teacher_id, title, `desc`, db_path, created_at, updated_at, file_name, total_difficulty, sub_count) VALUES (1390, '0', '平均售价', 'Table: Prices

```
+---------------+---------+
| Column Name   | Type    |
+---------------+---------+
| product_id    | int     |
| start_date    | date    |
| end_date      | date    |
| price         | int     |
+---------------+---------+
```

(product_id，start_date，end_date) 是 Prices 表的主键。

Prices 表的每一行表示的是某个产品在一段时期内的价格。

每个产品的对应时间段是不会重叠的，这也意味着同一个产品的价格时段不会出现交叉。

Table: UnitsSold

```
+---------------+---------+
| Column Name   | Type    |
+---------------+---------+
| product_id    | int     |
| purchase_date | date    |
| units         | int     |
+---------------+---------+
```

UnitsSold 表没有主键，它可能包含重复项。

UnitsSold 表的每一行表示的是每种产品的出售日期，单位和产品 id。

编写SQL查询以查找每种产品的平均售价。

average_price 应该四舍五入到小数点后两位。

查询结果格式如下例所示：

Prices table:

```
+------------+------------+------------+--------+
| product_id | start_date | end_date   | price  |
+------------+------------+------------+--------+
| 1          | 2019-02-17 | 2019-02-28 | 5      |
| 1          | 2019-03-01 | 2019-03-22 | 20     |
| 2          | 2019-02-01 | 2019-02-20 | 15     |
| 2          | 2019-02-21 | 2019-03-31 | 30     |
+------------+------------+------------+--------+
```

 

UnitsSold table:

```
+------------+---------------+-------+
| product_id | purchase_date | units |
+------------+---------------+-------+
| 1          | 2019-02-25    | 100   |
| 1          | 2019-03-01    | 15    |
| 2          | 2019-02-10    | 200   |
| 2          | 2019-03-22    | 30    |
+------------+---------------+-------+
```

Result table:

```
+------------+---------------+
| product_id | average_price |
+------------+---------------+
| 1          | 6.96          |
| 2          | 16.96         |
+------------+---------------+
```

平均售价 = 产品总价 / 销售的产品数量。

产品 1 的平均售价 = ((100 * 5)+(15 * 20) )/ 115 = 6.96

产品 2 的平均售价 = ((200 * 15)+(30 * 30) )/ 230 = 16.96

', 'examDataFiles/auto_upload_1390_1637126775642.sql', '2021-11-17 13:26:15', '2021-11-17 13:26:15', 'auto_upload_1390_1637126775642.sql', 1, 1);
INSERT INTO sqlexam.main_questions (id, teacher_id, title, `desc`, db_path, created_at, updated_at, file_name, total_difficulty, sub_count) VALUES (1399, '0', '页面推荐', '朋友关系列表：Friendship

```
+---------------+---------+
| Column Name   | Type    |
+---------------+---------+
| user1_id      | int     |
| user2_id      | int     |
+---------------+---------+
```

这张表的主键是 (user1_id, user2_id)。

这张表的每一行代表着 user1_id 和 user2_id 之间存在着朋友关系。

喜欢列表：Likes

```
+-------------+---------+
| Column Name | Type    |
+-------------+---------+
| user_id     | int     |
| page_id     | int     |
+-------------+---------+
```

这张表的主键是 (user_id, page_id)。

这张表的每一行代表着 user_id 喜欢 page_id。

写一段 SQL 向user_id = 1 的用户，推荐其朋友们喜欢的页面。不要推荐该用户已经喜欢的页面。

你返回的结果中不应当包含重复项。

返回结果的格式如下例所示：

Friendship table:

```
+----------+----------+
| user1_id | user2_id |
+----------+----------+
| 1        | 2        |
| 1        | 3        |
| 1        | 4        |
| 2        | 3        |
| 2        | 4        |
| 2        | 5        |
| 6        | 1        |
+----------+----------+
```

 

Likes table:

```
+---------+---------+
| user_id | page_id |
+---------+---------+
| 1       | 88      |
| 2       | 23      |
| 3       | 24      |
| 4       | 56      |
| 5       | 11      |
| 6       | 33      |
| 2       | 77      |
| 3       | 77      |
| 6       | 88      |
+---------+---------+
```

Result table:

```
+------------------+
| recommended_page |
+------------------+
| 23               |
| 24               |
| 56               |
| 33               |
| 77               |
+------------------+
```

用户1 同 用户2, 3, 4, 6 是朋友关系。

推荐页面为： 页面23 来自于 用户2, 页面24 来自于 用户3, 页面56 来自于 用户3 以及 页面33 来自于 用户6。

页面77 同时被 用户2 和 用户3 推荐。

页面88 没有被推荐，因为 用户1 已经喜欢了它。

', 'examDataFiles/auto_upload_1399_1637126775642.sql', '2021-11-17 13:26:15', '2021-11-17 13:26:15', 'auto_upload_1399_1637126775642.sql', 2, 1);
INSERT INTO sqlexam.main_questions (id, teacher_id, title, `desc`, db_path, created_at, updated_at, file_name, total_difficulty, sub_count) VALUES (1405, '0', '向公司CEO汇报工作的所有人', '员工表：Employees

```
+---------------+---------+
| Column Name   | Type    |
+---------------+---------+
| employee_id   | int     |
| employee_name | varchar |
| manager_id    | int     |
+---------------+---------+
```

employee_id 是这个表的主键。

这个表中每一行中，employee_id 表示职工的 ID，employee_name 表示职工的名字，manager_id 表示该职工汇报工作的直线经理。

这个公司 CEO 是 employee_id = 1 的人。

用 SQL 查询出所有直接或间接向公司 CEO 汇报工作的职工的 employee_id 。

由于公司规模较小，经理之间的间接关系不超过 3 个经理。

可以以任何顺序返回无重复项的结果。

查询结果示例如下：

Employees table:

```
+-------------+---------------+------------+
| employee_id | employee_name | manager_id |
+-------------+---------------+------------+
| 1           | Boss          | 1          |
| 3           | Alice         | 3          |
| 2           | Bob           | 1          |
| 4           | Daniel        | 2          |
| 7           | Luis          | 4          |
| 8           | Jhon          | 3          |
| 9           | Angela        | 8          |
| 77          | Robert        | 1          |
+-------------+---------------+------------+
```

Result table:

```
+-------------+
| employee_id |
+-------------+
| 2           |
| 77          |
| 4           |
| 7           |
+-------------+
```

公司 CEO 的 employee_id 是 1.

employee_id 是 2 和 77 的职员直接汇报给公司 CEO。

employee_id 是 4 的职员间接汇报给公司 CEO 4 --> 2 --> 1 。

employee_id 是 7 的职员间接汇报给公司 CEO 7 --> 4 --> 2 --> 1 。

employee_id 是 3, 8 ，9 的职员不会直接或间接的汇报给公司 CEO。 

', 'examDataFiles/auto_upload_1405_1637126775642.sql', '2021-11-17 13:26:15', '2021-11-17 13:26:15', 'auto_upload_1405_1637126775642.sql', 2, 1);
INSERT INTO sqlexam.main_questions (id, teacher_id, title, `desc`, db_path, created_at, updated_at, file_name, total_difficulty, sub_count) VALUES (1415, '0', '学生们参加各科测试的次数', '学生表: Students

```
+---------------+---------+
| Column Name   | Type    |
+---------------+---------+
| student_id    | int     |
| student_name  | varchar |
+---------------+---------+
```

主键为 student_id（学生ID），该表内的每一行都记录有学校一名学生的信息。

科目表: Subjects

```
+--------------+---------+
| Column Name  | Type    |
+--------------+---------+
| subject_name | varchar |
+--------------+---------+
```

主键为 subject_name（科目名称），每一行记录学校的一门科目名称。

考试表: Examinations

```
+--------------+---------+
| Column Name  | Type    |
+--------------+---------+
| student_id   | int     |
| subject_name | varchar |
+--------------+---------+
```

这张表压根没有主键，可能会有重复行。

学生表里的一个学生修读科目表里的每一门科目，而这张考试表的每一行记录就表示学生表里的某个学生参加了一次科目表里某门科目的测试。

要求写一段 SQL 语句，查询出每个学生参加每一门科目测试的次数，结果按 student_id 和 subject_name 排序。

查询结构格式如下所示：

Students table:

```
+------------+--------------+
| student_id | student_name |
+------------+--------------+
| 1          | Alice        |
| 2          | Bob          |
| 13         | John         |
| 6          | Alex         |
+------------+--------------+
```

Subjects table:

```
+--------------+
| subject_name |
+--------------+
| Math         |
| Physics      |
| Programming  |
+--------------+
```

Examinations table:

```
+------------+--------------+
| student_id | subject_name |
+------------+--------------+
| 1          | Math         |
| 1          | Physics      |
| 1          | Programming  |
| 2          | Programming  |
| 1          | Physics      |
| 1          | Math         |
| 13         | Math         |
| 13         | Programming  |
| 13         | Physics      |
| 2          | Math         |
| 1          | Math         |
+------------+--------------+
```

Result table:

```
+------------+--------------+--------------+----------------+
| student_id | student_name | subject_name | attended_exams |
+------------+--------------+--------------+----------------+
| 1          | Alice        | Math         | 3              |
| 1          | Alice        | Physics      | 2              |
| 1          | Alice        | Programming  | 1              |
| 2          | Bob          | Math         | 1              |
| 2          | Bob          | Physics      | 0              |
| 2          | Bob          | Programming  | 1              |
| 6          | Alex         | Math         | 0              |
| 6          | Alex         | Physics      | 0              |
| 6          | Alex         | Programming  | 0              |
| 13         | John         | Math         | 1              |
| 13         | John         | Physics      | 1              |
| 13         | John         | Programming  | 1              |
+------------+--------------+--------------+----------------+
```

结果表需包含所有学生和所有科目（即便测试次数为0）：

Alice 参加了 3 次数学测试, 2 次物理测试，以及 1 次编程测试；

Bob 参加了 1 次数学测试, 1 次编程测试，没有参加物理测试；

Alex 啥测试都没参加；

John  参加了数学、物理、编程测试各 1 次。

', 'examDataFiles/auto_upload_1415_1637126775642.sql', '2021-11-17 13:26:15', '2021-11-17 13:26:15', 'auto_upload_1415_1637126775642.sql', 1, 1);
INSERT INTO sqlexam.main_questions (id, teacher_id, title, `desc`, db_path, created_at, updated_at, file_name, total_difficulty, sub_count) VALUES (1420, '0', '找到连续区间的开始和结束数字', '表：Logs

```
+---------------+---------+
| Column Name   | Type    |
+---------------+---------+
| log_id        | int     |
+---------------+---------+
```

id 是上表的主键。

上表的每一行包含日志表中的一个 ID。

后来一些 ID 从Logs表中删除。编写一个 SQL 查询得到Logs表中的连续区间的开始数字和结束数字。

将查询表按照 start_id排序。

查询结果格式如下面的例子：

Logs 表：

```
+------------+
| log_id     |
+------------+
| 1          |
| 2          |
| 3          |
| 7          |
| 8          |
| 10         |
+------------+
```

结果表：

```
+------------+--------------+
| start_id   | end_id       |
+------------+--------------+
| 1          | 3            |
| 7          | 8            |
| 10         | 10           |
+------------+--------------+
```

结果表应包含 Logs 表中的所有区间。

从 1 到 3 在表中。

从 4 到 6 不在表中。

从 7 到 8 在表中。

9 不在表中。

10 在表中。

', 'examDataFiles/auto_upload_1420_1637126775642.sql', '2021-11-17 13:26:15', '2021-11-17 13:26:15', 'auto_upload_1420_1637126775642.sql', 2, 1);
INSERT INTO sqlexam.main_questions (id, teacher_id, title, `desc`, db_path, created_at, updated_at, file_name, total_difficulty, sub_count) VALUES (1425, '0', '不同国家的天气类型', '国家表：Countries

```
+---------------+---------+
| Column Name   | Type    |
+---------------+---------+
| country_id    | int     |
| country_name  | varchar |
+---------------+---------+
```

country_id 是这张表的主键。

该表的每行有 country_id 和 country_name 两列。

天气表：Weather

```
+---------------+---------+
| Column Name   | Type    |
+---------------+---------+
| country_id    | int     |
| weather_state | varchar |
| day           | date    |
+---------------+---------+
```

(country_id, day) 是该表的复合主键。

该表的每一行记录了某个国家某一天的天气情况。

写一段 SQL 来找到表中每个国家在 2019 年 11 月的天气类型。

天气类型的定义如下：当 weather_state 的平均值小于或等于15返回 Cold，当 weather_state 的平均值大于或等于 25 返回 Hot，否则返回Warm。

你可以以任意顺序返回你的查询结果。

查询结果格式如下所示：

Countries table:

```
+------------+--------------+
| country_id | country_name |
+------------+--------------+
| 2          | USA          |
| 3          | Australia    |
| 7          | Peru         |
| 5          | China        |
| 8          | Morocco      |
| 9          | Spain        |
+------------+--------------+
```

Weather table:

```
+------------+---------------+------------+
| country_id | weather_state | day        |
+------------+---------------+------------+
| 2          | 15            | 2019-11-01 |
| 2          | 12            | 2019-10-28 |
| 2          | 12            | 2019-10-27 |
| 3          | -2            | 2019-11-10 |
| 3          | 0             | 2019-11-11 |
| 3          | 3             | 2019-11-12 |
| 5          | 16            | 2019-11-07 |
| 5          | 18            | 2019-11-09 |
| 5          | 21            | 2019-11-23 |
| 7          | 25            | 2019-11-28 |
| 7          | 22            | 2019-12-01 |
| 7          | 20            | 2019-12-02 |
| 8          | 25            | 2019-11-05 |
| 8          | 27            | 2019-11-15 |
| 8          | 31            | 2019-11-25 |
| 9          | 7             | 2019-10-23 |
| 9          | 3             | 2019-12-23 |
+------------+---------------+------------+
```

Result table:

```
+--------------+--------------+
| country_name | weather_type |
+--------------+--------------+
| USA          | Cold         |
| Austraila    | Cold         |
| Peru         | Hot          |
| China        | Warm         |
| Morocco      | Hot          |
+--------------+--------------+
```

USA 11 月的平均 weather_state 为 (15) / 1 = 15 所以天气类型为 Cold。

Australia 11 月的平均 weather_state 为 (-2 + 0 + 3) / 3 = 0.333 所以天气类型为 Cold。

Peru 11 月的平均 weather_state 为 (25) / 1 = 25 所以天气类型为 Hot。

China 11 月的平均 weather_state 为 (16 + 18 + 21) / 3 = 18.333 所以天气类型为 Warm。

Morocco 11 月的平均 weather_state 为 (25 + 27 + 31) / 3 = 27.667 所以天气类型为 Hot。

我们并不知道 Spain 在 11 月的 weather_state 情况所以无需将他包含在结果中。

', 'examDataFiles/auto_upload_1425_1637126775642.sql', '2021-11-17 13:26:15', '2021-11-17 13:26:15', 'auto_upload_1425_1637126775642.sql', 1, 1);
INSERT INTO sqlexam.main_questions (id, teacher_id, title, `desc`, db_path, created_at, updated_at, file_name, total_difficulty, sub_count) VALUES (1438, '0', '求团队人数', '员工表：Employee

```
+---------------+---------+
| Column Name   | Type    |
+---------------+---------+
| employee_id   | int     |
| team_id       | int     |
+---------------+---------+
```

employee_id 字段是这张表的主键，表中的每一行都包含每个员工的 ID 和他们所属的团队。

编写一个 SQL 查询，以求得每个员工所在团队的总人数。

查询结果中的顺序无特定要求。

查询结果格式示例如下：

Employee Table:

```
+-------------+------------+
| employee_id | team_id    |
+-------------+------------+
|     1       |     8      |
|     2       |     8      |
|     3       |     8      |
|     4       |     7      |
|     5       |     9      |
|     6       |     9      |
+-------------+------------+
```

Result table:

```
+-------------+------------+
| employee_id | team_size  |
+-------------+------------+
|     1       |     3      |
|     2       |     3      |
|     3       |     3      |
|     4       |     1      |
|     5       |     2      |
|     6       |     2      |
+-------------+------------+
```

ID 为 1、2、3 的员工是 team_id 为 8 的团队的成员，

ID 为 4 的员工是 team_id 为 7 的团队的成员，

ID 为 5、6 的员工是 team_id 为 9 的团队的成员。

', 'examDataFiles/auto_upload_1438_1637126775642.sql', '2021-11-17 13:26:15', '2021-11-17 13:26:15', 'auto_upload_1438_1637126775642.sql', 1, 1);
INSERT INTO sqlexam.main_questions (id, teacher_id, title, `desc`, db_path, created_at, updated_at, file_name, total_difficulty, sub_count) VALUES (1439, '0', '不同性别每日分数总计', '表: Scores

```
+---------------+---------+
| Column Name   | Type    |
+---------------+---------+
| player_name   | varchar |
| gender        | varchar |
| day           | date    |
| score_points  | int     |
+---------------+---------+
```

(gender, day)是该表的主键

一场比赛是在女队和男队之间举行的

该表的每一行表示一个名叫 (player_name) 性别为 (gender) 的参赛者在某一天获得了 (score_points) 的分数

如果参赛者是女性，那么 gender 列为 ''F''，如果参赛者是男性，那么 gender 列为 ''M''

写一条SQL语句查询每种性别在每一天的总分，并按性别和日期对查询结果排序

下面是查询结果格式的例子：

Scores表:

```
+-------------+--------+------------+--------------+
| player_name | gender | day        | score_points |
+-------------+--------+------------+--------------+
| Aron        | F      | 2020-01-01 | 17           |
| Alice       | F      | 2020-01-07 | 23           |
| Bajrang     | M      | 2020-01-07 | 7            |
| Khali       | M      | 2019-12-25 | 11           |
| Slaman      | M      | 2019-12-30 | 13           |
| Joe         | M      | 2019-12-31 | 3            |
| Jose        | M      | 2019-12-18 | 2            |
| Priya       | F      | 2019-12-31 | 23           |
| Priyanka    | F      | 2019-12-30 | 17           |
+-------------+--------+------------+--------------+
```

结果表:

```
+--------+------------+-------+
| gender | day        | total |
+--------+------------+-------+
| F      | 2019-12-30 | 17    |
| F      | 2019-12-31 | 40    |
| F      | 2020-01-01 | 57    |
| F      | 2020-01-07 | 80    |
| M      | 2019-12-18 | 2     |
| M      | 2019-12-25 | 13    |
| M      | 2019-12-30 | 26    |
| M      | 2019-12-31 | 29    |
| M      | 2020-01-07 | 36    |
+--------+------------+-------+
```

女性队伍:

第一天是 2019-12-30，Priyanka 获得 17 分，队伍的总分是 17 分

第二天是 2019-12-31, Priya 获得 23 分，队伍的总分是 40 分

第三天是 2020-01-01, Aron 获得 17 分，队伍的总分是 57 分

第四天是 2020-01-07, Alice 获得 23 分，队伍的总分是 80 分

男性队伍：

第一天是 2019-12-18, Jose 获得 2 分，队伍的总分是 2 分

第二天是 2019-12-25, Khali 获得 11 分，队伍的总分是 13 分

第三天是 2019-12-30, Slaman 获得 13 分，队伍的总分是 26 分

第四天是 2019-12-31, Joe 获得 3 分，队伍的总分是 29 分

第五天是 2020-01-07, Bajrang 获得 7 分，队伍的总分是 36 分

', 'examDataFiles/auto_upload_1439_1637126775642.sql', '2021-11-17 13:26:15', '2021-11-17 13:26:15', 'auto_upload_1439_1637126775642.sql', 2, 1);
INSERT INTO sqlexam.main_questions (id, teacher_id, title, `desc`, db_path, created_at, updated_at, file_name, total_difficulty, sub_count) VALUES (1452, '0', '餐馆营业额变化增长', '表: Customer

```
+---------------+---------+
| Column Name   | Type    |
+---------------+---------+
| customer_id   | int     |
| name          | varchar |
| visited_on    | date    |
| amount        | int     |
+---------------+---------+
```

(customer_id, visited_on) 是该表的主键

该表包含一家餐馆的顾客交易数据

visited_on 表示 (customer_id) 的顾客在 visited_on 那天访问了餐馆

amount 是一个顾客某一天的消费总额

你是餐馆的老板，现在你想分析一下可能的营业额变化增长（每天至少有一位顾客）

写一条 SQL 查询计算以 7 天（某日期 + 该日期前的 6 天）为一个时间段的顾客消费平均值

查询结果格式的例子如下：

查询结果按 visited_on 排序

average_amount要 保留两位小数，日期数据的格式为(''YYYY-MM-DD'')

Customer 表:

```
+-------------+--------------+--------------+-------------+
| customer_id | name         | visited_on   | amount      |
+-------------+--------------+--------------+-------------+
| 1           | Jhon         | 2019-01-01   | 100         |
| 2           | Daniel       | 2019-01-02   | 110         |
| 3           | Jade         | 2019-01-03   | 120         |
| 4           | Khaled       | 2019-01-04   | 130         |
| 5           | Winston      | 2019-01-05   | 110         | 
| 6           | Elvis        | 2019-01-06   | 140         | 
| 7           | Anna         | 2019-01-07   | 150         |
| 8           | Maria        | 2019-01-08   | 80          |
| 9           | Jaze         | 2019-01-09   | 110         | 
| 1           | Jhon         | 2019-01-10   | 130         | 
| 3           | Jade         | 2019-01-10   | 150         | 
+-------------+--------------+--------------+-------------+
```

结果表:

```
+--------------+--------------+----------------+
| visited_on   | amount       | average_amount |
+--------------+--------------+----------------+
| 2019-01-07   | 860          | 122.86         |
| 2019-01-08   | 840          | 120            |
| 2019-01-09   | 840          | 120            |
| 2019-01-10   | 1000         | 142.86         |
+--------------+--------------+----------------+
```

第一个七天消费平均值从 2019-01-01 到 2019-01-07 是 (100 + 110 + 120 + 130 + 110 + 140 + 150)/7 = 122.86

第二个七天消费平均值从 2019-01-02 到 2019-01-08 是 (110 + 120 + 130 + 110 + 140 + 150 + 80)/7 = 120

第三个七天消费平均值从 2019-01-03 到 2019-01-09 是 (120 + 130 + 110 + 140 + 150 + 80 + 110)/7 = 120

第四个七天消费平均值从 2019-01-04 到 2019-01-10 是 (130 + 110 + 140 + 150 + 80 + 110 + 130 + 150)/7 = 142.86

', 'examDataFiles/auto_upload_1452_1637126775642.sql', '2021-11-17 13:26:15', '2021-11-17 13:26:15', 'auto_upload_1452_1637126775642.sql', 2, 1);
INSERT INTO sqlexam.main_questions (id, teacher_id, title, `desc`, db_path, created_at, updated_at, file_name, total_difficulty, sub_count) VALUES (1453, '0', '广告效果', '表: Ads

```
+---------------+---------+
| Column Name   | Type    |
+---------------+---------+
| ad_id         | int     |
| user_id       | int     |
| action        | enum    |
+---------------+---------+
```

(ad_id, user_id) 是该表的主键

该表的每一行包含一条广告的 ID(ad_id)，用户的 ID(user_id) 和用户对广告采取的行为 (action)

action 列是一个枚举类型 (''Clicked'', ''Viewed'', ''Ignored'') 。

一家公司正在运营这些广告并想计算每条广告的效果。

广告效果用点击通过率（Click-Through Rate：CTR）来衡量，公式如下:

写一条SQL语句来查询每一条广告的ctr，

ctr要保留两位小数。结果需要按ctr降序、按ad_id升序进行排序。

查询结果示例如下：

Ads 表:

```
+-------+---------+---------+
| ad_id | user_id | action  |
+-------+---------+---------+
| 1     | 1       | Clicked |
| 2     | 2       | Clicked |
| 3     | 3       | Viewed  |
| 5     | 5       | Ignored |
| 1     | 7       | Ignored |
| 2     | 7       | Viewed  |
| 3     | 5       | Clicked |
| 1     | 4       | Viewed  |
| 2     | 11      | Viewed  |
| 1     | 2       | Clicked |
+-------+---------+---------+
```

结果表:

```
+-------+-------+
| ad_id | ctr   |
+-------+-------+
| 1     | 66.67 |
| 3     | 50.00 |
| 2     | 33.33 |
| 5     | 0.00  |
+-------+-------+
```

对于 ad_id = 1, ctr = (2/(2+1)) * 100 = 66.67

对于 ad_id = 2, ctr = (1/(1+2)) * 100 = 33.33

对于 ad_id = 3, ctr = (1/(1+1)) * 100 = 50.00

对于 ad_id = 5, ctr = 0.00, 注意 ad_id = 5 没有被点击 (Clicked) 或查看 (Viewed) 过

注意我们不关心 action 为 Ingnored 的广告

结果按 ctr（降序），ad_id（升序）排序

', 'examDataFiles/auto_upload_1453_1637126775642.sql', '2021-11-17 13:26:15', '2021-11-17 13:26:15', 'auto_upload_1453_1637126775642.sql', 1, 1);
INSERT INTO sqlexam.main_questions (id, teacher_id, title, `desc`, db_path, created_at, updated_at, file_name, total_difficulty, sub_count) VALUES (1462, '0', '列出指定时间段内所有的下单产品', '表: Products

```
+------------------+---------+
| Column Name      | Type    |
+------------------+---------+
| product_id       | int     |
| product_name     | varchar |
| product_category | varchar |
+------------------+---------+
```

product_id 是该表主键。

该表包含该公司产品的数据。

表: Orders

```
+---------------+---------+
| Column Name   | Type    |
+---------------+---------+
| product_id    | int     |
| order_date    | date    |
| unit          | int     |
+---------------+---------+
```

该表无主键，可能包含重复行。

product_id 是表单 Products 的外键。

unit 是在日期 order_date 内下单产品的数目。

写一个 SQL 语句，要求获取在 2020 年 2 月份下单的数量不少于 100 的产品的名字和数目。

返回结果表单的顺序无要求。

查询结果的格式如下：

Products 表:

```
+-------------+-----------------------+------------------+
| product_id  | product_name          | product_category |
+-------------+-----------------------+------------------+
| 1           | Leetcode Solutions    | Book             |
| 2           | Jewels of Stringology | Book             |
| 3           | HP                    | Laptop           |
| 4           | Lenovo                | Laptop           |
| 5           | Leetcode Kit          | T-shirt          |
+-------------+-----------------------+------------------+
```

Orders 表:

```
+--------------+--------------+----------+
| product_id   | order_date   | unit     |
+--------------+--------------+----------+
| 1            | 2020-02-05   | 60       |
| 1            | 2020-02-10   | 70       |
| 2            | 2020-01-18   | 30       |
| 2            | 2020-02-11   | 80       |
| 3            | 2020-02-17   | 2        |
| 3            | 2020-02-24   | 3        |
| 4            | 2020-03-01   | 20       |
| 4            | 2020-03-04   | 30       |
| 4            | 2020-03-04   | 60       |
| 5            | 2020-02-25   | 50       |
| 5            | 2020-02-27   | 50       |
| 5            | 2020-03-01   | 50       |
+--------------+--------------+----------+
```

Result 表:

```
+--------------------+---------+
| product_name       | unit    |
+--------------------+---------+
| Leetcode Solutions | 130     |
| Leetcode Kit       | 100     |
+--------------------+---------+
```

2020 年 2 月份下单 product_id = 1 的产品的数目总和为 (60 + 70) = 130 。

2020 年 2 月份下单 product_id = 2 的产品的数目总和为 80 。

2020 年 2 月份下单 product_id = 3 的产品的数目总和为 (2 + 3) = 5 。

2020 年 2 月份 product_id = 4 的产品并没有下单。

2020 年 2 月份下单 product_id = 5 的产品的数目总和为 (50 + 50) = 100 。

', 'examDataFiles/auto_upload_1462_1637126775642.sql', '2021-11-17 13:26:15', '2021-11-17 13:26:15', 'auto_upload_1462_1637126775642.sql', 1, 1);
INSERT INTO sqlexam.main_questions (id, teacher_id, title, `desc`, db_path, created_at, updated_at, file_name, total_difficulty, sub_count) VALUES (1480, '0', '电影评分', '表：Movies

```
+---------------+---------+
| Column Name   | Type    |
+---------------+---------+
| movie_id      | int     |
| title         | varchar |
+---------------+---------+
```

movie_id 是这个表的主键。

title 是电影的名字。

表：Users

```
+---------------+---------+
| Column Name   | Type    |
+---------------+---------+
| user_id       | int     |
| name          | varchar |
+---------------+---------+
```

user_id 是表的主键。

表：Movie_Rating

```
+---------------+---------+
| Column Name   | Type    |
+---------------+---------+
| movie_id      | int     |
| user_id       | int     |
| rating        | int     |
| created_at    | date    |
+---------------+---------+
```

(movie_id, user_id) 是这个表的主键。

这个表包含用户在其评论中对电影的评分 rating 。

created_at 是用户的点评日期。 

请你编写一组SQL 查询：

查找评论电影数量最多的用户名。

	如果出现平局，返回字典序较小的用户名。

查找在 2020 年 2 月 平均评分最高 的电影名称。

	如果出现平局，返回字典序较小的电影名称。

字典序 ，即按字母在字典中出现顺序对字符串排序，字典序较小则意味着排序靠前。

查询分两行返回，查询结果格式如下例所示：

Movies 表：

```
+-------------+--------------+
| movie_id    |  title       |
+-------------+--------------+
| 1           | Avengers     |
| 2           | Frozen 2     |
| 3           | Joker        |
+-------------+--------------+
```

Users 表：

```
+-------------+--------------+
| user_id     |  name        |
+-------------+--------------+
| 1           | Daniel       |
| 2           | Monica       |
| 3           | Maria        |
| 4           | James        |
+-------------+--------------+
```

Movie_Rating 表：

```
+-------------+--------------+--------------+-------------+
| movie_id    | user_id      | rating       | created_at  |
+-------------+--------------+--------------+-------------+
| 1           | 1            | 3            | 2020-01-12  |
| 1           | 2            | 4            | 2020-02-11  |
| 1           | 3            | 2            | 2020-02-12  |
| 1           | 4            | 1            | 2020-01-01  |
| 2           | 1            | 5            | 2020-02-17  | 
| 2           | 2            | 2            | 2020-02-01  | 
| 2           | 3            | 2            | 2020-03-01  |
| 3           | 1            | 3            | 2020-02-22  | 
| 3           | 2            | 4            | 2020-02-25  | 
+-------------+--------------+--------------+-------------+
```

Result 表：

```
+--------------+
| results      |
+--------------+
| Daniel       |
| Frozen 2     |
+--------------+
```

Daniel 和 Monica 都点评了 3 部电影（"Avengers", "Frozen 2" 和 "Joker"） 但是 Daniel 字典序比较小。

Frozen 2 和 Joker 在 2 月的评分都是 3.5，但是 Frozen 2 的字典序比较小。

', 'examDataFiles/auto_upload_1480_1637126775642.sql', '2021-11-17 13:26:15', '2021-11-17 13:26:15', 'auto_upload_1480_1637126775642.sql', 2, 1);
INSERT INTO sqlexam.main_questions (id, teacher_id, title, `desc`, db_path, created_at, updated_at, file_name, total_difficulty, sub_count) VALUES (1481, '0', '院系无效的学生', '院系表: Departments

```
+---------------+---------+
| Column Name   | Type    |
+---------------+---------+
| id            | int     |
| name          | varchar |
+---------------+---------+
```

id 是该表的主键

该表包含一所大学每个院系的 id 信息

学生表: Students

```
+---------------+---------+
| Column Name   | Type    |
+---------------+---------+
| id            | int     |
| name          | varchar |
| department_id | int     |
+---------------+---------+
```

id 是该表的主键

该表包含一所大学每个学生的 id 和他/她就读的院系信息

写一条 SQL 语句以查询那些所在院系不存在的学生的 id 和姓名

可以以任何顺序返回结果

下面是返回结果格式的例子

Departments 表:

```
+------+--------------------------+
| id   | name                     |
+------+--------------------------+
| 1    | Electrical Engineering   |
| 7    | Computer Engineering     |
| 13   | Bussiness Administration |
+------+--------------------------+
```

Students 表:

```
+------+----------+---------------+
| id   | name     | department_id |
+------+----------+---------------+
| 23   | Alice    | 1             |
| 1    | Bob      | 7             |
| 5    | Jennifer | 13            |
| 2    | John     | 14            |
| 4    | Jasmine  | 77            |
| 3    | Steve    | 74            |
| 6    | Luis     | 1             |
| 8    | Jonathan | 7             |
| 7    | Daiana   | 33            |
| 11   | Madelynn | 1             |
+------+----------+---------------+
```

结果表:

```
+------+----------+
| id   | name     |
+------+----------+
| 2    | John     |
| 7    | Daiana   |
| 4    | Jasmine  |
| 3    | Steve    |
+------+----------+
```

John, Daiana, Steve 和 Jasmine 所在的院系分别是 14, 33, 74 和 77， 其中 14, 33, 74 和 77 并不存在于院系表

', 'examDataFiles/auto_upload_1481_1637126775642.sql', '2021-11-17 13:26:15', '2021-11-17 13:26:15', 'auto_upload_1481_1637126775642.sql', 1, 1);
INSERT INTO sqlexam.main_questions (id, teacher_id, title, `desc`, db_path, created_at, updated_at, file_name, total_difficulty, sub_count) VALUES (1494, '0', '活动参与者', '表: Friends

```
+---------------+---------+
| Column Name   | Type    |
+---------------+---------+
| id            | int     |
| name          | varchar |
| activity      | varchar |
+---------------+---------+
```

id 是朋友的 id 和该表的主键

name 是朋友的名字

activity 是朋友参加的活动的名字

表: Activities

```
+---------------+---------+
| Column Name   | Type    |
+---------------+---------+
| id            | int     |
| name          | varchar |
+---------------+---------+
```

id 是该表的主键

name 是活动的名字

写一条 SQL 查询那些既没有最多，也没有最少参与者的活动的名字

可以以任何顺序返回结果，Activities 表的每项活动的参与者都来自 Friends 表

注意：名称相同 id 不同的参与者算作两个人

下面是查询结果格式的例子：

Friends 表:

```
+------+--------------+---------------+
| id   | name         | activity      |
+------+--------------+---------------+
| 1    | Jonathan D.  | Eating        |
| 2    | Jade W.      | Singing       |
| 3    | Victor J.    | Singing       |
| 4    | Elvis Q.     | Eating        |
| 5    | Daniel A.    | Eating        |
| 6    | Bob B.       | Horse Riding  |
+------+--------------+---------------+
```

Activities 表:

```
+------+--------------+
| id   | name         |
+------+--------------+
| 1    | Eating       |
| 2    | Singing      |
| 3    | Horse Riding |
+------+--------------+
```

Result 表:

```
+--------------+
| activity     |
+--------------+
| Singing      |
+--------------+
```

Eating 活动有三个人参加, 是最多人参加的活动 (Jonathan D. , Elvis Q. and Daniel A.)

Horse Riding 活动有一个人参加, 是最少人参加的活动 (Bob B.)

Singing 活动有两个人参加 (Victor J. and Jade W.)

', 'examDataFiles/auto_upload_1494_1637126775642.sql', '2021-11-17 13:26:15', '2021-11-17 13:26:15', 'auto_upload_1494_1637126775642.sql', 2, 1);
INSERT INTO sqlexam.main_questions (id, teacher_id, title, `desc`, db_path, created_at, updated_at, file_name, total_difficulty, sub_count) VALUES (1495, '0', '顾客的可信联系人数量', '顾客表：Customers

```
+---------------+---------+
| Column Name   | Type    |
+---------------+---------+
| customer_id   | int     |
| customer_name | varchar |
| email         | varchar |
+---------------+---------+
```

customer_id 是这张表的主键。

此表的每一行包含了某在线商店顾客的姓名和电子邮件。

联系方式表：Contacts

```
+---------------+---------+
| Column Name   | Type    |
+---------------+---------+
| user_id       | id      |
| contact_name  | varchar |
| contact_email | varchar |
+---------------+---------+
```

(user_id, contact_email) 是这张表的主键。

此表的每一行表示编号为 user_id 的顾客的某位联系人的姓名和电子邮件。

此表包含每位顾客的联系人信息，但顾客的联系人不一定存在于顾客表中。

发票表：Invoices

```
+--------------+---------+
| Column Name  | Type    |
+--------------+---------+
| invoice_id   | int     |
| price        | int     |
| user_id      | int     |
+--------------+---------+
```

invoice_id 是这张表的主键。

此表的每一行分别表示编号为 user_id 的顾客拥有有一张编号为 invoice_id、价格为 price 的发票。

为每张发票 invoice_id 编写一个SQL查询以查找以下内容：

customer_name：与发票相关的顾客名称。

price：发票的价格。

contacts_cnt：该顾客的联系人数量。

trusted_contacts_cnt：可信联系人的数量：既是该顾客的联系人又是商店顾客的联系人数量（即：可信联系人的电子邮件存在于客户表中）。

将查询的结果按照invoice_id排序。

查询结果的格式如下例所示：

Customers table:

```
+-------------+---------------+--------------------+
| customer_id | customer_name | email              |
+-------------+---------------+--------------------+
| 1           | Alice         | alice@leetcode.com |
| 2           | Bob           | bob@leetcode.com   |
| 13          | John          | john@leetcode.com  |
| 6           | Alex          | alex@leetcode.com  |
+-------------+---------------+--------------------+
```

Contacts table:

```
+-------------+--------------+--------------------+
| user_id     | contact_name | contact_email      |
+-------------+--------------+--------------------+
| 1           | Bob          | bob@leetcode.com   |
| 1           | John         | john@leetcode.com  |
| 1           | Jal          | jal@leetcode.com   |
| 2           | Omar         | omar@leetcode.com  |
| 2           | Meir         | meir@leetcode.com  |
| 6           | Alice        | alice@leetcode.com |
+-------------+--------------+--------------------+
```

Invoices table:

```
+------------+-------+---------+
| invoice_id | price | user_id |
+------------+-------+---------+
| 77         | 100   | 1       |
| 88         | 200   | 1       |
| 99         | 300   | 2       |
| 66         | 400   | 2       |
| 55         | 500   | 13      |
| 44         | 60    | 6       |
+------------+-------+---------+
```

Result table:

```
+------------+---------------+-------+--------------+----------------------+
| invoice_id | customer_name | price | contacts_cnt | trusted_contacts_cnt |
+------------+---------------+-------+--------------+----------------------+
| 44         | Alex          | 60    | 1            | 1                    |
| 55         | John          | 500   | 0            | 0                    |
| 66         | Bob           | 400   | 2            | 0                    |
| 77         | Alice         | 100   | 3            | 2                    |
| 88         | Alice         | 200   | 3            | 2                    |
| 99         | Bob           | 300   | 2            | 0                    |
+------------+---------------+-------+--------------+----------------------+
```

Alice 有三位联系人，其中两位(Bob 和 John)是可信联系人。

Bob 有两位联系人, 他们中的任何一位都不是可信联系人。

Alex 只有一位联系人(Alice)，并是一位可信联系人。

John 没有任何联系人。

', 'examDataFiles/auto_upload_1495_1637126775642.sql', '2021-11-17 13:26:15', '2021-11-17 13:26:15', 'auto_upload_1495_1637126775642.sql', 2, 1);
INSERT INTO sqlexam.main_questions (id, teacher_id, title, `desc`, db_path, created_at, updated_at, file_name, total_difficulty, sub_count) VALUES (1504, '0', '获取最近第二次的活动', '表: UserActivity

```
+---------------+---------+
| Column Name   | Type    |
+---------------+---------+
| username      | varchar |
| activity      | varchar |
| startDate     | Date    |
| endDate       | Date    |
+---------------+---------+
```

该表不包含主键

该表包含每个用户在一段时间内进行的活动的信息

名为 username 的用户在 startDate 到 endDate 日内有一次活动

写一条SQL查询展示每一位用户 最近第二次 的活动

如果用户仅有一次活动，返回该活动

一个用户不能同时进行超过一项活动，以 任意 顺序返回结果

下面是查询结果格式的例子：

UserActivity 表:

```
+------------+--------------+-------------+-------------+
| username   | activity     | startDate   | endDate     |
+------------+--------------+-------------+-------------+
| Alice      | Travel       | 2020-02-12  | 2020-02-20  |
| Alice      | Dancing      | 2020-02-21  | 2020-02-23  |
| Alice      | Travel       | 2020-02-24  | 2020-02-28  |
| Bob        | Travel       | 2020-02-11  | 2020-02-18  |
+------------+--------------+-------------+-------------+
```

Result 表:

```
+------------+--------------+-------------+-------------+
| username   | activity     | startDate   | endDate     |
+------------+--------------+-------------+-------------+
| Alice      | Dancing      | 2020-02-21  | 2020-02-23  |
| Bob        | Travel       | 2020-02-11  | 2020-02-18  |
+------------+--------------+-------------+-------------+
```

Alice 最近一次的活动是从 2020-02-24 到 2020-02-28 的旅行, 在此之前的 2020-02-21 到 2020-02-23 她进行了舞蹈

Bob 只有一条记录，我们就取这条记录

', 'examDataFiles/auto_upload_1504_1637126775642.sql', '2021-11-17 13:26:15', '2021-11-17 13:26:15', 'auto_upload_1504_1637126775642.sql', 3, 1);
INSERT INTO sqlexam.main_questions (id, teacher_id, title, `desc`, db_path, created_at, updated_at, file_name, total_difficulty, sub_count) VALUES (1509, '0', '使用唯一标识码替换员工ID', 'Employees 表：

```
+---------------+---------+
| Column Name   | Type    |
+---------------+---------+
| id            | int     |
| name          | varchar |
+---------------+---------+
```

id 是这张表的主键。

这张表的每一行分别代表了某公司其中一位员工的名字和 ID 。

EmployeeUNI表：

```
+---------------+---------+
| Column Name   | Type    |
+---------------+---------+
| id            | int     |
| unique_id     | int     |
+---------------+---------+
```

(id, unique_id) 是这张表的主键。

这张表的每一行包含了该公司某位员工的 ID 和他的唯一标识码（unique ID）。

写一段SQL查询来展示每位用户的 唯一标识码（unique ID ）；如果某位员工没有唯一标识码，使用 null 填充即可。

你可以以 任意 顺序返回结果表。

查询结果的格式如下例所示：

Employees table:

```
+----+----------+
| id | name     |
+----+----------+
| 1  | Alice    |
| 7  | Bob      |
| 11 | Meir     |
| 90 | Winston  |
| 3  | Jonathan |
+----+----------+
```

EmployeeUNI table:

```
+----+-----------+
| id | unique_id |
+----+-----------+
| 3  | 1         |
| 11 | 2         |
| 90 | 3         |
+----+-----------+
```

EmployeeUNI table:

```
+-----------+----------+
| unique_id | name     |
+-----------+----------+
| null      | Alice    |
| null      | Bob      |
| 2         | Meir     |
| 3         | Winston  |
| 1         | Jonathan |
+-----------+----------+
```

Alice and Bob 没有唯一标识码, 因此我们使用 null 替代。

Meir 的唯一标识码是 2 。

Winston 的唯一标识码是 3 。

Jonathan 唯一标识码是 1 。

', 'examDataFiles/auto_upload_1509_1637126775642.sql', '2021-11-17 13:26:15', '2021-11-17 13:26:15', 'auto_upload_1509_1637126775642.sql', 1, 1);
INSERT INTO sqlexam.main_questions (id, teacher_id, title, `desc`, db_path, created_at, updated_at, file_name, total_difficulty, sub_count) VALUES (1518, '0', '按年度列出销售总额', 'Product表：

```
+---------------+---------+
| Column Name   | Type    |
+---------------+---------+
| product_id    | int     |
| product_name  | varchar |
+---------------+---------+
```

product_id 是这张表的主键。

product_name 是产品的名称。

Sales表：

```
+---------------------+---------+
| Column Name         | Type    |
+---------------------+---------+
| product_id          | int     |
| period_start        | date    |
| period_end          | date    |
| average_daily_sales | int     |
+---------------------+---------+
```

product_id 是这张表的主键。

period_start和 period_end是该产品销售期的起始日期和结束日期，且这两个日期包含在销售期内。

average_daily_sales 列存储销售期内该产品的日平均销售额。

编写一段 SQL 查询每个产品每年的总销售额，并包含 product_id, product_name 以及 report_year 等信息。

销售年份的日期介于 2018 年到 2020 年之间。你返回的结果需要按product_id 和 report_year 排序。

查询结果格式如下例所示：

Product table:

```
+------------+--------------+
| product_id | product_name |
+------------+--------------+
| 1          | LC Phone     |
| 2          | LC T-Shirt   |
| 3          | LC Keychain  |
+------------+--------------+
```

Sales table:

```
+------------+--------------+-------------+---------------------+
| product_id | period_start | period_end  | average_daily_sales |
+------------+--------------+-------------+---------------------+
| 1          | 2019-01-25   | 2019-02-28  | 100                 |
| 2          | 2018-12-01   | 2020-01-01  | 10                  |
| 3          | 2019-12-01   | 2020-01-31  | 1                   |
+------------+--------------+-------------+---------------------+
```

Result table:

```
+------------+--------------+-------------+--------------+
| product_id | product_name | report_year | total_amount |
+------------+--------------+-------------+--------------+
| 1          | LC Phone     |    2019     | 3500         |
| 2          | LC T-Shirt   |    2018     | 310          |
| 2          | LC T-Shirt   |    2019     | 3650         |
| 2          | LC T-Shirt   |    2020     | 10           |
| 3          | LC Keychain  |    2019     | 31           |
| 3          | LC Keychain  |    2020     | 31           |
+------------+--------------+-------------+--------------+
```

LC Phone 在 2019-01-25 至 2019-02-28 期间销售，该产品销售时间总计35天。销售总额 35*100 = 3500。

LC T-shirt 在 2018-12-01至 2020-01-01 期间销售，该产品在2018年、2019年、2020年的销售时间分别是31天、365天、1天，2018年、2019年、2020年的销售总额分别是31*10=310、365*10=3650、1*10=10。

LC Keychain 在 2019-12-01至 2020-01-31 期间销售，该产品在2019年、2020年的销售时间分别是：31天、31天，2019年、2020年的销售总额分别是31*1=31、31*1=31。

', 'examDataFiles/auto_upload_1518_1637126775642.sql', '2021-11-17 13:26:15', '2021-11-17 13:26:15', 'auto_upload_1518_1637126775642.sql', 3, 1);
INSERT INTO sqlexam.main_questions (id, teacher_id, title, `desc`, db_path, created_at, updated_at, file_name, total_difficulty, sub_count) VALUES (1523, '0', '股票的资本损益', 'Stocks表：

```
+---------------+---------+
| Column Name   | Type    |
+---------------+---------+
| stock_name    | varchar |
| operation     | enum    |
| operation_day | int     |
| price         | int     |
+---------------+---------+
```

(stock_name, day) 是这张表的主键

operation 列使用的是一种枚举类型，包括：(''Sell'',''Buy'')

此表的每一行代表了名为 stock_name 的某支股票在 operation_day 这一天的操作价格。

保证股票的每次''Sell''操作前，都有相应的''Buy''操作。

编写一个SQL查询来报告每支股票的资本损益。

股票的资本损益是一次或多次买卖股票后的全部收益或损失。

以任意顺序返回结果即可。

SQL查询结果的格式如下例所示：

Stocks 表:

```
+---------------+-----------+---------------+--------+
| stock_name    | operation | operation_day | price  |
+---------------+-----------+---------------+--------+
| Leetcode      | Buy       | 1             | 1000   |
| Corona Masks  | Buy       | 2             | 10     |
| Leetcode      | Sell      | 5             | 9000   |
| Handbags      | Buy       | 17            | 30000  |
| Corona Masks  | Sell      | 3             | 1010   |
| Corona Masks  | Buy       | 4             | 1000   |
| Corona Masks  | Sell      | 5             | 500    |
| Corona Masks  | Buy       | 6             | 1000   |
| Handbags      | Sell      | 29            | 7000   |
| Corona Masks  | Sell      | 10            | 10000  |
+---------------+-----------+---------------+--------+
```

Result 表:

```
+---------------+-------------------+
| stock_name    | capital_gain_loss |
+---------------+-------------------+
| Corona Masks  | 9500              |
| Leetcode      | 8000              |
| Handbags      | -23000            |
+---------------+-------------------+
```

Leetcode 股票在第一天以1000美元的价格买入，在第五天以9000美元的价格卖出。资本收益=9000-1000=8000美元。

Handbags 股票在第17天以30000美元的价格买入，在第29天以7000美元的价格卖出。资本损失=7000-30000=-23000美元。

Corona Masks 股票在第1天以10美元的价格买入，在第3天以1010美元的价格卖出。在第4天以1000美元的价格再次购买，在第5天以500美元的价格出售。最后，它在第6天以1000美元的价格被买走，在第10天以10000美元的价格被卖掉。资本损益是每次（’Buy''->''Sell''）操作资本收益或损失的和=（1010-10）+（500-1000）+（10000-1000）=1000-500+9000=9500美元。

', 'examDataFiles/auto_upload_1523_1637126775642.sql', '2021-11-17 13:26:15', '2021-11-17 13:26:15', 'auto_upload_1523_1637126775642.sql', 2, 1);
INSERT INTO sqlexam.main_questions (id, teacher_id, title, `desc`, db_path, created_at, updated_at, file_name, total_difficulty, sub_count) VALUES (1536, '0', '购买了产品 A 和产品 B 却没有购买产品 C 的顾客', 'Customers表：

```
+---------------------+---------+
| Column Name         | Type    |
+---------------------+---------+
| customer_id         | int     |
| customer_name       | varchar |
+---------------------+---------+
```

customer_id 是这张表的主键。

customer_name 是顾客的名称。

Orders表：

```
+---------------+---------+
| Column Name   | Type    |
+---------------+---------+
| order_id      | int     |
| customer_id   | int     |
| product_name  | varchar |
+---------------+---------+
```

order_id 是这张表的主键。

customer_id 是购买了名为 "product_name" 产品顾客的id。

请你设计 SQL 查询来报告购买了产品 A 和产品 B 却没有购买产品 C 的顾客的 ID 和姓名（ customer_id 和customer_name ），我们将基于此结果为他们推荐产品 C 。

您返回的查询结果需要按照customer_id 排序。

查询结果如下例所示。

Customers table:

```
+-------------+---------------+
| customer_id | customer_name |
+-------------+---------------+
| 1           | Daniel        |
| 2           | Diana         |
| 3           | Elizabeth     |
| 4           | Jhon          |
+-------------+---------------+
```

Orders table:

```
+------------+--------------+---------------+
| order_id   | customer_id  | product_name  |
+------------+--------------+---------------+
| 10         |     1        |     A         |
| 20         |     1        |     B         |
| 30         |     1        |     D         |
| 40         |     1        |     C         |
| 50         |     2        |     A         |
| 60         |     3        |     A         |
| 70         |     3        |     B         |
| 80         |     3        |     D         |
| 90         |     4        |     C         |
+------------+--------------+---------------+
```

Result table:

```
+-------------+---------------+
| customer_id | customer_name |
+-------------+---------------+
| 3           | Elizabeth     |
+-------------+---------------+
```

只有 customer_id 为 3 的顾客购买了产品 A 和产品 B ，却没有购买产品 C 。

', 'examDataFiles/auto_upload_1536_1637126775643.sql', '2021-11-17 13:26:15', '2021-11-17 13:26:15', 'auto_upload_1536_1637126775643.sql', 2, 1);
INSERT INTO sqlexam.main_questions (id, teacher_id, title, `desc`, db_path, created_at, updated_at, file_name, total_difficulty, sub_count) VALUES (1541, '0', '排名靠前的旅行者', '表：Users

```
+---------------+---------+
| Column Name   | Type    |
+---------------+---------+
| id            | int     |
| name          | varchar |
+---------------+---------+
```

id 是该表单主键。

name 是用户名字。

表：Rides

```
+---------------+---------+
| Column Name   | Type    |
+---------------+---------+
| id            | int     |
| user_id       | int     |
| distance      | int     |
+---------------+---------+
```

id 是该表单主键。

user_id 是本次行程的用户的 id, 而该用户此次行程距离为 distance 。

写一段 SQL ,报告每个用户的旅行距离。

返回的结果表单，以travelled_distance降序排列 ，如果有两个或者更多的用户旅行了相同的距离,那么再以name升序排列 。

查询结果格式如下例所示。

Users 表：

```
+------+-----------+
| id   | name      |
+------+-----------+
| 1    | Alice     |
| 2    | Bob       |
| 3    | Alex      |
| 4    | Donald    |
| 7    | Lee       |
| 13   | Jonathan  |
| 19   | Elvis     |
+------+-----------+
```

Rides 表：

```
+------+----------+----------+
| id   | user_id  | distance |
+------+----------+----------+
| 1    | 1        | 120      |
| 2    | 2        | 317      |
| 3    | 3        | 222      |
| 4    | 7        | 100      |
| 5    | 13       | 312      |
| 6    | 19       | 50       |
| 7    | 7        | 120      |
| 8    | 19       | 400      |
| 9    | 7        | 230      |
+------+----------+----------+
```

Result 表：

```
+----------+--------------------+
| name     | travelled_distance |
+----------+--------------------+
| Elvis    | 450                |
| Lee      | 450                |
| Bob      | 317                |
| Jonathan | 312                |
| Alex     | 222                |
| Alice    | 120                |
| Donald   | 0                  |
+----------+--------------------+
```

Elvis 和 Lee 旅行了 450 英里，Elvis 是排名靠前的旅行者，因为他的名字在字母表上的排序比 Lee 更小。

Bob, Jonathan, Alex 和 Alice 只有一次行程，我们只按此次行程的全部距离对他们排序。

Donald 没有任何行程, 他的旅行距离为 0。

', 'examDataFiles/auto_upload_1541_1637126775643.sql', '2021-11-17 13:26:15', '2021-11-17 13:26:15', 'auto_upload_1541_1637126775643.sql', 1, 1);
INSERT INTO sqlexam.main_questions (id, teacher_id, title, `desc`, db_path, created_at, updated_at, file_name, total_difficulty, sub_count) VALUES (1546, '0', '查找成绩处于中游的学生', '表: Student

```
+---------------------+---------+
| Column Name         | Type    |
+---------------------+---------+
| student_id          | int     |
| student_name        | varchar |
+---------------------+---------+
```

student_id 是该表主键.

student_name 学生名字.

表: Exam

```
+---------------+---------+
| Column Name   | Type    |
+---------------+---------+
| exam_id       | int     |
| student_id    | int     |
| score         | int     |
+---------------+---------+
```

(exam_id, student_id) 是该表主键.

学生 student_id 在测验 exam_id 中得分为 score.

成绩处于中游的学生是指至少参加了一次测验,且得分既不是最高分也不是最低分的学生。

写一个 SQL 语句，找出在 所有 测验中都处于中游的学生 (student_id, student_name)。

不要返回从来没有参加过测验的学生。返回结果表按照student_id排序。

查询结果格式如下。

Student 表：

```
+-------------+---------------+
| student_id  | student_name  |
+-------------+---------------+
| 1           | Daniel        |
| 2           | Jade          |
| 3           | Stella        |
| 4           | Jonathan      |
| 5           | Will          |
+-------------+---------------+
```

Exam 表：

```
+------------+--------------+-----------+
| exam_id    | student_id   | score     |
+------------+--------------+-----------+
| 10         |     1        |    70     |
| 10         |     2        |    80     |
| 10         |     3        |    90     |
| 20         |     1        |    80     |
| 30         |     1        |    70     |
| 30         |     3        |    80     |
| 30         |     4        |    90     |
| 40         |     1        |    60     |
| 40         |     2        |    70     |
| 40         |     4        |    80     |
+------------+--------------+-----------+
```

Result 表：

```
+-------------+---------------+
| student_id  | student_name  |
+-------------+---------------+
| 2           | Jade          |
+-------------+---------------+
```

对于测验 1: 学生 1 和 3 分别获得了最低分和最高分。

对于测验 2: 学生 1 既获得了最高分, 也获得了最低分。

对于测验 3 和 4: 学生 1 和 4 分别获得了最低分和最高分。

学生 2 和 5 没有在任一场测验中获得了最高分或者最低分。

因为学生 5 从来没有参加过任何测验, 所以他被排除于结果表。

由此, 我们仅仅返回学生 2 的信息。

', 'examDataFiles/auto_upload_1546_1637126775643.sql', '2021-11-17 13:26:15', '2021-11-17 13:26:15', 'auto_upload_1546_1637126775643.sql', 3, 1);
INSERT INTO sqlexam.main_questions (id, teacher_id, title, `desc`, db_path, created_at, updated_at, file_name, total_difficulty, sub_count) VALUES (1551, '0', '净现值查询', '表: NPV

```
+---------------+---------+
| Column Name   | Type    |
+---------------+---------+
| id            | int     |
| year          | int     |
| npv           | int     |
+---------------+---------+
```

(id, year) 是该表主键.

该表有每一笔存货的年份, id 和对应净现值的信息.

表: Queries

```
+---------------+---------+
| Column Name   | Type    |
+---------------+---------+
| id            | int     |
| year          | int     |
+---------------+---------+
```

(id, year) 是该表主键.

该表有每一次查询所对应存货的 id 和年份的信息.

写一个 SQL,找到 Queries表中每一次查询的净现值.

结果表没有顺序要求.

查询结果的格式如下所示:

NPV 表:

```
+------+--------+--------+
| id   | year   | npv    |
+------+--------+--------+
| 1    | 2018   | 100    |
| 7    | 2020   | 30     |
| 13   | 2019   | 40     |
| 1    | 2019   | 113    |
| 2    | 2008   | 121    |
| 3    | 2009   | 12     |
| 11   | 2020   | 99     |
| 7    | 2019   | 0      |
+------+--------+--------+
```

Queries 表:

```
+------+--------+
| id   | year   |
+------+--------+
| 1    | 2019   |
| 2    | 2008   |
| 3    | 2009   |
| 7    | 2018   |
| 7    | 2019   |
| 7    | 2020   |
| 13   | 2019   |
+------+--------+
```

结果表:

```
+------+--------+--------+
| id   | year   | npv    |
+------+--------+--------+
| 1    | 2019   | 113    |
| 2    | 2008   | 121    |
| 3    | 2009   | 12     |
| 7    | 2018   | 0      |
| 7    | 2019   | 0      |
| 7    | 2020   | 30     |
| 13   | 2019   | 40     |
+------+--------+--------+
```

(7, 2018)的净现值不在 NPV 表中, 我们把它看作是 0.

所有其它查询的净现值都能在 NPV 表中找到.

', 'examDataFiles/auto_upload_1551_1637126775643.sql', '2021-11-17 13:26:15', '2021-11-17 13:26:15', 'auto_upload_1551_1637126775643.sql', 2, 1);
INSERT INTO sqlexam.main_questions (id, teacher_id, title, `desc`, db_path, created_at, updated_at, file_name, total_difficulty, sub_count) VALUES (1564, '0', '制作会话柱状图', '表：Sessions

```
+---------------------+---------+
| Column Name         | Type    |
+---------------------+---------+
| session_id          | int     |
| duration            | int     |
+---------------------+---------+
```

session_id 是该表主键

duration 是用户访问应用的时间, 以秒为单位

你想知道用户在你的 app 上的访问时长情况。因此决定统计访问时长区间分别为 "[0-5>", "[5-10>", "[10-15>"和"15 or more" （单位：分钟）的会话数量，并以此绘制柱状图。

写一个SQL查询来报告（访问时长区间，会话总数）。结果可用任何顺序呈现。

下方为查询的输出格式：

Sessions 表：

```
+-------------+---------------+
| session_id  | duration      |
+-------------+---------------+
| 1           | 30            |
| 2           | 199           |
| 3           | 299           |
| 4           | 580           |
| 5           | 1000          |
+-------------+---------------+
```

Result 表：

```
+--------------+--------------+
| bin          | total        |
+--------------+--------------+
| [0-5>        | 3            |
| [5-10>       | 1            |
| [10-15>      | 0            |
| 15 or more   | 1            |
+--------------+--------------+
```

对于 session_id 1，2 和 3 ，它们的访问时间大于等于 0 分钟且小于 5 分钟。

对于 session_id 4，它的访问时间大于等于 5 分钟且小于 10 分钟。

没有会话的访问时间大于等于 10 分钟且小于 15 分钟。

对于 session_id 5, 它的访问时间大于等于 15 分钟。

', 'examDataFiles/auto_upload_1564_1637126775643.sql', '2021-11-17 13:26:15', '2021-11-17 13:26:15', 'auto_upload_1564_1637126775643.sql', 1, 1);
INSERT INTO sqlexam.main_questions (id, teacher_id, title, `desc`, db_path, created_at, updated_at, file_name, total_difficulty, sub_count) VALUES (1565, '0', '计算布尔表达式的值', '表 Variables:

```
+---------------+---------+
| Column Name   | Type    |
+---------------+---------+
| name          | varchar |
| value         | int     |
+---------------+---------+
```

name 是该表主键.

该表包含了存储的变量及其对应的值.

表 Expressions:

```
+---------------+---------+
| Column Name   | Type    |
+---------------+---------+
| left_operand  | varchar |
| operator      | enum    |
| right_operand | varchar |
+---------------+---------+
```

(left_operand, operator, right_operand) 是该表主键.

该表包含了需要计算的布尔表达式.

operator 是枚举类型, 取值于(''<'', ''>'', ''='')

left_operand 和 right_operand 的值保证存在于 Variables 表单中.

写一个 SQL 查询, 以计算表 Expressions中的布尔表达式.

返回的结果表没有顺序要求.

查询结果格式如下例所示.

Variables 表:

```
+------+-------+
| name | value |
+------+-------+
| x    | 66    |
| y    | 77    |
+------+-------+
```

Expressions 表:

```
+--------------+----------+---------------+
| left_operand | operator | right_operand |
+--------------+----------+---------------+
| x            | >        | y             |
| x            | <        | y             |
| x            | =        | y             |
| y            | >        | x             |
| y            | <        | x             |
| x            | =        | x             |
+--------------+----------+---------------+
```

Result 表:

```
+--------------+----------+---------------+-------+
| left_operand | operator | right_operand | value |
+--------------+----------+---------------+-------+
| x            | >        | y             | false |
| x            | <        | y             | true  |
| x            | =        | y             | false |
| y            | >        | x             | true  |
| y            | <        | x             | false |
| x            | =        | x             | true  |
+--------------+----------+---------------+-------+
```

如上所示, 你需要通过使用 Variables 表来找到 Expressions 表中的每一个布尔表达式的值.

', 'examDataFiles/auto_upload_1565_1637126775643.sql', '2021-11-17 13:26:15', '2021-11-17 13:26:15', 'auto_upload_1565_1637126775643.sql', 2, 1);
INSERT INTO sqlexam.main_questions (id, teacher_id, title, `desc`, db_path, created_at, updated_at, file_name, total_difficulty, sub_count) VALUES (1578, '0', '苹果和桔子', '表: Sales

```
+---------------+---------+
| Column Name   | Type    |
+---------------+---------+
| sale_date     | date    |
| fruit         | enum    | 
| sold_num      | int     | 
+---------------+---------+
```

(sale_date,fruit) 是该表主键.

该表包含了每一天中"苹果" 和 "桔子"的销售情况.

写一个 SQL查询,报告每一天苹果和桔子销售的数目的差异.

返回的结果表,按照格式为(''YYYY-MM-DD'') 的 sale_date 排序.

查询结果表如下例所示:

Sales 表:

```
+------------+------------+-------------+
| sale_date  | fruit      | sold_num    |
+------------+------------+-------------+
| 2020-05-01 | apples     | 10          |
| 2020-05-01 | oranges    | 8           |
| 2020-05-02 | apples     | 15          |
| 2020-05-02 | oranges    | 15          |
| 2020-05-03 | apples     | 20          |
| 2020-05-03 | oranges    | 0           |
| 2020-05-04 | apples     | 15          |
| 2020-05-04 | oranges    | 16          |
+------------+------------+-------------+
```

Result 表:

```
+------------+--------------+
| sale_date  | diff         |
+------------+--------------+
| 2020-05-01 | 2            |
| 2020-05-02 | 0            |
| 2020-05-03 | 20           |
| 2020-05-04 | -1           |
+------------+--------------+
```

在 2020-05-01, 卖了 10 个苹果 和 8 个桔子 (差异为 10 - 8 = 2).

在 2020-05-02, 卖了 15 个苹果 和 15 个桔子 (差异为 15 - 15 = 0).

在 2020-05-03, 卖了 20 个苹果 和 0 个桔子 (差异为 20 - 0 = 20).

在 2020-05-04, 卖了 15 个苹果 和 16 个桔子 (差异为 15 - 16 = -1).

', 'examDataFiles/auto_upload_1578_1637126775643.sql', '2021-11-17 13:26:15', '2021-11-17 13:26:15', 'auto_upload_1578_1637126775643.sql', 2, 1);
INSERT INTO sqlexam.main_questions (id, teacher_id, title, `desc`, db_path, created_at, updated_at, file_name, total_difficulty, sub_count) VALUES (1579, '0', '活跃用户', '表 Accounts:

```
+---------------+---------+
| Column Name   | Type    |
+---------------+---------+
| id            | int     |
| name          | varchar |
+---------------+---------+
```

id 是该表主键.

该表包含账户 id 和账户的用户名.

表 Logins:

```
+---------------+---------+
| Column Name   | Type    |
+---------------+---------+
| id            | int     |
| login_date    | date    |
+---------------+---------+
```

该表无主键, 可能包含重复项.

该表包含登录用户的账户 id 和登录日期. 用户也许一天内登录多次.

写一个 SQL 查询, 找到活跃用户的 id 和 name.

活跃用户是指那些至少连续5 天登录账户的用户.

返回的结果表按照 id 排序.

结果表格式如下例所示:

Accounts 表:

```
+----+----------+
| id | name     |
+----+----------+
| 1  | Winston  |
| 7  | Jonathan |
+----+----------+
```

Logins 表:

```
+----+------------+
| id | login_date |
+----+------------+
| 7  | 2020-05-30 |
| 1  | 2020-05-30 |
| 7  | 2020-05-31 |
| 7  | 2020-06-01 |
| 7  | 2020-06-02 |
| 7  | 2020-06-02 |
| 7  | 2020-06-03 |
| 1  | 2020-06-07 |
| 7  | 2020-06-10 |
+----+------------+
```

Result 表:

```
+----+----------+
| id | name     |
+----+----------+
| 7  | Jonathan |
+----+----------+
```

id = 1 的用户 Winston 仅仅在不同的 2 天内登录了 2 次, 所以, Winston 不是活跃用户.

id = 7 的用户 Jonathon 在不同的 6 天内登录了 7 次, , 6 天中有 5 天是连续的, 所以, Jonathan 是活跃用户.

进阶问题:

如果活跃用户是那些至少连续n天登录账户的用户,你能否写出通用的解决方案?

', 'examDataFiles/auto_upload_1579_1637126775643.sql', '2021-11-17 13:26:15', '2021-11-17 13:26:15', 'auto_upload_1579_1637126775643.sql', 2, 1);
INSERT INTO sqlexam.main_questions (id, teacher_id, title, `desc`, db_path, created_at, updated_at, file_name, total_difficulty, sub_count) VALUES (1607, '0', '矩形面积', '表: Points

```
+---------------+---------+
| Column Name   | Type    |
+---------------+---------+
| id            | int     |
| x_value       | int     |
| y_value       | int     |
+---------------+---------+
```

id 是该表主键

每个点都用二维坐标 (x_value, y_value) 表示

写一个 SQL 语句，报告由表中任意两点可以形成的所有 边与坐标轴平行 且 面积不为零 的矩形。

结果表中的每一行包含三列 (p1, p2, area)如下:

p1和p2是矩形两个对角的 id

矩形的面积由列area表示

请按照面积area 大小降序排列；如果面积相同的话, 则按照p1升序排序；若仍相同，则按 p2 升序排列。

查询结果如下例所示：

Points 表:

```
+----------+-------------+-------------+
| id       | x_value     | y_value     |
+----------+-------------+-------------+
| 1        | 2           | 7           |
| 2        | 4           | 8           |
| 3        | 2           | 10          |
+----------+-------------+-------------+
```

Result 表:

```
+----------+-------------+-------------+
| p1       | p2          | area        |
+----------+-------------+-------------+
| 2        | 3           | 4           |
| 1        | 2           | 2           |
+----------+-------------+-------------+
```

p1 = 2 且 p2 = 3 时, 面积等于 |4-2| * |8-10| = 4

p1 = 1 且 p2 = 2 时, 面积等于 ||2-4| * |7-8| = 2 

p1 = 1 且 p2 = 3 时, 是不可能为矩形的, 面积等于 0

', 'examDataFiles/auto_upload_1607_1637126775643.sql', '2021-11-17 13:26:15', '2021-11-17 13:26:15', 'auto_upload_1607_1637126775643.sql', 2, 1);
INSERT INTO sqlexam.main_questions (id, teacher_id, title, `desc`, db_path, created_at, updated_at, file_name, total_difficulty, sub_count) VALUES (1608, '0', '计算税后工资', 'Salaries 表：

```
+---------------+---------+
| Column Name   | Type    |
+---------------+---------+
| company_id    | int     |
| employee_id   | int     |
| employee_name | varchar |
| salary        | int     |
+---------------+---------+
```

(company_id, employee_id) 是这个表的主键

这个表包括员工的company id, id, name 和 salary 

写一条查询 SQL 来查找每个员工的税后工资

每个公司的税率计算依照以下规则

如果这个公司员工最高工资不到 1000 ，税率为 0%

如果这个公司员工最高工资在 1000 到 10000 之间，税率为 24%

如果这个公司员工最高工资大于 10000 ，税率为 49%

按任意顺序返回结果，税后工资结果取整

结果表格式如下例所示：

Salaries 表：

```
+------------+-------------+---------------+--------+
| company_id | employee_id | employee_name | salary |
+------------+-------------+---------------+--------+
| 1          | 1           | Tony          | 2000   |
| 1          | 2           | Pronub        | 21300  |
| 1          | 3           | Tyrrox        | 10800  |
| 2          | 1           | Pam           | 300    |
| 2          | 7           | Bassem        | 450    |
| 2          | 9           | Hermione      | 700    |
| 3          | 7           | Bocaben       | 100    |
| 3          | 2           | Ognjen        | 2200   |
| 3          | 13          | Nyancat       | 3300   |
| 3          | 15          | Morninngcat   | 7777   |
+------------+-------------+---------------+--------+
```

Result 表：

```
+------------+-------------+---------------+--------+
| company_id | employee_id | employee_name | salary |
+------------+-------------+---------------+--------+
| 1          | 1           | Tony          | 1020   |
| 1          | 2           | Pronub        | 10863  |
| 1          | 3           | Tyrrox        | 5508   |
| 2          | 1           | Pam           | 300    |
| 2          | 7           | Bassem        | 450    |
| 2          | 9           | Hermione      | 700    |
| 3          | 7           | Bocaben       | 76     |
| 3          | 2           | Ognjen        | 1672   |
| 3          | 13          | Nyancat       | 2508   |
| 3          | 15          | Morninngcat   | 5911   |
+------------+-------------+---------------+--------+
```

对于公司 1 ，最高工资是 21300 ，其每个员工的税率为 49%

对于公司 2 ，最高工资是 700 ，其每个员工税率为 0%

对于公司 3 ，最高工资是 7777 ，其每个员工税率是 24%

税后工资计算 = 工资 - ( 税率 / 100）*工资

对于上述案例，Morninngcat 的税后工资 = 7777 - 7777 * ( 24 / 100) = 7777 - 1866.48 = 5910.52 ，取整为 5911

', 'examDataFiles/auto_upload_1608_1637126775643.sql', '2021-11-17 13:26:15', '2021-11-17 13:26:15', 'auto_upload_1608_1637126775643.sql', 2, 1);
INSERT INTO sqlexam.main_questions (id, teacher_id, title, `desc`, db_path, created_at, updated_at, file_name, total_difficulty, sub_count) VALUES (1623, '0', '周内每天的销售情况', '表：Orders

```
+---------------+---------+
| Column Name   | Type    |
+---------------+---------+
| order_id      | int     |
| customer_id   | int     |
| order_date    | date    | 
| item_id       | varchar |
| quantity      | int     |
+---------------+---------+
```

(order_id, item_id) 是该表主键

该表包含了订单信息

order_date 是id为 item_id 的商品被id为 customer_id 的消费者订购的日期.

表：Items

```
+---------------------+---------+
| Column Name         | Type    |
+---------------------+---------+
| item_id             | varchar |
| item_name           | varchar |
| item_category       | varchar |
+---------------------+---------+
```

item_id 是该表主键

item_name 是商品的名字

item_category是商品的类别

你是企业主，想要获得分类商品和周内每天的销售报告。

写一个SQL语句，报告 周内每天 每个商品类别下订购了多少单位。

返回结果表单 按商品类别排序 。

查询结果格式如下例所示：

Orders 表：

```
+------------+--------------+-------------+--------------+-------------+
| order_id   | customer_id  | order_date  | item_id      | quantity    |
+------------+--------------+-------------+--------------+-------------+
| 1          | 1            | 2020-06-01  | 1            | 10          |
| 2          | 1            | 2020-06-08  | 2            | 10          |
| 3          | 2            | 2020-06-02  | 1            | 5           |
| 4          | 3            | 2020-06-03  | 3            | 5           |
| 5          | 4            | 2020-06-04  | 4            | 1           |
| 6          | 4            | 2020-06-05  | 5            | 5           |
| 7          | 5            | 2020-06-05  | 1            | 10          |
| 8          | 5            | 2020-06-14  | 4            | 5           |
| 9          | 5            | 2020-06-21  | 3            | 5           |
+------------+--------------+-------------+--------------+-------------+
```

Items 表：

```
+------------+----------------+---------------+
| item_id    | item_name      | item_category |
+------------+----------------+---------------+
| 1          | LC Alg. Book   | Book          |
| 2          | LC DB. Book    | Book          |
| 3          | LC SmarthPhone | Phone         |
| 4          | LC Phone 2020  | Phone         |
| 5          | LC SmartGlass  | Glasses       |
| 6          | LC T-Shirt XL  | T-Shirt       |
+------------+----------------+---------------+
```

Result 表：

```
+------------+-----------+-----------+-----------+-----------+-----------+-----------+-----------+
| Category   | Monday    | Tuesday   | Wednesday | Thursday  | Friday    | Saturday  | Sunday    |
+------------+-----------+-----------+-----------+-----------+-----------+-----------+-----------+
| Book       | 20        | 5         | 0         | 0         | 10        | 0         | 0         |
| Glasses    | 0         | 0         | 0         | 0         | 5         | 0         | 0         |
| Phone      | 0         | 0         | 5         | 1         | 0         | 0         | 10        |
| T-Shirt    | 0         | 0         | 0         | 0         | 0         | 0         | 0         |
+------------+-----------+-----------+-----------+-----------+-----------+-----------+-----------+
```

在周一(2020-06-01, 2020-06-08)，Book分类(ids: 1, 2)下，总共销售了20个单位(10 + 10)

在周二(2020-06-02)，Book分类(ids: 1, 2)下，总共销售了5个单位

在周三(2020-06-03)，Phone分类(ids: 3, 4)下，总共销售了5个单位

在周四(2020-06-04)，Phone分类(ids: 3, 4)下，总共销售了1个单位

在周五(2020-06-05)，Book分类(ids: 1, 2)下，总共销售了10个单位，Glasses分类(ids: 5)下，总共销售了5个单位

在周六, 没有商品销售

在周天(2020-06-14, 2020-06-21)，Phone分类(ids: 3, 4)下，总共销售了10个单位(5 + 5)

没有销售 T-Shirt 类别的商品

', 'examDataFiles/auto_upload_1623_1637126775643.sql', '2021-11-17 13:26:15', '2021-11-17 13:26:15', 'auto_upload_1623_1637126775643.sql', 3, 1);
INSERT INTO sqlexam.main_questions (id, teacher_id, title, `desc`, db_path, created_at, updated_at, file_name, total_difficulty, sub_count) VALUES (1625, '0', '按日期分组销售产品', '表Activities：

```
+-------------+---------+
| 列名         | 类型    |
+-------------+---------+
| sell_date   | date    |
| product     | varchar |
+-------------+---------+
```

此表没有主键，它可能包含重复项。

此表的每一行都包含产品名称和在市场上销售的日期。

编写一个 SQL 查询来查找每个日期、销售的不同产品的数量及其名称。

每个日期的销售产品名称应按词典序排列。

返回按sell_date 排序的结果表。

查询结果格式如下例所示。

Activities 表：

```
+------------+-------------+
| sell_date  | product     |
+------------+-------------+
| 2020-05-30 | Headphone   |
| 2020-06-01 | Pencil      |
| 2020-06-02 | Mask        |
| 2020-05-30 | Basketball  |
| 2020-06-01 | Bible       |
| 2020-06-02 | Mask        |
| 2020-05-30 | T-Shirt     |
+------------+-------------+
```

Result 表：

```
+------------+----------+------------------------------+
| sell_date  | num_sold | products                     |
+------------+----------+------------------------------+
| 2020-05-30 | 3        | Basketball,Headphone,T-shirt |
| 2020-06-01 | 2        | Bible,Pencil                 |
| 2020-06-02 | 1        | Mask                         |
+------------+----------+------------------------------+
```

对于2020-05-30，出售的物品是 (Headphone, Basketball, T-shirt)，按词典序排列，并用逗号 '','' 分隔。

对于2020-06-01，出售的物品是 (Pencil, Bible)，按词典序排列，并用逗号分隔。

对于2020-06-02，出售的物品是 (Mask)，只需返回该物品名。

', 'examDataFiles/auto_upload_1625_1637126775643.sql', '2021-11-17 13:26:15', '2021-11-17 13:26:15', 'auto_upload_1625_1637126775643.sql', 1, 1);
INSERT INTO sqlexam.main_questions (id, teacher_id, title, `desc`, db_path, created_at, updated_at, file_name, total_difficulty, sub_count) VALUES (1639, '0', '上月播放的儿童适宜电影', '表: TVProgram

```
+---------------+---------+
| Column Name   | Type    |
+---------------+---------+
| program_date  | date    |
| content_id    | int     |
| channel       | varchar |
+---------------+---------+
```

(program_date, content_id) 是该表主键.

该表包含电视上的节目信息.

content_id 是电视一些频道上的节目的 id.

表: Content

```
+------------------+---------+
| Column Name      | Type    |
+------------------+---------+
| content_id       | varchar |
| title            | varchar |
| Kids_content     | enum    |
| content_type     | varchar |
+------------------+---------+
```

content_id 是该表主键.

Kids_content 是枚举类型, 取值为(''Y'', ''N''), 其中: 

''Y'' 表示儿童适宜内容, 而''N''表示儿童不宜内容.

content_type表示内容的类型, 比如电影, 电视剧等.

写一个 SQL 语句,报告在 2020 年 6 月份播放的儿童适宜电影的去重电影名.

返回的结果表单没有顺序要求.

查询结果的格式如下例所示.

TVProgram 表:

```
+--------------------+--------------+-------------+
| program_date       | content_id   | channel     |
+--------------------+--------------+-------------+
| 2020-06-10 08:00   | 1            | LC-Channel  |
| 2020-05-11 12:00   | 2            | LC-Channel  |
| 2020-05-12 12:00   | 3            | LC-Channel  |
| 2020-05-13 14:00   | 4            | Disney Ch   |
| 2020-06-18 14:00   | 4            | Disney Ch   |
| 2020-07-15 16:00   | 5            | Disney Ch   |
+--------------------+--------------+-------------+
```

Content 表:

```
+------------+----------------+---------------+---------------+
| content_id | title          | Kids_content  | content_type  |
+------------+----------------+---------------+---------------+
| 1          | Leetcode Movie | N             | Movies        |
| 2          | Alg. for Kids  | Y             | Series        |
| 3          | Database Sols  | N             | Series        |
| 4          | Aladdin        | Y             | Movies        |
| 5          | Cinderella     | Y             | Movies        |
+------------+----------------+---------------+---------------+
```

Result 表:

```
+--------------+
| title        |
+--------------+
| Aladdin      |
+--------------+
```

"Leetcode Movie" 是儿童不宜的电影.

"Alg. for Kids" 不是电影.

"Database Sols" 不是电影

"Alladin" 是电影, 儿童适宜, 并且在 2020 年 6 月份播放.

"Cinderella" 不在 2020 年 6 月份播放.

', 'examDataFiles/auto_upload_1639_1637126775643.sql', '2021-11-17 13:26:15', '2021-11-17 13:26:15', 'auto_upload_1639_1637126775643.sql', 1, 1);
INSERT INTO sqlexam.main_questions (id, teacher_id, title, `desc`, db_path, created_at, updated_at, file_name, total_difficulty, sub_count) VALUES (1641, '0', '可以放心投资的国家', '表Person:

```
+----------------+---------+
| Column Name    | Type    |
+----------------+---------+
| id             | int     |
| name           | varchar |
| phone_number   | varchar |
+----------------+---------+
```

id 是该表主键.

该表每一行包含一个人的名字和电话号码.

电话号码的格式是:''xxx-yyyyyyy'', 其中xxx是国家码(3个字符), yyyyyyy是电话号码(7个字符), x和y都表示数字. 同时, 国家码和电话号码都可以包含前导0.

表Country:

```
+----------------+---------+
| Column Name    | Type    |
+----------------+---------+
| name           | varchar |
| country_code   | varchar |
+----------------+---------+
```

country_code是该表主键.

该表每一行包含国家名和国家码. country_code的格式是''xxx'', x是数字.

表Calls:

```
+-------------+------+
| Column Name | Type |
+-------------+------+
| caller_id   | int  |
| callee_id   | int  |
| duration    | int  |
+-------------+------+
```

该表无主键, 可能包含重复行.

每一行包含呼叫方id, 被呼叫方id和以分钟为单位的通话时长. caller_id != callee_id

一家电信公司想要投资新的国家. 该公司想要投资的国家是: 该国的平均通话时长要严格地大于全球平均通话时长.

写一段 SQL,找到所有该公司可以投资的国家.

返回的结果表没有顺序要求.

查询的结果格式如下例所示.

Person 表:

```
+----+----------+--------------+
| id | name     | phone_number |
+----+----------+--------------+
| 3  | Jonathan | 051-1234567  |
| 12 | Elvis    | 051-7654321  |
| 1  | Moncef   | 212-1234567  |
| 2  | Maroua   | 212-6523651  |
| 7  | Meir     | 972-1234567  |
| 9  | Rachel   | 972-0011100  |
+----+----------+--------------+
```

Country 表:

```
+----------+--------------+
| name     | country_code |
+----------+--------------+
| Peru     | 051          |
| Israel   | 972          |
| Morocco  | 212          |
| Germany  | 049          |
| Ethiopia | 251          |
+----------+--------------+
```

Calls 表:

```
+-----------+-----------+----------+
| caller_id | callee_id | duration |
+-----------+-----------+----------+
| 1         | 9         | 33       |
| 2         | 9         | 4        |
| 1         | 2         | 59       |
| 3         | 12        | 102      |
| 3         | 12        | 330      |
| 12        | 3         | 5        |
| 7         | 9         | 13       |
| 7         | 1         | 3        |
| 9         | 7         | 1        |
| 1         | 7         | 7        |
+-----------+-----------+----------+
```

Result 表:

```
+----------+
| country  |
+----------+
| Peru     |
+----------+
```

国家Peru的平均通话时长是 (102 + 102 + 330 + 330 + 5 + 5) / 6 = 145.666667

国家Israel的平均通话时长是 (33 + 4 + 13 + 13 + 3 + 1 + 1 + 7) / 8 = 9.37500

国家Morocco的平均通话时长是 (33 + 4 + 59 + 59 + 3 + 7) / 6 = 27.5000 

全球平均通话时长 = (2 * (33 + 4 + 59 + 102 + 330 + 5 + 13 + 3 + 1 + 7)) / 20 = 55.70000

所以, Peru是唯一的平均通话时长大于全球平均通话时长的国家, 也是唯一的推荐投资的国家.

', 'examDataFiles/auto_upload_1641_1637126775643.sql', '2021-11-17 13:26:15', '2021-11-17 13:26:15', 'auto_upload_1641_1637126775643.sql', 2, 1);
INSERT INTO sqlexam.main_questions (id, teacher_id, title, `desc`, db_path, created_at, updated_at, file_name, total_difficulty, sub_count) VALUES (1654, '0', '消费者下单频率', '表: Customers

```
+---------------+---------+
| Column Name   | Type    |
+---------------+---------+
| customer_id   | int     |
| name          | varchar |
| country       | varchar |
+---------------+---------+
```

customer_id 是该表主键.

该表包含公司消费者的信息.

表: Product

```
+---------------+---------+
| Column Name   | Type    |
+---------------+---------+
| product_id    | int     |
| description   | varchar |
| price         | int     |
+---------------+---------+
```

product_id 是该表主键.

该表包含公司产品的信息.

price 是本产品的花销.

表: Orders

```
+---------------+---------+
| Column Name   | Type    |
+---------------+---------+
| order_id      | int     |
| customer_id   | int     |
| product_id    | int     |
| order_date    | date    |
| quantity      | int     |
+---------------+---------+
```

order_id 是该表主键.

该表包含消费者下单的信息.

customer_id 是买了数量为"quantity", id为"product_id"产品的消费者的 id.

Order_date 是订单发货的日期, 格式为(''YYYY-MM-DD'').

写一个 SQL 语句, 报告消费者的 id 和名字,其中消费者在 2020 年 6 月和 7 月,每月至少花费了$100.

结果表无顺序要求.

查询结果格式如下例所示.

Customers

```
+--------------+-----------+-------------+
| customer_id  | name     | country   |
+--------------+-----------+-------------+
| 1           | Winston  | USA        |
| 2           | Jonathan  | Peru       |
| 3           | Moustafa | Egypt      |
+--------------+-----------+-------------+
```

Product

```
+--------------+-------------+-------------+
| product_id   | description | price     |
+--------------+-------------+-------------+
| 10          | LC Phone   | 300        |
| 20          | LC T-Shirt  | 10         |
| 30          | LC Book    | 45         |
| 40           | LC Keychain| 2          |
+--------------+-------------+-------------+
```

Orders

```
+--------------+-------------+-------------+-------------+-----------+
| order_id     | customer_id | product_id  | order_date  | quantity  |
+--------------+-------------+-------------+-------------+-----------+
| 1           | 1          | 10         | 2020-06-10  | 1         |
| 2           | 1           | 20         | 2020-07-01  | 1         |
| 3           | 1           | 30         | 2020-07-08  | 2         |
| 4           | 2          | 10         | 2020-06-15  | 2         |
| 5           | 2           | 40         | 2020-07-01  | 10        |
| 6           | 3           | 20         | 2020-06-24  | 2         |
| 7           | 3          | 30         | 2020-06-25  | 2         |
| 9           | 3           | 30         | 2020-05-08  | 3         |
+--------------+-------------+-------------+-------------+-----------+
```

Result 表:

```
+--------------+------------+
| customer_id  | name       |  
+--------------+------------+
| 1            | Winston    |
+--------------+------------+ 
```

Winston 在2020年6月花费了$300(300 * 1), 在7月花费了$100(10 * 1 + 45 * 2).

Jonathan 在2020年6月花费了$600(300 * 2), 在7月花费了$20(2 * 10).

Moustafa 在2020年6月花费了$110 (10 * 2 + 45 * 2), 在7月花费了$0.

', 'examDataFiles/auto_upload_1654_1637126775643.sql', '2021-11-17 13:26:15', '2021-11-17 13:26:15', 'auto_upload_1654_1637126775643.sql', 1, 1);
INSERT INTO sqlexam.main_questions (id, teacher_id, title, `desc`, db_path, created_at, updated_at, file_name, total_difficulty, sub_count) VALUES (1664, '0', '查找拥有有效邮箱的用户', '用户表：Users

```
+---------------+---------+
| Column Name   | Type    |
+---------------+---------+
| user_id       | int     |
| name          | varchar |
| mail          | varchar | 
+---------------+---------+
```

user_id （用户 ID）是该表的主键。

这个表包含用户在某网站上注册的信息。有些邮箱是无效的。

写一条SQL 语句，查询拥有有效邮箱的用户。

有效的邮箱包含符合下列条件的前缀名和域名：

前缀名是包含字母（大写或小写）、数字、下划线''_''、句点''.''和/或横杠''-''的字符串。前缀名必须以字母开头。

域名是''@leetcode.com''。

按任意顺序返回结果表。

查询格式如下所示：

Users

```
+---------+-----------+-------------------------+
| user_id | name      | mail                    |
+---------+-----------+-------------------------+
| 1       | Winston   | winston@leetcode.com    |
| 2       | Jonathan  | jonathanisgreat         |
| 3       | Annabelle | bella-@leetcode.com     |
| 4       | Sally     | sally.come@leetcode.com |
| 5       | Marwan    | quarz#2020@leetcode.com |
| 6       | David     | david69@gmail.com       |
| 7       | Shapiro   | .shapo@leetcode.com     |
+---------+-----------+-------------------------+
```

结果表：

```
+---------+-----------+-------------------------+
| user_id | name      | mail                    |
+---------+-----------+-------------------------+
| 1       | Winston   | winston@leetcode.com    |
| 3       | Annabelle | bella-@leetcode.com     |
| 4       | Sally     | sally.come@leetcode.com |
+---------+-----------+-------------------------+
```

2 号用户的邮箱没有域名。

5 号用户的邮箱包含非法字符 #。

6 号用户的邮箱的域名不是 leetcode。

7 号用户的邮箱以句点（.）开头。

', 'examDataFiles/auto_upload_1664_1637126775643.sql', '2021-11-17 13:26:15', '2021-11-17 13:26:15', 'auto_upload_1664_1637126775643.sql', 1, 1);
INSERT INTO sqlexam.main_questions (id, teacher_id, title, `desc`, db_path, created_at, updated_at, file_name, total_difficulty, sub_count) VALUES (1670, '0', '患某种疾病的患者', '患者信息表：Patients

```
+--------------+---------+
| Column Name  | Type    |
+--------------+---------+
| patient_id   | int     |
| patient_name | varchar |
| conditions   | varchar |
+--------------+---------+
```

patient_id （患者 ID）是该表的主键。

''conditions'' （疾病）包含 0 个或以上的疾病代码，以空格分隔。

这个表包含医院中患者的信息。

写一条SQL 语句，查询患有 I 类糖尿病的患者ID （patient_id）、患者姓名（patient_name）以及其患有的所有疾病代码（conditions）。I 类糖尿病的代码总是包含前缀DIAB1。

按任意顺序返回结果表。

查询结果格式如下示例所示：

Patients

```
+------------+--------------+--------------+
| patient_id | patient_name | conditions   |
+------------+--------------+--------------+
| 1          | Daniel      | YFEV COUGH   |
| 2         | Alice        |             |
| 3         | Bob         | DIAB100 MYOP|
| 4         | George      | ACNE DIAB100|
| 5         | Alain       | DIAB201     |
+------------+--------------+--------------+
```

结果表：

```
+------------+--------------+--------------+
| patient_id | patient_name | conditions   |
+------------+--------------+--------------+
| 3         | Bob         | DIAB100 MYOP|
| 4         | George      | ACNE DIAB100| 
+------------+--------------+--------------+
```

Bob 和 George 都患有代码以 DIAB1 开头的疾病。

', 'examDataFiles/auto_upload_1670_1637126775643.sql', '2021-11-17 13:26:15', '2021-11-17 13:26:15', 'auto_upload_1670_1637126775643.sql', 1, 1);
INSERT INTO sqlexam.main_questions (id, teacher_id, title, `desc`, db_path, created_at, updated_at, file_name, total_difficulty, sub_count) VALUES (1671, '0', '最近的三笔订单', '表：Customers

```
+---------------+---------+
| Column Name   | Type    |
+---------------+---------+
| customer_id   | int     |
| name          | varchar |
+---------------+---------+
```

customer_id 是该表主键

该表包含消费者的信息

表：Orders

```
+---------------+---------+
| Column Name   | Type    |
+---------------+---------+
| order_id      | int     |
| order_date    | date    |
| customer_id   | int     |
| cost          | int     |
+---------------+---------+
```

order_id 是该表主键

该表包含id为customer_id的消费者的订单信息

每一个消费者 每天一笔订单

写一个 SQL 语句，找到每个用户的最近三笔订单。如果用户的订单少于 3 笔，则返回他的全部订单。

返回的结果按照 customer_name升序排列。如果排名有相同，则继续按照 customer_id 升序排列。如果排名还有相同，则继续按照 order_date 降序排列。

查询结果格式如下例所示：

Customers

```
+-------------+-----------+
| customer_id | name      |
+-------------+-----------+
| 1           | Winston   |
| 2           | Jonathan  |
| 3           | Annabelle |
| 4           | Marwan    |
| 5           | Khaled    |
+-------------+-----------+
```

Orders

```
+----------+------------+-------------+------+
| order_id | order_date | customer_id | cost |
+----------+------------+-------------+------+
| 1        | 2020-07-31 | 1           | 30   |
| 2        | 2020-07-30 | 2           | 40   |
| 3        | 2020-07-31 | 3           | 70   |
| 4        | 2020-07-29 | 4           | 100  |
| 5        | 2020-06-10 | 1           | 1010 |
| 6        | 2020-08-01 | 2           | 102  |
| 7        | 2020-08-01 | 3           | 111  |
| 8        | 2020-08-03 | 1           | 99   |
| 9        | 2020-08-07 | 2           | 32   |
| 10       | 2020-07-15 | 1           | 2    |
+----------+------------+-------------+------+
```

Result table：

```
+---------------+-------------+----------+------------+
| customer_name | customer_id | order_id | order_date |
+---------------+-------------+----------+------------+
| Annabelle     | 3           | 7        | 2020-08-01 |
| Annabelle     | 3           | 3        | 2020-07-31 |
| Jonathan      | 2           | 9        | 2020-08-07 |
| Jonathan      | 2           | 6        | 2020-08-01 |
| Jonathan      | 2           | 2        | 2020-07-30 |
| Marwan        | 4           | 4        | 2020-07-29 |
| Winston       | 1           | 8        | 2020-08-03 |
| Winston       | 1           | 1        | 2020-07-31 |
| Winston       | 1           | 10       | 2020-07-15 |
+---------------+-------------+----------+------------+
```

Winston 有 4 笔订单, 排除了 "2020-06-10" 的订单, 因为它是最老的订单。

Annabelle 只有 2 笔订单, 全部返回。

Jonathan 恰好有 3 笔订单。

Marwan 只有 1 笔订单。

结果表我们按照 customer_name 升序排列，customer_id 升序排列，order_date 降序排列。

进阶：

你能写出来最近n笔订单的通用解决方案吗?

', 'examDataFiles/auto_upload_1671_1637126775643.sql', '2021-11-17 13:26:15', '2021-11-17 13:26:15', 'auto_upload_1671_1637126775643.sql', 2, 1);
INSERT INTO sqlexam.main_questions (id, teacher_id, title, `desc`, db_path, created_at, updated_at, file_name, total_difficulty, sub_count) VALUES (1686, '0', '产品名称格式修复', '表：Sales

```
+--------------+---------+
| Column Name  | Type    |
+--------------+---------+
| sale_id      | int     |
| product_name | varchar |
| sale_date    | date    |
+--------------+---------+
```

sale_id 是该表主键

该表的每一行包含了产品的名称及其销售日期

因为在 2000 年该表是手工填写的，product_name可能包含前后空格，而且包含大小写。

写一个 SQL 语句报告每个月的销售情况：

product_name是小写字母且不包含前后空格

sale_date格式为(''YYYY-MM'')

total是产品在本月销售的次数

返回结果以product_name升序 排列，如果有排名相同，再以sale_date 升序 排列。

查询结果格式如下所示：

Sales 表：

```
+------------+------------------+--------------+
| sale_id    | product_name     | sale_date    |
+------------+------------------+--------------+
| 1          |      LCPHONE     | 2000-01-16   |
| 2         |    LCPhone       | 2000-01-17   |
| 3         |     LcPhOnE     | 2000-02-18   |
| 4         |      LCKeyCHAiN  | 2000-02-19   |
| 5         |   LCKeyChain     | 2000-02-28   |
| 6         | Matryoshka      | 2000-03-31   | 
+------------+------------------+--------------+
```

Result 表：

```
+--------------+--------------+----------+
| product_name | sale_date    | total    |
+--------------+--------------+----------+
| lcphone     | 2000-01     | 2       |
| lckeychain   | 2000-02     | 2       | 
| lcphone      | 2000-02     | 1       | 
| matryoshka   | 2000-03     | 1       | 
+--------------+--------------+----------+
```

1 月份，卖了 2 个 LcPhones，请注意产品名称是小写的，中间可能包含空格

2 月份，卖了 2 个 LCKeychains 和 1 个 LCPhone

3 月份，卖了 1 个 matryoshka

', 'examDataFiles/auto_upload_1686_1637126775643.sql', '2021-11-17 13:26:15', '2021-11-17 13:26:15', 'auto_upload_1686_1637126775643.sql', 1, 1);
INSERT INTO sqlexam.main_questions (id, teacher_id, title, `desc`, db_path, created_at, updated_at, file_name, total_difficulty, sub_count) VALUES (1688, '0', '每件商品的最新订单', '表: Customers

```
+---------------+---------+
| Column Name   | Type    |
+---------------+---------+
| customer_id   | int     |
| name          | varchar |
+---------------+---------+
```

customer_id 是该表主键.

该表包含消费者的信息.

表: Orders

```
+---------------+---------+
| Column Name   | Type    |
+---------------+---------+
| order_id      | int     |
| order_date    | date    |
| customer_id   | int     |
| product_id    | int     |
+---------------+---------+
```

order_id 是该表主键.

该表包含消费者customer_id产生的订单.

不会有商品被相同的用户在一天内下单超过一次.

表: Products

```
+---------------+---------+
| Column Name   | Type    |
+---------------+---------+
| product_id    | int     |
| product_name  | varchar |
| price         | int     |
+---------------+---------+
```

product_id 是该表主键.

该表包含所有商品的信息.

写一个SQL 语句,找到每件商品的最新订单(可能有多个).

返回的结果以product_name 升序排列,如果有排序相同,再以product_id 升序排列.如果还有排序相同,再以order_id 升序排列.

查询结果格式如下例所示:

Customers

```
+-------------+-----------+
| customer_id | name      |
+-------------+-----------+
| 1           | Winston   |
| 2           | Jonathan  |
| 3           | Annabelle |
| 4           | Marwan    |
| 5           | Khaled    |
+-------------+-----------+
```

Orders

```
+----------+------------+-------------+------------+
| order_id | order_date | customer_id | product_id |
+----------+------------+-------------+------------+
| 1        | 2020-07-31 | 1           | 1          |
| 2        | 2020-07-30 | 2           | 2          |
| 3        | 2020-08-29 | 3           | 3          |
| 4        | 2020-07-29 | 4           | 1          |
| 5        | 2020-06-10 | 1           | 2          |
| 6        | 2020-08-01 | 2           | 1          |
| 7        | 2020-08-01 | 3           | 1          |
| 8        | 2020-08-03 | 1           | 2          |
| 9        | 2020-08-07 | 2           | 3          |
| 10       | 2020-07-15 | 1           | 2          |
+----------+------------+-------------+------------+
```

Products

```
+------------+--------------+-------+
| product_id | product_name | price |
+------------+--------------+-------+
| 1          | keyboard     | 120   |
| 2          | mouse        | 80    |
| 3          | screen       | 600   |
| 4          | hard disk    | 450   |
+------------+--------------+-------+
```

Result

```
+--------------+------------+----------+------------+
| product_name | product_id | order_id | order_date |
+--------------+------------+----------+------------+
| keyboard     | 1          | 6        | 2020-08-01 |
| keyboard     | 1          | 7        | 2020-08-01 |
| mouse        | 2          | 8        | 2020-08-03 |
| screen       | 3          | 3        | 2020-08-29 |
+--------------+------------+----------+------------+
```

keyboard 的最新订单在2020-08-01, 在这天有两次下单.

mouse 的最新订单在2020-08-03, 在这天只有一次下单.

screen 的最新订单在2020-08-29, 在这天只有一次下单.

hard disk 没有被下单, 我们不把它包含在结果表中.

', 'examDataFiles/auto_upload_1688_1637126775643.sql', '2021-11-17 13:26:15', '2021-11-17 13:26:15', 'auto_upload_1688_1637126775643.sql', 2, 1);
INSERT INTO sqlexam.main_questions (id, teacher_id, title, `desc`, db_path, created_at, updated_at, file_name, total_difficulty, sub_count) VALUES (1702, '0', '银行账户概要', '用户表：Users

```
+--------------+---------+
| Column Name  | Type    |
+--------------+---------+
| user_id      | int     |
| user_name    | varchar |
| credit       | int     |
+--------------+---------+
```

user_id 是这个表的主键。

表中的每一列包含每一个用户当前的额度信息。

交易表：Transactions

```
+---------------+---------+
| Column Name   | Type    |
+---------------+---------+
| trans_id      | int     |
| paid_by       | int     |
| paid_to       | int     |
| amount        | int     |
| transacted_on | date    |
+---------------+---------+
```

trans_id 是这个表的主键。

表中的每一列包含银行的交易信息。

ID 为 paid_by 的用户给 ID 为 paid_to 的用户转账。

力扣银行 (LCB) 帮助程序员们完成虚拟支付。我们的银行在表Transaction中记录每条交易信息，我们要查询每个用户的当前余额，并检查他们是否已透支（当前额度小于 0）。

写一条 SQL 语句，查询：

user_id用户 ID

user_name用户名

credit完成交易后的余额

credit_limit_breached检查是否透支 （"Yes" 或"No"）

以任意顺序返回结果表。

查询格式见如下示例：

Users 表：

```
+------------+--------------+-------------+
| user_id    | user_name    | credit      |
+------------+--------------+-------------+
| 1          | Moustafa     | 100         |
| 2          | Jonathan     | 200         |
| 3          | Winston      | 10000       |
| 4          | Luis         | 800         | 
+------------+--------------+-------------+
```

Transactions 表：

```
+------------+------------+------------+----------+---------------+
| trans_id   | paid_by    | paid_to    | amount   | transacted_on |
+------------+------------+------------+----------+---------------+
| 1          | 1          | 3          | 400      | 2020-08-01    |
| 2          | 3          | 2          | 500      | 2020-08-02    |
| 3          | 2          | 1          | 200      | 2020-08-03    |
+------------+------------+------------+----------+---------------+
```

结果表：

```
+------------+------------+------------+-----------------------+
| user_id    | user_name  | credit     | credit_limit_breached |
+------------+------------+------------+-----------------------+
| 1          | Moustafa   | -100       | Yes                   | 
| 2          | Jonathan   | 500        | No                    |
| 3          | Winston    | 9900       | No                    |
| 4          | Luis       | 800        | No                    |
+------------+------------+------------+-----------------------+
```

Moustafa 在 "2020-08-01" 支付了 $400 并在 "2020-08-03" 收到了 $200 ，当前额度 (100 -400 +200) = -$100

Jonathan 在 "2020-08-02" 收到了 $500 并在 "2020-08-08" 支付了 $200 ，当前额度 (200 +500 -200) = $500

Winston 在 "2020-08-01" 收到了 $400 并在 "2020-08-03" 支付了 $500 ，当前额度 (10000 +400 -500) = $9900

Luis 未收到任何转账信息，额度 = $800

', 'examDataFiles/auto_upload_1702_1637126775643.sql', '2021-11-17 13:26:15', '2021-11-17 13:26:15', 'auto_upload_1702_1637126775643.sql', 2, 1);
INSERT INTO sqlexam.main_questions (id, teacher_id, title, `desc`, db_path, created_at, updated_at, file_name, total_difficulty, sub_count) VALUES (1712, '0', '按月统计订单数与顾客数', '表：Orders

```
+---------------+---------+
| Column Name   | Type    |
+---------------+---------+
| order_id      | int     |
| order_date    | date    |
| customer_id   | int     |
| invoice       | int     |
+---------------+---------+
```

order_id 是 Orders 表的主键。

这张表包含顾客(customer_id)所下订单的信息。

写一个查询语句来 按月 统计 金额大于 $20 的唯一 订单数 和唯一 顾客数 。

查询结果无排序要求。

查询结果格式如下面例子所示：

Orders

```
+----------+------------+-------------+------------+
| order_id | order_date | customer_id | invoice    |
+----------+------------+-------------+------------+
| 1        | 2020-09-15 | 1           | 30         |
| 2        | 2020-09-17 | 2           | 90         |
| 3        | 2020-10-06 | 3           | 20         |
| 4        | 2020-10-20 | 3           | 21         |
| 5        | 2020-11-10 | 1           | 10         |
| 6        | 2020-11-21 | 2           | 15         |
| 7        | 2020-12-01 | 4           | 55         |
| 8        | 2020-12-03 | 4           | 77         |
| 9        | 2021-01-07 | 3           | 31         |
| 10       | 2021-01-15 | 2           | 20         |
+----------+------------+-------------+------------+
```

Result 表：

```
+---------+-------------+----------------+
| month   | order_count | customer_count |
+---------+-------------+----------------+
| 2020-09 | 2           | 2              |
| 2020-10 | 1           | 1              |
| 2020-12 | 2           | 1              |
| 2021-01 | 1           | 1              |
+---------+-------------+----------------+
```

在 2020 年 09 月，有 2 份来自 2 位不同顾客的金额大于 $20 的订单。

在 2020 年 10 月，有 2 份来自 1 位顾客的订单，并且只有其中的 1 份订单金额大于 $20 。

在 2020 年 11 月，有 2 份来自 2 位不同顾客的订单，但由于金额都小于 $20 ，所以我们的查询结果中不包含这个月的数据。

在 2020 年 12 月，有 2 份来自 1 位顾客的订单，且 2 份订单金额都大于 $20 。

在 2021 年 01 月，有 2 份来自 2 位不同顾客的订单，但只有其中一份订单金额大于 $20 。

', 'examDataFiles/auto_upload_1712_1637126775643.sql', '2021-11-17 13:26:15', '2021-11-17 13:26:15', 'auto_upload_1712_1637126775643.sql', 1, 1);
INSERT INTO sqlexam.main_questions (id, teacher_id, title, `desc`, db_path, created_at, updated_at, file_name, total_difficulty, sub_count) VALUES (1718, '0', '仓库经理', '表:Warehouse

```
+--------------+---------+
| Column Name  | Type    |
+--------------+---------+
| name         | varchar |
| product_id   | int     |
| units        | int     |
+--------------+---------+
```

(name, product_id) 是该表主键.

该表的行包含了每个仓库的所有商品信息.

表: Products

```
+---------------+---------+
| Column Name   | Type    |
+---------------+---------+
| product_id    | int     |
| product_name  | varchar |
| Width         | int     |
| Length        | int     |
| Height        | int     |
+---------------+---------+
```

product_id 是该表主键.

该表的行包含了每件商品以英尺为单位的尺寸(宽度, 长度和高度)信息.

写一个 SQL查询来报告,每个仓库的存货量是多少立方英尺.

仓库名

存货量

返回结果没有顺序要求.

查询结果如下例所示.

Warehouse 表:

```
+------------+--------------+-------------+
| name       | product_id   | units       |
+------------+--------------+-------------+
| LCHouse1   | 1            | 1           |
| LCHouse1   | 2            | 10          |
| LCHouse1   | 3            | 5           |
| LCHouse2   | 1            | 2           |
| LCHouse2   | 2            | 2           |
| LCHouse3   | 4            | 1           |
+------------+--------------+-------------+
```

Products 表:

```
+------------+--------------+------------+----------+-----------+
| product_id | product_name | Width      | Length   | Height    |
+------------+--------------+------------+----------+-----------+
| 1          | LC-TV        | 5          | 50       | 40        |
| 2          | LC-KeyChain  | 5          | 5        | 5         |
| 3          | LC-Phone     | 2          | 10       | 10        |
| 4          | LC-T-Shirt   | 4          | 10       | 20        |
+------------+--------------+------------+----------+-----------+
```

Result 表:

```
+----------------+------------+
| WAREHOUSE_NAME | VOLUME     | 
+----------------+------------+
| LCHouse1       | 12250      | 
| LCHouse2       | 20250      |
| LCHouse3       | 800        |
+----------------+------------+
```

Id为1的商品(LC-TV)的存货量为 5x50x40 = 10000

Id为2的商品(LC-KeyChain)的存货量为 5x5x5 = 125 

Id为3的商品(LC-Phone)的存货量为 2x10x10 = 200

Id为4的商品(LC-T-Shirt)的存货量为 4x10x20 = 800

仓库LCHouse1: 1个单位的LC-TV + 10个单位的LC-KeyChain + 5个单位的LC-Phone.

         总存货量为: 1*10000 + 10*125  + 5*200 = 12250 立方英尺

仓库LCHouse2: 2个单位的LC-TV + 2个单位的LC-KeyChain.

         总存货量为: 2*10000 + 2*125 = 20250 立方英尺

仓库LCHouse3: 1个单位的LC-T-Shirt.

          总存货量为: 1*800 = 800 立方英尺.

', 'examDataFiles/auto_upload_1718_1637126775643.sql', '2021-11-17 13:26:15', '2021-11-17 13:26:15', 'auto_upload_1718_1637126775643.sql', 1, 1);
INSERT INTO sqlexam.main_questions (id, teacher_id, title, `desc`, db_path, created_at, updated_at, file_name, total_difficulty, sub_count) VALUES (1724, '0', '进店却未进行过交易的顾客', '表：Visits

```
+-------------+---------+
| Column Name | Type    |
+-------------+---------+
| visit_id    | int     |
| customer_id | int     |
+-------------+---------+
```

visit_id 是该表的主键。

该表包含有关光临过购物中心的顾客的信息。

表：Transactions

```
+----------------+---------+
| Column Name    | Type    |
+----------------+---------+
| transaction_id | int     |
| visit_id       | int     |
| amount         | int     |
+----------------+---------+
```

transaction_id 是此表的主键。

此表包含 visit_id 期间进行的交易的信息。

有一些顾客可能光顾了购物中心但没有进行交易。请你编写一个 SQL 查询，来查找这些顾客的 ID ，以及他们只光顾不交易的次数。

返回以任何顺序排序的结果表。

查询结果格式如下例所示：

Visits

```
+----------+-------------+
| visit_id | customer_id |
+----------+-------------+
| 1        | 23          |
| 2        | 9           |
| 4        | 30          |
| 5        | 54          |
| 6        | 96          |
| 7        | 54          |
| 8        | 54          |
+----------+-------------+
```

Transactions

```
+----------------+----------+--------+
| transaction_id | visit_id | amount |
+----------------+----------+--------+
| 2              | 5        | 310    |
| 3              | 5        | 300    |
| 9              | 5        | 200    |
| 12             | 1        | 910    |
| 13             | 2        | 970    |
+----------------+----------+--------+
```

Result 表：

```
+-------------+----------------+
| customer_id | count_no_trans |
+-------------+----------------+
| 54          | 2              |
| 30          | 1              |
| 96          | 1              |
+-------------+----------------+
```

ID = 23 的顾客曾经逛过一次购物中心，并在 ID = 12 的访问期间进行了一笔交易。

ID = 9 的顾客曾经逛过一次购物中心，并在 ID = 13 的访问期间进行了一笔交易。

ID = 30 的顾客曾经去过购物中心，并且没有进行任何交易。

ID = 54 的顾客三度造访了购物中心。在 2 次访问中，他们没有进行任何交易，在 1 次访问中，他们进行了 3 次交易。

ID = 96 的顾客曾经去过购物中心，并且没有进行任何交易。

如我们所见，ID 为 30 和 96 的顾客一次没有进行任何交易就去了购物中心。顾客 54 也两次访问了购物中心并且没有进行任何交易。

', 'examDataFiles/auto_upload_1724_1637126775643.sql', '2021-11-17 13:26:15', '2021-11-17 13:26:15', 'auto_upload_1724_1637126775643.sql', 1, 1);
INSERT INTO sqlexam.main_questions (id, teacher_id, title, `desc`, db_path, created_at, updated_at, file_name, total_difficulty, sub_count) VALUES (1734, '0', '银行账户概要 II', '表: Users

```
+--------------+---------+
| Column Name  | Type    |
+--------------+---------+
| account      | int     |
| name         | varchar |
+--------------+---------+
```

account 是该表的主键.

表中的每一行包含银行里中每一个用户的账号.

表: Transactions

```
+---------------+---------+
| Column Name   | Type    |
+---------------+---------+
| trans_id      | int     |
| account       | int     |
| amount        | int     |
| transacted_on | date    |
+---------------+---------+
```

trans_id 是该表主键.

该表的每一行包含了所有账户的交易改变情况.

如果用户收到了钱, 那么金额是正的; 如果用户转了钱, 那么金额是负的.

所有账户的起始余额为 0.

写一个 SQL,报告余额高于 10000 的所有用户的名字和余额.账户的余额等于包含该账户的所有交易的总和.

返回结果表单没有顺序要求.

查询结果格式如下例所示.

Users table:

```
+------------+--------------+
| account    | name         |
+------------+--------------+
| 900001     | Alice        |
| 900002     | Bob          |
| 900003     | Charlie      |
+------------+--------------+
```

Transactions table:

```
+------------+------------+------------+---------------+
| trans_id   | account    | amount     | transacted_on |
+------------+------------+------------+---------------+
| 1          | 900001     | 7000       |  2020-08-01   |
| 2          | 900001     | 7000       |  2020-09-01   |
| 3          | 900001     | -3000      |  2020-09-02   |
| 4          | 900002     | 1000       |  2020-09-12   |
| 5          | 900003     | 6000       |  2020-08-07   |
| 6          | 900003     | 6000       |  2020-09-07   |
| 7          | 900003     | -4000      |  2020-09-11   |
+------------+------------+------------+---------------+
```

Result table:

```
+------------+------------+
| name       | balance    |
+------------+------------+
| Alice      | 11000      |
+------------+------------+
```

Alice 的余额为(7000 + 7000 - 3000) = 11000.

Bob 的余额为1000.

Charlie 的余额为(6000 + 6000 - 4000) = 8000.

', 'examDataFiles/auto_upload_1734_1637126775643.sql', '2021-11-17 13:26:15', '2021-11-17 13:26:15', 'auto_upload_1734_1637126775643.sql', 1, 1);
INSERT INTO sqlexam.main_questions (id, teacher_id, title, `desc`, db_path, created_at, updated_at, file_name, total_difficulty, sub_count) VALUES (1735, '0', '每位顾客最经常订购的商品', '表：Customers

```
+---------------+---------+
| Column Name   | Type    |
+---------------+---------+
| customer_id   | int     |
| name          | varchar |
+---------------+---------+
```

customer_id 是该表主键

该表包含所有顾客的信息

表：Orders

```
+---------------+---------+
| Column Name   | Type    |
+---------------+---------+
| order_id      | int     |
| order_date    | date    |
| customer_id   | int     |
| product_id    | int     |
+---------------+---------+
```

order_id 是该表主键

该表包含顾客 customer_id 的订单信息

没有顾客会在一天内订购相同的商品 多于一次

表：Products

```
+---------------+---------+
| Column Name   | Type    |
+---------------+---------+
| product_id    | int     |
| product_name  | varchar |
| price         | int     |
+---------------+---------+
```

product_id 是该表主键

该表包含了所有商品的信息

写一个 SQL 语句，找到每一个顾客最经常订购的商品。

结果表单应该有每一位至少下过一次单的顾客 customer_id,他最经常订购的商品的product_id和product_name。

返回结果 没有顺序要求。

查询结果格式如下例所示：

Customers

```
+-------------+-------+
| customer_id | name  |
+-------------+-------+
| 1           | Alice |
| 2           | Bob   |
| 3           | Tom   |
| 4           | Jerry |
| 5           | John  |
+-------------+-------+
```

Orders

```
+----------+------------+-------------+------------+
| order_id | order_date | customer_id | product_id |
+----------+------------+-------------+------------+
| 1        | 2020-07-31 | 1           | 1          |
| 2        | 2020-07-30 | 2           | 2          |
| 3        | 2020-08-29 | 3           | 3          |
| 4        | 2020-07-29 | 4           | 1          |
| 5        | 2020-06-10 | 1           | 2          |
| 6        | 2020-08-01 | 2           | 1          |
| 7        | 2020-08-01 | 3           | 3          |
| 8        | 2020-08-03 | 1           | 2          |
| 9        | 2020-08-07 | 2           | 3          |
| 10       | 2020-07-15 | 1           | 2          |
+----------+------------+-------------+------------+
```

Products

```
+------------+--------------+-------+
| product_id | product_name | price |
+------------+--------------+-------+
| 1          | keyboard     | 120   |
| 2          | mouse        | 80    |
| 3          | screen       | 600   |
| 4          | hard disk    | 450   |
+------------+--------------+-------+
```

Result 表：

```
+-------------+------------+--------------+
| customer_id | product_id | product_name |
+-------------+------------+--------------+
| 1           | 2          | mouse        |
| 2           | 1          | keyboard     |
| 2           | 2          | mouse        |
| 2           | 3          | screen       |
| 3           | 3          | screen       |
| 4           | 1          | keyboard     |
+-------------+------------+--------------+
```

Alice (customer 1) 三次订购鼠标, 一次订购键盘, 所以鼠标是 Alice 最经常订购的商品.

Bob (customer 2) 一次订购键盘, 一次订购鼠标, 一次订购显示器, 所以这些都是 Bob 最经常订购的商品.

Tom (customer 3) 只两次订购显示器, 所以显示器是 Tom 最经常订购的商品.

Jerry (customer 4) 只一次订购键盘, 所以键盘是 Jerry 最经常订购的商品.

John (customer 5) 没有订购过商品, 所以我们并没有把 John 包含在结果表中.

', 'examDataFiles/auto_upload_1735_1637126775643.sql', '2021-11-17 13:26:15', '2021-11-17 13:26:15', 'auto_upload_1735_1637126775643.sql', 2, 1);
INSERT INTO sqlexam.main_questions (id, teacher_id, title, `desc`, db_path, created_at, updated_at, file_name, total_difficulty, sub_count) VALUES (1749, '0', '没有卖出的卖家', '表: Customer

```
+---------------+---------+
| Column Name   | Type    |
+---------------+---------+
| customer_id   | int     |
| customer_name | varchar |
+---------------+---------+
```

customer_id 是该表主键.

该表的每行包含网上商城的每一位顾客的信息.

表: Orders

```
+---------------+---------+
| Column Name   | Type    |
+---------------+---------+
| order_id      | int     |
| sale_date     | date    |
| order_cost    | int     |
| customer_id   | int     |
| seller_id     | int     |
+---------------+---------+
```

order_id 是该表主键.

该表的每行包含网上商城的所有订单的信息.

sale_date 是顾客customer_id和卖家seller_id之间交易的日期.

表: Seller

```
+---------------+---------+
| Column Name   | Type    |
+---------------+---------+
| seller_id     | int     |
| seller_name   | varchar |
+---------------+---------+
```

seller_id 是该表主键.

该表的每行包含每一位卖家的信息.

写一个SQL语句,报告所有在2020年度没有任何卖出的卖家的名字.

返回结果按照seller_name升序排列.

查询结果格式如下例所示.

Customer 表:

```
+--------------+---------------+
| customer_id  | customer_name |
+--------------+---------------+
| 101          | Alice         |
| 102          | Bob           |
| 103          | Charlie       |
+--------------+---------------+
```

Orders 表:

```
+-------------+------------+--------------+-------------+-------------+
| order_id    | sale_date  | order_cost   | customer_id | seller_id   |
+-------------+------------+--------------+-------------+-------------+
| 1           | 2020-03-01 | 1500         | 101         | 1           |
| 2           | 2020-05-25 | 2400         | 102         | 2           |
| 3           | 2019-05-25 | 800          | 101         | 3           |
| 4           | 2020-09-13 | 1000         | 103         | 2           |
| 5           | 2019-02-11 | 700          | 101         | 2           |
+-------------+------------+--------------+-------------+-------------+
```

Seller 表:

```
+-------------+-------------+
| seller_id   | seller_name |
+-------------+-------------+
| 1           | Daniel      |
| 2           | Elizabeth   |
| 3           | Frank       |
+-------------+-------------+
```

Result 表:

```
+-------------+
| seller_name |
+-------------+
| Frank       |
+-------------+
```

Daniel在2020年3月卖出1次.

Elizabeth在2020年卖出2次, 在2019年卖出1次.

Frank在2019年卖出1次, 在2020年没有卖出.

', 'examDataFiles/auto_upload_1749_1637126775643.sql', '2021-11-17 13:26:15', '2021-11-17 13:26:15', 'auto_upload_1749_1637126775643.sql', 1, 1);
INSERT INTO sqlexam.main_questions (id, teacher_id, title, `desc`, db_path, created_at, updated_at, file_name, total_difficulty, sub_count) VALUES (1759, '0', '找到遗失的ID', '表: Customers

```
+---------------+---------+
| Column Name   | Type    |
+---------------+---------+
| customer_id   | int     |
| customer_name | varchar |
+---------------+---------+
```

customer_id 是该表主键.

该表第一行包含了顾客的名字和id.

写一个 SQL 语句,找到所有遗失的顾客id.遗失的顾客id是指那些不在Customers表中,值却处于1和表中最大customer_id之间的id.

注意:最大的customer_id值不会超过100.

返回结果按ids 升序排列

查询结果格式如下例所示.

Customers 表:

```
+-------------+---------------+
| customer_id | customer_name |
+-------------+---------------+
| 1           | Alice         |
| 4           | Bob           |
| 5           | Charlie       |
+-------------+---------------+
```

Result 表:

```
+-----+
| ids |
+-----+
| 2   |
| 3   |
+-----+
```

表中最大的customer_id是5, 所以在范围[1,5]内, ID2和3从表中遗失.

', 'examDataFiles/auto_upload_1759_1637126775643.sql', '2021-11-17 13:26:15', '2021-11-17 13:26:15', 'auto_upload_1759_1637126775643.sql', 2, 1);
INSERT INTO sqlexam.main_questions (id, teacher_id, title, `desc`, db_path, created_at, updated_at, file_name, total_difficulty, sub_count) VALUES (1763, '0', '三人国家代表队', '表: SchoolA

```
+---------------+---------+
| Column Name   | Type    |
+---------------+---------+
| student_id    | int     |
| student_name  | varchar |
+---------------+---------+
```

student_id 是表的主键

表中的每一行包含了学校A中每一个学生的名字和ID

所有student_name在表中都是独一无二的

表: SchoolB

```
+---------------+---------+
| Column Name   | Type    |
+---------------+---------+
| student_id    | int     |
| student_name  | varchar |
+---------------+---------+
```

student_id 是表的主键

表中的每一行包含了学校B中每一个学生的名字和ID

所有student_name在表中都是独一无二的

表: SchoolC

```
+---------------+---------+
| Column Name   | Type    |
+---------------+---------+
| student_id    | int     |
| student_name  | varchar |
+---------------+---------+
```

student_id 是表的主键

表中的每一行包含了学校C中每一个学生的名字和ID

所有student_name在表中都是独一无二的

有一个国家只有三所学校，这个国家的每一个学生只会注册一所学校。

这个国家正在参加一个竞赛，他们希望从这三所学校中各选出一个学生来组建一支三人的代表队。

例如：

member_A是从 SchoolA中选出的

member_B是从 SchoolB中选出的

member_C是从 SchoolC中选出的

被选中的学生具有不同的名字和ID（没有任何两个学生拥有相同的名字、没有任何两个学生拥有相同的ID）

使用上述条件，编写SQL查询语句来找到所有可能的三人国家代表队组合。

查询结果接受任何顺序。

查询结果格式样例：

SchoolA table:

```
+------------+--------------+
| student_id | student_name |
+------------+--------------+
| 1          | Alice        |
| 2          | Bob          |
+------------+--------------+
```

SchoolB table:

```
+------------+--------------+
| student_id | student_name |
+------------+--------------+
| 3          | Tom          |
+------------+--------------+
```

SchoolC table:

```
+------------+--------------+
| student_id | student_name |
+------------+--------------+
| 3          | Tom          |
| 2          | Jerry        |
| 10         | Alice        |
+------------+--------------+
```

预期结果:

```
+----------+----------+----------+
| member_A | member_B | member_C |
+----------+----------+----------+
| Alice    | Tom      | Jerry    |
| Bob      | Tom      | Alice    |
+----------+----------+----------+
```

让我们看看有哪些可能的组合：

- (Alice, Tom, Tom) --> 不适用，因为member_B（Tom）和member_C（Tom）有相同的名字和ID

- (Alice, Tom, Jerry) --> 可能的组合

- (Alice, Tom, Alice) --> 不适用，因为member_A和member_C有相同的名字

- (Bob, Tom, Tom) --> 不适用，因为member_B和member_C有相同的名字和ID

- (Bob, Tom, Jerry) --> 不适用，因为member_A和member_C有相同的ID

- (Bob, Tom, Alice) --> 可能的组合.

', 'examDataFiles/auto_upload_1763_1637126775643.sql', '2021-11-17 13:26:15', '2021-11-17 13:26:15', 'auto_upload_1763_1637126775643.sql', 1, 1);
INSERT INTO sqlexam.main_questions (id, teacher_id, title, `desc`, db_path, created_at, updated_at, file_name, total_difficulty, sub_count) VALUES (1773, '0', '各赛事的用户注册率', '用户表：Users

```
+-------------+---------+
| Column Name | Type    |
+-------------+---------+
| user_id     | int     |
| user_name   | varchar |
+-------------+---------+
```

user_id 是该表的主键。

该表中的每行包括用户 ID 和用户名。

注册表：Register

```
+-------------+---------+
| Column Name | Type    |
+-------------+---------+
| contest_id  | int     |
| user_id     | int     |
+-------------+---------+
```

(contest_id, user_id) 是该表的主键。

该表中的每行包含用户的 ID 和他们注册的赛事。

写一条 SQL 语句，查询各赛事的用户注册百分率，保留两位小数。

返回的结果表按percentage的降序排序，若相同则按contest_id的升序排序。

查询结果如下示例所示：

Users 表：

```
+---------+-----------+
| user_id | user_name |
+---------+-----------+
| 6       | Alice     |
| 2       | Bob       |
| 7       | Alex      |
+---------+-----------+
```

Register 表：

```
+------------+---------+
| contest_id | user_id |
+------------+---------+
| 215        | 6       |
| 209        | 2       |
| 208        | 2       |
| 210        | 6       |
| 208        | 6       |
| 209        | 7       |
| 209        | 6       |
| 215        | 7       |
| 208        | 7       |
| 210        | 2       |
| 207        | 2       |
| 210        | 7       |
+------------+---------+
```

结果表：

```
+------------+------------+
| contest_id | percentage |
+------------+------------+
| 208        | 100.0      |
| 209        | 100.0      |
| 210        | 100.0      |
| 215        | 66.67      |
| 207        | 33.33      |
+------------+------------+
```

所有用户都注册了 208、209 和 210 赛事，因此这些赛事的注册率为 100% ，我们按 contest_id 的降序排序加入结果表中。

Alice 和 Alex 注册了 215 赛事，注册率为 ((2/3) * 100) = 66.67%

Bob 注册了 207 赛事，注册率为 ((1/3) * 100) = 33.33%

', 'examDataFiles/auto_upload_1773_1637126775643.sql', '2021-11-17 13:26:15', '2021-11-17 13:26:15', 'auto_upload_1773_1637126775643.sql', 1, 1);
INSERT INTO sqlexam.main_questions (id, teacher_id, title, `desc`, db_path, created_at, updated_at, file_name, total_difficulty, sub_count) VALUES (1801, '0', '每台机器的进程平均运行时间', '表: Activity

```
+----------------+---------+
| Column Name    | Type    |
+----------------+---------+
| machine_id     | int     |
| process_id     | int     |
| activity_type  | enum    |
| timestamp      | float   |
+----------------+---------+
```

该表展示了一家工厂网站的用户活动.

(machine_id, process_id, activity_type) 是当前表的主键.

machine_id 是一台机器的ID号.

process_id 是运行在各机器上的进程ID号.

activity_type 是枚举类型 (''start'', ''end'').

timestamp 是浮点类型,代表当前时间(以秒为单位).

''start'' 代表该进程在这台机器上的开始运行时间戳 , ''end'' 代表该进程在这台机器上的终止运行时间戳.

同一台机器，同一个进程都有一对开始时间戳和结束时间戳，而且开始时间戳永远在结束时间戳前面.

现在有一个工厂网站由几台机器运行，每台机器上运行着相同数量的进程. 请写出一条SQL计算每台机器各自完成一个进程任务的平均耗时.

完成一个进程任务的时间指进程的''end'' 时间戳 减去''start'' 时间戳. 平均耗时通过计算每台机器上所有进程任务的总耗费时间除以机器上的总进程数量获得.

结果表必须包含machine_id（机器ID） 和对应的average time（平均耗时）别名processing_time, 且四舍五入保留3位小数.

具体参考例子如下:

Activity table:

```
+------------+------------+---------------+-----------+
| machine_id | process_id | activity_type | timestamp |
+------------+------------+---------------+-----------+
| 0          | 0          | start         | 0.712     |
| 0          | 0          | end           | 1.520     |
| 0          | 1          | start         | 3.140     |
| 0          | 1          | end           | 4.120     |
| 1          | 0          | start         | 0.550     |
| 1          | 0          | end           | 1.550     |
| 1          | 1          | start         | 0.430     |
| 1          | 1          | end           | 1.420     |
| 2          | 0          | start         | 4.100     |
| 2          | 0          | end           | 4.512     |
| 2          | 1          | start         | 2.500     |
| 2          | 1          | end           | 5.000     |
+------------+------------+---------------+-----------+
```

Result table:

```
+------------+-----------------+
| machine_id | processing_time |
+------------+-----------------+
| 0          | 0.894           |
| 1          | 0.995           |
| 2          | 1.456           |
+------------+-----------------+
```

一共有3台机器,每台机器运行着两个进程.

机器 0 的平均耗时: ((1.520 - 0.712) + (4.120 - 3.140)) / 2 = 0.894

机器 1 的平均耗时: ((1.550 - 0.550) + (1.420 - 0.430)) / 2 = 0.995

机器 2 的平均耗时: ((4.512 - 4.100) + (5.000 - 2.500)) / 2 = 1.456

', 'examDataFiles/auto_upload_1801_1637126775643.sql', '2021-11-17 13:26:15', '2021-11-17 13:26:15', 'auto_upload_1801_1637126775643.sql', 1, 1);
INSERT INTO sqlexam.main_questions (id, teacher_id, title, `desc`, db_path, created_at, updated_at, file_name, total_difficulty, sub_count) VALUES (1811, '0', '修复表中的名字', '表： Users

```
+----------------+---------+
| Column Name    | Type    |
+----------------+---------+
| user_id        | int     |
| name           | varchar |
+----------------+---------+
```

user_id 是该表的主键。

该表包含用户的 ID 和名字。名字仅由小写和大写字符组成。

编写一个 SQL 查询来修复名字，使得只有第一个字符是大写的，其余都是小写的。

返回按 user_id 排序的结果表。

查询结果格式示例如下：

Users table:

```
+---------+-------+
| user_id | name  |
+---------+-------+
| 1       | aLice |
| 2       | bOB   |
+---------+-------+
```

Result table:

```
+---------+-------+
| user_id | name  |
+---------+-------+
| 1       | Alice |
| 2       | Bob   |
+---------+-------+
```

', 'examDataFiles/auto_upload_1811_1637126775643.sql', '2021-11-17 13:26:15', '2021-11-17 13:26:15', 'auto_upload_1811_1637126775643.sql', 1, 1);
INSERT INTO sqlexam.main_questions (id, teacher_id, title, `desc`, db_path, created_at, updated_at, file_name, total_difficulty, sub_count) VALUES (1821, '0', '发票中的产品金额', 'Product 表：

```
+-------------+---------+
| Column Name | Type    |
+-------------+---------+
| product_id  | int     |
| name        | varchar |
+-------------+---------+
```

product_id 是这张表的主键

表中含有产品 id 、产品名称。产品名称都是小写的英文字母，产品名称都是唯一的

Invoice 表：

```
+-------------+------+
| Column Name | Type |
+-------------+------+
| invoice_id  | int  |
| product_id  | int  |
| rest        | int  |
| paid        | int  |
| canceled    | int  |
| refunded    | int  |
+-------------+------+
```

invoice_id 发票 id ，是这张表的主键

product_id 产品 id

rest 应缴款项

paid 已支付金额

canceled 已取消金额

refunded 已退款金额

要求写一个SQL查询，对于所有产品，返回每个产品的产品名称，以及全部发票累计的总应缴款项、总已支付金额、总已取消金额、总已退款金额。

查询结果按 product_name 排序

示例：

Product 表：

```
+------------+-------+
| product_id | name  |
+------------+-------+
| 0          | ham   |
| 1          | bacon |
+------------+-------+
```

Invoice table:

```
+------------+------------+------+------+----------+----------+
| invoice_id | product_id | rest | paid | canceled | refunded |
+------------+------------+------+------+----------+----------+
| 23         | 0          | 2    | 0    | 5        | 0        |
| 12         | 0          | 0    | 4    | 0        | 3        |
| 1          | 1          | 1    | 1    | 0        | 1        |
| 2          | 1          | 1    | 0    | 1        | 1        |
| 3          | 1          | 0    | 1    | 1        | 1        |
| 4          | 1          | 1    | 1    | 1        | 0        |
+------------+------------+------+------+----------+----------+
```

Result 表：

```
+-------+------+------+----------+----------+
| name  | rest | paid | canceled | refunded |
+-------+------+------+----------+----------+
| bacon | 3    | 3    | 3        | 3        |
| ham   | 2    | 4    | 5        | 3        |
+-------+------+------+----------+----------+
```

- bacon 的总应缴款项为 1 + 1 + 0 + 1 = 3

- bacon 的总已支付金额为 1 + 0 + 1 + 1 = 3

- bacon 的总已取消金额为 0 + 1 + 1 + 1 = 3

- bacon 的总已退款金额为 1 + 1 + 1 + 0 = 3

- ham 的总应缴款项为 2 + 0 = 2

- ham 的总已支付金额为 0 + 4 = 4

- ham 的总已取消金额为 5 + 0 = 5

- ham 的总已退款金额为 0 + 3 = 3

', 'examDataFiles/auto_upload_1821_1637126775643.sql', '2021-11-17 13:26:15', '2021-11-17 13:26:15', 'auto_upload_1821_1637126775643.sql', 1, 1);
INSERT INTO sqlexam.main_questions (id, teacher_id, title, `desc`, db_path, created_at, updated_at, file_name, total_difficulty, sub_count) VALUES (1827, '0', '无效的推文', '表：Tweets

```
+----------------+---------+
| Column Name    | Type    |
+----------------+---------+
| tweet_id       | int     |
| content        | varchar |
+----------------+---------+
```

tweet_id 是这个表的主键。

这个表包含某社交媒体 App 中所有的推文。

写一条SQL 语句，查询所有无效推文的编号（ID）。当推文内容中的字符数严格大于 15 时，该推文是无效的。

以任意顺序返回结果表。

查询结果格式如下示例所示：

Tweets 表：

```
+----------+----------------------------------+
| tweet_id | content                          |
+----------+----------------------------------+
| 1        | Vote for Biden                   |
| 2        | Let us make America great again! |
+----------+----------------------------------+
```

结果表：

```
+----------+
| tweet_id |
+----------+
| 2        |
+----------+
```

推文 1 的长度 length = 14。该推文是有效的。

推文 2 的长度 length = 32。该推文是无效的。

', 'examDataFiles/auto_upload_1827_1637126775643.sql', '2021-11-17 13:26:15', '2021-11-17 13:26:15', 'auto_upload_1827_1637126775643.sql', 1, 1);
INSERT INTO sqlexam.main_questions (id, teacher_id, title, `desc`, db_path, created_at, updated_at, file_name, total_difficulty, sub_count) VALUES (1837, '0', '每天的领导和合伙人', '表：DailySales

```
+-------------+---------+
| Column Name | Type    |
+-------------+---------+
| date_id     | date    |
| make_name   | varchar |
| lead_id     | int     |
| partner_id  | int     |
+-------------+---------+
```

该表没有主键。

该表包含日期、产品的名称，以及售给的领导和合伙人的编号。

名称只包含小写英文字母。

写一条 SQL 语句，使得对于每一个date_id和make_name，返回不同的lead_id以及不同的partner_id的数量。

按任意顺序返回结果表。

查询结果格式如下示例所示：

DailySales 表：

```
+-----------+-----------+---------+------------+
| date_id   | make_name | lead_id | partner_id |
+-----------+-----------+---------+------------+
| 2020-12-8 | toyota    | 0       | 1          |
| 2020-12-8 | toyota    | 1       | 0          |
| 2020-12-8 | toyota    | 1       | 2          |
| 2020-12-7 | toyota    | 0       | 2          |
| 2020-12-7 | toyota    | 0       | 1          |
| 2020-12-8 | honda     | 1       | 2          |
| 2020-12-8 | honda     | 2       | 1          |
| 2020-12-7 | honda     | 0       | 1          |
| 2020-12-7 | honda     | 1       | 2          |
| 2020-12-7 | honda     | 2       | 1          |
+-----------+-----------+---------+------------+
```

结果表：

```
+-----------+-----------+--------------+-----------------+
| date_id   | make_name | unique_leads | unique_partners |
+-----------+-----------+--------------+-----------------+
| 2020-12-8 | toyota    | 2            | 3               |
| 2020-12-7 | toyota    | 1            | 2               |
| 2020-12-8 | honda     | 2            | 2               |
| 2020-12-7 | honda     | 3            | 2               |
+-----------+-----------+--------------+-----------------+
```

在 2020-12-8，丰田（toyota）有领导者 = [0, 1] 和合伙人 = [0, 1, 2] ，同时本田（honda）有领导者 = [1, 2] 和合伙人 = [1, 2]。

在 2020-12-7，丰田（toyota）有领导者 = [0] 和合伙人 = [1, 2] ，同时本田（honda）有领导者 = [0, 1, 2] 和合伙人 = [1, 2]。

', 'examDataFiles/auto_upload_1837_1637126775643.sql', '2021-11-17 13:26:15', '2021-11-17 13:26:15', 'auto_upload_1837_1637126775643.sql', 1, 1);
INSERT INTO sqlexam.main_questions (id, teacher_id, title, `desc`, db_path, created_at, updated_at, file_name, total_difficulty, sub_count) VALUES (1842, '0', '两人之间的通话次数', '表：Calls

```
+-------------+---------+
| Column Name | Type    |
+-------------+---------+
| from_id     | int     |
| to_id       | int     |
| duration    | int     |
+-------------+---------+
```

该表没有主键，可能存在重复项。

该表包含 from_id 与 to_id 间的一次电话的时长。

from_id != to_id

编写 SQL 语句，查询每一对用户(person1, person2)之间的通话次数和通话总时长，其中person1 < person2。

以任意顺序返回结果表。

查询结果格式如下示例所示：

Calls 表：

```
+---------+-------+----------+
| from_id | to_id | duration |
+---------+-------+----------+
| 1       | 2     | 59       |
| 2       | 1     | 11       |
| 1       | 3     | 20       |
| 3       | 4     | 100      |
| 3       | 4     | 200      |
| 3       | 4     | 200      |
| 4       | 3     | 499      |
+---------+-------+----------+
```

结果表：

```
+---------+---------+------------+----------------+
| person1 | person2 | call_count | total_duration |
+---------+---------+------------+----------------+
| 1       | 2       | 2          | 70             |
| 1       | 3       | 1          | 20             |
| 3       | 4       | 4          | 999            |
+---------+---------+------------+----------------+
```

用户 1 和 2 打过 2 次电话，总时长为 70 (59 + 11)。

用户 1 和 3 打过 1 次电话，总时长为 20。

用户 3 和 4 打过 4 次电话，总时长为 999 (100 + 200 + 200 + 499)。

', 'examDataFiles/auto_upload_1842_1637126775643.sql', '2021-11-17 13:26:15', '2021-11-17 13:26:15', 'auto_upload_1842_1637126775643.sql', 2, 1);
INSERT INTO sqlexam.main_questions (id, teacher_id, title, `desc`, db_path, created_at, updated_at, file_name, total_difficulty, sub_count) VALUES (1852, '0', '访问日期之间最大的空档期', '表：UserVisits

```
+-------------+------+
| Column Name | Type |
+-------------+------+
| user_id     | int  |
| visit_date  | date |
+-------------+------+
```

该表没有主键。

该表包含用户访问某特定零售商的日期日志。

假设今天的日期是''2021-1-1''。

编写 SQL 语句，对于每个user_id，求出每次访问及其下一个访问（若该次访问是最后一次，则为今天）之间最大的空档期天数window。

返回结果表，按用户编号user_id排序。

查询格式如下示例所示：

UserVisits 表：

```
+---------+------------+
| user_id | visit_date |
+---------+------------+
| 1       | 2020-11-28 |
| 1       | 2020-10-20 |
| 1       | 2020-12-3  |
| 2       | 2020-10-5  |
| 2       | 2020-12-9  |
| 3       | 2020-11-11 |
+---------+------------+
```

结果表：

```
+---------+---------------+
| user_id | biggest_window|
+---------+---------------+
| 1       | 39            |
| 2       | 65            |
| 3       | 51            |
+---------+---------------+
```

对于第一个用户，问题中的空档期在以下日期之间：

    - 2020-10-20 至 2020-11-28 ，共计 39 天。

    - 2020-11-28 至 2020-12-3 ，共计 5 天。

    - 2020-12-3 至 2021-1-1 ，共计 29 天。

由此得出，最大的空档期为 39 天。

对于第二个用户，问题中的空档期在以下日期之间：

    - 2020-10-5 至 2020-12-9 ，共计 65 天。

    - 2020-12-9 至 2021-1-1 ，共计 23 天。

由此得出，最大的空档期为 65 天。

对于第三个用户，问题中的唯一空档期在 2020-11-11 至 2021-1-1 之间，共计 51 天。

', 'examDataFiles/auto_upload_1852_1637126775643.sql', '2021-11-17 13:26:15', '2021-11-17 13:26:15', 'auto_upload_1852_1637126775643.sql', 2, 1);
INSERT INTO sqlexam.main_questions (id, teacher_id, title, `desc`, db_path, created_at, updated_at, file_name, total_difficulty, sub_count) VALUES (1862, '0', '苹果和橘子的个数', '表：Boxes

```
+--------------+------+
| Column Name  | Type |
+--------------+------+
| box_id       | int  |
| chest_id     | int  |
| apple_count  | int  |
| orange_count | int  |
+--------------+------+
```

box_id 是该表的主键。

chest_id 是 chests 表的外键。

该表包含大箱子 (box) 中包含的苹果和橘子的个数。每个大箱子中可能包含一个小盒子 (chest) ，小盒子中也包含若干苹果和橘子。

表：Chests

```
+--------------+------+
| Column Name  | Type |
+--------------+------+
| chest_id     | int  |
| apple_count  | int  |
| orange_count | int  |
+--------------+------+
```

chest_id 是该表的主键。

该表包含小盒子的信息，以及小盒子中包含的苹果和橘子的个数。

编写 SQL 语句，查询每个大箱子中苹果和橘子的个数。如果大箱子中包含小盒子，还应当包含小盒子中苹果和橘子的个数。

以任意顺序返回结果表。

查询结果的格式如下示例所示：

Boxes 表：

```
+--------+----------+-------------+--------------+
| box_id | chest_id | apple_count | orange_count |
+--------+----------+-------------+--------------+
| 2      | null     | 6           | 15           |
| 18     | 14       | 4           | 15           |
| 19     | 3        | 8           | 4            |
| 12     | 2        | 19          | 20           |
| 20     | 6        | 12          | 9            |
| 8      | 6        | 9           | 9            |
| 3      | 14       | 16          | 7            |
+--------+----------+-------------+--------------+
```

Chests 表：

```
+----------+-------------+--------------+
| chest_id | apple_count | orange_count |
+----------+-------------+--------------+
| 6        | 5           | 6            |
| 14       | 20          | 10           |
| 2        | 8           | 8            |
| 3        | 19          | 4            |
| 16       | 19          | 19           |
+----------+-------------+--------------+
```

结果表：

```
+-------------+--------------+
| apple_count | orange_count |
+-------------+--------------+
| 151         | 123          |
+-------------+--------------+
```

大箱子 2 中有 6 个苹果和 15 个橘子。

大箱子 18 中有 4 + 20 (在小盒子中) = 24 个苹果和 15 + 10 (在小盒子中) = 25 个橘子。

大箱子 19 中有 8 + 19 (在小盒子中) = 27 个苹果和 4 + 4 (在小盒子中) = 8 个橘子。

大箱子 12 中有 19 + 8 (在小盒子中) = 27 个苹果和 20 + 8 (在小盒子中) = 28 个橘子。

大箱子 20 中有 12 + 5 (在小盒子中) = 17 个苹果和 9 + 6 (在小盒子中) = 15 个橘子。

大箱子 8 中有 9 + 5 (在小盒子中) = 14 个苹果和 9 + 6 (在小盒子中) = 15 个橘子。

大箱子 3 中有 16 + 20 (在小盒子中) = 36 个苹果和 7 + 10 (在小盒子中) = 17 个橘子。

苹果的总个数 = 6 + 24 + 27 + 27 + 17 + 14 + 36 = 151

橘子的总个数 = 15 + 25 + 8 + 28 + 15 + 15 + 17 = 123

', 'examDataFiles/auto_upload_1862_1637126775646.sql', '2021-11-17 13:26:15', '2021-11-17 13:26:15', 'auto_upload_1862_1637126775646.sql', 2, 1);
INSERT INTO sqlexam.main_questions (id, teacher_id, title, `desc`, db_path, created_at, updated_at, file_name, total_difficulty, sub_count) VALUES (1877, '0', '求关注者的数量', '表：Followers

```
+-------------+------+
| Column Name | Type |
+-------------+------+
| user_id     | int  |
| follower_id | int  |
+-------------+------+
```

(user_id, follower_id) 是这个表的主键。

该表包含一个关注关系中关注者和用户的编号，其中关注者关注用户。

写出 SQL 语句，对于每一个用户，返回该用户的关注者数量。

按user_id的顺序返回结果表。

查询结果的格式如下示例所示：

Followers 表：

```
+---------+-------------+
| user_id | follower_id |
+---------+-------------+
| 0       | 1           |
| 1       | 0           |
| 2       | 0           |
| 2       | 1           |
+---------+-------------+
```

结果表：

```
+---------+----------------+
| user_id | followers_count|
+---------+----------------+
| 0       | 1              |
| 1       | 1              |
| 2       | 2              |
+---------+----------------+
```

0 的关注者有 {1}

1 的关注者有 {0}

2 的关注者有 {0,1}

', 'examDataFiles/auto_upload_1877_1637126775646.sql', '2021-11-17 13:26:15', '2021-11-17 13:26:15', 'auto_upload_1877_1637126775646.sql', 1, 1);
INSERT INTO sqlexam.main_questions (id, teacher_id, title, `desc`, db_path, created_at, updated_at, file_name, total_difficulty, sub_count) VALUES (1882, '0', '每位经理的下属员工数量', 'Table: Employees

```
+-------------+----------+
| Column Name | Type     |
+-------------+----------+
| employee_id | int      |
| name        | varchar  |
| reports_to  | int      |
| age         | int      |
+-------------+----------+
```

employee_id 是这个表的主键.

该表包含员工以及需要听取他们汇报的上级经理的ID的信息。 有些员工不需要向任何人汇报（reports_to 为空）。

对于此问题，我们将至少有一个其他员工需要向他汇报的员工，视为一个经理。

编写SQL查询需要听取汇报的所有经理的ID、名称、直接向该经理汇报的员工人数，以及这些员工的平均年龄，其中该平均年龄需要四舍五入到最接近的整数。

返回的结果集需要按照employee_id 进行排序。

查询结果的格式如下：

Employees table:

```
+-------------+---------+------------+-----+
| employee_id | name    | reports_to | age |
+-------------+---------+------------+-----+
| 9           | Hercy   | null       | 43  |
| 6           | Alice   | 9          | 41  |
| 4           | Bob     | 9          | 36  |
| 2           | Winston | null       | 37  |
+-------------+---------+------------+-----+
```

Result table:

```
+-------------+-------+---------------+-------------+
| employee_id | name  | reports_count | average_age |
+-------------+-------+---------------+-------------+
| 9           | Hercy | 2             | 39          |
+-------------+-------+---------------+-------------+
```

Hercy 有两个需要向他汇报的员工, 他们是 Alice and Bob. 他们的平均年龄是 (41+36)/2 = 38.5, 四舍五入的结果是 39.

', 'examDataFiles/auto_upload_1882_1637126775646.sql', '2021-11-17 13:26:15', '2021-11-17 13:26:15', 'auto_upload_1882_1637126775646.sql', 1, 1);
INSERT INTO sqlexam.main_questions (id, teacher_id, title, `desc`, db_path, created_at, updated_at, file_name, total_difficulty, sub_count) VALUES (1892, '0', '查找每个员工花费的总时间', '表: Employees

```
+-------------+------+
| Column Name | Type |
+-------------+------+
| emp_id      | int  |
| event_day   | date |
| in_time     | int  |
| out_time    | int  |
+-------------+------+
```

(emp_id, event_day, in_time) 是这个表的主键。

该表显示了员工在办公室的出入情况。

event_day 是此事件发生的日期，in_time 是员工进入办公室的时间，而 out_time 是他们离开办公室的时间。

in_time 和 out_time 的取值在1到1440之间。

题目保证同一天没有两个事件在时间上是相交的，并且保证 in_time 小于 out_time。

编写一个SQL查询以计算每位员工每天在办公室花费的总时间（以分钟为单位）。 请注意，在一天之内，同一员工是可以多次进入和离开办公室的。 在办公室里一次进出所花费的时间为out_time 减去 in_time。

返回结果表单的顺序无要求。

查询结果的格式如下：

Employees table:

```
+--------+------------+---------+----------+
| emp_id | event_day  | in_time | out_time |
+--------+------------+---------+----------+
| 1      | 2020-11-28 | 4       | 32       |
| 1      | 2020-11-28 | 55      | 200      |
| 1      | 2020-12-03 | 1       | 42       |
| 2      | 2020-11-28 | 3       | 33       |
| 2      | 2020-12-09 | 47      | 74       |
+--------+------------+---------+----------+
```

Result table:

```
+------------+--------+------------+
| day        | emp_id | total_time |
+------------+--------+------------+
| 2020-11-28 | 1      | 173        |
| 2020-11-28 | 2      | 30         |
| 2020-12-03 | 1      | 41         |
| 2020-12-09 | 2      | 27         |
+------------+--------+------------+
```

雇员 1 有三次进出: 有两次发生在 2020-11-28 花费的时间为 (32 - 4) + (200 - 55) = 173, 有一次发生在 2020-12-03 花费的时间为 (42 - 1) = 41。

雇员 2 有两次进出: 有一次发生在 2020-11-28 花费的时间为 (33 - 3) = 30,  有一次发生在 2020-12-09 花费的时间为 (74 - 47) = 27。

', 'examDataFiles/auto_upload_1892_1637126775646.sql', '2021-11-17 13:26:15', '2021-11-17 13:26:15', 'auto_upload_1892_1637126775646.sql', 1, 1);
INSERT INTO sqlexam.main_questions (id, teacher_id, title, `desc`, db_path, created_at, updated_at, file_name, total_difficulty, sub_count) VALUES (1898, '0', '应该被禁止的Leetflex账户', '表: LogInfo

```
+-------------+----------+
| Column Name | Type     |
+-------------+----------+
| account_id  | int      |
| ip_address  | int      |
| login       | datetime |
| logout      | datetime |
+-------------+----------+
```

该表是没有主键的，它可能包含重复项。

该表包含有关Leetflex帐户的登录和注销日期的信息。 它还包含了该账户用于登录和注销的网络地址的信息。

题目确保每一个注销时间都在登录时间之后。

编写一个SQL查询语句，查找那些应该被禁止的Leetflex帐户编号account_id。 如果某个帐户在某一时刻从两个不同的网络地址登录了，则这个帐户应该被禁止。

可以以任何顺序返回结果。

查询结果格式如下例所示:

LogInfo table:

```
+------------+------------+---------------------+---------------------+
| account_id | ip_address | login               | logout              |
+------------+------------+---------------------+---------------------+
| 1          | 1          | 2021-02-01 09:00:00 | 2021-02-01 09:30:00 |
| 1          | 2          | 2021-02-01 08:00:00 | 2021-02-01 11:30:00 |
| 2          | 6          | 2021-02-01 20:30:00 | 2021-02-01 22:00:00 |
| 2          | 7          | 2021-02-02 20:30:00 | 2021-02-02 22:00:00 |
| 3          | 9          | 2021-02-01 16:00:00 | 2021-02-01 16:59:59 |
| 3          | 13         | 2021-02-01 17:00:00 | 2021-02-01 17:59:59 |
| 4          | 10         | 2021-02-01 16:00:00 | 2021-02-01 17:00:00 |
| 4          | 11         | 2021-02-01 17:00:00 | 2021-02-01 17:59:59 |
+------------+------------+---------------------+---------------------+
```

Result table:

```
+------------+
| account_id |
+------------+
| 1          |
| 4          |
+------------+
```

Account ID 1 --> 该账户从 "2021-02-01 09:00:00" 到 "2021-02-01 09:30:00" 在两个不同的网络地址(1 and 2)上激活了。它应该被禁止.

Account ID 2 --> 该账户在两个不同的网络地址 (6, 7) 激活了，但在不同的时间上.

Account ID 3 --> 该账户在两个不同的网络地址 (9, 13) 激活了，虽然是同一天，但时间上没有交集.

Account ID 4 --> 该账户从 "2021-02-01 17:00:00" 到 "2021-02-01 17:00:00" 在两个不同的网络地址 (10 and 11)上激活了。它应该被禁止.

', 'examDataFiles/auto_upload_1898_1637126775646.sql', '2021-11-17 13:26:15', '2021-11-17 13:26:15', 'auto_upload_1898_1637126775646.sql', 2, 1);
INSERT INTO sqlexam.main_questions (id, teacher_id, title, `desc`, db_path, created_at, updated_at, file_name, total_difficulty, sub_count) VALUES (1908, '0', '可回收且低脂的产品', '表：Products

```
+-------------+---------+
| Column Name | Type    |
+-------------+---------+
| product_id  | int     |
| low_fats    | enum    |
| recyclable  | enum    |
+-------------+---------+
```

product_id 是这个表的主键。

low_fats 是枚举类型，取值为以下两种 (''Y'', ''N'')，其中 ''Y'' 表示该产品是低脂产品，''N'' 表示不是低脂产品。

recyclable 是枚举类型，取值为以下两种 (''Y'', ''N'')，其中 ''Y'' 表示该产品可回收，而 ''N'' 表示不可回收。

写出 SQL 语句，查找既是低脂又是可回收的产品编号。

返回结果 无顺序要求 。

查询结果格式如下例所示：

Products 表：

```
+-------------+----------+------------+
| product_id  | low_fats | recyclable |
+-------------+----------+------------+
| 0           | Y        | N          |
| 1           | Y        | Y          |
| 2           | N        | Y          |
| 3           | Y        | Y          |
| 4           | N        | N          |
+-------------+----------+------------+
```

Result 表：

```
+-------------+
| product_id  |
+-------------+
| 1           |
| 3           |
+-------------+
```

只有产品 id 为 1 和 3 的产品，既是低脂又是可回收的产品。

', 'examDataFiles/auto_upload_1908_1637126775646.sql', '2021-11-17 13:26:15', '2021-11-17 13:26:15', 'auto_upload_1908_1637126775646.sql', 1, 1);
INSERT INTO sqlexam.main_questions (id, teacher_id, title, `desc`, db_path, created_at, updated_at, file_name, total_difficulty, sub_count) VALUES (1914, '0', '寻找没有被执行的任务对', '表：Tasks

```
+----------------+---------+
| Column Name    | Type    |
+----------------+---------+
| task_id        | int     |
| subtasks_count | int     |
+----------------+---------+
```

task_id 是这个表的主键。

task_id 表示的为主任务的id,每一个task_id被分为了多个子任务(subtasks)，subtasks_count表示为子任务的个数（n），它的值表示了子任务的索引从1到n。

本表保证2 <=subtasks_count<= 20。

表： Executed

```
+---------------+---------+
| Column Name   | Type    |
+---------------+---------+
| task_id       | int     |
| subtask_id    | int     |
+---------------+---------+
```

(task_id, subtask_id) 是这个表的主键。

每一行表示标记为task_id的主任务与标记为subtask_id的子任务被成功执行。

本表保证，对于每一个task_id，subtask_id <= subtasks_count。

请试写一个SQL查询语句报告没有被执行的（主任务，子任务）对，即没有被执行的（task_id, subtask_id）。

以 任何顺序 返回即可。

查询结果格式如下：

Tasks table:

```
+---------+----------------+
| task_id | subtasks_count |
+---------+----------------+
| 1       | 3              |
| 2       | 2              |
| 3       | 4              |
+---------+----------------+
```

Executed table:

```
+---------+------------+
| task_id | subtask_id |
+---------+------------+
| 1       | 2          |
| 3       | 1          |
| 3       | 2          |
| 3       | 3          |
| 3       | 4          |
+---------+------------+
```

Result table:

```
+---------+------------+
| task_id | subtask_id |
+---------+------------+
| 1       | 1          |
| 1       | 3          |
| 2       | 1          |
| 2       | 2          |
+---------+------------+
```

Task 1 被分成了 3 subtasks (1, 2, 3)。只有 subtask 2 被成功执行, 所以我们返回 (1, 1) 和 (1, 3) 这两个主任务子任务对。

Task 2 被分成了 2 subtasks (1, 2)。没有一个subtask被成功执行, 因此我们返回(2, 1)和(2, 2)。

Task 3 被分成了 4 subtasks (1, 2, 3, 4)。所有的subtask都被成功执行，因此对于Task 3,我们不返回任何值。

', 'examDataFiles/auto_upload_1914_1637126775646.sql', '2021-11-17 13:26:15', '2021-11-17 13:26:15', 'auto_upload_1914_1637126775646.sql', 3, 1);
INSERT INTO sqlexam.main_questions (id, teacher_id, title, `desc`, db_path, created_at, updated_at, file_name, total_difficulty, sub_count) VALUES (1932, '0', '大满贯数量', '表：Players

```
+----------------+---------+
| Column Name    | Type    |
+----------------+---------+
| player_id      | int     |
| player_name    | varchar |
+----------------+---------+
```

player_id 是这个表的主键

这个表的每一行给出一个网球运动员的 ID 和 姓名

表：Championships

```
+---------------+---------+
| Column Name   | Type    |
+---------------+---------+
| year          | int     |
| Wimbledon     | int     |
| Fr_open       | int     |
| US_open       | int     |
| Au_open       | int     |
+---------------+---------+
```

year 是这个表的主键

该表的每一行都包含在每场大满贯网球比赛中赢得比赛的球员的 ID

请写出查询语句，查询出每一个球员赢得大满贯比赛的次数。结果不包含没有赢得比赛的球员的ID 。

结果集无顺序要求。

查询结果的格式，如下所示：

Players 表：

```
+-----------+-------------+
| player_id | player_name |
+-----------+-------------+
| 1         | Nadal       |
| 2         | Federer     |
| 3         | Novak       |
+-----------+-------------+
```

Championships 表：

```
+------+-----------+---------+---------+---------+
| year | Wimbledon | Fr_open | US_open | Au_open |
+------+-----------+---------+---------+---------+
| 2018 | 1         | 1       | 1       | 1       |
| 2019 | 1         | 1       | 2       | 2       |
| 2020 | 2         | 1       | 2       | 2       |
+------+-----------+---------+---------+---------+
```

Result 表：

```
+-----------+-------------+-------------------+
| player_id | player_name | grand_slams_count |
+-----------+-------------+-------------------+
| 2         | Federer     | 5                 |
| 1         | Nadal       | 7                 |
+-----------+-------------+-------------------+
```

Player 1 (Nadal) 获得了 7 次大满贯：其中温网 2 次(2018, 2019), 法国公开赛 3 次 (2018, 2019, 2020), 美国公开赛 1 次 (2018)以及澳网公开赛 1 次 (2018) 。

Player 2 (Federer) 获得了 5 次大满贯：其中温网 1 次 (2020), 美国公开赛 2 次 (2019, 2020) 以及澳网公开赛 2 次 (2019, 2020) 。

Player 3 (Novak)  没有赢得，因此不包含在结果集中。

', 'examDataFiles/auto_upload_1932_1637126775646.sql', '2021-11-17 13:26:15', '2021-11-17 13:26:15', 'auto_upload_1932_1637126775646.sql', 2, 1);
INSERT INTO sqlexam.main_questions (id, teacher_id, title, `desc`, db_path, created_at, updated_at, file_name, total_difficulty, sub_count) VALUES (1942, '0', '员工的直属部门', 'Table: Employee

```
+---------------+---------+
| Column Name   |  Type   |
+---------------+---------+
| employee_id   | int     |
| department_id | int     |
| primary_flag  | varchar |
+---------------+---------+
```

这张表的主键为 employee_id, department_id

employee_id 是员工的ID

department_id 是部门的ID，表示员工与该部门有关系

primary_flag 是一个枚举类型，值分别为(''Y'', ''N''). 如果值为''Y'',表示该部门是员工的直属部门。 如果值是''N'',则否

一个员工可以属于多个部门。

当一个员工加入超过一个部门的时候，他需要决定哪个部门是他的直属部门。

请注意，当员工只加入一个部门的时候，那这个部门将默认为他的直属部门，虽然表记录的值为''N''.

请编写一段SQL，查出员工所属的直属部门。

返回结果没有顺序要求。

示例：

Employee table:

```
+-------------+---------------+--------------+
| employee_id | department_id | primary_flag |
+-------------+---------------+--------------+
| 1           | 1             | N            |
| 2           | 1             | Y            |
| 2           | 2             | N            |
| 3           | 3             | N            |
| 4           | 2             | N            |
| 4           | 3             | Y            |
| 4           | 4             | N            |
+-------------+---------------+--------------+
```

Result table:

```
+-------------+---------------+
| employee_id | department_id |
+-------------+---------------+
| 1           | 1             |
| 2           | 1             |
| 3           | 3             |
| 4           | 3             |
+-------------+---------------+
```

- 员工1的直属部门是1

- 员工2的直属部门是1

- 员工3的直属部门是3

- 员工4的直属部门是3

', 'examDataFiles/auto_upload_1942_1637126775646.sql', '2021-11-17 13:26:15', '2021-11-17 13:26:15', 'auto_upload_1942_1637126775646.sql', 1, 1);
INSERT INTO sqlexam.main_questions (id, teacher_id, title, `desc`, db_path, created_at, updated_at, file_name, total_difficulty, sub_count) VALUES (1948, '0', '每个产品在不同商店的价格', '表：Products

```
+-------------+---------+
| Column Name | Type    |
+-------------+---------+
| product_id  | int     |
| store1      | int     |
| store2      | int     |
| store3      | int     |
+-------------+---------+
```

这张表的主键是product_id（产品Id）。

每行存储了这一产品在不同商店store1, store2, store3的价格。

如果这一产品在商店里没有出售，则值将为null。

请你重构 Products 表，查询每个产品在不同商店的价格，使得输出的格式变为(product_id, store, price) 。如果这一产品在商店里没有出售，则不输出这一行。

输出结果表中的顺序不作要求。

查询输出格式请参考下面示例。

Products table:

```
+------------+--------+--------+--------+
| product_id | store1 | store2 | store3 |
+------------+--------+--------+--------+
| 0          | 95     | 100    | 105    |
| 1          | 70     | null   | 80     |
+------------+--------+--------+--------+
```

Result table:

```
+------------+--------+-------+
| product_id | store  | price |
+------------+--------+-------+
| 0          | store1 | 95    |
| 0          | store2 | 100   |
| 0          | store3 | 105   |
| 1          | store1 | 70    |
| 1          | store3 | 80    |
+------------+--------+-------+
```

产品0在store1，store2,store3的价格分别为95,100,105。

产品1在store1，store3的价格分别为70,80。在store2无法买到。

', 'examDataFiles/auto_upload_1948_1637126775646.sql', '2021-11-17 13:26:15', '2021-11-17 13:26:15', 'auto_upload_1948_1637126775646.sql', 1, 1);
INSERT INTO sqlexam.main_questions (id, teacher_id, title, `desc`, db_path, created_at, updated_at, file_name, total_difficulty, sub_count) VALUES (1958, '0', '没有广告的剧集', 'Table: Playback

```
+-------------+------+
| Column Name | Type |
+-------------+------+
| session_id  | int  |
| customer_id | int  |
| start_time  | int  |
| end_time    | int  |
+-------------+------+
```

该表主键为：session_id （剧集id）

customer_id 是观看该剧集的观众id

剧集播放时间包含start_time（开始时间） 及 end_time（结束时间）

可以保证的是，start_time（开始时间）<= end_time（结束时间），一个观众观看的两个剧集的时间不会出现重叠。

Table: Ads

```
+-------------+------+
| Column Name | Type |
+-------------+------+
| ad_id       | int  |
| customer_id | int  |
| timestamp   | int  |
+-------------+------+
```

该表的主键为：ad_id（广告id）

customer_id 为 观看广告的用户id

timestamp 表示广告出现的时间点

请查出，所有没有广告出现过的剧集。

如果观众观看了剧集，并且剧集里出现了广告，就一定会有观众观看广告的记录。

返回结果没有顺序要求。

示例：

Playback table:

```
+------------+-------------+------------+----------+
| session_id | customer_id | start_time | end_time |
+------------+-------------+------------+----------+
| 1          | 1           | 1          | 5        |
| 2          | 1           | 15         | 23       |
| 3          | 2           | 10         | 12       |
| 4          | 2           | 17         | 28       |
| 5          | 2           | 2          | 8        |
+------------+-------------+------------+----------+
```

Ads table:

```
+-------+-------------+-----------+
| ad_id | customer_id | timestamp |
+-------+-------------+-----------+
| 1     | 1           | 5         |
| 2     | 2           | 17        |
| 3     | 2           | 20        |
+-------+-------------+-----------+
```

Result table:

```
+------------+
| session_id |
+------------+
| 2          |
| 3          |
| 5          |
+------------+
```

广告1出现在了剧集1的时间段，被观众1看到了。

广告2出现在了剧集4的时间段，被观众2看到了。

广告3出现在了剧集4的时间段，被观众2看到了。

我们可以得出结论，剧集1 、4 内，起码有1处广告。 剧集2 、3 、5 没有广告。

', 'examDataFiles/auto_upload_1958_1637126775646.sql', '2021-11-17 13:26:15', '2021-11-17 13:26:15', 'auto_upload_1958_1637126775646.sql', 1, 1);
INSERT INTO sqlexam.main_questions (id, teacher_id, title, `desc`, db_path, created_at, updated_at, file_name, total_difficulty, sub_count) VALUES (1964, '0', '寻找面试候选人', '表: Contests

```
+--------------+------+
| Column Name  | Type |
+--------------+------+
| contest_id   | int  |
| gold_medal   | int  |
| silver_medal | int  |
| bronze_medal | int  |
+--------------+------+
```

contest_id 是该表的主键.

该表包含LeetCode竞赛的ID和该场比赛中金牌、银牌、铜牌的用户id。

可以保证，所有连续的比赛都有连续的ID，没有ID被跳过。

Table: Users

```
+-------------+---------+
| Column Name | Type    |
+-------------+---------+
| user_id     | int     |
| mail        | varchar |
| name        | varchar |
+-------------+---------+
```

user_id 是该表的主键.

该表包含用户信息。

编写 SQL 语句来返回面试候选人的 姓名和邮件.当用户满足以下两个要求中的任意一条，其成为面试候选人:

该用户在连续三场及更多比赛中赢得奖牌。

该用户在三场及更多不同的比赛中赢得金牌（这些比赛可以不是连续的）

可以以任何顺序返回结果。

查询结果格式如下例所示：

Contests表:

```
+------------+------------+--------------+--------------+
| contest_id | gold_medal | silver_medal | bronze_medal |
+------------+------------+--------------+--------------+
| 190        | 1          | 5            | 2            |
| 191        | 2          | 3            | 5            |
| 192        | 5          | 2            | 3            |
| 193        | 1          | 3            | 5            |
| 194        | 4          | 5            | 2            |
| 195        | 4          | 2            | 1            |
| 196        | 1          | 5            | 2            |
+------------+------------+--------------+--------------+
```

Users表:

```
+---------+--------------------+-------+
| user_id | mail               | name  |
+---------+--------------------+-------+
| 1       | sarah@leetcode.com | Sarah |
| 2       | bob@leetcode.com   | Bob   |
| 3       | alice@leetcode.com | Alice |
| 4       | hercy@leetcode.com | Hercy |
| 5       | quarz@leetcode.com | Quarz |
+---------+--------------------+-------+
```

结果表:

```
+-------+--------------------+
| name  | mail               |
+-------+--------------------+
| Sarah | sarah@leetcode.com |
| Bob   | bob@leetcode.com   |
| Alice | alice@leetcode.com |
| Quarz | quarz@leetcode.com |
+-------+--------------------+
```

Sarah 赢得了3块金牌 (190, 193, and 196),所以我们将她列入结果表。

Bob在连续3场竞赛中赢得了奖牌(190, 191, and 192), 所以我们将他列入结果表。

    - 注意他在另外的连续3场竞赛中也赢得了奖牌(194, 195, and 196).

Alice在连续3场竞赛中赢得了奖牌 (191, 192, and 193), 所以我们将她列入结果表。

Quarz在连续5场竞赛中赢得了奖牌(190, 191, 192, 193, and 194), 所以我们将他列入结果表。

进阶：

如果第一个条件变成“该用户在连续n场及比赛中赢得任意奖牌。”呢？你如何更改你的解法，来选出面试候选人？可以把n想象成存储过程中的参数。

有的用户可能没有参加每一场竞赛，但是在参加的每一场竞赛中都表现得不错。你如何更改你的解法，以达到只考虑那些用户参与了的比赛？可假设另一张表给出了每场比赛的注册用户信息。

', 'examDataFiles/auto_upload_1964_1637126775646.sql', '2021-11-17 13:26:15', '2021-11-17 13:26:15', 'auto_upload_1964_1637126775646.sql', 2, 1);
INSERT INTO sqlexam.main_questions (id, teacher_id, title, `desc`, db_path, created_at, updated_at, file_name, total_difficulty, sub_count) VALUES (1974, '0', '寻找今年具有正收入的客户', '表：Customers

```
+--------------+------+
| Column Name  | Type |
+--------------+------+
| customer_id  | int  |
| year         | int  |
| revenue      | int  |
+--------------+------+
```

(customer_id, year) 是这个表的主键。

这个表包含客户 ID 和不同年份的客户收入。

注意，这个收入可能是负数。

写一个 SQL 查询来查询 2021 年具有 正收入 的客户。

可以按 任意顺序 返回结果表。

查询结果格式如下例。

Customers

```
+-------------+------+---------+
| customer_id | year | revenue |
+-------------+------+---------+
| 1           | 2018 | 50      |
| 1           | 2021 | 30      |
| 1           | 2020 | 70      |
| 2           | 2021 | -50     |
| 3           | 2018 | 10      |
| 3           | 2016 | 50      |
| 4           | 2021 | 20      |
+-------------+------+---------+
```

Result table:

```
+-------------+
| customer_id |
+-------------+
| 1           |
| 4           |
+-------------+
```

客户 1 在 2021 年的收入等于 30 。

客户 2 在 2021 年的收入等于 -50 。

客户 3 在 2021 年没有收入。

客户 4 在 2021 年的收入等于 20 。

因此，只有客户 1 和 4 在 2021 年有正收入。

', 'examDataFiles/auto_upload_1974_1637126775646.sql', '2021-11-17 13:26:15', '2021-11-17 13:26:15', 'auto_upload_1974_1637126775646.sql', 1, 1);
INSERT INTO sqlexam.main_questions (id, teacher_id, title, `desc`, db_path, created_at, updated_at, file_name, total_difficulty, sub_count) VALUES (1981, '0', '每天的最大交易', '表: Transactions

```
+----------------+----------+
| Column Name    | Type     |
+----------------+----------+
| transaction_id | int      |
| day            | datetime |
| amount         | int      |
+----------------+----------+
```

transaction_id 是此表的主键。

每行包括了该次交易的信息。

写一条 SQL返回每天交易金额 amount 最大的交易 ID 。如果某天有多个这样的交易，返回这些交易的 ID 。

返回结果根据 transaction_id升序排列。

查询结果样例如下：

Transactions table:

```
+----------------+--------------------+--------+
| transaction_id | day                | amount |
+----------------+--------------------+--------+
| 8              | 2021-4-3 15:57:28  | 57     |
| 9              | 2021-4-28 08:47:25 | 21     |
| 1              | 2021-4-29 13:28:30 | 58     |
| 5              | 2021-4-28 16:39:59 | 40     |
| 6              | 2021-4-29 23:39:28 | 58     |
+----------------+--------------------+--------+
```

Result table:

```
+----------------+
| transaction_id |
+----------------+
| 1              |
| 5              |
| 6              |
| 8              |
+----------------+
```

"2021-4-3"  --> 有一个 id 是 8 的交易，因此，把它加入结果表。 

"2021-4-28" --> 有两个交易，id 是 5 和 9 ，交易 5 的金额是 40 ，而交易 9 的数量是 21 。只需要将交易 5 加入结果表，因为它是当天金额最大的交易。

"2021-4-29" --> 有两个交易，id 是 1 和 6 ，这两个交易的金额都是 58 ，因此需要把它们都写入结果表。

最后，把交易 id 按照升序排列。

进阶：你可以不使用MAX()函数解决这道题目吗?

', 'examDataFiles/auto_upload_1981_1637126775646.sql', '2021-11-17 13:26:15', '2021-11-17 13:26:15', 'auto_upload_1981_1637126775646.sql', 2, 1);
INSERT INTO sqlexam.main_questions (id, teacher_id, title, `desc`, db_path, created_at, updated_at, file_name, total_difficulty, sub_count) VALUES (1991, '0', '联赛信息统计', 'Table: Teams

```
+----------------+---------+
| Column Name    | Type    |
+----------------+---------+
| team_id        | int     |
| team_name      | varchar |
+----------------+---------+
```

team_id 是该表主键.

每一行都包含了一个参加联赛的队伍信息.

Table: Matches

```
+-----------------+---------+
| Column Name     | Type    |
+-----------------+---------+
| home_team_id    | int     |
| away_team_id    | int     |
| home_team_goals | int     |
| away_team_goals | int     |
+-----------------+---------+
```

(home_team_id, away_team_id) 是该表主键.

每一行包含了一次比赛信息.

home_team_goals 代表主场队得球数.

away_team_goals 代表客场队得球数.

获得球数较多的队伍为胜者队伍.

写一段SQL，用来报告联赛信息. 统计数据应使用已进行的比赛来构建，其中获胜球队获得三分，而失败球队获得零分. 如果打平，两支球队都得一分.

result表的每行应包含以下信息:

team_name - Teams表中的队伍名字

matches_played - 主场与客场球队进行的比赛次数.

points - 球队获得的总分数.

goal_for - 球队在所有比赛中获取的总进球数

goal_against - 球队在所有比赛中，他的对手球队的所有进球数

goal_diff - goal_for - goal_against.

按分数降序返回结果表。 如果两队或多队得分相同，则按goal_diff 降序排列。 如果仍然存在平局，则以team_name 按字典顺序排列它们。

查询的结果格式如下例所示:

Teams table:

```
+---------+-----------+
| team_id | team_name |
+---------+-----------+
| 1       | Ajax      |
| 4       | Dortmund  |
| 6       | Arsenal   |
+---------+-----------+
```

Matches table:

```
+--------------+--------------+-----------------+-----------------+
| home_team_id | away_team_id | home_team_goals | away_team_goals |
+--------------+--------------+-----------------+-----------------+
| 1            | 4            | 0               | 1               |
| 1            | 6            | 3               | 3               |
| 4            | 1            | 5               | 2               |
| 6            | 1            | 0               | 0               |
+--------------+--------------+-----------------+-----------------+
```

Result table:

```
+-----------+----------------+--------+----------+--------------+-----------+
| team_name | matches_played | points | goal_for | goal_against | goal_diff |
+-----------+----------------+--------+----------+--------------+-----------+
| Dortmund  | 2              | 6      | 6        | 2            | 4         |
| Arsenal   | 2              | 2      | 3        | 3            | 0         |
| Ajax      | 4              | 2      | 5        | 9            | -4        |
+-----------+----------------+--------+----------+--------------+-----------+
```

Ajax (team_id=1) 有4场比赛: 2败2平. 总分数 = 0 + 0 + 1 + 1 = 2.

Dortmund (team_id=4) 有2场比赛: 2胜. 总分数 = 3 + 3 = 6.

Arsenal (team_id=6) 有2场比赛: 2平. 总分数 = 1 + 1 = 2.

Dortmund 是积分榜上的第一支球队. Ajax和Arsenal 有同样的分数, 但Arsenal的goal_diff高于Ajax, 所以Arsenal在表中的顺序在Ajaxzhi''qian.

', 'examDataFiles/auto_upload_1991_1637126775646.sql', '2021-11-17 13:26:15', '2021-11-17 13:26:15', 'auto_upload_1991_1637126775646.sql', 2, 1);
INSERT INTO sqlexam.main_questions (id, teacher_id, title, `desc`, db_path, created_at, updated_at, file_name, total_difficulty, sub_count) VALUES (2004, '0', '转换日期格式', '表: Days

```
+-------------+------+
| Column Name | Type |
+-------------+------+
| day         | date |
+-------------+------+
```

day 是这个表的主键。

给定一个Days表，请你编写SQL查询语句，将Days表中的每一个日期转化为"day_name, month_name day, year"格式的字符串。

返回的结果表不计顺序。

例如：

Days table:

```
+------------+
| day        |
+------------+
| 2022-04-12 |
| 2021-08-09 |
| 2020-06-26 |
+------------+
```

Result table:

```
+-------------------------+
| day                     |
+-------------------------+
| Tuesday, April 12, 2022 |
| Monday, August 9, 2021  |
| Friday, June 26, 2020   |
+-------------------------+
```

请注意，输出对大小写敏感。

', 'examDataFiles/auto_upload_2004_1637126775646.sql', '2021-11-17 13:26:15', '2021-11-17 13:26:15', 'auto_upload_2004_1637126775646.sql', 1, 1);
INSERT INTO sqlexam.main_questions (id, teacher_id, title, `desc`, db_path, created_at, updated_at, file_name, total_difficulty, sub_count) VALUES (2024, '0', '计算特殊奖金', '表: Employees

```
+-------------+---------+
| 列名        | 类型     |
+-------------+---------+
| employee_id | int     |
| name        | varchar |
| salary      | int     |
+-------------+---------+
```

employee_id 是这个表的主键。

此表的每一行给出了雇员id ，名字和薪水。

写出一个SQL 查询语句，计算每个雇员的奖金。如果一个雇员的id是奇数并且他的名字不是以''M''开头，那么他的奖金是他工资的100%，否则奖金为0。

Return the result table ordered by employee_id.

返回的结果集请按照employee_id排序。

查询结果格式如下面的例子所示：

Employees 表:

```
+-------------+---------+--------+
| employee_id | name    | salary |
+-------------+---------+--------+
| 2           | Meir    | 3000   |
| 3           | Michael | 3800   |
| 7           | Addilyn | 7400   |
| 8           | Juan    | 6100   |
| 9           | Kannon  | 7700   |
+-------------+---------+--------+
```

结果表:

```
+-------------+-------+
| employee_id | bonus |
+-------------+-------+
| 2           | 0     |
| 3           | 0     |
| 7           | 7400  |
| 8           | 0     |
| 9           | 7700  |
+-------------+-------+
```

因为雇员id是偶数，所以雇员id 是2和8的两个雇员得到的奖金是0。

雇员id为3的因为他的名字以''M''开头，所以，奖金是0。

其他的雇员得到了百分之百的奖金。

', 'examDataFiles/auto_upload_2024_1637126775647.sql', '2021-11-17 13:26:15', '2021-11-17 13:26:15', 'auto_upload_2024_1637126775647.sql', 1, 1);
INSERT INTO sqlexam.main_questions (id, teacher_id, title, `desc`, db_path, created_at, updated_at, file_name, total_difficulty, sub_count) VALUES (2041, '0', '2020年最后一次登录', '表: Logins

```
+----------------+----------+
| 列名           | 类型      |
+----------------+----------+
| user_id        | int      |
| time_stamp     | datetime |
+----------------+----------+
```

(user_id, time_stamp) 是这个表的主键。

每一行包含的信息是user_id 这个用户的登录时间。

编写一个 SQL 查询，该查询可以获取在2020年登录过的所有用户的本年度最后一次登录时间。结果集不包含2020年没有登录过的用户。

返回的结果集可以按任意顺序排列。

查询结果格式如下例：

Logins 表:

```
+---------+---------------------+
| user_id | time_stamp          |
+---------+---------------------+
| 6       | 2020-06-30 15:06:07 |
| 6       | 2021-04-21 14:06:06 |
| 6       | 2019-03-07 00:18:15 |
| 8       | 2020-02-01 05:10:53 |
| 8       | 2020-12-30 00:46:50 |
| 2       | 2020-01-16 02:49:50 |
| 2       | 2019-08-25 07:59:08 |
| 14      | 2019-07-14 09:00:00 |
| 14      | 2021-01-06 11:59:59 |
+---------+---------------------+
```

Result 表:

```
+---------+---------------------+
| user_id | last_stamp          |
+---------+---------------------+
| 6       | 2020-06-30 15:06:07 |
| 8       | 2020-12-30 00:46:50 |
| 2       | 2020-01-16 02:49:50 |
+---------+---------------------+
```

6号用户登录了3次，但是在2020年仅有一次，所以结果集应包含此次登录。

8号用户在2020年登录了2次，一次在2月，一次在12月，所以，结果集应该包含12月的这次登录。

2号用户登录了2次，但是在2020年仅有一次，所以结果集应包含此次登录。

14号用户在2020年没有登录，所以结果集不应包含。

', 'examDataFiles/auto_upload_2041_1637126775647.sql', '2021-11-17 13:26:15', '2021-11-17 13:26:15', 'auto_upload_2041_1637126775647.sql', 1, 1);
INSERT INTO sqlexam.main_questions (id, teacher_id, title, `desc`, db_path, created_at, updated_at, file_name, total_difficulty, sub_count) VALUES (2057, '0', '按分类统计薪水', '表: Accounts

```
+-------------+------+
| 列名        | 类型  |
+-------------+------+
| account_id  | int  |
| income      | int  |
+-------------+------+
```

account_id是这个表的主键。

每一行都包含一个银行帐户的月收入的信息。

写出一个SQL查询，来报告每个工资类别的银行账户数量。工资类别如下：

“低薪”：所有工资严格低于20000美元。

“中等薪水”：包含范围内的所有工资[$20000,$50000]。

“高薪”：所有工资严格大于50000美元。

结果表必须包含所有三个类别。如果某个类别中没有帐户，则报告0。

按任意顺序返回结果表。

查询结果格式如下示例：

Accounts 表:

```
+------------+--------+
| account_id | income |
+------------+--------+
| 3          | 108939 |
| 2          | 12747  |
| 8          | 87709  |
| 6          | 91796  |
+------------+--------+
```

Result 表:

```
+----------------+----------------+
| category       | accounts_count |
+----------------+----------------+
| Low Salary     | 1              |
| Average Salary | 0              |
| High Salary    | 3              |
+----------------+----------------+
```

低薪: 数量为 2.

中等薪水: 没有.

高薪: 有三个账户，他们是 3, 6和 8.

', 'examDataFiles/auto_upload_2057_1637126775647.sql', '2021-11-17 13:26:15', '2021-11-17 13:26:15', 'auto_upload_2057_1637126775647.sql', 2, 1);
INSERT INTO sqlexam.main_questions (id, teacher_id, title, `desc`, db_path, created_at, updated_at, file_name, total_difficulty, sub_count) VALUES (2064, '0', '兴趣相同的朋友', 'Table: Listens

```
+-------------+---------+
| Column Name | Type    |
+-------------+---------+
| user_id     | int     |
| song_id     | int     |
| day         | date    |
+-------------+---------+
```

该表没有主键，因此会存在重复的行。

该表的每一行所代表的含义是：用户（user_id）在某天（day）听了某首歌曲（song_id）。

Table: Friendship

```
+---------------+---------+
| Column Name   | Type    |
+---------------+---------+
| user1_id      | int     |
| user2_id      | int     |
+---------------+---------+
```

(user1_id, user2_id) 是该表的主键。

该表的每一行所代表的含义是，用户（user1_id, user2_id）是朋友。

注意：user1_id < user2_id。

请写一段SQL查询获取到兴趣相同的朋友。用户 x和 用户 y是兴趣相同的朋友，需满足下述条件：

用户x和y是朋友，并且

用户x and y在同一天内听过相同的歌曲，且数量大于等于三首.

结果表无需排序。注意：返回的结果需要和源数据表的呈现方式相同（例如，需满足user1_id < user2_id）。

结果表的格式如下例：

Listens table:

```
+---------+---------+------------+
| user_id | song_id | day        |
+---------+---------+------------+
| 1       | 10      | 2021-03-15 |
| 1       | 11      | 2021-03-15 |
| 1       | 12      | 2021-03-15 |
| 2       | 10      | 2021-03-15 |
| 2       | 11      | 2021-03-15 |
| 2       | 12      | 2021-03-15 |
| 3       | 10      | 2021-03-15 |
| 3       | 11      | 2021-03-15 |
| 3       | 12      | 2021-03-15 |
| 4       | 10      | 2021-03-15 |
| 4       | 11      | 2021-03-15 |
| 4       | 13      | 2021-03-15 |
| 5       | 10      | 2021-03-16 |
| 5       | 11      | 2021-03-16 |
| 5       | 12      | 2021-03-16 |
+---------+---------+------------+
```

Friendship table:

```
+----------+----------+
| user1_id | user2_id |
+----------+----------+
| 1        | 2        |
| 2        | 4        |
| 2        | 5        |
+----------+----------+
```

Result table:

```
+----------+----------+
| user1_id | user2_id |
+----------+----------+
| 1        | 2        |
+----------+----------+
```

用户 1 和 2 是朋友, 并且他们在同一天内都听了10、11、12的歌曲。所以，他们是兴趣相同的朋友。

用户 1 和 3 在同一天内都听了10、11、12的歌曲，但他们不是朋友。

用户 2 和 4 是朋友，但他们同一天内听过相同的歌曲的数量小于3。

用户 2 和 5 是朋友，并且在都听了了10、11、12的歌曲，但不在同一天内。

', 'examDataFiles/auto_upload_2064_1637126775647.sql', '2021-11-17 13:26:15', '2021-11-17 13:26:15', 'auto_upload_2064_1637126775647.sql', 3, 1);
INSERT INTO sqlexam.main_questions (id, teacher_id, title, `desc`, db_path, created_at, updated_at, file_name, total_difficulty, sub_count) VALUES (2098, '0', '查询具有最多共同关注者的所有两两结对组', '表: Relations

```
+-------------+------+
| Column Name | Type |
+-------------+------+
| user_id     | int  |
| follower_id | int  |
+-------------+------+
```

(user_id, follower_id) 是这个表的主键.

这个表的每一行，表示这个user_id的用户和他的关注者，关注者的id 就是本表的 user_id.

写出一个查询语句，找到具有最多共同关注者的所有两两结对组。换句话说，如果有两个用户的共同关注者是最大的，我们应该返回所有具有此最大值的两两结对组

The result table should contain the pairs user1_id and user2_id where user1_id < user2_id.

结果返回表，每一行应该包含user1_id和user2_id，其中user1_id < user2_id.

返回结果不要求顺序。

查询结果格式如下例：

Relations 表:

```
+---------+-------------+
| user_id | follower_id |
+---------+-------------+
| 1       | 3           |
| 2       | 3           |
| 7       | 3           |
| 1       | 4           |
| 2       | 4           |
| 7       | 4           |
| 1       | 5           |
| 2       | 6           |
| 7       | 5           |
+---------+-------------+
```

Result 表:

```
+----------+----------+
| user1_id | user2_id |
+----------+----------+
| 1        | 7        |
+----------+----------+
```

用户1 和用户 2 有2个共同的关注者（3和4）。

用户1 和用户 7 有3个共同的关注者（3，4和5）。

用户2 和用户7 有2个共同的关注者（3和4）。

既然两两结对的所有组队的最大共同关注者的数值是3，所以，我们应该返回所有拥有3个共同关注者的两两组队，这就是仅有的一对(1, 7).

我们返回的是(1, 7).，而不是(7, 1).

注意，我们没有关于用户3，4，5的任何关注者信息，我们认为他们有0个关注者。

', 'examDataFiles/auto_upload_2098_1637126775647.sql', '2021-11-17 13:26:15', '2021-11-17 13:26:15', 'auto_upload_2098_1637126775647.sql', 2, 1);