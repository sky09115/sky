# -*- codeing = utf-8 -*-
# @Desc:
from flask_marshmallow import Marshmallow
from flask_sqlalchemy import SQLAlchemy

ma = Marshmallow()
db = SQLAlchemy()

class Rate(db.Model):
    __tablename__ = 'tb_rate'
    id = db.Column(db.Integer, autoincrement=True, primary_key=True)
    uid = db.Column(db.INTEGER)
    iid = db.Column(db.INTEGER)
    rate = db.Column(db.DECIMAL)

# 定义Marchmallow封装json用的类
class RateSchema(ma.Schema):
    class Meta:
        fields = ('uid','iid','rate')

rate_schema = RateSchema(many=True)
