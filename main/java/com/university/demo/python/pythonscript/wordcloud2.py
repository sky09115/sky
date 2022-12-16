import json
import sys

# import demjson
import jieba
import jieba.analyse

from lyric import Lyric
from tool import db



"""
"""
# tf-idf 关键词
def PythonFunc2():
    records = db.session.query(Lyric).limit(1000).all()
    text = ""
    for i in records:
        # print(i.intro)  # 每一行
        if i.brief_instruction is not None:   #需要修改词云分词的字段
            text = text + i.brief_instruction
            # print(text)

    result = jieba.analyse.extract_tags(text, topK=200, withWeight=True,
                               allowPOS=(
                                   'n', 'f', 's', 'nr', 'ns', 'nt', 'nw', 'nz', 'v', 'vd', 'vn', 'a', 'PER',
                                   'LOC', 'ORG'))


    return dict(result)


if __name__ == '__main__':
    # subject = sys.argv[1]
    # scores = demjson.decode(sys.argv[2])
    # subject = '理学'
    # scores = {'scores': [55,55,155]}
    # 调用生成json函数
    # createJson(subject, scores['scores'])
    result = PythonFunc2()
    print(result)