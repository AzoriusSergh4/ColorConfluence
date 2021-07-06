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
        http.authorizeRequests().antMatchers(HttpMethod.GET, "/api/cards/**").permitAll();
        http.authorizeRequests().antMatchers(HttpMethod.GET, "/api/sets").permitAll();
        http.authorizeRequests().antMatchers(HttpMethod.POST, "/users/registration").permitAll();
        http.authorizeRequests().antMatchers(HttpMethod.POST, "/users/account/confirmation").permitAll();
        http.authorizeRequests().antMatchers(HttpMethod.GET, "/users/account/confirmation").permitAll();
        http.authorizeRequests().antMatchers(HttpMethod.GET, "/users/password/recovery").permitAll();
        http.authorizeRequests().antMatchers(HttpMethod.POST, "/users/password/recovery/confirmation").permitAll();
        http.authorizeRequests().antMatchers(HttpMethod.GET, "/api/decks").permitAll();
        http.authorizeRequests().antMatchers(HttpMethod.GET, "/api/decks/{id}").permitAll();
        http.authorizeRequests().antMatchers(HttpMethod.GET, "/api/decks/user/{id}").permitAll();
        http.authorizeRequests().antMatchers(HttpMethod.GET, "/api/formats").permitAll();

        http.authorizeRequests().anyRequest().authenticated().and().httpBasic();

        http.csrf().disable();

        http.logout().logoutSuccessHandler((rq, rs, a) -> {
        });
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(authenticationProvider);
    }
}