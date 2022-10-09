# coding=utf-8
import json
import sys

import demjson
import jieba
import jieba.analyse

from news import New
from tool import db



"""
"""
# tf-idf 关键词
def PythonFunc2():
    records = db.session.query(New).limit(1000).all()
    text = ""
    for i in records:
        # print(i.intro)  # 每一行
        if i.maintxt is not None:   #需要修改词云分词的字段
            text = text + i.maintxt

    result = jieba.analyse.extract_tags(text, topK=50, withWeight=True,
                               allowPOS=(
                                   'n', 'f', 's', 'nr', 'ns', 'nt', 'nw', 'nz', 'v', 'vd', 'vn', 'a', 'PER',
                                   'LOC', 'ORG'))


    return dict(result)

def PythonFunc():
    result = {}
    word_count_sorted = []
    records = db.session.query(New).limit(1000).all()
    text = ""
    for i in records:
        # print(i.intro)  # 每一行
        if i.maintxt is not None:   #需要修改词云分词的字段
            text = text + i.maintxt

    word_count = dict()
    words = jieba.cut(text)
    for word in words:
        if word not in word_count:
            word_count[word] = 1
        else:
            word_count[word] += 1

    word_count_sorted = sorted(word_count.items(), key=lambda x: x[1], reverse=True)
    word_count_sorted = filter(lambda x: len(x[0]) > 1, word_count_sorted)

    result = json.dumps(dict(word_count_sorted), ensure_ascii=False)
    # print(result)
    # result = json.dumps(dict(word_count_sorted), ensure_ascii=False)
    # print(json.loads(result))
    # result = json.loads(result)
    return result

if __name__ == '__main__':
    # subject = sys.argv[1]
    # scores = demjson.decode(sys.argv[2])
    # subject = '理学'
    # scores = {'scores': [55,55,155]}
    # 调用生成json函数
    # createJson(subject, scores['scores'])
    result = PythonFunc2()
    print(result)