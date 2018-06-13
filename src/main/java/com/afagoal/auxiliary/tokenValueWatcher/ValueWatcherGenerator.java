package com.afagoal.auxiliary.tokenValueWatcher;

import com.afagoal.auxiliary.tokenEnum.TokenWatcherEnum;
import com.afagoal.dto.base.ValueDateModel;
import com.afagoal.dto.blockchain.TokenSimpleDto;
import com.afagoal.entity.blockchain.valueWatch.ValueWatcher;
import com.afagoal.entity.blockchain.valueWatch.ValueWatcherCondition;

import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * Created by BaoCai on 18/6/13.
 * Description:
 */
public class ValueWatcherGenerator {


    public static final int WATCHER_SIZE = 30;
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
        valueWatcher.setRemindInfo(tokenWatcherEnum.getRemindInfoGenerator().createRemindInfo(needWatch, todayValue, token, condition, isUp, realValueChange));
        return valueWatcher;
    }

}
