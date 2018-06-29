package com.afagoal.web.blockchain;

import com.afagoal.annotation.BehaviorLog;
import com.afagoal.annotation.mvc.RestGetMapping;
import com.afagoal.constant.BaseConstant;
import com.afagoal.dao.blockchain.TokenDetailDao;
import com.afagoal.dto.blockchain.tokenDetail.TokenDetailDto;
import com.afagoal.dto.blockchain.tokenDetail.TokenDetailEchartDto;
import com.afagoal.dto.blockchain.TokenSimpleDto;
import com.afagoal.entity.blockchain.TokenDetail;
import com.afagoal.service.token.TokenDetailService;
import com.afagoal.service.token.TokenService;
import com.afagoal.utildto.PageData;
import com.afagoal.utildto.Response;
import com.afagoal.utils.date.DateUtils;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.dsl.BooleanExpression;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
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
    @Autowired
    private TokenDetailService tokenDetailService;

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
    public PageData<TokenDetailDto> list(@RequestParam(value = "token_id", required = false) String tokenId,
                                         @RequestParam(defaultValue = "0", value = "page") int page,
                                         @RequestParam(defaultValue = "100", value = "size") int size) {
        if (StringUtils.isEmpty(tokenId)) {
            TokenSimpleDto hottestToken = tokenService.hottestToken();
            tokenId = hottestToken.getId();
        }
        return tokenDetailService.tokenDetails(tokenId, page, size);
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

        return tokenDetailService.tokenValues(tokenId, page, size);
    }


    /***********************REST*************************/

    /**
     * @param orderBy 1:市值排名   2:交易量排名   3:涨幅   4:跌幅
     */
    @RestGetMapping("/blockchain/tokens")
    public Response tokenDetails(@RequestParam(defaultValue = "1", value = "order_by") int orderBy,
                                 @RequestParam(required = false, value = "key") String key,
                                 @RequestParam(defaultValue = "0", value = "page") int page,
                                 @RequestParam(defaultValue = "100", value = "size") int size) {

        List<BooleanExpression> booleanExpressionList = new ArrayList();
        booleanExpressionList.add(tokenDetailDao.getQEntity().state.ne(BaseConstant.DELETE_STATE));
        if (StringUtils.isNotEmpty(key)) {
            booleanExpressionList.add(tokenDetailDao.getQEntity().tokenCode.like("%" + key + "%")
                    .or(tokenDetailDao.getQEntity().tokenName.like("%" + key + "%")
                    ));
        }

        booleanExpressionList.add(tokenDetailDao.getQEntity()
                .statisticTime.between(LocalDateTime.now().plusDays(-1), LocalDateTime.now()));

        List<OrderSpecifier> orderSpecifiers = new ArrayList();
        if (orderBy == 1) {
            orderSpecifiers.add(tokenDetailDao.getQEntity().totalValue.desc());
        } else if (orderBy == 2) {
            orderSpecifiers.add(tokenDetailDao.getQEntity().todayTransaction.desc());
        } else if (orderBy == 3) {
            //TODO   涨跌幅排序
        } else if (orderBy == 4) {

        }

        Pageable pageable = new PageRequest(page, size);

        List<TokenDetail> details = tokenDetailDao.getEntities(booleanExpressionList, orderSpecifiers, pageable);
        Long count = tokenDetailDao.getCount(booleanExpressionList);

        List<TokenDetailDto> dtos = details.stream()
                .map(detail -> TokenDetailDto.instance(detail))
                .collect(Collectors.toList());

        return Response.ok(new PageData(dtos, count.intValue()));
    }

    @RestGetMapping("/blockchain/tokens/{token_id}/details")
    public Response tokenDetails(@PathVariable(value = "token_id") String tokenId,
                                 @RequestParam(defaultValue = "0", value = "page") int page,
                                 @RequestParam(defaultValue = "100", value = "size") int size) {
        List<TokenDetailEchartDto> dtos = tokenDetailService.tokenValues(tokenId, page, size);
        return Response.ok(dtos);
    }

    @RestGetMapping("/blockchain/tokens/{token_id}/value_info")
    public Response tokenInfo(@PathVariable(value = "token_id") String tokenId) {
        TokenDetail detail = tokenDetailDao.todayDetail(tokenId);
        return Response.ok(TokenDetailDto.instance(detail));
    }

}
