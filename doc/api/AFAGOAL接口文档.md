# AFAGOAL接口文档

* 服务器地址：***https://dashboard.afagoal.top***

## 注册
### 微信端注册
* URL : /register/wechat
* 参数
<br>

| 参数  | 参数名称  | 参数类型 | 是否必传 |
|:------------- |:---------------:| -------------:| ------:|
|登录名     |user_name         |  String   |   是  |
|注册邮箱   |email              |     String     |  是  |
|注册电话   | mobile            |  String       |   是 |
|注册密码   | password          |   String      |  是   |
|确认密码   | repassword        |      String   |  是   |
|微信昵称   | wechat_nick_name  |   String      |   否  |
|微信头像   | wechat_avatar_url |  String       |   否  |
|性别       | wechat_gender     |   byte      |  否   |
|微信所在城市| wechat_city       |   String      |  否   |
|微信所在省份| wechat_province   |    String     |    否 |
|微信使用语言| wechat_language   |   String      |  否   |
|微信所在国家| wechat_country    |  String       |  否   |

* request
<br>

```
{
	"user_name":"littlefat",
	"wechat_nick_name":"小肥",
	"wechat_avatar_url":"wechat_avatar_url",
	"wechat_gender":1,
	"wechat_city":"杭州",
	"wechat_province":"浙江",
	"wechat_language":"中文",
	"wechat_country":"中国",
	"email":"12312@afagoal.top",
	"mobile":"18290873168",
	"password":"123456",
	"repassword":"123456"
}
```

* response
<br>
注册成功

```
{
    "msg": null,
    "rc": 0,
    "data": "注册成功！"
}
```

注册失败

```
{
    "msg": "该用户名已被被注册！",
    "rc": 1,
    "data": null
}
```

## 登录
### 移动端登录
* url : /afagoal_token/login
* 参数
<br>

| 参数  | 参数名称  | 参数类型 | 是否必传 |
|:------------- |:---------------:| -------------:| ------:|
|登录名/电话号码     | username         |  String   |   是  |
|注册邮箱   | password              |     String     |  是  |
* response 
<br>

```
{
    "msg": null,
    "rc": 0,
    "data": {
        "real_name": null,
        "afagoal_token": "d54ed9c146ab40bca5da4d496911553c",
        "user_id": 17,
        "user_name": "littlefat11",
        "nick_name": "小肥"
    }
}
```

* 登录后访问接口带上header ***Authorization:afagoal b83127fddd6e47dd99bdd4442235434b***

* 测试接口：https://dashboard.afagoal.top/blockchain/token/list