# -*- codeing = utf-8 -*-

# @File :base.py
import pymysql

host = '127.0.0.1'
user = 'root'
password = '123456'
port = 3306

database = 'metro'

cnn = pymysql.connect(host=host, user=user, password=password, port=port, database=database,
                      charset='utf8')