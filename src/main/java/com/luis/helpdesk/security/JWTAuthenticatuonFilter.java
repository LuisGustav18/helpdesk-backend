package com.luis.helpdesk.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.luis.helpdesk.domain.dtos.CredenciaisDTO;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;

// Filtro do Spring Security, intercepta requisição de login /login
public class JWTAuthenticatuonFilter extends UsernamePasswordAuthenticationFilter {

    // Executa a autenticação chama ( UserDetailsService, PasswordEncoder )
    private AuthenticationManager authenticationManager;
    // Cria o token, define o tempo de expiração
    private JWTUtil jwtUtil;

    public JWTAuthenticatuonFilter(AuthenticationManager authenticationManager, JWTUtil jwtUtil) {
        super();
        this.authenticationManager = authenticationManager;
        this.jwtUtil = jwtUtil;
    }

    // Método chamado quando alguém tenta fazer login
    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        try{
            // Le o corpo da requisição json é converte em CredenciasDTO
            CredenciaisDTO creds = new ObjectMapper().readValue(request.getInputStream(), CredenciaisDTO.class);
            // Cria o token de autenticação mas ainda não autenticado
            UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(creds.getEmail(), creds.getSenha(), new ArrayList<>());
            // tenta autenticar o token aqui
            Authentication authentication = authenticationManager.authenticate(authenticationToken);
            return authentication;
        } catch (Exception e){
            throw new RuntimeException(e); // Se não for autenticado lança exceção
        }
    }

    // Caso a autenticação ocorra corretamente entre aqui
    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException, ServletException {
        String username = ((UserSS) authResult.getPrincipal()).getUsername();  // Retorna um UserSS com authResult.getPrincipal()
        String token = jwtUtil.generateToken(username); // Cria o jwt, assina é define o tempo
        // Manda pro header no caso o front-end
        response.setHeader("access-control-expose-headers", "Authorization");
        response.setHeader("Authorization", "Bearer " + token);
    }

    // Caso a autenticação não ocorra corretamente entre aqui
    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException failed) throws IOException, ServletException {
        response.setStatus(401);
        response.setContentType("application/json");
        response.getWriter().append(json());
    }

    private CharSequence json(){
        long date = new Date().getTime();
        return "{"
                + "\"timestamp\": " + date + ", "
                + "\"status\": 401, "
                + "\"error\": \"não autorizado\", "
                + "\"message\": \"Email ou senha inválidos\", "
                + "\"path\": \"/login\"}";
    }


}
