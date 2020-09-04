#!/user/bin/env python3
# -*- coding:utf-8 -*-
__author__ = "ghj"

'''
python base demo
'''

import re, math
# import utils.mysql_util
import mysql_util

# ------输入输出------
name = input()
print(name)
a = input()
print(a)
a = 11
b = "ghj"
print(a)  # 11
print("hello world!")
print("hello", "world", "ghj")
print("hello %s" % a)  # %s占位符    hello 11
print("hello %s" % (a, ))  # (a, )表示元祖里只有一个元素    hello 11
print("hello %s - %s" % (a, b))  # hello 11 - ghj

# ------数据类型--------
a = 11
print("a = %s ,数据类型： %s" % (a, type(a)))  # type()获取数据类型     a = 11 ,数据类型： <class 'int'>
a = 11.11
print("a = %s ,数据类型： %s" % (a, type(a)))  # a = 11.11 ,数据类型： <class 'float'>
a = "a b c d e f g"
print("a = %s ,数据类型： %s" % (a, type(a)))  # a = a b c d e f g ,数据类型： <class 'str'>
a = ['aaa', 123, 12.3]
print("a = %s ,数据类型： %s" % (a, type(a)))  # a = ['aaa', 123, 12.3] ,数据类型： <class 'list'>
a = ("c d s a", 32, 33.3)
print("a = %s ,数据类型： %s" % (a, type(a)))  # tuple初始化之后元素不可改变 a = ('c d s a', 32, 33.3) ,数据类型： <class 'tuple'>
a = {"name": "ghj"}
print("a = %s ,数据类型： %s" % (a, type(a)))  # 键值对 a = {'name': 'ghj'} ,数据类型： <class 'dict'>
a = {"name": "ghj", "age": "21"}
print("a = %s ,数据类型： %s" % (a, type(a)))  # a = {'name': 'ghj', 'age': '21'} ,数据类型： <class 'dict'>
a = None
print("a = %s ,数据类型： %s" % (a, type(a)))  # a = None ,数据类型： <class 'NoneType'>
a = True
print("a = %s ,数据类型： %s" % (a, type(a)))  # a = True ,数据类型： <class 'bool'>
a = True or False
print("a = %s ,数据类型： %s" % (a, type(a)))  # a = True ,数据类型： <class 'bool'>
a = True and False
print("a = %s ,数据类型： %s" % (a, type(a)))  # a = False ,数据类型： <class 'bool'>
a = not True
print("a = %s ,数据类型： %s" % (a, type(a)))  # a = False ,数据类型： <class 'bool'>
a = 1 > 2
print("a = %s ,数据类型： %s" % (a, type(a)))  # a = False ,数据类型： <class 'bool'>
a = set(["abc", True, None, 212, 22.4])
print("a = %s ,数据类型： %s" % (a, type(a)))  # a = {True, 'abc', None, 212, 22.4} ,数据类型： <class 'set'>

# ------运算符--------
print((2+14/12)*3)  # 9.5
print(5//2)  # 地板除（取整） 2
print(5 % 2)  # 取余 1
print(2 ** 3)  # 乘方 8

# ------字符串--------
print(u'郭慧洁')  # 郭慧洁
print(r'郭慧洁')  # 郭慧洁
print(b'a b c d')  # b'a b c d'

# ------ASCII转换--------
print("98-->%s;a-->%s" % (chr(98), ord('a')))  # 98-->b;a-->97

# ------encode && decode--------
print("a b c d".encode("ascii"))  # b'a b c d'  转换为bytes类型
# print("郭慧洁".encode("ascii"))  报错，ascii只定义了127个字符，中文无法解析
print("郭慧洁".encode("utf-8"))  # b'\xe9\x83\xad\xe6\x85\xa7\xe6\xb4\x81'  转换为bytes类型，无法显示为ascii字符的字节，用\x##显示
print(b'a b c d'.decode("ascii"))  # a b c d  将bytes类型按照ascii解码
print(b'\xe9\x83\xad\xe6\x85\xa7\xe6\xb4\x81'.decode("utf-8"))  # 郭慧洁 将bytes类型按照utf-8解码

# ------function--------
print(len("a b c d e f g"))  # 长度13 对于str计算字符数
print(len("郭慧洁"))  # 长度3 对于str计算字符数
print(len("aaa".encode("utf-8")))  # 长度3 对于bytes计算字节数
print(len("郭慧洁".encode("utf-8")))  # 长度9 对于bytes计算字节数
print("a b c u a b c u a b c u".replace("c u", "--"))  # 替换   a b -- a b -- a b --
print("a b c u a b c u a b c u".find("c u"))  # 4 查找c u第一次出现的下标 没有-1
print("a b c u a b c u a b c u".rfind("c u"))  # 20 查找c u最后一次出现的下标 没有-1
print(" ".isspace())  # True 判断整个字符串是否是空格
print("abc ".isspace())  # False
print(" abc".isspace())  # False
print("a bc".isspace())  # False

# ------字符串格式化--------
print("%s ---- %2d ---- %02d" % (11, 4, 3))  # 11 ----  4 ---- 03   不足两位：%2d补空格 %02d补0
print("%s ---- %2d ---- %04d" % (11, 4, 3))  # 11 ----  4 ---- 0003
print("%f ---- %.2f" % (32.2323, 134.3467245))  # 32.232300 ---- 134.35   %.2f四舍五入保留两位小数
print("%x" % 333)  # 格式化为十六进制  14d
print("%d %% %d" % (3, 5))  # 3 % 5    %%转义
# range()给定范围，遍历range，使用for in语句，定义x变量，x变量取变量里的每一个值，把x的值以%d的方式加到list里边
print(list("%d" % x for x in range(1, 10)))  # ['1', '2', '3', '4', '5', '6', '7', '8', '9']
print("Hi {0},成绩提高了{1:.1f}%".format("郭慧洁", 1.234))  # Hi 郭慧洁,成绩提高了1.2%
print("Hi {0},成绩提高了{1}%".format("郭慧洁", "%.1f" % 1.234))  # Hi 郭慧洁,成绩提高了1.2%
print("=".join(["c d s d f", "a s d f", "s d f f a s"]))  # c d s d f=a s d f=s d f f a s   字符串拼接

# ------正则表达式--------
email_re = "^[\w-]+(\.[\w-]+)*@[\w-]+(\.[\w-]+)+$"
if re.match(email_re, "ghj@qq.com"):
    print("ok")
else:
    print("error")
ma = re.match("c", "abcd")
print(ma)  # None
sea = re.search("c", "abcd")
print(sea)  # <re.Match object; span=(2, 3), match='c'>

# ------切分字符串--------
print("a b c".split(" "))  # ['a', 'b', 'c']
print("a bc".split(" "))  # ['a', 'bc']
print(re.split(r'\s+', "a b c"))  # ['a', 'b', 'c']
print(re.split(r"[\s\,\;]+", "a,b;; c  d"))  # ['a', 'b', 'c', 'd']

# ------分组--------
math = re.match(r'^(\d{3})-(\d{3,8})$', "020-123456")
print(math.group())  # 020-123456
print(math.group(0))  # 020-123456
print(math.group(1))  # 020
print(math.group(2))  # 123456
print(math.groups())  # ('020', '123456')

# ------分组提取数字--------
new_line = r'截至9月2日0时，全省累计报告新型冠状病毒肺炎确诊病例653例(其中境外输入112例），' \
    r'累计治愈出院626例，死亡3例，目前在院隔离治疗24例，964人尚在接受医学观察'
new_line_re = r'^截至9月2日0时，全省累计报告新型冠状病毒肺炎确诊病例(\d+)例\(其中境外输入(\d+)例\），' \
    r'累计治愈出院(\d+)例，死亡(\d+)例，目前在院隔离治疗(\d+)例，(\d+)人尚在接受医学观察$'
new_line_math = re.match(new_line_re, new_line)
print(new_line_math.group(0))
print(new_line_math.group(1))
print(new_line_math.group(2))
print(new_line_math.group(3))
print(new_line_math.group(4))
print(new_line_math.group(5))
print(new_line_math.group(6))
new_line_compile = re.compile(new_line_re)
print(re.search(new_line_compile, new_line).group(1))
print(re.search(new_line_compile, new_line).group(2))
print(re.search(new_line_compile, new_line).group(3))
print(re.search(new_line_compile, new_line).group(4))
print(re.search(new_line_compile, new_line).group(5))
print(re.search(new_line_compile, new_line).group(6))

# ------贪婪匹配--------
print(re.match(r"^(\d+?)(0*)$", "102300").groups())   # ('1023', '00')

# ------list(有序的集合，中括号定义，元素类型可不一致，可以随时添加和删除其中的元素)--------
l1 = ["abc", 32, 33.2, None, True]
print(l1)  # ['abc', 32, 33.2, None, True]
l2 = list(range(1, 10))
print(l2)  # [1, 2, 3, 4, 5, 6, 7, 8, 9]
l1.append("ghj")
print(l1)  # ['abc', 32, 33.2, None, True, 'ghj']  在最后添加
l1.insert(2, "g u o h u i j i e")
print(l1)  # ['abc', 32, 'g u o h u i j i e', 33.2, None, True, 'ghj']  在指定位置添加
l1.pop()
print(l1)  # ['abc', 32, 'g u o h u i j i e', 33.2, None, True]  删除最后一个
l1.pop(2)
print(l1)  # ['abc', 32, 33.2, None, True] 删除指定位置
l1.append(l2)
print(l1)  # ['abc', 32, 33.2, None, True, [1, 2, 3, 4, 5, 6, 7, 8, 9]] 将l2作为一个元素添加到l1
l1 += l2
print(l1)  # ['abc', 32, 33.2, None, True, 1, 2, 3, 4, 5, 6, 7, 8, 9] 合并 将l1和l2合并
l1[0] = "ghj"
print(l1)  # ['ghj', 32, 33.2, None, True, [1, 2, 3, 4, 5, 6, 7, 8, 9], 1, 2, 3, 4, 5, 6, 7, 8, 9]

# ------tuple(元祖，小括号定义，有序集合，一旦初始化后不可变，在元祖定义一个元素为list，则这个list可改变)--------
l3 = list(range(1, 10))
t1 = ("aaa", 12, 12.2, True, None, l3)
l3[1] = 22
print(t1)  # ('aaa', 12, 12.2, True, None, [1, 22, 3, 4, 5, 6, 7, 8, 9])
t2 = tuple(range(1, 10))
print(t2)  # (1, 2, 3, 4, 5, 6, 7, 8, 9)
# 定义一个元素的元祖，元素后面追加“，”，以免误解成数学计算意义上的括号
print((1))
print((1,))

# ------dict（大括号定义，逗号分隔，键值对）--------
d = {"name": "ghj", "age": 21}
print(d)  # {'name': 'ghj', 'age': 21}
print(d.get("name"))  # ghj
print(d.get("name1"))  # None
print(d.get("name1", "aaa"))  # aaa  值不存在给定一个初始值
d["name"] = "g u o h u i j i e"
d["aaa"] = "g h j y y y"
print(d)  # {'name': 'g u o h u i j i e', 'age': 21, 'aaa': 'g h j y y y'}
d.pop("aaa")
print(d)  # {'name': 'g u o h u i j i e', 'age': 21}
print(len(d))  # 2

# ------set(存储一组key集合，无序，不存储value，没有重复元素，自动过滤重复key，key必须为不可变对象)--------
s = set(["abc", True, None, 212, 22.4])
print(s)  # {True, None, 212, 22.4, 'abc'}
s.add("aaa")
print(s)  # {True, 'aaa', None, 212, 22.4, 'abc'}
s.pop()
print(s)  # {None, 'aaa', 212, 22.4, 'abc'}
# s.remove(1)  报错
# print(s)  # {None, 212, 22.4, 'aaa'}
s.remove("aaa")
print(s)  # {'abc', None, 212, 22.4}
s2 = {"ccc", 212, 22.53, "abc"}
print(s & s2)  # {212, 'abc'}  共同
print(s | s2)  # {'abc', True, None, 'ccc', 212, 22.4, 22.53} 或

# ------判断语句--------
a = 20
# b
if a < 10:
    print("a")
elif 10 <= a <= 20:
    print("b")
else:
    print("c")
b = 10
# 重叠的情况 a
if b <= 10:
    print("a")
elif 10 <= b <= 20:
    print("b")
else:
    print("c")

# ------三目运算符--------
a, b, c = 1, 3, 2
print(a if b > c else c)  # 1

# ------循环语句--------
ll = list(range(1, 10))
for i in ll:
    print(i)  # 1 2 3 4 5 6 7 8 9
i = 0
while (i < 10):
    print(i)  # 0 1 2 3 4 5 6 7 8 9
    i += 1

# ------函数--------
def test(a):
    a += 3
    return a
b = test(3)
print(b)  # 6
print(test(3))  # 6
if __name__ == "__main__":
    print(test(5))  # 8

# ------位置参数--------
def test_2(x, y="ghj"):
    print(x, y)
if __name__ == "__main__":
    test_2("holle", "g")  # holle g
    test_2("holle")  # holle ghj

# ------可变参数--------
def test_3(*num):
    count = 0
    for i in num:
        count += i
    return count
if __name__ == "__main__":
    print(test_3())  # 0
    print(test_3(*list(range(1, 9))))  # 36
    print(test_3(1, 2, 3, 4, 5))  # 15

# ------可变关键字参数--------
def test_4(name, **kv):
    if "city" in kv:
        print("name:%s, city:%s" % (name, kv.get("city")))
    else:
        print("name:%s, city:%s" % (name, "chengdu"))
if __name__ == "__main__":
    test_4("ghj", **{"age": 21})  # name:ghj, city:chengdu
    test_4("ghj", **{"age": 21, "city": "cd"})  # name:ghj, city:cd

# ------命名关键字参数--------
def test_5(name, *, city):
    print("name:%s, city:%s" % (name, city))
if __name__ == "__main__":
    test_5("ghj", city="chengdu")  # name:ghj, city:chengdu

# ------位置参数或关键字参数--------
def test_6(name, *, city):
    if not isinstance(name, (str,)):
        raise TypeError("Type error!")
    print("name:%s, city:%s" % (name, city))
if __name__ == "__main__":
    test_6(111, city="chengdu")  # TypeError: Type error!   给定参数不是str 报错

# ------内置函数--------
print(int("22"))  # 数据类型转换函数，注意：如果定义变量名和函数名一样。则不会调用该函数，会报错  22
print(float("22.2"))  # 22.2
print(str(222))  # 222
print(abs(-1111))  # abs函数，求绝对值 1111
print(max(12, 23, 1, 345))  # max函数，求最大值 345
print(min(12, 45, 0, 3, -56))  # min函数，求最小值 -56
print(" aa bb cc ".strip())  # 字符串去前后空格 aa bb cc
print("['6k-8k']".strip('[\'\']'))  # 移除字符串头尾指定的字符 6k-8k
print(hex(12))  # hex函数，将十进制转换成十六进制 0xc
print(math.sqrt(3))  # 求平方根 1.7320508075688772
print(sum(range(1, 101)))  # 求和 5050
print(sum(list(range(101))))  # 5050
print("abcDEfg".capitalize())  # 将字符串第一个字符变成大写，其他小写 Abcdefg

