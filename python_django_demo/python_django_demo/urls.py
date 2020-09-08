#!/user/bin/env python3
# -*- coding: utf-8 -*-
__author__ = "ghj"

from django.contrib import admin
from django.urls import path, re_path
from gzbd import views

urlpatterns = [
    path('admin/', admin.site.urls),
    re_path(r'^helloWorld$', views.hello_world),
    re_path(r'^test/index$', views.test_index),
    # re_path(r'^user$', views.insert_user),

    # 业务逻辑处理
    # ----- user -----
    re_path(r'^user/(\d+)$', views.user),
    re_path(r'^user$', views.user_),

    # ------ gzbd -----
    re_path(r'^gzbd$', views.gzbd),

    # 页面跳转
    re_path(r'^index$', views.index),
    re_path(r'^indexSimple$', views.index_simple),
    re_path(r'^common/dashboard$', views.common_dashboard),
    re_path(r'^account/login$', views.account_login),
    re_path(r'^account/registerVue$', views.account_registerVue),
    re_path(r'^account/profile$', views.account_profile),
    re_path(r'^account/users$', views.account_users),
    re_path(r'^account/userAdd$', views.account_userAdd),
    re_path(r'^account/userEdit$', views.account_userEdit),
    re_path(r'^account/roles$', views.account_roles),
]
