package com.afagoal.task.token;

import com.afagoal.dao.blockchain.TokenDao;
import com.afagoal.dao.blockchain.userFollow.TokenAddressUserFollowDao;
import com.afagoal.dto.blockchain.TokenSimpleDto;
import com.afagoal.dto.blockchain.TokenTopHolderSimpleDto;
import com.afagoal.service.token.TokenService;
import com.afagoal.service.token.TokenTopHolderService;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import lombok.Getter;

/**
 * Created by BaoCai on 18/6/4.
 * Description:
 */
@Component
public class TokenTopHolderTask {

    @Autowired
    private TokenTopHolderService tokenTopHolderService;
    @Autowired
    private TokenService tokenService;
    @Autowired
    private TokenAddressUserFollowDao tokenAddressUserFollowDao;

    @Scheduled(cron = "0 20 4 * * ? ")
    public void tokenTopHolderMerge() {
        System.out.println("token_top_holder merge start at : " + System.currentTimeMillis());
        List<TokenSimpleDto> simpleDtoList = tokenService.simpleTokens();
        simpleDtoList.forEach(token ->
                TokenTaskHolder.TASK_EXECUTOR.execute(new TokenTopHolderRunnable(token.getId()))
        );
    }


    @Scheduled(cron = "0 20 5 * * ? ")
    public void tokenTopHolderWatch(){
        System.out.println("token_top_holder watch start at : " + System.currentTimeMillis());
        List<TokenTopHolderSimpleDto> followedAddress = tokenAddressUserFollowDao.followedAddress();
        followedAddress.forEach(addressDto ->
                TokenTaskHolder.TASK_EXECUTOR.execute(new TokenTopHolderWatcherRunnable(addressDto))
        );
    }

    @Getter
    private class TokenTopHolderRunnable implements Runnable {

        private String tokenId;

        public TokenTopHolderRunnable(String tokenId) {
            this.tokenId = tokenId;
        }

        @Override
        public void run() {
            tokenTopHolderService.mergeTopHolderInfo(this.getTokenId());
        }
    }

    @Getter
    private class TokenTopHolderWatcherRunnable implements Runnable{

        TokenTopHolderSimpleDto addressDto;


        public TokenTopHolderWatcherRunnable(TokenTopHolderSimpleDto addressDto) {
            this.addressDto = addressDto;
        }

        @Override
        public void run() {
            tokenTopHolderService.watchTopHolder(addressDto);
        }
    }

}
