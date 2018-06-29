package com.afagoal.service.token;

import com.afagoal.constant.BaseConstant;
import com.afagoal.dao.blockchain.TokenDao;
import com.afagoal.dao.blockchain.TokenExtDao;
import com.afagoal.dao.blockchain.TokenLinkDao;
import com.afagoal.dto.blockchain.TokenSimpleDto;
import com.afagoal.dto.blockchain.token.TokenDto;
import com.afagoal.entity.blockchain.Token;
import com.afagoal.entity.blockchain.TokenDetail;
import com.afagoal.entity.blockchain.TokenExt;
import com.afagoal.entity.blockchain.TokenLink;
import com.afagoal.utildto.PageData;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.dsl.BooleanExpression;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


/**
 * Created by BaoCai on 18/5/28.
 * Description:
 */
@Service
public class TokenService {

    @Autowired
    private TokenExtDao tokenExtDao;
    @Autowired
    private TokenDao tokenDao;
    @Autowired
    private TokenLinkDao tokenLinkDao;

    private List<TokenSimpleDto> cacheTokenSimpleDtos;

    private TokenExt merge(TokenDetail detail, TokenExt tokenExt) {
        if (null == tokenExt) {
            tokenExt = new TokenExt();
            tokenExt.setTokenId(detail.getTokenId());
            tokenExt.setState(BaseConstant.DEFAULT_STATE);
            tokenExt.setHighestPrice(detail.getUsd());
            tokenExt.setLowestPrice(detail.getUsd());
            tokenExt.setHighestTransaction(detail.getTodayTransaction());
            tokenExt.setLowestTransaction(detail.getTodayTransaction());
            tokenExt.setTransfers(detail.getTransfers());
            tokenExt.setHolders(detail.getHolders());
        } else {
            tokenExt.setHighestPrice(tokenExt.getHighestPrice().max(detail.getUsd()));
            tokenExt.setLowestPrice(tokenExt.getLowestPrice().min(detail.getUsd()));
            tokenExt.setHighestTransaction(Math.max(tokenExt.getHighestTransaction(), detail.getTodayTransaction()));
            tokenExt.setLowestTransaction(Math.min(tokenExt.getLowestTransaction(), detail.getTodayTransaction()));
            tokenExt.setTransfers(detail.getTransfers());
            tokenExt.setHolders(detail.getHolders());
            tokenExt.setTotalSupply(detail.getTransfersUsd());
            tokenExt.setUpdatedAt(LocalDateTime.now());
        }
        return tokenExt;
    }

    @Transactional
    public void mergeExt(List<TokenDetail> details) {
        List<String> tokenIds = details.stream()
                .map(tokenDetail -> tokenDetail.getTokenId())
                .collect(Collectors.toList());
        List<TokenExt> exts = tokenExtDao.getByTokenIds(tokenIds);
        Map<String, TokenExt> extMap = convertListToMap(exts);
        List<TokenExt> updateExts = new ArrayList();
        details.forEach(detail -> {
            TokenExt tokenExt = extMap.get(detail.getTokenId());
            updateExts.add(merge(detail, tokenExt));
        });
        tokenExtDao.save(updateExts);
    }

    private Map<String, TokenExt> convertListToMap(List<TokenExt> exts) {
        if (CollectionUtils.isEmpty(exts)) {
            return new HashMap();
        }
        return exts.stream().collect(
                Collectors.toMap(
                        ext -> ext.getTokenId(),
                        ext -> ext
                )
        );
    }

    @Transactional(readOnly = true)
    public List<TokenSimpleDto> simpleTokens() {
        if (null == cacheTokenSimpleDtos) {
            synchronized (this) {
                if (null == cacheTokenSimpleDtos) {
                    System.out.println("query for cacheToken from DB!");
                    cacheTokenSimpleDtos = tokenDao.simpleTokens();
                }
            }
        }
        return cacheTokenSimpleDtos;
    }

    public TokenSimpleDto hottestToken() {
        return simpleTokens().get(0);
    }

    public void cacheTokenToNull() {
        cacheTokenSimpleDtos = null;
    }


    public PageData getTokens(String key,
                              int page,
                              int size) {
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

    public Token getTokenById(String id) {
        List<BooleanExpression> booleanExpressions = new ArrayList();
        booleanExpressions.add(tokenDao.getQEntity().id.eq(id));
        booleanExpressions.add(tokenDao.getQEntity().state.ne(BaseConstant.DELETE_STATE));
        List<Token> tokens = tokenDao.getTokens(booleanExpressions, null, null);
        if (!CollectionUtils.isEmpty(tokens)) {
            Token token = tokens.get(0);
            Collection<TokenLink> tokenLinks = tokenLinkDao.getTokenLinks(token.getId());
            token.setTokenLinks(tokenLinks);
            return tokens.get(0);
        }
        return null;
    }

}
