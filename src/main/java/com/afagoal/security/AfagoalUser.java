package com.afagoal.security;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import lombok.Getter;
import lombok.Setter;

/**
 * Created by BaoCai on 18/2/26.
 * Description:
 */
@Getter
@Setter
public class AfagoalUser extends User {

    private Integer id;

    private String info;

    private final Map details;

    public AfagoalUser(String username, String password, Collection<? extends GrantedAuthority> authorities, Integer id) {
       this(username,password,authorities,id,null);
    }

    public AfagoalUser(String username, String password, Collection<? extends GrantedAuthority> authorities, Integer id,Map details) {
        super(username, password, authorities);
        this.id = id;
        this.details = details;
    }
}
