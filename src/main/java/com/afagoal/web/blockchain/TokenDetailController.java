package com.afagoal.web.blockchain;

import com.afagoal.annotation.BehaviorLog;
import com.afagoal.constant.BaseConstant;
import com.afagoal.dao.blockchain.TokenDetailDao;
import com.afagoal.dto.blockchain.TokenDetailDto;
import com.afagoal.entity.blockchain.TokenDetail;
import com.afagoal.utildto.PageData;
import com.afagoal.utils.date.DateUtils;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.dsl.BooleanExpression;

import java.time.LocalDate;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.stream.Collectors;

/**
 * Created by BaoCai on 18/5/19.
 * Description:
 */
@Controller
public class TokenDetailController {

    @Autowired
    private TokenDetailDao tokenDetailDao;

    @RequestMapping("/blockchain/token_detail")
    @BehaviorLog("币种每日详情")
    public String tokenDetailPage(ModelMap map) {
        //TODO 获取默认token_id
        map.put("token_id", "1");
        return "blockchain/tokenDetail/token_details";
    }

    @RequestMapping("/blockchain/token_detail/info")
    @BehaviorLog("币种每日详情info")
    public String tokenDetailInfo(@RequestParam(value = "id") String id,
                                  ModelMap map) {
        List<BooleanExpression> list = new ArrayList();
        list.add(tokenDetailDao.getQEntity().state.ne(BaseConstant.DELETE_STATE));
        list.add(tokenDetailDao.getQEntity().id.eq(id));
        TokenDetail detail = tokenDetailDao.getEntity(list);
        map.put("detail", detail);
        return "blockchain/tokenDetail/token_detail_info";
    }

    @RequestMapping("/blockchain/token_detail/echart")
    @BehaviorLog("币种每日详情Echart")
    public String tokenDetailEChart(ModelMap map) {
        //TODO 获取默认token_id
        map.put("token_id", "1");
        List<BooleanExpression> list = new ArrayList();
        list.add(tokenDetailDao.getQEntity().state.ne(BaseConstant.DELETE_STATE));
        list.add(tokenDetailDao.getQEntity().id.eq("1"));
        TokenDetail detail = tokenDetailDao.getEntity(list);
        map.put("detail", detail);
        return "blockchain/tokenDetail/token_detail_echart";
    }

    @RequestMapping("/blockchain/token_detail/list")
    @ResponseBody
    @BehaviorLog("币种每日详情列表")
    public PageData<TokenDetail> list(@RequestParam(value = "start_date", required = false) String startDate,
                                      @RequestParam(value = "end_date", required = false) String endDate,
                                      @RequestParam(value = "token_id", required = false) String tokenId,
                                      @RequestParam(value = "key", required = false) String key,
                                      @RequestParam(defaultValue = "0", value = "page") int page,
                                      @RequestParam(defaultValue = "10", value = "size") int size) {

        //TODO 时间查询，token下拉选
        List<BooleanExpression> booleanExpressionList = new ArrayList();
        booleanExpressionList.add(tokenDetailDao.getQEntity().state.ne(BaseConstant.DELETE_STATE));
        LocalDate end = LocalDate.now();
        LocalDate start = LocalDate.now().plusDays(-10);
        if (StringUtils.isNotEmpty(endDate)) {
            end = DateUtils.valueOfDate(endDate);
        }
        if (StringUtils.isNotEmpty(startDate)) {
            start = DateUtils.valueOfDate(startDate);
        }
        if (StringUtils.isNotEmpty(key)) {
            booleanExpressionList.add(tokenDetailDao.getQEntity().tokenCode.like("%" + key + "%").or(
                    tokenDetailDao.getQEntity().tokenName.like("%" + key + "%")
            ));
        }
        if (StringUtils.isNotEmpty(tokenId)) {
            booleanExpressionList.add(tokenDetailDao.getQEntity().tokenId.eq(tokenId));
        }
        booleanExpressionList.add(tokenDetailDao.getQEntity().statisticTime.between(start.atStartOfDay(), end.atTime(23, 59, 59)));


        List<OrderSpecifier> orderSpecifiers = new ArrayList();
        orderSpecifiers.add(tokenDetailDao.getQEntity().statisticTime.desc());

        Pageable pageable = new PageRequest(page, size);

        List<TokenDetail> details = tokenDetailDao.getEntities(booleanExpressionList, orderSpecifiers, pageable);
        Long count = tokenDetailDao.getCount(booleanExpressionList);

        List<TokenDetailDto> dtos = details.stream()
                .map(detail -> TokenDetailDto.instance(detail))
                .collect(Collectors.toList());

        return new PageData(dtos, count.intValue());
    }

}
