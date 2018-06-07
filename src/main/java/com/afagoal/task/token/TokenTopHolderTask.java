package com.afagoal.task.token;

import com.afagoal.dao.blockchain.TokenDao;
import com.afagoal.dto.blockchain.TokenSimpleDto;
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


    @Scheduled(cron = "0 20 4 * * ? ")
    public void tokenTopHolderMerge() {
        System.out.println("token_top_holder merge start at : " + System.currentTimeMillis());
        List<TokenSimpleDto> simpleDtoList = tokenService.simpleTokens();
        simpleDtoList.forEach(token ->
                TokenTaskHolder.TASK_EXECUTOR.execute(new TokenTopHolderRunnable(token.getId()))
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

}
