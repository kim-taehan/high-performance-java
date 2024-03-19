package com.skcc.orderv1.core.security;//package com.skcc.orderv1.core.security;
//
//import com.skcc.orderv1.core.security.handler.SkAccessDeniedHandler;
//import com.skcc.orderv1.core.security.handler.SkAuthenticationEntryPoint;
//import lombok.RequiredArgsConstructor;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.config.http.SessionCreationPolicy;
//import org.springframework.security.web.SecurityFilterChain;
//import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
//
//@Configuration
//@RequiredArgsConstructor
//public class SecurityConfig {
//
//    @Bean
//    public SkAccessDeniedHandler skAccessDeniedHandler() {
//        return new SkAccessDeniedHandler();
//    }
//
//    @Bean
//    public SkAuthenticationEntryPoint skAuthenticationEntryPoint() {
//        return new SkAuthenticationEntryPoint();
//    }
//
//    @Bean
//    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
//        http
//                // token을 사용하는 방식이기 때문에 csrf를 disable합니다.
//                .csrf(csrf -> csrf.disable())
//
////                .addFilterBefore(corsFilter, UsernamePasswordAuthenticationFilter.class)
//                .exceptionHandling(exceptionHandling -> exceptionHandling
//                        .accessDeniedHandler(skAccessDeniedHandler())
//                        .authenticationEntryPoint(skAuthenticationEntryPoint())
//                )
//
//                .authorizeHttpRequests(authorizeHttpRequests -> authorizeHttpRequests
////                        .requestMatchers(permitAllUrls()).permitAll()
////                        .requestMatchers("/api/**").authenticated()
//                        .anyRequest().permitAll()
//                )
//
//                // 세션을 사용하지 않기 때문에 STATELESS로 설정
//                .sessionManagement(sessionManagement ->
//                        sessionManagement.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
//                )
//
////                .apply(new JwtSecurityConfig(tokenProvider));
//
//        return http.build();
//    }
//}
