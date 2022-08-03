package com.example.assignmentt2009m1springboot.api;

import com.example.assignmentt2009m1springboot.entity.dto.UserInfoDto;
import com.example.assignmentt2009m1springboot.service.AccountService;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/api/")
public class ApiController {
    final AccountService accountService;
    @RequestMapping(value = "/user/get", method = RequestMethod.GET)
    public UserInfoDto getUser(HttpServletRequest request){
        String authorizationHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
        return accountService.getUserInfo(authorizationHeader);
    }
    @RequestMapping(value = "/user/get-admin", method = RequestMethod.GET)
    public UserInfoDto getAdmin(HttpServletRequest request){
        String authorizationHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
        return accountService.getUserInfo(authorizationHeader);
    }
}
