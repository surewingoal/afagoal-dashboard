package com.afagoal.valueWatch;

import com.afagoal.auxiliary.tokenValueWatcher.DefaultWatcherMatch;
import com.afagoal.entity.blockchain.valueWatch.ValueWatcherCondition;

import java.util.List;

import org.junit.Test;

import java.math.BigDecimal;
import java.util.ArrayList;

/**
 * Created by BaoCai on 18/6/10.
 * Description:
 */
public class ValueWatchTest {

    @Test
    public void testValueWatch() {

        List<Number> numberList = new ArrayList();
        List<ValueWatcherCondition> confitions = new ArrayList();

        Number value = new BigDecimal("15");

        Number value0 = new BigDecimal("14.57");
        Number value1 = new BigDecimal("13.94");
        Number value2 = new BigDecimal("13.98");
        Number value3 = new BigDecimal("13.06");
        Number value4 = new BigDecimal("13.23");
        Number value5 = new BigDecimal("13.45");
        Number value6 = new BigDecimal("13.90");
        Number value7 = new BigDecimal("11.67");
        Number value8 = new BigDecimal("11.78");
        Number value9 = new BigDecimal("11.80");
        numberList.add(value0);
        numberList.add(value1);
        numberList.add(value2);
        numberList.add(value3);
        numberList.add(value4);
        numberList.add(value5);
        numberList.add(value6);
        numberList.add(value7);
        numberList.add(value8);
        numberList.add(value9);

        ValueWatcherCondition watcherCondition1 = new ValueWatcherCondition();
        watcherCondition1.setChangeRank(new BigDecimal("0.03"));
        watcherCondition1.setCompareTimes(1);

        ValueWatcherCondition watcherCondition2 = new ValueWatcherCondition();
        watcherCondition2.setChangeRank(new BigDecimal("0.08"));
        watcherCondition2.setCompareTimes(2);

        ValueWatcherCondition watcherCondition3 = new ValueWatcherCondition();
        watcherCondition3.setChangeRank(new BigDecimal("0.15"));
        watcherCondition3.setCompareTimes(4);

        ValueWatcherCondition watcherCondition4 = new ValueWatcherCondition();
        watcherCondition4.setChangeRank(new BigDecimal("0.30"));
        watcherCondition4.setCompareTimes(3);

        confitions.add(watcherCondition1);
        confitions.add(watcherCondition2);
        confitions.add(watcherCondition3);
        confitions.add(watcherCondition4);

//        for (ValueWatcherCondition condition : confitions) {
//            if (null != DefaultWatcherMatch.watcherMatch.match(value, numberList, condition.getChangeRank(), condition.getCompareTimes())) {
//                break;
//            }
//        }

    }

}
