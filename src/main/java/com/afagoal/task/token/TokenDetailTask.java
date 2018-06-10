package com.afagoal.task.token;

import com.afagoal.dto.blockchain.TokenSimpleDto;
import com.afagoal.service.token.TokenDetailService;
import com.afagoal.service.token.TokenService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

import lombok.Getter;

/**
 * Created by BaoCai on 18/6/4.
 * Description:
 */
@Component
public class TokenDetailTask {

    @Autowired
    private TokenDetailService tokenDetailService;
    @Autowired
    private TokenService tokenService;

    @Scheduled(cron = "0 10 4 * * ? ")
    public void tokenDetailMerge() {

        long now = System.currentTimeMillis();
        System.out.println("token_detail merge start at : " + now);
        Pageable pageable = new PageRequest(0, TokenTaskHolder.DEFAULT_PAGE_SIZE);
        List<TokenSimpleDto> tokenSimpleDtos = tokenService.simpleTokens();
        int startIndex = 0;
        int endIndex = Math.min(TokenTaskHolder.DEFAULT_PAGE_SIZE, tokenSimpleDtos.size());
        while (true) {
            List<TokenSimpleDto> tokens = tokenSimpleDtos.subList(startIndex, endIndex);
            List<String> tokenIds = tokens.stream()
                    .map(token -> token.getId())
                    .collect(Collectors.toList());
            TokenTaskHolder.TASK_EXECUTOR.execute(new TokenDetailRunnable(tokenIds, pageable));
            pageable = pageable.next();
            startIndex += TokenTaskHolder.DEFAULT_PAGE_SIZE;
            endIndex = Math.min((endIndex + TokenTaskHolder.DEFAULT_PAGE_SIZE), tokenSimpleDtos.size());
            if (startIndex >= tokenSimpleDtos.size()) {
                break;
            }
            pageable = pageable.next();
        }

    }

//    @Scheduled(cron = "0 0 5 * * ? ")
    @Scheduled(cron = "0 58 20 * * ? ")
    public void watchTokenValue() {
        List<TokenSimpleDto> tokens = tokenService.simpleTokens();
        tokens.forEach(token ->
                TokenTaskHolder.TASK_EXECUTOR.execute(new TokenValueWatcherTask(token))
        );

    }

    @Getter
    private class TokenDetailRunnable implements Runnable {

        private List<String> tokenIds;

        private Pageable pageable;

        public TokenDetailRunnable(List<String> tokenIds, Pageable pageable) {
            this.tokenIds = tokenIds;
            this.pageable = pageable;
        }

        @Override
        public void run() {
            tokenDetailService.mergeDetails(this.getTokenIds(), this.getPageable());
        }
    }

    @Getter
    private class TokenValueWatcherTask implements Runnable {

        private TokenSimpleDto token;

        TokenValueWatcherTask(TokenSimpleDto token) {
            this.token = token;
        }

        @Override
        public void run() {
            tokenDetailService.watchTokenValue(this.getToken());
        }
    }

}
