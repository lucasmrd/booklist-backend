package com.facens.booklist.infra.security;

import com.facens.booklist.repository.UserRepository;
import com.facens.booklist.service.TokenService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class SecurityFilter extends OncePerRequestFilter {

    @Autowired
    private TokenService tokenService;

    @Autowired
    private UserRepository userRepository;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        System.out.println("FILTRO CHAMADO!!");
        var jwt = recuperarToken(request);
        System.out.println("TOKEN JWT= " + jwt);
        if (jwt != null) {
            var subject = tokenService.getSubject(jwt);
            var user = userRepository.findByEmail(subject);
            System.out.println("FIND BY EMAIL=" + user);
            var authentication = new UsernamePasswordAuthenticationToken(user.get(), null, user.get().getAuthorities());
            System.out.println("var authentication = new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());");
            SecurityContextHolder.getContext().setAuthentication(authentication);
            System.out.println("AUTENTICADO");
        }
        System.out.println("PRÓXIMO FILTRO");
        filterChain.doFilter(request, response);
    }

    private String recuperarToken(HttpServletRequest request) {
        var authorizationHeader = request.getHeader("Authorization");
        if (authorizationHeader != null) {
            return authorizationHeader.replace("Bearer ", "");
        }

        return null;
    }
}
