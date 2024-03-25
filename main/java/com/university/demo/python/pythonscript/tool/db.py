# -*- codeing = utf-8 -*-
# Author: Tesla Tech
# QQ:  1693353672
# @Time: 2022/7/3 8:42
# @Author: Administrator
# @File: db.py
# @Desc:
from sqlalchemy import create_engine
from sqlalchemy.orm import declarative_base, sessionmaker

DB_URI ='mysql+pymysql://root:Lvsg9410267963@localhost/metro'

engine = create_engine(DB_URI)
Base = declarative_base(engine)  # SQLORM基类
session = sessionmaker(engine)()  # 构建session对象