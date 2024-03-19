package com.skcc.stockv1.core.security;//package com.skcc.stockv1.core.security;
//
//import com.skcc.atworks.global.auth.TokenProvider;
//import com.skcc.atworks.global.auth.filters.JwtSecurityFilter;
//import org.springframework.security.config.annotation.SecurityConfigurerAdapter;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.web.DefaultSecurityFilterChain;
//import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
//
//public class SkSecurityConfig extends SecurityConfigurerAdapter<DefaultSecurityFilterChain, HttpSecurity> {
//
//    private TokenProvider tokenProvider;
//
//    public SkSecurityConfig(TokenProvider tokenProvider) {
//        this.tokenProvider = tokenProvider;
//    }
//
//    @Override
//    public void configure(HttpSecurity http) {
//        http.addFilterBefore(
//            new JwtSecurityFilter(tokenProvider),
//            UsernamePasswordAuthenticationFilter.class
//        );
//    }
//}
