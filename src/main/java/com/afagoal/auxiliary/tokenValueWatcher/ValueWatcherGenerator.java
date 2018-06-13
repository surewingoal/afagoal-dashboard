package com.afagoal.auxiliary.tokenValueWatcher;

import com.afagoal.auxiliary.tokenEnum.TokenWatcherEnum;
import com.afagoal.dto.base.ValueDateModel;
import com.afagoal.dto.blockchain.TokenSimpleDto;
import com.afagoal.entity.blockchain.valueWatch.ValueWatcher;
import com.afagoal.entity.blockchain.valueWatch.ValueWatcherCondition;
import com.afagoal.utils.date.DateUtils;

import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * Created by BaoCai on 18/6/13.
 * Description:
 */
public class ValueWatcherGenerator {


    private static final BigDecimal IGNORE_VALUE = new BigDecimal("0.0000000001");

    public static ValueWatcher createValueWatcher(ValueDateModel needWatch,
                                                   ValueDateModel todayValue,
                                                   ValueWatcherCondition condition,
                                                   TokenSimpleDto token,
                                                   TokenWatcherEnum tokenWatcherEnum) {
        ValueWatcher valueWatcher = new ValueWatcher();
        valueWatcher.setToday(LocalDate.now());
        BigDecimal nowValue = (BigDecimal) todayValue.getValue();
        valueWatcher.setTodayValue(nowValue);
        valueWatcher.setWatchType(condition.getWatchType());
        valueWatcher.setTokenId(token.getId());
        valueWatcher.setWatchConditionId(condition.getId());
        BigDecimal oldValue = (BigDecimal) needWatch.getValue();
        valueWatcher.setTriggerValue(oldValue);
        valueWatcher.setTriggerDate(needWatch.getStatisticTime().toLocalDate());
        byte isUp = (byte) ((BigDecimal) todayValue.getValue()).compareTo(oldValue);
        valueWatcher.setChangeSign(isUp);
        BigDecimal realValueChange = null;
        if (IGNORE_VALUE.compareTo(oldValue) < 0) {
            realValueChange = nowValue.subtract(oldValue).divide(oldValue, 2, BigDecimal.ROUND_HALF_UP);
            valueWatcher.setRealValueChange(realValueChange);
        }
        valueWatcher.setRemindInfo(createRemindInfo(needWatch, todayValue, token, condition, isUp, realValueChange, tokenWatcherEnum.getIntro()));
        return valueWatcher;
    }

    private static String createRemindInfo(ValueDateModel needWatch,
                                           ValueDateModel todayValue,
                                           TokenSimpleDto token,
                                           ValueWatcherCondition condition,
                                           byte isUp,
                                           BigDecimal realValueChange,
                                           String intro) {
        StringBuilder builder = new StringBuilder();
        if (null == realValueChange) {
            realValueChange = condition.getChangeRank();
        }
        String upOrDown = isUp == 1 ? "上涨" : "下降";
        builder.append("您关注的币种:")
                .append(token.getTokenName())
                .append("最近")
                .append(intro)
                .append("波动较大。")
                .append("<br/>")
                .append(condition.getWatchDays())
                .append("天内，价格")
                .append(upOrDown)
                .append(realValueChange.doubleValue() * 100)
                .append(condition.getWatchUnit())
                .append("。")
                .append("<br/>")
                .append(DateUtils.format(needWatch.getStatisticTime().toLocalDate()))
                .append("价格：")
                .append(needWatch.getValue())
                .append("$；")
                .append("今日价格：")
                .append("<br/>")
                .append(todayValue.getValue())
                .append("$。");
        return builder.toString();
    }

}
