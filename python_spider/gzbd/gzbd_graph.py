#!/user/bin/env python3
# -*- coding:utf-8 -*-
__author__ = "ghj"

'''
gzbd graph
'''

import plotly
import gzbd.gzbd_storage

def draw_line_graph():
    result = gzbd.gzbd_storage.get_gzbd_data()
    print(result)
    x = []
    y_diagnosis = []
    y_cure = []
    y_death = []
    for item in result:
        x.append(item[2])
        y_diagnosis.append(item[3])
        y_cure.append(item[5])
        y_death.append(item[6])

    # 准备图轨数据
    trace_l = plotly.graph_objs.Scatter(
        x=x,
        y=y_diagnosis,
        name="确诊数"
    )
    trace_2 = plotly.graph_objs.Scatter(
        x=x,
        y=y_cure,
        name="治愈数"
    )
    trace_3 = plotly.graph_objs.Scatter(
        x=x,
        y=y_death,
        name="死亡数"
    )
    line_data = [trace_l, trace_2, trace_3]
    # 设置画布布局
    layout = plotly.graph_objs.Layout(title="冠状病毒折线图", xaxis={"title": "时间"}, yaxis={"title": "数量"})
    # 融合图轨数据和布局
    figure = plotly.graph_objs.Figure(data=line_data, layout=layout)
    # 输出
    plotly.offline.plot(figure, filename="/test/gzbd_scatter.html", image="png")

def draw_bar_graph():
    result = gzbd.gzbd_storage.get_gzbd_data()
    x = []
    y_diagnosis = []
    y_cure = []
    y_death = []
    for item in result:
        x.append(item[2])
        y_diagnosis.append(item[3])
        y_cure.append(item[5])
        y_death.append(item[6])
    # 准备图轨数据
    trace_l = plotly.graph_objs.Bar(
        x=x,
        y=y_diagnosis,
        name="确诊数"
    )
    trace_2 = plotly.graph_objs.Bar(
        x=x,
        y=y_cure,
        name="治愈数"
    )
    trace_3 = plotly.graph_objs.Bar(
        x=x,
        y=y_death,
        name="死亡数"
    )
    bar_data = [trace_l, trace_2, trace_3]
    # 设置画布布局
    layout = plotly.graph_objs.Layout(title="冠状病毒折线图", xaxis={"title": "时间"}, yaxis={"title": "数量"})
    # 融合图轨数据和布局
    figure = plotly.graph_objs.Figure(data=bar_data, layout=layout)
    # 输出
    plotly.offline.plot(figure, filename="/test/gzbd_bar.html", image="png")

def draw_pie_graph():
    # 准备图轨数据
    trace_l = plotly.graph_objs.Pie(
        labels=["产品一", "产品二", "产品三", "产品四", "产品五"],
        values=[13.4, 21.4, 56.8, 23.5, 45,9]
    )
    pie_data = [trace_l]
    # 设置画布布局
    layout = plotly.graph_objs.Layout(title="饼图测试")
    # 融合图轨数据和布局
    figure = plotly.graph_objs.Figure(data=pie_data, layout=layout)
    # 输出
    plotly.offline.plot(figure, filename="/test/pie_graph.html", image="png")

if __name__ == "__main__":
    draw_line_graph()  # 折线图散点图
    # draw_bar_graph()  # 柱状图
    # draw_pie_graph()
