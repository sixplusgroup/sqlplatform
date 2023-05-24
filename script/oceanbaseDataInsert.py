import random
import string
import datetime
import pymysql
from faker import Faker

'''
此Python脚本文件用于随机生成OceanBase题目所需的数据，并将数据插入对应的表中
'''


# count-表示生成count个模拟用户信息
# step-表示每生成step个时打印提示信息
def generate_user(count, step):
    fake = Faker(locale="zh_cn")
    user_list = []
    a = string.ascii_letters + string.digits
    for i in range(1, count + 1):
        user_dic = {
            'name': fake.name(),
            'email': fake.ascii_free_email(),
            'phone': fake.phone_number(),
            'gender': random.randint(0, 1),
            'passwd': "".join(random.sample(a, 18)),
            'birthday': fake.date(pattern="%Y-%m-%d", end_datetime=None),
            'province': fake.province()
        }
        user_list.append(user_dic)
        if i % step == 0:
            print("\r", '已经生成 {} 条记录...'.format(i), end="", flush=True)
    return user_list


def generate_insert_sql(user_list, table_name):
    sql = "insert into " + table_name + "(name, email, phone, gender, passwd, birthday, province) values "
    for i in range(len(user_list)):
        user = user_list[i]
        sql += "('" + user['name'] + "', '" + user['email'] + "', '" + user['phone'] + "', " + str(
            user['gender']) + ", '" + \
               user['passwd'] + "', '" + user['birthday'] + "', '" + user['province'] + "')"
        if i < len(user_list) - 1:
            sql += ", "
    return sql


def run_sql(sql):
    # 建立数据库连接
    db = pymysql.connect(host='172.29.4.60', user='root', passwd='', db='index_test', port=2881)
    # 获取游标对象
    cursor = db.cursor()
    # 执行SQL语句
    cursor.execute(sql)
    # 关闭不使用的游标对象
    cursor.close()
    # 提交
    db.commit()
    # 关闭数据库连接
    db.close()


# 直接运行main即可
if __name__ == '__main__':
    # 表的总个数
    total_table_num = 15
    # 遍历，向每张表中分别插入数据
    for i in range(total_table_num):
        # 目标表名，即要往哪个表插数据
        table_name = "app_user_" + str(i)
        # 一次运行要插入的总数据量
        total = 200000
        # 每次insert的数据量。例如，total = 200，count_of_a_round = 10，则表示总共要插入200条，每次插入10条，分20次完成
        count_of_a_round = 20000
        # 计算需要几次
        rounds = int(total / count_of_a_round)
        print("开始向表 " + table_name + " 中插入数据，共需 " + str(rounds) + " 次\n")
        for j in range(rounds):
            print("========== 表 " + table_name + " 第 " + str(j + 1) + "/" + str(rounds) + " 次============")
            user_list = []
            begin = datetime.datetime.now()

            print(str(begin) + " 开始生成模拟数据...")
            user_list.extend(generate_user(count_of_a_round, 1000))
            print('')
            end = datetime.datetime.now()
            print(str(end) + " 生成结束, 共花费时间: " + str(end - begin))

            print(str(datetime.datetime.now()) + " 开始写入...")
            sql = generate_insert_sql(user_list, table_name)
            run_sql(sql)
            print(str(datetime.datetime.now()) + " 写入完成.\n")
