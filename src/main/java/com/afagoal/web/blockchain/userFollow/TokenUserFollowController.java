package com.afagoal.web.blockchain.userFollow;

import com.afagoal.dao.blockchain.userFollow.TokenUserFollowDao;
import com.afagoal.entity.blockchain.userFollow.TokenUserFollow;
import com.afagoal.entity.system.SysUser;
import com.afagoal.security.SecurityContext;
import com.afagoal.utildto.Response;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by BaoCai on 18/6/10.
 * Description:
 */
@RestController
public class TokenUserFollowController {

    @Autowired
    private TokenUserFollowDao tokenUserFollowDao;

    @Transactional
    @PostMapping("/token_user/follow")
    public Response follow(@RequestParam(value = "token_id") String tokenId) {
        SysUser user = SecurityContext.currentUser();
        TokenUserFollow oldFollow = tokenUserFollowDao.findByTokenId(tokenId, user.getId());
        if (null != oldFollow && TokenUserFollowDao.FOLLOW_STATE == oldFollow.getState()) {
            return Response.ok("你已关注该币种！");
        }
        TokenUserFollow newFollow = new TokenUserFollow();
        newFollow.setTokenId(tokenId);
        newFollow.setUserId(user.getId());
        tokenUserFollowDao.save(newFollow);
        return Response.ok("关注成功！");
    }

    @Transactional
    @PutMapping("/token_user/un_follow")
    public Response unFollow(@RequestParam(value = "token_id") String tokenId) {
        SysUser user = SecurityContext.currentUser();
        TokenUserFollow oldFollow = tokenUserFollowDao.findByTokenId(tokenId, user.getId());
        if (null != oldFollow) {
            oldFollow.setState(TokenUserFollowDao.UN_FOLLOW_STATE);
            tokenUserFollowDao.save(oldFollow);
        }
        return Response.ok("取关成功！");
    }

}
