#!/user/bin/env python3
# -*- coding: utf-8 -*-

__author__ = "ghj"

from django.http import HttpResponse, JsonResponse
from gzbd.models import *
import random
import datetime
from django.core import serializers
import json
from django.shortcuts import render
from common.result import *

def hello_world(request):
    return HttpResponse("Hello World!")

def insert_user(request):
    user = User(user_name="ghj" + str(random.randint(1, 10)), password="111111",
        create_date=datetime.datetime.now())
    # user.save()

    user = User.objects.get(id=1)
    print(type(user))
    user_json = serializers.serialize("json", [user])

    # return JsonResponse(user_json, safe=False)  # 获取数据库的值
    # return JsonResponse({"name": "ghj", "age": 22}, safe=False)  # 直接获取{"name": "ghj", "age": 22}
    return JsonResponse(json.loads(user_json), safe=False)  # 将user对象序列化后，反序列化

def index(request):
    contex = {}
    return render(request, "index.html", contex)

'''
http://127.0.0.1:8080/user/1 --- get
http://127.0.0.1:8080/user/2 --- delete
'''
def user(request, id):
    if request.method == "GET":
        user = User.objects.get(id=id)
        print(user.user_name)
        user_result = {}
        user_result["id"] = user.id
        user_result["user_name"] = user.user_name
        user_result["password"] = user.password
        user_result["user_email"] = user.user_email
        user_result["create_date"] = user.create_date
        return JsonResponse(user_result, safe=False)
    elif request.method == "DELETE":
        User.objects.filter(id=id).delete()
        return JsonResponse(Result(status=200, message="delete success").result(), safe=False)
    else:
        return JsonResponse(Result(status=200, message="No opration for user").result(), safe=False)

'''
http://127.0.0.1:8080/user?userName=ghj9&password=111111 --- get
http://127.0.0.1:8080/user --- post
{"user_name":"ghj","password":"111111"}
http://127.0.0.1:8080/user --- put
{"id":"1","user_name":"ghj","password":"111111"}
'''
def user_(request):
    if request.method == "GET":
        user_name = request.GET.get("userName")
        password = request.GET.get("password")
        print("user_")
        user = User.objects.filter(user_name=user_name, password=password).first()
        if user:
            user_result = {}
            user_result["id"] = user.id
            user_result["user_name"] = user.user_name
            user_result["password"] = user.password
            user_result["user_email"] = user.user_email
            user_result["create_date"] = user.create_date
            return JsonResponse(user_result, safe=False)
        else:
            return JsonResponse(Result(status=500, message="No user find").result(), safe=False)
    elif request.method == "POST":
        # 获取查询参数、form表单参数
        user_name = request.POST.get("userName")
        password = request.POST.get("password")
        # 获取json数据
        query = json.loads(request.body)
        user_name = query.get("userName")
        password = query.get("password")
        user = User(user_name=user_name, password=password, create_date=datetime.datetime.now())
        user.save()
        return JsonResponse(Result(status=200, message="Insert user").result(), safe=False)
    elif request.method == "PUT":
        query = json.loads(request.body)
        id = query.get("userId")
        user_name = query.get("userName")
        user_email = query.get("userEmail")
        user = User.objects.get(id=id)
        user.user_name = user_name
        user.user_email = user_email
        user.save()
        return JsonResponse(Result(status=200, message="Update success").result(), safe=False)
    else:
        return JsonResponse(Result(status=200, message="No opration for user").result(), safe=False)

'''
http://127.0.0.1:8080/gzbd
'''
def gzbd(request):
    gzbds = Epidemic.objects.all()[0:7]
    gzbd_list = []
    for item in gzbds:
        epidemic = {}
        epidemic["id"] = item.id
        epidemic["region"] = item.region
        epidemic["date"] = item.date
        epidemic["diagnosis"] = item.diagnosis
        epidemic["overseas_import"] = item.overseas_import
        epidemic["cure"] = item.cure
        epidemic["death"] = item.death
        gzbd_list.append(epidemic)
    return JsonResponse(gzbd_list, safe=False)

'''
页面跳转
'''
def test_index(request):
    contex = {}
    contex["name"] = "ghj"
    contex["name_list"] = ["ghJ", "ghY"]
    contex["person"] = {"name": "ghj", "age": 21}
    contex["person1"] = {"name": "ghj", "age": 21}
    contex["birthday"] = datetime.datetime.now()
    contex["number"] = '1024'
    contex["url"] = 'http://www.baidu.com'
    contex["isman"] = True
    contex["age"] = 20
    return render(request, "test/index.html", contex)

def index_simple(request):
    contex = {}
    return render(request, "indexSimple.html", contex)

def common_dashboard(request):
    contex = {}
    return render(request, "common/dashboard.html", contex)

def account_login(request):
    contex = {}
    return render(request, "account/login.html", contex)

def account_registerVue(request):
    contex = {}
    return render(request, "account/registerVue.html", contex)

def account_profile(request):
    contex = {}
    return render(request, "account/profile.html", contex)

def account_users(request):
    contex = {}
    return render(request, "account/users.html", contex)

def account_userAdd(request):
    contex = {}
    return render(request, "account/userAdd.html", contex)

def account_userEdit(request):
    contex = {}
    return render(request, "account/userEdit.html", contex)

def account_roles(request):
    contex = {}
    return render(request, "account/roles.html", contex)




