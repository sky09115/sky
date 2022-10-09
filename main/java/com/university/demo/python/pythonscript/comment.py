# -*- codeing = utf-8 -*-
# Author: Tesla Tech
# QQ:  1693353672
# @Time: 2022/10/2 22:25
# @Author: Administrator
# @File: comment.py
# @Desc:
# 歌曲评论的表
import json
import jieba
from flask_sqlalchemy import SQLAlchemy
db = SQLAlchemy()
class Comment(db.Model):
    __tablename__ = 'tb_comment'  # 表名
    id = db.Column(db.Integer, primary_key=True, autoincrement=True)
    songId = db.Column(db.String(255))
    nickname = db.Column(db.String(255))
    avatar = db.Column(db.String(255))
    content = db.Column(db.TEXT)
    likedCount = db.Column(db.Integer)
    isHot = db.Column(db.String(1))
    pubTime = db.Column(db.String(255))  #时间的处理

def getWords():
    records = db.session.query(Comment).limit(500).all()
    text = ""
    for i in records:
        # print(i.intro)  # 每一行
        if i.content is not None:   #需要修改词云分词的字段
            text = text + i.content

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

    # 过滤结果中的标点符号和单词
    word_count_sorted = filter(lambda x: len(x[0]) > 1, word_count_sorted)
    # print(word_count_sorted)

    # 元组转json
    result = json.dumps(dict(word_count_sorted), ensure_ascii=False)
    # result = json.dumps(dict(word_count_sorted), ensure_ascii=False)
    # print(json.loads(result))
    result = json.loads(result)
    return result