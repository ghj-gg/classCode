#!/user/bin/env python3
# -*- coding:utf-8 -*-
__author__ = "ghj"

'''
gzbd 数据爬取
'''

import requests
from bs4 import BeautifulSoup
import re

__wjw_regin = "四川"
__wjw_domain = "http://wsjkw.sc.gov.cn"
__wjw_base_url = "/scwsjkw/gzbd01/ztwzlmgl.shtml"
__wjw_page_count = 10

# 获取gzbd所有数据
def gzbd_all_data():
    all_data = []
    # 创建 新闻列表url & 获取列表页的数据
    news_page_url = __wjw_domain
    for i in range(1, __wjw_page_count + 1):
        if i == 1:
            news_page_url += __wjw_base_url
        else:
            l = __wjw_base_url.split(".")
            l.insert(1, "_%d." % (i,))
            news_page_url += "".join(l)
        # print("Visit url: %s" % (news_page_url,))
        news_list = news_page_data(news_page_url)
        # print("      %s" % (news_list,))
        all_data += news_list
        # 置空
        news_page_url = __wjw_domain
    # print(all_data)
    return all_data

# 爬取新闻列表页数据
def news_page_data(url):
    news_list = []

    r = requests.get(url)
    r.encoding = r.apparent_encoding

    bs = BeautifulSoup(r.text, 'html.parser')
    li_list = bs.find(name="div", attrs={"class": "contMain fontSt"}).find_all(name='li')
    for li in li_list:
        child_span = li.findChildren("span", recursive=False)[0]
        child_a = li.findChildren("a", recursive=False)[0]
        new_page_url = __wjw_domain + child_a.get("href")
        new_dict = new_page_data(new_page_url)
        new_dict["日期"] = child_span.get_text()
        new_dict["地区"] = __wjw_regin
        news_list.append(new_dict)
    # print(news_list)
    return news_list

def new_page_data(url):
    # 装载新闻页数据
    new_dict = {}
    # requests获取页面内容
    r = requests.get(url)
    r.encoding = r.apparent_encoding
    # bs4解析页面标签
    bs = BeautifulSoup(r.text, "html.parser")
    span_list = bs.find_all(name="span", attrs={"style": "font-size: 12pt;"})
    line = span_list[1].get_text()
    # 正则表达式提取数据，并装载到dict
    line_re = r'全省累计报告新型冠状病毒肺炎确诊病例(\d+)例\(其中境外输入(\d+)例\），' \
                  r'累计治愈出院(\d+)例，死亡(\d+)例，目前在院隔离治疗(\d+)例，(\d+)人尚在接受医学观察'
    rea = re.search(line_re, line)
    if rea:
        new_dict["确诊数"] = rea.group(1)
        new_dict["境外输入数"] = rea.group(2)
        new_dict["治愈数"] = rea.group(3)
        new_dict["死亡数"] = rea.group(4)
        new_dict["隔离数"] = rea.group(5)
        new_dict["观察数"] = rea.group(6)
    return new_dict

if __name__ == "__main__":
    # url = "http://wsjkw.sc.gov.cn/scwsjkw/gzbd01/2020/9/3/fe0eb6e3101d4709a9bbd27f5a12ae78.shtml"
    # url = "http://wsjkw.sc.gov.cn/scwsjkw/gzbd01/ztwzlmgl.shtml"
    # gzbd_all_data()
    pass


