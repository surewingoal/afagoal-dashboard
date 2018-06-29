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

//    @Scheduled(cron = "0 10 5 * * ? ")
    public void tokenTopPercentageWatch() {
        System.out.println("token_top_percentage_watch start at : " + System.currentTimeMillis());
        List<TokenSimpleDto> tokens = tokenService.simpleTokens();
        tokens.forEach(token ->
                TokenTaskHolder.TASK_EXECUTOR.execute(new TokenTopPercentageWatchRunnable(token))
        );
    }

    @Getter
    private class TokenTopPercentageWatchRunnable implements Runnable {

        private TokenSimpleDto token;

        public TokenTopPercentageWatchRunnable(TokenSimpleDto token) {
            this.token = token;
        }

        @Override
        public void run() {
            tokenTopPercentageService.watchTokenTopPercentage(token);
        }
    }
}
