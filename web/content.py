# -*-coding:utf8-*-

import time
import requests

user_id = 12345678

cookie = {
    "Cookie": "ALF=1544707706; SCF=Ak7h6raU-MOVDMV3RDOAWlZKt0DWVwcjJ13fKETjSF6u-vs0nu_VAOJocKAOI-LM_9K5yyiJuTWm8pOvASeXZt0.; SUB=_2A2527qHpDeRhGeVH6lAQ8CvOzDqIHXVSEM-hrDV6PUJbktANLRnakW1NT2fsMmZ0BDPDk1Ao3-fpQ2lkz1RfF9uZ; SUBP=0033WrSXqPxfM725Ws9jqgMF55529P9D9WWPCrQQRo3CG6es4qqEDNqL5JpX5K-hUgL.Foe4eKzpeh-ES0q2dJLoI09e9g8.MJv4q-7LxKqL1KMLBoqLxKqL1KMLBK-LxKBLBonL12BLxK-LBKBL1-2LxKBLB.2L1hqt; SUHB=0ftp9n7k2d59rv; SSOLoginState=1542115769; _T_WM=5059053e1fe585297cba2fceba393ef4; MLOGIN=1; WEIBOCN_FROM=1110006030; M_WEIBOCN_PARAMS=luicode%3D10000011%26lfid%3D2304133912105276_-_WEIBO_SECOND_PROFILE_WEIBO%26fid%3D102803%26uicode%3D20000174"}

headers = {
    "Accept": "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8",
    "Referer": "https://m.weibo.cn/p/2304133912105276_-_WEIBO_SECOND_PROFILE_WEIBO",
    "Accept-Language": "zh-CN,zh;q=0.9",
    "User-Agent": "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/70.0.3538.102 Safari/537.36",
    "Cache-Control": "max-age=0",
    "Upgrade-Insecure-Requests": "1",
    "Host": "m.weibo.cn",
    "Connection": "close",
    "MWeibo-Pwa": "1",
    "X-Requested-With": "XMLHttpRequest",

}

for i in range(70):
    fd = open("D:\\content_.json" % i, 'wb')
    url = 'https://m.weibo.cn/api/container/getIndex?containerid=2304133912105276_-_WEIBO_SECOND_PROFILE_WEIBO&page_type=03&page=%d' % i
    lxml = requests.get(url, cookies=cookie, headers=headers).content
    print(lxml)
    print("-------")
    fd.write(lxml)
    fd.close()
    time.sleep(5)
