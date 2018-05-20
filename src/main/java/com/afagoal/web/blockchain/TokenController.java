package com.afagoal.web.blockchain;

import com.afagoal.constant.BaseStateConstant;
import com.afagoal.dao.blockchain.TokenDao;
import com.afagoal.dao.blockchain.TokenLinkDao;
import com.afagoal.dto.blockchain.TokenDto;
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
    public String tokenPage() {
        return "blockchain/token/tokens";
    }

    @RequestMapping(value = "/blockchain/token/info")
    public String tokenInfo(@RequestParam(value = "id",required = false) String id,
                            ModelMap map){
        if(StringUtils.isEmpty(id)){
            throw new RuntimeException("请上传token id !");
        }
        List<BooleanExpression> booleanExpressions = new ArrayList();
        booleanExpressions.add(tokenDao.getQEntity().id.eq(id));
        booleanExpressions.add(tokenDao.getQEntity().state.ne(BaseStateConstant.DELETE_STATE));
        List<Token> tokens = tokenDao.getTokens(booleanExpressions,null,null);
        if(!CollectionUtils.isEmpty(tokens)){
            Token token = tokens.get(0);
            Collection<TokenLink> tokenLinks = tokenLinkDao.getTokenLinks(token.getId());
            token.setTokenLinks(tokenLinks);
            map.put("token",token);
        }
        return "blockchain/token/token_info";
    }

    @RequestMapping(value = "/blockchain/token/list")
    @ResponseBody
    public PageData list(@RequestParam(value = "token_name", required = false) String tokenName,
                         @RequestParam(value = "token_code", required = false) String tokenCode,
                         @RequestParam(value = "country", required = false) String country,
                         @RequestParam(defaultValue = "0", value = "page") int page,
                         @RequestParam(defaultValue = "10", value = "size") int size) {
        List<BooleanExpression> booleanExpressionList = new ArrayList();
        booleanExpressionList.add(tokenDao.getQEntity().state.ne(BaseStateConstant.DELETE_STATE));
        if (StringUtils.isNotEmpty(tokenCode)) {
            booleanExpressionList.add(tokenDao.getQEntity().tokenCode.like("%" + tokenCode + "%"));
        }

        if (StringUtils.isNotEmpty(country)) {
            booleanExpressionList.add(tokenDao.getQEntity().country.like("%" + country + "%"));
        }

        if (StringUtils.isNotEmpty(tokenName)) {
            booleanExpressionList.add(tokenDao.getQEntity().tokenName.like("%" + tokenName + "%"));
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

}
