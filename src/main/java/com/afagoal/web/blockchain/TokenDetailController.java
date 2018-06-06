package com.afagoal.web.blockchain;

import com.afagoal.annotation.BehaviorLog;
import com.afagoal.constant.BaseConstant;
import com.afagoal.dao.blockchain.TokenDetailDao;
import com.afagoal.dto.blockchain.TokenDetailDto;
import com.afagoal.dto.blockchain.TokenDetailEchartDto;
import com.afagoal.dto.blockchain.TokenSimpleDto;
import com.afagoal.entity.blockchain.TokenDetail;
import com.afagoal.service.token.TokenService;
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
    @Autowired
    private TokenService tokenService;

    @RequestMapping("/blockchain/token_detail")
    @BehaviorLog("币种每日详情")
    public String tokenDetailPage(ModelMap map) {
        TokenSimpleDto hottestToken = tokenService.hottestToken();
        map.put("token_id", hottestToken.getId());
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
        map.put("detail", TokenDetailDto.instance(detail));
        return "blockchain/tokenDetail/token_detail_info";
    }

    @RequestMapping("/blockchain/token_detail/echart_bar")
    @BehaviorLog("币种每日详情Echart柱状图")
    public String tokenDetailEChart_bar(ModelMap map) {
        TokenSimpleDto hottestToken = tokenService.hottestToken();
        map.put("token_id", hottestToken.getId());
        map.put("detail", hottestToken);
        return "blockchain/tokenDetail/token_detail_echart_bar";
    }

    @RequestMapping("/blockchain/token_detail/echart_k")
    @BehaviorLog("币种每日详情Echart K线图")
    public String tokenDetailEChart_K(ModelMap map) {
        TokenSimpleDto hottestToken = tokenService.hottestToken();
        map.put("token_id", hottestToken.getId());
        map.put("detail", hottestToken);
        return "blockchain/tokenDetail/token_detail_echart_k";
    }

    @RequestMapping("/blockchain/token_detail/echart_line")
    @BehaviorLog("币种每日详情Echart折线图")
    public String tokenDetailEChart_line(ModelMap map) {
        TokenSimpleDto hottestToken = tokenService.hottestToken();
        map.put("token_id", hottestToken.getId());
        map.put("detail", hottestToken);
        return "blockchain/tokenDetail/token_detail_echart_line";
    }

    @RequestMapping("/blockchain/token_detail/list")
    @ResponseBody
    @BehaviorLog("币种每日详情列表")
    public PageData<TokenDetailDto> list(@RequestParam(value = "start_date", required = false) String startDate,
                                         @RequestParam(value = "end_date", required = false) String endDate,
                                         @RequestParam(value = "token_id", required = false) String tokenId,
                                         @RequestParam(value = "key", required = false) String key,
                                         @RequestParam(defaultValue = "0", value = "page") int page,
                                         @RequestParam(defaultValue = "10", value = "size") int size) {
        List<BooleanExpression> booleanExpressionList = new ArrayList();
        booleanExpressionList.add(tokenDetailDao.getQEntity().state.ne(BaseConstant.DELETE_STATE));
        LocalDate end = LocalDate.now();
        LocalDate start = LocalDate.now().plusDays(-60);
        if (StringUtils.isNotEmpty(endDate)) {
            end = DateUtils.valueOfDate(endDate);
        }
        if (StringUtils.isNotEmpty(startDate)) {
            start = DateUtils.valueOfDate(startDate);
        }
        if (StringUtils.isEmpty(tokenId)) {
            TokenSimpleDto hottestToken = tokenService.hottestToken();
            tokenId = hottestToken.getId();
        }
        if (StringUtils.isNotEmpty(key)) {
            booleanExpressionList.add(tokenDetailDao.getQEntity().tokenCode.like("%" + key + "%")
                    .or(tokenDetailDao.getQEntity().tokenName.like("%" + key + "%")
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

    @RequestMapping("/blockchain/token_detail/echart_list")
    @ResponseBody
    @BehaviorLog("币种每日详情echart数据")
    public List<TokenDetailEchartDto> tokenDetailEchartDtos(@RequestParam(value = "token_id", required = false) String tokenId,
                                                            @RequestParam(defaultValue = "0", value = "page") int page,
                                                            @RequestParam(defaultValue = "100", value = "size") int size) {
        if (StringUtils.isEmpty(tokenId)) {
            TokenSimpleDto hottestToken = tokenService.hottestToken();
            tokenId = hottestToken.getId();
        }
        List<BooleanExpression> booleanExpressionList = new ArrayList();
        booleanExpressionList.add(tokenDetailDao.getQEntity().state.ne(BaseConstant.DELETE_STATE));
        booleanExpressionList.add(tokenDetailDao.getQEntity().tokenId.eq(tokenId));
        List<OrderSpecifier> orderSpecifiers = new ArrayList();
        orderSpecifiers.add(tokenDetailDao.getQEntity().statisticTime.desc());
        Pageable pageable = new PageRequest(page, size);
        List<TokenDetail> details = tokenDetailDao.getEntities(booleanExpressionList, orderSpecifiers, pageable);
        List<TokenDetailEchartDto> dtos = details.stream()
                .map(detail -> TokenDetailEchartDto.instance(detail))
                .collect(Collectors.toList());
        return dtos;
    }

}
