package com.afagoal.auxiliary.tokenEnum;

import com.afagoal.auxiliary.tokenValueWatcher.DefaultWatcherMatch;
import com.afagoal.auxiliary.tokenValueWatcher.WatcherMatch;

import lombok.Getter;

/**
 * Created by BaoCai on 18/6/10.
 * Description:
 */
@Getter
public enum TokenWatcherEnum {
    TOKEN_VALUE((byte) 1, DefaultWatcherMatch.watcherMatch,"币种价格");

    private Byte watcherType;

    private WatcherMatch watcherMatch;

    private String intro;

    TokenWatcherEnum(Byte watcherType, WatcherMatch watcherMatch,String intro) {
        this.watcherType = watcherType;
        this.watcherMatch = watcherMatch;
        this.intro = intro;
    }

}
