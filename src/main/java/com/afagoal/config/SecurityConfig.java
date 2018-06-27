package com.afagoal.config;

import com.afagoal.dao.system.SysUserDao;
import com.afagoal.security.AfagoalLogoutFilter;
import com.afagoal.security.AfagoalPasswordEncoder;
import com.afagoal.security.AfagoalSecurityContextPersistenceFilter;
import com.afagoal.security.AfagoalSecurityContextRepository;
import com.afagoal.security.AfagoalUserDetailsService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.event.AbstractAuthenticationEvent;
import org.springframework.security.authentication.event.InteractiveAuthenticationSuccessEvent;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.logout.LogoutFilter;
import org.springframework.security.web.context.SecurityContextPersistenceFilter;

/**
 * Created by BaoCai on 18/2/26.
 * Description:
 */
@Configuration
@EnableWebSecurity
//@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true, jsr250Enabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter
        implements ApplicationListener<AbstractAuthenticationEvent> {

    @Autowired
    private SysUserDao sysUserDao;

    @Value("${afagoal.session.enable}")
    private Boolean afagoalSessionEnable;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
                .antMatchers("/css/**", "/fonts/**", "/img/**", "/js/**", "/editor-app/**").permitAll()
                .antMatchers("/register/**").permitAll()
                .antMatchers("/world_cup/**").permitAll()
                .antMatchers("/token_task/**").permitAll()
                .antMatchers("/session/**").permitAll()
                .anyRequest().authenticated()
                .and().logout().logoutUrl("/logout")
                .and().formLogin()
                .loginProcessingUrl("/login")
                .failureUrl("/login?authentication_error=true")
                .defaultSuccessUrl("/")
                .loginPage("/login").permitAll();

        http.sessionManagement()
                .maximumSessions(1);

        http.csrf().disable();

        http.headers().frameOptions().sameOrigin();

        if (afagoalSessionEnable) {
            customSecurityContextFilter(http);
        }
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService()).passwordEncoder(new AfagoalPasswordEncoder());
    }

    @Bean
    public UserDetailsService userDetailsService() {
        return new AfagoalUserDetailsService(sysUserDao);
    }

    private void customSecurityContextFilter(HttpSecurity http) throws NoSuchFieldException, IllegalAccessException {
        AfagoalSecurityContextRepository afagoalSecurityContextRepository =
                new AfagoalSecurityContextRepository();
        AfagoalSecurityContextPersistenceFilter securityContextPersistenceFilter =
                new AfagoalSecurityContextPersistenceFilter(afagoalSecurityContextRepository);
        AfagoalLogoutFilter afagoalLogoutFilter =
                new AfagoalLogoutFilter(afagoalSecurityContextRepository);
        http.addFilterAfter(securityContextPersistenceFilter, SecurityContextPersistenceFilter.class);
        http.addFilterBefore(afagoalLogoutFilter, LogoutFilter.class);
    }

    @Override
    public void onApplicationEvent(AbstractAuthenticationEvent event) {
        if (afagoalSessionEnable) {
            if (event instanceof InteractiveAuthenticationSuccessEvent) {
                org.springframework.security.core.context.SecurityContext securityContext = SecurityContextHolder.getContext();
                AfagoalSecurityContextRepository.saveContext(securityContext);
            }
        }
    }
}
