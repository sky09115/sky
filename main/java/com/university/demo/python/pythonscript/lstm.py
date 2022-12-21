# coding=utf-8
import sys
import paddlehub as hub

# BiLstm 情感分析接口
if __name__ == '__main__':
    # param1 = sys.argv[1]
    param1 = '这个电影不错的哟'
    # param1 = "1"
    senta = hub.Module(name="senta_bilstm")
    test_text= [param1]
    input_dict = {"text": test_text}
    results = senta.sentiment_classify(data=input_dict)
    print(results)