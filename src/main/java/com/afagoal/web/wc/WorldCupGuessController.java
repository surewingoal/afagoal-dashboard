package com.afagoal.web.wc;

import com.afagoal.constant.BaseConstant;
import com.afagoal.dao.wc.AdvertisementDao;
import com.afagoal.dao.wc.WorldCupGuessDao;
import com.afagoal.dto.wc.AdvertisementDto;
import com.afagoal.dto.wc.CountryEnum;
import com.afagoal.dto.wc.GuessNumDto;
import com.afagoal.dto.wc.WorldGuessRequestDto;
import com.afagoal.entity.wc.Advertisement;
import com.afagoal.entity.wc.WorldCupGuess;
import com.afagoal.utildto.Response;
import com.querydsl.core.types.dsl.BooleanExpression;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Map;
import java.util.stream.Collectors;

/**
 * Created by BaoCai on 18/6/3.
 * Description:
 */
@Controller
public class WorldCupGuessController {

    @Autowired
    private WorldCupGuessDao worldCupGuessDao;
    @Autowired
    private AdvertisementDao advertisementDao;

    @PostMapping("/world_cup/guess")
    @Transactional
    @ResponseBody
    public Response guess(@RequestBody WorldGuessRequestDto requestDto) {
        if (null == requestDto.getChampion() || null == CountryEnum.valueOfEnum(requestDto.getChampion())) {
            return Response.error("请填入世界杯参赛队伍名称！");
        }
        WorldCupGuess worldCupGuessEntity = new WorldCupGuess();
        BeanUtils.copyProperties(requestDto, worldCupGuessEntity);
        worldCupGuessDao.save(worldCupGuessEntity);
        return Response.ok("竞猜成功！");
    }

    @GetMapping("/world_cup/guess")
    @ResponseBody
    public Response guessList() {
        Map<String, Long> guessNumMap = CountryEnum.guessNum;
        List<GuessNumDto> dtos = guessNumMap.entrySet().stream()
                .map(entry -> GuessNumDto.instance(entry.getKey(), entry.getValue()))
                .collect(Collectors.toList());
        dtos.sort((dto1, dto2) ->
                (int) (dto2.getGuessNum() - dto1.getGuessNum()));
        return Response.ok(dtos);
    }

    @GetMapping("/world_cup/guess/is_voted")
    @ResponseBody
    public Response voted(@RequestParam(value = "avatar_url") String avatarUrl,
                          @RequestParam(value = "wechat_nick_name") String wechatNickName,
                          @RequestParam(value = "city") String city,
                          @RequestParam(value = "gender") Byte gender) {
        List<BooleanExpression> list = new ArrayList();
        list.add(worldCupGuessDao.getQEntity().avatarUrl.eq(avatarUrl));
        if (0 < worldCupGuessDao.getCount(list)) {
            return Response.ok(Boolean.TRUE);
        }
        list.clear();
        list.add(worldCupGuessDao.getQEntity().wechatNickName.eq(wechatNickName));
        list.add(worldCupGuessDao.getQEntity().gender.eq(gender));
        list.add(worldCupGuessDao.getQEntity().city.eq(city));
        if (0 < worldCupGuessDao.getCount(list)) {
            return Response.ok(Boolean.TRUE);
        }
        return Response.ok(Boolean.FALSE);
    }

    @GetMapping("/world_cup/advertise")
    @ResponseBody
    public Response advertisements() {
        List<BooleanExpression> list = new ArrayList();
        list.add(advertisementDao.getQEntity().state.ne(BaseConstant.DELETE_STATE));
        List<Advertisement> advertisements = advertisementDao.getEntities(list, null);

        List<AdvertisementDto> dtos = advertisements.stream()
                .map(advertisement -> AdvertisementDto.instance(advertisement))
                .collect(Collectors.toList());
        return Response.ok(dtos);
    }

    @GetMapping("/world_cup/guess/voted_country")
    @ResponseBody
    public Response votedCountry(@RequestParam(value = "avatar_url") String avatarUrl,
                                 @RequestParam(value = "wechat_nick_name") String wechatNickName,
                                 @RequestParam(value = "city") String city,
                                 @RequestParam(value = "gender") Byte gender) {
        List<BooleanExpression> list = new ArrayList();
        list.add(worldCupGuessDao.getQEntity().avatarUrl.eq(avatarUrl));
        WorldCupGuess worldCupGuess = worldCupGuessDao.getEntity(list);
        if (null == worldCupGuess) {
            list.clear();
            list.add(worldCupGuessDao.getQEntity().wechatNickName.eq(wechatNickName));
            list.add(worldCupGuessDao.getQEntity().gender.eq(gender));
            list.add(worldCupGuessDao.getQEntity().city.eq(city));
            worldCupGuess = worldCupGuessDao.getEntity(list);
        }

        if (worldCupGuess != null) {
            String guessChampion = worldCupGuess.getChampion();
            Long guessNum = CountryEnum.guessNum.get(guessChampion);
            GuessNumDto guessNumDto = GuessNumDto.instance(guessChampion, guessNum);
            return Response.ok(guessNumDto);
        }
        return Response.ok(null);
    }

}
