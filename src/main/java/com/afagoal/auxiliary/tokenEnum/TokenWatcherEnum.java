package com.afagoal.auxiliary.tokenEnum;

import com.afagoal.auxiliary.tokenValueWatcher.DefaultWatcherMatch;
import com.afagoal.auxiliary.tokenValueWatcher.RemindInfoGenerator;
import com.afagoal.auxiliary.tokenValueWatcher.WatcherMatch;

import lombok.Getter;

/**
 * Created by BaoCai on 18/6/10.
 * Description:
 */
@Getter
public enum TokenWatcherEnum {
    TOKEN_VALUE((byte) 1, DefaultWatcherMatch.watcherMatch,RemindInfoGenerator.TOKEN_VALUE_GENERATOR),
    TOKEN_TOP_PERCENTAGE_10((byte) 3, DefaultWatcherMatch.watcherMatch,RemindInfoGenerator.TOP_PERCENTAGE_10_GENERATOR);

    private Byte watcherType;

    private WatcherMatch watcherMatch;

    private RemindInfoGenerator remindInfoGenerator;

    TokenWatcherEnum(Byte watcherType, WatcherMatch watcherMatch,RemindInfoGenerator remindInfoGenerator) {
        this.watcherType = watcherType;
        this.watcherMatch = watcherMatch;
        this.remindInfoGenerator = remindInfoGenerator;
    }

}
