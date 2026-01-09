package com.luis.helpdesk.config;

import com.luis.helpdesk.security.JWTAuthenticatuonFilter;
import com.luis.helpdesk.security.JWTUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.core.env.Environment;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;

@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private Environment env; // Acessa o perfil ativo

    @Autowired
    private JWTUtil jwtUtil; // Gerar, validar, extrair o usuário do token

    @Autowired
    private UserDetailsService userDetailsService; // Busca o usuário no banco

    private static final String[] PUBLIC_MATCHES = {"/h2-console/**"}; // Define uma rota que não precisa de autenticar

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        // Pegamos a env e verificamos se esta como test
        if(Arrays.asList(env.getActiveProfiles()).contains("test")){
            http.headers().frameOptions().disable(); // se estiver, desativamos o bloqueio de iframe
        }
        http.cors().and().csrf().disable(); // permite outros domínios tenham acesso a API e desativamos a segurança de requisição

        // Intercepta o login, valida o usuário e senha, gera token, e retorna o token
        http.addFilter(new JWTAuthenticatuonFilter(authenticationManager(), jwtUtil));

        // Permite acessarmos o caminho sem token
        http.authorizeRequests().antMatchers(PUBLIC_MATCHES)
                .permitAll().anyRequest().authenticated();

        // Spring não cria sessão, cada requisição precisa mandar um token
        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        // Como busca usuário e como comparar a senha
        auth.userDetailsService(userDetailsService).passwordEncoder(bCryptPasswordEncoder());
    }

    @Bean
    CorsConfigurationSource corsConfigurationSource(){
        // Permite por pradão qualquer origem, headers básicos
        CorsConfiguration configuration = new CorsConfiguration().applyPermitDefaultValues();
        // Define os http métodos permitidos
        configuration.setAllowedMethods(Arrays.asList("POST", "GET", "PUT", "DELETE", "OPTIONS"));
        final UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        // Aplica o cors em todas as rotas
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder(){
        return new BCryptPasswordEncoder();
    }
}
