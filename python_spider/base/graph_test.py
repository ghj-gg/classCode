#!/user/bin/env python3
# -*- coding:utf-8 -*-
__author__ = "ghj"

'''
graph test
'''

import plotly

def draw_line_graph():
    # 准备图轨数据
    trace_l = plotly.graph_objs.Scatter(
        x=[1, 2, 3, 4],
        y=[32, 44, 11, 56],
        name="散点图",
        mode="markers"
    )
    trace_2 = plotly.graph_objs.Scatter(
        x=[1, 2, 3, 4],
        y=[12, 43, 1, 56],
        name="折线图"
    )
    line_data = [trace_l, trace_2]
    # 设置画布布局
    layout = plotly.graph_objs.Layout(title="折线图测试", xaxis={"title": "x"}, yaxis={"title": "y"})
    # 融合图轨数据和布局
    figure = plotly.graph_objs.Figure(data=line_data, layout=layout)
    # 输出
    plotly.offline.plot(figure, filename="/test/line_graph.html", image="png")

def draw_bar_graph():
    # 准备图轨数据
    trace_l = plotly.graph_objs.Bar(
        x=[1, 2, 3, 4],
        y=[32, 44, 11, 56],
        name="第一产业"
    )
    trace_2 = plotly.graph_objs.Bar(
        x=[1, 2, 3, 4],
        y=[32, 15, 11, 90],
        name="第二产业"
    )
    trace_3 = plotly.graph_objs.Bar(
        x=[1, 2, 3, 4],
        y=[32, 4, 10, 66],
        name="第三产业"
    )
    trace_4 = plotly.graph_objs.Bar(
        x=[1, 2, 3, 4],
        y=[32, 54, 21, 56],
        name="第四产业"
    )
    bar_data = [trace_l, trace_2, trace_3, trace_4]
    # 设置画布布局
    layout = plotly.graph_objs.Layout(title="柱状图测试", xaxis={"title": "季度"}, yaxis={"title": "产值"})
    # 融合图轨数据和布局
    figure = plotly.graph_objs.Figure(data=bar_data, layout=layout)
    # 输出
    plotly.offline.plot(figure, filename="/test/bar_graph.html", image="png")

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
    # draw_line_graph()  # 折线图散点图
    # draw_bar_graph()  # 柱状图
    draw_pie_graph()
