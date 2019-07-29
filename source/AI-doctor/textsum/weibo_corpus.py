import re
import copy
import random
from pyhanlp import *

pattern3 = re.compile(r"(http.*)|(\[.*\])|(\(.*\))|")
pattern4 = re.compile(r"(\[.*\])|")


def data_get(content_path, title_path, data_sum):
    """
    从原始数据提取出title及content，并分别存入词典以对应
    同时做了去重和转半角和一定的过滤
    :return:
    """
    content_title = {}
    content = {}
    global i, j
    with open(content_path, "r", encoding="utf-8") as f1, open(title_path, "r", encoding="utf-8") as f2:
        for content_line in f1:
            content_line = strQ2B(content_line).replace("\ue40c", "").replace("(图)", "").replace("↓↓", "")
            re.sub(pattern3, "", content_line)
            if not content.items().__contains__(content_line):
                content.setdefault(i, content_line)
                i = i + 1
        for title_line in f2:
            title_line = strQ2B(title_line).replace("\ue40c", "").replace("(图)", "")
            re.sub(pattern4, "", title_line)
            if not content_title.items().__contains__(title_line):
                content_title.setdefault(j, title_line)
                j = j + 1
        if i == data_sum or j == data_sum:
            return content_title, content
    return content_title, content


def strQ2B(string):
    """
    全角转半角
    :param string:
    :return:
    """
    rstring = ""
    for uchar in string:
        inside_code = ord(uchar)
        if inside_code == 12288:
            inside_code = 32
        elif 65281 <= inside_code <= 65374:
            inside_code -= 65248
        rstring += chr(inside_code)
    return rstring


def data_clean(dic, cls):
    """
    去除较短的数据
    :param dic:
    :param cls:区分标题还是内容
    :return:
    """
    dic_copy = copy.deepcopy(dic)
    for key in dic.keys():
        text = dic.get(key)
        if cls == 1 and len(text) <= 4:
            dic_copy.__delitem__(key)
            continue
        if cls == 2 and len(text) <= 20:
            dic_copy.__delitem__(key)
            continue
        if (not re.match(pattern3, text)) or re.match(pattern4, text):
            dic_copy.__delitem__(key)
            continue

    return dic_copy


if __name__ == '__main__':
    contents = {}
    titles = {}

    title, content = data_get("C:/Users/ljw8155/Desktop/data/新闻标题数据集/新闻标题数据集/train_text.txt",
                              "C:/Users/ljw8155/Desktop/data/新闻标题数据集/新闻标题数据集/train_label.txt",
                              3000)
    contents.update(content)
    titles.update(title)

    content = data_clean(contents, 2)
    content_title = data_clean(titles, 1)
    # print(len(content))
    # print(len(content_title))
    datas = data_participle(content, content_title)
    data_write(datas, 0.8, "E:\\0个人\\算法小组\\code\\textsum\\my_data")
