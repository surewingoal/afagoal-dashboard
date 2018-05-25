package com.afagoal.config;

import com.afagoal.dao.system.SysUserDao;
import com.afagoal.security.AfagoalPasswordEncoder;
import com.afagoal.security.AfagoalUserDetailsService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;

/**
 * Created by BaoCai on 18/2/26.
 * Description:
 */
@Configuration
@EnableWebSecurity
//@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true, jsr250Enabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private SysUserDao sysUserDao;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
                .antMatchers("/css/**", "/fonts/**", "/img/**", "/js/**", "/editor-app/**").permitAll()
                .antMatchers("/register/**").permitAll()
                .anyRequest().authenticated()
                .and().logout().logoutUrl("/logout")
                .and().formLogin()
                .loginProcessingUrl("/login")
                .failureUrl("/login?authentication_error=true")
                .defaultSuccessUrl("/")
                .loginPage("/login").permitAll();

        http.csrf().disable();

        http.headers().frameOptions().sameOrigin();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService()).passwordEncoder(new AfagoalPasswordEncoder());
    }

    @Bean
    public UserDetailsService userDetailsService() {
        return new AfagoalUserDetailsService(sysUserDao);
    }

}
