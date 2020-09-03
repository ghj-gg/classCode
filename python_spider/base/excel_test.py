#!/user/bin/env python3
# -*- coding:utf-8 -*-
__author__ = "ghj"

'''
excel test
'''

import excel_util
# import openpyxl
import random

# ------excel test------
# def write_excel():
#     # 获取workbook对象
#     workbook = openpyxl.Workbook()
#     # 获取sheet对象
#     active_sheet = workbook.get_active_sheet()
#     # 数据操作
#     active_sheet.append(["第一季度", "第二季度", "第三季度", "第四季度"])
#     for i in range(1, 10):
#         active_sheet.append([i*random.randint(1, 10), i*random.randint(1, 10), i*random.randint(1, 10),
#                              i*random.randint(1, 10)])
#     # 文件保存
#     workbook.save(filename="/test/excel_test.xlsx")
#
# if __name__ == "__main__":
#     # print(random.randint(1, 10))
#     write_excel()

# ------excel util------
def write_excel():
    header = ["第一季度", "第二季度", "第三季度", "第四季度"]
    file_path = "/test/excel_test1.xlsx"
    body = list()
    for item in range(1, 10):
        line = list()
        for i in range(1, len(header)+1):
            line.append(i * random.randint(1, 10))
        body.append(line)
    excel_util.create_excel(header, body, file_path)

if __name__ == "__main__":
    write_excel()
