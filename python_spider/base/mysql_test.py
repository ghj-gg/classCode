#!/user/bin/env python3
# -*- coding:utf-8 -*-
__author__ = "ghj"

'''
mysql test
'''

# import pymysql
import mysql_util

# ------mysql test------
# 插入
# def insert_resource():
#     conn = pymysql.connect(host='localhost', user='root', passwd='123456', db='test', charset='utf8')
#     cur = conn.cursor()
#     # cur.execute("insert into resource (resource_uri,resource_name,permission) values ('aa','bb','cc')")
#     result = cur.execute("insert into resource (resource_uri,resource_name,permission) values ('aa','bb','cc')")
#     print(result)  # 1
#     conn.commit()
#     cur.close()
#     conn.close()
#
# # 查询
# def query_resource():
#     conn = pymysql.connect(host='localhost', user='root', passwd='123456', db='test', charset='utf8')
#     cur = conn.cursor()
#     result = cur.execute("select * from resource")
#     print(result)  # 13
#     print(cur.fetchall())  # 获取到查询的每一条 放在元祖里面，元祖里面又是一个元祖
#     conn.commit()
#     cur.close()
#     conn.close()
#
# if __name__ == "__main__":
#     # insert_resource() #插入
#     query_resource()  # 查询

# ------封装------
def insert_resource():
    conn, cur = mysql_util.get_connect_cursor()
    sql = "insert into resource (resource_uri,resource_name,permission) values ('aa1','bb1','cc1')"
    mysql_util.execute_insert_update_delete(cur, sql)
    mysql_util.commit_(conn)
    mysql_util.close_connect_cursor(conn, cur)

def query_resource():
    conn, cur = mysql_util.get_connect_cursor()
    sql = "select * from resource"
    result = mysql_util.execute_query(cur, sql)
    print(result)
    mysql_util.close_connect_cursor(conn, cur)
if __name__ == "__main__":
    # insert_resource()
    query_resource()
