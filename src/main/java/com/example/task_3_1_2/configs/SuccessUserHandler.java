package com.example.task_3_1_2.configs;

import com.example.task_3_1_2.models.User;
import com.example.task_3_1_2.repos.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Set;

@Component
public class SuccessUserHandler implements AuthenticationSuccessHandler {
    // Spring Security использует объект Authentication, пользователя авторизованной сессии.
    @Autowired
    private UserRepository userRepository;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Authentication authentication) throws IOException {

        Set<String> roles = AuthorityUtils.authorityListToSet(authentication.getAuthorities());
        if (roles.contains("ADMIN")) {
            User admin = userRepository.findByUsername(authentication.getName());
            httpServletResponse.sendRedirect("/admin/" + admin.getId());
        } else if (roles.contains("USER")) {
            User user = userRepository.findByUsername(authentication.getName());
            httpServletResponse.sendRedirect("/user/" + user.getId());
        } else {
            httpServletResponse.sendRedirect("/");
        }
    }
}
