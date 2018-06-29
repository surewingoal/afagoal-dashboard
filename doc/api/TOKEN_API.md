# TOKEN API

* [币种相关](#币种相关)
	* [币种列表](#币种列表) 	
	* [币种价格纪录](#币种价格纪录)
	* [币种详情-价格相关](#币种详情-价格相关)
	* [币种详情-固定信息](#币种详情-固定信息)
	* [币种TOP持有者](#币种TOP持有者)
	* [币种TOP持有者持仓变化](#币种TOP持有者持仓变化)
	* [币种TOP持有比例](#币种TOP持有比例)
	* [币种TOP持有比例变化趋势](#币种TOP持有比例变化趋势)

## <a name="币种相关"></a>币种相关
### <a name="币种列表"></a>币种列表

* URL : /blockchain/tokens
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

* URL : /blockchain/tokens/{token_id}/details
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

### <a name="币种详情-价格相关"></a>币种详情-价格相关

* URL : /blockchain/tokens/{token_id}/value_info
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

### <a name="币种详情-固定信息"></a>币种详情-固定信息

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

### <a name="币种TOP持有者"></a>币种TOP持有者

* URL : /blockchain/tokens/{token_id}/top_holders
* 参数 : token_id : 币种ID
* response

```
{
    "msg": null,
    "rc": 0,
    "data": [
        {
            "id": "9e98dc68-7af6-11e8-bfd8-525400376068",
            "tokenName": "EOS",
            "tokenCode": "EOS",
            "tokenId": "0x86fa049857e0209aa7d9e616f7eb3b3b78ecfdb0",
            "address": "0x00000000000000000000000000000000000000b1",
            "rank": 1,
            "quantily": 100000000.01,
            "percentage": 10,
            "yesterdayRank": 1,
            "yesterdayQuantily": 100000000.01,
            "yesterdayPercentage": 10,
            "percentageChange": 0,
            "statisticTime": "2018-06-29 01:13:47"
        },
        {
            "id": "9e98dc69-7af6-11e8-bfd8-525400376068",
            "tokenName": "EOS",
            "tokenCode": "EOS",
            "tokenId": "0x86fa049857e0209aa7d9e616f7eb3b3b78ecfdb0",
            "address": "0x100634b885f54286762578042a1bfcbeacb91729",
            "rank": 2,
            "quantily": 75291097.61865,
            "percentage": 7.5291,
            "yesterdayRank": 2,
            "yesterdayQuantily": 75291097.61865,
            "yesterdayPercentage": 7.5291,
            "percentageChange": 0,
            "statisticTime": "2018-06-29 01:13:47"
        }
    ]
}
```


### <a name="币种TOP持有者持仓变化"></a>币种TOP持有者持仓变化

* URL : /blockchain/tokens/{token_id}/top_holders/{address}
* 参数 : token_id : 币种ID
* 参数 : address  : 持有者地址
* 描述 : 某token下某address持有者持仓变化趋势
* response

```
{
    "msg": null,
    "rc": 0,
    "data": [
        {
            "id": "d531921d-68e3-11e8-bfd8-525400376068",
            "rank": 2,
            "percentage": 7.5291,
            "quantily": 75291097.61865,
            "statisticTime": "2018-06-06"
        },
        {
            "id": "df545571-70bf-11e8-bfd8-525400376068",
            "rank": 2,
            "percentage": 7.5291,
            "quantily": 75291097.61865,
            "statisticTime": "2018-06-16"
        },
        {
            "id": "eb6a08c5-7708-11e8-bfd8-525400376068",
            "rank": 2,
            "percentage": 7.5291,
            "quantily": 75291097.61865,
            "statisticTime": "2018-06-24"
        }
    ]
}
```


### <a name="币种TOP持有比例"></a>币种TOP持有比例

* URL : /blockchain/tokens/{token_id}/top_percentages
* 参数 : token_id : 币种ID
* response

```
{
    "msg": null,
    "rc": 0,
    "data": [
        {
            "id": "ac7cade6-7af6-11e8-bfd8-525400376068",
            "tokenName": "EOS",
            "tokenId": "0x86fa049857e0209aa7d9e616f7eb3b3b78ecfdb0",
            "tokenCode": "EOS",
            "topType": 10,
            "yesterdayPercentage": 0,
            "percentage": 49.67,
            "statisticTime": "2018-06-29 01:14:10",
            "holdNums": 496704568.49
        },
        {
            "id": "aadecd66-7af6-11e8-bfd8-525400376068",
            "tokenName": "EOS",
            "tokenId": "0x86fa049857e0209aa7d9e616f7eb3b3b78ecfdb0",
            "tokenCode": "EOS",
            "topType": 25,
            "yesterdayPercentage": 0,
            "percentage": 64.76,
            "statisticTime": "2018-06-29 01:14:08",
            "holdNums": 647629172.16
        },
        {
            "id": "a9c89b6e-7af6-11e8-bfd8-525400376068",
            "tokenName": "EOS",
            "tokenId": "0x86fa049857e0209aa7d9e616f7eb3b3b78ecfdb0",
            "tokenCode": "EOS",
            "topType": 50,
            "yesterdayPercentage": 0,
            "percentage": 70.25,
            "statisticTime": "2018-06-29 01:14:06",
            "holdNums": 702525171.75
        },
        {
            "id": "a84c2d3c-7af6-11e8-bfd8-525400376068",
            "tokenName": "EOS",
            "tokenId": "0x86fa049857e0209aa7d9e616f7eb3b3b78ecfdb0",
            "tokenCode": "EOS",
            "topType": 100,
            "yesterdayPercentage": 0,
            "percentage": 75.13,
            "statisticTime": "2018-06-29 01:14:03",
            "holdNums": 751337324.36
        }
    ]
}
```


### <a name="币种TOP持有比例变化趋势"></a>币种TOP持有比例变化趋势

* URL : /blockchain/tokens/{token_id}/top_percentages/{top_type}
* 参数 : token_id : 币种ID
* 参数 : top_type : top_type  10、25、50、100
* 描述 : 某token下TOP(10、25、50、100)持有比例变更纪录
* response

```
{
    "msg": null,
    "rc": 0,
    "data": [
        {
            "statisticTime": "2018-06-27",
            "percentage": 75.13,
            "holdNums": 75133.732436
        },
        {
            "statisticTime": "2018-06-28",
            "percentage": 75.13,
            "holdNums": 75133.732436
        },
        {
            "statisticTime": "2018-06-29",
            "percentage": 75.13,
            "holdNums": 75133.732436
        }
    ]
}
```