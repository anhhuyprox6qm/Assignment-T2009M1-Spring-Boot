package com.example.assignmentt2009m1springboot.config;

import com.example.assignmentt2009m1springboot.entity.dto.CredentialDto;
import com.example.assignmentt2009m1springboot.entity.dto.LoginDto;
import com.example.assignmentt2009m1springboot.utils.JwtUtil;
import com.google.gson.Gson;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.catalina.User;
import org.apache.tomcat.util.net.openssl.ciphers.Authentication;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.naming.AuthenticationException;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
public class ApiAuthenticationFilter extends UsernamePasswordAuthenticationFilter {
    private final AuthenticationManager authenticationManager;
    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletRequest response)
        throws AuthenticationException {
        log.info("Checking login information");
        try {
            String jsonData = request.getReader().lines().collect(Collectors.joining());
            Gson gson = new Gson();
            LoginDto loginDto = gson.fromJson(jsonData, LoginDto.class);
            UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(loginDto.getEmail(), loginDto.getPassword());
            return authenticationManager.authenticate(authenticationToken);
        } catch (IOException e) {
            return null;
        }
    }
    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletRequest response, FilterChain chain, Authentication authResult)
        throws IOException, ServletException{
        User user = (User) authResult.getPrincipal();
        String accessToken = JwtUtil.generateToken(user.getUsername(),
                request.getRequestURL().toString(),
                JwtUtil.ONE_DAY * 7);
        String refreshToken = JwtUtil.generateToken(user.getUsername(),
                user.getAuthorities().iterator().next().getAuthority(),
                request.getReader().toString(),
                JwtUtil.ONE_DAY * 14);
        CredentialDto credential = CredentialDto.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .expiresIn(JwtUtil.ONE_DAY * 7)
                .tokenType("Bearer")
                .scope("basic_info")
                .build();
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        Gson gson = new Gson();
        response.getWriter().println(gson.toJson(credential));
    }

    // X??? l?? khi ????ng nh???p kh??ng th??nh c??ng, th??ng b??o l???i, tr??? v??? error ??? d???ng json
    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException failed)
            throws IOException, ServletException {
        HashMap<String, String> errors = new HashMap<>();
        errors.put("message", "Invalid information");
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        Gson gson = new Gson();
        response.getWriter().println(gson.toJson(errors));

    }

}
