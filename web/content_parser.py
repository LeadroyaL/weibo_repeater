import json

wb_count = 0
simple_count = 0
pic_count = 0
retweet_count = 0

for i in range(70):
    fd = open('content_%d.json' % i)
    sd = fd.read()
    data = json.loads(sd)
    for j in range(len(data['data']['cards'])):
        simple_flag = True
        pic_flag = False
        retweet_flag = False
        card_type = data['data']['cards'][j]['card_type']
        if 'mblog' in data['data']['cards'][j]:
            mblog = data['data']['cards'][j]['mblog']
            created_at = mblog['created_at']
            text = None
            if 'raw_text' in mblog:
                text = mblog['raw_text']
            elif 'text' in mblog:
                text = mblog['text']
            if len(mblog['created_at']) < 6:
                text = "@LeadroyaL时日不多了 发表于" + '2018-' + mblog['created_at'] + "：" + text
            else:
                text = "@LeadroyaL时日不多了 发表于" + mblog['created_at'] + "：" + text
            print(text)
            if 'pics' in mblog:
                if len(mblog['pics']) != 0:
                    pic_count += 1
                    simple_flag = False
                    pic_flag = True
                for k in range(len(mblog['pics'])):
                    pass
                    print('pic_%d_%d:' % (wb_count, k), mblog['pics'][k]['large']['url'], end='')
                print()
            can_retweet = True
            if 'retweeted_status' in mblog:
                retweet_count += 1
                simple_flag = False
                retweet_flag = True
                if "抱歉，此微博已被作者删除" in mblog['retweeted_status']['text']:
                    print('retweeted_status', mblog['retweeted_status']['mid'], mblog['retweeted_status']['text'])
                    can_retweet = False
                else:
                    print('retweeted_status', mblog['retweeted_status']['mid'])
            print('%d type is ' % wb_count, end='')
            if simple_flag:
                print('simple')
            elif pic_flag:
                print('pic')
            elif retweet_flag:
                print('retweet')
            else:
                print('unknown')
            wb_count += 1
    fd.close()
print(wb_count)
print(simple_count)
print(pic_count)
print(retweet_count)

