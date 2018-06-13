package com.afagoal.web.blockchain;

import com.afagoal.task.token.TokenDetailTask;
import com.afagoal.task.token.TokenExtTask;
import com.afagoal.task.token.TokenTopHolderTask;
import com.afagoal.task.token.TokenTopPercentageTask;
import com.afagoal.utildto.Response;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by BaoCai on 18/6/10.
 * Description:
 */
@RestController
public class TokenTaskController {

    @Autowired
    private TokenDetailTask tokenDetailTask;
    @Autowired
    private TokenExtTask tokenExtTask;
    @Autowired
    private TokenTopHolderTask tokenTopHolderTask;
    @Autowired
    private TokenTopPercentageTask tokenTopPercentageTask;

    @PutMapping("/token_task/ext")
    public Response tokenExtTask() {
        tokenExtTask.tokenExtMerge();
        return Response.ok("started ext task !");
    }

    @PutMapping("/token_task/detail")
    public Response tokenDetailTask() {
        tokenDetailTask.tokenDetailMerge();
        return Response.ok("started detail task !");
    }

    @PutMapping("/token_task/top_percentage")
    public Response tokenTopPercentageTask() {
        tokenTopPercentageTask.tokenTopPercentageMerge();
        return Response.ok("started top percentage task !");
    }

    @PutMapping("/token_task/top_holder")
    public Response tokenTopHolderTask() {
        tokenTopHolderTask.tokenTopHolderMerge();
        return Response.ok("started top holder task !");
    }


    @PutMapping("/token_task/token_detail/watch")
    public Response tokenWatchDetail() {
        tokenDetailTask.watchTokenValue();
        return Response.ok("started watch detail task !");
    }

    @PutMapping("/token_task/watch/notice")
    public Response tokenWatchNotice() {
        tokenDetailTask.noticeUser();
        return Response.ok("started token notice task !");
    }

    @PutMapping("/token_task/top_percentage/watch")
    public Response tokenTopPercentageWatchTask() {
        tokenTopPercentageTask.tokenTopPercentageWatch();
        return Response.ok("started top percentage watch task !");
    }

}
