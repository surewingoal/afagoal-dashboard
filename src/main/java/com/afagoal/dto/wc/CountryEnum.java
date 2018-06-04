package com.afagoal.dto.wc;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import lombok.Getter;

/**
 * Created by BaoCai on 18/6/3.
 * Description:
 */
@Getter
public enum CountryEnum {

    ELUOSI("俄罗斯", 'A'),
    SHATE("沙特", 'A'),
    AIJI("埃及", 'A'),
    WULAGUI("乌拉圭", 'A'),

    PUTAOYA("葡萄牙", 'B'),
    SIBANYA("西班牙", 'B'),
    MOLUOGE("摩洛哥", 'B'),
    YALANG("伊朗", 'B'),

    FAGUO("法国", 'C'),
    AODALIYA("澳大利亚", 'C'),
    MILU("秘鲁", 'C'),
    DANMAI("丹麦", 'C'),

    AGENTING("阿根廷", 'D'),
    BINGDAO("冰岛", 'D'),
    KELUOLIYA("克罗地亚", 'D'),
    NIRILIYA("尼日利亚", 'D'),

    BAXI("巴西", 'E'),
    RUISHI("瑞士", 'E'),
    GESIDALIJIA("哥斯达黎加", 'E'),
    SAIERWEIYA("塞尔维亚", 'E'),

    DEGUO("德国", 'F'),
    MOXIGE("墨西哥", 'F'),
    RUIDIAN("瑞典", 'F'),
    HANGUO("韩国", 'F'),

    BILISHI("比利时", 'G'),
    BANAMA("巴拿马", 'G'),
    TUNISI("突尼斯", 'G'),
    YINGGELAN("英格兰", 'G'),

    BOLAN("波兰", 'H'),
    SAINEIJIAER("塞内加尔", 'H'),
    GELUNBIYA("哥伦比亚", 'H'),
    RIBEN("日本", 'H');

    private String country;

    private char group;

    CountryEnum(String country, char group) {
        this.country = country;
        this.group = group;
    }

    public static final Map<String, Long> guessNum = new ConcurrentHashMap();

    public static final Map<String, CountryEnum> countryMap = new HashMap();

    static {
        Arrays.stream(CountryEnum.values()).forEach(countryEnum -> {
            long count = (long) (Math.random() * 1000);
            guessNum.put(countryEnum.getCountry(), count);
            countryMap.put(countryEnum.getCountry(), countryEnum);
        });
    }

    public static CountryEnum valueOfEnum(String country) {
        return countryMap.get(country);
    }

}
