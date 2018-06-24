package com.afagoal.auxiliary.tokenValueWatcher;

import com.afagoal.dto.base.ValueDateModel;
import com.afagoal.dto.blockchain.TokenSimpleDto;
import com.afagoal.dto.blockchain.TokenTopHolderSimpleDto;
import com.afagoal.entity.blockchain.valueWatch.ValueWatcherCondition;
import com.afagoal.utils.date.DateUtils;

import java.math.BigDecimal;

/**
 * Created by BaoCai on 18/6/13.
 * Description: 通知内容生成器
 */
@FunctionalInterface
public interface RemindInfoGenerator {

    String createRemindInfo(ValueDateModel needWatch,
                            ValueDateModel todayValue,
                            TokenSimpleDto token,
                            ValueWatcherCondition condition,
                            byte isUp,
                            BigDecimal realValueChange);


    RemindInfoGenerator TOKEN_VALUE_GENERATOR = (needWatch,todayValue,token,condition,isUp,realValueChange) -> {
        StringBuilder builder = new StringBuilder();
        if (null == realValueChange) {
            realValueChange = condition.getChangeRank();
        }
        String upOrDown = isUp == 1 ? "上涨" : "下降";
        builder.append("您关注的币种:")
                .append(token.getTokenName())
                .append("最近价格波动较大。")
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
    };

    RemindInfoGenerator TOP_PERCENTAGE_10_GENERATOR = (needWatch,todayValue,token,condition,isUp,realValueChange) -> {
        StringBuilder builder = new StringBuilder();
        if (null == realValueChange) {
            realValueChange = condition.getChangeRank();
        }
        String upOrDown = isUp == 1 ? "上涨" : "下降";
        builder.append("您关注的币种:")
                .append(token.getTokenName())
                .append("最近TOP持有量波动较大。")
                .append("<br/>")
                .append(condition.getWatchDays())
                .append("天内，持有量")
                .append(upOrDown)
                .append(realValueChange.doubleValue() * 100)
                .append(condition.getWatchUnit())
                .append("。")
                .append("<br/>")
                .append(DateUtils.format(needWatch.getStatisticTime().toLocalDate()))
                .append("持有量：")
                .append(needWatch.getValue())
                .append("％；")
                .append("今日持有量：")
                .append("<br/>")
                .append(todayValue.getValue())
                .append("％。");
        return builder.toString();
    };


    RemindInfoGenerator TOKEN_TOP_HOLDER_GENERATOR = (needWatch,todayValue,token,condition,isUp,realValueChange) -> {
        TokenTopHolderSimpleDto simpleDto = (TokenTopHolderSimpleDto) token;
        StringBuilder builder = new StringBuilder();
        if (null == realValueChange) {
            realValueChange = condition.getChangeRank();
        }
        String upOrDown = isUp == 1 ? "上涨" : "下降";
        builder.append("您关注的仓主:")
                .append(simpleDto.getTokenName())
                .append("：")
                .append(simpleDto.getAddress())
                .append("最近持有量波动较大。")
                .append("<br/>")
                .append(condition.getWatchDays())
                .append("天内，持有量")
                .append(upOrDown)
                .append(realValueChange.doubleValue() * 100)
                .append(condition.getWatchUnit())
                .append("。")
                .append("<br/>")
                .append(DateUtils.format(needWatch.getStatisticTime().toLocalDate()))
                .append("持有量：")
                .append(needWatch.getValue())
                .append("％；")
                .append("今日持有量：")
                .append("<br/>")
                .append(todayValue.getValue())
                .append("％。");
        return builder.toString();
    };
}
