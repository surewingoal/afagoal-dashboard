package com.afagoal.web.blockchain;

import com.afagoal.dao.blockchain.TokenDetailDao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by BaoCai on 18/5/19.
 * Description:
 */
@Controller
public class TokenDetailController {

    @Autowired
    private TokenDetailDao tokenDetailDao;

    @RequestMapping("/blockchain/token_detail")
    public String tokenDetailPage() {
        return "blockchain/tokenDetail/token_details";
    }

}
