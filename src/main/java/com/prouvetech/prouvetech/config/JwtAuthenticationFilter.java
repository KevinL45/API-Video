package com.prouvetech.prouvetech.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.filter.OncePerRequestFilter;
import com.prouvetech.prouvetech.service.UserService;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

public class JwtAuthenticationFilter extends OncePerRequestFilter {

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    @Autowired
    private UserService userService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        String token = request.getHeader("Authorization");

        if (token != null && token.startsWith("Bearer ")) {
            token = token.substring(7); // Retirer le "Bearer " du début du token
            logger.info("Validation du token en cours...");
            logger.info("Token reçu : " + token);
            if (jwtTokenProvider.validateToken(token)) {
                String mail = jwtTokenProvider.getUsernameFromToken(token);
                logger.info("Utilisateur authentifié : " + mail);

                UserDetails user = userService.loadUserByUsername(mail);
                if (user != null) {
                    UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                            user, null, user.getAuthorities());
                    SecurityContextHolder.getContext().setAuthentication(authentication);
                }
            } else {
                logger.warn("Token invalide !");
            }
        }

        filterChain.doFilter(request, response); // Continue la chaîne de filtres
    }
}
