# -*-coding:utf8-*-

import re
import requests
import time

user_id = 12345678

patter = re.compile(r"https://weibo.cn/attention/del\?uid=(\d+)\&amp")

cookie = {
    "Cookie": "ALF=1544707706; SCF=Ak7h6raU-MOVDMV3RDOAWlZKt0DWVwcjJ13fKETjSF6u-vs0nu_VAOJocKAOI-LM_9K5yyiJuTWm8pOvASeXZt0.; SUB=_2A2527qHpDeRhGeVH6lAQ8CvOzDqIHXVSEM-hrDV6PUJbktANLRnakW1NT2fsMmZ0BDPDk1Ao3-fpQ2lkz1RfF9uZ; SUBP=0033WrSXqPxfM725Ws9jqgMF55529P9D9WWPCrQQRo3CG6es4qqEDNqL5JpX5K-hUgL.Foe4eKzpeh-ES0q2dJLoI09e9g8.MJv4q-7LxKqL1KMLBoqLxKqL1KMLBK-LxKBLBonL12BLxK-LBKBL1-2LxKBLB.2L1hqt; SUHB=0ftp9n7k2d59rv; SSOLoginState=1542115769; _T_WM=5059053e1fe585297cba2fceba393ef4"}

headers = {
    "Accept": "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8",
    "Referer": "https://weibo.cn/3912105276/follow?page=2",
    "Accept-Language": "zh-CN,zh;q=0.9",
    "User-Agent": "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/70.0.3538.102 Safari/537.36",
    "Cache-Control": "max-age=0",
    "Upgrade-Insecure-Requests": "1",

}
for i in range(26):
    url = 'http://weibo.cn/%d/follow' % (user_id)
    url += "?page=%d" % (i + 1)
    c = requests.get(url, cookies=cookie, headers=headers).content
    matcher = patter.findall(c)
    print(matcher)
    time.sleep(15)

