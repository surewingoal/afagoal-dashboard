package com.afagoal.dto.sys;

import com.afagoal.constant.BaseConstant;
import com.afagoal.entity.system.SysUser;
import com.afagoal.entity.system.SysUserExt;
import com.afagoal.security.MD5Utils;
import com.afagoal.utils.regex.RegExUtils;
import com.fasterxml.jackson.annotation.JsonProperty;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;

import lombok.Getter;
import lombok.Setter;

/**
 * Created by BaoCai on 18/6/28.
 * Description:
 */
@Getter
@Setter
public class WechatUserRegisterDto {

    @JsonProperty("wechat_nick_name")
    private String wechatNickName;

    @JsonProperty("wechat_avatar_url")
    private String wechatAvatarUrl;

    @JsonProperty("wechat_gender")
    private Byte wechatGender;

    @JsonProperty("wechat_city")
    private String wechatCity;

    @JsonProperty("wechat_province")
    private String wechatProvince;

    @JsonProperty("wechat_language")
    private String wechatLanguage;

    @JsonProperty("wechat_country")
    private String wechatCountry;

    @JsonProperty("email")
    private String email;

    @JsonProperty("mobile")
    private String mobile;

    @JsonProperty("password")
    private String password;

    @JsonProperty("repassword")
    private String repassword;

    @JsonProperty("user_name")
    private String userName;


    public String valid() {
        if (StringUtils.isEmpty(this.getMobile())) {
            return "请填写手机号码！";
        }
        if (!RegExUtils.isMobile(this.getMobile())) {
            return "请填写正确格式手机号码！";
        }
        if (StringUtils.isEmpty(this.getEmail())) {
            return "请填写邮箱！";
        }
        if (!RegExUtils.isEmail(this.getEmail())) {
            return "请填写正确格式的邮箱！";
        }
        if (StringUtils.isEmpty(this.getUserName())) {
            return "请填写登录名！";
        }
        if (StringUtils.isEmpty(this.getPassword())) {
            return "请填写密码！";
        }
        if (!this.getPassword().equals(this.getRepassword())) {
            return "两次输入密码不正确！";
        }
        return null;
    }

    public SysUser instanceUser() {
        SysUser user = new SysUser();
        user.setPassword(MD5Utils.passwordSecurcy(this.getPassword()));
        user.setUserName(this.getUserName());
        user.setMobile(this.getMobile());
        user.setNickName(this.getWechatNickName());
        user.setState(BaseConstant.DEFAULT_STATE);
        user.setEmail(this.getEmail());
        return user;
    }

    public SysUserExt instanceUserExt() {
        SysUserExt sysUserExt = new SysUserExt();
        BeanUtils.copyProperties(this, sysUserExt);
        return sysUserExt;
    }

}
