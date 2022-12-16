# -*- codeing = utf-8 -*-
# Author: Tesla Tech
# QQ:  1693353672
# @Time: 2022/10/2 22:25
# @Author: Administrator
# @File: lyric.py
# @Desc:
# 歌词的表
import json
import jieba
from flask_sqlalchemy import SQLAlchemy
db = SQLAlchemy()

class Lyric(db.Model):
    __tablename__ = 'movie_detail'  # 表名
    douban_id = db.Column(db.Integer, primary_key=True)
    brief_instruction = db.Column(db.TEXT)

def getWords2():
    records = db.session.query(Lyric).limit(5000).all()
    text = ""
    for i in records:
        # print(i.intro)  # 每一行
        if i.brief_instruction is not None:   #需要修改词云分词的字段
            text = text + i.brief_instruction

    word_count = dict()
    words = jieba.cut(text)
    for word in words:
        if word not in word_count:
            word_count[word] = 1
        else:
            word_count[word] += 1

    # 词频顺序进行排序，以元祖形式存储
    word_count_sorted = sorted(word_count.items(), key=lambda x: x[1], reverse=True)
    # print(word_count_sorted)
    word_count_sorted = word_count_sorted[:1000]
    # 过滤结果中的标点符号和单词
    word_count_sorted = filter(lambda x: len(x[0]) > 1, word_count_sorted)
    # print(word_count_sorted)

    # 过滤到全部的数字
    word_count_sorted = filter(lambda ch: ch[0].isdigit, word_count_sorted)
    # 元组转json
    result = json.dumps(dict(word_count_sorted), ensure_ascii=False)
    # result = json.dumps(dict(word_count_sorted), ensure_ascii=False)
    # print(json.loads(result))
    result = json.loads(result)
    # result = result[:100]
    return result