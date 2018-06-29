# TOKEN API

* [币种相关](#币种相关)
	* [币种列表](#币种列表) 	
	* [币种价格纪录](#币种价格纪录)
	* [币种详情:价格相关](#币种详情:价格相关)
	* [币种详情:固定信息](#币种详情:固定信息)

## <a name="币种相关"></a>币种相关
### <a name="币种列表"></a>币种列表

* URL : /blockchain/token_details
* 参数
<br>

| 参数  | 参数名称  | 参数类型 | 是否必传 | 描述 |
|:------- |:----------:| ------:| ------:| ---:|
|排序类型  |order_by  | byte  |   否  |1:市值排名   2:交易量排名3:涨幅4:跌幅      |
|关键字    |key      | String   |  否  |   tokenName/tokencode模糊查询   |
|分页大小  | page    | int   |   否 | 默认20    |
|当前页码  | size  | int  |  否   |  默认1  |

* response
<br>
注册成功

```
{
    "msg": null,
    "rc": 0,
    "data": {
        "rows": [
            {
                "id": "ac7cade7-7af6-11e8-bfd8-525400376068",
                "tokenName": "EOS",
                "tokenCode": "EOS",
                "tokenId": null,
                "priceChange": "-2.54000%",
                "statisticTime": "2018-06-29 01:13:30",
                "totalValue": "7857800000.00000 USD",
                "usdProfitability": 693.72,
                "usd": 7.8578,
                "usdStr": "7.85780 USD",
                "ethProfitability": 452.57,
                "eth": ".01807 ETH",
                "circulatingSupply": ".00000 USD",
                "todayTransaction": 0,
                "transfersUsd": 7041763480,
                "transfersToken": 896149492
            }
        ],
        "total": 495
    }
}
```

### <a name="币种价格纪录"></a>币种价格纪录

* URL : /blockchain/token_details/{token_id}/details
* 参数 : token_id : 币种ID
* response 

```
{
    "msg": null,
    "rc": 0,
    "data": [
        {
            "id": "1b7e4c6c-61a3-11e8-bfd8-525400376068",
            "usd": 12.5517,
            "eth": 0.021846,
            "statisticTime": "2018-05-27"
        },{
            "id": "ac7cade7-7af6-11e8-bfd8-525400376068",
            "usd": 7.8578,
            "eth": 0.018069,
            "statisticTime": "2018-06-29"
        }
    ]
}
```

### <a name="币种详情:价格相关"></a>币种详情:价格相关

* URL : /blockchain/token_details/{token_id}/info
* 参数 : token_id : 币种ID
* response 

```
{
    "msg": null,
    "rc": 0,
    "data": {
        "id": "ac7cade7-7af6-11e8-bfd8-525400376068",
        "tokenName": "EOS",
        "tokenCode": "EOS",
        "tokenId": null,
        "priceChange": "-2.54000%",
        "statisticTime": "2018-06-29 01:13:30",
        "totalValue": "7857800000.00000 USD",
        "usdProfitability": 693.72,
        "usd": 7.8578,
        "usdStr": "7.85780 USD",
        "ethProfitability": 452.57,
        "eth": ".01807 ETH",
        "circulatingSupply": ".00000 USD",
        "todayTransaction": 0,
        "transfersUsd": 7041763480,
        "transfersToken": 896149492
    }
}
```

### <a name="币种详情:固定信息"></a>币种详情:固定信息

* URL : /blockchain/tokens/{token_id}/info
* 参数 : token_id : 币种ID
* response

```
{
    "msg": null,
    "rc": 0,
    "data": {
        "id": "0x86fa049857e0209aa7d9e616f7eb3b3b78ecfdb0",
        "tokenName": "EOS",
        "tokenCode": "EOS",
        "totalSupply": 7041763480,
        "holders": 330687,
        "transfers": 3570224,
        "decimals": 18,
        "country": "USA",
        "highestPrice": "14.31530 USD",
        "lowestPrice": "7.75580 USD",
        "highestTransaction": 0,
        "lowestTransaction": 0,
        "icoStartDate": "2017-06-26",
        "icoEndDate": "2018-06-01",
        "icoPrice": "0.99 USD | 0.00327 ETH |0.00036526 BTC",
        "totalCap": 0,
        "totalRaised": ".00000 USD",
        "weight": null,
        "tokenLinks": [
            {
                "id": "2b7e3c71-68c2-11e8-bfd8-525400376068",
                "createdAt": null,
                "updatedAt": null,
                "createdBy": "",
                "updatedBy": "",
                "state": 0,
                "version": 0,
                "tokenId": "0x86fa049857e0209aa7d9e616f7eb3b3b78ecfdb0",
                "linkType": "Website",
                "linkValue": "https://eos.io/"
            },
            {
                "id": "2b7e3c72-68c2-11e8-bfd8-525400376068",
                "createdAt": null,
                "updatedAt": null,
                "createdBy": "",
                "updatedBy": "",
                "state": 0,
                "version": 0,
                "tokenId": "0x86fa049857e0209aa7d9e616f7eb3b3b78ecfdb0",
                "linkType": "Email",
                "linkValue": "mailto:eos@block.one"
            },
            {
                "id": "2b7e3c73-68c2-11e8-bfd8-525400376068",
                "createdAt": null,
                "updatedAt": null,
                "createdBy": "",
                "updatedBy": "",
                "state": 0,
                "version": 0,
                "tokenId": "0x86fa049857e0209aa7d9e616f7eb3b3b78ecfdb0",
                "linkType": "Facebook",
                "linkValue": "https://www.facebook.com/eosblockchain"
            }
        ]
    }
}
```