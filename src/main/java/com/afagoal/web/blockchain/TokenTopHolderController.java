package com.afagoal.web.blockchain;

import com.afagoal.annotation.BehaviorLog;
import com.afagoal.constant.BaseConstant;
import com.afagoal.dao.blockchain.TokenTopHolderDao;
import com.afagoal.dto.blockchain.TokenSimpleDto;
import com.afagoal.dto.blockchain.tokenTopHolder.TokenTopHolderDto;
import com.afagoal.dto.blockchain.tokenTopHolder.TokenTopHolderEchartDto;
import com.afagoal.entity.blockchain.TokenTopHolder;
import com.afagoal.service.token.TokenService;
import com.afagoal.service.token.TokenTopHolderService;
import com.afagoal.utildto.PageData;
import com.afagoal.utils.date.DateUtils;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.dsl.BooleanExpression;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by BaoCai on 18/5/20.
 * Description:
 */
@Controller
public class TokenTopHolderController {

    @Autowired
    private TokenTopHolderDao tokenTopHolderDao;
    @Autowired
    private TokenTopHolderService tokenTopHolderService;
    @Autowired
    private TokenService tokenService;

    @RequestMapping("/blockchain/token_top_holder")
    @BehaviorLog("币种排名")
    public String listPage(ModelMap map) {
        TokenSimpleDto hottestToken = tokenService.hottestToken();
        map.put("token_id", hottestToken.getId());
        return "blockchain/tokenTopHolder/token_top_holders";
    }

    @RequestMapping("/blockchain/token_top_holder/info")
    @BehaviorLog("币种排名详情")
    public String info(@RequestParam(value = "id") String id,
                       ModelMap map) {
        TokenTopHolder tokenTopHolder = tokenTopHolderDao.getById(id);
        if (tokenTopHolder != null) {
            map.put("tokenTopHolder", TokenTopHolderDto.instance(tokenTopHolder));
        }
        return "blockchain/tokenTopHolder/token_top_holder_info";
    }

    @RequestMapping("/blockchain/token_top_holder/echart_info")
    @BehaviorLog("币种排名echart页面")
    public String echartInfo(@RequestParam(value = "token_id",required = false) String tokenId,
                             @RequestParam(value = "address",required = false) String address,
                             @RequestParam(value = "id",required = false) String id,
                             ModelMap map) {
        //TODO  直接从前端传过来
        TokenTopHolder tokenTopHolder = tokenTopHolderDao.getById(id);
        tokenId = tokenTopHolder.getTokenId();
        address = tokenTopHolder.getAddress();
        map.put("token_id",tokenId);
        map.put("address",address);
        map.put("id",id);
        return "blockchain/tokenTopHolder/token_top_holder_echart";
    }

    @RequestMapping("/blockchain/token_top_holder/list")
    @ResponseBody
    @BehaviorLog("币种排名列表")
    public PageData<TokenTopHolderDto> list(@RequestParam(value = "start_date", required = false) String date,
                                            @RequestParam(value = "token_id", required = false) String tokenId,
                                            @RequestParam(value = "key", required = false) String key,
                                            @RequestParam(defaultValue = "0", value = "page") int page,
                                            @RequestParam(defaultValue = "10", value = "size") int size) {
        List<BooleanExpression> booleanExpressionList = new ArrayList();
        booleanExpressionList.add(tokenTopHolderDao.getQEntity().state.ne(BaseConstant.DELETE_STATE));
        LocalDate end = LocalDate.now();
        LocalDate start = LocalDate.now();
        if (StringUtils.isNotEmpty(date)) {
            start = DateUtils.valueOfDate(date);
        }
        if (StringUtils.isNotEmpty(key)) {
            booleanExpressionList.add(tokenTopHolderDao.getQEntity().tokenCode.like("%" + key + "%").or(
                    tokenTopHolderDao.getQEntity().tokenName.like("%" + key + "%")
            ));
        }
        if (StringUtils.isNotEmpty(tokenId)) {
            booleanExpressionList.add(tokenTopHolderDao.getQEntity().tokenId.eq(tokenId));
        }
        booleanExpressionList.add(tokenTopHolderDao.getQEntity().statisticTime.between(start.atStartOfDay(), end.atTime(23, 59, 59)));

        List<OrderSpecifier> orderSpecifiers = new ArrayList();
        orderSpecifiers.add(tokenTopHolderDao.getQEntity().rank.asc());

        Pageable pageable = new PageRequest(page, size);
        List<TokenTopHolder> tokenTopHolders = tokenTopHolderDao.getEntities(booleanExpressionList, orderSpecifiers, pageable);
        Long count = tokenTopHolderDao.getCount(booleanExpressionList);

        List<TokenTopHolderDto> dtos = tokenTopHolders.stream()
                .map(tokenTopHolder -> TokenTopHolderDto.instance(tokenTopHolder))
                .collect(Collectors.toList());
        return new PageData(dtos, count.intValue());
    }

    @RequestMapping("/blockchain/token_top_holder/echart_list")
    @ResponseBody
    @BehaviorLog("币种排名echart数据")
    public List<TokenTopHolderEchartDto> echartList(@RequestParam(value = "token_id",required = false) String tokenId,
                                                    @RequestParam(value = "id",required = false) String id,
                                                    @RequestParam(value = "address",required = false) String address) {
        //TODO  直接从前端传过来
        TokenTopHolder tokenTopHolder = tokenTopHolderDao.getById(id);
        tokenId = tokenTopHolder.getTokenId();
        address = tokenTopHolder.getAddress();
        return tokenTopHolderService.echartTopHolders(tokenId, address);
    }

}
