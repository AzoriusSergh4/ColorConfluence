package com.cc.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    public UserAuthenticationProvider authenticationProvider;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.cors();
        http.authorizeRequests().antMatchers(HttpMethod.GET, "/api/card/**").permitAll();
        http.authorizeRequests().antMatchers(HttpMethod.GET, "/api/set/**").permitAll();
        http.authorizeRequests().antMatchers(HttpMethod.POST,"/user/register").permitAll();
        http.authorizeRequests().antMatchers(HttpMethod.POST,"/user/confirm-account").permitAll();
        http.authorizeRequests().antMatchers(HttpMethod.GET,"/user/confirm-account").permitAll();
        http.authorizeRequests().antMatchers(HttpMethod.GET,"/user/recover-password").permitAll();
        http.authorizeRequests().antMatchers(HttpMethod.POST,"/user/confirm-recover-password").permitAll();
        http.authorizeRequests().antMatchers(HttpMethod.GET,"/user/confirm-recover-account0").permitAll();
        http.authorizeRequests().antMatchers(HttpMethod.GET,"/api/deck/{id}").permitAll();
        http.authorizeRequests().antMatchers(HttpMethod.GET,"/api/deck/decks/{id}").permitAll();
        http.authorizeRequests().antMatchers(HttpMethod.GET,"/api/format/all").permitAll();

        http.authorizeRequests().anyRequest().authenticated().and().httpBasic();

        http.csrf().disable();

        http.logout().logoutSuccessHandler((rq, rs, a) -> {});
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(authenticationProvider);
    }
}