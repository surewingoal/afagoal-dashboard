package com.afagoal.task.token;

import com.afagoal.dao.blockchain.TokenDetailDao;
import com.afagoal.entity.blockchain.TokenDetail;
import com.afagoal.service.token.TokenService;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import lombok.Getter;

/**
 * Created by BaoCai on 18/5/28.
 * Description:
 */
@Component
public class TokenExtTask {

    @Autowired
    private TokenDetailDao tokenDetailDao;
    @Autowired
    private TokenService tokenService;

    @Scheduled(cron = "0 30 4 * * ? ")
    public void tokenExtMerge() {
        long now = System.currentTimeMillis();
        System.out.println("token_ext merge start at : " + now);
        Pageable pageable = new PageRequest(0, TokenTaskHolder.DEFAULT_PAGE_SIZE);
        while (true) {
            List<TokenDetail> details = tokenDetailDao.todayDetails(pageable);

            TokenTaskHolder.TASK_EXECUTOR.execute(new TokenExtMergeTask(details));

            if (details.size() < TokenTaskHolder.DEFAULT_PAGE_SIZE) {
                break;
            }
            pageable = pageable.next();
        }
    }

    @Getter
    private class TokenExtMergeTask implements Runnable {
        private List<TokenDetail> details;

        public TokenExtMergeTask(List<TokenDetail> details) {
            this.details = details;
        }

        @Override
        public void run() {
            tokenService.mergeExt(details);
        }
    }

}
