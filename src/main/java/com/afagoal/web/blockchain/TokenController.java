package com.afagoal.web.blockchain;

import com.afagoal.annotation.BehaviorLog;
import com.afagoal.annotation.mvc.RestGetMapping;
import com.afagoal.constant.BaseConstant;
import com.afagoal.dao.blockchain.TokenDao;
import com.afagoal.dao.blockchain.TokenLinkDao;
import com.afagoal.dto.blockchain.token.TokenDto;
import com.afagoal.dto.blockchain.TokenSimpleDto;
import com.afagoal.entity.blockchain.Token;
import com.afagoal.entity.blockchain.TokenLink;
import com.afagoal.service.token.TokenService;
import com.afagoal.utildto.PageData;
import com.afagoal.utildto.Response;
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
import org.springframework.web.bind.annotation.GetMapping;
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
public class TokenController {

    @Autowired
    private TokenService tokenService;

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
        Token token = tokenService.getTokenById(id);
        if (null != token) {
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
        return tokenService.getTokens(key, page, size);
    }

    @RequestMapping(value = "/blockchain/token/simple_list")
    @ResponseBody
    public List<TokenSimpleDto> simpleTokens() {
        List<TokenSimpleDto> dtos = tokenService.simpleTokens();
        return dtos;
    }

    /************************REST*****************************/
    @RestGetMapping("/blockchain/tokens/{token_id}/info")
    public Response tokenInfo(@PathVariable(value = "token_id") String tokenId) {
        Token token = tokenService.getTokenById(tokenId);
        if (null == token) {
            throw new RuntimeException("系统错误：找不到token ： " + tokenId);
        }
        return Response.ok(TokenDto.instance(token));
    }
}
