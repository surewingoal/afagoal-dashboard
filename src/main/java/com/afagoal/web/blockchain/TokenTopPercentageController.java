package com.afagoal.web.blockchain;

import com.afagoal.annotation.BehaviorLog;
import com.afagoal.constant.BaseConstant;
import com.afagoal.dao.blockchain.TokenTopPercentageDao;
import com.afagoal.dto.blockchain.TokenSimpleDto;
import com.afagoal.dto.blockchain.tokenTopPercentage.TokenTopPercentageDto;
import com.afagoal.dto.blockchain.tokenTopPercentage.TokenTopPercentageEchartDto;
import com.afagoal.entity.blockchain.TokenTopPercentage;
import com.afagoal.service.token.TokenService;
import com.afagoal.utildto.PageData;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.dsl.BooleanExpression;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.stream.Collectors;

/**
 * Created by BaoCai on 18/6/8.
 * Description:
 */
@Controller
public class TokenTopPercentageController {

    @Autowired
    private TokenService tokenService;
    @Autowired
    private TokenTopPercentageDao tokenTopPercentageDao;

    @RequestMapping("/blockchain/token_top_percentage")
    @BehaviorLog("top持仓比例")
    public String listPage(ModelMap map) {
        TokenSimpleDto hottestToken = tokenService.hottestToken();
        map.put("token_id", hottestToken.getId());
        return "blockchain/tokenTopPercentage/token_top_percentages";
    }

    @RequestMapping("/blockchain/token_top_percentage/info")
    @BehaviorLog("top持仓详情")
    public String info(@RequestParam(value = "id") String id,
                       ModelMap map) {
        TokenTopPercentage tokenTopPercentage = tokenTopPercentageDao.getById(id);
        if (tokenTopPercentage != null) {
            map.put("tokenTopPercentage", TokenTopPercentageDto.instance(tokenTopPercentage));
        }
        return "blockchain/tokenTopPercentage/token_top_percentage_info";
    }

    @RequestMapping("/blockchain/token_top_percentage/echart_line")
    @BehaviorLog("top持仓比例折线图")
    public String infoEchart(ModelMap map) {
        TokenSimpleDto hottestDto = tokenService.hottestToken();
        map.put("token_id", hottestDto.getId());
        map.put("token_name", hottestDto.getTokenName());
        return "blockchain/tokenTopPercentage/token_top_percentage_echart_line";
    }

    @RequestMapping("/blockchain/token_top_percentage/holder_echart_line")
    @BehaviorLog("top持仓量折线图")
    public String infoHolderNumsEchart(ModelMap map) {
        TokenSimpleDto hottestDto = tokenService.hottestToken();
        map.put("token_id", hottestDto.getId());
        map.put("token_name", hottestDto.getTokenName());
        return "blockchain/tokenTopPercentage/token_top_percentage_holders_echart_line";
    }

    @RequestMapping("/blockchain/token_top_percentage/list")
    @BehaviorLog("top持仓列表")
    @ResponseBody
    public PageData topPercentageList(@RequestParam(value = "token_id") String tokenId,
                                      @RequestParam(value = "top_type", defaultValue = "10") Byte topType,
                                      @RequestParam(defaultValue = "0", value = "page") int page,
                                      @RequestParam(defaultValue = "100", value = "size") int size) {
        List<BooleanExpression> booleanExpressionList = new ArrayList();
        booleanExpressionList.add(tokenTopPercentageDao.getQEntity().state.ne(BaseConstant.DELETE_STATE));
        booleanExpressionList.add(tokenTopPercentageDao.getQEntity().tokenId.eq(tokenId));
        booleanExpressionList.add(tokenTopPercentageDao.getQEntity().topType.eq(topType));

        List<OrderSpecifier> orderSpecifiers = new ArrayList();
        orderSpecifiers.add(tokenTopPercentageDao.getQEntity().statisticTime.desc());
        Pageable pageable = new PageRequest(page, size);

        List<TokenTopPercentage> percentages =
                tokenTopPercentageDao.getEntities(booleanExpressionList, orderSpecifiers, pageable);
        Long count = tokenTopPercentageDao.getCount(booleanExpressionList);

        List<TokenTopPercentageDto> dtos = percentages.stream()
                .map(percentage -> TokenTopPercentageDto.instance(percentage))
                .collect(Collectors.toList());
        return new PageData(dtos, count.intValue());
    }

    @GetMapping("/blockchain/token_top_percentage/echart_list")
    @ResponseBody
    @BehaviorLog("top持仓echart数据")
    public List<TokenTopPercentageEchartDto> echartDtos(@RequestParam(value = "token_id") String tokenId,
                                                        @RequestParam(value = "top_type", defaultValue = "10") Byte topType,
                                                        @RequestParam(defaultValue = "0", value = "page") int page,
                                                        @RequestParam(defaultValue = "1000", value = "size") int size) {
        List<BooleanExpression> booleanExpressionList = new ArrayList();
        booleanExpressionList.add(tokenTopPercentageDao.getQEntity().state.ne(BaseConstant.DELETE_STATE));
        booleanExpressionList.add(tokenTopPercentageDao.getQEntity().tokenId.eq(tokenId));
        booleanExpressionList.add(tokenTopPercentageDao.getQEntity().topType.eq(topType));

        List<OrderSpecifier> orderSpecifiers = new ArrayList();
        orderSpecifiers.add(tokenTopPercentageDao.getQEntity().statisticTime.asc());
        Pageable pageable = new PageRequest(page, size);

        List<TokenTopPercentage> percentages =
                tokenTopPercentageDao.getEntities(booleanExpressionList, orderSpecifiers, pageable);

        return percentages.stream()
                .map(percentage -> TokenTopPercentageEchartDto.instance(percentage))
                .collect(Collectors.toList());
    }


}
