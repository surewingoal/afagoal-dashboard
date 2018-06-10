package com.afagoal.auxiliary.tokenValueWatcher;

import com.afagoal.dto.base.ValueDateModel;

import java.util.List;

/**
 * Created by BaoCai on 18/6/10.
 * Description:
 */
public interface WatcherMatch {

    ValueDateModel match(Number value, List<ValueDateModel> listValues, Number changeRank, int compareTimes);

}
