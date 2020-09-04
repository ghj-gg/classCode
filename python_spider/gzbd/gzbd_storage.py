#!/user/bin/env python3
# -*- coding:utf-8 -*-
__author__ = "ghj"

'''
gzbd 数据存储
'''

import mysql_util, excel_util, gzbd.gzbd_spider

def storage_excel():
    # 获取所有数据
    gzbd_all_data = gzbd.gzbd_spider.gzbd_all_data()
    header = ["地区", "日期", "确诊数", "境外输入数", "治愈数", "死亡数", "隔离数", "观察数"]
    body = list()
    for item in gzbd_all_data:
        line = []
        line.append(item["地区"])
        line.append(item["日期"])
        line.append(item.get("确诊数", None))
        line.append(item.get("境外输入数", None))
        line.append(item.get("治愈数", None))
        line.append(item.get("死亡数", None))
        line.append(item.get("隔离数", None))
        line.append(item.get("观察数", None))
        body.append(line)
    file_path = "/test/gzbd_data.xlsx"
    excel_util.create_excel(header, body, file_path)

def storage_mysql():
    # 获取所有数据
    gzbd_all_data = gzbd.gzbd_spider.gzbd_all_data()

    for item in gzbd_all_data:
        epidemic_date = item["日期"]
        # build sql语句：根据时间查询，插入语句
        query_sql = "select * from gzbd_epidemic where date = %s" % (epidemic_date, )
        insert_sql = "insert into gzbd_epidemic (region, date, diagnosis, overseas_import, cure, " \
                     "death, therapy, observation) values ('%s', '%s', %s, %s, %s, %s, %s, %s)" % \
                     (item["地区"], item["日期"], item.get("确诊数", None), item.get("境外输入数", None), item.get("治愈数", None),
                      item.get("死亡数", None), item.get("隔离数", None), item.get("观察数", None))
        insert_sql = insert_sql.replace("None", "Null")
        print(insert_sql)
        # 查询数据，判断是否需要插入
        conn, cur = mysql_util.get_connect_cursor()
        result = mysql_util.execute_(query_sql)
        if len(result) == 0:
            # 插入数据
            result = mysql_util.execute_(insert_sql)
            if result > 0:
                print("ok")
            else:
                print("error")

def get_gzbd_data():
    sql = "select * from gzbd_epidemic order by date desc"
    return mysql_util.execute_(sql)

if __name__ == "__main__":
    # storage_mysql()
    # storage_excel()
    pass


