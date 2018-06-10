package com.afagoal.auxiliary.tokenValueWatcher;

import com.afagoal.dto.base.ValueDateModel;

import org.springframework.util.ObjectUtils;

import java.util.List;

/**
 * Created by BaoCai on 18/6/10.
 * Description:
 */
public class DefaultWatcherMatch implements WatcherMatch {

    public final static WatcherMatch watcherMatch = new DefaultWatcherMatch();

    private DefaultWatcherMatch() {
    }

    @Override
    public ValueDateModel match(Number value, List<ValueDateModel> listValues, Number changeRank, int compareTimes) {
        if (ObjectUtils.isEmpty(value)) {
            throw new RuntimeException("当前value不可为空！");
        }

        for (int i = 0; i < compareTimes; i++) {

            if (ObjectUtils.isEmpty(listValues)) {
                return null;
            }

            if (singleMatch(value, listValues.get(0), changeRank)) {
                return listValues.get(0);
            }
            listValues.remove(0);
        }
        return null;
    }

    private boolean singleMatch(Number todayValue, ValueDateModel oldValue, Number changeRank) {
        double scale = todayValue.doubleValue() / oldValue.getValue().doubleValue();
        return scale >= 1 + changeRank.doubleValue() || scale <= 1 - changeRank.doubleValue();
    }
}
