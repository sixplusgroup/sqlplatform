# 该文件用于向main_question表和sub_question表中添加OceanBase题目的信息

# 在main_question表中插入大题信息
insert into sqlexercise.main_question(title, description, db_path, created_at, updated_at, file_name, total_difficulty,
                                      sub_count)
values ('OceanBase查询优化', '某应用后台数据库有一张表用来存用户信息，表结构如下：

`app_user`表：

```
+--------------+--------------+
| Column Name  | Type         |
+--------------+--------------+
|  id          |  int         |
|  name        |  varchar     |
|  email       |  varchar     |
|  phone       |  varchar     |
|  gender      |  tinyint     |
|  passwd      |  varchar     |
|  birthday    |  datetime    |
|  province    |  varchar     |
+--------------+--------------+
id为该表主键。
该表包含用户的编号、姓名、邮箱、性别、密码、生日、所在省份
```

现因业务需要，存在一个查询任务：查出在1980-01-01到2000-01-01之间出生的，且邮箱以字母 c 开头的用户的id、姓名、邮箱和生日，查询结果以邮箱排序（升序），邮箱相同的用户按生日排序（升序）。

开发人员编写了如下SQL查询语句

```
SELECT au.id, au.name, au.email, au.birthday FROM index_test.app_user au
WHERE au.email LIKE "c%"
	AND au.birthday BETWEEN "1980-01-01" AND "2000-01-01"
ORDER BY au.email, au.birthday;
```

', '', now(), now(), '', 1, 1);

# 获取刚插入的题目main_id
SET @main_id := (SELECT id
                 from sqlexercise.main_question
                 where title = 'OceanBase查询优化');

# 在sub_question表中插入小题信息，其中answer字段用来存需要被优化的查询语句
insert into sqlexercise.sub_question(main_id, description, answer, created_at, updated_at, difficulty, ordered)
VALUES (@main_id, '请在app_user表上创建合适的索引，使题目中的查询语句的性能提升至少5倍。只需编写create index xxx语句。',
        'SELECT au.id, au.name, au.email, au.birthday FROM index_test.app_user au WHERE au.email LIKE "c%" AND au.birthday BETWEEN "1980-01-01" AND "2000-01-01" ORDER BY au.email, au.birthday;',
        now(), now(), 1, 0);