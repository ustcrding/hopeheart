from flask import Flask, request, jsonify
from lstm_test import lstm_predict
import ibm_watson
import json
import time

app = Flask(__name__)


@app.route('/')
def hello_world():
    return 'Hello World!'


@app.route('/ai-doctor/cure',methods=['POST'])
def ai_cure():
    # request_data = request.get_data().decode('utf-8')  # 解析json前进行编码.不然出来的结果并不是中文的..
    # print(type(request_data))
    # print(request_data)
    # reqform = json.loads(request_data)
    reqform = request.form
    query = reqform['query']
    session_id = reqform['session_id']
    print("query : %s" % query)
    print("session_id : %s" % session_id)
    resdict = {}
    data = {}

    answer = get_watson_answer(query)
    if len(answer) > 0:
        data['answer'] = answer
        data['manuFlag'] = False
    else:
        # 主动辅导
        # 情感分析
        modes = lstm_predict(query)
        mode_score = (modes[0][2] / modes[0][0]) - 1
        print("mode_score : %f" % mode_score)
        if mode_score >= 0.5:
            data['answer'] = "好的，您的情况我了解，下面由X专家给您解答。"
            data['manuFlag'] = True
        else:
            postive_cure(query)
            data['answer'] = "真是糟糕的感觉，还有其他地方不舒服吗"
            data['manuFlag'] = False

    resdict['code'] = 0
    resdict['message'] = "success"
    resdict['data'] = data
    print(resdict)
    return jsonify(resdict)


def get_watson_answer(query):
    print("请求IBM watson机器人。。。")
    startTime = time.time()
    service = ibm_watson.AssistantV2(
        iam_apikey='IYTiMNBR5yE6AXy9jSpwmMX1A5Hx6NlaGHUpxBaoYRhX',
        version='2019-07-29',
        url='https://gateway.watsonplatform.net/assistant/api'
    )

    response = service.create_session(
        assistant_id='672fa42c-151b-44ef-9243-7ec9031d04bb'
    ).get_result()

    session_id = response.get("session_id")

    qa_response = service.message(
        assistant_id='672fa42c-151b-44ef-9243-7ec9031d04bb',
        session_id=session_id,
        input={
            'message_type': 'text',
            'text': query
        }
    ).get_result()
    print(qa_response)
    outputs = qa_response.get("output")
    generics = outputs.get("generic")
    intents = outputs.get("intents")
    if len(intents) > 0:
        # 寒暄和查询知识库
        answer = generics[0].get("text")
    else:
        # 主动辅导
        answer = ""
    endTime = time.time()
    peroid = endTime - startTime
    print("IBM watson耗时： %s" % peroid)
    return answer


def postive_cure(query):
    # 调用textsum训练好的语言模型
    pass


if __name__ == '__main__':
    app.run(host='0.0.0.0', port=9090)
