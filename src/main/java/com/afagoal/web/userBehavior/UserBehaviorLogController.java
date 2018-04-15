package com.afagoal.web.userBehavior;

import com.afagoal.constant.BaseStateConstant;
import com.afagoal.dao.behavior.UserBehaviorLogDao;
import com.afagoal.dto.userBehavior.UserBehaviorLogDto;
import com.afagoal.entity.behavior.UserBehaviorLog;
import com.afagoal.utildto.PageData;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.dsl.BooleanExpression;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.stream.Collectors;

/**
 * Created by BaoCai on 18/4/15.
 * Description:
 */
@Controller
public class UserBehaviorLogController {

    @Autowired
    private UserBehaviorLogDao userBehaviorLogDao;

    @RequestMapping(value = "/user_behavior/user_behavior_log", method = RequestMethod.GET)
    public String userBehavior() {
        return "user_behavior/user_behavior_log/user_behavior_logs";
    }

    @RequestMapping("/user_behavior/user_behavior_log/list")
    @ResponseBody
    public PageData list(@RequestParam(value = "user_name", required = false) String userName,
                         @RequestParam(value = "user_id", required = false) Integer userId,
                         @RequestParam(value = "page", defaultValue = "1") int page,
                         @RequestParam(value = "size", defaultValue = "10") int size) {
        List<BooleanExpression> expressionList = new ArrayList();
        expressionList.add(userBehaviorLogDao.getQEntity().state.ne((byte) 99));
        if (StringUtils.isNotEmpty(userName)) {
            expressionList.add(userBehaviorLogDao.getQEntity().userName.like("%" + userName + "%"));
        }
        if (null != userId) {
            expressionList.add(userBehaviorLogDao.getQEntity().userId.eq(userId));
        }
        Pageable pageable = new PageRequest(Math.max(0, page), size);
        List<OrderSpecifier> orderSpecifiers = new ArrayList();
        orderSpecifiers.add(userBehaviorLogDao.getQEntity().createdAt.desc());
        List<UserBehaviorLog> logs = userBehaviorLogDao.getEntities(expressionList, orderSpecifiers, pageable);

        List<UserBehaviorLogDto> dtos = logs.stream()
                .map(log -> UserBehaviorLogDto.instance(log))
                .collect(Collectors.toList());
        long count = userBehaviorLogDao.getCount(expressionList);

        return new PageData(dtos, (int) count);
    }

}
