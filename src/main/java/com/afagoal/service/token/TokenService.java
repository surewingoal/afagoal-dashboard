package com.afagoal.service.token;

import com.afagoal.constant.BaseConstant;
import com.afagoal.dao.blockchain.TokenDao;
import com.afagoal.dao.blockchain.TokenExtDao;
import com.afagoal.dto.blockchain.TokenSimpleDto;
import com.afagoal.entity.blockchain.TokenDetail;
import com.afagoal.entity.blockchain.TokenExt;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.time.LocalDateTime;
import java.util.ArrayList;
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
}
