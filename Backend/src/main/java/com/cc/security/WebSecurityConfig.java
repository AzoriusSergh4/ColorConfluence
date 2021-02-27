package com.cc.security;

import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests().antMatchers(HttpMethod.GET, "/api/card/**").permitAll();
        http.authorizeRequests().antMatchers(HttpMethod.GET, "/api/set/**").permitAll();

        http
                .authorizeRequests()
                .anyRequest().authenticated()
                .and()
                .httpBasic();
        http.cors();
    }
}