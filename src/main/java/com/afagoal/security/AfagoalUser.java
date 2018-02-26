package com.afagoal.security;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.Collection;

import lombok.Data;

/**
 * Created by BaoCai on 18/2/26.
 * Description:
 */
@Data
public class AfagoalUser extends User {

    private Integer id;

    public AfagoalUser(String username, String password, Collection<? extends GrantedAuthority> authorities) {
        super(username, password, authorities);
    }

    public AfagoalUser(String username, String password, Collection<? extends GrantedAuthority> authorities,Integer id) {
        super(username, password, authorities);
        this.id = id;
    }
}
