# 看我如何让被封掉的微博秽土转生

```
├── com.weico.international_2.9.2_2920.apk 逆向时选取的apk
├── web
│   ├── content.py  用来dump整个微博内容
│   ├── content_parser.py  用来简单地将微博内容按照格式分类
│   ├── create_friend.py   用来自动关注指定用户
│   ├── follow.py    用来获取已关注的列表
│   ├── follow.txt   follow.py的结果
│   ├── pics.txt     需要保存的图片的结果
│   └── resources
│       └── content_%d.json  我自己的微博数据
└── xposed
    ├── Test.java   xposed的插件，需要借助hotposed
    └── wrapper.py  pc上运行的、控制手机发微博的python脚本
```
