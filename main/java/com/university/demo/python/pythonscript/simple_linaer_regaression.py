# -*- coding: utf-8 -*-
# @Time    : 2020/02/28 下午 10:47
# @Author  : Alan D. Chen
# @FileName: simple_linaer_regaression.py
# @Software: PyCharm
import sys
import numpy as np
from tool.db import session

def fitSLR (x , y) :
    n = len(x)
    dim = 0
    mune = 0
    for i in range(0,len(x)):
        mune += (x[i] - np.mean(x))*(y[i] - np.mean(y))
        dim += (x[i] - np.mean(x))**2
    print("numerator:" , mune , "\n" , "dinominator:",dim)
    b1 = mune/float(dim)
    b0 = np.mean(y)/float(np.mean(x))

    return(b0,b1)

def predict(x , b0 ,b1):
    return(b0 + x*b1)

def linaer_regression_predict(p1, p2, n):
    print(p1)
    print(p2)

    # 输入专业，获取年份
    # x = [1, 3, 2, 1, 3]
    # 输入专业，获取国家线
    # y = [14, 24, 18, 17, 27]

    b0, b1 = fitSLR(p1, p2)
    print("intercept:", b0, "\n", "slope", b1)
    x_t = float(n)
    y_t = predict(x_t, b0, b1)
    return y_t
    # print("x_t:", x_t, "\n", "y_t:", y_t)

if __name__ == '__main__':
    # blockid = 69
    blockid = sys.argv[1]

    sql0 = "select max(time1) from tb_road where blockid='%s'" % blockid
    tt = session.execute(sql0)
    cur = tt.fetchall()
    for c in cur:
        time1 = c[0]

    sql = "select period, flow  from  tb_road where time1 like '%s%%' " \
          "and blockid='%s' order by period asc " % (time1, blockid)
    x = []
    w = []
    tt = session.execute(sql)
    cur = tt.fetchall()
    for c in cur:
        x.append(c[0])
        w.append(c[1])
    print(x)
    print(w)
    # print(cur)
    # w = [13, 3, 1, 11, 12, 7]
    # x = [1, 2, 3, 4, 5, 6, 7]
    m = max(x)
    w2 = linaer_regression_predict(x, w, m+1)
    w3 = linaer_regression_predict(x, w, m+2)
    w4 = linaer_regression_predict(x, w, m+3)

    x.append(m+1)
    x.append(m+2)
    x.append(m+3)

    r = []
    w.append(int(w2))
    w.append(int(w3))
    w.append(int(w4))
    ret = dict(v=w, x=x)
    # print(r)
    # print(int(w2))
    # print(int(w3))
    # print(int(w4))
    # result = '1'
    print(ret)


