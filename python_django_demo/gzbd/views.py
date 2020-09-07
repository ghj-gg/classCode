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




