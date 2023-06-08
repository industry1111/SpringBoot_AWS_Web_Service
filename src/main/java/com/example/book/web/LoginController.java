package com.example.book.web;

import com.example.book.domain.service.login.LoginService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/login/oauth2", produces = "application/json")
public class LoginController {
    LoginService loginService;

    public LoginController(LoginService loginService) {
        this.loginService = loginService;
    }
    @GetMapping("/code/{registrationId}")
    public void googleLogin(@RequestParam String code, @PathVariable String registrationId) {
        System.out.println("googleLogin");
        loginService.socialLogin(code, registrationId);
    }
}
