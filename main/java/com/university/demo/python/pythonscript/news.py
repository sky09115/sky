# -*- codeing = utf-8 -*-
# @Desc:
import json
from flask_marshmallow import Marshmallow
from flask_sqlalchemy import SQLAlchemy

ma = Marshmallow()
db = SQLAlchemy()

class New(db.Model):
    __tablename__ = 'new'
    id = db.Column(db.Integer, autoincrement=True, primary_key=True)
    title = db.Column(db.String(255))
    maintxt = db.Column(db.String(255))

# 定义Marchmallow封装json用的类
class NewSchema(ma.Schema):
    class Meta:
        fields = ('id','title','maintxt')

new_schema = NewSchema(many=True)
