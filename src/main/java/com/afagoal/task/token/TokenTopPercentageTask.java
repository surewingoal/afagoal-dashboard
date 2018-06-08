package com.afagoal.task.token;

import com.afagoal.dto.blockchain.TokenSimpleDto;
import com.afagoal.service.token.TokenService;
import com.afagoal.service.token.TokenTopPercentageService;

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
public class TokenTopPercentageTask {

    @Autowired
    private TokenTopPercentageService tokenTopPercentageService;
    @Autowired
    private TokenService tokenService;

    @Scheduled(cron = "0 40 4 * * ? ")
    public void tokenTopPercentageMerge() {
        System.out.println("token_top_holder merge start at : " + System.currentTimeMillis());
        List<TokenSimpleDto> dtoList = tokenService.simpleTokens();
        dtoList.forEach(token ->
                TokenTaskHolder.TASK_EXECUTOR.execute(new TokenTopPercentageRunnable(token.getId()))
        );
    }

    @Getter
    private class TokenTopPercentageRunnable implements Runnable {

        private String tokenId;

        public TokenTopPercentageRunnable(String tokenId) {
            this.tokenId = tokenId;
        }

        @Override
        public void run() {
            tokenTopPercentageService.mergeTokenTopPercentage(this.getTokenId());
        }
    }
}
