package com.afagoal.service.token;

import com.afagoal.auxiliary.tokenEnum.TokenWatcherEnum;
import com.afagoal.auxiliary.tokenValueWatcher.ValueWatcherGenerator;
import com.afagoal.constant.BaseConstant;
import com.afagoal.dao.blockchain.TokenTopPercentageDao;
import com.afagoal.dao.blockchain.valueWatcher.ValueWatcherConditionDao;
import com.afagoal.dao.blockchain.valueWatcher.ValueWatcherDao;
import com.afagoal.dto.base.ValueDateModel;
import com.afagoal.dto.blockchain.TokenSimpleDto;
import com.afagoal.entity.blockchain.TokenTopPercentage;
import com.afagoal.entity.blockchain.valueWatch.ValueWatcher;
import com.afagoal.entity.blockchain.valueWatch.ValueWatcherCondition;
import com.querydsl.core.types.dsl.BooleanExpression;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

/**
 * Created by BaoCai on 18/6/4.
 * Description:
 */
@Service
public class TokenTopPercentageService {

    @Autowired
    private TokenTopPercentageDao tokenTopPercentageDao;
    @Autowired
    private ValueWatcherConditionDao watcherConditionDao;
    @Autowired
    private ValueWatcherDao valueWatcherDao;

    @Transactional
    public void watchTokenTopPercentage(TokenSimpleDto token) {
        //TODO 重构watch机制
        List<ValueDateModel> historyValues = tokenTopPercentageDao.valueDateModels(ValueWatcherGenerator.WATCHER_SIZE, token.getId(), (byte) 10);
        if (CollectionUtils.isEmpty(historyValues)) {
            return;
        }
        List<ValueWatcherCondition> conditions = watcherConditionDao.getByWatcherType(TokenWatcherEnum.TOKEN_TOP_PERCENTAGE_10.getWatcherType());
        ValueDateModel todayValue = historyValues.remove(0);

        for (ValueWatcherCondition condition : conditions) {
            ValueDateModel needWatch = TokenWatcherEnum.TOKEN_TOP_PERCENTAGE_10.getWatcherMatch()
                    .match(todayValue.getValue(), historyValues, condition.getChangeRank(), condition.getCompareTimes());
            if (null != needWatch) {
                ValueWatcher valueWatcher = ValueWatcherGenerator.createValueWatcher(needWatch, todayValue, condition, token, TokenWatcherEnum.TOKEN_TOP_PERCENTAGE_10);
                valueWatcherDao.save(valueWatcher);
                break;
            }
        }
    }
}
