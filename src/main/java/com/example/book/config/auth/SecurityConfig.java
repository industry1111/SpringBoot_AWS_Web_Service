package com.example.book.config.auth;

import com.example.book.domain.user.Role;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@RequiredArgsConstructor
@EnableWebSecurity
public class SecurityConfig {

    private final CustomOAuth2UserService customOAuth2UserService;
    @Bean
    SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        return http.csrf().disable() // csrf 보안 설정 사용 X
//                .logout().disable() // 로그아웃 사용 X
                .formLogin().disable() // 폼 로그인 사용 X
                .headers().frameOptions().disable() // h2-console 사용하기 위해 해당 옵션들 disable

                .and()
                .authorizeRequests() // 사용자가 보내는 요청에 인증 절차 수행 필요

                .antMatchers("/","/js/**","/css/**","/images/**","/h2-console/**").permitAll() // 해당 URL은 인증 절차 수행 생략 가능
                .antMatchers("/api/v1/**").hasRole(Role.USER.name())
                .anyRequest().authenticated() // 나머지 요청들은 모두 인증 절차 수행해야함

                .and()
                .logout()
                .logoutSuccessUrl("/")

                .and()
                .oauth2Login() // OAuth2를 통한 로그인 사용
//                .defaultSuccessUrl("/oauth/loginInfo", true) // 로그인 성공시 이동할 URL
                .userInfoEndpoint() // 사용자가 로그인에 성공하였을 경우,
                .userService(customOAuth2UserService) // 해당 서비스 로직을 타도록 설정

                .and()
                .and().build();

    }
}
