package com.afagoal.web.blockchain;

import com.afagoal.annotation.BehaviorLog;
import com.afagoal.constant.BaseConstant;
import com.afagoal.dao.blockchain.TokenDao;
import com.afagoal.dao.blockchain.TokenLinkDao;
import com.afagoal.dto.blockchain.TokenDto;
import com.afagoal.dto.blockchain.TokenSimpleDto;
import com.afagoal.entity.blockchain.Token;
import com.afagoal.entity.blockchain.TokenLink;
import com.afagoal.utildto.PageData;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.dsl.BooleanExpression;

import java.util.Collection;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.util.CollectionUtils;
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
public class TokenController {

    @Autowired
    private TokenDao tokenDao;
    @Autowired
    private TokenLinkDao tokenLinkDao;

    @RequestMapping(value = "/blockchain/token")
    @BehaviorLog("虚拟货币管理")
    public String tokenPage() {
        return "blockchain/token/tokens";
    }

    @RequestMapping(value = "/blockchain/token/info")
    @BehaviorLog("虚拟货币详情")
    public String tokenInfo(@RequestParam(value = "id", required = false) String id,
                            ModelMap map) {
        if (StringUtils.isEmpty(id)) {
            throw new RuntimeException("请上传token id !");
        }
        List<BooleanExpression> booleanExpressions = new ArrayList();
        booleanExpressions.add(tokenDao.getQEntity().id.eq(id));
        booleanExpressions.add(tokenDao.getQEntity().state.ne(BaseConstant.DELETE_STATE));
        List<Token> tokens = tokenDao.getTokens(booleanExpressions, null, null);
        if (!CollectionUtils.isEmpty(tokens)) {
            Token token = tokens.get(0);
            Collection<TokenLink> tokenLinks = tokenLinkDao.getTokenLinks(token.getId());
            token.setTokenLinks(tokenLinks);
            map.put("token", TokenDto.instance(token));
        }
        return "blockchain/token/token_info";
    }

    @RequestMapping(value = "/blockchain/token/list")
    @ResponseBody
    @BehaviorLog("虚拟货币列表")
    public PageData list(@RequestParam(value = "key", required = false) String key,
                         @RequestParam(defaultValue = "0", value = "page") int page,
                         @RequestParam(defaultValue = "10", value = "size") int size) {
        List<BooleanExpression> booleanExpressionList = new ArrayList();

        booleanExpressionList.add(tokenDao.getQEntity().state.ne(BaseConstant.DELETE_STATE));
        if (StringUtils.isNotEmpty(key)) {
            booleanExpressionList.add(tokenDao.getQEntity().tokenCode.like("%" + key + "%")
                    .or(tokenDao.getQEntity().country.like("%" + key + "%"))
                    .or(tokenDao.getQEntity().tokenName.like("%" + key + "%")));
        }

        Pageable pageable = new PageRequest(page, size);
        List<OrderSpecifier> orderSpecifiers = new ArrayList();
        orderSpecifiers.add(tokenDao.getQEntity().weight.desc());
        List<Token> tokens = tokenDao.getTokens(booleanExpressionList,
                orderSpecifiers, pageable);
        long count = tokenDao.getCount(booleanExpressionList);
        List<TokenDto> dtos = tokens.stream()
                .map(token -> TokenDto.instance(token))
                .collect(Collectors.toList());
        return new PageData(dtos, (int) count);
    }

    @RequestMapping(value = "/blockchain/token/simple_list")
    @ResponseBody
    public List<TokenSimpleDto> simpleTokens() {
        List<TokenSimpleDto> dtos = tokenDao.simpleTokens();
        return dtos;
    }

}
