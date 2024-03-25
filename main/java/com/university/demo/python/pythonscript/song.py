# -*- codeing = utf-8 -*-
# Author:  Tesla Tech
# QQ: 1693353672
# @Time :2022年10月2日22:29:01
# @Author: Administrator
# @File :song.py
from flask_marshmallow import Marshmallow
from flask_sqlalchemy import SQLAlchemy
db = SQLAlchemy()

ma = Marshmallow()

# 歌曲的表
class Song(db.Model):
    __tablename__ = 'tb_song2'  # 表名
    id = db.Column(db.Integer, primary_key=True, autoincrement=True)
    songId = db.Column(db.String(255))
    songName = db.Column(db.String(255))
    alia = db.Column(db.String(255))
    pic = db.Column(db.String(255))
    singerName = db.Column(db.String(255))
    albumName = db.Column(db.String(255))
    dt = db.Column(db.String(50))
    pop = db.Column(db.String(20))
    originCoverType = db.Column(db.String(20))
    fee = db.Column(db.String(50))
    mv_url = db.Column(db.TEXT)
    mv = db.Column(db.String(50))
    publishTime = db.Column(db.DATETIME)  #时间的处理

class songSchema(ma.Schema):
    class Meta:
        fields = ('id','songId', 'songName', 'alia', 'pic', 'singerName', 'albumName', 'dt', 'pop', 'publishTime','originCoverType','fee',
                  'mv_url','mv')

song_schema = songSchema()
song_schema = songSchema(many=True)

class RankSchema(ma.Schema):
    class Meta:
        fields = ('name', 'value')

rank_schema = RankSchema()
rank_schema = RankSchema(many=True)




