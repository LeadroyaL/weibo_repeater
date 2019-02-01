import os
import time
import random

fd = open("../data.txt")
lines = []

while True:
    l = fd.readline().strip("\n\r")
    if l == "":
        break
    lines.append(l)
fd.close()

i = 0
while True:
    l = lines[i]
    if "retweeted_status" in l:
        mid = l[len("retweeted_status") + 1:]
        print "mid", mid
        i += 1
        plain = lines[i]
        print "plain", plain
        fd = open('1.txt', 'w')
        fd.write(plain)
        fd.close()
        os.system("adb push 1.txt /sdcard/")
        os.system("adb shell am broadcast -a com.leadroyal.REPOST --es filename 1.txt --es mid " + mid)
        i += 1
        second = (10 + int(round(random.random() * 100))) * 60
        os.system("date")
        print("sleep second", second)
        time.sleep(second)
    elif "pic" in l:
        print l
        print "========picture========"
        i += 1
        break
    else:
        plain = l
        print "plain", plain
        fd = open('1.txt', 'w')
        fd.write(plain)
        fd.close()
        os.system("adb push 1.txt /sdcard/")
        os.system("adb shell am broadcast -a com.leadroyal.SEND --es filename 1.txt")
        i += 1
        second = 5 * 60
        os.system("date")
        print("sleep second", second)
        time.sleep(second)
